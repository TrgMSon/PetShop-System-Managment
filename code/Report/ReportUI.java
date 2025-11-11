package Report;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDialog;

import Format.Format;
import Connection.DataConnection;
import Mode.Mode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;;

class Inform {
    private static JButton okBt;
    private static JLabel lbInform;
    private static JPanel pButton, pInform;
    private static JDialog inform;

    public static void initInform(JFrame menuWarehouseDetail, String message) {
        inform = new JDialog(menuWarehouseDetail, "Thông báo", true);
        inform.setLayout(new GridLayout(2, 1));
        inform.setSize(400, 200);
        inform.setLocationRelativeTo(menuWarehouseDetail);

        okBt = new JButton("Đóng");
        okBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                inform.dispose();
            }
        });
        pButton = new JPanel();
        pButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        pButton.add(okBt);

        pInform = new JPanel();
        pInform.setLayout(new FlowLayout(FlowLayout.CENTER));
        lbInform = new JLabel(message);
        pInform.add(lbInform);

        inform.add(pInform);
        inform.add(pButton);
        inform.setVisible(true);
    }
}

class ButtonPanel extends JPanel {
    private JRadioButton monthOpt, yearOpt;

    public ButtonPanel(ReportPanel reportPanel) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        yearOpt = new JRadioButton("Năm");
        monthOpt = new JRadioButton("Tháng");
        monthOpt.setSelected(true);
        initActionMonth(monthOpt, reportPanel, yearOpt);
        initActionYear(monthOpt, reportPanel, yearOpt);

        add(monthOpt);
        add(yearOpt);

        setVisible(true);
    }

    public void initActionYear(JRadioButton monthOpt, ReportPanel reportPanel, JRadioButton yearOpt) {
        yearOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (yearOpt.isSelected()) {
                    monthOpt.setSelected(false);
                } else {
                    yearOpt.setSelected(true);
                }
                reportPanel.getTarget().setText("Năm");
                reportPanel.getHeader().setText("Doanh thu theo năm");
                reportPanel.resetData();
            }
        });
    }

    public void initActionMonth(JRadioButton monthOpt, ReportPanel reportPanel, JRadioButton yearOpt) {
        monthOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (monthOpt.isSelected()) {
                    yearOpt.setSelected(false);
                } else {
                    monthOpt.setSelected(true);
                }
                reportPanel.getTarget().setText("Tháng");
                reportPanel.getHeader().setText("Doanh thu theo tháng");
                reportPanel.resetData();
            }
        });
    }

    public JRadioButton getMonthOpt() {
        return monthOpt;
    }
}

class PanelOldStock extends JPanel {
    private JTable tableCustomer;
    private DefaultTableModel dtm;
    private int duration;

    public PanelOldStock(int duration) {
        this.duration = duration;
        setLayout(new GridLayout(1, 1));

        tableCustomer = new JTable();
        String[] nameColumns = { "Mã sản phẩm", "Tên", "Giá (VND)", "Ngày cuối nhập kho", "Số lượng", "Mã kho",
                "Địa chỉ kho" };
        dtm = new DefaultTableModel(nameColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData();

        tableCustomer.setModel(dtm);
        tableCustomer.setRowSelectionAllowed(true);
        add(new JScrollPane(tableCustomer));

        setVisible(true);
    }

    public void loadData() {
        dtm.setRowCount(0);

        LocalDate now = LocalDate.now();
        LocalDate old = now.minusDays(duration);
        DateTimeFormatter normalize = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String oldDate = normalize.format(old);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();

            String sql = """
                    SELECT p.idProduct, wd.nameProduct AS nameproduct, p.cost, wd.lastReceiveDate, wd.quantityInStock, w.idWarehouse AS warehouseId, w.address
                     FROM warehousedetail AS wd
                     JOIN warehouse AS w ON w.idWarehouse = wd.idWarehouse
                     JOIN product AS p ON p.idProduct = wd.idProductW
                     WHERE wd.lastReceiveDate <= ?;
                                                            """;
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, oldDate);
            ResultSet rs = psm.executeQuery();

            while (rs.next()) {
                Object[] row = { rs.getString("idProduct"), rs.getString("nameproduct"),
                        Format.normalizeNumber(String.valueOf(rs.getBigDecimal("cost"))),
                        rs.getString("lastReceiveDate"), rs.getInt("quantityInStock"),
                        rs.getString("warehouseId"), rs.getString("address") };
                dtm.addRow(row);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReportUI.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public JTable getTableCustomer() {
        return tableCustomer;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }
}

class OldStock extends JFrame {
    private JPanel pButton;
    private JButton closeBt;

    public OldStock() {
        setLayout(new BorderLayout());
        setSize(1050, 200);
        setTitle("Hàng tồn kho");

        pButton = new JPanel();
        pButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        closeBt = new JButton("Đóng");
        initActionClose(closeBt);
        pButton.add(closeBt);
        add(pButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initActionClose(JButton closeBt) {
        closeBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
    }
}

class ReportPanel extends JPanel {
    private JLabel header, OldStock, target;
    private JTextField txtTarget, duration;
    private JPanel pHeader, pOldStock, pTarget;
    private JButton search, showOldStock;

    public JLabel getTarget() {
        return target;
    }

    public JLabel getHeader() {
        return header;
    }

    public void resetData() {
        txtTarget.setText("");
    }

    public ReportPanel(JFrame menuReport) {
        setLayout(new GridLayout(3, 1));

        pHeader = new JPanel();
        pHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
        header = new JLabel("Doanh thu theo tháng");
        pHeader.add(header);
        add(pHeader);

        pTarget = new JPanel();
        pTarget.setLayout(new FlowLayout(FlowLayout.LEFT));
        target = new JLabel("Tháng");
        txtTarget = new JTextField(25);
        search = new JButton("Xem báo cáo doanh thu");
        initActionSearch(search, menuReport);
        pTarget.add(target);
        pTarget.add(txtTarget);
        pTarget.add(search);
        add(pTarget);

        pOldStock = new JPanel();
        pOldStock.setLayout(new FlowLayout(FlowLayout.LEFT));
        OldStock = new JLabel("Số ngày tồn kho tối đa");
        duration = new JTextField(9);
        duration.setText("50");
        showOldStock = new JButton("Xem danh sách hàng tồn kho");
        initActionShow(showOldStock, duration, menuReport);
        pOldStock.add(OldStock);
        pOldStock.add(duration);
        pOldStock.add(showOldStock);
        add(pOldStock);

        setVisible(true);
    }

    public void initActionShow(JButton showOldStock, JTextField duration, JFrame menuReport) {
        showOldStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                OldStockReport oldStockReport = new OldStockReport(Integer.parseInt(duration.getText()));
                if (oldStockReport.getCountRow() == 0) {
                    Inform.initInform(menuReport, "Không có sản phẩm tồn kho theo yêu cầu, vui lòng thử lại");
                }
                else {
                    oldStockReport.setVisible(true);
                }
            }
        });
    }

    public void initActionSearch(JButton search, JFrame menuReport) {
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String key = txtTarget.getText();
                if (key.equals("") == false) {
                    String[] tmp = key.split("-");
                    LocalDate target;
                    if (tmp.length == 2) {
                        target = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1);
                        ArrayList<BigDecimal> listIncome = ReportBusiness.getListIncome(target, Mode.MONTH);
                        LineChartReport p = new LineChartReport();
                        p.setListIncome(listIncome);
                        p.setInforChart("DOANH THU THÁNG " + tmp[1], "Doanh thu (nghìn VND)", "Ngày");
                        p.show();
                    }
                    else if (tmp.length == 1) {
                        target = LocalDate.of(Integer.parseInt(tmp[0]), 1, 1);
                        ArrayList<BigDecimal> listIncome = ReportBusiness.getListIncome(target, Mode.YEAR);
                        LineChartReport p = new LineChartReport();
                        p.setListIncome(listIncome);
                        p.setInforChart("DOANH THU NĂM " + key, "Doanh thu (nghìn VND)", "Tháng");
                        p.show();
                    }
                    
                }
                else {
                    Inform.initInform(menuReport, "Vui lòng nhập thông tin tìm kiếm");
                }
            }
        });
    }
}

public class ReportUI {
    public static void showMenu() {
        JFrame menuReport = new JFrame("Báo cáo");
        menuReport.setSize(600, 400);
        menuReport.setLayout(new BorderLayout());
        

        ReportPanel reportPanel = new ReportPanel(menuReport);
        menuReport.add(reportPanel, BorderLayout.CENTER);

        ButtonPanel buttonPanel = new ButtonPanel(reportPanel);
        menuReport.add(buttonPanel, BorderLayout.NORTH);

        menuReport.setLocationRelativeTo(null);
        menuReport.setVisible(true);
    }
}
