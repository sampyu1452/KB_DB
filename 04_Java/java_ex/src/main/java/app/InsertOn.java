package app;

public class InsertOn {
    public static void main(String[] args) {
        //Database클래스를 쓰는 순간 static{}이 실행이 됨.db서버연결, db연결이 준비가 됨.
    Database.getCollection("todo");
    }

}
