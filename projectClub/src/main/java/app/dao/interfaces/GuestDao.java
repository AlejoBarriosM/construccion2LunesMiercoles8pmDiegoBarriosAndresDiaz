package app.dao.interfaces;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;

public interface GuestDao {
    public GuestDto findByUserName(GuestDto guestDto) throws Exception;
    public boolean existsByUserName(GuestDto guestDto) throws Exception;
    public void createGuest(GuestDto guestDto, PartnerDto partnerDto, UserDto userDto) throws Exception;
}
