package store.v1;

import java.util.Properties;

public class DnaStoreFactory {

    private static DnaStore instance;
    static {
        // Only use MongoDB if credentials are available.
        if (VCAPHelper.getCloudCredentials("mongodb") == null &&
            (VCAPHelper.getLocalProperties("mongo.properties").getProperty("mongo_url") == null ||
             VCAPHelper.getLocalProperties("mongo.properties").getProperty("mongo_url").equals(""))) {
            CloudantVisitorStore cvif = new CloudantVisitorStore();
            if (cvif.getDB() != null) {
                instance = cvif;
            }
        }
        else {
            MongoDbVisitorStore cvif = new MongoDbVisitorStore();
            if (cvif.getDB() != null) {
                instance = cvif;
            }
        }
    }

    public static VisitorStore getInstance() {
        return instance;
    }
}