package cz.cvut.fit.tjv.semestral.dao.jpa;

import cz.cvut.fit.tjv.semestral.domain.Job;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PlaceJpaRepositoryTest {

    @Autowired
    PlaceJpaRepository placeJpaRepository;

    @AfterEach
    void tearDown() {
        placeJpaRepository.deleteAll();
    }

    @Test
    void existsByAddress() {
        Place place1 = new Place(null, "ubrez 69", false);
        placeJpaRepository.save(place1);

        Assertions.assertEquals(true, placeJpaRepository.existsByAddress("ubrez 69"));
        Assertions.assertEquals(false, placeJpaRepository.existsByAddress("ubrez 420"));
    }
}