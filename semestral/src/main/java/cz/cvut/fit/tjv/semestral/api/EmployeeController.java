package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.EmployeeDto;
import cz.cvut.fit.tjv.semestral.business.AbstractCrudService;
import cz.cvut.fit.tjv.semestral.business.EmployeeService;
import cz.cvut.fit.tjv.semestral.business.JobService;
import cz.cvut.fit.tjv.semestral.domain.DomainEntity;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends AbstractCrudController<Employee, EmployeeDto, Long> {

}
