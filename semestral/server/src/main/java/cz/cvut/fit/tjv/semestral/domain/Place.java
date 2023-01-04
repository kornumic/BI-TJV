package cz.cvut.fit.tjv.semestral.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Place implements DomainEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String address;
    private Boolean state;

    @OneToMany(mappedBy = "id")
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
