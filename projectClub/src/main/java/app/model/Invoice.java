package app.model;

public class Invoice {

    private long idInvoice;
    private long idPerson;
    private long idPartner;
    private String creationDateInvoice;
    private double amountInvoice;
    private String statusInvoice;

    public Invoice() {
    }

    public long getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public int getIdPerson() {
        return (int) idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    public long getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(long idPartner) {
        this.idPartner = idPartner;
    }

    public String getCreationDateInvoice() {
        return creationDateInvoice;
    }

    public void setCreationDateInvoice(String creationDateInvoice) {
        this.creationDateInvoice = creationDateInvoice;
    }

    public double getAmountInvoice() {
        return amountInvoice;
    }

    public void setAmountInvoice(double amountInvoice) {
        this.amountInvoice = amountInvoice;
    }

    public String getStatusInvoice() {
        return statusInvoice;
    }

    public void setStatusInvoice(String statusInvoice) {
        this.statusInvoice = statusInvoice;
    }

    public void getStatusInvoice(Object statusInvoice) {
    }
}
