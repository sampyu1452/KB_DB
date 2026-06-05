package app;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FindTest {
    public static void main(String[] args) {
        //Database클래스를 쓰는 순간 static{ }이 실행이 됨.db서버연결, db연결이 준비가 됨.
        MongoCollection<Document> collection = Database.getCollection("todo");
        System.out.println("3. todo 컬렉션 연결 성공.");

        //전체 검색해보자.
       // FindIterable<Document> doc = collection.find();
        // 조건 검색 --->json 조건을 만들자.

        FindIterable<Document> doc = collection.find();
        System.out.println(doc);

        //FindIterable 인덱스가 없어서 반복해서 Document를 꺼내줄 수 없음.
        //외부에 반복해서 꺼내주는 반복자를 설정해야함. Iterator
        //있는지 체크(hasNext())하고 있으면 꺼내줌(next()).
        Iterator<Document> iterator = doc.iterator();
        while(iterator.hasNext()) {
//            System.out.println(iterator.next());
            Document document = (Document) iterator.next();
            System.out.println(document.get("title"));
        }
        Database.close();
    }
}
