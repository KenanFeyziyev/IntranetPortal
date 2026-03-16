package com.intranet.portal.repository;

import com.intranet.portal.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Employee> findByCardCode(String cardCode);

    boolean existsByCardCode(String cardCode);

    @Query("""
       select e from Employee e
       left join fetch e.department
       left join fetch e.position
       where e.id = :id
       """)
    Optional<Employee> findByIdWithDepartmentAndPosition(Long id);
}