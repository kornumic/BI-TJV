package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToEntityConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.EmployeeService;
import cz.cvut.fit.tjv.semestral.business.EntityStateException;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@WebMvcTest(EmployeeController.class)
@Import({EmployeeToDtoConverter.class, EmployeeToEntityConverter.class})
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;


//    @BeforeEach
//    void setUp(){
//        Mockito.when(toDtoConverter)
//    }

    Job jobValid = new Job(1L, "jobValid", 3L, 50L, false);
    Job jobInvalid = new Job(2L, "jobInvalid", 3L, 161L, false);

    Employee employeeToAdd = new Employee(1L, "employee1", 500L, null, "address1", 3L, jobValid);
    Employee employeeNotToAdd = new Employee(2L , "employee2", 400L, null, "address2", 4L, jobInvalid);

    Employee employeeUnprocessable = new Employee(3L , "employee", 400L, null, "address2", 4L, jobValid);

    @BeforeEach
    public void setUp(){
        Mockito.when(service.create(employeeToAdd)).thenReturn(employeeToAdd);
        Mockito.when(service.create(employeeNotToAdd)).thenThrow(EntityStateException.class);

        Mockito.when(service.readById(employeeToAdd.getId())).thenReturn(Optional.of(employeeToAdd));
        //Mockito.when(service.readById(employeeNotToAdd.getId())).thenReturn(Optional.empty());


        Mockito.when(service.existsById(employeeToAdd.getId())).thenReturn(true);
        Mockito.when(service.existsById(employeeNotToAdd.getId())).thenReturn(false);
        Mockito.when(service.existsById(employeeUnprocessable.getId())).thenReturn(true);


        Mockito.when(service.update(employeeToAdd)).thenReturn(employeeToAdd);
        Mockito.when(service.update(employeeNotToAdd)).thenReturn(employeeNotToAdd);
        Mockito.when(service.update(employeeUnprocessable)).thenThrow(EntityStateException.class);

        Mockito.when(service.deleteById(employeeToAdd.getId())).thenReturn(true);
        Mockito.when(service.deleteById(employeeNotToAdd.getId())).thenReturn(false);

    }

    @Test
    public void readAll() throws Exception {
        Collection<Employee> allEmployees = new ArrayList<>();
        Collection<Employee> freeEmployees = new ArrayList<>();
        allEmployees.add(employeeToAdd);
        allEmployees.add(employeeNotToAdd);

        Mockito.when(service.readAll()).thenReturn(allEmployees);
        Mockito.when(service.readAllAssignable()).thenThrow(EntityStateException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("employee1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address", Matchers.is("address1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].myJobs", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].myJobs[0].name", Matchers.is("jobValid")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("employee2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address", Matchers.is("address2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].myJobs", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].myJobs[0].name", Matchers.is("jobInvalid")));
    }

    @Test
    public void readAllParam() throws Exception {
        Collection<Employee> allEmployees = new ArrayList<>();
        Collection<Employee> freeEmployees = new ArrayList<>();
        allEmployees.add(employeeToAdd);
        allEmployees.add(employeeNotToAdd);
        freeEmployees.add(employeeToAdd);

        Mockito.when(service.readAll()).thenReturn(allEmployees);
        Mockito.when(service.readAllAssignable()).thenReturn(freeEmployees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .accept("application/json")
                        .param("assignable", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("employee1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address", Matchers.is("address1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].myJobs", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].myJobs[0].name", Matchers.is("jobValid")));
    }

    @Test
    void createValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "employee1",
                                    "salary": "500",
                                    "address": "address1",
                                    "skill": "3",
                                    "myJobs":
                                    [
                                        {
                                            "id": "1",
                                            "name": "jobValid",
                                            "difficulty": 3,
                                            "time": 50,
                                            "finished": false
                                        }
                                    ]
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("employee1")));
    }

    @Test
    void createInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "id": 2,
                                        "name": "employee2",
                                        "salary": "400",
                                        "dateOfBirth": null,
                                        "address": "address2",
                                        "skill": "4",
                                        "myJobs":
                                        [
                                            {
                                                "id": "2",
                                                "name": "jobInvalid",
                                                "difficulty": 3,
                                                "time": 161,
                                                "finished": false
                                            }
                                        ]
                                    }"""))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void createUnprocessable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "salary": "500",
                                    "address": "address1",
                                    "skill": "3",
                                    "myJobs":
                                    []
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void readOneFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("employee1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void readOneNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "employee1",
                                    "salary": "500",
                                    "address": "address1",
                                    "skill": "3",
                                    "myJobs":
                                    [
                                        {
                                            "id": "1",
                                            "name": "jobValid",
                                            "difficulty": 3,
                                            "time": 50,
                                            "finished": false
                                        }
                                    ]
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("employee1")));
    }

    @Test
    void updateNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "employee2",
                                        "salary": "400",
                                        "dateOfBirth": null,
                                        "address": "address2",
                                        "skill": "4",
                                        "myJobs":
                                        [
                                            {
                                                "id": "2",
                                                "name": "jobInvalid",
                                                "difficulty": 3,
                                                "time": 161,
                                                "finished": false
                                            }
                                        ]
                                    }"""))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void updateUnprocessable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "employee",
                                    "salary": "400",
                                    "address": "address2",
                                    "skill": "4",
                                    "myJobs":
                                    [
                                        {
                                            "id": "1",
                                            "name": "jobValid",
                                            "difficulty": 3,
                                            "time": 50,
                                            "finished": false
                                        }
                                    ]
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void updateBad() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "salary": "400",
                                    "address": "address2",
                                    "skill": "4",
                                    "myJobs":
                                    [
                                        {
                                            "id": "1",
                                            "name": "jobValid",
                                            "difficulty": 3,
                                            "time": 50,
                                            "finished": false
                                        }
                                    ]
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

