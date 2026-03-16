package com.intranet.portal.mapper;

import com.intranet.portal.dto.department.DepartmentCreateRequest;
import com.intranet.portal.dto.department.DepartmentResponse;
import com.intranet.portal.dto.department.DepartmentUpdateRequest;
import com.intranet.portal.entity.Department;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employees", ignore = true)
    })
    Department toEntity(DepartmentCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employees", ignore = true)
    })
    void updateEntityFromDto(DepartmentUpdateRequest request, @MappingTarget Department department);

    DepartmentResponse toResponse(Department department);
}