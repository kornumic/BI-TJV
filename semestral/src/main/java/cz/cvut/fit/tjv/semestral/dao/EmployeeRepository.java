package cz.cvut.fit.tjv.semestral.dao;

import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
