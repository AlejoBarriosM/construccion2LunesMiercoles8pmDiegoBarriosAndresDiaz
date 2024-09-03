package app.dto;

public class PartnerDto {

    private long idPartner;
    private UserDto idUserPartner;
    private double amountPartner;
    private String typePartner;
    private String creationDatePartner;

    public PartnerDto() {
    }

    public long getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(long idPartner) {
        this.idPartner = idPartner;
    }

    public UserDto getIdUserPartner() {
        return idUserPartner;
    }

    public void setIdUserPartner(UserDto idUserPartner) {
        this.idUserPartner = idUserPartner;
    }

    public double getAmountPartner() {
        return amountPartner;
    }

    public void setAmountPartner(double amountPartner) {
        this.amountPartner = amountPartner;
    }

    public String getTypePartner() {
        return typePartner;
    }

    public void setTypePartner(String typePartner) {
        this.typePartner = typePartner;
    }

    public String getCreationDatePartner() {
        return creationDatePartner;
    }

    public void setCreationDatePartner(String creationDatePartner) {
        this.creationDatePartner = creationDatePartner;
    }
}
