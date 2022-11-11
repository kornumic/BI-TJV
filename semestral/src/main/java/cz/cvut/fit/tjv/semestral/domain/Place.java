package cz.cvut.fit.tjv.semestral.domain;

public class Place implements DomainEntity<Long>{
    public Long id;
    public String address;
    public Boolean state;

    public Place(){}
    public Place(Long id) {
        this.id = id;
    }

    public Place(Long id, String address, Boolean state) {
        this(id);
        this.address = address;
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
}
