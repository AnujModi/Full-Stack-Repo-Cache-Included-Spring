package com.microsoft.employeeservice.Controller;

import com.microsoft.employeeservice.Entity.Employee;
import com.microsoft.employeeservice.Repo.EmployeeRepository;
import com.microsoft.employeeservice.Services.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")

/*To create as and how needed...*/

public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/get/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeRepository.findByIdEmployee(id);
    }

    //rather than using repo, i used service to showcase that if multiple repo's are involved, we can also use business logic from service itself...
    @PostMapping("/save")
    @Transactional
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/update/{id}")
    @Transactional
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee existingEmployee = employeeRepository.findByIdEmployee(id);
        if(existingEmployee != null){
            existingEmployee.setName(employee.getName());
            existingEmployee.setActive(employee.getActive());
            existingEmployee.setRole(employee.getRole());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setReportsTo(employee.getReportsTo());
        }
        else{
            return employeeService.createEmployee(employee);
        }
        return employeeRepository.save(existingEmployee);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteByIdEmployee(id);
        return;
    }

    //couple other findAll and other get queries...
    @GetMapping("list-all")
    public List<Employee> allEmp() {
        return employeeRepository.findAll();
    }
    @GetMapping("/department/{dept}")
    public List<Employee> findByDep(@PathVariable String dept) {
        return employeeRepository.findByDepartment(dept);
    }
    @GetMapping("/role/{role}")
    public List<Employee> findByRole(@PathVariable String role) {
        return employeeRepository.findByRole(role);
    }
    @GetMapping("/all-active")
    public List<Employee> findByActive() {
        return employeeRepository.findByActiveTrue();
    }
}