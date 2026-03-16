package com.intranet.portal.mapper;

import com.intranet.portal.dto.position.PositionCreateRequest;
import com.intranet.portal.dto.position.PositionResponse;
import com.intranet.portal.dto.position.PositionUpdateRequest;
import com.intranet.portal.entity.Position;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employees", ignore = true)
    })
    Position toEntity(PositionCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "employees", ignore = true)
    })
    void updateEntityFromDto(PositionUpdateRequest request, @MappingTarget Position position);

    PositionResponse toResponse(Position position);
}