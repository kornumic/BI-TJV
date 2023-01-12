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
public class EmployeeToEntityConverter implements Function<EmployeeDto, Employee> {
    private final JobToEntityConverter jobConverter = new JobToEntityConverter();
    @Override
    public Employee apply(EmployeeDto employeeDto) throws NullPointerException {
        return new Employee(employeeDto.id,
                            employeeDto.name,
                            employeeDto.salary,
                            employeeDto.dateOfBirth,
                            employeeDto.address,
                            employeeDto.skill,
                            convertMyJobs(employeeDto.myJobs));
    }

    private Collection<Job> convertMyJobs(Collection<JobDto> myJobs){
        Collection<Job> myEntityJobs = new ArrayList<Job>();
        if(myJobs == null)
            return myEntityJobs;

        for (JobDto jobDto : myJobs) {
            myEntityJobs.add(jobConverter.apply(jobDto));
        }
        return myEntityJobs;
    }
}
