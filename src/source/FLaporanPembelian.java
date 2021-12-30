package source;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXDatePicker;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class FLaporanPembelian {

	JFrame frame;
	static FLaporanPembelian window;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private JXDatePicker datePicker;
	private JXDatePicker datePicker_1;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FLaporanPembelian();
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
	public FLaporanPembelian() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Laporan Pembelian");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				datePicker.setFormats(new SimpleDateFormat("dd MMMM yyyy"));
				datePicker_1.setFormats(new SimpleDateFormat("dd MMMM yyyy"));
				db = new Database();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 323, 160);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMulaiTanggal = new JLabel("Mulai Tgl");
		lblMulaiTanggal.setBounds(10, 11, 65, 16);
		frame.getContentPane().add(lblMulaiTanggal);
		
		JLabel lblSdTanggal = new JLabel("s/d Tanggal");
		lblSdTanggal.setBounds(10, 39, 65, 16);
		frame.getContentPane().add(lblSdTanggal);
		
		datePicker = new JXDatePicker();
		datePicker.setBounds(109, 6, 185, 26);
		frame.getContentPane().add(datePicker);
		
		datePicker_1 = new JXDatePicker();
		datePicker_1.setBounds(109, 34, 185, 26);
		frame.getContentPane().add(datePicker_1);
		
		JButton btnCetak = new JButton("Cetak");
		btnCetak.setIcon(new ImageIcon(".\\icon\\printer.png"));
		btnCetak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cetak();
			}
		});
		btnCetak.setBounds(184, 65, 110, 26);
		frame.getContentPane().add(btnCetak);
		
		JButton btnEksporExcel = new JButton("Ekspor Excel");
		btnEksporExcel.setIcon(new ImageIcon(".\\icon\\export.png"));
		btnEksporExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EksporExcel();
			}
		});
		btnEksporExcel.setToolTipText("Ekspor ke Ms. Excel");
		btnEksporExcel.setBounds(184, 90, 110, 26);
		frame.getContentPane().add(btnEksporExcel);
	}
	
	private void Cetak() {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\LaporanPembelian.jasper";
			param.put("paramMulai", df.format(datePicker.getDate()));
			param.put("paramSampai", df.format(datePicker_1.getDate()));
			
			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
			JRViewer jv = new JRViewer(print);
			final JFrame jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					FLaporanPembelian.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}
	
	private void EksporExcel() {
		Calendar cal = Calendar.getInstance();
		try {
			db.con();
			Map<String, Object> param = new HashMap<String,Object>();
			String reportName = ".\\laporan\\LaporanPembelian.jasper";
			String outXlsName = System.getProperty("user.home")+"\\Desktop\\LaporanPembelian " +
				df.format(cal.getTime())+".xls";
			param.put("paramMulai", df.format(datePicker.getDate()));
			param.put("paramSampai", df.format(datePicker_1.getDate()));
			JasperPrint jp = JasperFillManager.fillReport(reportName, param, db.con);
			jp.setProperty("net.sf.jasperreports.export.xls.ignore.graphics", "true");
			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setExporterInput(new SimpleExporterInput(jp));
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outXlsName));
			SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
			new SimpleXlsExporterConfiguration();
			xlsReportConfiguration.setOnePagePerSheet(false);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setDetectCellType(true);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsExporter.setConfiguration(xlsReportConfiguration);

            xlsExporter.exportReport();
            JOptionPane.showMessageDialog(null, "Data diekspor ke Ms. Excel");
			db.con.close();
		} catch (Exception e) {
			System.out.println("error " + e);
		}
	}
}
