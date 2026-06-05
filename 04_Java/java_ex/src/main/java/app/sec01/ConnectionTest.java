package app.sec01;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionTest {
        public static void main(String[] args) {
            String uri = "mogodb://127.0.0.1.27017";
            String db = "pratice_db";
            try (MongoClient client = MongoClients.create(uri)){
                MongoDatabase database = client.getDatabase(db);
            } catch(Exception e) {
                e.printStackTrace();
            }
}

}