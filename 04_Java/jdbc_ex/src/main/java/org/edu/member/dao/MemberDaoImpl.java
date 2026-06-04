package org.edu.member.dao;

import org.edu.member.common.JDBCUtil;
import org.edu.member.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao {

    // JDBCUtil을 통해 Connection 객체를 가져오기
    private Connection conn = JDBCUtil.getConnection();

    // 회원 등록 (기존 코드 유지)
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
            if (result > 0) conn.commit();
            return result; // 성공한 행의 개수 반환
        }
    }

    @Override
    public int create(Member member) throws SQLException {
        return 0;
    }

    // 회원의 부서명 조회 (기존 코드 유지)
    @Override
    public Member getDeptName(int memberNo) throws SQLException {
        String sql = "select no, name, d.dept_no, dept_name " +
                "from members m left join departments d " +
                "on m.dept_no = d.dept_no where no = ?";

        Member member = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberNo);
            try (ResultSet rs = pstmt.executeQuery()) {
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

    // ================= 새로 추가된 구현 메소드 =================

    // 1. 회원 목록 전체 조회
    @Override
    public List<Member> getList() throws SQLException {
        String sql = "SELECT no, id, name, role, deleted_yn FROM members ORDER BY no DESC";
        List<Member> list = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Member member = new Member();
                member.setNo(rs.getInt("no"));
                member.setId(rs.getString("id"));
                member.setName(rs.getString("name"));
                member.setRole(rs.getString("role"));

                String delYnStr = rs.getString("deleted_yn");
                if (delYnStr != null && !delYnStr.isEmpty()) {
                    member.setDeletedYn(delYnStr.charAt(0));
                }

                list.add(member);
            }
        }
        return list;
    }

    // 2. 회원 번호가 일치하는 회원 한명 조회
    @Override
    public Member get(int memberNo) throws SQLException {
        String sql = "SELECT no, id, name, role, deleted_yn, dept_no FROM members WHERE no = ?";
        Member member = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    member = new Member();
                    member.setNo(rs.getInt("no"));
                    member.setId(rs.getString("id"));
                    member.setName(rs.getString("name"));
                    member.setRole(rs.getString("role"));

                    String delYnStr = rs.getString("deleted_yn");
                    if (delYnStr != null && !delYnStr.isEmpty()) {
                        member.setDeletedYn(delYnStr.charAt(0));
                    }
                    member.setDeptNo(rs.getInt("dept_no"));
                }
            }
        }
        return member;
    }

    // 3. 회원 번호가 일치하는 회원 삭제
    @Override
    public int delete(int memberNo) throws SQLException {
        String sql = "DELETE FROM members WHERE no = ?";
        int result = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberNo);

            result = pstmt.executeUpdate();

            if (result > 0) {
                conn.commit();
            }
        }
        return result;
    }
}