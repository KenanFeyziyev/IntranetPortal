package com.intranet.portal.controller;

import com.intranet.portal.dto.department.DepartmentCreateRequest;
import com.intranet.portal.dto.department.DepartmentResponse;
import com.intranet.portal.dto.department.DepartmentUpdateRequest;
import com.intranet.portal.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentResponse> getAll() {
        log.info("HTTP GET /api/departments called");
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        log.info("HTTP GET /api/departments/{} called", id);
        return departmentService.getById(id);
    }

    @PostMapping
    public Long create(@RequestBody @Valid DepartmentCreateRequest request) {
        log.info("HTTP POST /api/departments called");
        return departmentService.create(request);
    }

    @PutMapping("/{id}")
    public DepartmentResponse update(@PathVariable Long id,
                                     @RequestBody @Valid DepartmentUpdateRequest request) {
        log.info("HTTP PUT /api/departments/{} called", id);
        return departmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("HTTP DELETE /api/departments/{} called", id);
        departmentService.delete(id);
    }
}