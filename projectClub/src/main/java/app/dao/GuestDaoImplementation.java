package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.GuestDao;
import app.dao.interfaces.PartnerDao;
import app.dao.interfaces.UserDao;
import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Guest;
import app.model.Partner;
import app.model.Person;
import app.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GuestDaoImplementation implements GuestDao {

    private UserDao userDao = new UserDaoImplementation();
    private PartnerDao partnerDao = new PartnerDaoImplementation();

    @Override
    public GuestDto findByUserId(UserDto userDto) throws Exception {
        String query = "SELECT * FROM GUEST WHERE USERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, userDto.getIdUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Guest guest = new Guest();
            guest.setIdGuest(resultSet.getLong("ID"));
            guest.setUserIdGuest(Helper.parse(userDao.findById(resultSet.getLong("USERID"))));
            guest.setPartnerIdGuest(Helper.parse(partnerDao.findById(resultSet.getLong("PARTNERID"))));
            guest.setStatusGuest(resultSet.getString("STATUS"));
            resultSet.close();
            preparedStatement.close();
            return Helper.parse(guest);
        }
        resultSet.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public void createGuest(GuestDto guestDto, PartnerDto partnerDto, UserDto userDto) throws Exception {
        Guest user = Helper.parse(guestDto);
        String query = "INSERT INTO GUEST(USERID, PARTNERID, STATUS) VALUES (?,?,?)";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, userDto.getIdUser());
        preparedStatement.setLong(2, partnerDto.getIdPartner());
        preparedStatement.setString(3, user.getStatusGuest());
        preparedStatement.execute();
        preparedStatement.close();
    }
}
