package com.intranet.portal.mapper;

import com.intranet.portal.dto.attendance.AttendanceCreateRequest;
import com.intranet.portal.dto.attendance.AttendanceResponse;
import com.intranet.portal.dto.attendance.AttendanceUpdateRequest;
import com.intranet.portal.entity.Attendance;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employee", ignore = true)
    })
    Attendance toEntity(AttendanceCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employee", ignore = true)
    })
    void updateEntityFromDto(AttendanceUpdateRequest request, @MappingTarget Attendance attendance);

    @Mappings({
            @Mapping(target = "employeeId", source = "employee.id"),
            @Mapping(target = "employeeFirstName", source = "employee.firstName"),
            @Mapping(target = "employeeLastName", source = "employee.lastName")
    })
    AttendanceResponse toResponse(Attendance attendance);
}