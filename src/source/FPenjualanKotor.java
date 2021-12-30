package source;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;

public class FPenjualanKotor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FPenjualanKotor window = new FPenjualanKotor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FPenjualanKotor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 140, 119, 207, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		JPanel panel = new JPanel();
		  JFreeChart chart = createChart(createDataset());
	        
	        JComboBox<String> comboBox = new JComboBox<String>();
	        GridBagConstraints gbc_comboBox = new GridBagConstraints();
	        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
	        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
	        gbc_comboBox.gridx = 1;
	        gbc_comboBox.gridy = 1;
	        frame.getContentPane().add(comboBox, gbc_comboBox);
	        ChartPanel panelc = new ChartPanel(chart);
		panel.setLayout(new java.awt.BorderLayout());
		panel.add(panelc);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		frame.getContentPane().add(panel, gbc_panel);
	}
	
	private static JFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        JFreeChart chart = ChartFactory.createLineChart(
            "Grafik Penjualan Kotor",   // chart title
            null,                       // domain axis label
            "Penjualan Kotor",                   // range axis label
            dataset,                         // data
            PlotOrientation.VERTICAL,        // orientation
            false,                           // include legend
            true,                            // tooltips
            false                            // urls
        );

        chart.addSubtitle(new TextTitle("Number of Classes By Release"));
        TextTitle source = new TextTitle(
                "Source: Java In A Nutshell (5th Edition) "
                + "by David Flanagan (O'Reilly)");
        source.setFont(new Font("SansSerif", Font.PLAIN, 10));
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
//        plot.setRangePannable(true);
        plot.setRangeGridlinesVisible(false);
        
        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartUtilities.applyCurrentTheme(chart);

        // customise the renderer...
        LineAndShapeRenderer renderer
                = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setBaseFillPaint(Color.white);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
//        renderer.setSeriesOutlineStroke(0, new BasicStroke(5.0f));
//        renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
        return chart;
    }
	
	 private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(212, "Classes", "JDK 1.0");
        dataset.addValue(504, "Classes", "JDK 1.1");
        dataset.addValue(1520, "Classes", "JDK 1.2");
        dataset.addValue(1842, "Classes", "JDK 1.3");
        dataset.addValue(2991, "Classes", "JDK 1.4");
        dataset.addValue(3500, "Classes", "JDK 1.5");
        return dataset;
    }

}
