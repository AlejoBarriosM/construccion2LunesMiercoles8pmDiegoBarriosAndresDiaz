package app.controller;

import app.dto.NewInvoiceDto;
import app.dto.UpdateInvoiceDto;
import app.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/invoicesAPI/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody NewInvoiceDto newInvoiceDto) {
        return ResponseEntity.ok(invoiceService.save(newInvoiceDto));
    }

    @GetMapping
    public ResponseEntity<?> getInvoices(@RequestParam (required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(invoiceService.findById(id));
        }
        return ResponseEntity.ok(invoiceService.findAll());
    }

    @GetMapping("/partner/{idPartner}")
    public ResponseEntity<?> getInvoicesByPartner(@PathVariable long idPartner) {
        return ResponseEntity.ok(invoiceService.getInvoicesByPartner(idPartner));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable long id, @RequestBody UpdateInvoiceDto status) {
        return ResponseEntity.ok(invoiceService.update(id, status));
    }

}
