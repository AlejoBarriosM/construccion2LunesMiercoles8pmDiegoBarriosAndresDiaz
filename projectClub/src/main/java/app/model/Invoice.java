package app.model;

public class Invoice {

    private long idInvoice;
    private Person idPerson;
    private Partner idPartner;
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

    public Person getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    public Partner getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(Partner idPartner) {
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
}
