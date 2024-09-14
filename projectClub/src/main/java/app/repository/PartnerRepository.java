package app.repository;


import app.entity.Partner;
import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Partner findByIdUserPartner(User user);

    @Modifying
    @Query("UPDATE Partner p SET p.amountPartner = p.amountPartner + :amount WHERE p.idPartner = :partner")
    void incrementAmount(@Param("partner") Partner partner, @Param("amount") Double amount);

    @Modifying
    @Query("UPDATE Partner p SET p.amountPartner = p.amountPartner - :amount WHERE p.idPartner = :partner")
    void decrementAmount(@Param("partner") Partner partner, @Param("amount") Double amount);

    int countByTypePartner(String type);

    void updateTypePartnerByIdPartner(Partner partner, String type);

}
