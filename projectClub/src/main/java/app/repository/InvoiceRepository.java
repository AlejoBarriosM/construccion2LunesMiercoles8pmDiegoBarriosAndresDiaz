package app.repository;

import app.entity.Invoice;
import app.entity.Partner;
import app.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    boolean existsByIdPartnerAndStatusInvoiceIs(Partner partner, String statusInvoice);
    boolean existsByIdPersonAndStatusInvoiceIs(Person idPerson, String statusInvoice);
    @Modifying
    @Query("UPDATE Invoice i SET i.statusInvoice = :statusInvoice WHERE i.idInvoice = :invoice")
    void updateStatusInvoiceByIdInvoice(@Param("invoice") Long invoice, @Param("statusInvoice") String statusInvoice);
    List<Invoice> findAllByIdPartnerAndStatusInvoiceIsNot(Partner idPartner, String statusInvoice);

    List<Invoice> findByIdPartner(Partner partner);
}
