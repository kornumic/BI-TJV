package cz.cvut.fit.tjv.semestral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
public class Employee implements DomainEntity<Long>{
    @Id
    private Long id;
    private String name;
    private Long salary;
    private Date dateOfBirth;
    private String address;
    private Long skill;

    @ManyToMany
    private Collection<Job> myJobs;

    public Employee() {}

    public Employee(Long id) {
        this.id = Objects.requireNonNull(id);
    }

    public Employee(Long id, String name, Long salary, Date dateOfBirth, String address, Long skill) {
        this(id);
        this.name = name;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.skill = skill;
    }

    public Collection<Job> returnMyJobs(){
        return myJobs;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long employeeNumber) {
        this.id = employeeNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
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
