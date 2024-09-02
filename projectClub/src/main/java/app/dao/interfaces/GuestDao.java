package app.dao.interfaces;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;

public interface GuestDao {
    GuestDto findByUserId(UserDto userDto) throws Exception;
    void createGuest(GuestDto guestDto, PartnerDto partnerDto, UserDto userDto) throws Exception;
    boolean pendingInvoices(GuestDto guestDto) throws Exception;
    void upgradeToPartner(GuestDto guestDto) throws Exception;
    void changeType(GuestDto guestDto) throws Exception;
}
