package com.intranet.portal.service;

import com.intranet.portal.dto.department.DepartmentCreateRequest;
import com.intranet.portal.dto.department.DepartmentResponse;
import com.intranet.portal.dto.department.DepartmentUpdateRequest;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAll();

    DepartmentResponse getById(Long id);

    Long create(DepartmentCreateRequest request);

    DepartmentResponse update(Long id, DepartmentUpdateRequest request);

    void delete(Long id);
}