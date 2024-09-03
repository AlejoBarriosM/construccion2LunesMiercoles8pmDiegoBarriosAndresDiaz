package app.controller.validator;

public class UserValidator extends CommonsValidator{
	
	public void validUserName(String userName) throws Exception {
		super.isValidString("El nombre de usuario", userName);
	}
	public void validPassword(String password) throws Exception {
		super.isValidString("La contraseña de usuario", password);
	}

}

