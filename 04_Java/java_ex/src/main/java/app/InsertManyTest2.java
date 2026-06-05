package app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertManyTest2 {
    public static void main(String[] args) {
        //Database클래스를 쓰는 순간 static{ }이 실행이 됨.db서버연결, db연결이 준비가 됨.
        MongoCollection<Document> collection = Database.getCollection("todo");
        System.out.println("3. todo 컬렉션 연결 성공.");


        List<Document> insertList = new ArrayList<>();

        for(int i = 10; i < 21; i++) {
            Document document = new Document();
            document.append("name", "user_" + i);
            document.append("age", i);
            document.append("created", new Date() );
            insertList.add(document);
        }

        System.out.println("삽입할 document list의 개수 " + insertList.size());

        InsertManyResult result  = collection.insertMany(insertList);
        System.out.println("4. document insert 몽고db로 보냄.");
        System.out.println("5. insert후에 결과 " + result.getInsertedIds());
        Database.close();
    }
}