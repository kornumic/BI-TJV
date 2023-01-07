package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.PlaceJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Job;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @MockBean
    private PlaceJpaRepository placeRepository;

    @MockBean
    private JobService jobService;


    Job jobExists = new Job(1L, "jobExists", 3L, 50L, false);
    Job jobNotExists = new Job(2L, "jobNotExists", 3L, 50L, false);

    Place placeValid = new Place(1L,"ubrez 37", false, jobExists);
    Place placeInvalid = new Place(2L,"ubrez 38", false, jobNotExists);

    @BeforeEach
    void setUp(){
        Mockito.when(placeRepository.save(placeValid)).thenReturn(Optional.of(placeValid).get());
        Mockito.when(placeRepository.save(placeInvalid)).thenReturn(Optional.of(placeInvalid).get());

        Mockito.when(placeRepository.existsById(placeValid.getId())).thenReturn(true);
        Mockito.when(placeRepository.existsById(placeInvalid.getId())).thenReturn(false);

        Mockito.when(jobService.checkEntityValid(jobExists)).thenReturn(true);
        Mockito.when(jobService.checkEntityValid(jobNotExists)).thenReturn(false);
    }


    @Test
    void testCreateCreated() {
        placeService.create(placeValid);
        Mockito.verify(placeRepository, Mockito.times(1)).save(placeValid);
    }

    @Test
    void testCreateRejected() {
        Assertions.assertThrows(EntityStateException.class, () -> placeService.create(placeInvalid));
    }


    @Test
    void updateUpdated() {
        placeService.update(placeValid);
        Mockito.verify(placeRepository, Mockito.times(1)).save(placeValid);
        Mockito.verify(placeRepository, Mockito.times(1)).existsById(placeValid.getId());

    }

    @Test
    void updateNotUpdated() {
        Assertions.assertThrows(EntityStateException.class, () -> placeService.update(placeInvalid));
    }
}