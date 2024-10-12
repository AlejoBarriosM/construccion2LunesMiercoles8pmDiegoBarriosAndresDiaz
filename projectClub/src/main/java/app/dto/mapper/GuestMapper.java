package app.dto.mapper;

import app.dto.GuestDto;
import app.entity.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuestMapper {
    GuestMapper INSTANCE = Mappers.getMapper(GuestMapper.class);
    GuestDto toGuestDto(Guest guest);
    Guest toGuest(GuestDto guestDto);
}
