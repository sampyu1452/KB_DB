package org.edu.member.run;

import org.edu.member.common.JDBCUtil;
import org.edu.member.service.MemberService;

public class memberRun {
    public static void main(String[] args) {
        // conn 생성 확인
       // System.out.println(JDBCUtil.getConnection();

        MemberService service = new MemberService();
        service.displayMenu();
    }

}

