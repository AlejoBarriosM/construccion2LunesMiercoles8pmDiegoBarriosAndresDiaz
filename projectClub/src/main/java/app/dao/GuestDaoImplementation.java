package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.GuestDao;
import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Guest;

import java.sql.PreparedStatement;

public class GuestDaoImplementation implements GuestDao {

    @Override
    public GuestDto findByUserName(UserDto userDto) throws Exception {
        return null;
    }

    @Override
    public boolean existsByUserName(GuestDto guestDto) throws Exception {
        return false;
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
