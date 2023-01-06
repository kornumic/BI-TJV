package cz.cvut.fit.tjv.semestral.dao.jpa;

import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface JobJpaRepository extends JpaRepository<Job, Long> {
    Boolean existsByName(String name);
}
