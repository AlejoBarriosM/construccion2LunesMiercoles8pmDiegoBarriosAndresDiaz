package app.service.interfaces;

import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;

public interface PartnerService {
    void createPartner(UserDto userDto, PersonDto personDto) throws Exception;
    void increaseAmount(PartnerDto partnerDto, Double amount) throws Exception;
    void changeSubscription(PartnerDto partnerDto) throws Exception;
    boolean pendingInvoices(PartnerDto partnerDto) throws Exception;
    void unsubscribe(PartnerDto partnerDto) throws Exception;
}
