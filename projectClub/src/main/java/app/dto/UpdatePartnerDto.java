package app.dto;

import app.entity.Subscription;
import lombok.Data;

@Data
public class UpdatePartnerDto {
    Double amount;
    Boolean increase;
    Subscription typePartner;

}
