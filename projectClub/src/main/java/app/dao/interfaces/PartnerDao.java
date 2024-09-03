package app.dao.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;

import java.util.Map;

public interface PartnerDao {
    PartnerDto findByIdUser(UserDto userDto) throws Exception;
    PartnerDto findById(Long id) throws Exception;
    void createPartner(PartnerDto partnerDto, UserDto userDto) throws Exception;
    int numberOfGuests(PartnerDto partnerDto) throws Exception;
    void increaseAmount(PartnerDto partnerDto,Double amount) throws Exception;
    void decreaseAmount(PartnerDto partnerDto,Double amount) throws Exception;
    void changeSubscription(PartnerDto partnerDto, String type) throws Exception;
    int cantTypeSubscription(String type) throws Exception;
    Map<Long, PartnerDto> pendingSubscriptions() throws Exception;
    boolean pendingInvoices(PartnerDto partnerDto) throws Exception;
    void unsubscribe(PartnerDto partnerDto) throws Exception;

}

