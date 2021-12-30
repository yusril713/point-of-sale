package source;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class FLaporanRugiLaba {
	private Database db;
	public FLaporanRugiLaba() {
		db = new Database();
	}
	
	private long AmbilTotalPiutang() {
		db.con();
		try {
			String query = "select sum(sisa_hutang)as tot from tb_hutang "
					+ "join tb_transaksi_kredit on tb_hutang.no_faktur = tb_transaksi_kredit.no_faktur";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
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
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan "
					+ "UNION "
					+ "select tb_barang.nama, sum(tb_detail_transaksi_kredit.qty) as qty, (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty)) as hrg_beli, "
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) as hrg_jual, ("
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) - (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty))) as laba, tb_detail_transaksi_kredit.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "join tb_detail_transaksi_kredit on tb_detail_transaksi_kredit.id_barang = tb_detail_barang.id_barang "
					+ "and tb_detail_barang.satuan = tb_detail_transaksi_kredit.satuan "
					+ "join tb_transaksi_kredit on tb_transaksi_kredit.no_faktur = tb_detail_transaksi_kredit.no_faktur "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
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
			String query = "select sum(jumlah) as jumlah from tb_cashflow where jenis = ? ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, jenis);
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
	
	private int AmbilTotalReturn() {
		int hasil = 0;
		db.con();
		try {
			String query = "select (harga - harga_beli) as modal, jumlah from tb_detail_return_penjualan";
			PreparedStatement ps = db.con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hasil += rs.getInt("modal") * rs.getInt("jumlah");
			}
			
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return hasil;
	}
	
	public void Cetak() {
		int totalReturn = AmbilTotalReturn();
		long labaKotor = (AmbilTotalRugiLaba() - AmbilTotalPiutang() - totalReturn);
		long labaUsaha = labaKotor - AmbilCashFlow("Pengeluaran");
		long labaBersih = labaUsaha + AmbilCashFlow("Pemasukan");
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\LaporanRugiLaba.jasper";
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
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}
}
