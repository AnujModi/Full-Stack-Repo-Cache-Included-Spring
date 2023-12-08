package com.microsoft.employeeservice.Repo;

import com.microsoft.employeeservice.Entity.Employee;
import com.microsoft.employeeservice.Services.EmployeeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByIdEmployee(Long idEmployee);
    List<Employee> findAll();
    Employee save(Employee employee);
    void deleteByIdEmployee(Long idEmployee);
    List<Employee> findByDepartment(String department);
    List<Employee> findByRole(String role);
    List<Employee> findByActiveTrue();

}