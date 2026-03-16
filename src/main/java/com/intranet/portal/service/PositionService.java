package com.intranet.portal.service;

import com.intranet.portal.dto.position.PositionCreateRequest;
import com.intranet.portal.dto.position.PositionResponse;
import com.intranet.portal.dto.position.PositionUpdateRequest;

import java.util.List;

public interface PositionService {
    List<PositionResponse> getAll();

    PositionResponse getById(Long id);

    Long create(PositionCreateRequest request);

    PositionResponse update(Long id, PositionUpdateRequest request);

    void delete(Long id);
}