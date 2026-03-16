package com.intranet.portal.service.impl;

import com.intranet.portal.dto.position.PositionCreateRequest;
import com.intranet.portal.dto.position.PositionResponse;
import com.intranet.portal.dto.position.PositionUpdateRequest;
import com.intranet.portal.entity.Position;
import com.intranet.portal.exception.NotFoundException;
import com.intranet.portal.mapper.PositionMapper;
import com.intranet.portal.repository.PositionRepository;
import com.intranet.portal.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PositionResponse> getAll() {

        log.info("Fetching all positions");

        return positionRepository.findAll()
                .stream()
                .map(positionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PositionResponse getById(Long id) {

        log.info("Fetching position by id: {}", id);

        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position not found: " + id));

        return positionMapper.toResponse(position);
    }

    @Override
    public Long create(PositionCreateRequest request) {

        log.info("Creating position with name: {}", request.name());

        Position position = positionMapper.toEntity(request);
        Position saved = positionRepository.save(position);

        log.info("Position created successfully with id: {}", saved.getId());

        return saved.getId();
    }

    @Override
    public PositionResponse update(Long id, PositionUpdateRequest request) {

        log.info("Updating position with id: {}", id);

        Position existing = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position not found: " + id));

        positionMapper.updateEntityFromDto(request, existing);

        Position saved = positionRepository.save(existing);

        log.info("Position updated successfully with id: {}", saved.getId());

        return positionMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting position with id: {}", id);

        Position existing = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Position not found: " + id));

        positionRepository.delete(existing);

        log.info("Position deleted successfully with id: {}", id);
    }
}