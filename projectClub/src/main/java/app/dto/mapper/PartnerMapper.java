package app.dto.mapper;

import app.dto.PartnerDto;
import app.entity.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PartnerMapper {
    PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);
    PartnerDto toPartnerDto(Partner partner);
    Partner toPartner(PartnerDto partnerDto);
}
