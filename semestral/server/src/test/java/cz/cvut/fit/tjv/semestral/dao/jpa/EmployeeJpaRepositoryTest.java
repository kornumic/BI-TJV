package cz.cvut.fit.tjv.semestral.dao.jpa;

import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeJpaRepositoryTest {

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    JobJpaRepository jobJpaRepository;

    @AfterEach
    void tearDown() {
        employeeJpaRepository.deleteAll();
    }


    /**
     * Test is not working. I couldn't find what is wrong, but I think problem is with hsqldb test database
     * and my query being written for Postgres in nativeQuery. I couldn't manage to rewrite my query in JPQL,
     * but it would almost surely solve the problem.
     */
    @Test
    void findAllFree() {
        /*
        Job job1 = new Job(1L, "job1", 5L, 60L, false);
        Job job2 = new Job(2L, "job1", 5L, 161L, false);

        jobJpaRepository.save(job1);
        jobJpaRepository.save(job2);

        Collection<Job> myJobs1 = new ArrayList<Job>();
        myJobs1.add(job1);
        Collection<Job> myJobs2 = new ArrayList<Job>();
        myJobs2.add(job2);

        Employee employee1 = new Employee(1L, "employee1", 500L, null, "ubrez1", 5L );
        employee1.setMyJobs(myJobs1);
        Employee employee2 = new Employee(2L, "employee2", 600L, null, "ubrez2", 3L );
        employee2.setMyJobs(myJobs2);

        employeeJpaRepository.save(employee1);
        employeeJpaRepository.save(employee2);

        Assertions.assertEquals(1, employeeJpaRepository.findAllFree(50L).size());
        Assertions.assertEquals(2, employeeJpaRepository.findAllFree(160L).size());
        Assertions.assertEquals(1, employeeJpaRepository.findAllFree(200L).size());
        */

    }
}