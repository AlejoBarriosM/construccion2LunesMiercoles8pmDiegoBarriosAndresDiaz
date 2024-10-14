package app.dto.mapper;

import app.dto.GuestDto;
import app.dto.NewGuestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewGuestMapper {
    NewGuestMapper INSTANCE = Mappers.getMapper(NewGuestMapper.class);
    GuestDto toGuestDto(NewGuestDto newGuestDto);
}
