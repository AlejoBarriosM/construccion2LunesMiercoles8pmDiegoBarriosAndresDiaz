package app.controller;

import app.dto.*;
import app.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/usersAPI/v1/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody NewUserDto newUserDto){
        return ResponseEntity.ok(userService.save(newUserDto));
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam(required = false) Long id, @RequestParam(required = false) String username) {
        if (id != null) {
            return ResponseEntity.ok(userService.getUserById(id));
        } else if (username != null) {
            return ResponseEntity.ok(userService.getUserByUserName(username));
        } else {
            return ResponseEntity.ok(userService.findAll());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto.getUsername(), loginDto.getPassword()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto fields) {
        try {
            return ResponseEntity.ok(userService.updateById(id, fields));
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado");
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
