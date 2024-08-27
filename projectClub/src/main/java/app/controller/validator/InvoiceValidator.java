package app.controller.validator;


public class InvoiceValidator extends CommonsValidator {

    public void validDesciption (String description) throws Exception{
        super.isValidString("La descripción del producto: ", description);
    }

    public Double validItemNumber (String itemNumber) throws Exception{
        return super.isValidDouble("El número del item: ", itemNumber);
    }

    public long validItemValue (String itemValue) throws Exception{
        return super.isValidLong("El valor del item: ", itemValue);
    }

}
