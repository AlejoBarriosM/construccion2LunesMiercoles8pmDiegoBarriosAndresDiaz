package app.controller;

import app.dto.MessageDto;
import app.dto.NewPartnerDto;
import app.dto.PartnerDto;
import app.dto.UpdatePartnerDto;
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
    public ResponseEntity<?> getPartner(@RequestParam(required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(partnerService.findById(id));
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

}
