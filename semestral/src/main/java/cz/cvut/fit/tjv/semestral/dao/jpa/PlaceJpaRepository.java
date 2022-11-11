package cz.cvut.fit.tjv.semestral.dao.jpa;

import cz.cvut.fit.tjv.semestral.dao.PlaceRepository;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceJpaRepository extends JpaRepository<Place, Long>, PlaceRepository {

}
