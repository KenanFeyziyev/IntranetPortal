package com.intranet.portal.controller;

import com.intranet.portal.dto.position.PositionCreateRequest;
import com.intranet.portal.dto.position.PositionResponse;
import com.intranet.portal.dto.position.PositionUpdateRequest;
import com.intranet.portal.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
@Slf4j
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public List<PositionResponse> getAll() {
        log.info("HTTP GET /api/positions called");
        return positionService.getAll();
    }

    @GetMapping("/{id}")
    public PositionResponse getById(@PathVariable Long id) {
        log.info("HTTP GET /api/positions/{} called", id);
        return positionService.getById(id);
    }

    @PostMapping
    public Long create(@RequestBody @Valid PositionCreateRequest request) {
        log.info("HTTP POST /api/positions called");
        return positionService.create(request);
    }

    @PutMapping("/{id}")
    public PositionResponse update(@PathVariable Long id,
                                   @RequestBody @Valid PositionUpdateRequest request) {
        log.info("HTTP PUT /api/positions/{} called", id);
        return positionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("HTTP DELETE /api/positions/{} called", id);
        positionService.delete(id);
    }
}