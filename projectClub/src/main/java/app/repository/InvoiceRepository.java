package app.repository;

import app.entity.Invoice;
import app.entity.Partner;
import app.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    boolean existsByIdPartnerAndStatusInvoiceIs(Partner partner, String statusInvoice);
    boolean existsByIdPersonAndStatusInvoiceIs(Person idPerson, String statusInvoice);
    void updateStatusInvoiceByIdInvoice(Invoice invoice, String statusInvoice);

    List<Invoice> findAllByIdPartnerAndStatusInvoiceIsNot(Partner idPartner, String statusInvoice);
}
