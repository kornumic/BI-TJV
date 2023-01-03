package cz.cvut.fit.tjv.semestral.api.model;

import java.time.LocalDate;
import java.util.Date;

public class EmployeeDto extends AbstractEntityDto<Long>{

    public String name;
    public Long salary;
    public LocalDate dateOfBirth;
    public String address;
    public Long skill;
}
