package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EmployeeService extends AbstractCrudService<Employee, Long> {
    protected EmployeeService(CrudRepository<Employee, Long> employeeRepository) {
        super(employeeRepository);
    }
}
