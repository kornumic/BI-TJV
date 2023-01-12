package cz.cvut.fit.tjv.semestral.client.model;

import java.util.ArrayList;
import java.util.Collection;

public class PlaceModel {
    public Long id;

    public String address;

    public Boolean state;

    public Collection<JobModel> jobsInPlace = new ArrayList<>();

    public Boolean error;

    public String message;

    public PlaceModel(){
    }

    public PlaceModel(PlaceModel place, Boolean error, String message){
        this.id = place.id;
        this.address = place.address;
        this.state = place.state;
        this.jobsInPlace = place.jobsInPlace;

        this.error = error;
        this.message = message;
    }

    public PlaceModel(Boolean error, String message){

        this.error = error;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Collection<JobModel> getJobsInPlace() {
        return jobsInPlace;
    }

    public void setJobsInPlace(Collection<JobModel> jobsInPlace) {
        this.jobsInPlace = jobsInPlace;
    }
}
