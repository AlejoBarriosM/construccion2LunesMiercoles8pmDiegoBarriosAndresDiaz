package app.controller;

import app.dto.GuestDto;
import app.dto.MessageDto;
import app.service.GuestService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createGuest(@RequestBody GuestDto guestDto) {
        MessageDto messageDto = new MessageDto(guestService.save(guestDto), 201);
        return ResponseEntity.status(201).body(messageDto);
    }

    @GetMapping
    public ResponseEntity<?> getGuests() {
        MessageDto messageDto = new MessageDto(guestService.findAll(), 200);
        return ResponseEntity.status(200).body(messageDto);
    }

    @GetMapping("/partner/{id}")
    public ResponseEntity<?> getGuestsByPartner(@PathVariable Long id) {
        MessageDto messageDto = new MessageDto(guestService.findByPartnerId(id), 200);
        return ResponseEntity.status(200).body(messageDto);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<MessageDto> handleEntityExistsException(EntityExistsException ex) {
        MessageDto errorDetails = new MessageDto(ex.getMessage(), 409);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

}
