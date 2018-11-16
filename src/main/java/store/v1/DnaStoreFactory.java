package store.v1;

public class DnaStoreFactory {

    private static DnaStore instance;
    static {
            CloudantDnaStore cvif = new CloudantDnaStore();
            if (cvif.getDB() != null) {
                instance = cvif;
            }
        
    }

    public static DnaStore getInstance() {
        return instance;
    }
}