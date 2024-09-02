package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PartnerDao;
import app.dao.interfaces.UserDao;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Partner;
import app.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PartnerDaoImplementation implements PartnerDao {

    private final UserDao userDao = new UserDaoImplementation();

    @Override
    public PartnerDto findByIdUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        String query = "SELECT * FROM PARTNER WHERE USERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, user.getIdUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Partner partner = new Partner();
            partner.setIdPartner(resultSet.getLong("ID"));
            partner.setAmountPartner(resultSet.getDouble("AMOUNT"));
            partner.setTypePartner(resultSet.getString("TYPE"));
            partner.setCreationDatePartner(String.valueOf(resultSet.getTimestamp("CREATIONDATE")));
            partner.setIdUserPartner(Helper.parse(userDto));
            resultSet.close();
            preparedStatement.close();
            return Helper.parse(partner);
        }
        resultSet.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public PartnerDto findById(Long id) throws Exception {
        String query = "SELECT * FROM PARTNER WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Partner partner = new Partner();
            partner.setIdPartner(resultSet.getLong("ID"));
            partner.setAmountPartner(resultSet.getDouble("AMOUNT"));
            partner.setTypePartner(resultSet.getString("TYPE"));
            partner.setCreationDatePartner(String.valueOf(resultSet.getTimestamp("CREATIONDATE")));
            partner.setIdUserPartner(Helper.parse(userDao.findById(resultSet.getLong("USERID"))));
            resultSet.close();
            preparedStatement.close();
            return Helper.parse(partner);
        }
        resultSet.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public void createPartner(PartnerDto partnerDto, UserDto userDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        String query = "INSERT INTO PARTNER(USERID, AMOUNT, TYPE) VALUES (?,?,?)";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1,userDto.getIdUser());
        preparedStatement.setDouble(2,partner.getAmountPartner());
        preparedStatement.setString(3,partner.getTypePartner());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public int numberOfGuests(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        String query = "SELECT COUNT(ID) FROM GUEST WHERE PARTNERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, partner.getIdPartner());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            resultSet.close();
            preparedStatement.close();
            return count;
        }
        resultSet.close();
        preparedStatement.close();
        return 0;
    }

    @Override
    public void increaseAmount(PartnerDto partnerDto, Double amount) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        String query = "UPDATE PARTNER SET AMOUNT = AMOUNT + ? WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setDouble(1, amount);
        preparedStatement.setLong(2, partner.getIdPartner());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void decreaseAmount(PartnerDto partnerDto, Double amount) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        String query = "UPDATE PARTNER SET AMOUNT = AMOUNT - ? WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setDouble(1, amount);
        preparedStatement.setLong(2, partner.getIdPartner());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void changeSubscription(PartnerDto partnerDto, String type) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        String query = "UPDATE PARTNER SET TYPE = ? WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, type);
        preparedStatement.setLong(2, partner.getIdPartner());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public int cantTypeSubscription(String type) throws Exception {
        String query = "SELECT COUNT(ID) FROM PARTNER WHERE TYPE = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, type);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int cont = resultSet.getInt(1);
            resultSet.close();
            preparedStatement.close();
            return cont;
        }
        resultSet.close();
        preparedStatement.close();
        return 0;
    }

    @Override
    public Map<Long, PartnerDto> pendingSubscriptions() throws Exception {
        String query = "SELECT * FROM VW_VIPREQUESTS";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        Map<Long, PartnerDto> partnersDto = new HashMap<>();
        while (resultSet.next()) {
            Partner partner = new Partner();
            partner.setIdPartner(resultSet.getLong("ID"));
            partner.setIdUserPartner(Helper.parse(userDao.findById(resultSet.getLong("USERID"))));
            partner.setAmountPartner(resultSet.getDouble("AMOUNT_INVOICE"));
            partner.setTypePartner(resultSet.getString("TYPE"));
            partner.setCreationDatePartner(String.valueOf(resultSet.getTimestamp("CREATIONDATE")));
            partnersDto.put(partner.getIdPartner(), Helper.parse(partner));
        }
        resultSet.close();
        preparedStatement.close();
        return partnersDto;
    }

    @Override
    public boolean pendingInvoices(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        String query = "SELECT CASE WHEN COUNT(ID) <> 0 THEN true ELSE false END AS invoice_count FROM INVOICE WHERE PARTNERID = ? AND STATUS = 'Debe'";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, partner.getIdPartner());
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
    public void unsubscribe(PartnerDto partnerDto) {
        try {
            Partner partner = Helper.parse(partnerDto);
            String query = "DELETE FROM PERSON WHERE ID = ?";
            PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, partner.getIdUserPartner().getIdPerson().getIdPerson());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
