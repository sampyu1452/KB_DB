package org.scoula.jdbc_ex;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {

    @Test
    public void testConnection() throws ClassNotFoundException, SQLException {
  Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("1. jdbc드라이버 세팅(로딩) 성공");
        //2. db연결해보자.
        // db연결시 필요한 데이터 3가지 : utrl(ip + port + db명), username, pw
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_ex";
        String id = "scoula";
        String password = "1234";
        Connection con = DriverManager.getConnection(url,id,password);
        System.out.println("2. db연결 성공>> " + con);

        //자원해체 해주어야함.
        con.close();
    }
}
