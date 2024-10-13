package app.dto;


import app.entity.InvoiceStatus;
import lombok.Data;
import java.util.List;

@Data
public class NewInvoiceDto {

    Long idPerson;
    Long idPartner;
    Double amountInvoice;
    InvoiceStatus statusInvoice;
    List<NewDetailInvoiceDto> detailInvoice;
}
