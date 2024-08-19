package app.controller.validator;

public class PartnerValidator extends CommonsValidator {

    public void validAmount(String amount) throws Exception{
        super.isValidDouble("Los fondos ingresados", amount);
    }

}
