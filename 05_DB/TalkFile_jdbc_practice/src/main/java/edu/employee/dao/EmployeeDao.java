package edu.employee.dao;

import edu.employee.vo.EmployeeVO; // Vo에서 VO로 변경 완료

import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    // 1. 마케팅부 직원 정보 조회
    List<EmployeeVO> getDepartmentEmployees(String deptTitle);

    // 2. 부서·직급별 평균 급여 조회
    List<Map<String, Object>> getDepartmentAvgSalary();

    // 3. 재직 중인 직원 목록 조회
    List<EmployeeVO> getWorkingEmployees();

    // 4. 부서 급여 10% 인상
    int increaseSalary(String deptCode);

    // 5. 휴대폰 번호 없는 직원 조회
    List<EmployeeVO> getEmployeesWithoutPhone();

}