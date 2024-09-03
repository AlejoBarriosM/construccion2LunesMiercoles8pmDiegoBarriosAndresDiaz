package app.service.interfaces;

import app.dto.GuestDto;
import app.dto.PersonDto;
import app.dto.UserDto;

public interface GuestService {
    void createGuest(GuestDto guestDto, UserDto userDto, PersonDto personDto) throws Exception;
    boolean pendingInvoices(GuestDto guestDto) throws Exception;
    void upgradeToPartner(GuestDto guestDto) throws Exception;
}

