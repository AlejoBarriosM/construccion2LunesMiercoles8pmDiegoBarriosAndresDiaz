package app.service.interfaces;


import app.dto.PartnerDto;

import java.util.Map;

public interface AdminService {
    void approvalVIP(PartnerDto partnerDto, Boolean approve) throws Exception;
    Map<Long, PartnerDto> pendingSubscriptions() throws Exception;
}
