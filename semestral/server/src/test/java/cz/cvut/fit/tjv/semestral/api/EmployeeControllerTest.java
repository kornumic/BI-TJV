package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.EmployeeService;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    Job jobValid = new Job(1L, "jobValid", 3L, 50L, false);
    Job jobInvalid = new Job(2L, "jobInvalid", 3L, 161L, false);

    Employee employeeToAdd = new Employee(null, "employee1", 500L, null, "address1", 3L, jobValid);
    Employee employeeNotToAdd = new Employee(null , "employee2", 400L, null, "address2", 4L, jobInvalid);

//    @BeforeEach
//    void setUp(){
//        Mockito.when(toDtoConverter)
//    }

    @Test
    public void readAll() throws Exception {
        Collection<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(employeeToAdd);
        allEmployees.add(employeeNotToAdd);

        Mockito.when(employeeService.readAll()).thenReturn(allEmployees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is("employee1")));
    }

    @Test
    void create() {
    }
}