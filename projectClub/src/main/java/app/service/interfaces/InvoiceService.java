package app.service.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;

import java.util.Map;

public interface InvoiceService {
    void createInovice(InvoiceDto invoiceDto, Map<Long, InvoiceDetailDto> items) throws Exception;
    Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices() throws Exception;
    Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoicesByPartner(PartnerDto partnerDto) throws Exception;
    boolean payInvoices(PartnerDto partnerDto) throws Exception;
    void validateInvoice(InvoiceDto invoiceDto, Map<Long, InvoiceDetailDto> items) throws Exception;
}
