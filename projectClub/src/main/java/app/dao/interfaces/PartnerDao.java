package app.dao.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;

public interface PartnerDao {
    public PartnerDto findByDocument(PartnerDto partnerDto) throws Exception;
    public void createPartner(PartnerDto partnerDto, UserDto userDto) throws Exception;
    public boolean existsByDocument(PartnerDto partnerDto) throws Exception;

}
