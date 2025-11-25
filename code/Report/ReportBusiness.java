package Report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.math.BigDecimal;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Connection.DataConnection;
import Mode.Mode;

public class ReportBusiness {
    public static BigDecimal getIncome(String keyword) {
        BigDecimal result = new BigDecimal("0");

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = """
                    SELECT SUM(p.cost * it.quantity) AS sum FROM product AS p
	                    JOIN invoicedetail AS it ON it.idProduct = p.idProduct
                        JOIN invoice AS i ON i.idInvoice = it.idInvoice
                        WHERE i.date LIKE ?
                        GROUP BY i.date
                    """;
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, "%" + keyword + "%");
            ResultSet rs = psm.executeQuery();

            if (rs.next()) {
                result = rs.getBigDecimal("sum");
            }
        } catch (SQLException e) {
            Logger.getLogger(ReportBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return result;
    }

    public static ArrayList<BigDecimal> getListIncome(LocalDate target, Mode mode) {
        ArrayList<BigDecimal> listIncome = new ArrayList<>();

        if (mode == Mode.YEAR) {
            for (int i=1; i<=12; i++) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                String date = target.format(formatter);
                listIncome.add(getIncome(date).divide(new BigDecimal("1000")));
                target = target.plusMonths(1);
            }
        }

        else {
            for (int i=1; i<=target.getDayOfMonth(); i++) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = target.format(formatter);
                listIncome.add(getIncome(date).divide(new BigDecimal("1000")));
                target = target.plusDays(1);
            }
        }

        return listIncome;
    }

    public static String normalizeDate(String date) {
        String[] tmp = date.split("-");
        return tmp[2] + "/" + tmp[1] + "/" + tmp[0];
    }
}