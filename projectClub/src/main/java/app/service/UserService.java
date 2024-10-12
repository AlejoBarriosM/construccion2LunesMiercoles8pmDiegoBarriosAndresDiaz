package app.service;


import app.dto.UserDto;
import app.dto.mapper.UserMapper;
import app.entity.User;
import app.repository.PersonRepository;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    @Autowired
    public UserService(UserRepository userRepository, PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }


    public UserDto save(UserDto userDto) {
        User user = UserMapper.INSTANCE.toUser(userDto);
        if (personRepository.existsByDocumentPerson(user.getIdPerson().getDocumentPerson())) {
            throw new EntityExistsException("Persona ya existe");
        }
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new EntityExistsException("Usuario ya existe");
        }
        return UserMapper.INSTANCE.toUserDto(userRepository.save(user));
    }

    public UserDto login(String userName, String password) {
        if (!userRepository.existsByUserNameAndPasswordUser(userName, password)) {
            throw new EntityNotFoundException("Usuario o contraseña invalida");
        }
        return UserMapper.INSTANCE.toUserDto(userRepository.findByUserName(userName));
    }

    public void updateRoleUserByIdUser(UserDto userDto, String role) {
        User user = UserMapper.INSTANCE.toUser(userDto);
        if (!userRepository.existsById(user.getIdUser())) {
            throw new EntityExistsException("Usuario no existe");
        }
        userRepository.updateRoleUserByIdUser(user.getIdUser(), role);
    }

    public UserDto getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return UserMapper.INSTANCE.toUserDto(user);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityExistsException("Usuario no existe");
        }
        userRepository.deleteById(id);
    }
}
