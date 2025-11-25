package Invoice;

import Mode.Mode;
import Product.ProductBusiness;
import Product.Product;
import Format.Format;
import Warehouse.WarehouseDetailBusiness;
import Customer.Customer;
import Customer.CustomerBusiness;
import Connection.DataConnection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDialog;

import com.toedter.calendar.JDateChooser;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

class ButtonPanel extends JPanel {
    private JButton closeBt, acptBt, okBt, reloadBt;
    private JDialog inform;
    private JLabel lbInform;
    private JPanel pButton, pInform;

    public void initInform(JFrame menuInvoiceDetail, String message) {
        inform = new JDialog(menuInvoiceDetail, "Thông báo", true);
        inform.setLayout(new GridLayout(2, 1));
        inform.setSize(400, 200);
        inform.setLocationRelativeTo(menuInvoiceDetail);

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

    public void initActionClose(JFrame menuInvoiceDetail) {
        closeBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                menuInvoiceDetail.setVisible(false);
            }
        });
    }

    public void markProduct() {

    }

    public void initActionAccept(JFrame menuInvoiceDetail, ProductPanel productPanel, InforPanel inforPanel,
            InvoicePanel invoicePanel) {
        acptBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Random rand = new Random();
                String idInvoice = "I" + String.format("%04d", rand.nextInt(10000));
                while (InvoiceDetailBusiness.isExist(idInvoice)) {
                    idInvoice = "I" + String.format("%04d", rand.nextInt(10000));
                }
                String nameCustomer = "", phone = "";

                String idCustomer = CustomerBusiness.findIdCustomer(inforPanel.getTxtPhone().getText());
                if (idCustomer.compareTo("") == 0) {
                    idCustomer = "C" + String.format("%04d", rand.nextInt(10000));
                    while (CustomerBusiness.isExist(idCustomer)) {
                        idCustomer = "C" + String.format("%04d", rand.nextInt(10000));
                    }
                    nameCustomer = inforPanel.getTxtNameCustomer().getText();
                    phone = inforPanel.getTxtPhone().getText();

                    if (CustomerBusiness.isValidPhone(phone) == false) {
                        initInform(menuInvoiceDetail, "Số điện thoại không hợp lệ, vui lòng nhập đủ 10 số");
                        return;
                    }

                    if (CustomerBusiness.validInforCustomer(idCustomer, nameCustomer, phone) == false) {
                        initInform(menuInvoiceDetail, "Vui lòng nhập đầy đủ thông tin khách hàng");
                        return;
                    } else {
                        CustomerBusiness.addCustomer(new Customer(idCustomer, nameCustomer, phone));
                    }
                }

                Date dateInvoice = inforPanel.getDateChooser().getDate();
                if (dateInvoice == null) {
                    initInform(menuInvoiceDetail, "Vui lòng nhập ngày giao dịch");
                    return;
                }
                SimpleDateFormat normalize = new SimpleDateFormat("yyyy-MM-dd");
                String date = normalize.format(dateInvoice);

                Invoice invoice = new Invoice(idInvoice, idCustomer, date);
                InvoiceBusiness.addInvoice(invoice);

                boolean isOver = false;
                boolean hasChoosen = false;
                JTable table = productPanel.getTableProduct();
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (table.getValueAt(i, 5) != null) {
                        String idProduct = (String) table.getValueAt(i, 0);

                        Integer quantity;
                        String tmp = (String) table.getValueAt(i, 5);
                        if (tmp.equals("")) {
                            quantity = 0;
                        } else {
                            quantity = Integer.parseInt((String) table.getValueAt(i, 5));
                        }

                        if (quantity == 0)
                            continue;

                        InvoiceDetailBusiness.addInvoiceDetail(idInvoice, idProduct, quantity);

                        if (WarehouseDetailBusiness.decreaseQuantityInStock(idProduct, quantity, false) == true) {
                            WarehouseDetailBusiness.decreaseQuantityInStock(idProduct, quantity, true);
                            hasChoosen = true;
                        } else {
                            if (isOver == false) table.clearSelection();

                            isOver = true;
                            table.addRowSelectionInterval(i, i);
                        }
                    }
                }

                if (isOver == true) {
                    initInform(menuInvoiceDetail, "Không đủ sản phẩm, vui lòng thử lại");
                    InvoiceBusiness.deleteInvoice(idInvoice);
                    return;
                }

                if (hasChoosen == false) {
                    initInform(menuInvoiceDetail, "Vui lòng nhập số lượng sản phẩm");
                    InvoiceBusiness.deleteInvoice(idInvoice);
                    productPanel.loadData();
                    return;
                }

                menuInvoiceDetail.setVisible(false);
                invoicePanel.loadData();
            }
        });
    }

    public void initActionReload(JButton reloadBt, ProductPanel productPanel) {
        reloadBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                productPanel.loadData();
            }
        });
    }

    public ButtonPanel(JFrame menuInvoiceDetail, Mode mode, ProductPanel productPanel, InforPanel inforPanel,
            InvoicePanel invoicePanel) {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        if (mode == Mode.ADD) {
            reloadBt = new JButton("Làm mới");
            reloadBt.setFont(new Font("System", Font.BOLD, 16));
            add(reloadBt);
            initActionReload(reloadBt, productPanel);

            acptBt = new JButton("Xác nhận");
            acptBt.setFont(new Font("System", Font.BOLD, 16));
            add(acptBt);
            initActionAccept(menuInvoiceDetail, productPanel, inforPanel, invoicePanel);
        }

        closeBt = new JButton("Đóng");
        closeBt.setFont(new Font("System", Font.BOLD, 16));
        add(closeBt);
        initActionClose(menuInvoiceDetail);
    }
}

class InforPanel extends JPanel {
    private JLabel lbIdInvoice, lbIdCustomer, lbNameCustomer, lbPhone, lbDate;
    private JTextField txtIdInvoice, txtIdCustomer, txtNameCustomer, txtPhone, txtDate;
    private JDateChooser txtChooseDate;
    private JPanel pIdInvoice, pIdCustomer, pNamecustomer, pPhone, pDate;

    public void initChooseDate() {
        lbDate = new JLabel("Ngày giao dịch");
        lbDate.setFont(new Font("System", Font.BOLD, 16));
        txtChooseDate = new JDateChooser();
        txtChooseDate.setFont(new Font("System", Font.PLAIN, 16));
        txtChooseDate.setSize(500, 500);

        pDate = new JPanel();
        pDate.setLayout(new GridLayout(1, 2));
        pDate.add(lbDate);
        pDate.add(txtChooseDate);
    }

    public void initDate(String idInvoice) {
        lbDate = new JLabel("Ngày giao dịch");
        lbDate.setFont(new Font("System", Font.BOLD, 16));
        txtDate = new JTextField(25);
        txtDate.setFont(new Font("System", Font.PLAIN, 16));
        txtDate.setEditable(false);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "select date from invoice where idInvoice = '" + idInvoice + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                txtDate.setText(rs.getString("date"));
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDetail.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        pDate = new JPanel();
        pDate.setLayout(new GridLayout(1, 2));
        pDate.add(lbDate);
        pDate.add(txtDate);
    }

    public void initpPhone(String idCustomer) {
        lbPhone = new JLabel("Số điện thoại");
        lbPhone.setFont(new Font("System", Font.BOLD, 16));
        txtPhone = new JTextField(25);
        txtPhone.setFont(new Font("System", Font.PLAIN, 16));
        txtPhone.setEditable(false);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "select phone from customer where idCustomer = '" + idCustomer + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                txtPhone.setText(rs.getString("phone"));
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDetail.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        pPhone = new JPanel();
        pPhone.setLayout(new GridLayout(1, 2));
        pPhone.add(lbPhone);
        pPhone.add(txtPhone);
    }

    public void initpIdInvoice(String idInvoice) {
        lbIdInvoice = new JLabel("Mã hóa đơn");
        lbIdInvoice.setFont(new Font("System", Font.BOLD, 16));
        txtIdInvoice = new JTextField(25);
        txtIdInvoice.setFont(new Font("System", Font.PLAIN, 16));
        txtIdInvoice.setEditable(false);
        txtIdInvoice.setText(idInvoice);

        pIdInvoice = new JPanel();
        pIdInvoice.setLayout(new GridLayout(1, 2));
        pIdInvoice.add(lbIdInvoice);
        pIdInvoice.add(txtIdInvoice);
    }

    public void initpIdCustomer(String idCustomer) {
        lbIdCustomer = new JLabel("Mã khách hàng");
        lbIdCustomer.setFont(new Font("System", Font.BOLD, 16));
        txtIdCustomer = new JTextField(25);
        txtIdCustomer.setFont(new Font("System", Font.PLAIN, 16));
        txtIdCustomer.setEditable(false);
        txtIdCustomer.setText(idCustomer);

        pIdCustomer = new JPanel();
        pIdCustomer.setLayout(new GridLayout(1, 2));
        pIdCustomer.add(lbIdCustomer);
        pIdCustomer.add(txtIdCustomer);
    }

    public void initpNameCustomer(String idCustomer) {
        lbNameCustomer = new JLabel("Họ và tên");
        lbNameCustomer.setFont(new Font("System", Font.BOLD, 16));
        txtNameCustomer = new JTextField(25);
        txtNameCustomer.setFont(new Font("System", Font.PLAIN, 16));
        txtNameCustomer.setEditable(false);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "select name from customer where idCustomer = '" + idCustomer + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                txtNameCustomer.setText(rs.getString("name"));
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDetail.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        pNamecustomer = new JPanel();
        pNamecustomer.setLayout(new GridLayout(1, 2));
        pNamecustomer.add(lbNameCustomer);
        pNamecustomer.add(txtNameCustomer);
    }

    public InforPanel(String idInvoice, String idCustomer, Mode mode) {
        setSize(600, 300);
        setLayout(new GridLayout(5, 1));
        setBorder(new TitledBorder("Thông tin hóa đơn"));

        initpIdInvoice(idInvoice);
        initpIdCustomer(idCustomer);
        initpNameCustomer(idCustomer);
        initpPhone(idCustomer);

        add(pIdInvoice);
        add(pIdCustomer);
        add(pNamecustomer);
        add(pPhone);

        if (mode == Mode.ADD) {
            initChooseDate();
            add(pDate);
        } else {
            initDate(idInvoice);
            add(pDate);
        }

        setVisible(true);
    }

    public JTextField getTxtDate() {
        return txtDate;
    }

    public JTextField getTxtPhone() {
        return txtPhone;
    }

    public JTextField getTxtNameCustomer() {
        return txtNameCustomer;
    }

    public JDateChooser getDateChooser() {
        return txtChooseDate;
    }
}

class InvoiceDetailPanel extends JPanel {
    private JTable invoiceDetailTable;
    private DefaultTableModel dtm;

    public void loadData(String idInvoice) {
        dtm.setRowCount(0);

        ArrayList<InvoiceDetail> list = InvoiceDetailBusiness.showInvoiceDetail(idInvoice);
        for (InvoiceDetail i : list) {
            Object[] row = { i.getIdProduct(), i.getNameProduct(),
                    Format.normalizeNumber(String.valueOf(i.getCostProduct())),
                    String.valueOf(i.getQuantity()) };
            dtm.addRow(row);
        }
    }

    public InvoiceDetailPanel(String idInvoice, String idCustomer) {
        setLayout(new GridLayout(1, 1));

        invoiceDetailTable = new JTable();
        String[] nameColumns = { "Mã sản phẩm", "Tên sản phẩm", "Giá bán (VND)", "Số lượng" };
        dtm = new DefaultTableModel(nameColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData(idInvoice);

        invoiceDetailTable.setModel(dtm);
        invoiceDetailTable.setRowSelectionAllowed(true);
        invoiceDetailTable.getTableHeader().setFont(new Font("System", Font.BOLD, 16));
        invoiceDetailTable.setRowHeight(50);
        invoiceDetailTable.setFont(new Font("System", Font.PLAIN, 16));
        invoiceDetailTable.setBorder(new LineBorder(new Color(0, 0, 0)));

        invoiceDetailTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        invoiceDetailTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        invoiceDetailTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        invoiceDetailTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(invoiceDetailTable);
        scrollPane.setBorder(new EmptyBorder(0, 100, 0, 100));

        add(scrollPane);

        setVisible(true);
    }
}

class ProductPanel extends JPanel {
    private JTable tableProduct;
    private DefaultTableModel dtm;
    private ArrayList<Integer> used;

    public void loadData() {
        used = new ArrayList<>(Collections.nCopies(10005, 0));
        dtm.setRowCount(0);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "select * from product order by cost";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Object[] row = { rs.getString("idProduct"), rs.getString("name"),
                        Format.normalizeNumber(String.valueOf(rs.getBigDecimal("cost"))),
                        rs.getString("origin"), rs.getString("type") };
                dtm.addRow(row);
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDetailUI.class.getName()).log(Level.SEVERE, null, e);
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

    public void initActionSearch(JButton searchBt, JTextField txtSearch) {
        searchBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String keyword = txtSearch.getText();

                for (int i = 0; i < tableProduct.getRowCount(); i++) {
                    if (tableProduct.getValueAt(i, 5) != null) {
                        String idProduct = (String) tableProduct.getValueAt(i, 0);
                        
                        int qty;
                        String tmp = (String) tableProduct.getValueAt(i, 5);
                        if (tmp.equals("")) {
                            qty = 0;
                        }
                        else {
                            qty = Integer.parseInt((String) tableProduct.getValueAt(i, 5));
                        }

                        Integer index = Integer.parseInt(idProduct.substring(2));
                        used.set(index, qty);
                    }
                }

                if (keyword.isEmpty() == false) {
                    ArrayList<Product> listProduct = ProductBusiness.showListProduct(keyword);
                    dtm.setRowCount(0);

                    for (Product i : listProduct) {
                        Object[] row = { i.getIdProduct(), i.getName(), Format.normalizeNumber(String.valueOf(i.getCost())), i.getOrigin(), i.getType() };
                        dtm.addRow(row);
                    }
                    tableProduct.setModel(dtm);
                } else {
                    dtm.setRowCount(0);
                    ArrayList<Product> listProduct = ProductBusiness.showAllProduct();
                    dtm.setRowCount(0);

                    for (Product i : listProduct) {
                        Object[] row = { i.getIdProduct(), i.getName(), Format.normalizeNumber(String.valueOf(i.getCost())), i.getOrigin(), i.getType() };
                        dtm.addRow(row);
                    }
                    tableProduct.setModel(dtm);
                }

                for (int i = 0; i < tableProduct.getRowCount(); i++) {
                    String idProduct = (String) tableProduct.getValueAt(i, 0);
                    Integer index = Integer.parseInt(idProduct.substring(2));
                    if (used.get(index) != 0) {
                        tableProduct.setValueAt(String.valueOf(used.get(index)), i, 5);
                    }
                }
            }
        });
    }

    public JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel lbSearch = new JLabel("Từ khóa");
        lbSearch.setFont(new Font("System", Font.BOLD, 16));
        JTextField txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("System", Font.PLAIN, 16));
        JButton searchBt = new JButton("Tìm kiếm");
        searchBt.setFont(new Font("System", Font.BOLD, 16));
        initActionSearch(searchBt, txtSearch);

        searchPanel.add(lbSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(searchBt);

        return searchPanel;
    }

    public ProductPanel() {
        used = new ArrayList<>(Collections.nCopies(10005, 0));
        setLayout(new BorderLayout());

        tableProduct = new JTable();
        String[] namecolumns = { "Mã sản phẩm", "Tên sản phẩm", "Giá bán (VND)", "Xuất xứ", "Loại", "Số lượng" };
        dtm = new DefaultTableModel(namecolumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        loadData();

        tableProduct.setModel(dtm);
        tableProduct.setRowSelectionAllowed(true);
        tableProduct.setRowHeight(50);
        tableProduct.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableProduct.getTableHeader().setFont(new Font("System", Font.BOLD, 16));
        tableProduct.setFont(new Font("System", Font.PLAIN, 16));

        tableProduct.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableProduct.getColumnModel().getColumn(1).setPreferredWidth(360);
        tableProduct.getColumnModel().getColumn(2).setPreferredWidth(80);
        tableProduct.getColumnModel().getColumn(3).setPreferredWidth(80);
        tableProduct.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableProduct.getColumnModel().getColumn(5).setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableProduct);

        add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public JTable getTableProduct() {
        return tableProduct;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }
}

public class InvoiceDetailUI {
    public static void showMenuDetail(String idInvoice, String idCustomer, Mode mode, InvoicePanel invoicePanel) {
        JFrame menuInvoiceDetail = new JFrame("Chi tiết hóa đơn");
        menuInvoiceDetail.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuInvoiceDetail.setLayout(new BorderLayout());

        InforPanel inforPanel = new InforPanel(idInvoice, idCustomer, mode);
        menuInvoiceDetail.add(inforPanel, BorderLayout.NORTH);

        InvoiceDetailPanel invoiceDetailPanel = new InvoiceDetailPanel(idInvoice, idCustomer);
        ProductPanel productPanel = new ProductPanel();

        if (mode == Mode.SHOW) {
            menuInvoiceDetail.add(invoiceDetailPanel, BorderLayout.CENTER);
        }

        else if (mode == Mode.ADD) {
            menuInvoiceDetail.setTitle("Thêm hóa đơn");
            menuInvoiceDetail.add(productPanel, BorderLayout.CENTER);
            inforPanel.getTxtPhone().setEditable(true);
            inforPanel.getTxtNameCustomer().setEditable(true);
        }

        ButtonPanel buttonPanel = new ButtonPanel(menuInvoiceDetail, mode, productPanel, inforPanel, invoicePanel);
        menuInvoiceDetail.add(buttonPanel, BorderLayout.SOUTH);

        menuInvoiceDetail.setLocationRelativeTo(null);
        menuInvoiceDetail.setVisible(true);
    }
}