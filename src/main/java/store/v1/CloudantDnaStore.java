
package store.v1;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.JsonObject;

import vo.v1.Dna;

public class CloudantDnaStore implements DnaStore{
    
    private Database db = null;
    private static final String databaseName = "mydb";
    
    public CloudantDnaStore(){
        CloudantClient cloudant = createClient();
        if(cloudant!=null){
         db = cloudant.database(databaseName, true);
        }
    }
    
    public Database getDB(){
        return db;
    }

    private static CloudantClient createClient() {
        
        String url;

        if (System.getenv("VCAP_SERVICES") != null) {
            // When running in IBM Cloud, the VCAP_SERVICES env var will have the credentials for all bound/connected services
            // Parse the VCAP JSON structure looking for cloudant.
            JsonObject cloudantCredentials = VCAPHelper.getCloudCredentials("cloudant");
            if(cloudantCredentials == null){
                System.out.println("No cloudant database service bound to this application");
                return null;
            }
            url = cloudantCredentials.get("url").getAsString();
        } else if (System.getenv("CLOUDANT_URL") != null) {
            url = System.getenv("CLOUDANT_URL");
        } else {
            System.out.println("Running locally. Looking for credentials in cloudant.properties");
            url = VCAPHelper.getLocalProperties("cloudant.properties").getProperty("cloudant_url");
            if(url == null || url.length()==0){
                System.out.println("To use a database, set the Cloudant url in src/main/resources/cloudant.properties");
                return null;
            }
        }

        try {
            System.out.println("Connecting to Cloudant");
            CloudantClient client = ClientBuilder.url(new URL(url)).build();
            return client;
        } catch (Exception e) {
            System.out.println("Unable to connect to database");
            //e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Collection<Dna> getAll(){
        List<Dna> docs;
        try {
            docs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Dna.class);
        } catch (IOException e) {
            return null;
        }
        return docs;
    }

    @Override
    public Dna get(String id) {
        return db.find(Dna.class, id);
    }

    @Override
    public Dna persist(Dna td) {
        String id = db.save(td).getId();
        return db.find(Dna.class, id);
    }

    @Override
    public Dna update(String id, Dna newDna) {
        Dna Dna = db.find(Dna.class, id);
        Dna.setDna(newDna.getDna());
        db.update(Dna);
        return db.find(Dna.class, id);

    }

    @Override
    public void delete(String id) {
        Dna Dna = db.find(Dna.class, id);
        db.remove(id, Dna.get_rev());

    }

    @Override
    public int count() throws Exception {
        return getAll().size();
    }
}