package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.EmployeeDto;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EmployeeToEntityConverter implements Function<EmployeeDto, Employee> {
    @Override
    public Employee apply(EmployeeDto employeeDto) {
        return new Employee(employeeDto.getId(),
                            employeeDto.getName(),
                            employeeDto.getSalary(),
                            employeeDto.getDateOfBirth(),
                            employeeDto.getAddress(),
                            employeeDto.getSkill());
    }
}
