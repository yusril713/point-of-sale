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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class FLaporanRugiLabaBulanan {

	JFrame frame;
	static FLaporanRugiLabaBulanan window;
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
					window = new FLaporanRugiLabaBulanan();
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
	public FLaporanRugiLabaBulanan() {
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
				TampilTahun();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 307, 134);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTahun = new JLabel("Tahun");
		lblTahun.setBounds(10, 11, 67, 16);
		frame.getContentPane().add(lblTahun);
		
		JLabel lblBulan = new JLabel("Bulan");
		lblBulan.setBounds(10, 36, 67, 16);
		frame.getContentPane().add(lblBulan);
		
		cmbTahun = new JComboBox<String>();
		cmbTahun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cmbTahun.getSelectedIndex() >= 0)
					TampilBulan();
			}
		});
		cmbTahun.setBounds(99, 6, 179, 26);
		frame.getContentPane().add(cmbTahun);
		
		cmbBulan = new JComboBox<String>();
		cmbBulan.setBounds(99, 31, 179, 26);
		frame.getContentPane().add(cmbBulan);
		
		JButton btnCetak = new JButton("Cetak");
		btnCetak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cetak();
			}
		});
		btnCetak.setBounds(189, 63, 89, 26);
		frame.getContentPane().add(btnCetak);
	}
	
	private void TampilTahun() {
		db.con();
		try {
			cmbTahun.removeAllItems();
			String query = "select a.tahun from "
					+ "((select distinct date_format(tanggal, '%Y') as tahun from tb_transaksi order by tahun desc) "
					+ "union "
					+ "(select distinct date_format(tanggal, '%Y') as tahun from tb_transaksi_kredit order by tahun desc)) as a "
					+ "order by a.tahun desc";
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
			String query = "select a.bulan from "
					+ "((select distinct date_format(tanggal, '%m') as bulan "
					+ "from tb_transaksi where date_format(tanggal, '%Y') = ?"
					+ "order by cast(bulan as unsigned) desc) "
					+ "union "
					+ "(select distinct date_format(tanggal, '%m') as bulan "
					+ "from tb_transaksi_kredit where date_format(tanggal, '%Y') = ? "
					+ "order by cast(bulan as unsigned) desc)) as a "
					+ "order by a.bulan desc";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, cmbTahun.getSelectedItem().toString());
			ps.setString(2, cmbTahun.getSelectedItem().toString());
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
	
	private long AmbilTotalPiutang() {
		db.con();
		try {
			String query = "select sum(sisa_hutang)as tot from tb_hutang "
					+ "join tb_transaksi_kredit on tb_hutang.no_faktur = tb_transaksi_kredit.no_faktur "
					+ "where date_format(tb_transaksi_kredit.tanggal, '%m%Y') = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, cmbBulan.getSelectedItem().toString() 
					+ cmbTahun.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getLong("tot");
			}
		} catch(Exception e) {
			System.out.println("Piutang " + e);
		}
		return 0;
	}
	
	private long AmbilTotalRugiLaba() {
		long tot = 0;
		db.con();
		try {
			String query = "select tb_barang.nama, sum(tb_detail_transaksi.qty) as qty, (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty)) as hrg_beli, "
					+ "	(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) as hrg_jual, ("
					+ "(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) - (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty))) as laba, tb_detail_transaksi.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "join tb_detail_transaksi on tb_detail_transaksi.id_barang = tb_detail_barang.id_barang "
					+ "and tb_detail_barang.satuan = tb_detail_transaksi.satuan "
					+ "join tb_transaksi on tb_transaksi.no_faktur = tb_detail_transaksi.no_faktur "
					+ "where date_format(tb_transaksi.tanggal, '%m%Y') = ? "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan "
					+ "UNION "
					+ "select tb_barang.nama, sum(tb_detail_transaksi_kredit.qty) as qty, (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty)) as hrg_beli, "
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) as hrg_jual, ("
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) - (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty))) as laba, tb_detail_transaksi_kredit.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "join tb_detail_transaksi_kredit on tb_detail_transaksi_kredit.id_barang = tb_detail_barang.id_barang "
					+ "and tb_detail_barang.satuan = tb_detail_transaksi_kredit.satuan "
					+ "join tb_transaksi_kredit on tb_transaksi_kredit.no_faktur = tb_detail_transaksi_kredit.no_faktur "
					+ "where date_format(tb_transaksi_kredit.tanggal, '%m%Y') = ? "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, cmbBulan.getSelectedItem().toString() 
					+ cmbTahun.getSelectedItem().toString());
			ps.setString(2, cmbBulan.getSelectedItem().toString() 
					+ cmbTahun.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tot += rs.getLong("laba");
			}
		} catch(Exception e) {
			System.out.println("RL" + e);
		}
		
		return tot;
	}
	
	private long AmbilCashFlow(String jenis) {
		db.con();
		try {
			String query = "select sum(jumlah) as jumlah from tb_cashflow where jenis = ? "
					+ "AND date_format(tanggal, '%m%Y') = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, jenis);
			ps.setString(2, cmbTahun.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getLong("jumlah");
			
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int AmbilTotalReturn(String tgl) {
		int hasil = 0;
		db.con();
		try {
			String query = "select (tb_detail_return_penjualan.harga - tb_detail_return_penjualan.harga_beli) as modal, "
					+ "tb_detail_return_penjualan.jumlah from tb_return_penjualan "
					+ "join tb_detail_return_penjualan on tb_detail_return_penjualan.id_return = tb_return_penjualan.id "
					+ "where date_format(tb_return_penjualan.tanggal, '%m%Y') = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, tgl);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hasil += (rs.getInt("modal") * rs.getInt("jumlah"));
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
		int totalReturn = AmbilTotalReturn(cmbBulan.getSelectedItem().toString() + " " + cmbTahun.getSelectedItem().toString());
		long labaKotor = (AmbilTotalRugiLaba() - AmbilTotalPiutang() - totalReturn);
		long labaUsaha = labaKotor - AmbilCashFlow("Pengeluaran");
		long labaBersih = labaUsaha + AmbilCashFlow("Pemasukan");
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\LaporanRugiLabaBulanan.jasper";
			param.put("paramBulan", cmbBulan.getSelectedItem().toString() + " " + cmbTahun.getSelectedItem().toString());
			param.put("Parameter1", "" + FMain.FormatAngka(AmbilTotalRugiLaba()));
			param.put("Parameter2", "" + FMain.FormatAngka(AmbilTotalPiutang()));
			param.put("Parameter3", "" + FMain.FormatAngka(labaKotor));
			param.put("Parameter4", "" + FMain.FormatAngka(AmbilCashFlow("Pengeluaran")));
			param.put("Parameter5", "" + FMain.FormatAngka(labaUsaha));
			param.put("Parameter6", "" + FMain.FormatAngka(AmbilCashFlow("Pemasukan")));
			param.put("Parameter7", "" + FMain.FormatAngka(labaBersih));
			param.put("Parameter8", "" + FMain.FormatAngka(totalReturn));
			
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
					FLaporanRugiLabaBulanan.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}
}
