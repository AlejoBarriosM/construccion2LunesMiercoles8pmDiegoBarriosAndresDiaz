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

    Guest findByUserIdGuest(User userIdGuest);
    int countGuestByPartnerIdGuest(Partner partner);
    @Modifying
    @Query("UPDATE Guest g SET g.statusGuest = :status WHERE g.idGuest = :guest")
    void updateStatusGuestByIdGuest(@Param("guest") Long guest, @Param("status") String status);

    List<Guest> findByPartnerIdGuest_IdPartner(Long id);
}
