package app.model;

public class Partner {
    private long idPartner;
    private long idUserPartner;
    private double amountPartner;
    private String typePartner;
    private String creationDatePartner;

    public Partner() {
    }

    public long getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(long idPartner) {
        this.idPartner = idPartner;
    }

    public long getIdUserPartner() {
        return idUserPartner;
    }

    public void setIdUserPartner(long idUserPartner) {
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
