package app.sec01;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Database {

    // MongoDB 클라이언트 인스턴스 (싱글톤)
    // 애플리케이션 전체에서 공유하는 단일 연결 객체
    static MongoClient client;

    // MongoDB 데이터베이스 인스턴스 (싱글톤)
    // practice_db 데이터베이스에 대한 접근 객체
    static MongoDatabase database;

    // 정적 초기화 블록
    // = 클래스가 로드될 때 한 번만 실행
    // - MongoDB 서버 연결 및 데이터베이스 객체 초기화
    static {

        // 데이터베이스 연결 및 database 참조 설정

        // MongoDB 연결 문자열 생성
        ConnectionString connectionString =
                new ConnectionString("mongodb://127.0.0.1:27017");

        // MongoClient 인스턴스 생성
        client = MongoClients.create(connectionString);

        // 데이터베이스 선택
        database = client.getDatabase("practice_db");
    }
        // MongoDB 연결을 종료하는 메서드
    public static void close() {
        // 데이터베이스 닫기
        if (client != null) {client.close();
        }
    }
    // MongoDB 데이터베이스 인스턴스를 반환하는 메서드
    public static MongoDatabase getDatabase() {
        // 데이터베이스 참조 얻기
        return database;
    }
    // 지정된 이름의 컬렉션을 반환하는 메서드
    public static MongoCollection<Document> getCollection(String colName) {
        // 전달된 컬렉션이름의 컬렉션 리턴하기
        MongoCollection<Document> collection = database.getCollection(colName);
        return collection;
    }
}