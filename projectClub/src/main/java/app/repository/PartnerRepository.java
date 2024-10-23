package app.repository;


import app.entity.Partner;
import app.entity.Subscription;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    int countByTypePartner(Subscription typePartner);

    Partner findByidUserPartner(User user);
}
