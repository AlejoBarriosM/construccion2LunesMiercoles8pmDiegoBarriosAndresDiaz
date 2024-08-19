package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.UserDao;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Person;
import app.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImplementation  implements UserDao {

    @Override
    public UserDto findByUserName(UserDto userDto) throws Exception {
        String query = "SELECT ID,USERNAME,PASSWORD,ROLE,PERSONID FROM USER WHERE USERNAME = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, userDto.getNameUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setIdUser(resultSet.getLong("ID"));
            user.setUserName(resultSet.getString("USERNAME"));
            user.setPasswordUser(resultSet.getString("PASSWORD"));
            user.setRoleUser(resultSet.getString("ROLE"));
            Person person = new Person();
            person.setIdPerson(resultSet.getLong("PERSONID"));
            user.setIdPerson(person);
            resultSet.close();
            preparedStatement.close();
            return Helper.parse(user);
        }
        resultSet.close();
        preparedStatement.close();
        return null;

    }

    @Override
    public boolean existsByUserName(UserDto userDto) throws Exception {
        String query = "SELECT 1 FROM USER WHERE USERNAME = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, userDto.getNameUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean exists = resultSet.next();
        resultSet.close();
        preparedStatement.close();
        return exists;
    }

    @Override
    public void createUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        String query = "INSERT INTO USER(USERNAME,PASSWORD,PERSONID,ROLE) VALUES (?,?,?,?) ";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPasswordUser());
        preparedStatement.setLong(3,user.getIdPerson().getIdPerson());
        preparedStatement.setString(4, user.getRoleUser());
        preparedStatement.execute();
        preparedStatement.close();
    }

}
