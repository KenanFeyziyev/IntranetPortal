package com.intranet.portal.service.impl;

import com.intranet.portal.dto.department.DepartmentCreateRequest;
import com.intranet.portal.dto.department.DepartmentResponse;
import com.intranet.portal.dto.department.DepartmentUpdateRequest;
import com.intranet.portal.entity.Department;
import com.intranet.portal.exception.NotFoundException;
import com.intranet.portal.mapper.DepartmentMapper;
import com.intranet.portal.repository.DepartmentRepository;
import com.intranet.portal.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll() {
        log.info("Fetching all departments");

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(Long id) {
        log.info("Fetching department by id: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found: " + id));

        return departmentMapper.toResponse(department);
    }

    @Override
    public Long create(DepartmentCreateRequest request) {
        log.info("Creating department with name: {}", request.departmentName());

        Department department = departmentMapper.toEntity(request);
        Department saved = departmentRepository.save(department);

        log.info("Department created successfully with id: {}", saved.getId());
        return saved.getId();
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentUpdateRequest request) {
        log.info("Updating department with id: {}", id);

        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found: " + id));

        departmentMapper.updateEntityFromDto(request, existing);

        Department saved = departmentRepository.save(existing);

        log.info("Department updated successfully with id: {}", saved.getId());
        return departmentMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting department with id: {}", id);

        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found: " + id));

        departmentRepository.delete(existing);

        log.info("Department deleted successfully with id: {}", id);
    }
}