package app.dto.mapper;

import app.dto.InvoiceDetailDto;
import app.entity.InvoiceDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface InvoiceDetailMapper {
    InvoiceDetailMapper INSTANCE = Mappers.getMapper(InvoiceDetailMapper.class);
    InvoiceDetailDto toDetailDto(InvoiceDetail invoiceDetail);
    InvoiceDetail toDetail(InvoiceDetailDto invoiceDetailDto);

    List<InvoiceDetailDto> toInvoiceDetailDtoList(List<InvoiceDetail> allByIdInvoice);
}
