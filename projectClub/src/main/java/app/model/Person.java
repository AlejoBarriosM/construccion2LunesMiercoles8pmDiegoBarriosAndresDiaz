package app.model;

public class Person {

    private long idPerson;
    private Long documentPerson;
    private String namePerson;
    private Long cellphonePerson;

    public Person() {
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getDocumentPerson() {
        return documentPerson;
    }

    public void setDocumentPerson(Long documentPerson) {
        this.documentPerson = documentPerson;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public Long getCellphonePerson() {
        return cellphonePerson;
    }

    public void setCellphonePerson(Long cellphonePerson) {
        this.cellphonePerson = cellphonePerson;
    }
}

