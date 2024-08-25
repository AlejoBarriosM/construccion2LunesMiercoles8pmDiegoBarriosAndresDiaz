package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PartnerDao;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Partner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PartnerDaoImplementation implements PartnerDao {

    @Override
    public PartnerDto findByDocument(UserDto userDto) throws Exception {
        String query = "SELECT * FROM PARTNER WHERE USERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, userDto.getIdUser());
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
        String query = "SELECT COUNT(ID) AS ID FROM PARTNER WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, partnerDto.getIdPartner());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("ID");
        }
        resultSet.close();
        preparedStatement.close();
        return 0;
    }

    @Override
    public void updateAmount(PartnerDto partnerDto, Double amount) throws Exception {
        String query = "UPDATE PARTNER SET AMOUNT = AMOUNT + ? WHERE ID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, partnerDto.getIdPartner());
        preparedStatement.setDouble(2, amount);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.close();
        preparedStatement.close();
    }


}
