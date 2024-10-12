package app.service;

import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.mapper.InvoiceDetailMapper;
import app.dto.mapper.InvoiceMapper;
import app.dto.mapper.PartnerMapper;
import app.entity.Invoice;
import app.entity.InvoiceDetail;
import app.entity.Partner;
import app.entity.Person;
import app.repository.InvoiceDetailRepository;
import app.repository.InvoiceRepository;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final PartnerRepository partnerRepository;
    private final PersonRepository personRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceDetailRepository invoiceDetailRepository, PartnerRepository partnerRepository, PersonRepository personRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.partnerRepository = partnerRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public void save(InvoiceDto invoiceDto, List<InvoiceDetailDto> invoiceDetailDtos) {

        Invoice invoice = InvoiceMapper.INSTANCE.toInvoice(invoiceDto);
        Partner partner = partnerRepository.findById(invoiceDto.getIdPartner().getIdPartner()).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        invoice.setIdPartner(partner);
        Person person = personRepository.findById(invoiceDto.getIdPerson().getIdPerson()).orElseThrow(()-> new EntityNotFoundException("Person not found"));
        invoice.setIdPerson(person);

        Invoice newInvoice = invoiceRepository.save(invoice);

        for (InvoiceDetailDto invoiceDetailDto : invoiceDetailDtos) {
            InvoiceDetail invoiceDetail = InvoiceDetailMapper.INSTANCE.toDetail(invoiceDetailDto);
            invoiceDetail.setIdInvoice(newInvoice);
            invoiceDetailRepository.save(invoiceDetail);
        }

    }

    public ResponseEntity<?> getInvoicesByPartner(long idPartner) {
        Partner partner = partnerRepository.findById(idPartner).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        List<Invoice> invoices = invoiceRepository.findByIdPartner(partner);
        List<InvoiceDto> invoiceDtos = InvoiceMapper.INSTANCE.toInvoiceDtoList(invoices);
        return ResponseEntity.ok(invoiceDtos);
    }
}
