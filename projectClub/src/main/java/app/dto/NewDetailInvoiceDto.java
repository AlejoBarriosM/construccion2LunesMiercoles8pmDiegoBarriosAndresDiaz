package app.dto;

import lombok.Data;

@Data
public class NewDetailInvoiceDto {
    Long item;
    String descriptionInvoiceDetail;
    Double amountInvoiceDetail;
}
