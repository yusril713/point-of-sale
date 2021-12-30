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

public class FLaporanTransaksiBulanan {

	JFrame frame;
	static FLaporanTransaksiBulanan window;
	private JComboBox<String> cmbTahun;
	private JComboBox<String> cmbBulan;
	private Database db;

	/**
	 * Launch the application.p
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FLaporanTransaksiBulanan();
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
	public FLaporanTransaksiBulanan() {
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
		frame.setLocationRelativeTo(null);
		frame.setBounds(100, 100, 271, 136);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTahun = new JLabel("Tahun");
		lblTahun.setBounds(10, 11, 46, 16);
		frame.getContentPane().add(lblTahun);
		
		JLabel lblBulan = new JLabel("Bulan");
		lblBulan.setBounds(10, 36, 46, 16);
		frame.getContentPane().add(lblBulan);
		
		cmbTahun = new JComboBox<String>();
		cmbTahun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cmbTahun.getSelectedIndex() >= 0)
					TampilBulan();
			}
		});
		cmbTahun.setBounds(66, 6, 178, 26);
		frame.getContentPane().add(cmbTahun);
		
		cmbBulan = new JComboBox<String>();
		cmbBulan.setBounds(66, 31, 178, 26);
		frame.getContentPane().add(cmbBulan);
		
		JButton btnCetak = new JButton("Cetak");
		btnCetak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cetak();
			}
		});
		btnCetak.setBounds(155, 63, 89, 26);
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
	
	private void TampilBulan() {
		db.con();
		try {
			cmbBulan.removeAllItems();
			String query = "select distinct date_format(tanggal, '%m') as bulan "
					+ "from tb_transaksi where date_format(tanggal, '%Y') = ?"
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
	
	private String AmbilTotalTransaksi(String tgl) {
		int tot = 0;
		db.con();
		try {
			String query = "select total_bayar from tb_transaksi where "
					+ "date_format(tanggal, '%m%Y') = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, tgl);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tot += rs.getInt("total_bayar");
			}
			
			query = "select jml_bayar from tb_detail_hutang "
					+ "where date_format(tgl_bayar, '%m%Y') = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, tgl);
			rs = ps.executeQuery();
			while (rs.next()) {
				tot += rs.getInt("jml_bayar");
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
			String query = "select sum(subtotal) as total_return from tb_return_penjualan where date_format(tb_return_penjualan.tanggal, '%m%Y') = ?";
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
		String totTrans = AmbilTotalTransaksi(cmbBulan.getSelectedItem().toString() + cmbTahun.getSelectedItem().toString());
		int totalReturn = AmbilTotalReturn(cmbBulan.getSelectedItem().toString() + cmbTahun.getSelectedItem().toString());
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\LaporanTransaksiBulanan.jasper";
			param.put("paramTanggal", cmbBulan.getSelectedItem().toString() + cmbTahun.getSelectedItem().toString());
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
					FLaporanTransaksiBulanan.window.frame.setEnabled(true);
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
