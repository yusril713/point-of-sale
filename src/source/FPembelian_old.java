package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class FPembelian_old {

	JFrame frame;
	static FPembelian_old window;
	private JTextField txtNoFaktur;
	static JTextField txtIdSupplier;
	static JTextField txtNamaSupplier;
	static JTextField txtKodeNama;
	private JXDatePicker datePicker;
	private JComboBox<String> comboBox;
	private JScrollPane scrollPane;
	private JList<String> list;
	static JTable table;
	private JButton btnHapus;
	static JTextField txtSubTotal;
	private JButton btnSimpan;
	private JButton btnBatal;
	private Database db;
	private JXDatePicker datePicker_1;
	private JCheckBox chckbxJatuhTempo;
	static DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FPembelian_old();
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
	public FPembelian_old() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Pembelian");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				AturTabel();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				if (model.getRowCount() > 0)
					TransaksiBatal();
				while (model.getRowCount() > 0)
					model.removeRow(0);
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(200, 164, 220, 81);
		frame.getContentPane().add(scrollPane);
		
		list = new JList<>();
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtKodeNama.setText(list.getSelectedValue().toString());
					scrollPane.setVisible(false);
					FDetailPembelian.main(null);
					FDetailPembelian.edit = false;
					FDetailPembelian.kodeNama = txtKodeNama.getText();
					window.frame.setEnabled(false);
				}
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblNoFaktur = new JLabel("No Faktur");
		lblNoFaktur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNoFaktur.setBounds(30, 30, 66, 17);
		frame.getContentPane().add(lblNoFaktur);
		
		JLabel lblTanggal = new JLabel("Tanggal");
		lblTanggal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTanggal.setBounds(30, 58, 66, 17);
		frame.getContentPane().add(lblTanggal);
		
		JLabel lblIdSupplier = new JLabel("ID Supplier");
		lblIdSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIdSupplier.setBounds(30, 86, 84, 17);
		frame.getContentPane().add(lblIdSupplier);
		
		JLabel lblNamaSupplier = new JLabel("Nama Supplier");
		lblNamaSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNamaSupplier.setBounds(30, 114, 99, 17);
		frame.getContentPane().add(lblNamaSupplier);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Kode Barang", "Nama Barang"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(30, 142, 123, 23);
		frame.getContentPane().add(comboBox);
		
		txtNoFaktur = new JTextField();
		txtNoFaktur.setBackground(SystemColor.control);
		txtNoFaktur.setBorder(null);
		txtNoFaktur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNoFaktur.setText("no faktur");
		txtNoFaktur.setBounds(200, 26, 220, 25);
		frame.getContentPane().add(txtNoFaktur);
		txtNoFaktur.setColumns(10);
		
		txtIdSupplier = new JTextField();
		txtIdSupplier.setBackground(SystemColor.control);
		txtIdSupplier.setBorder(null);
		txtIdSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtIdSupplier.setText("id supplier");
		txtIdSupplier.setBounds(200, 82, 173, 25);
		frame.getContentPane().add(txtIdSupplier);
		txtIdSupplier.setColumns(10);
		
		txtNamaSupplier = new JTextField();
		txtNamaSupplier.setBackground(SystemColor.control);
		txtNamaSupplier.setBorder(null);
		txtNamaSupplier.setEditable(false);
		txtNamaSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNamaSupplier.setText("nama supplier");
		txtNamaSupplier.setBounds(200, 110, 220, 25);
		frame.getContentPane().add(txtNamaSupplier);
		txtNamaSupplier.setColumns(10);
		
		txtKodeNama = new JTextField();
		txtKodeNama.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					list.requestFocus();
					list.setSelectedIndex(0);
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					boolean cek = false;
					switch (comboBox.getSelectedIndex()) {
					case 0:
						cek = CekDataBarang("Kode Barang");
						break;

					default:
						cek = CekDataBarang("Nama Barang");
						break;
					}
					if (cek) {
						boolean edit = false;
						int i = 0;
						if (comboBox.getSelectedIndex() == 0) {
							for ( i = 0; i < table.getRowCount(); i++) {
								if (txtKodeNama.getText().equals(table.getValueAt(i, 0).toString())) {
									edit = true;
									break;
								}
							}
						} else if (comboBox.getSelectedIndex() == 1) {
							for ( i = 0; i < table.getRowCount(); i++) {
								if (txtKodeNama.getText().equals(table.getValueAt(i, 1).toString())) {
									edit = true;
									break;
								}
							}
						}
						
						if (edit) {
							/*FDetailTransaksi.main(null);
							FDetailTransaksi.key = null;
							FDetailTransaksi.edit = true;
							FDetailTransaksi.harga = table.getValueAt(i, 3).toString();
							FDetailTransaksi.id = table.getValueAt(i, 0).toString();
							FDetailTransaksi.nama = table.getValueAt(i, 1).toString();
							FDetailTransaksi.qty = table.getValueAt(i, 4).toString();
							FDetailTransaksi.satuan = table.getValueAt(i, 2).toString();
							FDetailTransaksi.row = i;*/
							table.setValueAt((Integer.parseInt(table.getValueAt(i, 4).toString()) + 1), i, 4);
							txtKodeNama.selectAll();
							txtKodeNama.requestFocus();
						} else {

							scrollPane.setVisible(false);
							window.frame.setEnabled(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Data barang tidak ditemukan");
					}
				}
			}
		});
		txtKodeNama.setBackground(SystemColor.control);
		txtKodeNama.setBorder(null);
		txtKodeNama.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtKodeNama.setText("kode nama");
		txtKodeNama.setBounds(200, 141, 220, 25);
		txtKodeNama.getDocument().addDocumentListener(new DocumentListener() {
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
				switch (comboBox.getSelectedIndex()) {
				case 0:
					CariBarang("Kode Barang");
					break;
				default:
					CariBarang("Nama Barang");
					break;
				}
			}
		});
		frame.getContentPane().add(txtKodeNama);
		txtKodeNama.setColumns(10);
		
		datePicker = new JXDatePicker();
		datePicker.getEditor().setBackground(SystemColor.control);
		datePicker.getEditor().setBorder(null);
		datePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePicker.setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePicker.setBounds(200, 54, 220, 25);
		frame.getContentPane().add(datePicker);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FCariSupplier.main(null);
				window.frame.setEnabled(false);
			}
		});
		button.setIcon(new ImageIcon(FPembelian_old.class.getResource("/org/jdesktop/swingx/color/mag.png")));
		button.setOpaque(true);
		button.setForeground(Color.LIGHT_GRAY);
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBounds(378, 80, 43, 28);
		frame.getContentPane().add(button);
		
		chckbxJatuhTempo = new JCheckBox("Jatuh Tempo");
		chckbxJatuhTempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxJatuhTempo.isSelected())
					datePicker_1.setVisible(true);
				else
					datePicker_1.setVisible(false);
			}
		});
		chckbxJatuhTempo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJatuhTempo.setBackground(Color.WHITE);
		chckbxJatuhTempo.setBounds(589, 29, 116, 25);
		frame.getContentPane().add(chckbxJatuhTempo);
		
		datePicker_1 = new JXDatePicker();
		datePicker_1.setVisible(false);
		datePicker_1.setBorder(null);
		datePicker_1.getEditor().setBackground(SystemColor.control);
		datePicker_1.getEditor().setBorder(null);
		datePicker_1.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePicker_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePicker_1.setBounds(589, 54, 220, 25);
		frame.getContentPane().add(datePicker_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(30, 186, 1310, 460);
		frame.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane_1.setViewportView(table);
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() > 0)  {
					HapusTransaksiPembelian(table.getSelectedRow());
					model.removeRow(table.getSelectedRow()); 
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FPembelian_old.class.getResource("/img/Hapus.png")));
		btnHapus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHapus.setBounds(30, 657, 123, 28);
		frame.getContentPane().add(btnHapus);
		
		txtSubTotal = new JTextField();
		txtSubTotal.setBackground(SystemColor.control);
		txtSubTotal.setBorder(null);
		txtSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubTotal.setText("sub total");
		txtSubTotal.setBounds(1174, 658, 166, 28);
		frame.getContentPane().add(txtSubTotal);
		txtSubTotal.setColumns(10);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan transaksi tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (txtNoFaktur.getText().equals("") || 
							txtIdSupplier.getText().equals("") ||
							table.getRowCount() == 0) {
						JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
						txtNoFaktur.requestFocus();
					} else {
						SimpanPembelian();
						SimpanDetailPembelian();
						JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
						while (model.getRowCount() > 0) 
							model.removeRow(0);
						DataBaru();
					}
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon(FPembelian_old.class.getResource("/img/Simpan.png")));
		btnSimpan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSimpan.setBounds(1217, 108, 123, 28);
		frame.getContentPane().add(btnSimpan);
		
		btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.getRowCount() > 0)
					TransaksiBatal();
				while (model.getRowCount() > 0)
					model.removeRow(0);
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setIcon(new ImageIcon(FPembelian_old.class.getResource("/img/Batal.png")));
		btnBatal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBatal.setBounds(1217, 139, 123, 28);
		frame.getContentPane().add(btnBatal);
		frame.setBounds(100, 100, 1366, 768);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
	}
	
	private void DataBaru() {
		Calendar cal = Calendar.getInstance();
		txtNoFaktur.setText("");
		txtIdSupplier.setText("");
		txtKodeNama.setText("");
		txtNamaSupplier.setText("");
		txtSubTotal.setText("");
		
		datePicker.setDate(cal.getTime());
		datePicker.setFormats(new SimpleDateFormat("dd MMMM yyyy"));
		cal.add(2, 1);
		datePicker_1.setDate(cal.getTime());
		datePicker_1.setFormats(new SimpleDateFormat("dd MMMM yyyy"));
	}
	
	private void AturTabel() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(4);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(0);
	    
	    String[] columnNames = { "Kode Barang", "Nama Barang", "Satuan", "Harga Beli", "Qty", "Total" };
	    model = new DefaultTableModel() {
	    	private static final long serialVersionUID = 1L;
	    	public boolean isCellEditable(int row, int column) {
	    		return false;
	    	}
	    };
	    model.setColumnIdentifiers(columnNames);
	    table.setModel(model);
	    table.setAutoResizeMode(0);
	    table.getColumnModel().getColumn(0).setPreferredWidth(200);
	    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(1).setPreferredWidth(200);
	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
	    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(3).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(4).setPreferredWidth(100);
	    table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(5).setPreferredWidth(200);
	    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
	    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
		table.setRowHeight(25);
	}
	
	private void CariBarang(String str) {
		db.con();
		try {
			String query = null;
			if (str.equals("Kode Barang"))
				query = "select id as str from tb_barang where id like ?";
			else 
				query = "select nama as str from tb_barang where nama like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + txtKodeNama.getText() + "%");
			ResultSet rs = ps.executeQuery();
			
			int rowcount = 0;
			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst();
			}
			//System.out.println(id_jenis.get(comboBox.getSelectedIndex()));
			String[] hasil = new String[rowcount];
			if ((rowcount > 0) && (!txtKodeNama.getText().equals(""))) {
				scrollPane.setVisible(true);
				int i = 0;
				while (rs.next()) {
					hasil[i] = rs.getString("str");
					i++;
				}
			} else {
				scrollPane.setVisible(false);
			}
			list.setListData(hasil);
			
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("ada error cari barang " + e);
		}
	}
	
	private boolean CekDataBarang(String str) {
		db.con();
		boolean cek = false;
		try {
			String query = null;
			if (str.equals("Kode Barang"))
				query = "select id as str from tb_barang where id =  ?";
			else 
				query = "select nama as str from tb_barang where nama = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKodeNama.getText());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cek = true;
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("ada error cari barang " + e);
		}
		return cek;
	}
	
	private void TransaksiBatal() {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = stok - ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = null;
			System.out.println(model.getRowCount());
			for (int i = 0; i < model.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				ps.setInt(1, Integer.parseInt(table.getValueAt(i, 4).toString()));
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.setString(3, table.getValueAt(i, 2).toString());
				ps.execute();
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error transaksi batal " + e);
		}
	}
	
	private void HapusTransaksiPembelian(int row) {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = stok - ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(table.getValueAt(row, 4).toString()));
			ps.setString(2, table.getValueAt(row, 0).toString());
			ps.setString(3, table.getValueAt(row, 2).toString());
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error hapus transaksi: " + e);
		}
	}
	
	private void SimpanPembelian() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		db.con();
		try {
			String query = "";
			PreparedStatement ps = null;
			if (chckbxJatuhTempo.isSelected()) {
				query = "insert into tb_pembelian values(?,?,?,?,?)";
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, txtSubTotal.getText().replace(",", ""));
				ps.setString(3, txtIdSupplier.getText());
				ps.setString(4, df.format(datePicker.getDate()));
				ps.setString(5, df.format(datePicker_1.getDate()));
				ps.execute();
				
				query = "insert into tb_hutang values (?,?)";
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, txtSubTotal.getText().replace(",", ""));
				ps.execute();
			} else {
				query = "insert into tb_pembelian(no_faktur, sub_total, supplier, tanggal) "
						+ "values(?,?,?,?)";
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, txtSubTotal.getText().replace(",", ""));
				ps.setString(3, txtIdSupplier.getText());
				ps.setString(4, df.format(datePicker.getDate()));
				ps.execute();
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan pembelian " + e);
		}
	}
	
	private void SimpanDetailPembelian() {
		db.con();
		try {
			String query = "insert into tb_detail_pembelian values(?,?,?,?,?)";
			PreparedStatement ps = null;
			for (int i = 0; i < table.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.setString(3, table.getValueAt(i, 2).toString());
				ps.setString(4, table.getValueAt(i, 3).toString().replace(",", ""));
				ps.setString(5, table.getValueAt(i, 4).toString());
				ps.execute();
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan detail pembelian " + e);
		}
	}
}
