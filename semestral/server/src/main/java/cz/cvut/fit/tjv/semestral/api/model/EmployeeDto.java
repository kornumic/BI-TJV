package cz.cvut.fit.tjv.semestral.api.model;

import cz.cvut.fit.tjv.semestral.domain.Job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class EmployeeDto extends AbstractEntityDto<Long>{

    public String name;

    public Long salary;
    public LocalDate dateOfBirth;
    public String address;
    public Long skill;
    public Collection<JobDto> myJobs = new ArrayList<>();

}
