package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class PlaceService extends AbstractCrudService<Place, Long> {
    protected PlaceService(CrudRepository<Place, Long> placeRepository) {
        super(placeRepository);
    }
}
