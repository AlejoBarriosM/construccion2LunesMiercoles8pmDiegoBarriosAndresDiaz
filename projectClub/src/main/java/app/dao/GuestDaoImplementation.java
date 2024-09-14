package app.dao;

import app.dao.interfaces.GuestDao;
import app.dto.GuestDto;
import app.dto.PartnerDto;
import app.dto.UserDto;
import app.helpers.Helper;
import app.entity.Guest;
import app.entity.User;
import app.repository.GuestRepository;
import app.repository.InvoiceRepository;
import app.repository.UserRepository;

public class GuestDaoImplementation implements GuestDao {

    private GuestRepository guestRepository;
    private InvoiceRepository invoiceRepository;
    private UserRepository userRepository;


    @Override
    public GuestDto findByUserId(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        return Helper.parse(guestRepository.findByUserIdGuest(user));
    }

    @Override
    public void createGuest(GuestDto guestDto, PartnerDto partnerDto, UserDto userDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        guestRepository.save(guest);
    }

    @Override
    public boolean pendingInvoices(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        return invoiceRepository.existsByIdPersonAndStatusInvoiceIs(guest.getUserIdGuest().getIdPerson(), "Debe");
    }

    @Override
    public void upgradeToPartner(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        guestRepository.updateStatusGuestByIdGuest(guest, "Inactivo");
    }

    @Override
    public void changeType(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse(guestDto);
        userRepository.updateRoleUserByIdUser(guest.getUserIdGuest(), "socio");
    }


}

