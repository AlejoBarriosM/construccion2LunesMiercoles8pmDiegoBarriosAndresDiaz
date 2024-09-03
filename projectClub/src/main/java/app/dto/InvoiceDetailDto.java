package app.dto;



public class InvoiceDetailDto {

    private long idInvoiceDetail;
    private InvoiceDto idInvoice;
    private long item;
    private String descriptionInvoiceDetail;
    private double amountInvoiceDetail;

    public InvoiceDetailDto() { }

    public long getIdInvoiceDetail() {
        return idInvoiceDetail;
    }

    public void setIdInvoiceDetail(long idInvoiceDetail) {
        this.idInvoiceDetail = idInvoiceDetail;
    }

    public InvoiceDto getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(InvoiceDto idInvoice) {
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

    @Override
    public String toString() {
        return "Item:" + item +
                " | Concepto: '" + descriptionInvoiceDetail + '\'' +
                " | Valor: " + amountInvoiceDetail;
    }
}

