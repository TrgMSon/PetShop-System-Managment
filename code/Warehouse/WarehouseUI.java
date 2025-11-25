package Warehouse;

import Mode.Mode;
import Connection.DataConnection;
import Format.Format;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

class WarehousePanel extends JPanel {
    private JTable tableWarehouse;
    private DefaultTableModel dtm;

    public void loadData() {
        dtm.setRowCount(0);

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();

            String sql = "select * from warehouse order by maxCapacity";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Object[] row = { rs.getString("idWarehouse"), rs.getString("address"),
                        Format.normalizeNumber(String.valueOf(rs.getInt("maxCapacity"))),
                        Format.normalizeNumber(
                                String.valueOf(WarehouseBusiness.getCurrentCapacity(rs.getString("idWarehouse")))) };
                dtm.addRow(row);
            }
        } catch (SQLException e) {
            Logger.getLogger(WarehouseUI.class.getName()).log(Level.SEVERE, null, e);
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

    public WarehousePanel() {
        setLayout(new GridLayout(1, 1));

        tableWarehouse = new JTable();
        String[] nameColumns = { "Mã kho", "Địa chỉ", "Sức chứa tối đa", "Sức chứa đã dùng" };
        dtm = new DefaultTableModel(nameColumns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData();

        tableWarehouse.setModel(dtm);
        tableWarehouse.setBorder(new LineBorder(new Color(0, 0, 0)));
        tableWarehouse.setRowSelectionAllowed(true);
        tableWarehouse.getTableHeader().setFont(new Font("System", Font.BOLD, 16));
        tableWarehouse.setFont(new Font("System", Font.PLAIN, 16));
        tableWarehouse.setRowHeight(50);

        tableWarehouse.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableWarehouse.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableWarehouse.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableWarehouse.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableWarehouse);
        scrollPane.setBorder(new EmptyBorder(0, 50, 0, 50));

        add(scrollPane);

        setVisible(true);
    }

    public JTable getTableWarehouse() {
        return tableWarehouse;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }
}

class SearchPanel extends JPanel {
    private JTextArea text;
    private JLabel label;
    private JButton searchBt;

    public void initActionSearch(WarehousePanel warehousePanel) {
        searchBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String keyword = text.getText();
                if (keyword.equals("") == false) {
                    ArrayList<Warehouse> list = WarehouseBusiness.showListWarehouse(keyword);

                    DefaultTableModel dtm = warehousePanel.getDtm();
                    dtm.setRowCount(0);
                    for (Warehouse i : list) {
                        Object[] row = { i.getIdWarehouse(), i.getAddress(),
                                Format.normalizeNumber(String.valueOf(i.getMaxCapacity())),
                                Format.normalizeNumber(
                                        String.valueOf(WarehouseBusiness.getCurrentCapacity(i.getIdWarehouse()))) };
                        dtm.addRow(row);
                    }
                    warehousePanel.getTableWarehouse().setModel(dtm);
                }
            }
        });
    }

    public SearchPanel(WarehousePanel warehousePanel) {
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

        initActionSearch(warehousePanel);

        setVisible(true);
    }

    public JTextArea getText() {
        return text;
    }

    public JButton getSearchBt() {
        return searchBt;
    }
}

class ButtonPanel extends JPanel {
    private JButton addBt, delBt, editBt, detailBt, reloadBt;
    private String idWarehouse;

    public void initActionReload(WarehousePanel warehousePanel) {
        reloadBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                warehousePanel.loadData();
            }
        });
    }

    public void initActionDelete(WarehousePanel warehousePanel) {
        delBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = warehousePanel.getTableWarehouse().getSelectedRow();
                if (row != -1) {
                    idWarehouse = (String) warehousePanel.getTableWarehouse().getValueAt(row, 0);
                    WarehouseBusiness.deleteWarehouse(idWarehouse);
                    warehousePanel.loadData();
                }
            }
        });
    }

    public void initActionEdit(WarehousePanel warehousePanel) {
        editBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = warehousePanel.getTableWarehouse().getSelectedRow();

                if (row != -1) {
                    idWarehouse = (String) warehousePanel.getTableWarehouse().getValueAt(row, 0);
                    WarehouseDetailUI.showWarehouseDetail(idWarehouse, Mode.EDIT, warehousePanel);
                    row = -1;
                }
            }
        });
    }

    public void initDetailAction(WarehousePanel warehousePanel) {
        detailBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = warehousePanel.getTableWarehouse().getSelectedRow();

                if (row != -1) {
                    String idWarehouse = (String) warehousePanel.getTableWarehouse().getValueAt(row, 0);
                    WarehouseDetailUI.showWarehouseDetail(idWarehouse, Mode.SHOW, warehousePanel);
                }
            }
        });
    }

    public void initActionAdd(WarehousePanel warehousePanel) {
        addBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                WarehouseDetailUI.showWarehouseDetail("", Mode.ADD, warehousePanel);
            }
        });
    }

    public ButtonPanel(WarehousePanel warehousePanel) {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        reloadBt = new JButton("Làm mới");
        reloadBt.setFont(new Font("System", Font.BOLD, 16));
        addBt = new JButton("Thêm");
        addBt.setFont(new Font("System", Font.BOLD, 16));
        delBt = new JButton("Xóa");
        delBt.setFont(new Font("System", Font.BOLD, 16));
        editBt = new JButton("Sửa");
        editBt.setFont(new Font("System", Font.BOLD, 16));
        detailBt = new JButton("Chi tiết");
        detailBt.setFont(new Font("System", Font.BOLD, 16));

        add(reloadBt);
        add(addBt);
        add(delBt);
        add(editBt);
        add(detailBt);

        initActionReload(warehousePanel);
        initDetailAction(warehousePanel);
        initActionAdd(warehousePanel);
        initActionEdit(warehousePanel);
        initActionDelete(warehousePanel);

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

public class WarehouseUI {
    public static void showMenu() {
        JFrame menuWarehouse = new JFrame("Kho");
        menuWarehouse.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuWarehouse.setLayout(new BorderLayout());

        WarehousePanel warehousePanel = new WarehousePanel();
        SearchPanel searchPanel = new SearchPanel(warehousePanel);
        ButtonPanel buttonPanel = new ButtonPanel(warehousePanel);

        menuWarehouse.add(searchPanel, BorderLayout.NORTH);
        menuWarehouse.add(warehousePanel, BorderLayout.CENTER);
        menuWarehouse.add(buttonPanel, BorderLayout.SOUTH);

        menuWarehouse.setLocationRelativeTo(null);
        menuWarehouse.setVisible(true);
    }
}
