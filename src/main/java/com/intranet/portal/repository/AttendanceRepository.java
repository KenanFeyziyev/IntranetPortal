package com.intranet.portal.repository;

import com.intranet.portal.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("""
           select a from Attendance a
           left join fetch a.employee
           where a.id = :id
           """)
    Optional<Attendance> findByIdWithEmployee(@Param("id") Long id);

    @Query("""
           select a from Attendance a
           left join fetch a.employee
           """)
    List<Attendance> findAllWithEmployee();

    @Query("""
           select a from Attendance a
           join fetch a.employee
           where function('date', a.actionDate) = :date
           """)
    List<Attendance> findByActionDate(@Param("date") LocalDate date);

    @Query("""
           select a from Attendance a
           where a.employee.id = :employeeId
           and function('date', a.actionDate) = :date
           """)
    Optional<Attendance> findTodayAttendance(@Param("employeeId") Long employeeId,
                                             @Param("date") LocalDate date);
}