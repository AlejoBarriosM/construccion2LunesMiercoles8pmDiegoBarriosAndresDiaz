package app.dto;

import lombok.Data;

@Data
public class InvoiceDetailDto {

    private long idInvoiceDetail;
    private long item;
    private String descriptionInvoiceDetail;
    private double amountInvoiceDetail;

}

