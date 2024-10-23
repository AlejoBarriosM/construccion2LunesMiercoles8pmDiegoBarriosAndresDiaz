package app.service;

import app.dto.*;
import app.dto.mapper.*;
import app.entity.*;
import app.repository.InvoiceDetailRepository;
import app.repository.InvoiceRepository;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
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
    public ResponseEntity<?> save(NewInvoiceDto newInvoiceDto) {
        Invoice invoice = new Invoice();
        Person person = personRepository.findById(newInvoiceDto.getIdPerson()).orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));
        invoice.setIdPerson(person);
        Partner partner = partnerRepository.findById(newInvoiceDto.getIdPartner()).orElseThrow(() -> new EntityNotFoundException("Partner no encontrado"));
        invoice.setIdPartner(partner);
        invoice.setAmountInvoice(newInvoiceDto.getAmountInvoice());
        invoice.setStatusInvoice(newInvoiceDto.getStatusInvoice());

        Invoice newInvoice = invoiceRepository.save(invoice);

        for (NewDetailInvoiceDto newDetailInvoiceDto : newInvoiceDto.getDetailInvoice()) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setAmountInvoiceDetail(newDetailInvoiceDto.getAmountInvoiceDetail());
            invoiceDetail.setItem(newDetailInvoiceDto.getItem());
            invoiceDetail.setDescriptionInvoiceDetail(newDetailInvoiceDto.getDescriptionInvoiceDetail());
            invoiceDetail.setIdInvoice(newInvoice);
            invoiceDetailRepository.save(invoiceDetail);
        }
        return ResponseEntity.ok(InvoiceMapper.INSTANCE.toInvoiceDto(newInvoice));
    }

    public ResponseEntity<?> getInvoicesByPartner(long idPartner) {
        Partner partner = partnerRepository.findById(idPartner).orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));
        List<Invoice> invoices = invoiceRepository.findByIdPartner(partner);
        List<InvoiceDto> invoiceDtos = InvoiceMapper.INSTANCE.toInvoiceDtoList(invoices);
        return ResponseEntity.ok(invoiceDtos);
    }

    public ResponseEntity<?> findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));
        InvoiceDto invoiceDto = InvoiceMapper.INSTANCE.toInvoiceDto(invoice);
        invoiceDto.setDetailsInvoice(InvoiceDetailMapper.INSTANCE.toInvoiceDetailDtoList(invoiceDetailRepository.findAllByIdInvoice(invoice)));
        return ResponseEntity.ok(invoiceDto);
    }

    public ResponseEntity<?> findAll() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return ResponseEntity.ok(InvoiceMapper.INSTANCE.toInvoiceDtoList(invoices));
    }

    public ResponseEntity<?> update(long id, UpdateInvoiceDto status) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));
        invoice.setStatusInvoice(status.getStatus());
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return ResponseEntity.ok(InvoiceMapper.INSTANCE.toInvoiceDto(updatedInvoice));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.badRequest().body(new MessageDto(ex.getMessage(), 400, "Bad Request"));
    }

}
