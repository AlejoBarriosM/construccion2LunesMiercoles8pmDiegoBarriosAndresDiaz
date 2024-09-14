package app.repository;

import app.entity.Guest;
import app.entity.Partner;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Guest findByUserIdGuest(User userIdGuest);
    int countGuestByPartnerIdGuest(Partner partner);
    void updateStatusGuestByIdGuest(Guest guest, String status);
}
