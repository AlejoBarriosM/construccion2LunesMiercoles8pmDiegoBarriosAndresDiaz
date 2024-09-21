package app.dto;

import lombok.Data;

@Data
public class PartnerDto {

    private long idPartner;
    private UserDto idUserPartner;
    private double amountPartner;
    private String typePartner;
    private String creationDatePartner;

    public PartnerDto() {
    }

}
