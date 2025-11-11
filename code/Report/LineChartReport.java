package Report;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.math.BigDecimal;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChartReport {
    private ArrayList<BigDecimal> listIncome;
    private String titleChart, titleRow, titleColumn;
    private JPanel storeInfor;
    private JFrame lineChartFrame;

    public void setListIncome(ArrayList<BigDecimal> listIncome) {
        this.listIncome = listIncome;
    }

    public void setInforChart(String titleChart, String titleRow, String titleColumn) {
        this.titleChart = titleChart;
        this.titleRow = titleRow;
        this.titleColumn = titleColumn;
    }

    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i=0; i<listIncome.size(); i++) {
            dataset.addValue((Number) listIncome.get(i), "Doanh thu (nghìn VND)", String.valueOf(i+1));
        }

        return dataset;
    }

    public JFreeChart createLineChart() {
        JFreeChart lineChart = ChartFactory.createLineChart(titleChart, titleColumn, titleRow, createDataset(), PlotOrientation.VERTICAL, false, false, false);
        return lineChart;
    }

    public void createStoreInfor() {
		storeInfor = new JPanel();
		lineChartFrame.add(storeInfor, BorderLayout.NORTH);
		storeInfor.setLayout(new GridLayout(3, 1, 0, 0));
        storeInfor.setBackground(new Color(255, 255, 255));

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

    public void show() {
        ChartPanel chartPanel = new ChartPanel(createLineChart());
        chartPanel.setPreferredSize(new Dimension(1000, 700));
        chartPanel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        lineChartFrame = new JFrame();
        lineChartFrame.setLayout(new BorderLayout(0, 0));
        lineChartFrame.add(chartPanel, BorderLayout.CENTER);

        createStoreInfor();

        lineChartFrame.setSize(1000, 700);
        lineChartFrame.setLocationRelativeTo(null);
        lineChartFrame.setResizable(false);
        lineChartFrame.setVisible(true);
    }
}