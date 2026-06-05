package app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionTest {
    public static void main(String[] args) {
        String url = "mongodb://127.0.0.1:27017";
        String db = "todo_db";

        //몽고db와 java연동
        //몽고db연동할 수 있는 자바 라이브러리가 필요.
        //몽고db driver필요.!!!
        //1. 몽고db 서버연결 --> 외부자원(네트워크)연결은 반드시 예외처리
        //try-catch-resources(close자동으로 해줌)
        //2. 몽고db 서버의 db연결
        //3. 몽고db연결된 db의 collection연결
        //4. collection에 document(json) crud
        //5. 몽고db close

        try(MongoClient mongoClient = MongoClients.create(url)) {
            System.out.println("1. 몽고db연결 성공" + mongoClient);
            MongoDatabase database = mongoClient.getDatabase(db);
            System.out.println("2.몽고db연결(todo_db) 성공." + database);
        } catch (Exception e) {
            System.out.println("몽고DB연결시 에러 발생 : " + e.getMessage());
        }
    }
}


