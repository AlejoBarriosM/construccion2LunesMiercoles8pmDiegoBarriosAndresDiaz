package app.model;

public class InvoiceDetail {

    private long idInvoiceDetail;
    private long idInvoice;
    private long item;
    private String descriptionInvoiceDetail;
    private double amountInvoiceDetail;

    public InvoiceDetail() {
    }

    public long getIdInvoiceDetail() {
        return idInvoiceDetail;
    }

    public void setIdInvoiceDetail(long idInvoiceDetail) {
        this.idInvoiceDetail = idInvoiceDetail;
    }

    public long getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public long getItem() {
        return item;
    }

    public void setItem(long item) {
        this.item = item;
    }

    public String getDescriptionInvoiceDetail() {
        return descriptionInvoiceDetail;
    }

    public void setDescriptionInvoiceDetail(String descriptionInvoiceDetail) {
        this.descriptionInvoiceDetail = descriptionInvoiceDetail;
    }

    public double getAmountInvoiceDetail() {
        return amountInvoiceDetail;
    }

    public void setAmountInvoiceDetail(double amountInvoiceDetail) {
        this.amountInvoiceDetail = amountInvoiceDetail;
    }
}
