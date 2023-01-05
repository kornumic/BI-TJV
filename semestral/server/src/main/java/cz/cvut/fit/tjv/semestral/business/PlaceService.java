package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.JobJpaRepository;
import cz.cvut.fit.tjv.semestral.dao.jpa.PlaceJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Component
public class PlaceService extends AbstractCrudService<Place, Long> {
    protected PlaceService(CrudRepository<Place, Long> placeRepository) {
        super(placeRepository);
    }

    @Override
    public Place create(Place entity) {
        if(((PlaceJpaRepository)repository).existsByAddress(entity.getAddress())){
            throw new EntityStateException("Job with this name already exists!");
        }
        return super.create(entity);
    }
}
