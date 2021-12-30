package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class FLaporanTransaksiKreditBulanan {

	JFrame frame;
	static FLaporanTransaksiKreditBulanan window;
	private JComboBox<String> cmbTahun;
	private JComboBox<String> cmbBulan;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FLaporanTransaksiKreditBulanan();
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
	public FLaporanTransaksiKreditBulanan() {
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
		frame.setBounds(100, 100, 302, 128);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTahun = new JLabel("Tahun");
		lblTahun.setBounds(10, 14, 46, 16);
		frame.getContentPane().add(lblTahun);
		
		JLabel lblBulan = new JLabel("Bulan");
		lblBulan.setBounds(10, 39, 46, 16);
		frame.getContentPane().add(lblBulan);
		
		cmbTahun = new JComboBox<String>();
		cmbTahun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cmbTahun.getSelectedIndex() >= 0)
					TampilBulan();
			}
		});
		cmbTahun.setBounds(92, 9, 183, 26);
		frame.getContentPane().add(cmbTahun);
		
		cmbBulan = new JComboBox<String>();
		cmbBulan.setBounds(92, 34, 183, 26);
		frame.getContentPane().add(cmbBulan);
		
		JButton btnCetak = new JButton("Cetak");
		btnCetak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cetak();
			}
		});
		btnCetak.setBounds(186, 67, 89, 26);
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
	
	private void TampilBulan() {
		db.con();
		try {
			cmbBulan.removeAllItems();
			String query = "select distinct date_format(tanggal, '%m') as bulan "
					+ "from tb_transaksi_kredit where date_format(tanggal, '%Y') = ?"
					+ "order by cast(bulan as unsigned) desc";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, cmbTahun.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cmbBulan.addItem(rs.getString("bulan"));
			}
			ps.close();
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
			
			String reportName = ".\\laporan\\LaporanTransaksiKreditBulanan.jasper";
			param.put("paramTanggal", cmbBulan.getSelectedItem().toString() + cmbTahun.getSelectedItem().toString());
			
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
					FLaporanTransaksiKreditBulanan.window.frame.setEnabled(true);
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
