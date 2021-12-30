package source;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class FLaporanTransaksiKreditTahunan {

	JFrame frame;
	static FLaporanTransaksiKreditTahunan window;
	private JComboBox<String> cmbTahun;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FLaporanTransaksiKreditTahunan();
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
	public FLaporanTransaksiKreditTahunan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				TampilTahun();
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 302, 105);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTahun = new JLabel("Tahun");
		lblTahun.setBounds(10, 14, 46, 16);
		frame.getContentPane().add(lblTahun);
		
		cmbTahun = new JComboBox<String>();
		cmbTahun.setBounds(89, 9, 186, 26);
		frame.getContentPane().add(cmbTahun);
		
		JButton btnCetak = new JButton("Cetak");
		btnCetak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cetak();
			}
		});
		btnCetak.setBounds(186, 42, 89, 26);
		frame.getContentPane().add(btnCetak);
	}
	
	private void TampilTahun() {
		db.con();
		try {
			cmbTahun.removeAllItems();
			String query = "select distinct date_format(tanggal, '%Y') as tahun from tb_transaksi_kredit order by tahun desc";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				cmbTahun.addItem(rs.getString("tahun"));
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.err.println("Error tampil tanggal " + e);
		}
	}
	
	private void Cetak() {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\LaporanTransaksiKreditTahunan.jasper";
			param.put("paramTanggal", cmbTahun.getSelectedItem().toString());
			
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
					FLaporanTransaksiKreditTahunan.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}
}
