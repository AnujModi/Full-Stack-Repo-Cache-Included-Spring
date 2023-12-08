package com.microsoft.employeeservice.Services;

import com.microsoft.employeeservice.Entity.Employee;
import com.microsoft.employeeservice.Repo.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @PersistenceContext
    private EntityManager entityManager;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    @Transactional
    @Modifying
    public Employee createEmployee(Employee employee){
        try{
            String sql = "INSERT INTO `employee-microsoft`.`employee` (id_employee, `Name`, Department, reports_to, Role, Salary, Active)" +
                    "VALUES (:id, :name, :dept, :reports_to, :role, :salary, :active)";
            Query query = entityManager.createNativeQuery(sql)
                    .setParameter("id", employee.getIdEmployee())
                    .setParameter("name", employee.getName())
                    .setParameter("dept", employee.getDepartment())
                    .setParameter("reports_to", employee.getReportsTo())
                    .setParameter("role", employee.getRole())
                    .setParameter("salary", employee.getSalary())
                    .setParameter("active", employee.getActive());
            query.executeUpdate();
            return employee;
        }
        catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Can add more service methods as needed... i think for this task, these services should be fine...
}