package app.dao;

import app.dao.interfaces.UserDao;
import app.dto.UserDto;
import app.helpers.Helper;
import app.entity.User;
import app.repository.UserRepository;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImplementation  implements UserDao {

    private UserRepository userRepository;

    @Override
    public UserDto findByUserName(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        return Helper.parse(userRepository.findByUserName(user.getUserName()));
    }

    @Override
    public UserDto findById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        return Helper.parse(user);
    }

    @Override
    public boolean existsByUserName(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        return userRepository.existsByUserName(user.getUserName());
    }

    @Override
    public void createUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        userRepository.save(user);
    }

}

