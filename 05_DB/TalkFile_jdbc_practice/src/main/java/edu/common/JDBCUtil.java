package edu.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {

    // 1. DB 접속 정보 (자주 바뀌지 않으므로 상수로 관리)
    private static final String URL = "jdbc:mysql://localhost:3306/employeedb?serverTimezone=Asia/Seoul&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // 본인 비번 입력

    // 2. DB 연결 객체를 가져오는 메서드
    public static Connection getConnection() {
        try {
            // 드라이버 로딩 (생략 가능하지만 안정성을 위해 포함)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("❌ JDBC Util: DB 연결 실패!");
            e.printStackTrace();
            return null;
        }
    }

    // 3. 사용한 자원을 닫아주는 메서드 (Insert, Update, Delete용)
    public static void close(PreparedStatement pstmt, Connection conn) {
        try {
            if (pstmt != null) pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. 사용한 자원을 닫아주는 메서드 - 오버로딩 (Select용: ResultSet이 추가됨)
    public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(pstmt, conn); // 위의 3번 메서드를 재사용해서 중복 제거
    }
}