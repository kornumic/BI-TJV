package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.JobJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Component
public class JobService extends AbstractCrudService<Job, Long>{
    protected JobService(CrudRepository<Job, Long> jobRepository) {
        super(jobRepository);
    }

    @Override
    public Job create(Job entity) {
        if(((JobJpaRepository)repository).existsByName(entity.getName())){
            throw new EntityStateException("Job with this name already exists!");
        }
        return super.create(entity);
    }

    public boolean checkEntityValid(Job job) {
        var jobFromDb = readById(job.getId());
        return jobFromDb.isPresent() && job.equals(jobFromDb.get());
    }
}
