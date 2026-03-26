package com.intranet.portal.repository;

import com.intranet.portal.entity.WorkPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkPermitRepository extends JpaRepository<WorkPermit, Long> {

    List<WorkPermit> findAllByEmployeeEmailOrderByIdDesc(String email);

    @Query("""
           select w from WorkPermit w
           left join fetch w.employee
           where w.id = :id
             and w.isDeleted = false
           """)
    Optional<WorkPermit> findByIdWithEmployee(@Param("id") Long id);

    @Query("""
           select w from WorkPermit w
           left join fetch w.employee
           where w.isDeleted = false
           """)
    List<WorkPermit> findAllWithEmployee();

    @Query("""
           select w from WorkPermit w
           left join fetch w.employee
           where w.startDate >= :startOfMonth
             and w.endDate <= :endOfMonth
             and w.isDeleted = false
           """)
    List<WorkPermit> findMonthlyActivePermits(@Param("startOfMonth") LocalDateTime startOfMonth,
                                              @Param("endOfMonth") LocalDateTime endOfMonth);
}