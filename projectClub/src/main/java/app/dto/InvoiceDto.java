package app.dto;

import lombok.Data;

@Data
public class InvoiceDto {

    private long idInvoice;
    private PersonDto idPerson;
    private PartnerDto idPartner;
    private String creationDateInvoice;
    private double amountInvoice;
    private String statusInvoice;

}

