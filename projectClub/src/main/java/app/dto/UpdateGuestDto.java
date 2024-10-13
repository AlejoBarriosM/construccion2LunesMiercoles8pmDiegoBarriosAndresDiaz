package app.dto;

import app.entity.GuestStatus;
import lombok.Data;

@Data
public class UpdateGuestDto {
    GuestStatus status;
}
