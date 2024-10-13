package app.controller;

import app.dto.MessageDto;
import app.dto.NewGuestDto;
import app.dto.UpdateGuestDto;
import app.service.GuestService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/guestsAPI/v1/guest")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public ResponseEntity<?> createGuest(@RequestBody NewGuestDto newGuestDto) {
        return ResponseEntity.accepted().body(guestService.save(newGuestDto));
    }

    @GetMapping
    public ResponseEntity<?> getGuests(@RequestParam(required = false) Long id) {
        if (id != null) {
            return ResponseEntity.ok(guestService.findById(id));
        }
        return ResponseEntity.ok(guestService.findAll());
    }

    @GetMapping("/partner/{id}")
    public ResponseEntity<?> getGuestsByPartner(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findByPartnerId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateGuest(@PathVariable Long id, @RequestBody UpdateGuestDto fields){
        return ResponseEntity.ok(guestService.updateById(id, fields));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.badRequest().body(new MessageDto(ex.getMessage()));
    }

}
