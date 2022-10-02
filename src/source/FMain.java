package source;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import pole.Display;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SpringLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
	private JMenuItem mntmNewMenuItem_2;
	private JMenuItem mntmNewMenuItem_3;
	private JMenuItem mntmDO;
	private JLabel lblTanggal;
	private JMenuItem mntmDashboard;

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
				//New Features
//				mntmDashboard.setVisible(false);
				
				final DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss");
			    int interval = 1000; // 1000 ms

			    new Timer(interval, new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            Calendar now = Calendar.getInstance();
			            lblTanggal.setText(dateFormat.format(now.getTime()));
			        }
			    }).start();
				
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
					mntmNewMenuItem_2.setEnabled(false);
					mntmNewMenuItem_3.setEnabled(false);
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
					mntmNewMenuItem_2.setEnabled(false);
					mntmNewMenuItem_3.setEnabled(false);
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
		mnMaster.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnMaster.setIcon(new ImageIcon(FMain.class.getResource("/img/master.png")));
		menuBar.add(mnMaster);
		
		mntmUser = new JMenuItem("User");
		mntmUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FUser.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		mntmDashboard = new JMenuItem("Dashboard");
		mntmDashboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FDashboard.main(null);
				window.frame.setEnabled(false);
			}
		});
		mntmDashboard.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnMaster.add(mntmDashboard);
		mnMaster.add(mntmUser);
		
		mntmKaryawan = new JMenuItem("Karyawan");
		mntmKaryawan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmKaryawan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FKaryawan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnMaster.add(mntmKaryawan);
		
		mntmPelanggan = new JMenuItem("Pelanggan");
		mntmPelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmPelanggan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FPelanggan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnMaster.add(mntmPelanggan);
		
		mnBarang = new JMenu("Barang");
		mnBarang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnMaster.add(mnBarang);
		
		mntmStokBarang = new JMenuItem("Stok Barang");
		mntmStokBarang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmStokBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FStokBarangNew.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmStokBarang);
		
		mntmSatuanBarang = new JMenuItem("Satuan Barang");
		mntmSatuanBarang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmSatuanBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FSatuan.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		mntmBarangRusak = new JMenuItem("Barang Rusak");
		mntmBarangRusak.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmBarangRusak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FBarangRusak.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmBarangRusak);
		mnBarang.add(mntmSatuanBarang);
		
		mntmSupplier = new JMenuItem("Supplier");
		mntmSupplier.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FSupplier.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		mntmBarcode = new JMenuItem("Barcode & Rak");
		mntmBarcode.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FBarcode.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmBarcode);
		mnBarang.add(mntmSupplier);
		
		mntmDiskon = new JMenuItem("Diskon");
		mntmDiskon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmDiskon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FDiskon.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnBarang.add(mntmDiskon);
		
		mntmProfilPemilik = new JMenuItem("Profil Pemilik");
		mntmProfilPemilik.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmProfilPemilik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FProfilPemilik.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnMaster.add(mntmProfilPemilik);
		
		mnTransaksi = new JMenu("Transaksi");
		mnTransaksi.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnTransaksi.setIcon(new ImageIcon(FMain.class.getResource("/img/transaction.png")));
		menuBar.add(mnTransaksi);
		
		mntmTunai = new JMenuItem("Tunai\r\n");
		mntmTunai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmTunai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTransaksi.main(null);
				FTransaksi.transkaksiKredit = false;
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmTunai);
		
		mntmPembelian = new JMenuItem("Pembelian");
		mntmPembelian.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmPembelian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FPembelian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmPembelian);
		
		mntmPembayaranPiutang = new JMenuItem("Pembayaran Piutang");
		mntmPembayaranPiutang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmPembayaranPiutang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FPiutang.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		mntmHutang = new JMenuItem("Hutang");
		mntmHutang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmHutang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FPiutang.main(null);
				FPiutang.hutang = true;
				window.frame.setEnabled(false);
			}
		});
		
		mnReturn = new JMenu("Return");
		mnReturn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnTransaksi.add(mnReturn);
		
		mntmPenjualan = new JMenuItem("Penjualan");
		mntmPenjualan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmPenjualan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnPenjualan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnReturn.add(mntmPenjualan);
		
		mntmPembelian_2 = new JMenuItem("Pembelian");
		mntmPembelian_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmPembelian_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnPembelian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnReturn.add(mntmPembelian_2);
		
		mntmKredit = new JMenuItem("Kredit");
		mntmKredit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmKredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FTransaksi.main(null);
				FTransaksi.transkaksiKredit = true;
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmKredit);
		
		mntmDO = new JMenuItem("Pengambilan Barang DO");
		mntmDO.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmDO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FDO.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmDO);
		mnTransaksi.add(mntmHutang);
		mnTransaksi.add(mntmPembayaranPiutang);
		
		mntmCashflow = new JMenuItem("Cashflow");
		mntmCashflow.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmCashflow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FCashflowData.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksi.add(mntmCashflow);
		
		mnLaporan = new JMenu("Laporan");
		mnLaporan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnLaporan.setIcon(new ImageIcon(FMain.class.getResource("/img/Report.png")));
		menuBar.add(mnLaporan);
		
		mnTransaksiTunai = new JMenu("Transaksi Tunai");
		mnTransaksiTunai.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnLaporan.add(mnTransaksiTunai);
		
		mntmLaporanHarian = new JMenuItem("Laporan Harian");
		mntmLaporanHarian.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLaporanHarian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiHarian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiTunai.add(mntmLaporanHarian);
		
		mntmLaporanBulanan = new JMenuItem("Laporan Bulanan");
		mntmLaporanBulanan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLaporanBulanan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiBulanan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiTunai.add(mntmLaporanBulanan);
		
		mntmLaporanTahunan = new JMenuItem("Laporan Tahunan");
		mntmLaporanTahunan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLaporanTahunan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiTahunan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiTunai.add(mntmLaporanTahunan);
		
		mntmNewMenuItem_2 = new JMenuItem("Laporan Berdasarkan Karyawan");
		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanKaryawan.main(null);
				FLaporanKaryawan.kredit = false;
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiTunai.add(mntmNewMenuItem_2);
		
		mnTransaksiKredit = new JMenu("Transaksi Kredit");
		mnTransaksiKredit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnLaporan.add(mnTransaksiKredit);
		
		mntmLaporanHarian_1 = new JMenuItem("Laporan Harian");
		mntmLaporanHarian_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLaporanHarian_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiKreditHarian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiKredit.add(mntmLaporanHarian_1);
		
		mntmLaporanBulanan_1 = new JMenuItem("Laporan Bulanan");
		mntmLaporanBulanan_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLaporanBulanan_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiKreditBulanan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiKredit.add(mntmLaporanBulanan_1);
		
		mntmLaporanTahunan_1 = new JMenuItem("Laporan Tahunan");
		mntmLaporanTahunan_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLaporanTahunan_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanTransaksiKreditTahunan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiKredit.add(mntmLaporanTahunan_1);
		
		mntmNewMenuItem_3 = new JMenuItem("Laporan Berdasarkan Karyawan");
		mntmNewMenuItem_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanKaryawan.main(null);
				FLaporanKaryawan.kredit = true;
				window.frame.setEnabled(false);
			}
		});
		mnTransaksiKredit.add(mntmNewMenuItem_3);
		
		mntmPembelian_1 = new JMenuItem("Pembelian");
		mntmPembelian_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmPembelian_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanPembelian.main(null);
				window.frame.setEnabled(false);
				
			}
		});
		
		mnRugiLaba = new JMenu("Rugi Laba");
		mnRugiLaba.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnLaporan.add(mnRugiLaba);
		
		mntmHarian = new JMenuItem("Harian");
		mntmHarian.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmHarian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanRugiLabaHarian.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnRugiLaba.add(mntmHarian);
		
		mntmBulanan = new JMenuItem("Bulanan");
		mntmBulanan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmBulanan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanRugiLabaBulanan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnRugiLaba.add(mntmBulanan);
		
		mntmTahunan = new JMenuItem("Tahunan");
		mntmTahunan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmTahunan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FLaporanRugiLabaTahunan.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnRugiLaba.add(mntmTahunan);
		
		mntmSemua = new JMenuItem("Semua");
		mntmSemua.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmSemua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FLaporanRugiLaba l = new FLaporanRugiLaba();
				l.Cetak();
			}
		});
		mnRugiLaba.add(mntmSemua);
		mnLaporan.add(mntmPembelian_1);
		
		mnNewMenu = new JMenu("Database");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.setIcon(new ImageIcon(FMain.class.getResource("/img/Setting.png")));
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Backup");
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FBackup.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Restore");
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FRestore.main(null);
				window.frame.setEnabled(false);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		mnUserLogin = new JMenu("User Login:");
		mnUserLogin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnUserLogin.setIcon(new ImageIcon(FMain.class.getResource("/img/User.png")));
		menuBar.add(mnUserLogin);
		
		mntmLogout = new JMenuItem("Logout");
		mntmLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
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
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		ImagePanel lblNewLabel = new ImagePanel(new ImageIcon(FMain.class.getResource("/img/home.png")).getImage());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, 0, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(lblNewLabel);
		SpringLayout sl_lblNewLabel = new SpringLayout();
		lblNewLabel.setLayout(sl_lblNewLabel);
		
		lblTanggal = new JLabel("");
		lblTanggal.setFont(new Font("Segoe UI", Font.BOLD, 22));
		sl_lblNewLabel.putConstraint(SpringLayout.NORTH, lblTanggal, 10, SpringLayout.NORTH, lblNewLabel);
		sl_lblNewLabel.putConstraint(SpringLayout.EAST, lblTanggal, -10, SpringLayout.EAST, lblNewLabel);
		lblNewLabel.add(lblTanggal);
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
