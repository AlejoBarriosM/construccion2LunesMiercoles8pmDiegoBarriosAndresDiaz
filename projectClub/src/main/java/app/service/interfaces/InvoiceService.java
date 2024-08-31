package app.service.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;

import java.util.Map;

public interface InvoiceService {
    public void createInovice (InvoiceDto invoiceDto, Map<Long, InvoiceDetailDto> items) throws Exception;
    public Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices () throws Exception;
}
