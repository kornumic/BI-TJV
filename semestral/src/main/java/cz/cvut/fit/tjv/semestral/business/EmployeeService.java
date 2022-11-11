package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public abstract class EmployeeService extends AbstractCrudService<Employee, Long> {
    protected EmployeeService(CrudRepository<Employee, Long> employeeRepository) {
        super(employeeRepository);
    }

}
