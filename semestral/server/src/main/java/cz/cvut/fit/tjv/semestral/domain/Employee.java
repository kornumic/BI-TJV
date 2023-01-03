package cz.cvut.fit.tjv.semestral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
public class Employee implements DomainEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Long salary;
    private LocalDate dateOfBirth;
    private String address;
    private Long skill;

    @ManyToMany
    private Collection<Job> myJobs;

    public Employee() {}

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(Long id, String name, Long salary, LocalDate dateOfBirth, String address, Long skill) {
        this(id);
        this.name = Objects.requireNonNull(name);
        this.salary = Objects.requireNonNull(salary);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.skill = Objects.requireNonNull(skill);
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
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
