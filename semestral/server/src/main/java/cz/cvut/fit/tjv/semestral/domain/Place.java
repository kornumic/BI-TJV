package cz.cvut.fit.tjv.semestral.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Place implements DomainEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String address;
    private Boolean state;

    @OneToMany
    @JoinColumn(name = "jobs_in_place")
    private Collection<Job> jobsInPlace;

    public Place(){}
    public Place(Long id) {
        this.id = id;
    }

    public Place(Long id, String address, Boolean state) {
        this(id);
        this.address = Objects.requireNonNull(address);
        this.state = state;
    }

    public Place(Long id, String address, Boolean state, Collection<Job> jobsInPlace) {
        this(id, address, state);
        this.jobsInPlace = jobsInPlace;
    }

    public Place(Long id, String address, Boolean state, Job myJob) {
        this(id, address, state);
        this.jobsInPlace = new ArrayList<>();
        this.jobsInPlace.add(myJob);
    }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Collection<Job> getJobsInPlace() {
        return jobsInPlace;
    }

    public void setJobsInPlace(Collection<Job> jobsInPlace) {
        this.jobsInPlace = jobsInPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(id, place.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
