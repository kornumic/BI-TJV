package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.EmployeeDto;
import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.EmployeeToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.EmployeeService;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends AbstractCrudController<Employee, EmployeeDto, Long> {
    public EmployeeController(EmployeeService service, EmployeeToDtoConverter toDtoConverter, EmployeeToEntityConverter toEntityConverter){
        super(service, toDtoConverter, toEntityConverter);
    }
}
