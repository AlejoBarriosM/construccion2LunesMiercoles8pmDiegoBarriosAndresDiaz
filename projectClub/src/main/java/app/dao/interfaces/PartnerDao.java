package app.dao.interfaces;

import app.dto.PartnerDto;

public interface PartnerDao {
    public PartnerDto findByDocument(PartnerDto partnerDto) throws Exception;
    public PartnerDto createPartner(PartnerDto partnerDto) throws Exception;
}
