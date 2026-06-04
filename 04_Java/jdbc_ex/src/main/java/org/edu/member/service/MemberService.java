package org.edu.member.service;

import org.edu.member.dao.MemberDao;
import org.edu.member.dao.MemberDaoImpl;
import org.edu.member.vo.Member;

import java.sql.SQLException;
import java.util.Scanner;

public class MemberService {
    private Scanner sc = new Scanner(System.in);

// 수업
// private MemberDao dao = new MemberDaoImpl

    // 숙제
    private MemberDao dao = new MemberDaoImpl();

    public void displayMenu() {

        int menu = 0; // 메뉴 선택용 변수

        do {
            try {
                System.out.println("[메인 메뉴]");
                System.out.println("1. 회원 등록");
                System.out.println("2. 회원 목록 조회");
                System.out.println("3. 회원 정보 조회");
                System.out.println("4. 회원 수정");
                System.out.println("5. 회원 삭제");
                System.out.println("6. 회원 부서명 조회");
                System.out.println("0. 종료");
                System.out.print("메뉴 선택 >> ");

                menu = sc.nextInt();
                sc.nextLine(); // 입력 버퍼 개행문자 제거
                System.out.println(); // 줄바꿈

                switch (menu) {
                    case 1:
                        create();
                        break;
                    case 2:
                        // getList();
                        break;
                    case 3:
                        // get();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        // delete();
                        break;
                    case 6:
                        getDeptName();
                        break;
                    case 0:
                        System.out.println("[프로그램 종료]");
                        break;
                    default:
                        System.out.println("잘못 입력하셨습니다. 메뉴를 다시 선택해주세요.");
                }

            } catch (Exception e) {
                sc.nextLine(); // 잘못된 입력 제거
                e.printStackTrace();
            }
        } while (menu != 0);
    }

    // 회원 등록
    private void create() throws SQLException {
        System.out.println("=== 회원 등록 ===");

        // 아이디, 비밀번호, 이름, 권한 입력받아 변수에 저장
        System.out.print("아이디 : ");
        String id = sc.next();

        System.out.print("비밀번호 : ");
        String password = sc.next();

        System.out.print("이름 : ");
        String name = sc.next();

        System.out.print("권한 : ");
        String role = sc.next();

        // Member 객체 생성 후 전달
        Member member = new Member();
        member.setId(id);
        member.setPassword(password);
        member.setName(name);
        member.setRole(role);

        int result = dao.create(member);

        // 회원 등록 성공 시 : "000님의 가입을 환영합니다."
        //         실패 시 : "회원 등록 실패 ㅠㅠ"
        if (result > 0) {
            System.out.println(name + "000님의 가입을 환영합니다.");

        } else {
            System.out.println("회원 등록 실패 ㅠㅠ");
        }

    }

    // 회원 정보 삭제
    private void update() throws SQLException {
        System.out.println("회원 정보 수정");
        // 회원번호 입력받아 일치하는 회원의 이름, 권한 수정
        System.out.print("아이디를 입력하세요: ");
        String memberId = sc.next();
        System.out.println();
        System.out.print(" 수정할 이름: ");
        String newName = sc.next();
        System.out.println();
        System.out.print(" 수정할 권한: ");
        String newRole = sc.next();
        System.out.println();

        Member member = new Member();
        member.setId(memberId);
        member.setName(newName);
        member.setRole(newRole);

        int result = dao.update(member);
        if (result == 1) System.out.println("수정된 이름: " + newName + " 수정된 권한: " + newRole);
        else {
            System.out.println("수정 등록 실패");
        }
    }


    // getList(): 회원 목록 전체 조회
    // 2. 회원 목록 전체 조회
    private void getList() throws SQLException {
        System.out.println("=== 회원 목록 전체 조회 ===");

        // Dao 인터페이스에 추가한 getList() 호출
        java.util.List<Member> list = dao.getList();

        if (list == null || list.isEmpty()) {
            System.out.println("등록된 회원 정보가 존재하지 않습니다.");
        } else {
            System.out.println("--------------------------------------------------");
            System.out.printf("%-5s | %-10s | %-10s | %-5s\n", "번호", "아이디", "이름", "권한");
            System.out.println("--------------------------------------------------");
            for (Member member : list) {
                System.out.printf("%-5d | %-10s | %-10s | %-5s\n",
                        member.getNo(), member.getId(), member.getName(), member.getRole());
            }
            System.out.println("--------------------------------------------------");
            System.out.println("총 " + list.size() + "명의 회원이 조회되었습니다.");
        }
    }

    // 3. 회원 번호가 일치하는 회원 한명 조회
    private void get() throws SQLException {
        System.out.println("=== 회원 정보 단건 조회 ===");
        System.out.print("조회할 회원 번호 입력 : ");
        int memberNo = sc.nextInt();
        sc.nextLine(); // 입력 버퍼 개행문자 제거

        // Dao 인터페이스에 추가한 get(int memberNo) 호출
        Member member = dao.get(memberNo);

        if (member == null) {
            System.out.println(memberNo + "번 회원 정보가 존재하지 않습니다.");
        } else {
            System.out.println("\n[조회 결과]");
            System.out.println("회원 번호 : " + member.getNo());
            System.out.println("아 이 디  : " + member.getId());
            System.out.println("이    름  : " + member.getName());
            System.out.println("권    한  : " + member.getRole());
            System.out.println("삭제 여부 : " + member.getDeletedYn());
        }
    }

    // 5. 회원 번호가 일치하는 회원 삭제
    private void delete() throws SQLException {
        System.out.println("=== 회원 정보 삭제 ===");
        System.out.print("삭제할 회원 번호 입력 : ");
        int memberNo = sc.nextInt();
        sc.nextLine(); // 입력 버퍼 개행문자 제거

        System.out.print("정말로 삭제하시겠습니까? (Y/N) : ");
        String confirm = sc.next();

        if (confirm.equalsIgnoreCase("Y")) {
            // Dao 인터페이스에 추가한 delete(int memberNo) 호출
            int result = dao.delete(memberNo);

            if (result > 0) {
                System.out.println(memberNo + "번 회원의 정보가 정상적으로 삭제되었습니다.");
            } else {
                System.out.println("삭제 실패 : 일치하는 회원 번호가 없거나 오류가 발생했습니다.");
            }
        } else {
            System.out.println("삭제가 취소되었습니다.");
        }
    }
    // get() : 회원 번호가 일치하는 회원 한명 조회
    // delete() :: 회원 번호가 일치하는 회원 삭제

    // 회원 번호가 일치하는 회원의 번호, 이름, 부서코드, 부서명 조회
    private void getDeptName() throws SQLException {
        System.out.println("=== 회원의 부서명 조회 === ");

        System.out.println("검색할 회원 번호 : ");
        int memberNo = sc.nextInt();

        Member member = dao.getDeptName(memberNo);

        if (member == null) {
            System.out.println("일치하는 회원 정보는 없습니다");
        } else {
            System.out.println("조회 결과");
        }


    }
}




