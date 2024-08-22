package app.dao.interfaces;

import app.dto.InvoiceDto;


public interface InvoiceDao {

   public void createInvoice(InvoiceDto invoice) throws Exception;
   public void updateInvoice(InvoiceDto invoice) throws Exception;
   public void deleteInvoice(InvoiceDto invoice) throws Exception;
   public boolean payInvoice(InvoiceDto invoice) throws Exception;



}
