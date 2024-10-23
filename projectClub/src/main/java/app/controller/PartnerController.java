package app.controller;

import app.dto.MessageDto;
import app.dto.NewPartnerDto;
import app.dto.PartnerDto;
import app.dto.UpdatePartnerDto;
import app.entity.Subscription;
import app.service.PartnerService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/partnersAPI/v1/partner")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    public ResponseEntity<?> createPartner(@RequestBody NewPartnerDto newPartnerDto) {
        return ResponseEntity.accepted().body(partnerService.save(newPartnerDto));
    }

    @GetMapping
    public ResponseEntity<?> getPartner(@RequestParam(required = false) Long id, @RequestParam(required = false) Subscription typePartner) {
        if (id != null) {
            return ResponseEntity.ok(partnerService.findById(id));
        }
        if (typePartner != null) {
            return ResponseEntity.ok(partnerService.findByTypePartner(typePartner));
        }
        return ResponseEntity.ok(partnerService.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePartner(@PathVariable Long id, @RequestBody UpdatePartnerDto fields) {
        return ResponseEntity.ok(partnerService.updateById(id, fields));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.badRequest().body(new MessageDto(ex.getMessage(), 400, "Bad Request"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new MessageDto(ex.getMessage(), 400, "Bad Request"));
    }

}
