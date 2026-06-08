package edu.employee.service;

import edu.employee.dao.EmployeeDao;
import edu.employee.dao.EmployeeDaoImpl;
import edu.employee.vo.EmployeeVO;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeeService {

    private Scanner sc = new Scanner(System.in);
    private EmployeeDao dao = new EmployeeDaoImpl();

    public void displayMenu() {
        int menu = 0;

        do {
            try {
                System.out.println("========= [직원 관리 시스템] =========");
                System.out.println("1. 마케팅부 직원 정보 조회");
                System.out.println("2. 부서·직급별 평균 급여 조회");
                System.out.println("3. 재직 중인 직원 목록 조회");
                System.out.println("4. 부서 급여 10% 인상");
                System.out.println("5. 휴대폰 번호 없는 직원 조회");
                System.out.println("0. 프로그램 종료");
                System.out.println("=================================");
                System.out.print("메뉴 선택 >> ");

                menu = sc.nextInt();
                sc.nextLine();
                System.out.println();

                switch (menu) {
                    case 1: getDepartmentEmployees(); break;
                    case 2: getDepartmentAvgSalary(); break;
                    case 3: getWorkingEmployees(); break;
                    case 4: increaseSalary(); break;
                    case 5: getEmployeesWithoutPhone(); break;
                    case 0: System.out.println("[프로그램을 종료합니다.]"); break;
                    default: System.out.println("❌ 잘못 입력하셨습니다. 메뉴를 다시 선택해주세요.\n");
                }
            } catch (Exception e) {
                System.out.println("❌ 입력 오류가 발생했습니다. 다시 시도해주세요.\n");
                sc.nextLine();
            }
        } while (menu != 0);
    }

    /**
     * 1. 부서명을 입력받아 해당 부서에 근무하는 직원 정보를 조회
     */
    private void getDepartmentEmployees() {
        System.out.print("조회할 부서명 입력 (예: 마케팅부) >> ");
        String deptTitle = sc.nextLine();

        List<EmployeeVO> list = dao.getDepartmentEmployees(deptTitle);

        System.out.println("\n[ " + deptTitle + " 직원 정보 조회 결과 ]");
        if (list.isEmpty()) {
            System.out.println("조회된 직원 정보가 없습니다.");
        } else {
            for (EmployeeVO emp : list) {
                System.out.printf("사원명: %s | 부서명: %s | 직급명: %s | 보너스율: %s | 퇴직여부: %s\n",
                        emp.getEmpName(), emp.getDeptTitle(), emp.getJobName(), emp.getBonus(), emp.getEntYn());
            }
        }
        System.out.println();
    }

    /**
     * 2. 부서별, 직급별 평균 급여 정보를 조회
     */
    private void getDepartmentAvgSalary() {
        System.out.println("[ 부서·직급별 평균 급여 조회 (300만원 이상) ]");

        List<Map<String, Object>> list = dao.getDepartmentAvgSalary();

        if (list.isEmpty()) {
            System.out.println("조건에 맞는 부서·직급별 평균 급여 정보가 없습니다.");
        } else {
            for (Map<String, Object> map : list) {
                System.out.printf("부서명: %-10s | 직급명: %s | 사원수: %d명 | 평균급여: %,d원\n",
                        map.get("deptTitle"), map.get("jobName"), map.get("empCount"), map.get("avgSal"));
            }
        }
        System.out.println();
    }

    /**
     * 3. 현재 재직 중인 직원 목록을 조회 (상위 10명)
     */
    private void getWorkingEmployees() {
        System.out.println("[ 재직 중인 직원 목록 조회 (직급순 상위 10명) ]");

        List<EmployeeVO> list = dao.getWorkingEmployees();

        if (list.isEmpty()) {
            System.out.println("현재 재직 중인 직원이 없습니다.");
        } else {
            int index = 1;
            for (EmployeeVO emp : list) {
                System.out.printf("%2d. 부서명: %-10s | 직급명: %s | 사원명: %s | 급여: %,d원\n",
                        index++, emp.getDeptTitle(), emp.getJobName(), emp.getEmpName(), emp.getSalary());
            }
        }
        System.out.println();
    }

    /**
     * 4. 특정 부서 직원의 급여를 10% 인상한다.
     */
    private void increaseSalary() {
        System.out.print("급여를 인상할 부서코드 입력 (예: D5) >> ");
        String deptCode = sc.nextLine();

        int result = dao.increaseSalary(deptCode);

        if (result > 0) {
            System.out.printf("🎉 성공: %d명의 급여가 10%% 인상되었습니다.\n", result);
        } else {
            System.out.println("❌ 실패: 해당 부서코드가 존재하지 않거나 대상 직원이 없습니다.");
        }
        System.out.println();
    }

    /**
     * 5. 휴대폰 번호가 없는 직원 정보를 조회한다.
     */
    private void getEmployeesWithoutPhone() {
        System.out.println("[ 휴대폰 번호 없는 직원 정보 조회 ]");

        List<EmployeeVO> list = dao.getEmployeesWithoutPhone();

        if (list.isEmpty()) {
            System.out.println("휴대폰 번호가 없는 직원이 없습니다. (모두 등록됨)");
        } else {
            for (EmployeeVO emp : list) {
                System.out.printf("사원명: %s | 휴대폰번호: %s | 부서명: %s\n",
                        emp.getEmpName(), emp.getPhone(), emp.getDeptTitle());
            }
        }
        System.out.println();
    }
}