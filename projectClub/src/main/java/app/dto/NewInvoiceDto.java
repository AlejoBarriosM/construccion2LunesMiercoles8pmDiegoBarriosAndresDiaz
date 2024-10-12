package app.dto;


import lombok.Data;
import java.util.List;

@Data
public class NewInvoiceDto {
    InvoiceDto invoice;
    List<InvoiceDetailDto> detailInvoice;
}
