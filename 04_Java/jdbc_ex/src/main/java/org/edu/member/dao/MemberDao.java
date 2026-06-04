package org.edu.member.dao;

import org.edu.member.vo.Member;
import java.sql.SQLException;
import java.util.List; // List 임포트 필요

public interface MemberDao {

    // 회원 등록
    int create(Member member) throws SQLException;

    // 회원 정보 수정
    int update(Member member) throws SQLException;

    // 회원의 부서명 조회
    Member getDeptName(int memberNo) throws SQLException;


    // ================= [여기서부터 새로 추가할 메소드] =================

    // 1. 회원 목록 전체 조회
    List<Member> getList() throws SQLException;

    // 2. 회원 번호가 일치하는 회원 한명 조회
    Member get(int memberNo) throws SQLException;

    // 3. 회원 번호가 일치하는 회원 삭제
    int delete(int memberNo) throws SQLException;
}