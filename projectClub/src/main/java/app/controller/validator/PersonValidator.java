package app.controller.validator;

public class PersonValidator extends CommonsValidator {
	
	public void validName(String name) throws Exception{
		super.isValidString("El nombre de la persona", name);
	}
	
	public long validDocument(String document) throws Exception{
		return super.isValidLong("La cédula de la persona", document);
	}
	
	public long validCellphone(String cellphone) throws Exception{
		if (cellphone.length() <= 10){
			return super.isValidLong("El celular de la persona", cellphone);
		} else {
			throw new Exception("El celular de la persona no debe tener mas de 10 caracteres");
		}
	}


}
