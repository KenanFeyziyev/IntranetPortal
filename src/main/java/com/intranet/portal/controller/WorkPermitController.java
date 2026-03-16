package com.intranet.portal.controller;

import com.intranet.portal.dto.workpermit.WorkPermitApproveRequest;
import com.intranet.portal.dto.workpermit.WorkPermitCreateRequest;
import com.intranet.portal.dto.workpermit.WorkPermitResponse;
import com.intranet.portal.dto.workpermit.WorkPermitUpdateRequest;
import com.intranet.portal.service.WorkPermitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/work-permits")
@RequiredArgsConstructor
@Slf4j
public class WorkPermitController {

    private final WorkPermitService workPermitService;

    @GetMapping
    public List<WorkPermitResponse> getAll() {
        log.info("HTTP GET /api/work-permits called");
        return workPermitService.getAll();
    }

    @GetMapping("/{id}")
    public WorkPermitResponse getById(@PathVariable Long id) {
        log.info("HTTP GET /api/work-permits/{} called", id);
        return workPermitService.getById(id);
    }

    @PostMapping
    public Long create(@RequestBody @Valid WorkPermitCreateRequest request) {
        log.info("HTTP POST /api/work-permits called");
        return workPermitService.create(request);
    }

    @PutMapping("/{id}")
    public WorkPermitResponse update(@PathVariable Long id,
                                     @RequestBody @Valid WorkPermitUpdateRequest request) {
        log.info("HTTP PUT /api/work-permits/{} called", id);
        return workPermitService.update(id, request);
    }

    @PatchMapping("/{id}/approve")
    public WorkPermitResponse approve(@PathVariable Long id,
                                      @RequestBody @Valid WorkPermitApproveRequest request) {
        log.info("HTTP PATCH /api/work-permits/{}/approve called", id);
        return workPermitService.approve(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("HTTP DELETE /api/work-permits/{} called", id);
        workPermitService.delete(id);
    }

    @GetMapping("/monthly-duration")
    public Map<String, String> getTotalPermitDuration() {
        log.info("HTTP GET /api/work-permits/monthly-duration called");
        return workPermitService.getTotalPermitDuration();
    }
}