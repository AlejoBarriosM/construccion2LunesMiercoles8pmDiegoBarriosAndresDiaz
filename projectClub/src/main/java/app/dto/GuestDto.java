package app.dto;

import app.entity.GuestStatus;
import lombok.Data;

@Data
public class GuestDto {

    private long idGuest;
    private UserDto userIdGuest;
    private PartnerDto partnerIdGuest;
    private GuestStatus statusGuest;

}

