package app.dto;

import app.entity.InvoiceStatus;
import lombok.Data;

@Data
public class InvoiceDto {

    private long idInvoice;
    private PersonDto idPerson;
    private PartnerDto idPartner;
    private String creationDateInvoice;
    private double amountInvoice;
    private InvoiceStatus statusInvoice;

}

