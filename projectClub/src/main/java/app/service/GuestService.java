package app.service;

import app.dto.*;
import app.dto.mapper.GuestMapper;
import app.dto.mapper.NewGuestMapper;
import app.dto.mapper.NewPersonMapper;
import app.dto.mapper.NewUserMapper;
import app.entity.*;
import app.repository.GuestRepository;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository, PersonRepository personRepository, UserRepository userRepository, PartnerRepository partnerRepository) {
        this.guestRepository = guestRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.partnerRepository = partnerRepository;
    }

    public ResponseEntity<?> save(NewGuestDto newGuestDto) {
        GuestDto guestDto = NewGuestMapper.INSTANCE.toGuestDto(newGuestDto);
        UserDto userDto = NewUserMapper.INSTANCE.toUserDto(newGuestDto.getUserIdGuest());
        PersonDto personDto = NewPersonMapper.INSTANCE.toNewPerson(newGuestDto.getUserIdGuest().getIdPerson());
        userDto.setIdPerson(personDto);
        userDto.setRoleUser(Role.GUEST);
        guestDto.setUserIdGuest(userDto);
        guestDto.setStatusGuest(GuestStatus.ACTIVE);

        Guest guest = GuestMapper.INSTANCE.toGuest(guestDto);
        Partner partner = partnerRepository.findById(newGuestDto.getIdPartner()).orElseThrow(() -> new EntityExistsException("El socio no existe"));
        guest.setPartnerIdGuest(partner);
        if (guestRepository.countGuestByPartnerIdGuest(partner) >= 3 && (partner.getTypePartner().equals(Subscription.REGULAR) || partner.getTypePartner().equals(Subscription.PENDIENTE))) {
            throw new EntityExistsException("El socio ya tiene 3 invitados");
        }
        if (personRepository.existsByDocumentPerson(guest.getUserIdGuest().getIdPerson().getDocumentPerson())) {
            throw new EntityExistsException("Persona ya existe");
        }
        if (userRepository.existsByUserName(guest.getUserIdGuest().getUserName())) {
            throw new EntityExistsException("Usuario ya existe");
        }
        return ResponseEntity.ok(GuestMapper.INSTANCE.toGuestDto(guestRepository.save(guest)));
    }

    public ResponseEntity<?> findAll() {
        List<Guest> guests = guestRepository.findAll();
        List<GuestDto> guestDtos = new ArrayList<>();
        for (Guest guest : guests) {
            guestDtos.add(GuestMapper.INSTANCE.toGuestDto(guest));
        }
        return ResponseEntity.ok(guestDtos);
    }

    public ResponseEntity<?> findByPartnerId(Long id) {
        List<Guest> guests = guestRepository.findByPartnerIdGuest_IdPartner(id);
        List<GuestDto> guestDtos = new ArrayList<>();
        for (Guest guest : guests) {
            guestDtos.add(GuestMapper.INSTANCE.toGuestDto(guest));
        }
        return ResponseEntity.ok(guestDtos);
    }

    public ResponseEntity<?> findById(Long id) {
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new EntityExistsException("Invitado no encontrado"));
        return ResponseEntity.ok(GuestMapper.INSTANCE.toGuestDto(guest));
    }

    public ResponseEntity<?> updateById(Long id, UpdateGuestDto fields) {
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new EntityExistsException("Invitado no encontrado"));
        if (fields.getStatus() != null) {
            if (fields.getStatus().equals(GuestStatus.ACTIVE)) guest.setStatusGuest(GuestStatus.ACTIVE);
            else if (fields.getStatus().equals(GuestStatus.INACTIVE)) guest.setStatusGuest(GuestStatus.INACTIVE);
        }
        return ResponseEntity.ok(GuestMapper.INSTANCE.toGuestDto(guestRepository.save(guest)));
    }
}
