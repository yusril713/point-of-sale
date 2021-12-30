package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import org.jdesktop.swingx.JXDatePicker;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FRiwayatTransaksi {

	JFrame frame;
	static FRiwayatTransaksi window;
	private JXDatePicker datePicker_1;
	private JXDatePicker datePicker;
	private JTextField txtNoFaktur;
	private JXTable table;
	private JScrollPane scrollPane;
	private JButton btnHapus;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JLabel lblSd;
	private JXSearchField searchField;
	private Database db;
	static boolean transaksiKredit = false;
	private JButton btnOk;
	private JComboBox<String> comboBox;
	private DateFormat currentYear;
	private DateFormat currentMonth;
	private Calendar cal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FRiwayatTransaksi();
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
	public FRiwayatTransaksi() {
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
				cal = Calendar.getInstance();
				currentYear = new SimpleDateFormat("yyyy");
				currentMonth = new SimpleDateFormat("MM");
				db = new Database();
				Semua();
				TampilByFilter("hari_ini");
				searchField.setVisible(false);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FTransaksi.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1366, 680);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 0, 0, 202, 174, 0, 400, 0, 0, 0, 0, 0, 88, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() >= 0) {
					if (comboBox.getSelectedIndex() == 0) {
						Semua();
						searchField.setVisible(false);
						TampilByFilter("hari_ini");
					} else if (comboBox.getSelectedIndex() == 1) {
						Semua();
						searchField.setVisible(false);
						TampilByFilter("bulan_ini");
					} else if (comboBox.getSelectedIndex() == 2) {
						Semua();
						searchField.setVisible(false);
						TampilByFilter("tahun_ini");
					} else if (comboBox.getSelectedIndex() == 3) {
						Semua();
						TampilSemua("");
					} else if (comboBox.getSelectedIndex() == 4) {
						BerdasarTanggal();
					} else {
						BerdasarFaktur();
					}
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Hari ini", "Bulan ini", "Tahun ini", "Semua", "Berdasarkan Tanggal", "Berdasarkan No Faktur"}));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		datePicker = new JXDatePicker();
		datePicker.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker.setVisible(false);
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.fill = GridBagConstraints.BOTH;
		gbc_datePicker.gridx = 1;
		gbc_datePicker.gridy = 2;
		frame.getContentPane().add(datePicker, gbc_datePicker);
		
		lblSd = new JLabel("s/d");
		lblSd.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblSd.setVisible(false);
		GridBagConstraints gbc_lblSd = new GridBagConstraints();
		gbc_lblSd.insets = new Insets(0, 0, 5, 5);
		gbc_lblSd.gridx = 2;
		gbc_lblSd.gridy = 2;
		frame.getContentPane().add(lblSd, gbc_lblSd);
		
		datePicker_1 = new JXDatePicker();
		datePicker_1.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker_1.setVisible(false);
		GridBagConstraints gbc_datePicker_1 = new GridBagConstraints();
		gbc_datePicker_1.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker_1.fill = GridBagConstraints.BOTH;
		gbc_datePicker_1.gridx = 3;
		gbc_datePicker_1.gridy = 2;
		frame.getContentPane().add(datePicker_1, gbc_datePicker_1);
		
		txtNoFaktur = new JTextField();
		txtNoFaktur.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtNoFaktur.setText("no faktur");
		txtNoFaktur.setVisible(false);
		txtNoFaktur.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			public void EventChanged() {
				TampilBerdasarFaktur(txtNoFaktur.getText());
			}
		});
		
		btnOk = new JButton("Tampil");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (datePicker.getDate() == null || datePicker_1.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Isi tanggal terlebih dahulu...");
				} else 
				TampilBerdasarTanggal();
			}
		});
		btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk.gridx = 4;
		gbc_btnOk.gridy = 2;
		frame.getContentPane().add(btnOk, gbc_btnOk);
		GridBagConstraints gbc_txtNoFaktur = new GridBagConstraints();
		gbc_txtNoFaktur.insets = new Insets(0, 0, 5, 5);
		gbc_txtNoFaktur.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNoFaktur.gridx = 1;
		gbc_txtNoFaktur.gridy = 3;
		frame.getContentPane().add(txtNoFaktur, gbc_txtNoFaktur);
		txtNoFaktur.setColumns(10);
		
		searchField = new JXSearchField();
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			public void EventChanged() {
				TampilSemua(searchField.getText());
			}
		});
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.insets = new Insets(0, 0, 5, 5);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 1;
		gbc_searchField.gridy = 4;
		frame.getContentPane().add(searchField, gbc_searchField);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JXTable() 
//		{ 
//			private static final long serialVersionUID = 1L;
//		 
//			@Override 
//			public Component prepareRenderer(TableCellRenderer renderer, int
//					row, int col) { 
//				Component comp = super.prepareRenderer(renderer, row, col);
//				db.con(); 
//				try { 
//					String query = null;
//					if (!transaksiKredit)
//						query = "select count(no_faktur) as count from tb_detail_transaksi where no_faktur = ?"; 
//					else
//						query = "select count(no_faktur) as count from tb_detail_transaksi_kredit where no_faktur = ?"; 
//					PreparedStatement ps = db.con.prepareStatement(query); 
//					ps.setString(1, getModel().getValueAt(row, 0).toString()); 
//					ResultSet rs = ps.executeQuery();
//					int count = 0; 
//					if (rs.next()) { count = rs.getInt("count"); 
//					}
//			  
//					System.out.println(getModel().getValueAt(row, 0).toString() + " Jumlah data: " + count);
//					if (count == 0) { 
//						comp.setBackground(Color.red); 
//					} 
//					db.con.close(); 
//				}
//				catch(Exception e) { 
//					e.printStackTrace(); 
//				}; 
//					return comp; 
//				} 
//			}
		;
		table.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0)
					TampilDetail(table.getValueAt(table.getSelectedRow(), 0).toString());
 			}
		});
		scrollPane.setViewportView(table);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 9;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 5;
		gbc_scrollPane_1.gridy = 5;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		scrollPane_1.setViewportView(table_1);
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if (table.getSelectedRow() >= 0) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus transaksi dengan No Faktur: " + table.getValueAt(table.getSelectedRow(), 0).toString(), "Konfirmasi", 0);
					if (konfirm == 0)
						HapusTransaksi(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
			}
		});
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 13;
		gbc_btnHapus.gridy = 6;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
	}

	private void BerdasarTanggal() {
		datePicker.setVisible(true);
		datePicker_1.setVisible(true);
		searchField.setVisible(false);
		lblSd.setVisible(true);
		txtNoFaktur.setVisible(false);
		btnOk.setVisible(true);
	}
	
	private void BerdasarFaktur() {
		datePicker.setVisible(false);
		datePicker_1.setVisible(false);
		searchField.setVisible(false);
		lblSd.setVisible(false);
		txtNoFaktur.setVisible(true);
		txtNoFaktur.setText("");
		txtNoFaktur.requestFocus();
		btnOk.setVisible(false);
	}
	
	private void Semua() {
		datePicker.setVisible(false);
		datePicker_1.setVisible(false);
		searchField.setVisible(true);
		lblSd.setVisible(false);
		txtNoFaktur.setVisible(false);
		btnOk.setVisible(false);
	}
	
	private void TampilByFilter(String filter) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("No Faktur");
		model.addColumn("Pelanggan");
		model.addColumn("Tgl Transaksi");
		model.addColumn("Karyawan");
		model.addColumn("Subtotal");
		System.out.println("Filter by: " + currentMonth.format(cal.getTime()));
		db.con();
		try {
			String query = null;
			if (filter.equals("tahun_ini")) {
				if (!transaksiKredit) {
					query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
							+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
							+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
							+ "where date_format(tb_transaksi.tanggal, '%Y') = ?"
							+ "order by tb_transaksi.tanggal desc";
				} else {
					query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
							+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
							+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
							+ "where date_format(tb_transaksi_kredit.tanggal, '%Y') = ? "
							+ "order by tb_transaksi_kredit.tanggal desc";
				}
			} else if (filter.equals("hari_ini")) {
				if (!transaksiKredit) {
					query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
							+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
							+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
							+ "where date_format(tb_transaksi.tanggal, '%d%m%Y') = ?"
							+ "order by tb_transaksi.tanggal desc";
				} else {
					query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
							+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
							+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
							+ "where date_format(tb_transaksi_kredit.tanggal, '%d%ms%Y') = ? "
							+ "order by tb_transaksi_kredit.tanggal desc";
				}
			} 
			else {
				if (!transaksiKredit) {
					query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
							+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
							+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
							+ "where date_format(tb_transaksi.tanggal, '%m%Y') = ?"
							+ "order by tb_transaksi.tanggal desc";
				} else {
					query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
							+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
							+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
							+ "where date_format(tb_transaksi_kredit.tanggal, '%m%Y') = ? "
							+ "order by tb_transaksi_kredit.tanggal desc";
				}
			}
			PreparedStatement ps = db.con.prepareStatement(query);
			if (filter.equals("tahun_ini")) {
				ps.setString(1, currentYear.format(cal.getTime()));
			} else if (filter.equals("hari_ini")) {
				ps.setString(1, new SimpleDateFormat("dd").format(cal.getTime()) + currentMonth.format(cal.getTime()) + currentYear.format(cal.getTime()));
			} else {
				ps.setString(1, currentMonth.format(cal.getTime()) + currentYear.format(cal.getTime()));
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("pelanggan"),
						rs.getString("tanggal"),
						rs.getString("karyawan"),
						FMain.FormatAngka(rs.getInt("total_bayar"))
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(180);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(140);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
			table.getColumnModel().getColumn(4).setPreferredWidth(200);
			table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(30);
			table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 22));
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void TampilSemua(String keyword) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("No Faktur");
		model.addColumn("Pelanggan");
		model.addColumn("Tgl Transaksi");
		model.addColumn("Karyawan");
		model.addColumn("Subtotal");
		
		db.con();
		try {
			String query = null;
			if (!transaksiKredit) {
				query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
						+ "where tb_transaksi.no_faktur like ? or tb_pelanggan.nama like ? or "
						+ "tb_karyawan.nama like ?"
						+ "order by tb_transaksi.tanggal desc";
			} else {
				query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
						+ "where tb_transaksi_kredit.no_faktur like ? or tb_pelanggan.nama like ? or "
						+ "tb_karyawan.nama like ?"
						+ "order by tb_transaksi_kredit.tanggal desc";
			}
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("pelanggan"),
						rs.getString("tanggal"),
						rs.getString("karyawan"),
						FMain.FormatAngka(rs.getInt("total_bayar"))
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(180);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(140);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
			table.getColumnModel().getColumn(4).setPreferredWidth(200);
			table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(30);
			table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 22));
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void TampilDetail(String noFaktur) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Barang");
		model.addColumn("satuan");
		model.addColumn("Qty");
		model.addColumn("Harga");
		model.addColumn("Total");
		
		db.con();
		try {
			String query = null;
			if (!transaksiKredit) {
				query = "select tb_detail_transaksi.*, (tb_detail_transaksi.qty * tb_detail_transaksi.harga_jual) as total, "
						+ "tb_barang.nama as barang from tb_detail_transaksi "
						+ "join tb_barang on tb_barang.id = tb_detail_transaksi.id_barang "
						+ "where tb_detail_transaksi.no_faktur = ?";
			} else {
				query = "select tb_detail_transaksi_kredit.*, (tb_detail_transaksi_kredit.qty * tb_detail_transaksi_kredit.harga_jual) as total, "
						+ "tb_barang.nama as barang from tb_detail_transaksi_kredit "
						+ "join tb_barang on tb_barang.id = tb_detail_transaksi_kredit.id_barang "
						+ "where tb_detail_transaksi_kredit.no_faktur = ?";
			}
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("barang"),
						rs.getString("satuan"),
						rs.getString("qty"),
						FMain.FormatAngka(rs.getInt("harga_jual")),
						FMain.FormatAngka(rs.getInt("total"))
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			table_1.setModel(model);
			table_1.setAutoResizeMode(0);
			table_1.getColumnModel().getColumn(0).setPreferredWidth(170);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(2).setPreferredWidth(60);
			table_1.getColumnModel().getColumn(3).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(4).setPreferredWidth(90);
			
			table_1.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table_1.setRowHeight(30);
			table_1.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 22));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void TampilBerdasarFaktur(String noFaktur) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("No Faktur");
		model.addColumn("Pelanggan");
		model.addColumn("Tgl Transaksi");
		model.addColumn("Karyawan");
		model.addColumn("Subtotal");
		
		db.con();
		try {
			String query = null;
			if (!transaksiKredit) {
				query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
						+ "where tb_transaksi.no_faktur like ? "
						+ "order by tb_transaksi.tanggal desc";
			} else {
				query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
						+ "where tb_transaksi_kredit.no_faktur like ? "
						+ "order by tb_transaksi_kredit.tanggal desc";
			}
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + noFaktur + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("pelanggan"),
						rs.getString("tanggal"),
						rs.getString("karyawan"),
						FMain.FormatAngka(rs.getInt("total_bayar"))
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(180);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(140);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
			table.getColumnModel().getColumn(4).setPreferredWidth(200);
			table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(30);
			table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 22));
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void TampilBerdasarTanggal() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("No Faktur");
		model.addColumn("Pelanggan");
		model.addColumn("Tgl Transaksi");
		model.addColumn("Karyawan");
		model.addColumn("Subtotal");
		
		db.con();
		try {
			String query = null;
			if (!transaksiKredit) {
				query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
						+ "where tb_transaksi.tanggal between ? and ? "
						+ "order by tb_transaksi.tanggal desc";
			} else {
				query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
						+ "where tb_transaksi_kredit.tanggal between ? and ? "
						+ "order by tb_transaksi_kredit.tanggal desc";
			}
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, df.format(datePicker.getDate()));
			ps.setString(2, df.format(datePicker_1.getDate()));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("pelanggan"),
						rs.getString("tanggal"),
						rs.getString("karyawan"),
						FMain.FormatAngka(rs.getInt("total_bayar"))
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(180);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(140);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
			table.getColumnModel().getColumn(4).setPreferredWidth(200);
			
			table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(30);
			table.getTableHeader().setFont(new Font("SegoeUI", Font.BOLD, 22));
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void HapusTransaksi(String noFaktur) {
		db.con();
		try {
			String query = "";
			if (!transaksiKredit) {
				query = "delete from tb_transaksi where no_faktur = ?";
				PreparedStatement ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
				
				query = "delete from tb_detail_transaksi where no_faktur = ?";
				ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
			} else {
				query = "delete from tb_transaksi_kredit where no_faktur = ?";
				PreparedStatement ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
				
				query = "delete from tb_detail_transaksi_kredit where no_faktur = ?";
				ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
				
				query = "delete from tb_hutang where no_faktur = ?";
				ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
				
				query = "delete from tb_detail_hutang where no_faktur = ?";
				ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
			}
			
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data transaksi dengan No Faktur: " + noFaktur + " berhasil dihapus...");
			TampilSemua("");
			comboBox.setSelectedIndex(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}