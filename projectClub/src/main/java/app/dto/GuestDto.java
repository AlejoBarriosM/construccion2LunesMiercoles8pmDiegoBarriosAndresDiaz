package app.dto;

import lombok.Data;

@Data
public class GuestDto {

    private long idGuest;
    private UserDto userIdGuest;
    private PartnerDto partnerIdGuest;
    private String statusGuest;

}

