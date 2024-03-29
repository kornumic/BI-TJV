package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.EmployeeJpaRepository;
import cz.cvut.fit.tjv.semestral.dao.jpa.JobJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class EmployeeService extends AbstractCrudService<Employee, Long> {
    private final JobService jobService;
    protected EmployeeService(CrudRepository<Employee, Long> employeeRepository, JobService jobService) {
        super(employeeRepository);
        this.jobService = jobService;
    }

    @Override
    public Employee create(Employee entity) throws EntityStateException{
        var jobs = entity.getMyJobs();
        if(jobs == null){
            entity.setMyJobs(new ArrayList<Job>());
            return repository.save(entity);
        }
        for(Job job : jobs){
            if(!jobService.checkEntityValid(job))
                throw new EntityStateException("Job \"" + job.getName() + "\" is not valid");
        }
        return repository.save(entity);
    }

    public Employee update(Employee entity) throws EntityStateException {
        var jobs = entity.getMyJobs();
        if(jobs == null){
            entity.setMyJobs(new ArrayList<Job>());
            return super.update(entity);
        }

        for(Job job : jobs){
            if(!jobService.checkEntityValid(job))
                throw new EntityStateException("Job \"" + job.getName() + "\" is not valid");
        }

        return super.update(entity);
    }

    public Collection<Employee> readAllAssignable() {
        return ((EmployeeJpaRepository) repository).findAllFree(160L);
    }
}
