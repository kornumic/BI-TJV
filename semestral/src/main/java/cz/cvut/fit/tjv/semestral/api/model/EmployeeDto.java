package cz.cvut.fit.tjv.semestral.api.model;

import java.util.Date;

public class EmployeeDto {

    private Long id;
    private String name;
    private Long salary;
    private Date dateOfBirth;
    private String address;
    private Long skill;

    public Long getId() {
        return id;
    }
    public void setId(Long employeeNumber) {
        this.id = employeeNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Long getSalary() {
        return salary;
    }
    public void setSalary(Long salary) {
        this.salary = salary;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getSkill() {
        return skill;
    }
    public void setSkill(Long skill) {
        this.skill = skill;
    }
}
