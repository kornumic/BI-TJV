package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.EmployeeJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeJpaRepository employeeRepository;

    @MockBean
    private JobService jobService;

    ArrayList<Job> jobArray = new ArrayList<Job>(2);
    Job jobValid = new Job(1L, "jobValid", 3L, 50L, false);
    Job jobInvalid = new Job(2L, "jobInvalid", 3L, 50L, false);

    Employee employeeToAdd = new Employee(1L, "employee1", 500L, null, "address1", 3L, jobValid);
    Employee employeeNotToAdd = new Employee(2L, "employee2", 400L, null, "address2", 4L, jobInvalid);


    @BeforeEach
    void setUp(){
        Mockito.when(employeeRepository.save(employeeToAdd)).thenReturn(Optional.of(employeeToAdd).get());
        Mockito.when(employeeRepository.save(employeeNotToAdd)).thenReturn(Optional.of(employeeNotToAdd).get());

        Mockito.when(employeeRepository.existsById(employeeToAdd.getId())).thenReturn(true);
        Mockito.when(employeeRepository.existsById(employeeNotToAdd.getId())).thenReturn(false);

        Mockito.when(jobService.checkEntityValid(jobValid)).thenReturn(true);
        Mockito.when(jobService.checkEntityValid(jobInvalid)).thenReturn(false);
    }

    @Test
    void testCreateCreated() {
        employeeService.create(employeeToAdd);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employeeToAdd);
    }

    @Test
    void testCreateRejected() {
        Assertions.assertThrows(EntityStateException.class, () -> employeeService.create(employeeNotToAdd));
    }


    @Test
    void updateUpdated() {
        employeeService.update(employeeToAdd);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employeeToAdd);
        Mockito.verify(employeeRepository, Mockito.times(1)).existsById(employeeToAdd.getId());

    }

    @Test
    void updateNotUpdated() {
        Assertions.assertThrows(EntityStateException.class, () -> employeeService.update(employeeNotToAdd));
    }
}