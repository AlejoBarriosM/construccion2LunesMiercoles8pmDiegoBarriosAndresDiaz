package app.dao;

//import app.dto.InvoiceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao {

   // private Connection connection;

//    public InvoiceDao(Connection connection) {
//        this.connection = connection;
//    }

//    public void createInvoice(InvoiceDto invoice) throws SQLException {
//        String sql = "INSERT INTO invoices (id, idPartner, Date, totalAmount) VALUES (?,?,?,?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, invoice.getId());
//            stmt.setInt(2, invoice.getIdPartner());
//            stmt.setDate(3, new java.sql.Date(invoice.getDate().getTime()));
//            stmt.setDouble(4, invoice.getTotalAmount());
//            stmt.executeUpdate();
//        }

  // }

//    public List<InvoiceDto> getAllInvoices() throws SQLException {
//        String sql = "SELECT * FROM invoices";
//        List<InvoiceDto> invoices = new ArrayList<>();
//        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                InvoiceDto invoice = new InvoiceDto(
//                        rs.getInt("id"),
//                        rs.getInt("idPartner"),
//                        rs.getDate("date"),
//                        rs.getDouble("totalAmount")
//                );
//                invoices.add(invoice);
//            }
//        }
//        return invoices;
//    }

//    public void updateInvoice(InvoiceDto invoice) throws SQLException {
//        String sql = "UPDATE invoices SET idPartner =?, date =?, totalAmount =? WHERE id=?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, invoice.getIdPartner());
//            stmt.setDate(2, new java.sql.Date(invoice.getDate().getTime()));
//            stmt.setDouble(3, invoice.getTotalAmount());
//            stmt.setInt(4, invoice.getId());
//            stmt.executeUpdate();
//        }
//    }
    
//    public void eliminateInvoice(int id)throws SQLException{
//        String sql = "DELETE FROM invoices WHERE id = ?";
//        try(PreparedStatement stmt = connection.prepareStatement(sql)){
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        }
//    }

}
