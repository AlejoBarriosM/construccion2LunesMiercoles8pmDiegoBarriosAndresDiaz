package app.service.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.model.Invoice;

public interface InvoiceService {
    public void createInovice (InvoiceDto invoiceDto, InvoiceDetailDto invoiceDetailDto);
}
