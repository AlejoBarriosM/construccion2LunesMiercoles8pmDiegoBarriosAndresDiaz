package app.service.interfaces;

import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;

public interface PartnerService {
    public void createPartner(UserDto userDto, PersonDto personDto) throws Exception;
    public void increaseAmount(PartnerDto partnerDto, Double amount) throws Exception;
}
