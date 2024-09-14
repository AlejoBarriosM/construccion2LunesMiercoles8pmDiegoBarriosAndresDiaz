package app.dao;

import app.config.MYSQLConnection;
import app.dao.interfaces.PartnerDao;
import app.dao.interfaces.UserDao;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.entity.Partner;
import app.entity.User;
import app.repository.GuestRepository;
import app.repository.InvoiceRepository;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PartnerDaoImplementation implements PartnerDao {

    private final UserDao userDao = new UserDaoImplementation();

    private PartnerRepository partnerRepository;
    private GuestRepository guestRepository;
    private InvoiceRepository invoiceRepository;
    private PersonRepository personRepository;

    @Override
    public PartnerDto findByIdUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        return Helper.parse(partnerRepository.findByIdUserPartner(user));
    }

    @Override
    public PartnerDto findById(Long id) throws Exception {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new Exception("Partner no encontrado"));
        return Helper.parse(partner);
    }

    @Override
    public void createPartner(PartnerDto partnerDto, UserDto userDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        partnerRepository.save(partner);
    }

    @Override
    public int numberOfGuests(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        return guestRepository.countGuestByPartnerIdGuest(partner);
    }

    @Override
    public void increaseAmount(PartnerDto partnerDto, Double amount) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        partnerRepository.incrementAmount(partner, amount);
    }

    @Override
    public void decreaseAmount(PartnerDto partnerDto, Double amount) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        partnerRepository.decrementAmount(partner, amount);
    }

    @Override
    public void changeSubscription(PartnerDto partnerDto, String type) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        partnerRepository.updateTypePartnerByIdPartner(partner, type);
    }

    @Override
    public int cantTypeSubscription(String type) throws Exception {
        return partnerRepository.countByTypePartner(type);
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
        return invoiceRepository.existsByIdPartnerAndStatusInvoiceIs(partner, "Debe");
    }

    @Override
    public void unsubscribe(PartnerDto partnerDto) {
        Partner partner = Helper.parse(partnerDto);
        personRepository.delete(partner.getIdUserPartner().getIdPerson());
    }


}

