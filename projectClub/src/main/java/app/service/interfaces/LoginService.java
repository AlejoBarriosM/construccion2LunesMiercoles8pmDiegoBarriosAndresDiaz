package app.service.interfaces;

import app.dto.UserDto;

public interface LoginService {
	void login(UserDto userDto) throws Exception;
	boolean logout();
}
