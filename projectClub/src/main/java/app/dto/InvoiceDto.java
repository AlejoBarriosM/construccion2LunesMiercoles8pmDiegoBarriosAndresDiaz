
package app.dto;


public class InvoiceDto {

    private long idInvoice;
    private PersonDto idPerson;
    private PartnerDto idPartner;
    private String creationDateInvoice;
    private double amountInvoice;
    private String statusInvoice;

    public InvoiceDto() { }

    public long getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public PersonDto getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(PersonDto idPerson) {
        this.idPerson = idPerson;
    }

    public PartnerDto getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(PartnerDto idPartner) {
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

    @Override
    public String toString() {
        return "Nro. Factura: " + idInvoice +
                "\nPersona: " + idPerson.getNamePerson() +
                "\nSocio: " + idPartner.getIdUserPartner().getIdPerson().getNamePerson() +
                "\nFecha: " + creationDateInvoice +
                "\nTotal: " + amountInvoice +
                "\nEstado: " + statusInvoice;
    }
}

