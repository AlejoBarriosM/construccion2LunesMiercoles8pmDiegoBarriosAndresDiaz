package app.model;

public class Person {

    private long idPerson;
    private int documentPerson;
    private String namePerson;
    private int cellphonePerson;

    public Person() {
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    public int getDocumentPerson() {
        return documentPerson;
    }

    public void setDocumentPerson(int documentPerson) {
        this.documentPerson = documentPerson;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public int getCellphonePerson() {
        return cellphonePerson;
    }

    public void setCellphonePerson(int cellphonePerson) {
        this.cellphonePerson = cellphonePerson;
    }
}
