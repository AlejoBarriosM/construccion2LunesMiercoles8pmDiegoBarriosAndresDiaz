package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PersonDao;
import app.dto.PersonDto;
import app.helpers.Helper;
import app.model.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonDaoImplementation implements PersonDao {

    @Override
    public boolean existsByDocument(PersonDto personDto) throws Exception {
        String query = "SELECT 1 FROM PERSON WHERE DOCUMENT = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, personDto.getDocumentPerson());
        ResultSet resulSet = preparedStatement.executeQuery();
        boolean exists = resulSet.next();
        resulSet.close();
        preparedStatement.close();
        return exists;
    }

    @Override
    public void createPerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        String query = "INSERT INTO PERSON(NAME,DOCUMENT,CELLPHONE) VALUES (?,?,?) ";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, person.getNamePerson());
        preparedStatement.setLong(2,person.getDocumentPerson());
        preparedStatement.setLong(3, person.getCellphonePerson());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deletePerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        String query = "DELETE FROM PERSON WHERE DOCUMENT = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1,person.getDocumentPerson());
        preparedStatement.execute();
        preparedStatement.close();	}

    @Override
    public PersonDto findByDocument(PersonDto personDto) throws Exception {
        String query = "SELECT * FROM PERSON WHERE DOCUMENT = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, personDto.getDocumentPerson());
        ResultSet resulSet = preparedStatement.executeQuery();
        if (resulSet.next()) {
            Person person = new Person();
            person.setIdPerson(resulSet.getLong("ID"));
            person.setNamePerson(resulSet.getString("NAME"));
            person.setDocumentPerson(resulSet.getLong("DOCUMENT"));
            person.setCellphonePerson(resulSet.getLong("CELLPHONE"));
            resulSet.close();
            preparedStatement.close();
            return Helper.parse(person);
        }
        resulSet.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public PersonDto findById(Long id) throws Exception {
        String query = "SELECT * FROM PERSON WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resulSet = preparedStatement.executeQuery();
        if (resulSet.next()) {
            Person person = new Person();
            person.setIdPerson(resulSet.getLong("ID"));
            person.setNamePerson(resulSet.getString("NAME"));
            person.setDocumentPerson(resulSet.getLong("DOCUMENT"));
            person.setCellphonePerson(resulSet.getLong("CELLPHONE"));
            resulSet.close();
            preparedStatement.close();
            return Helper.parse(person);
        }
        resulSet.close();
        preparedStatement.close();
        return null;
    }

}
