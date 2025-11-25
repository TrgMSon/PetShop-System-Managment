package Report;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import Mode.Mode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDate;

class Inform {
    private static JButton okBt;
    private static JLabel lbInform;
    private static JPanel pButton, pInform;
    private static JDialog inform;

    public static void initInform(JFrame menuWarehouseDetail, String message) {
        inform = new JDialog(menuWarehouseDetail, "Thông báo", true);
        inform.setLayout(new GridLayout(2, 1));
        inform.setSize(500, 200);
        inform.setLocationRelativeTo(menuWarehouseDetail);

        okBt = new JButton("Đóng");
        okBt.setFont(new Font("System", Font.BOLD, 16));
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
        lbInform.setFont(new Font("System", Font.PLAIN, 16));
        pInform.add(lbInform);

        inform.add(pInform);
        inform.add(pButton);
        inform.setVisible(true);
    }
}

class ButtonPanel extends JPanel {
    private JRadioButton dayOpt, monthOpt, yearOpt;

    public ButtonPanel(ReportPanel reportPanel) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        dayOpt = new JRadioButton("Ngày");
        dayOpt.setFont(new Font("System", Font.BOLD, 16));
        yearOpt = new JRadioButton("Năm");
        yearOpt.setFont(new Font("System", Font.BOLD, 16));
        monthOpt = new JRadioButton("Tháng");
        monthOpt.setFont(new Font("System", Font.BOLD, 16));
        monthOpt.setSelected(true);
        initActionDay(dayOpt, reportPanel, monthOpt, yearOpt);
        initActionMonth(dayOpt, monthOpt, reportPanel, yearOpt);
        initActionYear(dayOpt, monthOpt, reportPanel, yearOpt);

        add(dayOpt);
        add(monthOpt);
        add(yearOpt);

        setVisible(true);
    }

    public void initActionDay(JRadioButton dayOpt, ReportPanel reportPanel, JRadioButton monthOpt, JRadioButton yearOpt) {
        dayOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (dayOpt.isSelected()) {
                    monthOpt.setSelected(false);
                    yearOpt.setSelected(false);
                } else {
                    dayOpt.setSelected(true);
                }
                reportPanel.getPTarget().setVisible(false);
                reportPanel.getlbBestSeller().setText("Ngày");
                reportPanel.resetData();
            }
        });
    }

    public void initActionYear(JRadioButton dayOpt, JRadioButton monthOpt, ReportPanel reportPanel, JRadioButton yearOpt) {
        yearOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (yearOpt.isSelected()) {
                    monthOpt.setSelected(false);
                    dayOpt.setSelected(false);
                } else {
                    yearOpt.setSelected(true);
                }
                reportPanel.getPTarget().setVisible(true);
                reportPanel.getTarget().setText("Năm");
                reportPanel.getlbBestSeller().setText("Năm");
                reportPanel.resetData();
            }
        });
    }

    public void initActionMonth(JRadioButton dayOpt, JRadioButton monthOpt, ReportPanel reportPanel, JRadioButton yearOpt) {
        monthOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (monthOpt.isSelected()) {
                    yearOpt.setSelected(false);
                    dayOpt.setSelected(false);
                } else {
                    monthOpt.setSelected(true);
                }
                reportPanel.getPTarget().setVisible(true);
                reportPanel.getTarget().setText("Tháng");
                reportPanel.getlbBestSeller().setText("Tháng");
                reportPanel.resetData();
            }
        });
    }

    public JRadioButton getMonthOpt() {
        return monthOpt;
    }
}

class ReportPanel extends JPanel {
    private JLabel oldStock, target, lbBestSeller;
    private JTextField txtTarget, duration, txtBestSeller;
    private JPanel pOldStock, pTarget, pBestSeller;
    private JButton showIncome, showOldStock, showBestSeller;

    public JPanel getPTarget() {
        return pTarget;
    }

    public JLabel getTarget() {
        return target;
    }

    public JLabel getlbBestSeller() {
        return lbBestSeller;
    }

    public void resetData() {
        txtTarget.setText("");
        txtBestSeller.setText("");
    }

    public ReportPanel(JFrame menuReport) {
        setLayout(new GridLayout(3, 1));

        pTarget = new JPanel();
        pTarget.setLayout(new FlowLayout(FlowLayout.LEFT));
        target = new JLabel("Tháng");
        target.setFont(new Font("System", Font.BOLD, 16));
        txtTarget = new JTextField(25);
        txtTarget.setFont(new Font("System", Font.PLAIN, 16));
        showIncome = new JButton("Xem báo cáo doanh thu");
        showIncome.setFont(new Font("System", Font.BOLD, 16));
        initActionSearchOldStock(showIncome, menuReport);
        pTarget.add(target);
        pTarget.add(txtTarget);
        pTarget.add(showIncome);
        add(pTarget);

        pBestSeller = new JPanel();
        pBestSeller.setLayout(new FlowLayout(FlowLayout.LEFT));
        showBestSeller = new JButton("Xem sản phẩm bán chạy");
        showBestSeller.setFont(new Font("System", Font.BOLD, 16));
        lbBestSeller = new JLabel("Tháng");
        lbBestSeller.setFont(new Font("System", Font.BOLD, 16));
        txtBestSeller = new JTextField(25);
        txtBestSeller.setFont(new Font("System", Font.PLAIN, 16));
        pBestSeller.add(lbBestSeller);
        pBestSeller.add(txtBestSeller);
        pBestSeller.add(showBestSeller);
        initActionSearchBestSeller(showBestSeller, menuReport);
        add(pBestSeller);

        pOldStock = new JPanel();
        pOldStock.setLayout(new FlowLayout(FlowLayout.LEFT));
        oldStock = new JLabel("Số ngày tồn kho tối đa");
        oldStock.setFont(new Font("System", Font.BOLD, 16));
        duration = new JTextField(9);
        duration.setFont(new Font("System", Font.PLAIN, 16));
        duration.setText("50");
        showOldStock = new JButton("Xem danh sách hàng tồn kho");
        showOldStock.setFont(new Font("System", Font.BOLD, 16));
        initActionShow(showOldStock, duration, menuReport);
        pOldStock.add(oldStock);
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

    public void initActionSearchOldStock(JButton showIncome, JFrame menuReport) {
        showIncome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String key = txtTarget.getText();
                if (key.equals("") == false) {
                    String[] tmp = key.split("-");
                    LocalDate target;
                    if (tmp.length == 2) {
                        target = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1);
                        ArrayList<BigDecimal> listIncome = ReportBusiness.getListIncome(target, Mode.MONTH);
                        LineChartIncomeReport p = new LineChartIncomeReport();
                        p.setListIncome(listIncome);
                        p.setInforChart("DOANH THU THÁNG " + tmp[1], "Doanh thu (nghìn VND)", "Ngày");
                        p.show();
                    }
                    else if (tmp.length == 1) {
                        target = LocalDate.of(Integer.parseInt(tmp[0]), 1, 1);
                        ArrayList<BigDecimal> listIncome = ReportBusiness.getListIncome(target, Mode.YEAR);
                        LineChartIncomeReport p = new LineChartIncomeReport();
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

    public void initActionSearchBestSeller(JButton showBestSeller, JFrame menuReport) {
        showBestSeller.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (txtBestSeller.getText().isEmpty()) {
                    Inform.initInform(menuReport, "Vui lòng nhập thông tin tìm kiếm");
                }
                else {
                    BestSellerReport bestSellerReport = new BestSellerReport(txtBestSeller.getText());
                    if (bestSellerReport.getCountRow() == 0) {
                        Inform.initInform(menuReport, "Không có sản phẩm theo thời điểm tìm kiếm, vui lòng thử lại");
                        return;
                    }

                    bestSellerReport.setVisible(true);;
                }
            }
        });
    }
}

public class ReportUI {
    public static void showMenu() {
        JFrame menuReport = new JFrame("Báo cáo");
        menuReport.setSize(900, 500);
        menuReport.setLayout(new BorderLayout());
        

        ReportPanel reportPanel = new ReportPanel(menuReport);
        menuReport.add(reportPanel, BorderLayout.CENTER);

        ButtonPanel buttonPanel = new ButtonPanel(reportPanel);
        menuReport.add(buttonPanel, BorderLayout.NORTH);

        menuReport.setLocationRelativeTo(null);
        menuReport.setVisible(true);
    }
}
