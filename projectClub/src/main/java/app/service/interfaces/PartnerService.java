package app.service.interfaces;

import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;

public interface PartnerService {
    public void createPartner(PartnerDto partnerDto, UserDto userDto, PersonDto personDto) throws Exception;
}
