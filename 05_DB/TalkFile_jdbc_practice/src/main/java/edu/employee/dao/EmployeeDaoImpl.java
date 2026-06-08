package edu.employee.dao;

import edu.common.JDBCUtil; // 👈 본인의 패키지 경로에 맞게 수정하세요!
import edu.employee.vo.EmployeeVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {

    /**
     * 1. 부서명을 입력받아 해당 부서에 근무하는 직원 정보를 조회
     */
    @Override
    public List<EmployeeVO> getDepartmentEmployees(String deptTitle) {
        List<EmployeeVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 테이블명을 소문자(employee, department, job)로 수정 완료
        String sql = "SELECT EMP_NAME, DEPT_TITLE, JOB_NAME, " +
                "       IFNULL(BONUS, '보너스 없음') AS BONUS, " +
                "       IF(ENT_YN = 'N', '재직', '퇴사') AS ENT_YN " +
                "FROM employee " +
                "JOIN department ON (DEPT_CODE = DEPT_ID) " +
                "JOIN job USING (JOB_CODE) " +
                "WHERE DEPT_TITLE = ? " +
                "ORDER BY BONUS DESC";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deptTitle);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                EmployeeVO emp = new EmployeeVO();
                emp.setEmpName(rs.getString("EMP_NAME"));
                emp.setDeptTitle(rs.getString("DEPT_TITLE"));
                emp.setJobName(rs.getString("JOB_NAME"));
                emp.setBonus(rs.getString("BONUS"));
                emp.setEntYn(rs.getString("ENT_YN"));

                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }
        return list;
    }

    /**
     * 2. 부서별, 직급별 평균 급여 정보를 조회
     */
    @Override
    public List<Map<String, Object>> getDepartmentAvgSalary() {
        List<Map<String, Object>> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT DEPT_TITLE, JOB_NAME, COUNT(*) AS EMP_COUNT, ROUND(AVG(SALARY)) AS AVG_SAL " +
                "FROM employee " +
                "JOIN department ON (DEPT_CODE = DEPT_ID) " +
                "JOIN job USING (JOB_CODE) " +
                "WHERE ENT_YN = 'N' " +
                "GROUP BY DEPT_TITLE, JOB_NAME " +
                "HAVING AVG_SAL >= 3000000 " +
                "ORDER BY AVG_SAL DESC";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("deptTitle", rs.getString("DEPT_TITLE"));
                map.put("jobName", rs.getString("JOB_NAME"));
                map.put("empCount", rs.getInt("EMP_COUNT"));
                map.put("avgSal", rs.getInt("AVG_SAL"));

                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }
        return list;
    }

    /**
     * 3. 현재 재직 중인 직원 목록을 조회 (상위 10명)
     */
    @Override
    public List<EmployeeVO> getWorkingEmployees() {
        List<EmployeeVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT DEPT_TITLE, JOB_NAME, EMP_NAME, SALARY " +
                "FROM employee " +
                "LEFT JOIN department ON (DEPT_CODE = DEPT_ID) " +
                "JOIN job USING (JOB_CODE) " +
                "WHERE ENT_YN = 'N' " +
                "ORDER BY JOB_NAME ASC " +
                "LIMIT 10";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EmployeeVO emp = new EmployeeVO();
                emp.setDeptTitle(rs.getString("DEPT_TITLE"));
                emp.setJobName(rs.getString("JOB_NAME"));
                emp.setEmpName(rs.getString("EMP_NAME"));
                emp.setSalary(rs.getInt("SALARY"));

                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }
        return list;
    }

    /**
     * 4. 특정 부서 직원의 급여를 10% 인상한다.
     */
    @Override
    public int increaseSalary(String deptCode) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        String sql = "UPDATE employee SET SALARY = SALARY * 1.1 WHERE DEPT_CODE = ?";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deptCode);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(pstmt, conn);
        }
        return result;
    }

    /**
     * 5. 휴대폰 번호가 없는 직원 정보를 조회한다.
     */
    @Override
    public List<EmployeeVO> getEmployeesWithoutPhone() {
        List<EmployeeVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT EMP_NAME, IFNULL(PHONE, '없음') AS PHONE, DEPT_TITLE " +
                "FROM employee " +
                "LEFT JOIN department ON (DEPT_CODE = DEPT_ID) " +
                "WHERE PHONE IS NULL " +
                "ORDER BY EMP_NAME DESC";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EmployeeVO emp = new EmployeeVO();
                emp.setEmpName(rs.getString("EMP_NAME"));
                emp.setPhone(rs.getString("PHONE"));
                emp.setDeptTitle(rs.getString("DEPT_TITLE"));

                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }
        return list;
    }
}