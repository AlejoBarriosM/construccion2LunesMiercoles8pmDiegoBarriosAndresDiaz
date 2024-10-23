package app.dto;

import app.entity.InvoiceStatus;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceDto {

    private Long idInvoice;
    private PersonDto idPerson;
    private PartnerDto idPartner;
    private String creationDateInvoice;
    private double amountInvoice;
    private InvoiceStatus statusInvoice;
    private List<InvoiceDetailDto> detailsInvoice;

}

