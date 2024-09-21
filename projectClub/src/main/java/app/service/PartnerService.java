package app.service;

import app.dto.PartnerDto;
import app.dto.mapper.PartnerMapper;
import app.entity.Partner;
import app.entity.User;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, PersonRepository personRepository, UserRepository userRepository) {
        this.partnerRepository = partnerRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }


    public PartnerDto save(PartnerDto partnerDto) {
        Partner partner = PartnerMapper.INSTANCE.toPartner(partnerDto);
        partner.setAmountPartner(50000);
        partner.setTypePartner("Regular");
        User user = partner.getIdUserPartner();
        user.setRoleUser("partner");
        if (personRepository.existsByDocumentPerson(partner.getIdUserPartner().getIdPerson().getDocumentPerson())){
            throw new EntityExistsException("Persona ya existe");
        }
        if (userRepository.existsByUserName(partner.getIdUserPartner().getUserName())) {
            throw new EntityExistsException("Usuario ya existe");
        }
        return PartnerMapper.INSTANCE.toPartnerDto(partnerRepository.save(partner));
    }

    public void deleteById(Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new EntityExistsException("El socio no existe");
        }
        partnerRepository.deleteById(id);
    }

    @Transactional
    public void updateRoleById(Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new EntityExistsException("El socio no existe");
        }
        if (partnerRepository.countByTypePartner("VIP") < 5 ){
            throw new EntityExistsException("No hay cupos VIP");
        }

        partnerRepository.updateTypePartnerByIdPartner(id, "Pendiente");
    }

    @Transactional
    public PartnerDto increaseAmountPartnerById(Long id, Double amount) {
        if (!partnerRepository.existsById(id)) {
            throw new EntityExistsException("El socio no existe");
        }
        partnerRepository.increaseAmount(id, amount);
        return this.findById(id);
    }

    public PartnerDto findById(Long id) {
        return PartnerMapper.INSTANCE.toPartnerDto(partnerRepository.findById(id).orElse(null));
    }
}
