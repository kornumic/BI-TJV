package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.dao.jpa.EmployeeJpaRepository;
import cz.cvut.fit.tjv.semestral.dao.jpa.JobJpaRepository;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Component
public class EmployeeService extends AbstractCrudService<Employee, Long> {
    private JobService jobService;
    protected EmployeeService(CrudRepository<Employee, Long> employeeRepository, JobService jobService) {
        super(employeeRepository);
        this.jobService = jobService;
    }

    @Override
    public Employee create(Employee entity) throws EntityStateException{
        var jobs = entity.getMyJobs();
        for(Job job : jobs){
            if(!jobService.existsById(job.getId()))
                throw new EntityStateException("Job \"" + job.getName() + "\" does not exists");
        }
        return repository.save(entity);
    }

    public Collection<Employee> readAllAssignable() {
        return ((EmployeeJpaRepository) repository).findAllFree(160L);
    }
}
