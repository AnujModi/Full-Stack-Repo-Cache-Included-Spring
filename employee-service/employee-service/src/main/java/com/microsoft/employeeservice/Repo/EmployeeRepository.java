package com.microsoft.employeeservice.Repo;

import com.microsoft.employeeservice.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Spring Data JPA provides common CRUD operations out of the box.
    // You can also define custom query methods here if needed.
}