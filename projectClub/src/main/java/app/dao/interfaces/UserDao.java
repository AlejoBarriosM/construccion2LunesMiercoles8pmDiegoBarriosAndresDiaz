package app.dao.interfaces;

import app.dto.UserDto;

public interface UserDao {
    UserDto findByUserName(UserDto userDto) throws Exception;
    boolean existsByUserName(UserDto userDto) throws Exception;
    void createUser(UserDto userDto) throws Exception;
    UserDto findById(Long id) throws Exception;
}
