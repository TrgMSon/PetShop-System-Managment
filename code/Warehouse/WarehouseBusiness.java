package Warehouse;

import Connection.DataConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;

public class WarehouseBusiness {
    public static boolean addWarehouse(Warehouse warehouse) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();

            String sql = "insert into warehouse(idWarehouse, address, maxCapacity) values(?, ?, ?)";
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, warehouse.getIdWarehouse());
            psm.setString(2, warehouse.getAddress());
            psm.setInt(3, warehouse.getMaxCapacity());

            return psm.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(WarehouseBusiness.class.getName()).log(Level.SEVERE, null, e);
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

    public static boolean deleteWarehouse(String idWarehouse) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();
            String sql = "delete from warehouse where idWarehouse = '" + idWarehouse + "'";
            Statement stm = conn.createStatement();

            return stm.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            Logger.getLogger(WarehouseBusiness.class.getName()).log(Level.SEVERE, null, e);
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

    public static Warehouse showWarehouse(String idWarehouse) {
        Warehouse warehouse = new Warehouse();

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "select * from warehouse where idWarehouse = '" + idWarehouse + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                warehouse.setIdWarehouse(idWarehouse);
                warehouse.setAddress(rs.getString("address"));
                warehouse.setMaxCapacity(rs.getInt("maxCapacity"));
            }
        } catch (SQLException e) {
            Logger.getLogger(WarehouseBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return warehouse;
    }

    public static ArrayList<Warehouse> showListWarehouse(String keyword) {
        ArrayList<Warehouse> list = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();

            String sql;
            if (keyword.contains("W"))
                sql = "SELECT * FROM warehouse WHERE idWarehouse LIKE ? ORDER BY maxCapacity";
            else if (keyword.charAt(0) >= '0' && keyword.charAt(0) <= '9')
                sql = "SELECT * FROM warehouse WHERE maxCapacity LIKE ? ORDER BY maxCapacity";
            else
                sql = "SELECT * FROM warehouse WHERE address LIKE ? ORDER BY maxCapacity";

            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, "%" + keyword + "%");
            ResultSet rs = psm.executeQuery();

            while (rs.next()) {
                list.add(new Warehouse(rs.getString("idWarehouse"), rs.getString("address"), rs.getInt("maxCapacity")));
            }
        } catch (SQLException e) {
            Logger.getLogger(WarehouseBusiness.class.getName()).log(Level.SEVERE, null, e);
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

    public static int getCurrentCapacity(String idWarehouse) {
        int sum = 0;

        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "SELECT quantityInStock FROM warehousedetail WHERE idWarehouse = '" + idWarehouse + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                sum += rs.getInt("quantityInStock");
            }
        } catch (SQLException e) {
            Logger.getLogger(WarehouseBusiness.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close connection");
            }
        }

        return sum;
    }

    public static boolean updateWarehouse(Warehouse warehouse) {
        Connection conn = null;

        try {
            conn = DataConnection.setConnect();
            String sql = "UPDATE warehouse SET address = ?, maxCapacity = ? WHERE idWarehouse = ?";
            PreparedStatement psm = conn.prepareStatement(sql);
            psm.setString(1, warehouse.getAddress());
            psm.setInt(2, warehouse.getMaxCapacity());
            psm.setString(3, warehouse.getIdWarehouse());

            return psm.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(WarehouseBusiness.class.getName()).log(Level.SEVERE, null, e);
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

    public static boolean isValidAddress(String address) {
        String[] tmp = address.trim().split(" ");
        for (String i : tmp) {
            for (int j=0; j<i.length(); j++) {
                if (i.charAt(j) >= '0' && i.charAt(j) <= '9') return false;
            }
        }
        return true;
    }

    public static boolean isExist(String idWarehouse) {
        Connection conn = null;
        try {
            conn = DataConnection.setConnect();
            String sql = "SELECT * FROM warehouse WHERE idWarehouse = '" + idWarehouse + "'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) return true;
        } catch (SQLException e) {
            Logger.getLogger(WarehouseBusiness.class.getName()).log(Level.SEVERE, null, e);
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
