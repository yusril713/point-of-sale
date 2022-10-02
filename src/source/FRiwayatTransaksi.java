package source;

import java.awt.Component;
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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;

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
import javax.swing.SpringLayout;

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
	private SpringLayout springLayout;

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
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() >= 0) {
					if (comboBox.getSelectedIndex() == 0) {
						Semua();
						TampilByFilter("hari_ini");
					} else if (comboBox.getSelectedIndex() == 1) {
						Semua();
						TampilByFilter("bulan_ini");
					} else if (comboBox.getSelectedIndex() == 2) {
						Semua();
						TampilByFilter("tahun_ini");
					} else if (comboBox.getSelectedIndex() == 3) {
						BerdasarPencarian();
//						TampilSemua("");
					} else if (comboBox.getSelectedIndex() == 4) {
						BerdasarTanggal();
					} else {
						BerdasarFaktur();
					}
				}
			}
		});
		springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 30, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 30, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Hari ini", "Bulan ini", "Tahun ini", "Berdasarkan Pencarian", "Berdasarkan Tanggal", "Berdasarkan No Faktur"}));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		frame.getContentPane().add(comboBox);
		
		datePicker = new JXDatePicker();
		springLayout.putConstraint(SpringLayout.NORTH, datePicker, 5, SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, datePicker, 30, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, datePicker, 231, SpringLayout.WEST, frame.getContentPane());
		datePicker.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker.setVisible(false);
		frame.getContentPane().add(datePicker);
		
		lblSd = new JLabel("s/d");
		springLayout.putConstraint(SpringLayout.NORTH, lblSd, 67, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblSd, 5, SpringLayout.EAST, datePicker);
		lblSd.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblSd.setVisible(false);
		frame.getContentPane().add(lblSd);
		
		datePicker_1 = new JXDatePicker();
		springLayout.putConstraint(SpringLayout.NORTH, datePicker_1, 63, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, datePicker_1, 5, SpringLayout.EAST, lblSd);
		springLayout.putConstraint(SpringLayout.EAST, datePicker_1, 460, SpringLayout.WEST, frame.getContentPane());
		datePicker_1.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker_1.setVisible(false);
		frame.getContentPane().add(datePicker_1);
		
		txtNoFaktur = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtNoFaktur, 5, SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, txtNoFaktur, 30, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtNoFaktur, 231, SpringLayout.WEST, frame.getContentPane());
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
				if (!txtNoFaktur.getText().equals(""))
					TampilBerdasarFaktur(txtNoFaktur.getText());
			}
		});
		
		btnOk = new JButton("Tampil");
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 64, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 5, SpringLayout.EAST, datePicker_1);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (datePicker.getDate() == null || datePicker_1.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Isi tanggal terlebih dahulu...");
				} else 
				TampilBerdasarTanggal();
			}
		});
		btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		frame.getContentPane().add(btnOk);
		frame.getContentPane().add(txtNoFaktur);
		txtNoFaktur.setColumns(10);
		
		searchField = new JXSearchField();
		springLayout.putConstraint(SpringLayout.NORTH, searchField, 5, SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, searchField, 30, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, searchField, 231, SpringLayout.WEST, frame.getContentPane());
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
				if (!searchField.getText().equals(""))
					TampilSemua(searchField.getText());
			}
		});
		frame.getContentPane().add(searchField);
		
		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 5, SpringLayout.SOUTH, datePicker_1);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 30, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -(frame.getWidth()/2), SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
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
////					System.out.println(getModel().getValueAt(row, 0).toString() + " Jumlah data: " + count);
//					if (count == 0) { 
////						comp.setBackground(Color.red); 
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
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 0, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 5, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, -55, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, -20, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		scrollPane_1.setViewportView(table_1);
		
		btnHapus = new JButton("Hapus");
		springLayout.putConstraint(SpringLayout.SOUTH, btnHapus, -20, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnHapus, 0, SpringLayout.EAST, scrollPane_1);
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
		frame.getContentPane().add(btnHapus);
	}

	private void BerdasarTanggal() {
		datePicker.setVisible(true);
		datePicker_1.setVisible(true);
		lblSd.setVisible(true);
		btnOk.setVisible(true);
		txtNoFaktur.setVisible(false);
		searchField.setVisible(false);
	}
	
	private void BerdasarPencarian() {
		datePicker.setVisible(false);
		lblSd.setVisible(false);
		datePicker_1.setVisible(false);
		btnOk.setVisible(false);
		txtNoFaktur.setVisible(false);
		searchField.setVisible(true);
	}
	
	private void BerdasarFaktur() {
		datePicker.setVisible(false);
		datePicker_1.setVisible(false);
		lblSd.setVisible(false);
		btnOk.setVisible(false);
		txtNoFaktur.setVisible(true);
		searchField.setVisible(false);
		txtNoFaktur.setText("");
		txtNoFaktur.requestFocus();
	}
	
	private void Semua() {
		datePicker.setVisible(false);
		lblSd.setVisible(false);
		datePicker_1.setVisible(false);
		btnOk.setVisible(false);
		txtNoFaktur.setVisible(false);
		searchField.setVisible(false);
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
				
				query = "delete from tb_do where no_faktur = ?";
				ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
				
				query = "delete from tb_rincian_do where no_faktur = ?";
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
				
				query = "delete from tb_do where no_faktur = ?";
				ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.execute();
				
				query = "delete from tb_rincian_do where no_faktur = ?";
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