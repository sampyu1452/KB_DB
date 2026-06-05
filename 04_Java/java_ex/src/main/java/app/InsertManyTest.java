package app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class InsertManyTest {
    public static void main(String[] args) {
        //Database클래스를 쓰는 순간 static{ }이 실행이 됨.db서버연결, db연결이 준비가 됨.
        MongoCollection<Document> collection = Database.getCollection("todo");
        System.out.println("3. todo 컬렉션 연결 성공.");


        List<Document> insertList = new ArrayList<>();

        Document document1 = new Document();
        Document document2 = new Document();
        document1.append("title", "Dune2 영화보기");
        document1.append("desc", "이번 주말 IMAX로 Dune2 영화보기");
        document1.append("done", false);
        document2.append("title", "Java MongoDB 연동");
        document2.append("desc", "Java로 MongoDB 연동 프로그래밍 연습하기");
        document2.append("done", false);

        insertList.add(document1);
        insertList.add(document2);

        System.out.println("삽입할 document list의 개수 " + insertList.size());

        InsertManyResult result  = collection.insertMany(insertList);
        System.out.println("4. document insert 몽고db로 보냄.");
        System.out.println("5. insert후에 결과 " + result.getInsertedIds());
        Database.close();
    }
}
