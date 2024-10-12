package app.controller;


import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.NewInvoiceDto;
import app.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoicesAPI/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody NewInvoiceDto newInvoiceDto) {
        InvoiceDto invoiceDto = newInvoiceDto.getInvoice();
        List<InvoiceDetailDto> invoiceDetailDtos = newInvoiceDto.getDetailInvoice();
        invoiceService.save(invoiceDto, invoiceDetailDtos);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/partner/{idPartner}")
    public ResponseEntity<?> getInvoicesByPartner(@PathVariable long idPartner) {
        return ResponseEntity.ok(invoiceService.getInvoicesByPartner(idPartner));
    }

}
