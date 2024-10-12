package app.service;

import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.mapper.GuestMapper;
import app.entity.Guest;
import app.entity.Partner;
import app.entity.User;
import app.repository.GuestRepository;
import app.repository.PartnerRepository;
import app.repository.PersonRepository;
import app.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public GuestDto save(GuestDto guestDto) {
        Guest guest = GuestMapper.INSTANCE.toGuest(guestDto);
        Partner partner = partnerRepository.findById(guest.getPartnerIdGuest().getIdPartner()).orElseThrow(() -> new EntityExistsException("El socio no existe"));
        guest.setPartnerIdGuest(partner);
        if (guestRepository.countGuestByPartnerIdGuest(partner) >= 3 && partner.getTypePartner().equals("Regular")) {
            throw new EntityExistsException("El socio ya tiene 3 invitados");
        }
        guest.setStatusGuest("Activo");
        User user = guest.getUserIdGuest();
        user.setRoleUser("guest");
        if (personRepository.existsByDocumentPerson((guest.getUserIdGuest().getIdPerson().getDocumentPerson()))) {
            throw new EntityExistsException("Persona ya existe");
        }
        if (userRepository.existsByUserName(guest.getUserIdGuest().getUserName())) {
            throw new EntityExistsException("Usuario ya existe");
        }
        return GuestMapper.INSTANCE.toGuestDto(guestRepository.save(guest));
    }

    public List<GuestDto> findAll() {
        List<Guest> guests = guestRepository.findAll();
        List<GuestDto> guestDtos = new ArrayList<>();
        for (Guest guest : guests) {
            guestDtos.add(GuestMapper.INSTANCE.toGuestDto(guest));
        }
        return guestDtos;
    }

    public List<GuestDto> findByPartnerId(Long id) {
        List<Guest> guests = guestRepository.findByPartnerIdGuest_IdPartner(id);
        List<GuestDto> guestDtos = new ArrayList<>();
        for (Guest guest : guests) {
            guestDtos.add(GuestMapper.INSTANCE.toGuestDto(guest));
        }
        return guestDtos;
    }
}
