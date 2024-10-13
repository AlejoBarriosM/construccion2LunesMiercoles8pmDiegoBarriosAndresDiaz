package app.dto;

import app.entity.InvoiceStatus;
import lombok.Data;

@Data
public class UpdateInvoiceDto {
    InvoiceStatus status;
}
