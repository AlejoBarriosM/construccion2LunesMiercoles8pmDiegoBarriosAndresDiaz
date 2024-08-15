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
        preparedStatement.setInt(2,person.getDocumentPerson());
        preparedStatement.setInt(3, person.getCellphonePerson());
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
        String query = "SELECT ID,NAME,DOCUMENT,AGE FROM PERSON WHERE DOCUMENT = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, personDto.getDocumentPerson());
        ResultSet resulSet = preparedStatement.executeQuery();
        if (resulSet.next()) {
            Person person = new Person();
            person.setIdPerson(resulSet.getLong("ID"));
            person.setNamePerson(resulSet.getString("NAME"));
            person.setDocumentPerson(resulSet.getInt("DOCUMENT"));
            person.setCellphonePerson(resulSet.getInt("CELLPHONE"));
            resulSet.close();
            preparedStatement.close();
            return Helper.parse(person);
        }
        resulSet.close();
        preparedStatement.close();
        return null;
    }

}
