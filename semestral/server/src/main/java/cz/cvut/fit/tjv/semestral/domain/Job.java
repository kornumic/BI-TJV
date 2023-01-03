package cz.cvut.fit.tjv.semestral.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Job implements DomainEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Long difficulty;
    private Long time;
    private Boolean finished;

    @ManyToMany(mappedBy = "myJobs")
    private Collection<Employee> assignedEmployees;
    public Job(){}
    public Job(Long id) {
        this.id = id;
    }

    public Job(Long id, String name, Long difficulty, Long time, Boolean finished) {
        this(id);
        this.name = Objects.requireNonNull(name);
        this.difficulty = Objects.requireNonNull(difficulty);
        this.time = Objects.requireNonNull(time);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return id.equals(job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
