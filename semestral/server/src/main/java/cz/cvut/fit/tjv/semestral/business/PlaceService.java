package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.JobJpaRepository;
import cz.cvut.fit.tjv.semestral.dao.jpa.PlaceJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Component
public class PlaceService extends AbstractCrudService<Place, Long> {
    private final JobService jobService;
    protected PlaceService(CrudRepository<Place, Long> placeRepository, JobService jobService) {
        super(placeRepository);
        this.jobService = jobService;
    }

    @Override
    public Place create(Place entity) throws EntityStateException{
        var jobs = entity.getJobsInPlace();
        for(Job job : jobs){
            if(!jobService.checkEntityValid(job))
                throw new EntityStateException("Job \"" + job.getName() + "\" does not is invalid");
        }
        return repository.save(entity);
    }

    @Override
    public Place update(Place entity) throws EntityStateException {
        var jobs = entity.getJobsInPlace();
        for(Job job : jobs){
            if(!jobService.checkEntityValid(job))
                throw new EntityStateException("Job \"" + job.getName() + "\" is invalid");
        }

        return super.update(entity);
    }
}
