package app.dto.mapper;

import app.dto.NewPartnerDto;
import app.dto.PartnerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewPartnerMapper {
    NewPartnerMapper INSTANCE = Mappers.getMapper(NewPartnerMapper.class);
    PartnerDto toPartnerDto(NewPartnerDto newPartnerDto);
}
