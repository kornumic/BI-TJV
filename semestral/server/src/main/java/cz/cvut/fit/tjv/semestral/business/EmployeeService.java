package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.EmployeeJpaRepository;
import cz.cvut.fit.tjv.semestral.dao.jpa.JobJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        for(Job job : jobs){
            if(!jobService.checkEntityValid(job))
                throw new EntityStateException("Job \"" + job.getName() + "\" does not is invalid");
        }
        return repository.save(entity);
    }

    public Employee update(Employee entity) throws EntityStateException {
        var jobs = entity.getMyJobs();
        for(Job job : jobs){
            if(!jobService.checkEntityValid(job))
                throw new EntityStateException("Job \"" + job.getName() + "\" is invalid");
        }

        return super.update(entity);
    }

    public Collection<Employee> readAllAssignable() {
        return ((EmployeeJpaRepository) repository).findAllFree(/*160L*/);
    }
}
