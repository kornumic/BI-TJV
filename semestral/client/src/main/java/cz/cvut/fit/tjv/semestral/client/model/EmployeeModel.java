package cz.cvut.fit.tjv.semestral.client.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeModel {
    public Long id;
    public String name;
    public Long salary;
    public LocalDate dateOfBirth;
    public String address;
    public Long skill;
    public Boolean error;
    public String message;
    public ArrayList<JobModel> myJobs;

    public EmployeeModel() {
    }
    public EmployeeModel(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public ArrayList<JobModel> getMyJobs() {
        return myJobs;
    }

    public void setMyJobs(ArrayList<JobModel> myJobs) {
        this.myJobs = myJobs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
