package com.intranet.portal.repository;

import com.intranet.portal.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentName(String departmentName);
}
