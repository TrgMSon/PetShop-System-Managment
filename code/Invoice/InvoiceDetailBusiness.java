package Invoice;

import Connection.DataConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.logging.Level;

public class InvoiceDetailBusiness {
    public static ArrayList<InvoiceDetail> showInvoiceDetail(String idInvoice) {
        ArrayList<InvoiceDetail> list = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = """
                    SELECT it.idProduct, it.quantity, p.cost, p.name FROM invoicedetail AS it
                    JOIN product AS p ON p.idProduct = it.idProduct
                    WHERE it.idInvoice = ?;
                    """;
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, idInvoice);
            ResultSet rs = psm.executeQuery();

            while (rs.next()) {
                int quantity = rs.getInt("quantity");
                String nameProduct = rs.getString("name");
                BigDecimal cost = rs.getBigDecimal("cost");
                String idProduct = rs.getString("idProduct");
                list.add(new InvoiceDetail(nameProduct, idProduct, idInvoice, quantity, cost));
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDetailBusiness.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public static void addInvoiceDetail(String idInvoice, String idProduct, String nameProduct, BigDecimal cost,
            int quantity) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();
            String sql = "insert into invoicedetail(idInvoice, idProduct, quantity) values(?, ?, ?)";
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, idInvoice);
            psm.setString(2, idProduct);
            psm.setInt(3, quantity);

            psm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDetailBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }
    }

    public static boolean isExist(String idInvoice) {
        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "SELECT * FROM invoice WHERE idInvoice = '" + idInvoice + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) return true;
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDetailBusiness.class.getName()).log(Level.SEVERE, null, e);
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
}