package app.dao.interfaces;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;

public interface GuestDao {
    public GuestDto findByUserId(UserDto userDto) throws Exception;
    public void createGuest(GuestDto guestDto, PartnerDto partnerDto, UserDto userDto) throws Exception;
}
