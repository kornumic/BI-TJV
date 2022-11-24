package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.EmployeeDto;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EmployeeToDtoConverter implements Function<Employee, EmployeeDto> {
    @Override
    public EmployeeDto apply(Employee employee){
        var ret = new EmployeeDto();

        ret.id = employee.getId();
        ret.name = employee.getName();
        ret.salary = employee.getSalary();
        ret.dateOfBirth = employee.getDateOfBirth();
        ret.address = employee.getAddress();
        ret.skill = employee.getSkill();

        return ret;
    }
}
