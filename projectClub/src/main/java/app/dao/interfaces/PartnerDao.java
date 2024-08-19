package app.dao.interfaces;

import app.dto.PartnerDto;

public interface PartnerDao {
    public PartnerDto findByDocument(PartnerDto partnerDto) throws Exception;
    public void createPartner(PartnerDto partnerDto) throws Exception;
    public boolean existsByDocument(PartnerDto partnerDto) throws Exception;

}
