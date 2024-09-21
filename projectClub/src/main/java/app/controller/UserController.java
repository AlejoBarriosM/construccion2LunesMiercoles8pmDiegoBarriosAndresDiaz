package app.controller;

import app.controller.validator.PersonValidator;
import app.controller.validator.UserValidator;
import app.dto.LoginDto;
import app.dto.MessageDto;
import app.dto.UserDto;
import app.service.UserService;
import jakarta.persistence.EntityExistsException;
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
        return ResponseEntity.ok(userService.save(userDto) + "Usuario creado");
//        MessageDto creationDetails = new MessageDto("Usuario creado", "202");
//        return new ResponseEntity<>(creationDetails, HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userName) {
        return ResponseEntity.ok(userService.getUserByUserName(userName));
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDto> login(@RequestBody LoginDto loginDto) {
        userService.login(loginDto.getUsername(), loginDto.getPassword());
        MessageDto loginDetails = new MessageDto("Usuario valido", "200");
        return new ResponseEntity<>(loginDetails, HttpStatus.OK);
    }

    @PatchMapping("/changeRole/{id}/{role}")
    public ResponseEntity<MessageDto> updateRole(@PathVariable Long id, @PathVariable String role) {
        UserDto userDto = new UserDto();
        userDto.setIdUser(id);
        userService.updateRoleUserByIdUser(userDto,role);
        MessageDto updateDetails = new MessageDto("Usuario actualizado", "202");
        return new ResponseEntity<>(updateDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        MessageDto deleteDetail = new MessageDto("Usuario eliminado", "200");
        return new ResponseEntity<>(deleteDetail, HttpStatus.OK);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<MessageDto> handleEntityExistsException(EntityExistsException ex) {
        MessageDto errorDetails = new MessageDto(ex.getMessage(), "409");
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
