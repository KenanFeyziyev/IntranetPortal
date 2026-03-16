package com.intranet.portal.entity;

import com.intranet.portal.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_permits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkPermit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reason;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


    @Column(nullable = false)
    private Boolean isApproved;

    private LocalDateTime approvedDate;
    private String approvedBy;

    @Column(nullable = false)
    private Boolean isDeleted;

    private LocalDateTime permitDate;

    private Integer permitDurationInMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
