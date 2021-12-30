package source;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import pole.Display;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FMain {

	JFrame frame;
	static FMain window;
	static String user = null;
	static String level = null;
	static String nama = null;
	private int konfirm = 0;
	private JMenu mnUserLogin;
	private JMenu mnMaster;
	private JMenu mnTransaksi;
	private JMenu mnLaporan;
	private JMenuItem mntmUser;
	private JMenuItem mntmKaryawan;
	private JMenuItem mntmPelanggan;
	private JMenu mnBarang;
	private JMenuItem mntmStokBarang;
	private JMenuItem mntmSatuanBarang;
	private JMenuItem mntmSupplier;
	private JMenuItem mntmDiskon;
	private JMenuItem mntmProfilPemilik;
	private JMenuItem mntmTunai;
	private JMenuItem mntmKredit;
	private JMenuItem mntmPembelian;
	private JMenuItem mntmPembayaranPiutang;
	private JMenu mnTransaksiTunai;
	private JMenuItem mntmLaporanBulanan;
	private JMenuItem mntmLaporanHarian;
	private JMenuItem mntmLaporanTahunan;
	private JMenu mnTransaksiKredit;
	private JMenuItem mntmLaporanHarian_1;
	private JMenuItem mntmLaporanBulanan_1;
	private JMenuItem mntmLaporanTahunan_1;
	private JMenuItem mntmPembelian_1;
	private JMenuItem mntmLogout;
	private JMenuItem mntmHutang;
	private JMenu mnRugiLaba;
	private JMenuItem mntmHarian;
	private JMenuItem mntmBulanan;
	private JMenuItem mntmTahunan;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	static Display d;
	public static String PORT;
	public static int BIT_PER_SECOND;
	public static int STOP_BITS;
	public static int PARITY;
	public static int DATA_BITS;
	private SwingWorker<String, Object> sw;
	private JMenuItem mntmCashflow;
	private JMenuItem mntmSemua;
	private JMenuItem mntmBarcode;
	private JMenu mnReturn;
	private JMenuItem mntmPenjualan;
	private JMenuItem mntmPembelian_2;
	private JMenuItem mntmBarangRusak;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FMain();
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
	public FMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Aplikasi Penjualan");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				mnUserLogin.setText("User login: " + nama + "(" + level + ")");
				sw = new SwingWorker<String, Object>() {
					@Override
					protected String doInBackground() throws Exception {
						d = new Display();
						d.StartDisplay();
						if (d.portFound)
							d.ShowOpening("    ***WELCOME***   ", 
									       " MEGA DEPO BANGUNAN ");
						return null;
					}
				};
				Thread t1 = new Thread(sw);
				t1.setPriority(Thread.MAX_PRIORITY);
				t1.start();
				
				
				if (level.equals("kasir")) {
					mntmUser.setEnabled(false);
					mntmKaryawan.setEnabled(false);
					mntmProfilPemilik.setEnabled(false);
					mntmDiskon.setEnabled(false);
					mnLaporan.setEnabled(false);
					mntmHutang.setEnabled(false);
				} else if(level.equals("kasir_utama")) {
					mntmUser.setEnabled(false);
					mntmKaryawan.setEnabled(false);
					mntmProfilPemilik.setEnabled(false);
					mntmDiskon.setEnabled(false);
					mntmHutang.setEnabled(false);
					mntmLaporanBulanan.setEnabled(false);
					mntmLaporanTahunan.setEnabled(false);
					mnTransaksiKredit.setEnabled(false);
					mnRugiLaba.setEnabled(false);
					mntmPembelian_1.setEnabled(false);
				}
 			}
			@Override
			public void windowClosing(WindowEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menuptup aplikasi?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (d.portFound)
						d.close();
					System.exit(0); 
					
				}
			}
		});
		frame.setBounds(100, 100, 1920, 1080);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FMain.class.getResource("/img/Toko.png")));
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnMaster = new JMenu("Master");
		mnMaster.setIcon(new ImageIcon(FMain.class.getResource("/img/master.png")));
		menuBar.add(mnMaster);
		
		mntmUser = new JMenuItem("User");
		mntmUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FUser.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnMaster.add(mntmUser);
		
		mntmKaryawan = new JMenuItem("Karyawan");
		mntmKaryawan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FKaryawan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnMaster.add(mntmKaryawan);
		
		mntmPelanggan = new JMenuItem("Pelanggan");
		mntmPelanggan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FPelanggan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnMaster.add(mntmPelanggan);
		
		mnBarang = new JMenu("Barang");
		mnMaster.add(mnBarang);
		
		mntmStokBarang = new JMenuItem("Stok Barang");
		mntmStokBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FStokBarangNew.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmStokBarang);
		
		mntmSatuanBarang = new JMenuItem("Satuan Barang");
		mntmSatuanBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FSatuan.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		mntmBarangRusak = new JMenuItem("Barang Rusak");
		mntmBarangRusak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FBarangRusak.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmBarangRusak);
		mnBarang.add(mntmSatuanBarang);
		
		mntmSupplier = new JMenuItem("Supplier");
		mntmSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FSupplier.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		mntmBarcode = new JMenuItem("Barcode & Rak");
		mntmBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FBarcode.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmBarcode);
		mnBarang.add(mntmSupplier);
		
		mntmDiskon = new JMenuItem("Diskon");
		mntmDiskon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FDiskon.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmDiskon);
		
		mntmProfilPemilik = new JMenuItem("Profil Pemilik");
		mntmProfilPemilik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FProfilPemilik.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnMaster.add(mntmProfilPemilik);
		
		mnTransaksi = new JMenu("Transaksi");
		mnTransaksi.setIcon(new ImageIcon(FMain.class.getResource("/img/transaction.png")));
		menuBar.add(mnTransaksi);
		
		mntmTunai = new JMenuItem("Tunai\r\n");
		mntmTunai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTransaksi.main(null);
				FTransaksi.transkaksiKredit = false;
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmTunai);
		
		mntmKredit = new JMenuItem("Kredit");
		mntmKredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FTransaksi.main(null);
				FTransaksi.transkaksiKredit = true;
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmKredit);
		
		mntmPembelian = new JMenuItem("Pembelian");
		mntmPembelian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FPembelian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmPembelian);
		
		mntmPembayaranPiutang = new JMenuItem("Pembayaran Piutang");
		mntmPembayaranPiutang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FPiutang.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		mntmHutang = new JMenuItem("Hutang");
		mntmHutang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FPiutang.main(null);
				FPiutang.hutang = true;
				window.frame.setEnabled(false);
			}
		});
		
		mnReturn = new JMenu("Return");
		mnTransaksi.add(mnReturn);
		
		mntmPenjualan = new JMenuItem("Penjualan");
		mntmPenjualan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnPenjualan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnReturn.add(mntmPenjualan);
		
		mntmPembelian_2 = new JMenuItem("Pembelian");
		mntmPembelian_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnPembelian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnReturn.add(mntmPembelian_2);
		mnTransaksi.add(mntmHutang);
		mnTransaksi.add(mntmPembayaranPiutang);
		
		mntmCashflow = new JMenuItem("Cashflow");
		mntmCashflow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FCashflowData.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmCashflow);
		
		mnLaporan = new JMenu("Laporan");
		mnLaporan.setIcon(new ImageIcon(FMain.class.getResource("/img/Report.png")));
		menuBar.add(mnLaporan);
		
		mnTransaksiTunai = new JMenu("Transaksi Tunai");
		mnLaporan.add(mnTransaksiTunai);
		
		mntmLaporanHarian = new JMenuItem("Laporan Harian");
		mntmLaporanHarian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiHarian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiTunai.add(mntmLaporanHarian);
		
		mntmLaporanBulanan = new JMenuItem("Laporan Bulanan");
		mntmLaporanBulanan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiBulanan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiTunai.add(mntmLaporanBulanan);
		
		mntmLaporanTahunan = new JMenuItem("Laporan Tahunan");
		mntmLaporanTahunan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiTahunan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiTunai.add(mntmLaporanTahunan);
		
		mnTransaksiKredit = new JMenu("Transaksi Kredit");
		mnLaporan.add(mnTransaksiKredit);
		
		mntmLaporanHarian_1 = new JMenuItem("Laporan Harian");
		mntmLaporanHarian_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiKreditHarian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiKredit.add(mntmLaporanHarian_1);
		
		mntmLaporanBulanan_1 = new JMenuItem("Laporan Bulanan");
		mntmLaporanBulanan_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiKreditBulanan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiKredit.add(mntmLaporanBulanan_1);
		
		mntmLaporanTahunan_1 = new JMenuItem("Laporan Tahunan");
		mntmLaporanTahunan_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiKreditTahunan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiKredit.add(mntmLaporanTahunan_1);
		
		mntmPembelian_1 = new JMenuItem("Pembelian");
		mntmPembelian_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanPembelian.main(null);
				window.frame.setEnabled(false);
				
			}
		});
		
		mnRugiLaba = new JMenu("Rugi Laba");
		mnLaporan.add(mnRugiLaba);
		
		mntmHarian = new JMenuItem("Harian");
		mntmHarian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanRugiLabaHarian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnRugiLaba.add(mntmHarian);
		
		mntmBulanan = new JMenuItem("Bulanan");
		mntmBulanan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanRugiLabaBulanan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnRugiLaba.add(mntmBulanan);
		
		mntmTahunan = new JMenuItem("Tahunan");
		mntmTahunan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanRugiLabaTahunan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnRugiLaba.add(mntmTahunan);
		
		mntmSemua = new JMenuItem("Semua");
		mntmSemua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanRugiLaba l = new FLaporanRugiLaba();
				l.Cetak();
			}
		});
		mnRugiLaba.add(mntmSemua);
		mnLaporan.add(mntmPembelian_1);
		
		mnNewMenu = new JMenu("Database");
		mnNewMenu.setIcon(new ImageIcon(FMain.class.getResource("/img/Setting.png")));
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Backup");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FBackup.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Restore");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FRestore.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		mnUserLogin = new JMenu("User Login:");
		mnUserLogin.setIcon(new ImageIcon(FMain.class.getResource("/img/User.png")));
		menuBar.add(mnUserLogin);
		
		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				konfirm = JOptionPane.showConfirmDialog(null, "Logout aplikasi?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (d.portFound)
						d.close();
					window.frame.dispose();
					FLogin.main(null);
				}
			}
		});
		mnUserLogin.add(mntmLogout);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FMain.class.getResource("/img/bg.jpg")));
		frame.getContentPane().add(lblNewLabel, BorderLayout.CENTER);
	}

	static String FormatAngka(int Angka){
		NumberFormat numberFormatter = new DecimalFormat("#,##0");
		return numberFormatter.format(Angka);
	}
	
	static String FormatAngka(long Angka){
		NumberFormat numberFormatter = new DecimalFormat("#,##0");
		return numberFormatter.format(Angka);
	}
}
