package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.JobJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
class JobServiceTest {
    @Autowired
    private JobService jobService;

    @MockBean
    private JobJpaRepository jobRepository;

    Job jobExists = new Job(1L, "jobExists", 3L, 50L, false);
    Job jobNotExists = new Job(2L, "jobNotExists", 3L, 50L, false);


    @BeforeEach
    void setUp(){
        Mockito.when(jobRepository.findById(jobExists.getId())).thenReturn(Optional.of(jobExists));
        Mockito.when(jobRepository.findById(jobNotExists.getId())).thenReturn(Optional.empty());
    }

    @Test
    void testCreateCreated() {
        jobService.create(jobNotExists);
        Mockito.verify(jobRepository, Mockito.times(1)).save(jobNotExists);
    }

    @Test
    void testCreateRejected() {
        Mockito.when(jobRepository.existsByName(jobExists.getName())).thenReturn(true);
        Assertions.assertThrows(EntityStateException.class, () -> jobService.create(jobExists));
    }


    @Test
    void updateUpdated() {
        Mockito.when(jobService.existsById(jobExists.getId())).thenReturn(true);

        jobService.update(jobExists);
        Mockito.verify(jobRepository, Mockito.times(1)).save(jobExists);
        Mockito.verify(jobRepository, Mockito.times(1)).existsById(jobExists.getId());

    }

    @Test
    void updateNotUpdated() {
        Mockito.when(jobService.existsById(jobNotExists.getId())).thenReturn(false);
        Assertions.assertThrows(EntityStateException.class, () -> jobService.update(jobNotExists));
    }
    
    @Test
    void checkEntityValid(){
        Assertions.assertTrue(jobService.checkEntityValid(jobExists));
    }

    @Test
    void checkEntityInvalid(){
        Assertions.assertFalse(jobService.checkEntityValid(jobNotExists));
    }

}