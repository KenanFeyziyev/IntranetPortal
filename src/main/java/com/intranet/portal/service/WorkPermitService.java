package com.intranet.portal.service;

import com.intranet.portal.dto.workpermit.WorkPermitApproveRequest;
import com.intranet.portal.dto.workpermit.WorkPermitCreateRequest;
import com.intranet.portal.dto.workpermit.WorkPermitResponse;
import com.intranet.portal.dto.workpermit.WorkPermitUpdateRequest;

import java.util.List;
import java.util.Map;

public interface WorkPermitService {
    List<WorkPermitResponse> getAll();

    WorkPermitResponse getById(Long id);

    Long create(WorkPermitCreateRequest request);

    Long createForCurrentUser(String email, WorkPermitCreateRequest request);

    WorkPermitResponse update(Long id, WorkPermitUpdateRequest request);

    WorkPermitResponse approve(Long id, WorkPermitApproveRequest request);

    void delete(Long id);

    Map<String, String> getTotalPermitDuration();

    List<WorkPermitResponse> getMyWorkPermits(String email);
}