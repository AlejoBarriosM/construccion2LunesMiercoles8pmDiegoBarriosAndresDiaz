package app.repository;

import app.entity.Guest;
import app.entity.Partner;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    int countGuestByPartnerIdGuest(Partner partner);
    List<Guest> findByPartnerIdGuest_IdPartner(Long id);
}
