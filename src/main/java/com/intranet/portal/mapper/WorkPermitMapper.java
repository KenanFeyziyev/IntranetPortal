package com.intranet.portal.mapper;

import com.intranet.portal.dto.workpermit.WorkPermitCreateRequest;
import com.intranet.portal.dto.workpermit.WorkPermitResponse;
import com.intranet.portal.dto.workpermit.WorkPermitUpdateRequest;
import com.intranet.portal.entity.WorkPermit;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WorkPermitMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employee", ignore = true),
            @Mapping(target = "isApproved", ignore = true),
            @Mapping(target = "approvedDate", ignore = true),
            @Mapping(target = "approvedBy", ignore = true),
            @Mapping(target = "isDeleted", ignore = true),
            @Mapping(target = "permitDate", ignore = true),
            @Mapping(target = "permitDurationInMinutes", ignore = true)
    })
    WorkPermit toEntity(WorkPermitCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employee", ignore = true),
            @Mapping(target = "isApproved", ignore = true),
            @Mapping(target = "approvedDate", ignore = true),
            @Mapping(target = "approvedBy", ignore = true),
            @Mapping(target = "isDeleted", ignore = true),
            @Mapping(target = "permitDate", ignore = true),
            @Mapping(target = "permitDurationInMinutes", ignore = true)
    })
    void updateEntityFromDto(WorkPermitUpdateRequest request, @MappingTarget WorkPermit workPermit);

    @Mappings({
            @Mapping(target = "employeeId", source = "employee.id"),
            @Mapping(target = "employeeFirstName", source = "employee.firstName"),
            @Mapping(target = "employeeLastName", source = "employee.lastName")
    })
    WorkPermitResponse toResponse(WorkPermit workPermit);
}