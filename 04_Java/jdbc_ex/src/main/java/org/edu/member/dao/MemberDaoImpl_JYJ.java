package org.edu.member.dao;

import org.edu.member.common.JDBCUtil;
import org.edu.member.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDaoImpl_JYJ implements MemberDao {

    // JDBCUtil을 통해 Connection 객체를 가져오기
    private Connection conn = JDBCUtil.getConnection();
    ;

    // 회원 등록
    @Override
    public int create (Member member) throws SQLException {
        // Statement를 사용하는 경우 sql문
        /*
        String sql = "INSERT INTO members VALUES (DEFAULT, "
                + member.getMemberId() + ", "
                + member.getMemberPw() +", "
                + member.getMemberName()+ ", "
                + member.getMemberRole()+", 'N');";
         */

        // PreparedStatement
        // - Statement의 자식으로 좀 더 향상된 기능을 제공
        // - ?(위치 홀더)를 이용하여 SQL에 작성되어지는 리터럴을 동적으로 제어
        // -> 오타 위험 감소, 가독성 상승

        // sql문 작성 시 세미콜론(;)은 안 쓰는 것이 관례
        String sql = "insert into members (id, password, name, role) values (?, ?, ?, ?)";

        // try-with-resources문을 사용하여 작업이 끝나면 close()가 자동 호출됨
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getRole());

            // select : excuteQuery();  -> ResultSet 반환
            // DML    : excuteUpdate(); -> 성공한 행의 개수 반환
            int result = pstmt.executeUpdate();

            if (result != 0) conn.commit();

            return result; // 성공한 행의 개수 반환
        }
    }
    // 회원 부서명 조회
    @Override
    public Member getDepName(int memberNo) {
        String sql = "select no, d.dept_no, dept_name " +
                "from members m " +
                "left join departments d on m.dept_no = d.dept_no" +
                "where no = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, memberNo);

            try(ResultSet re = pstmt.executeQuery()){
                if(rs.next()){

                    Member mem = new Member();

                    mem.setNo(rs.getInt("NO"));
                    mem.setName(rs.getString("NAME"));
                    mem.setDeptNo(rs.getInt("DEPT_NO"));
                    mem.setDeptName(rs.getString("DEPT_NAME"));
                }
            }

        }

        return mem;
    }

    // 회원 정보 수정
    @Override
    public int update(Member member) throws SQLException {
        String sql = "update members set name = ?, role = ? where id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getRole());
            pstmt.setString(3, member.getId());

            int result = pstmt.executeUpdate();

            if (result != 0) conn.commit();

            return result;


        }
    }
}
