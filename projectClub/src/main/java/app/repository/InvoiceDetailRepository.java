package app.repository;

import app.entity.Invoice;
import app.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {
    List<InvoiceDetail> findAllByIdInvoice(Invoice idInvoice);
}

