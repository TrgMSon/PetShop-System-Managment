package Product;

import Connection.DataConnection;
import Format.Format;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.math.BigDecimal;
import java.util.Random;
import java.util.ArrayList;

class Inform {
    private static JButton okBt;
    private static JLabel lbInform;
    private static JPanel pButton, pInform;
    private static JDialog inform;

    public static void initInform(JFrame menuProduct, String message) {
        inform = new JDialog(menuProduct, "Thông báo", true);
        inform.setLayout(new GridLayout(2, 1));
        inform.setSize(400, 200);
        inform.setLocationRelativeTo(menuProduct);

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

class ButtonDetailProduct extends JPanel {
    private JButton acptBt, closeBt;

    public void initActionAccept(ProductPanel productPanel, DetailProduct formEdit) {
        acptBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Product obj = new Product();
                obj.setName(formEdit.getTxtName().getText());
                obj.setOrigin(formEdit.getTxtOrigin().getText());
                if (formEdit.getTxtCost().getText().equals("") == false) {
                    obj.setCost(new BigDecimal(formEdit.getTxtCost().getText()));
                }
                obj.setType(String.valueOf(formEdit.getTxtType().getSelectedItem()));

                if (ProductBusiness.isValidProduct(obj) == false) {
                    Inform.initInform(formEdit, "Vui lòng nhập đủ thông tin sản phẩm");
                    return;
                }

                if (formEdit.getTxtId().getText().compareTo("") == 0) {
                    Random rand = new Random();
                    String tmp;
                    if (String.valueOf(formEdit.getTxtType().getSelectedItem())
                            .compareTo("Đồ dùng cho thú cưng") == 0) {
                        tmp = "DD";
                    } else {
                        tmp = "TC";
                    }

                    String idProduct = tmp + String.format("%04d", rand.nextInt(10000));
                    while (ProductBusiness.isExist(idProduct.substring(2))) {
                        idProduct = tmp + String.format("%04d", rand.nextInt(10000));
                    }
                    obj.setIdProduct(idProduct);

                    if (ProductBusiness.isExist(obj)) {
                        Inform.initInform(formEdit, "Thông tin đã tồn tại, vui lòng thử lại");
                        return;
                    }

                    ProductBusiness.addProduct(obj);
                    productPanel.loadData();
                }

                else {
                    obj.setIdProduct(formEdit.getTxtId().getText());
                    if (ProductBusiness.isInInvoice(obj.getIdProduct())) {
                        Inform.initInform(formEdit, "Sản phẩm đang có trong hóa đơn, không thể sửa");
                        formEdit.setVisible(false);
                        return;
                    }
                    else {
                        ProductBusiness.updateProduct(obj);
                    }
                    productPanel.loadData();
                }

                formEdit.setVisible(false);
            }
        });
    }

    public ButtonDetailProduct(ProductPanel productPanel, DetailProduct formEdit) {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        acptBt = new JButton("Xác nhận");
        acptBt.setFont(new Font("System", Font.BOLD, 16));
        initActionAccept(productPanel, formEdit);
        add(acptBt);

        closeBt = new JButton("Đóng");
        closeBt.setFont(new Font("System", Font.BOLD, 16));
        closeBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                formEdit.setVisible(false);
            }
        });
        add(closeBt);

        setVisible(true);
    }
}

class DetailProduct extends JFrame {
    private JPanel panelId, panelName, panelCost, panelOrigin, panelType;
    private JTextArea id, name, cost, origin;
    private JComboBox<String> type;
    private JLabel lbId, lbName, lbCost, lbOrigin, lbType;

    public DetailProduct(ProductPanel productPanel) {
        setTitle("Thông tin sản phẩm");
        setSize(900, 500);
        setLayout(new GridLayout(6, 1));

        panelId = new JPanel();
        panelId.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbId = new JLabel("Mã sản phẩm");
        lbId.setFont(new Font("System", Font.BOLD, 16));
        id = new JTextArea(2, 35);
        id.setFont(new Font("System", Font.PLAIN, 16));
        panelId.add(lbId);
        panelId.add(id);

        panelName = new JPanel();
        panelName.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbName = new JLabel("Tên sản phẩm");
        lbName.setFont(new Font("System", Font.BOLD, 16));
        name = new JTextArea(2, 35);
        name.setFont(new Font("System", Font.PLAIN, 16));
        panelName.add(lbName);
        panelName.add(name);

        panelCost = new JPanel();
        panelCost.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbCost = new JLabel("Giá bán (VND)");
        lbCost.setFont(new Font("System", Font.BOLD, 16));
        cost = new JTextArea(2, 35);
        cost.setFont(new Font("System", Font.PLAIN, 16));
        panelCost.add(lbCost);
        panelCost.add(cost);

        panelOrigin = new JPanel();
        panelOrigin.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbOrigin = new JLabel("Xuất xứ");
        lbOrigin.setFont(new Font("System", Font.BOLD, 16));
        origin = new JTextArea(2, 35);
        origin.setFont(new Font("System", Font.PLAIN, 16));
        panelOrigin.add(lbOrigin);
        panelOrigin.add(origin);

        panelType = new JPanel();
        panelType.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbType = new JLabel("Loại");
        lbType.setFont(new Font("System", Font.BOLD, 16));
        String[] options = { "Đồ dùng cho thú cưng", "Thú cưng" };
        type = new JComboBox<String>(options);
        type.setFont(new Font("System", Font.BOLD, 16));
        panelType.add(lbType);
        panelType.add(type);

        add(panelId);
        add(panelName);
        add(panelCost);
        add(panelOrigin);
        add(panelType);

        setLocationRelativeTo(null);
    }

    public JPanel getPanelId() {
        return panelId;
    }

    public JTextArea getTxtId() {
        return id;
    }

    public JTextArea getTxtName() {
        return name;
    }

    public JTextArea getTxtCost() {
        return cost;
    }

    public JTextArea getTxtOrigin() {
        return origin;
    }

    public JComboBox<String> getTxtType() {
        return type;
    }
}

class ProductPanel extends JPanel {
    private JTable tableProduct;
    private DefaultTableModel dtm;

    public void loadData() {
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
            Logger.getLogger(ProductUI.class.getName()).log(Level.SEVERE, null, e);
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

    public ProductPanel() {
        setLayout(new BorderLayout());

        tableProduct = new JTable();
        String[] nameColumns = { "Mã sản phẩm", "Tên sản phẩm", "Giá bán (VND)", "Xuất xứ", "Loại" };
        dtm = new DefaultTableModel(nameColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData();

        tableProduct.setModel(dtm);
        tableProduct.setRowHeight(50);
        tableProduct.setFont(new Font("System", Font.PLAIN, 16));
        tableProduct.getTableHeader().setFont(new Font("System", Font.BOLD, 16));
        tableProduct.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableProduct.setRowSelectionAllowed(true);

        tableProduct.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableProduct.getColumnModel().getColumn(1).setPreferredWidth(400);
        tableProduct.getColumnModel().getColumn(2).setPreferredWidth(50);
        tableProduct.getColumnModel().getColumn(3).setPreferredWidth(50);
        tableProduct.getColumnModel().getColumn(4).setPreferredWidth(50);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableProduct);
        scrollPane.setBorder(new EmptyBorder(0, 100, 0, 100));

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public JTable getTableProduct() {
        return tableProduct;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }
}

class ButtonPanel extends JPanel {
    private JButton addBt, delBt, editBt, reloadBt;

    public ButtonPanel(ProductPanel productPanel, JFrame menuProduct) {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        reloadBt = new JButton("Làm mới");
        reloadBt.setFont(new Font("System", Font.BOLD, 16));
        addBt = new JButton("Thêm");
        addBt.setFont(new Font("System", Font.BOLD, 16));
        delBt = new JButton("Xóa");
        delBt.setFont(new Font("System", Font.BOLD, 16));
        editBt = new JButton("Sửa");
        editBt.setFont(new Font("System", Font.BOLD, 16));

        add(reloadBt);
        add(addBt);
        add(delBt);
        add(editBt);

        reloadBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                productPanel.loadData();
            }
        });

        addBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                DetailProduct formEdit = new DetailProduct(productPanel);
                ButtonDetailProduct buttonDetailProduct = new ButtonDetailProduct(productPanel, formEdit);
                formEdit.add(buttonDetailProduct);
                formEdit.getTxtId().setEditable(false);
                formEdit.getPanelId().setVisible(false);
                formEdit.setVisible(true);
            }
        });

        delBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = productPanel.getTableProduct().getSelectedRow();

                if (row != -1) {
                    String id = String.valueOf(productPanel.getTableProduct().getValueAt(row, 0));
                    if (ProductBusiness.deleteProduct(id) == false) {
                        Inform.initInform(menuProduct, "Sản phẩm đang có trong hóa đơn, không thể xóa");
                        return;
                    }
                    productPanel.loadData();
                }
            }
        });

        editBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = productPanel.getTableProduct().getSelectedRow();
                if (row != -1) {
                    DetailProduct formEdit = new DetailProduct(productPanel);
                    ButtonDetailProduct buttonDetailProduct = new ButtonDetailProduct(productPanel, formEdit);
                    formEdit.add(buttonDetailProduct);
                    String id = String.valueOf(productPanel.getTableProduct().getValueAt(row, 0));
                    Product obj = ProductBusiness.showProductDetail(id);

                    formEdit.getTxtId().setEditable(false);
                    formEdit.getTxtId().setText(obj.getIdProduct());
                    formEdit.getTxtName().setText(obj.getName());
                    formEdit.getTxtCost().setText(String.valueOf(obj.getCost()));
                    formEdit.getTxtOrigin().setText(obj.getOrigin());
                    formEdit.getTxtType().setSelectedItem(obj.getType());

                    formEdit.setVisible(true);
                }
            }
        });

        setVisible(true);
    }

    public JButton getAddBt() {
        return addBt;
    }

    public JButton getDelBt() {
        return delBt;
    }

    public JButton getEditBt() {
        return editBt;
    }
}

class SearchPanel extends JPanel {
    private JTextArea text;
    private JLabel label;
    private JButton searchBt;

    public void initActionSearch(ProductPanel productPanel) {
        searchBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String keyword = text.getText();
                if (keyword.equals("") == false) {
                    ArrayList<Product> list = ProductBusiness.showListProduct(keyword);

                    DefaultTableModel dtm = productPanel.getDtm();
                    dtm.setRowCount(0);
                    for (Product i : list) {
                        Object[] row = { i.getIdProduct(), i.getName(),
                                Format.normalizeNumber(String.valueOf(i.getCost())),
                                i.getOrigin(), i.getType() };
                        dtm.addRow(row);
                    }
                    productPanel.getTableProduct().setModel(dtm);
                }
            }
        });
    }

    public SearchPanel(ProductPanel productPanel) {
        setBorder(new TitledBorder("Nhập thông tin tìm kiếm"));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        label = new JLabel("Từ khóa");
        label.setFont(new Font("System", Font.BOLD, 16));
        text = new JTextArea(1, 25);
        text.setFont(new Font("System", Font.PLAIN, 16));
        searchBt = new JButton("Tìm kiếm");
        searchBt.setFont(new Font("System", Font.BOLD, 16));

        add(label);
        add(text);
        add(searchBt);

        initActionSearch(productPanel);

        setVisible(true);
    }

    public JTextArea getText() {
        return text;
    }

    public JButton getSearchBt() {
        return searchBt;
    }
}

public class ProductUI {
    public static void showMenu() {
        JFrame menuProduct = new JFrame("Sản phẩm");
        menuProduct.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuProduct.setLayout(new BorderLayout());

        ProductPanel productPanel = new ProductPanel();
        SearchPanel searchPanel = new SearchPanel(productPanel);
        ButtonPanel buttonPanel = new ButtonPanel(productPanel, menuProduct);

        menuProduct.add(productPanel, BorderLayout.CENTER);
        menuProduct.add(searchPanel, BorderLayout.NORTH);
        menuProduct.add(buttonPanel, BorderLayout.SOUTH);

        menuProduct.setLocationRelativeTo(null);
        menuProduct.setVisible(true);
    }
}