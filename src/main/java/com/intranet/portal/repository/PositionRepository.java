package com.intranet.portal.repository;

import com.intranet.portal.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
    boolean existsByName(String name);
}
