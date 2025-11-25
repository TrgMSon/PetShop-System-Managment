package Customer;

import Connection.DataConnection;
import Mode.Mode;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDialog;

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

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

class Inform {
    private static JButton okBt;
    private static JLabel lbInform;
    private static JPanel pButton, pInform;
    private static JDialog inform;

    public static void initInform(JFrame menuCustomer, String message) {
        inform = new JDialog(menuCustomer, "Thông báo", true);
        inform.setLayout(new GridLayout(2, 1));
        inform.setSize(400, 200);
        inform.setLocationRelativeTo(menuCustomer);

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

class ButtonDetailPanel extends JPanel {
    private JButton closeBt, acptBt;

    public void initActionUpdate(DetailCustomer formEdit, PanelCustomer panelCustomer) {
        acptBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Customer obj = new Customer();
                if (formEdit.getTxtId().getText().compareTo("") == 0) {
                    Random rand = new Random();
                    String tmp = "C" + String.format("%04d", rand.nextInt(10000));
                    while (CustomerBusiness.isExist(tmp)) {
                        tmp = "C" + String.format("%04d", rand.nextInt(10000));
                    }
                    obj.setId(tmp);
                    obj.setName(formEdit.getTxtName().getText());
                    obj.setPhone(formEdit.getTxtPhone().getText());

                    if (CustomerBusiness.isValidPhone(obj.getPhone()) == false) {
                        Inform.initInform(formEdit, "Số điện thoại không hợp lệ, vui lòng nhập đủ 10 số");
                        return;
                    }

                    if (CustomerBusiness.isPhoneExist(obj.getPhone(), obj.getId())) {
                        Inform.initInform(formEdit, "Số điện thoại đã tồn tại, vui lòng thử lại");
                        return;
                    }

                    if (CustomerBusiness.isExist(obj)) {
                        Inform.initInform(formEdit, "Thông tin đã tồn tại, vui lòng thử lại");
                        return;
                    }

                    if (CustomerBusiness.validInforCustomer(obj.getId(), obj.getName(), obj.getPhone()) == false) {
                        Inform.initInform(formEdit, "Vui lòng nhập đầy đủ thông tin khách hàng");
                        return;
                    }
                    CustomerBusiness.addCustomer(obj);
                    panelCustomer.loadData();
                }

                else {
                    obj.setId(formEdit.getTxtId().getText());
                    obj.setName(formEdit.getTxtName().getText());
                    obj.setPhone(formEdit.getTxtPhone().getText());

                    if (CustomerBusiness.validInforCustomer(obj.getId(), obj.getName(), obj.getPhone()) == false) {
                        Inform.initInform(formEdit, "Vui lòng nhập đầy đủ thông tin khách hàng");
                        return;
                    }

                    if (CustomerBusiness.isValidPhone(obj.getPhone()) == false) {
                        Inform.initInform(formEdit, "Số điện thoại không hợp lệ, vui lòng nhập đủ 10 số");
                        return;
                    }

                    if (CustomerBusiness.isPhoneExist(obj.getPhone(), obj.getId())) {
                        Inform.initInform(formEdit, "Số điện thoại đã tồn tại, vui lòng thử lại");
                        return;
                    }

                    CustomerBusiness.updateCustomer(obj);
                    panelCustomer.loadData();
                }

                formEdit.setVisible(false);
            }
        });
    }

    public ButtonDetailPanel(DetailCustomer formEdit, PanelCustomer panelCustomer) {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        acptBt = new JButton("Xác nhận");
        acptBt.setFont(new Font("System", Font.BOLD, 16));
        initActionUpdate(formEdit, panelCustomer);
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

class DetailCustomer extends JFrame {
    private JPanel panelId, panelName, panelPhone;
    private JTextField id, name, phone;
    private JLabel lbId, lbName, lbPhone;

    public DetailCustomer(PanelCustomer panelCustomer, Mode mode) {
        setTitle("Thông tin khách hàng");
        if (mode == Mode.ADD) {
            setSize(800, 300);
            setLayout(new GridLayout(3, 1));
        } else {
            setSize(800, 400);
            setLayout(new GridLayout(4, 1));
        }

        panelId = new JPanel();
        panelId.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbId = new JLabel("Mã khách hàng");
        lbId.setFont(new Font("System", Font.BOLD, 16));
        id = new JTextField(25);
        id.setFont(new Font("System", Font.PLAIN, 16));
        panelId.add(lbId);
        panelId.add(id);

        panelName = new JPanel();
        panelName.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbName = new JLabel("Họ và tên");
        lbName.setFont(new Font("System", Font.BOLD, 16));
        name = new JTextField(25);
        name.setFont(new Font("System", Font.PLAIN, 16));
        panelName.add(lbName);
        panelName.add(name);

        panelPhone = new JPanel();
        panelPhone.setLayout(new FlowLayout(FlowLayout.LEFT));
        lbPhone = new JLabel("Số điện thoại");
        lbPhone.setFont(new Font("System", Font.BOLD, 16));
        phone = new JTextField(25);
        phone.setFont(new Font("System", Font.PLAIN, 16));
        panelPhone.add(lbPhone);
        panelPhone.add(phone);

        if (mode == Mode.EDIT) {
            add(panelId);
        }
        add(panelName);
        add(panelPhone);

        setLocationRelativeTo(null);
    }

    public JTextField getTxtId() {
        return id;
    }

    public JTextField getTxtName() {
        return name;
    }

    public JTextField getTxtPhone() {
        return phone;
    }
}

class SearchPanel extends JPanel {
    private JButton searchBt;
    private JTextArea text;
    private JLabel label;

    public void initActionSearch(PanelCustomer panelCustomer) {
        searchBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String keyword = text.getText();
                if (keyword.equals("") == false) {
                    ArrayList<Customer> list = CustomerBusiness.showListCustomer(keyword);

                    DefaultTableModel dtm = panelCustomer.getDtm();
                    dtm.setRowCount(0);
                    for (Customer i : list) {
                        Object[] row = { i.getId(), i.getName(), i.getPhone() };
                        dtm.addRow(row);
                    }
                    panelCustomer.getTableCustomer().setModel(dtm);
                }
            }
        });
    }

    public SearchPanel(PanelCustomer panelCustomer) {
        setBorder(new TitledBorder("Nhập thông tin tìm kiếm"));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        text = new JTextArea(1, 25);
        text.setFont(new Font("System", Font.PLAIN, 16));
        label = new JLabel("Từ khóa");
        label.setFont(new Font("System", Font.BOLD, 16));
        searchBt = new JButton("Tìm kiếm");
        searchBt.setFont(new Font("System", Font.BOLD, 16));

        add(label);
        add(text);
        add(searchBt);

        initActionSearch(panelCustomer);

        setVisible(true);
    }

    public JButton getSearchBt() {
        return searchBt;
    }

    public JTextArea getText() {
        return text;
    }
}

class PanelCustomer extends JPanel {
    private JTable tableCustomer;
    private DefaultTableModel dtm;

    public PanelCustomer() {
        setLayout(new GridLayout(1, 1));

        tableCustomer = new JTable();
        String[] nameColumns = { "Mã khách hàng", "Họ và tên", "Số điện thoại" };
        dtm = new DefaultTableModel(nameColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData();

        tableCustomer.setModel(dtm);
        tableCustomer.setRowSelectionAllowed(true);
        tableCustomer.getTableHeader().setFont(new Font("System", Font.BOLD, 16));
        tableCustomer.setRowHeight(50);
        tableCustomer.setFont(new Font("System", Font.PLAIN, 16));
        tableCustomer.setBorder(new LineBorder(new Color(0, 0, 0)));

        tableCustomer.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableCustomer.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableCustomer.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableCustomer);
        scrollPane.setBorder(new EmptyBorder(0, 150, 0, 150));

        add(scrollPane);

        setVisible(true);
    }

    public void loadData() {
        dtm.setRowCount(0);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();

            String sql = "select * from customer";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Object[] row = { rs.getString("idCustomer"), rs.getString("name"), rs.getString("phone") };
                dtm.addRow(row);
            }
        } catch (SQLException e) {
            Logger.getLogger(CustomerUI.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public JTable getTableCustomer() {
        return tableCustomer;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }
}

class PanelButton extends JPanel {
    private JButton addBt, delBt, editBt, reloadBt;

    public PanelButton(PanelCustomer panelCustomer, JFrame menuCustomer) {
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
                panelCustomer.loadData();
            }
        });

        addBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                DetailCustomer formEdit = new DetailCustomer(panelCustomer, Mode.ADD);
                ButtonDetailPanel buttonDetailPanel = new ButtonDetailPanel(formEdit, panelCustomer);
                formEdit.add(buttonDetailPanel);
                formEdit.getTxtId().setEditable(false);
                formEdit.setVisible(true);
            }
        });

        delBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = panelCustomer.getTableCustomer().getSelectedRow();

                if (row != -1) {
                    String id = String.valueOf(panelCustomer.getTableCustomer().getValueAt(row, 0));
                    if (CustomerBusiness.deleteCustomer(id) == false) {
                        Inform.initInform(menuCustomer, "Khách hàng đang có hóa đơn, không thể xóa");
                        return;
                    }
                    panelCustomer.loadData();
                }
            }
        });

        editBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = panelCustomer.getTableCustomer().getSelectedRow();
                if (row != -1) {
                    DetailCustomer formEdit = new DetailCustomer(panelCustomer, Mode.EDIT);
                    ButtonDetailPanel buttonDetailPanel = new ButtonDetailPanel(formEdit, panelCustomer);
                    formEdit.add(buttonDetailPanel);
                    String id = String.valueOf(panelCustomer.getTableCustomer().getValueAt(row, 0));
                    Customer obj = CustomerBusiness.showCustomerDetail(id);

                    formEdit.getTxtId().setEditable(false);
                    formEdit.getTxtId().setText(obj.getId());
                    formEdit.getTxtName().setText(obj.getName());
                    formEdit.getTxtPhone().setText(obj.getPhone());

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

public class CustomerUI {

    public static void showMenu() {
        JFrame menuCustomer = new JFrame("Khách hàng");
        menuCustomer.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuCustomer.setLayout(new BorderLayout());

        PanelCustomer panelCustomer = new PanelCustomer();
        SearchPanel searchPanel = new SearchPanel(panelCustomer);
        panelCustomer.loadData();
        PanelButton panelButton = new PanelButton(panelCustomer, menuCustomer);

        menuCustomer.add(searchPanel, BorderLayout.NORTH);
        menuCustomer.add(panelCustomer, BorderLayout.CENTER);
        menuCustomer.add(panelButton, BorderLayout.SOUTH);

        menuCustomer.setResizable(true);
        menuCustomer.setLocationRelativeTo(null);
        menuCustomer.setVisible(true);
    }
}