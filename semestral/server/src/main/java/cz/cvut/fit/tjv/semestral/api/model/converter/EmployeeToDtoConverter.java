package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.EmployeeDto;
import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.domain.Employee;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class EmployeeToDtoConverter implements Function<Employee, EmployeeDto> {
    private final JobToDtoConverter jobConverter = new JobToDtoConverter();
    @Override
    public EmployeeDto apply(Employee employee){
        var ret = new EmployeeDto();

        ret.id = employee.getId();
        ret.name = employee.getName();
        ret.salary = employee.getSalary();
        ret.dateOfBirth = employee.getDateOfBirth();
        ret.address = employee.getAddress();
        ret.skill = employee.getSkill();
        ret.myJobs = convertMyJobs(employee.getMyJobs());
        return ret;
    }

    private Collection<JobDto> convertMyJobs(Collection<Job> myJobs){
        Collection<JobDto> myEntityJobs = new ArrayList<JobDto>();
        for (Job job : myJobs) {
            myEntityJobs.add(jobConverter.apply(job));
        }
        return myEntityJobs;
    }

}
