package com.microsoft.employeeservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.employeeservice.Entity.Employee;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeController employeeController;

    @Test
    public void testGetEmployeeById() throws Exception {
        Long employeeId = 1L;
        Mockito.when(employeeController.getEmployee(employeeId)).thenReturn(new Employee());

        mockMvc.perform(MockMvcRequestBuilders.get("/get/{id}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        Mockito.when(employeeController.createEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        existingEmployee.setIdEmployee(employeeId);

        Mockito.when(employeeController.getEmployee(employeeId)).thenReturn(existingEmployee);

        mockMvc.perform(MockMvcRequestBuilders.put("/update/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(existingEmployee)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/{id}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAllEmp() throws Exception {
        Mockito.when(employeeController.allEmp()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/list-all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindByDep() throws Exception {
        String department = "IT";
        Mockito.when(employeeController.findByDep(department)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/department/{dept}", department))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindByRole() throws Exception {
        String role = "Developer";
        Mockito.when(employeeController.findByRole(role)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/role/{role}", role))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindByActive() {
        Mockito.when(employeeController.findByActive()).thenReturn(Collections.emptyList());

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/all-active"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // fail tests/null tests/negative tests here... unit tests also involve testing for edge cases, boundary cases, domain, out of domain etc.
    //so far we have only tested for unit tests which work as expected. But we also need to test for invalidity.
    @Test
    public void testGetEmployeeByIdNot() {
        Long employeeId = 1L;
        Employee employee = employeeController.getEmployee(employeeId);
        Assert.assertEquals(employee.getActive(), false);
        Assert.assertNotEquals(employee.getDepartment(), "fail test");
    }
}