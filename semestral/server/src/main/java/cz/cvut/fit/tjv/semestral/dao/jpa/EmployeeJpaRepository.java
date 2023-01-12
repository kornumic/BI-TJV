package cz.cvut.fit.tjv.semestral.dao.jpa;

import cz.cvut.fit.tjv.semestral.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    @Query(nativeQuery = true,
           value = "select * from employee\n" +
                   "except\n" +
                   "select id, address, date_of_birth, name, salary, skill from (select assigned_employees_id, sum(time) as time_total from (\n" +
                   "       select assigned_employees_id, j.time from employee_my_jobs e\n" +
                   "       join job j on (j.id = e.my_jobs_id)\n" +
                   "   )R1\n" +
                   "   group by assigned_employees_id\n" +
                   "   having (sum(time) > :limit))R0\n" +
                   "   join employee on (employee.id = R0.assigned_employees_id)")
    Collection<Employee> findAllFree(@Param("limit") Long limit);
}
