package app.service.interfaces;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;

public interface GuestService {
    public void createGuest(GuestDto guestDto, PartnerDto partnerDto, UserDto userDto, PersonDto personDto) throws Exception;
}
