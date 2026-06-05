package app.sec02;

import app.sec01.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;


public class InsertOneTest {
    public static void main(String[] args) {

        // Database 유틸리티 클래스를 이용해서 study 컬렉션(테이블) 연결 객체 알아보기
        MongoCollection<Document> collection =
                Database.getCollection("study");
        // 문서(행)을 정의하는 객체 생성(BSON)
        Document document = new Document();

        // 문서 객체에 필드(열) 추가
        document.append("title", "MongoDB");
        document.append("desc", "MongoDB 공부하기");
        document.append("done", "false");

        // 컬렉션 문서 1개 삽입 후
        InsertOneResult result = collection.insertOne(document);

        // 생성된 문서의 _id 필드값 얻어오기
        System.out.println("InsertOneResult : " + result.getInsertedId());

        // 데이터베이스 연결 종료 및 리소스 정리
        Database.close();


    }

}
