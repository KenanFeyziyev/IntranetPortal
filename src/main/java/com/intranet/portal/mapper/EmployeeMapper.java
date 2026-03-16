package com.intranet.portal.mapper;

import com.intranet.portal.dto.employee.EmployeeCreateRequest;
import com.intranet.portal.dto.employee.EmployeeResponse;
import com.intranet.portal.dto.employee.EmployeeUpdateRequest;
import com.intranet.portal.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "department", ignore = true),
            @Mapping(target = "position", ignore = true),
            @Mapping(target = "workPermits", ignore = true),
            @Mapping(target = "attendances", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    Employee toEntity(EmployeeCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "department", ignore = true),
            @Mapping(target = "position", ignore = true),
            @Mapping(target = "workPermits", ignore = true),
            @Mapping(target = "attendances", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    void updateEntityFromDto(EmployeeUpdateRequest dto, @MappingTarget Employee entity);

    @Mappings({
            @Mapping(target = "departmentId", source = "department.id"),
            @Mapping(target = "departmentName", source = "department.departmentName"),
            @Mapping(target = "positionId", source = "position.id"),
            @Mapping(target = "positionName", source = "position.name")
    })
    EmployeeResponse toResponse(Employee employee);
}
