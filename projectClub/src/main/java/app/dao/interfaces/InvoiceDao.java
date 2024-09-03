package app.dao.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;

import java.util.Map;


public interface InvoiceDao {

   Long createInvoice(InvoiceDto invoice) throws Exception;
   void createDetailInvoice(InvoiceDetailDto invoiceDetailDto) throws Exception;
   Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices(Long partnerId) throws Exception;
   Map<Long, InvoiceDetailDto> showAllInvoicesDetails(InvoiceDto invoiceDto) throws Exception;
   void payInvoice(InvoiceDto invoice) throws Exception;

}

