package Report;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

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

public class BestSellerReport extends JFrame {

	private JTable table;
	private DefaultTableModel dtm;
	private String target;
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
					BestSellerReport bestSellerReport = new BestSellerReport("");
					bestSellerReport.setVisible(true);
				} catch(Exception e) {
					Logger.getLogger(BestSellerReport.class.getName()).log(Level.SEVERE, null, e);
				}
			}
		});
	}

	public void loadData() {
		totalAmount = new BigDecimal("0");
		totalQuantity = 0;
		dtm.setRowCount(0);

		Connection conn = null;
		try {
			conn = DataConnection.setConnect();

			String sql = """
                            SELECT p.idProduct, p.name, SUM(it.quantity) AS sumQty, p.cost FROM product AS p
                             JOIN invoicedetail AS it ON p.idProduct = it.idProduct
                             JOIN invoice AS i ON i.idInvoice = it.idInvoice 
                             WHERE i.date LIKE ?
                             GROUP BY p.idProduct
                             ORDER BY sumQty DESC, p.name ASC
                    """;
			PreparedStatement psm = conn.prepareStatement(sql);
			psm.setString(1, "%" + target + "%");
			ResultSet rs = psm.executeQuery();

			while (rs.next()) {
				String idProduct = rs.getString("idProduct");
				String name = rs.getString("name");
				int sumQty = rs.getInt("sumQty");
				BigDecimal cost = rs.getBigDecimal("cost");
				totalQuantity += sumQty;
				totalAmount = totalAmount.add(cost.multiply(new BigDecimal(String.valueOf(sumQty))));
				
				Object[] row = {
					idProduct, name, sumQty, Format.normalizeNumber(String.valueOf(cost))
				};
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
		storeName.setBorder(new EmptyBorder(10, 10, 0, 0));
		storeInfor.add(storeName);

		JLabel storeAddress = new JLabel("Địa chỉ: Phường Hà Đông, Thành phố Hà Nội");
		storeAddress.setVerticalAlignment(SwingConstants.TOP);
		storeAddress.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		storeAddress.setBorder(new EmptyBorder(0, 10, 70, 0));
		storeInfor.add(storeAddress);
	}

	public void createReportHeader() {
		reportHeader = new JPanel();
		storeInfor.add(reportHeader);
		reportHeader.setLayout(new BorderLayout(0, 0));

		JLabel reportName = new JLabel("BÁO CÁO SẢN PHẨM BÁN CHẠY");
		reportName.setFont(new Font("Times New Roman", Font.BOLD, 24));
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportHeader.add(reportName, BorderLayout.NORTH);

		String dateSell = "";
		JLabel reportDate;
		String[] tmp = target.trim().split("-");
		if (tmp.length == 2) {
			LocalDate localDate = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 1);
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");
			dateSell = fmt.format(localDate);
			reportDate = new JLabel("THÁNG: " + dateSell);
		}
		else if (tmp.length == 1) {
			LocalDate localDate = LocalDate.of(Integer.parseInt(tmp[0]), 1, 1);
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy");
			dateSell = fmt.format(localDate);
			reportDate = new JLabel("NĂM: " + dateSell);
		}
		else {
			LocalDate localDate = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			dateSell = fmt.format(localDate);
			reportDate = new JLabel("NGÀY: " + dateSell);
		}

		reportDate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		reportDate.setHorizontalAlignment(SwingConstants.CENTER);
		reportHeader.add(reportDate, BorderLayout.CENTER);
	}

	public void createTableHeader() {
		tableHeader = new JPanel();
		tableHeader.setBorder(new EmptyBorder(0, 180, 0, 180));
		FlowLayout flowLayout = (FlowLayout) tableHeader.getLayout();
		flowLayout.setHgap(0);

		JLabel headerIdProduct = new JLabel("MÃ SẢN PHẨM");
		headerIdProduct.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerIdProduct.setBackground(new Color(102, 204, 102));
		headerIdProduct.setOpaque(true);
		headerIdProduct.setPreferredSize(new Dimension(150, 50));
		headerIdProduct.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerIdProduct.setHorizontalAlignment(SwingConstants.CENTER);
		tableHeader.add(headerIdProduct);

		JLabel headerName = new JLabel("TÊN SẢN PHẨM");
		headerName.setBorder(new LineBorder(new Color(0, 0, 0)));
		headerName.setBackground(new Color(102, 204, 102));
		headerName.setOpaque(true);
		headerName.setHorizontalAlignment(SwingConstants.CENTER);
		headerName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerName.setPreferredSize(new Dimension(490, 50));
		tableHeader.add(headerName);

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
		headerCost.setPreferredSize(new Dimension(250, 50));
		headerCost.setFont(new Font("Times New Roman", Font.BOLD, 18));
		headerCost.setHorizontalAlignment(SwingConstants.CENTER);
		tableHeader.add(headerCost);
	}

	public void createTableContent() {
		tableContent = new JPanel(new BorderLayout(0, 0));

		table = new JTable();
		table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		table.setRowHeight(28);
		String[] nameColumns = { "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá" };
		dtm = new DefaultTableModel(nameColumns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		loadData();

		table.setModel(dtm);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 280, 6, 280));
		scrollPane.setViewportView(table);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		tableContent.add(scrollPane, BorderLayout.CENTER);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(440);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
	}

	public void createSummary() {
		summary = new JPanel();
		summary.setLayout(new FlowLayout(FlowLayout.RIGHT));
		summary.setBorder(new EmptyBorder(0, 10, 5, 280));
		summary.setPreferredSize(new Dimension(400, 300));
		FlowLayout summary_layout = (FlowLayout) summary.getLayout();
		summary_layout.setHgap(0);
		tableContent.add(summary, BorderLayout.SOUTH);
		
		JLabel lbl_sum = new JLabel("TỔNG CỘNG");
		lbl_sum.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_sum.setPreferredSize(new Dimension(640, 30));
		lbl_sum.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lbl_sum.setHorizontalAlignment(SwingConstants.CENTER);
		summary.add(lbl_sum);

		JLabel lbl_qty = new JLabel(String.valueOf(totalQuantity));
		lbl_qty.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_qty.setPreferredSize(new Dimension(150, 30));
		lbl_qty.setFont(new Font("Times New Roman", Font.BOLD, 20));
		summary.add(lbl_qty);

		JLabel lbl_totalAmount = new JLabel(Format.normalizeNumber(String.valueOf(totalAmount)));
		lbl_totalAmount.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_totalAmount.setPreferredSize(new Dimension(250, 30));
		lbl_totalAmount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		summary.add(lbl_totalAmount);
	}


	public BestSellerReport(String target) {
		this.target = target;

		setTitle("Báo cáo");
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
