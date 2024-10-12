package app.controller;

import app.dto.LoginDto;
import app.dto.MessageDto;
import app.dto.UserDto;
import app.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/usersAPI/v1/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        MessageDto messageDto = new MessageDto(userService.save(userDto), 201);
        return new ResponseEntity<>(messageDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName) {
        MessageDto messageDto = new MessageDto(userService.getUserByUserName(userName), 200);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        MessageDto messageDto = new MessageDto(userService.login(loginDto.getUsername(), loginDto.getPassword()), 200);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setIdUser(id);
        MessageDto updateDetails = new MessageDto(userService.save(userDto), 202);
        return new ResponseEntity<>(updateDetails, HttpStatus.OK);
    }

    @PatchMapping("/changeRole/{id}/{role}")
    public ResponseEntity<MessageDto> updateRole(@PathVariable Long id, @PathVariable String role) {
        UserDto userDto = new UserDto();
        userDto.setIdUser(id);
        userService.updateRoleUserByIdUser(userDto,role);
        MessageDto updateDetails = new MessageDto("Usuario actualizado", 202);
        return new ResponseEntity<>(updateDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        MessageDto deleteDetail = new MessageDto("Usuario eliminado", 200);
        return new ResponseEntity<>(deleteDetail, HttpStatus.OK);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException ex) {
        MessageDto errorDetails = new MessageDto(ex.getMessage(), 409);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        MessageDto errorDetails = new MessageDto(ex.getMessage(), 401);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
