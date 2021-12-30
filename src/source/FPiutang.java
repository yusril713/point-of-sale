package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.SwingConstants;

public class FPiutang {

	JFrame frame;
	static FPiutang window;
	private JTextField txtCari;
	private static JTable table;
	private static JTable table_1;
	private JTextField txtNoFaktur;
	private JTextField txtTanggal;
	private JTextField txtJatuhTempo;
	private JTextField txtPelanggan;
	private JTextField txtSisaHutang;
	private JTextField txtTotalBayar;
	private JTextField txtTotalPembelian;
	private JTable table_2;
	private static Database db;
	static boolean hutang = false;
	private JLabel lblNoFaktur_1;
	private static JLabel lblSisaHutang_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FPiutang();
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
	public FPiutang() {
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
				KosongkanTeks();
				if (hutang == false) {
					lblSisaHutang_1.setText(null);
					lblSisaHutang_1.setVisible(false);
					TampilDataPiutang(txtCari.getText()); 
					frame.setTitle("Data Piutang");
					lblNoFaktur_1.setText("Faktur/Pelanggan");
				}
				else {
					lblSisaHutang_1.setVisible(true);
					TampilHutang(txtCari.getText()); 
					frame.setTitle("Data Hutang");
					lblNoFaktur_1.setText("Faktur/Supplier");
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				hutang = false;
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 701, 524);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Daftar Piutang", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 417, 225);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		txtCari = new JTextField();
		txtCari.setBorder(null);
		txtCari.setBackground(SystemColor.control);
		txtCari.setText("cari");
		txtCari.setBounds(134, 17, 183, 26);
		txtCari.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				if (hutang == false)
					TampilDataPiutang(txtCari.getText());
				else 
					TampilHutang(txtCari.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (hutang == false)
					TampilDataPiutang(txtCari.getText());
				else 
					TampilHutang(txtCari.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (hutang == false)
					TampilDataPiutang(txtCari.getText());
				else 
					TampilHutang(txtCari.getText());
			}
		});
		panel.add(txtCari);
		txtCari.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 397, 168);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					if (hutang == false) {
						String noFaktur = table.getValueAt(table.getSelectedRow(), 0).toString();
						txtNoFaktur.setText(noFaktur);
						TampilDetailPembayaran(noFaktur);
						TampilDetailBarang(noFaktur);
					} else {
						String noFaktur = table.getValueAt(table.getSelectedRow(), 0).toString();
						txtNoFaktur.setText(noFaktur);
						TampilDetailPembayaran(noFaktur);
						TampilDetailBarangPembelian(noFaktur);
					}
				}
				

				if (arg0.getClickCount() == 2) {
					FBayar.main(null);
					window.frame.setEnabled(false);
					FBayar.hutang = hutang;
					FBayar.noFaktur = table.getValueAt(table.getSelectedRow(), 0).toString();
					if (!hutang)
						FBayar.sisaHutang = table.getValueAt(table.getSelectedRow(), 3).toString();
					else
						FBayar.sisaHutang = table.getValueAt(table.getSelectedRow(), 1).toString();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnBayar = new JButton("Bayar");
		btnBayar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					FBayar.main(null);
					window.frame.setEnabled(false);
					FBayar.hutang = hutang;
					FBayar.noFaktur = table.getValueAt(table.getSelectedRow(), 0).toString();
					if (!hutang)
						FBayar.sisaHutang = table.getValueAt(table.getSelectedRow(), 3).toString();
					else
						FBayar.sisaHutang = table.getValueAt(table.getSelectedRow(), 1).toString();
				}
			}
		});
		btnBayar.setBounds(318, 17, 89, 26);
		panel.add(btnBayar);
		
		lblNoFaktur_1 = new JLabel("No Faktur / Nama Plg");
		lblNoFaktur_1.setBounds(10, 21, 123, 16);
		panel.add(lblNoFaktur_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Detail Pembayaran", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(437, 11, 249, 225);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 21, 229, 162);
		panel_1.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JButton btnCetak = new JButton("Cetak");
		btnCetak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hutang) {
					if(!txtNoFaktur.getText().equals("")) {
						cetak();
					}
				} 
			}
		});
		btnCetak.setBounds(150, 191, 89, 26);
		panel_1.add(btnCetak);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Detail Barang", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 247, 676, 237);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNoFaktur = new JLabel("No. Faktur");
		lblNoFaktur.setBounds(10, 22, 127, 16);
		panel_2.add(lblNoFaktur);
		
		JLabel lblNamaPelanggan = new JLabel("Nama Pelanggan");
		lblNamaPelanggan.setBounds(10, 97, 127, 16);
		panel_2.add(lblNamaPelanggan);
		
		JLabel lblSisaHutang = new JLabel("Sisa Hutang");
		lblSisaHutang.setBounds(10, 122, 127, 16);
		panel_2.add(lblSisaHutang);
		
		JLabel lblTotalYangSudah = new JLabel("Total yang Sudah Dibayar");
		lblTotalYangSudah.setBounds(10, 147, 127, 16);
		panel_2.add(lblTotalYangSudah);
		
		JLabel lblTotalPembelian = new JLabel("Total Pembelian");
		lblTotalPembelian.setBounds(10, 172, 91, 16);
		panel_2.add(lblTotalPembelian);
		
		txtNoFaktur = new JTextField();
		txtNoFaktur.setBorder(null);
		txtNoFaktur.setEditable(false);
		txtNoFaktur.setText("no faktur");
		txtNoFaktur.setBounds(145, 17, 178, 26);
		panel_2.add(txtNoFaktur);
		txtNoFaktur.setColumns(10);
		
		JLabel lblTanggalPembelian = new JLabel("Tanggal Pembelian");
		lblTanggalPembelian.setBounds(10, 47, 98, 16);
		panel_2.add(lblTanggalPembelian);
		
		JLabel lblJatuhTempo = new JLabel("Jatuh Tempo");
		lblJatuhTempo.setBounds(10, 72, 98, 16);
		panel_2.add(lblJatuhTempo);
		
		txtTanggal = new JTextField();
		txtTanggal.setBorder(null);
		txtTanggal.setEditable(false);
		txtTanggal.setText("tanggal");
		txtTanggal.setBounds(145, 42, 178, 26);
		panel_2.add(txtTanggal);
		txtTanggal.setColumns(10);
		
		txtJatuhTempo = new JTextField();
		txtJatuhTempo.setBorder(null);
		txtJatuhTempo.setEditable(false);
		txtJatuhTempo.setText("jatuh tempo");
		txtJatuhTempo.setBounds(145, 67, 178, 26);
		panel_2.add(txtJatuhTempo);
		txtJatuhTempo.setColumns(10);
		
		txtPelanggan = new JTextField();
		txtPelanggan.setBorder(null);
		txtPelanggan.setEditable(false);
		txtPelanggan.setText("pelanggan");
		txtPelanggan.setBounds(145, 92, 178, 26);
		panel_2.add(txtPelanggan);
		txtPelanggan.setColumns(10);
		
		txtSisaHutang = new JTextField();
		txtSisaHutang.setBorder(null);
		txtSisaHutang.setEditable(false);
		txtSisaHutang.setText("sisa hutang");
		txtSisaHutang.setBounds(145, 117, 178, 26);
		panel_2.add(txtSisaHutang);
		txtSisaHutang.setColumns(10);
		
		txtTotalBayar = new JTextField();
		txtTotalBayar.setBorder(null);
		txtTotalBayar.setEditable(false);
		txtTotalBayar.setText("total bayar");
		txtTotalBayar.setBounds(145, 142, 178, 26);
		panel_2.add(txtTotalBayar);
		txtTotalBayar.setColumns(10);
		
		txtTotalPembelian = new JTextField();
		txtTotalPembelian.setBorder(null);
		txtTotalPembelian.setEditable(false);
		txtTotalPembelian.setText("total pembelian");
		txtTotalPembelian.setBounds(145, 167, 178, 26);
		panel_2.add(txtTotalPembelian);
		txtTotalPembelian.setColumns(10);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(333, 19, 333, 144);
		panel_2.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		lblSisaHutang_1 = new JLabel("Sisa Hutang");
		lblSisaHutang_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSisaHutang_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSisaHutang_1.setBounds(335, 167, 331, 26);
		panel_2.add(lblSisaHutang_1);
	}
	
	private void KosongkanTeks() {
		txtCari.setText("");
		txtJatuhTempo.setText("");
		txtNoFaktur.setText("");
		txtPelanggan.setText("");
		txtSisaHutang.setText("");
		txtTanggal.setText("");
		txtTotalBayar.setText("");
		txtTotalPembelian.setText("");
	}
	
	static void TampilDataPiutang(String str) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("No. Faktur");
		model.addColumn("Nama Pelanggan");
		model.addColumn("Alamat");
		model.addColumn("Sisa Hutang");
		model.addColumn("Tanggal Jatuh Tempo");
		db.con();
		try {
			String query = "select a.no_faktur, b.sisa_hutang, "
					+ "c.nama, c.alamat, "
					+ "date_format(a.tanggal_jatuh_tempo, '%d %M %Y') as jatuh_tempo "
					+ "from tb_transaksi_kredit a "
					+ "inner join tb_hutang b on a.no_faktur = b.no_faktur "
					+ "inner join tb_pelanggan c on a.id_pelanggan = c.id "
					+ "where (a.no_faktur like ? or c.nama like ?) "
					+ "and b.sisa_hutang > 0 order by a.tanggal asc";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" +str + "%");
			ps.setString(2, "%" +str + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("nama"),
						rs.getString("alamat"),
						FMain.FormatAngka(rs.getInt("sisa_hutang")),
						rs.getString("jatuh_tempo")
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			table.getColumnModel().getColumn(1).setPreferredWidth(85);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(80);
		} catch(Exception e) {
			System.out.println("error tampil data piutang " + e);
		}
	}
	
	static void TampilDetailPembayaran(String noFaktur) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("Tgl Bayar");
		model.addColumn("Jumlah");
		db.con();
		try {
			String query = "select * from tb_detail_hutang where no_faktur = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("tgl_bayar"),
						FMain.FormatAngka(rs.getInt("jml_bayar"))
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			table_1.setModel(model);
			table_1.setAutoResizeMode(0);
			table_1.getColumnModel().getColumn(0).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(110);
		} catch(Exception e) {
			System.out.println("error tampil detail pembayaran " + e);
		}
	}
	
	private void TampilDetailBarang(String noFaktur) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Qty");
		model.addColumn("Harga");
		db.con();
		try {
			String query = "select a.no_faktur, "
					+ "date_format(a.tanggal, '%d %M %Y')as tgl, "
					+ "date_format(a.tanggal_jatuh_tempo, '%d %M %Y')as jatuh_tempo, "
					+ "b.nama as nama_pelanggan, c.sisa_hutang, "
					+ "(select sum(jml_bayar)from tb_detail_hutang where no_faktur = ?) as jumlah_bayar, "
					+ "a.total_bayar as sub_total, "
					+ "e.id_barang, f.nama as nama_barang, e.qty, "
					+ "e.harga_jual from tb_transaksi_kredit a "
					+ "inner join tb_pelanggan b on a.id_pelanggan = b.id "
					+ "inner join tb_hutang c on a.no_faktur = c.no_faktur "
					+ "inner join tb_detail_transaksi_kredit e on a.no_faktur = e.no_faktur "
					+ "inner join tb_barang f on e.id_barang = f.id "
					+ "where a.no_faktur = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ps.setString(2, noFaktur);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtNoFaktur.setText(rs.getString("no_faktur"));
				txtTanggal.setText(rs.getString("tgl"));
				txtJatuhTempo.setText(rs.getString("jatuh_tempo"));
				txtPelanggan.setText(rs.getString("nama_pelanggan"));
				txtSisaHutang.setText(FMain.FormatAngka(rs.getInt("sisa_hutang")));
				txtTotalBayar.setText(rs.getString("jumlah_bayar") == null ? "-" : 
					FMain.FormatAngka(rs.getInt("jumlah_bayar")));
				txtTotalPembelian.setText(FMain.FormatAngka(rs.getInt("sub_total")));
				
				model.addRow(new Object[] {
						rs.getString("id_barang"),
						rs.getString("nama_barang"),
						rs.getString("qty"),
						FMain.FormatAngka(rs.getInt("harga_jual"))
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			
			table_2.setModel(model);
			table_2.setAutoResizeMode(0);
			table_2.getColumnModel().getColumn(0).setPreferredWidth(80);
			table_2.getColumnModel().getColumn(1).setPreferredWidth(110);
			table_2.getColumnModel().getColumn(2).setPreferredWidth(50);
			table_2.getColumnModel().getColumn(3).setPreferredWidth(80);
		} catch(Exception e) {
			System.out.println("error tampil detail barang " + e);
		}
	}
	
	static void TampilHutang(String cari) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("No. Faktur");
		model.addColumn("Sisa Hutang");
		model.addColumn("Nama Supplier");
		model.addColumn("Tanggal Jatuh Tempo");
		
		db.con();
		try {
			String query = "select tb_pembelian.*, "
					+ "tb_hutang.sisa_hutang, tb_supplier.nama as nama_supplier "
					+ "from tb_pembelian "
					+ "join tb_hutang on tb_pembelian.no_faktur = tb_hutang.no_faktur "
					+ "join tb_supplier on tb_pembelian.supplier = tb_supplier.id "
					+ "where tb_pembelian.jatuh_tempo is not null and (tb_pembelian.no_faktur like ? "
					+ "or tb_supplier.nama like ?) and tb_hutang.sisa_hutang > 0";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + cari + "%");
			ps.setString(2, "%" + cari + "%");
			ResultSet rs = ps.executeQuery();
			int totSisaHutang = 0;
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						FMain.FormatAngka(rs.getInt("sisa_hutang")),
						rs.getString("nama_supplier"),
						rs.getString("jatuh_tempo")
				});
				totSisaHutang += rs.getInt("sisa_hutang");
			}
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
			table.getColumnModel().getColumn(3).setPreferredWidth(120);
			
			lblSisaHutang_1.setText("Total Hutang Rp. " + FMain.FormatAngka(totSisaHutang));
		} catch(Exception e) {
			System.out.println("error tampil hutang " + e);
		}
	}
	
	private void TampilDetailBarangPembelian(String no_faktur) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Qty");
		model.addColumn("Harga Beli");
		db.con();
		try {
			String query = "select a.no_faktur, "
					+ "date_format(a.tanggal, '%d %M %Y')as tgl, "
					+ "date_format(a.jatuh_tempo, '%d %M %Y')as jatuh_tempo, "
					+ "b.nama as nama_pelanggan, c.sisa_hutang, "
					+ "(select sum(jml_bayar)from tb_detail_hutang "
					+ "where no_faktur = ? ) as jumlah_bayar, "
					+ "a.sub_total, e.satuan, "
					+ "e.id_barang, f.nama as nama_barang, e.qty, "
					+ "e.harga_beli from tb_pembelian a "
					+ "inner join tb_supplier b on a.supplier = b.id "
					+ "inner join tb_hutang c on a.no_faktur = c.no_faktur "
					+ "inner join tb_detail_pembelian e on a.no_faktur = e.no_faktur "
					+ "inner join tb_barang f on e.id_barang = f.id "
					+ "where a.no_faktur = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, no_faktur);
			ps.setString(2, no_faktur);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				txtNoFaktur.setText(rs.getString("no_faktur"));
				txtTanggal.setText(rs.getString("tgl"));
				txtJatuhTempo.setText(rs.getString("jatuh_tempo"));
				txtPelanggan.setText(rs.getString("nama_pelanggan"));
				txtSisaHutang.setText(FMain.FormatAngka(rs.getInt("sisa_hutang")));
				txtTotalBayar.setText(rs.getString("jumlah_bayar") == null ? "-" : 
					FMain.FormatAngka(rs.getInt("jumlah_bayar")));
				txtTotalPembelian.setText(FMain.FormatAngka(rs.getInt("sub_total")));
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("nama_barang"),
						rs.getString("satuan"),
						rs.getString("qty"),
						FMain.FormatAngka(rs.getInt("harga_beli"))
				});
			}
			table_2.setModel(model);
			table_2.setAutoResizeMode(0);
			table_2.getColumnModel().getColumn(0).setPreferredWidth(80);
			table_2.getColumnModel().getColumn(1).setPreferredWidth(100);
			table_2.getColumnModel().getColumn(2).setPreferredWidth(80);
			table_2.getColumnModel().getColumn(3).setPreferredWidth(80);
			table_2.getColumnModel().getColumn(4).setPreferredWidth(120);
		} catch(Exception e) {
			System.out.println("error tampil barang beli " + e);
		}
	}
	
	private void cetak() {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\LaporanPiutang.jasper";
			param.put("paramNoFaktur", txtNoFaktur.getText());
			
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
					FPiutang.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
