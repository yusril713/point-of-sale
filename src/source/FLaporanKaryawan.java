package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FLaporanKaryawan {

	JFrame frame;
	static FLaporanKaryawan window;
	static boolean kredit = false;
	private JXDatePicker datePicker;
	private JXDatePicker datePicker_1;
	private JComboBox cmbOrder;
	private JComboBox cmbKaryawan;
	private Database db;
	private Calendar cal = Calendar.getInstance();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private JLabel lblSd;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FLaporanKaryawan();
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
	public FLaporanKaryawan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				db = new Database();
				datePicker.setVisible(false);
				datePicker_1.setVisible(false);
				lblSd.setVisible(false);
				AmbilDataKaryawan();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 426, 195);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 20, 125, 0, 127, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		cmbOrder = new JComboBox();
		cmbOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbOrder.getSelectedIndex() == 0 || cmbOrder.getSelectedIndex() == 1) {
					datePicker.setVisible(false);
					datePicker_1.setVisible(false);
					lblSd.setVisible(false);
				} else {
					datePicker.setVisible(true);
					datePicker_1.setVisible(true);
					lblSd.setVisible(true);
				}
			}
		});
		cmbOrder.setModel(new DefaultComboBoxModel(new String[] {"Hari ini", "Bulan ini", "Berdasarkan Tanggal"}));
		GridBagConstraints gbc_cmbOrder = new GridBagConstraints();
		gbc_cmbOrder.gridwidth = 3;
		gbc_cmbOrder.insets = new Insets(0, 0, 5, 5);
		gbc_cmbOrder.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbOrder.gridx = 1;
		gbc_cmbOrder.gridy = 1;
		frame.getContentPane().add(cmbOrder, gbc_cmbOrder);
		
		datePicker = new JXDatePicker();
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.fill = GridBagConstraints.BOTH;
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.gridx = 3;
		gbc_datePicker.gridy = 2;
		frame.getContentPane().add(datePicker, gbc_datePicker);
		
		lblSd = new JLabel("s.d");
		GridBagConstraints gbc_lblSd = new GridBagConstraints();
		gbc_lblSd.insets = new Insets(0, 0, 5, 5);
		gbc_lblSd.gridx = 4;
		gbc_lblSd.gridy = 2;
		frame.getContentPane().add(lblSd, gbc_lblSd);
		
		datePicker_1 = new JXDatePicker();
		GridBagConstraints gbc_datePicker_1 = new GridBagConstraints();
		gbc_datePicker_1.fill = GridBagConstraints.BOTH;
		gbc_datePicker_1.insets = new Insets(0, 0, 5, 0);
		gbc_datePicker_1.gridx = 5;
		gbc_datePicker_1.gridy = 2;
		frame.getContentPane().add(datePicker_1, gbc_datePicker_1);
		
		JLabel lblNewLabel = new JLabel("Karyawan");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 3;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		cmbKaryawan = new JComboBox();
		GridBagConstraints gbc_cmbKaryawan = new GridBagConstraints();
		gbc_cmbKaryawan.insets = new Insets(0, 0, 5, 0);
		gbc_cmbKaryawan.gridwidth = 3;
		gbc_cmbKaryawan.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbKaryawan.gridx = 3;
		gbc_cmbKaryawan.gridy = 3;
		frame.getContentPane().add(cmbKaryawan, gbc_cmbKaryawan);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!kredit) {
					Cetak("LaporanKaryawan");
				} else {
					Cetak("LaporanKaryawanKredit");
				}
			}
		});
		GridBagConstraints gbc_btnPrint = new GridBagConstraints();
		gbc_btnPrint.fill = GridBagConstraints.VERTICAL;
		gbc_btnPrint.anchor = GridBagConstraints.EAST;
		gbc_btnPrint.gridx = 5;
		gbc_btnPrint.gridy = 4;
		frame.getContentPane().add(btnPrint, gbc_btnPrint);
	}

	private void AmbilDataKaryawan() {
		db.con();
		cmbKaryawan.removeAllItems();
		cmbKaryawan.addItem("Pilih Karyawan");

		try {
			String query = "select * from tb_karyawan order by nama";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				cmbKaryawan.addItem(rs.getString("id") + " - " + rs.getString("nama"));
			}
			rs.close();
			st.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Cetak(String report) {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
	        String[] arrOfStr = cmbKaryawan.getSelectedItem().toString().split(" - ", 2);
			String reportName = ".\\laporan\\"+ report +".jasper";
			if (cmbOrder.getSelectedIndex() == 0) {
				param.put("paramMulai", java.sql.Date.valueOf(df.format(cal.getTime())));
				param.put("paramSampai", java.sql.Date.valueOf(df.format(cal.getTime())));
				param.put("paramOrder", new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime())); 
			} else if (cmbOrder.getSelectedIndex() == 1) {
				Calendar calFirst = Calendar.getInstance();
				Calendar calLast = Calendar.getInstance();
			    int lastDate = calLast.getActualMaximum(Calendar.DATE);
			    int firstDate = calFirst.getActualMinimum(Calendar.DATE);

			    calFirst.set(Calendar.DATE, firstDate);
			    calLast.set(Calendar.DATE, lastDate);
			    param.put("paramMulai", java.sql.Date.valueOf(df.format(calFirst.getTime())));
				param.put("paramSampai", java.sql.Date.valueOf(df.format(calLast.getTime())));
				param.put("paramOrder", new SimpleDateFormat("MMMM yyyy").format(cal.getTime())); 
			} else if (cmbOrder.getSelectedIndex() == 2) {
				param.put("paramMulai", java.sql.Date.valueOf(df.format(datePicker.getDate())));
				param.put("paramSampai", java.sql.Date.valueOf(df.format(datePicker_1.getDate())));
				param.put("paramOrder", new SimpleDateFormat("dd MMMM yyyy").format(datePicker.getDate()) + " - " + new SimpleDateFormat("dd MMMM yyyy").format(datePicker_1.getDate())); 
			}
			param.put("paramIdKaryawan", arrOfStr[0]);
			
			System.out.println(param.toString());
			
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
					FLaporanKaryawan.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			window.frame.setEnabled(true);
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}
}
