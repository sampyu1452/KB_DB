package org.edu.member.vo;

import lombok.*;

// VO(Value Object) : 값 자체를 표현하고 의미를 갖는 객체
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

private int no;
private String id;
private String password;
private String name;
private String role;
private char deletedYn;

// 부서
    private int deptNo;
    private String deptName;

}
