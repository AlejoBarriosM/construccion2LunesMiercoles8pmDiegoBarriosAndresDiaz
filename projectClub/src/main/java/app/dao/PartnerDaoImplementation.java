package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PartnerDao;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.model.Partner;
import app.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PartnerDaoImplementation implements PartnerDao {

    @Override
    public PartnerDto findByDocument(PartnerDto partnerDto) throws Exception {
        String query = "SELECT * FROM PARTNER WHERE USERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, partnerDto.getIdUserPartner().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Partner partner = new Partner();
            User user = new User();
            user.setIdUser(resultSet.getLong("USERID"));
            partner.setIdUserPartner(user);
            partner.setIdPartner(resultSet.getLong("ID"));
            partner.setAmountPartner(resultSet.getDouble("AMOUNT"));
            partner.setTypePartner(resultSet.getString("TYPE"));
            partner.setCreationDatePartner(String.valueOf(resultSet.getTimestamp("CREATIONDATE")));
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
    public boolean existsByDocument(PartnerDto partnerDto) throws Exception {
        String query = "SELECT 1 FROM PARTNER WHERE USERID = ?";
        PreparedStatement preparedStatement = MYSQLConnection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, partnerDto.getIdUserPartner().getIdUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean exists = resultSet.next();
        resultSet.close();
        preparedStatement.close();
        return exists;
    }


}
