package cz.cvut.fit.tjv.semestral.dao.jpa;

import cz.cvut.fit.tjv.semestral.domain.Job;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JobJpaRepositoryTest {

    @Autowired
    JobJpaRepository jobJpaRepository;

    @AfterEach
    void tearDown() {
        jobJpaRepository.deleteAll();
    }

    @Test
    void testExistsByName() {
        Job job1 = new Job(null, "job1", 5L, 60L, false);
        jobJpaRepository.save(job1);

        Assertions.assertEquals(true, jobJpaRepository.existsByName("job1"));
        Assertions.assertEquals(false, jobJpaRepository.existsByName("job2"));
    }
}