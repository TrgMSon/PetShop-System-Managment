package Invoice;

import Connection.DataConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;

public class InvoiceBusiness {
    public static boolean addInvoice(Invoice invoice) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();

            String sql = "insert into invoice(idInvoice, date, idCustomer) values( ?, ?, ?)";
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, invoice.getIdInvoice());
            psm.setString(2, invoice.getDate());
            psm.setString(3, invoice.getIdCustomer());

            return psm.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(InvoiceBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return false;
    }

    public static BigDecimal getTotalAmount(String idInvoice) {
        BigDecimal total = new BigDecimal("0");

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = """
                    SELECT SUM(p.cost * it.quantity) AS sum FROM product AS p
	                    JOIN invoicedetail AS it ON it.idProduct = p.idProduct
	                    JOIN invoice AS i ON i.idInvoice = it.idInvoice
                        WHERE i.idInvoice = ?
                    """;
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, idInvoice);
            ResultSet rs = psm.executeQuery();

            if (rs.next()) {
                total = rs.getBigDecimal("sum");
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return total;
    }

    public static boolean deleteInvoice(String idInvoice) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();

            String sql = "delete from invoice where idInvoice = '" + idInvoice + "'";
            PreparedStatement psm = conn.prepareStatement(sql);

            return psm.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(InvoiceBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return false;
    }

    public static ArrayList<Invoice> showListInvoice(String keyword) {
        ArrayList<Invoice> list = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();

            String  sql;
            if (keyword.contains("C"))
                sql = "SELECT * FROM invoice WHERE idCustomer LIKE ? ORDER BY date DESC";
            else if (keyword.contains("I"))
                sql = "SELECT * FROM invoice WHERE idInvoice LIKE ? ORDER BY date DESC";
            else 
                sql = "SELECT * FROM invoice WHERE date LIKE ? ORDER BY date DESC";

            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, "%" + keyword + "%");
            ResultSet rs = psm.executeQuery();

            while (rs.next()) {
                list.add(new Invoice(rs.getString("idInvoice"), rs.getString("idCustomer"), rs.getString("date")));
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return list;
    }
}