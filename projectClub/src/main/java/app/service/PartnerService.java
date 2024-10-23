package app.service;

import app.dto.*;
import app.dto.mapper.NewPartnerMapper;
import app.dto.mapper.NewPersonMapper;
import app.dto.mapper.NewUserMapper;
import app.dto.mapper.PartnerMapper;
import app.entity.Partner;
import app.entity.Role;
import app.entity.Subscription;
import app.entity.User;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<?> save(NewPartnerDto newPartnerDto) {
        PartnerDto partnerDto = NewPartnerMapper.INSTANCE.toPartnerDto(newPartnerDto);
        UserDto userDto = NewUserMapper.INSTANCE.toUserDto(newPartnerDto.getIdUserPartner());
        PersonDto personDto = NewPersonMapper.INSTANCE.toNewPerson(newPartnerDto.getIdUserPartner().getIdPerson());
        userDto.setIdPerson(personDto);
        partnerDto.setIdUserPartner(userDto);
        Partner partner = PartnerMapper.INSTANCE.toPartner(partnerDto);
        User user = partner.getIdUserPartner();
        if (personRepository.existsByDocumentPerson(user.getIdPerson().getDocumentPerson())) {
            throw new EntityExistsException("Persona ya existe");
        }
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new EntityExistsException("Usuario ya existe");
        }
        partner.setTypePartner(Subscription.REGULAR);
        user.setRoleUser(Role.PARTNER);
        partner.setAmountPartner(50000);
        partner.setIdUserPartner(user);
        return ResponseEntity.ok(PartnerMapper.INSTANCE.toPartnerDto(partnerRepository.save(partner)));
    }

    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(PartnerMapper.INSTANCE.toPartnerDto(partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El socio no existe"))));
    }

    public ResponseEntity<?> findAll() {
        List<Partner> partners = partnerRepository.findAll();
        List<PartnerDto> partnerDtos = new ArrayList<>();
        for (Partner partner : partners) {
            partnerDtos.add(PartnerMapper.INSTANCE.toPartnerDto(partner));
        }
        return ResponseEntity.ok(partnerDtos);
    }

    public ResponseEntity<?> findByTypePartner(Subscription typePartner) {
        List<Partner> partners = partnerRepository.findByTypePartner(typePartner);
        List<PartnerDto> partnerDtos = new ArrayList<>();
        for (Partner partner : partners) {
            partnerDtos.add(PartnerMapper.INSTANCE.toPartnerDto(partner));
        }
        return ResponseEntity.ok(partnerDtos);
    }

    public ResponseEntity<?> updateById(Long id, UpdatePartnerDto fields) {

        if (!partnerRepository.existsById(id)) throw new EntityExistsException("El socio no existe");
        Partner partner = partnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El socio no existe"));

        if (fields.getIncrease() != null && fields.getAmount() != null) {
            Double amount = fields.getAmount();
            updateAmount(fields, partner, amount);
        }

        if (fields.getTypePartner() != null) {
            if (fields.getTypePartner() == Subscription.VIP) partner.setTypePartner(Subscription.VIP);
            switch (partner.getTypePartner()) {
                case VIP:
                    break;
                case REGULAR:
                    if (partnerRepository.countByTypePartner(Subscription.VIP) >= 5) throw new EntityExistsException("No hay cupos VIP");
                    partner.setTypePartner(Subscription.PENDIENTE);
                    break;
                case PENDIENTE:
                    throw new IllegalArgumentException("La solicitud ya está radicada");
            }
        }
        return ResponseEntity.ok(PartnerMapper.INSTANCE.toPartnerDto(partnerRepository.save(partner)));
    }

    private static Boolean maxAmount(Partner partner, Double amount) {
        return (partner.getTypePartner() == Subscription.VIP && amount < 5000000.0) || ((partner.getTypePartner() == Subscription.REGULAR || partner.getTypePartner() == Subscription.PENDIENTE) && amount < 1000000.0);
    }

    private static void updateAmount(UpdatePartnerDto fields, Partner partner, Double amount) {
        if (fields.getIncrease()) {
            if (!maxAmount(partner, partner.getAmountPartner() + amount)) throw new IllegalArgumentException("Monto excedido");
            partner.setAmountPartner(partner.getAmountPartner() + amount);
        } else {
            if (partner.getAmountPartner() - amount < 0 ) throw new IllegalArgumentException("Monto insuficiente");
            partner.setAmountPartner(partner.getAmountPartner() - amount);
        }
    }
}
