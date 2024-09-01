package app.dao.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;

import java.util.Map;


public interface InvoiceDao {

   public Long createInvoice(InvoiceDto invoice) throws Exception;
   public void createDetailInvoice(InvoiceDetailDto invoiceDetailDto) throws Exception;
   public Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices(Long partnerId) throws Exception;
   public Map<Long, InvoiceDetailDto> showAllInvoicesDetails(InvoiceDto invoiceDto) throws Exception;
   public InvoiceDto findIdInvoce(Long idInvoice) throws Exception;
   public boolean payInvoice(InvoiceDto invoice) throws Exception;



}
