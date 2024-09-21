package app.controller;

import app.dto.MessageDto;
import app.dto.PartnerDto;
import app.service.PartnerService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partnersAPI/v1/partner")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    public ResponseEntity<?> createPartner(@RequestBody PartnerDto partnerDto) {
        return ResponseEntity.ok(partnerService.save(partnerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePartner(@PathVariable Long id) {
        partnerService.deleteById(id);
        MessageDto deleteDetail = new MessageDto("Socio eliminado", "200");
        return ResponseEntity.ok(deleteDetail);
    }

    @PatchMapping("/changeSubscription/{id}")
    public ResponseEntity<?> changeRole(@PathVariable Long id){
        partnerService.updateRoleById(id);
        return ResponseEntity.ok(new MessageDto("La solicitud ha sido generada", "200"));
    }

    @PatchMapping("/amount/{id}/{amount}")
    public ResponseEntity<?> changeAmount(@PathVariable Long id, @PathVariable Double amount){
        return ResponseEntity.ok(new MessageDto((String.valueOf(partnerService.increaseAmountPartnerById(id, amount).getAmountPartner())), "200"));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<MessageDto> handleEntityExistsException(EntityExistsException ex) {
        MessageDto errorDetails = new MessageDto(ex.getMessage(), "409");
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }


}
