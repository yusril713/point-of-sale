package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.JComboBox;
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

public class FLaporanTransaksiTahunan {

	JFrame frame;
	static FLaporanTransaksiTahunan window;
	private JComboBox<String> cmbTahun;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FLaporanTransaksiTahunan();
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
	public FLaporanTransaksiTahunan() {
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
		frame.setBounds(100, 100, 271, 104);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTahun = new JLabel("Tahun");
		lblTahun.setBounds(10, 11, 46, 16);
		frame.getContentPane().add(lblTahun);
		
		cmbTahun = new JComboBox<String>();
		cmbTahun.setBounds(68, 6, 178, 26);
		frame.getContentPane().add(cmbTahun);
		
		JButton btnCetak = new JButton("Cetak");
		btnCetak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cetak();
			}
		});
		btnCetak.setBounds(157, 43, 89, 26);
		frame.getContentPane().add(btnCetak);
	}
	
	private void TampilTahun() {
		db.con();
		try {
			cmbTahun.removeAllItems();
			String query = "select distinct date_format(tanggal, '%Y') as tahun from tb_transaksi order by tahun desc";
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
	
	private String AmbilTotalTransaksi(String tgl) {
		int tot = 0;
		db.con();
		try {
			String query = "select total_bayar from tb_transaksi where "
					+ "date_format(tanggal, '%Y') = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, tgl);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tot += rs.getInt("total_bayar");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		String hasil = "Rp. " + FMain.FormatAngka(tot);
		return hasil;
	}
	
	private int AmbilTotalReturn(String tgl) {
		int hasil = 0;
		db.con();
		try {
			String query = "select sum(subtotal) as total_return from tb_return_penjualan where date_format(tb_return_penjualan.tanggal, '%Y') = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, tgl);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hasil += rs.getInt("total_return");
			}
			
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return hasil;
	}
	
	private void Cetak() {
		window.frame.setEnabled(false);
		String totTrans = AmbilTotalTransaksi(cmbTahun.getSelectedItem().toString());
		int totalReturn = AmbilTotalReturn(cmbTahun.getSelectedItem().toString());
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\LaporanTransaksiTahunan.jasper";
			param.put("paramTanggal", cmbTahun.getSelectedItem().toString());
			param.put("paramTotalTransaksi", totTrans);
			param.put("paramTotalReturn", totalReturn);
			
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
					FLaporanTransaksiTahunan.window.frame.setEnabled(true);
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
