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
import app.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GuestDaoImplementation implements GuestDao {

    private final UserDao userDao = new UserDaoImplementation();
    private final PartnerDao partnerDao = new PartnerDaoImplementation();

    @Override
    public GuestDto findByUserId(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        String query = "SELECT * FROM GUEST WHERE USERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, user.getIdUser());
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
        Guest guest = Helper.parse(guestDto);
        Partner partner = Helper.parse(partnerDto);
        User user = Helper.parse(userDto);
        String query = "INSERT INTO GUEST(USERID, PARTNERID, STATUS) VALUES (?,?,?)";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, user.getIdUser());
        preparedStatement.setLong(2, partner.getIdPartner());
        preparedStatement.setString(3, guest.getStatusGuest());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public boolean pendingInvoices(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        String query = "SELECT CASE WHEN COUNT(ID) <> 0 THEN true ELSE false END AS invoice_count FROM INVOICE WHERE PERSONID = ? AND STATUS = 'Debe'";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, guest.getUserIdGuest().getIdPerson().getIdPerson());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            boolean count = resultSet.getBoolean("invoice_count");
            resultSet.close();
            preparedStatement.close();
            return count;
        }
        resultSet.close();
        preparedStatement.close();
        return false;
    }

    @Override
    public void upgradeToPartner(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        String query = "UPDATE GUEST SET STATUS = 'Inactivo' WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, guest.getIdGuest());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void changeType(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        String query = "UPDATE USER SET ROLE = 'socio' WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, guest.getUserIdGuest().getIdUser());
        preparedStatement.execute();
        preparedStatement.close();
    }


}
