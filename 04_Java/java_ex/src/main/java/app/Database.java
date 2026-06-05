package app;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Database {
    static MongoClient client;
    static MongoDatabase database;

    static {
        //다른 static메서드 호출하기 전에 미리 실행하고 싶은 부분을 넣어주세요.
        ConnectionString connectionString = new ConnectionString("mongodb://127.0.0.1:27017");
        client = MongoClients.create(connectionString);
        database = client.getDatabase("todo_db");
    }

    public static MongoDatabase getDatabase(){
        return database;
    }

    public static void close(){
        client.close(); //몽고db서버와 연결이 닫힘.(끊김)
        //close이후에는 crud작업 불가.
    }

    public static MongoCollection<Document> getCollection(String colName){
        MongoCollection<Document> collection = database.getCollection(colName);
        return collection;
    }
}