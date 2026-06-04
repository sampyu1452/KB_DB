package org.edu.member.dao;

import org.edu.member.common.JDBCUtil;
import org.edu.member.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDaoImpl implements MemberDao {

    // JDBCUtil을 통해 Connection 객체를 가져오기
    private Connection conn = JDBCUtil.getConnection();
    ;

    // 회원 등록
    @Override
    public int update(Member member) throws SQLException {
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
        // -?(위치 홀더)를 이용하여 SQL에 작성되어지는 리터럴을 동적으로 제어
        // -> 오타 위험 감소, 가독성 상승

        // sql문 작성 시 세미콜론(;)은 안쓰는 것이 관례
        String sql = "INSERT INTO members VALUES (DEFAULT, ? , ? , ? , ? , 'N');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getRole());

            // SELECT : executeQuery(); -> ResultSet 반환
            // DML : executeUpdate();  -> 성공한 행의 개수 반환
            int result = pstmt.executeUpdate();

            // 성공 시 커밋
            if (result > 1) conn.commit();
            return result; // 성공한 행의 개수 반환
        }


    }

    @Override
    public int create(Member member) throws SQLException {
        return 0;
    }

    @Override
    public Member getDeptName(int memberNo) throws SQLException{
        String sql = "select no, name, d.dept_no, dept_name " +
                "from members m left join departments d " +
                "on m.dept_no = d.dept_no where no = ?";

        Member member = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, memberNo);
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    // no == pk == 조회 성공 시 1행만 존재
                    member = new Member();
                    member.setNo(rs.getInt("no"));
                    member.setName(rs.getString("name"));
                    member.setDeptNo(rs.getInt("dept_no"));
                    member.setDeptName(rs.getString("dept_name"));

                }
            }
        }
        return member;


    }

    @Override
    public List<Member> getList() throws SQLException {
        return List.of();
    }

    @Override
    public Member get(int memberNo) throws SQLException {
        return null;
    }

    @Override
    public int delete(int memberNo) throws SQLException {
        return 0;
    }
}
