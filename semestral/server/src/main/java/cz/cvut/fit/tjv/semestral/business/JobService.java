package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class JobService extends AbstractCrudService<Job, Long>{
    protected JobService(CrudRepository<Job, Long> jobRepository) {
        super(jobRepository);
    }
}