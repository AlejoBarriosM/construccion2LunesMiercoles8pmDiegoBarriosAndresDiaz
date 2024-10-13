package app.dto.mapper;

import app.dto.InvoiceDto;
import app.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);
    InvoiceDto toInvoiceDto(Invoice invoice);
    Invoice toInvoice(InvoiceDto invoiceDto);

    List<InvoiceDto> toInvoiceDtoList(List<Invoice> invoices);
}
