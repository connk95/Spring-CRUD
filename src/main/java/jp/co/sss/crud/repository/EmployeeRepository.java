package jp.co.sss.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.co.sss.crud.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findAll();
	
	@Query("SELECT e FROM Employee e JOIN FETCH e.department ORDER BY e.empId ASC")
	List<Employee> findAllWithDepartment();
	
	@Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.empName LIKE %:empName%")
	List<Employee> findByEmpName(String empName);
	
	@Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.department.deptId = :deptId")
	List<Employee> findAllByDeptId(Integer deptId);
}
