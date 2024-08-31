package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PersonDao;
import app.dao.interfaces.UserDao;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImplementation  implements UserDao {

    private PersonDao personDao = new PersonDaoImplementation();

    @Override
    public UserDto findByUserName(UserDto userDto) throws Exception {
        String query = "SELECT * FROM USER WHERE USERNAME = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, userDto.getNameUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setIdUser(resultSet.getLong("ID"));
            user.setUserName(resultSet.getString("USERNAME"));
            user.setPasswordUser(resultSet.getString("PASSWORD"));
            user.setRoleUser(resultSet.getString("ROLE"));
            user.setIdPerson(Helper.parse(personDao.findById(resultSet.getLong("PERSONID"))));
            resultSet.close();
            preparedStatement.close();
            return Helper.parse(user);
        }
        resultSet.close();
        preparedStatement.close();
        return null;

    }

    @Override
    public UserDto findById(Long id) throws Exception {
        String query = "SELECT * FROM USER WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setIdUser(resultSet.getLong("ID"));
            user.setUserName(resultSet.getString("USERNAME"));
            user.setPasswordUser(resultSet.getString("PASSWORD"));
            user.setRoleUser(resultSet.getString("ROLE"));
            user.setIdPerson(Helper.parse(personDao.findById(resultSet.getLong("PERSONID"))));
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
        String query = "INSERT INTO USER(USERNAME,PASSWORD,PERSONID,ROLE) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPasswordUser());
        preparedStatement.setLong(3,user.getIdPerson().getIdPerson());
        preparedStatement.setString(4, user.getRoleUser());
        preparedStatement.execute();
        preparedStatement.close();
    }

}
