package app.dao.interfaces;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;

import java.util.Map;


public interface InvoiceDao {

   public Long createInvoice(InvoiceDto invoice) throws Exception;
   public void createDetailInvoice(InvoiceDetailDto invoiceDetailDto) throws Exception;
   public Map<InvoiceDto, Map<Long, InvoiceDetailDto>> showAllInvoices () throws Exception;
   public Map<Long, InvoiceDetailDto> showAllInvoicesDetails (InvoiceDto invoiceDto) throws Exception;
   public InvoiceDto findIdInvoce (Long idInvoice) throws Exception;
   public void updateInvoice(InvoiceDto invoice) throws Exception;
   public void deleteInvoice(InvoiceDto invoice) throws Exception;
   public boolean payInvoice(InvoiceDto invoice) throws Exception;



}
