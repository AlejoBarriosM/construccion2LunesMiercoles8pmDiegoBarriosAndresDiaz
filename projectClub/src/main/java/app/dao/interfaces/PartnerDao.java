package app.dao.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;

public interface PartnerDao {
    public PartnerDto findByDocument(UserDto userDto) throws Exception;
    public void createPartner(PartnerDto partnerDto, UserDto userDto) throws Exception;
    public int numberOfGuests(PartnerDto partnerDto) throws Exception;
    public void updateAmount(PartnerDto partnerDto, Double amount) throws Exception;
}
