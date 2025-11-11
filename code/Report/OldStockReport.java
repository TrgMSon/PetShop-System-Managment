package Report;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.DataConnection;
import Format.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OldStockReport extends JFrame {

	private JTable table;
	private DefaultTableModel dtm;
	private int duration;
	private JScrollPane scrollPane;
	private BigDecimal totalAmount;
	private int totalQuantity;
	private JPanel storeInfor, reportHeader, reportContent, tableHeader, tableContent, summary;
	private int row;

	public int getCountRow() {
		return row;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OldStockReport frame = new OldStockReport(50);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadData() {
		totalAmount = new BigDecimal("0");
		totalQuantity = 0;

		dtm.setRowCount(0);

		LocalDate now = LocalDate.now();
		LocalDate old = now.minusDays(duration);
		DateTimeFormatter normalize = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String oldDate = normalize.format(old);

		Connection conn = null;
		try {
			conn = DataConnection.setConnect();

			String sql = """
					SELECT p.idProduct, p.name AS nameProduct, p.cost, wd.lastReceiveDate, wd.quantityInStock, wd.idWarehouse
					 FROM warehousedetail AS wd
					 JOIN product AS p ON p.idProduct = wd.idProductW
					 WHERE wd.lastReceiveDate <= ? AND wd.quantityInStock > 0
					 ORDER BY wd.lastReceiveDate;
					                                        """;
			PreparedStatement psm = conn.prepareStatement(sql);
			psm.setString(1, oldDate);
			ResultSet rs = psm.executeQuery();

			while (rs.next()) {
				BigDecimal item = rs.getBigDecimal("cost");
				int qty = rs.getInt("quantityInStock");
				totalQuantity += qty;
				totalAmount = totalAmount.add(item.multiply(new BigDecimal(qty)));

				Object[] row = { rs.getString("idWarehouse"), rs.getString("idProduct"), rs.getString("nameproduct"),
						ReportBusiness.normalizeDate(rs.getString("lastReceiveDate")),
						rs.getInt("quantityInStock"),
						Format.normalizeNumber(String.valueOf(rs.getBigDecimal("cost"))) };
				dtm.addRow(row);
			}
			row = dtm.getRowCount();
		} catch (SQLException e) {
			Logger.getLogger(ReportUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void createStoreInfor() {
		storeInfor = new JPanel();
		getContentPane().add(storeInfor, BorderLayout.NORTH);
		storeInfor.setLayout(new GridLayout(3, 1, 0, 0));

		JLabel storeName = new JLabel("Cửa hàng thú cưng");
		storeName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		storeName.setBorder(new EmptyBorder(0, 10, 0, 0));
		storeInfor.add(storeName);

		JLabel storeAddress = new JLabel("Địa chỉ: Phường Hà Đông, Thành phố Hà Nội");
		storeAddress.setVerticalAlignment(SwingConstants.TOP);
		storeAddress.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		storeAddress.setBorder(new EmptyBorder(0, 10, 0, 0));
		storeInfor.add(storeAddress);
	}

	public void createReportHeader() {
		reportHeader = new JPanel();
		storeInfor.add(reportHeader);
		reportHeader.setLayout(new BorderLayout(0, 0));

		JLabel reportName = new JLabel("DANH SÁCH HÀNG TỒN KHO");
		reportName.setFont(new Font("Times New Roman", Font.BOLD, 24));
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportHeader.add(reportName, BorderLayout.NORTH);

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter normalize = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateNow = normalize.format(localDate);
		JLabel reportDate = new JLabel("NGÀY: " + dateNow);
		reportDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		reportDate.setHorizontalAlignment(SwingConstants.CENTER);
		reportHeader.add(reportDate, BorderLayout.CENTER);

		JLabel reportWarehouse = new JLabel("KHO: TẤT CẢ KHO");
		reportWarehouse.setBorder(new EmptyBorder(0, 0, 40, 0));
		reportWarehouse.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		reportWarehouse.setHorizontalAlignment(SwingConstants.CENTER);
		reportHeader.add(reportWarehouse, BorderLayout.SOUTH);
	}

	public void createTableHeader() {
		tableHeader = new JPanel();
		tableHeader.setBorder(new EmptyBorder(0, 110, 0, 120));
		// tableHeader.setBackground(new Color(102, 204, 102));
		FlowLayout flowLayout = (FlowLayout) tableHeader.getLayout();
		flowLayout.setHgap(0);

		JLabel headerIdWarehouse = new JLabel("MÃ KHO");
		headerIdWarehouse.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerIdWarehouse.setBackground(new Color(102, 204, 102));
		headerIdWarehouse.setOpaque(true);
		headerIdWarehouse.setPreferredSize(new Dimension(135, 50));
		headerIdWarehouse.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerIdWarehouse.setHorizontalAlignment(SwingConstants.CENTER);
		tableHeader.add(headerIdWarehouse);

		JLabel headerIdProduct = new JLabel("MÃ SẢN PHẨM");
		headerIdProduct.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerIdProduct.setBackground(new Color(102, 204, 102));
		headerIdProduct.setOpaque(true);
		headerIdProduct.setPreferredSize(new Dimension(145, 50));
		headerIdProduct.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerIdProduct.setHorizontalAlignment(SwingConstants.CENTER);
		tableHeader.add(headerIdProduct);

		JLabel headerName = new JLabel("TÊN SẢN PHẨM");
		headerName.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerName.setBackground(new Color(102, 204, 102));
		headerName.setOpaque(true);
		headerName.setHorizontalAlignment(SwingConstants.CENTER);
		headerName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerName.setPreferredSize(new Dimension(440, 50));
		tableHeader.add(headerName);

		JLabel headerLastReceive = new JLabel("NGÀY CUỐI NHẬP KHO");
		headerLastReceive.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerLastReceive.setBackground(new Color(102, 204, 102));
		headerLastReceive.setOpaque(true);
		headerLastReceive.setPreferredSize(new Dimension(220, 50));
		headerLastReceive.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerLastReceive.setHorizontalAlignment(SwingConstants.CENTER);
		tableHeader.add(headerLastReceive);

		JLabel headerQty = new JLabel("SỐ LƯỢNG");
		headerQty.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerQty.setBackground(new Color(102, 204, 102));
		headerQty.setOpaque(true);
		headerQty.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerQty.setHorizontalAlignment(SwingConstants.CENTER);
		headerQty.setPreferredSize(new Dimension(150, 50));
		tableHeader.add(headerQty);

		JLabel headerCost = new JLabel("GIÁ (VND)");
		headerCost.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerCost.setBackground(new Color(102, 204, 102));
		headerCost.setOpaque(true);
		headerCost.setPreferredSize(new Dimension(200, 50));
		headerCost.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerCost.setHorizontalAlignment(SwingConstants.CENTER);
		tableHeader.add(headerCost);
	}

	public void createTableContent() {
		tableContent = new JPanel(new BorderLayout(0, 0));

		table = new JTable();
		table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		table.setRowHeight(28);
		String[] nameColumns = { "Mã kho", "Mã sản phẩm", "Tên sản phẩm", "Ngày cuối nhập kho", "Số lượng", "Giá" };
		dtm = new DefaultTableModel(nameColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		loadData();

		table.setModel(dtm);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 150, 6, 160));
		scrollPane.setViewportView(table);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		tableContent.add(scrollPane, BorderLayout.CENTER);

		table.getColumnModel().getColumn(0).setPreferredWidth(134);
		table.getColumnModel().getColumn(1).setPreferredWidth(144);
		table.getColumnModel().getColumn(2).setPreferredWidth(440);
		table.getColumnModel().getColumn(3).setPreferredWidth(218);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(197);
	}

	public void createSummary() {
		summary = new JPanel();
		summary.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summary.setBorder(new EmptyBorder(0, 10, 5, 158));
		summary.setPreferredSize(new Dimension(400, 200));
		FlowLayout summary_layout = (FlowLayout) summary.getLayout();
		summary_layout.setHgap(0);
		tableContent.add(summary, BorderLayout.SOUTH);
		
		JLabel lbl_sum = new JLabel("TỔNG CỘNG");
		lbl_sum.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_sum.setPreferredSize(new Dimension(940, 30));
		lbl_sum.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lbl_sum.setHorizontalAlignment(SwingConstants.CENTER);
		summary.add(lbl_sum);

		JLabel lbl_qty = new JLabel(String.valueOf(totalQuantity));
		lbl_qty.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_qty.setPreferredSize(new Dimension(152, 30));
		lbl_qty.setFont(new Font("Times New Roman", Font.BOLD, 20));
		summary.add(lbl_qty);

		JLabel lbl_totalAmount = new JLabel(Format.normalizeNumber(String.valueOf(totalAmount)));
		lbl_totalAmount.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_totalAmount.setPreferredSize(new Dimension(200, 30));
		lbl_totalAmount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		summary.add(lbl_totalAmount);
	}


	public OldStockReport(int duration) {
		this.duration = duration;

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 10));
		setBounds(100, 100, 1036, 763);
		getContentPane().setLayout(new BorderLayout(0, 0));

		createStoreInfor();
		createReportHeader();

		reportContent = new JPanel();
		getContentPane().add(reportContent, BorderLayout.CENTER);
		reportContent.setLayout(new BorderLayout(0, 0));

		createTableHeader();
		createTableContent();
		createSummary();
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(1600, 500));

		tableContent.setBounds(0, 50, 1600, 500);
		layeredPane.add(tableContent, JLayeredPane.DEFAULT_LAYER);

		tableHeader.setBounds(0, 18, 1600, table.getTableHeader().getPreferredSize().height + 30);
		layeredPane.add(tableHeader, JLayeredPane.PALETTE_LAYER);

		reportContent.add(layeredPane, BorderLayout.CENTER);
	}
}
