package app.service;


import app.dto.*;
import app.dto.mapper.*;
import app.entity.Person;
import app.entity.User;
import app.repository.GuestRepository;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;
    private final PartnerRepository partnerRepository;
    private final GuestRepository guestRepository;

    @Autowired
    public UserService(UserRepository userRepository, PersonRepository personRepository, PersonService personService, PartnerRepository partnerRepository, GuestRepository guestRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.partnerRepository = partnerRepository;
        this.guestRepository = guestRepository;
    }

    public ResponseEntity<?> save(NewUserDto newUserDto) {
        UserDto userDto = NewUserMapper.INSTANCE.toUserDto(newUserDto);
        PersonDto personDto = NewPersonMapper.INSTANCE.toNewPerson(newUserDto.getIdPerson());
        userDto.setIdPerson(personDto);
        User user = UserMapper.INSTANCE.toUser(userDto);
        if (personRepository.existsByDocumentPerson(user.getIdPerson().getDocumentPerson())) {
            throw new EntityExistsException("Persona ya existe");
        }
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new EntityExistsException("Usuario ya existe");
        }
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(userRepository.save(user)));
    }

    public ResponseEntity<?> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no existe"));
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(user));
    }

    public ResponseEntity<?> getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(user));
    }

    public ResponseEntity<?> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.INSTANCE.toUserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    public ResponseEntity<?> login(String userName, String password) {
        if (!userRepository.existsByUserNameAndPasswordUser(userName, password)) {
            throw new EntityNotFoundException("Usuario o contraseña invalida");
        }
        User user = userRepository.findByUserName(userName);
        UserDto userDto = UserMapper.INSTANCE.toUserDto(user);
        return switch (userDto.getRoleUser()) {
            case ADMIN -> ResponseEntity.ok().header("Role", "ADMIN").body(userDto);
            case PARTNER -> ResponseEntity.ok().header("Role", "PARTNER").body(PartnerMapper.INSTANCE.toPartnerDto(partnerRepository.findByidUserPartner(user)));
            case GUEST -> ResponseEntity.ok().header("Role", "GUEST").body(GuestMapper.INSTANCE.toGuestDto(guestRepository.findByuserIdGuest(user)));
        };

    }

    public ResponseEntity<?> updateById(Long id, UpdateUserDto fields) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no existe"));
        Person person = personRepository.findById(user.getIdPerson().getIdPerson()).orElseThrow(() -> new EntityNotFoundException("Persona no existe"));
        if (fields.getUserName() != null && !userRepository.existsByUserName(fields.getUserName())) user.setUserName(fields.getUserName());
        else  throw new EntityExistsException("Usuario ya existe");

        if (fields.getPassword() != null) user.setPasswordUser(fields.getPassword());

        if (fields.getRoleUser() != null) user.setRoleUser(fields.getRoleUser());

        if (fields.getDocumentPerson() != null && !personRepository.existsByDocumentPerson(fields.getDocumentPerson())) person.setDocumentPerson(fields.getDocumentPerson());
        else throw new EntityExistsException("Persona ya existe");

        if (fields.getNamePerson() != null) person.setNamePerson(fields.getNamePerson());

        if (fields.getCellphonePerson() != null) person.setCellphonePerson(fields.getCellphonePerson());

        user.setIdPerson(person);
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(userRepository.save(user)));
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityExistsException("Usuario no existe");
        }
        personService.deleteById(userRepository.findById(id).get().getIdPerson().getIdPerson());
    }
}
