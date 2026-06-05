package app.sec02;

import app.sec01.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertManyTest {
    public static void main(String[] args) {

        // study 컬렉션 가져오기
        MongoCollection<Document> collection =
                Database.getCollection("study");

        // 문서 리스트 객체 생성
        List<Document> insertList = new ArrayList<>();

        // document1
        Document document1 = new Document()
                .append("title", "Dune2 영화보기")
                .append("desc", "이번 주말 IMAX로 Dune2 영화보기")
                .append("done", false);

        Document document2 = new Document()
                .append("title", "Java MongoDB 연동")
                .append("desc", "Java로 MongoDB 연동 프로그래밍 연습하기")
                .append("done", true);

        // 문서 리스트 추가
        insertList.add(document1);
        insertList.add(document2);

        // 한번에 insert


        System.out.println("InsertManyResult : ");
    }
}
