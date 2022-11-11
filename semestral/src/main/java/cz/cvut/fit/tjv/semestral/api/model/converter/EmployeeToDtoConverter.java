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

        ret.setId(employee.getId());
        ret.setName(employee.getName());
        ret.setSalary(employee.getSalary());
        ret.setDateOfBirth(employee.getDateOfBirth());
        ret.setAddress(employee.getAddress());
        ret.setSkill(employee.getSkill());

        return ret;
    }
}
