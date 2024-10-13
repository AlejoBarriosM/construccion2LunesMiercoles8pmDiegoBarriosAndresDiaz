package app.dto;

import lombok.Data;

@Data
public class NewGuestDto {
    NewUserDto userIdGuest;
    Long idPartner;
}
