package app.sec03;

import app.sec01.Database;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Iterator;

public class FindTest {
    public static void main(String[] args) {

        MongoCollection<Document> todo =
                Database.getCollection("study");

        // 반복자 형태로 반환
        Iterator<Document> it = todo.find().iterator();

        while (it.hasNext()) { // 다음 행이 있으면 true
            System.out.println("FindResultRow: " + it.next());
        }

        Database.close();
    }
}
