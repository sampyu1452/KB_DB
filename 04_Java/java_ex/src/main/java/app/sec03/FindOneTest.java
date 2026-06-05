package app.sec03;

import app.sec01.Database;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class FindOneTest {

    public static void main(String[] args) {

        MongoCollection<Document> todo =
                Database.getCollection("study");

        // 존재하는 _id
        String id = "6a2269d750a48040d815b156";

        // Filters.eq()
        Bson query = eq("_id", new ObjectId(id));

        // 조건을 만족하는 결과 중 1행(문서 1개만) 조회
        // collection.find() == db.study.find()
        Document doc = todo.find(query).first();

        System.out.println(doc);

        Database.close();
    }
}