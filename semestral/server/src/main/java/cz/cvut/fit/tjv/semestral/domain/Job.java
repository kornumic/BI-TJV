package cz.cvut.fit.tjv.semestral.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Job implements DomainEntity<Long>{
    @Id
    private Long id;
    private String name;
    private Long difficulty;
    private Long time;
    private Boolean finished;

    public Job(){}
    public Job(Long id) {
        this.id = id;
    }

    public Job(Long id, String name, Long difficulty, Long time, Boolean finished) {
        this(id);
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
        this.finished = finished;
    }

    @Override
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
    public Long getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }
    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public Boolean getFinished() {
        return finished;
    }
    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
