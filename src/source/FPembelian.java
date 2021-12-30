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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class FPembelian {

	JFrame frame;
	static FPembelian window;
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
					window = new FPembelian();
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
	public FPembelian() {
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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 123, 47, 173, 43, 168, 220, 215, 166, 0};
		gridBagLayout.rowHeights = new int[]{20, 28, 25, 28, 28, 28, 136, 29, 29, 0, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		datePicker_1 = new JXDatePicker();
		datePicker_1.setVisible(false);
		
		chckbxJatuhTempo = new JCheckBox("Jatuh Tempo");
		chckbxJatuhTempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxJatuhTempo.isSelected())
					datePicker_1.setVisible(true);
				else
					datePicker_1.setVisible(false);
			}
		});
		
		JLabel lblNoFaktur = new JLabel("No Faktur");
		lblNoFaktur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNoFaktur = new GridBagConstraints();
		gbc_lblNoFaktur.anchor = GridBagConstraints.WEST;
		gbc_lblNoFaktur.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoFaktur.gridx = 1;
		gbc_lblNoFaktur.gridy = 1;
		frame.getContentPane().add(lblNoFaktur, gbc_lblNoFaktur);
		
		txtNoFaktur = new JTextField();
		txtNoFaktur.setBackground(SystemColor.control);
		txtNoFaktur.setBorder(null);
		txtNoFaktur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNoFaktur.setText("no faktur");
		GridBagConstraints gbc_txtNoFaktur = new GridBagConstraints();
		gbc_txtNoFaktur.fill = GridBagConstraints.BOTH;
		gbc_txtNoFaktur.insets = new Insets(0, 0, 5, 5);
		gbc_txtNoFaktur.gridwidth = 2;
		gbc_txtNoFaktur.gridx = 3;
		gbc_txtNoFaktur.gridy = 1;
		frame.getContentPane().add(txtNoFaktur, gbc_txtNoFaktur);
		txtNoFaktur.setColumns(10);
		chckbxJatuhTempo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJatuhTempo.setBackground(Color.WHITE);
		GridBagConstraints gbc_chckbxJatuhTempo = new GridBagConstraints();
		gbc_chckbxJatuhTempo.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chckbxJatuhTempo.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxJatuhTempo.gridx = 6;
		gbc_chckbxJatuhTempo.gridy = 1;
		frame.getContentPane().add(chckbxJatuhTempo, gbc_chckbxJatuhTempo);
		
		JLabel lblTanggal = new JLabel("Tanggal");
		lblTanggal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTanggal = new GridBagConstraints();
		gbc_lblTanggal.anchor = GridBagConstraints.WEST;
		gbc_lblTanggal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTanggal.gridx = 1;
		gbc_lblTanggal.gridy = 2;
		frame.getContentPane().add(lblTanggal, gbc_lblTanggal);
		
		datePicker = new JXDatePicker();
		datePicker.getEditor().setBackground(SystemColor.control);
		datePicker.getEditor().setBorder(null);
		datePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePicker.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.fill = GridBagConstraints.BOTH;
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.gridwidth = 2;
		gbc_datePicker.gridx = 3;
		gbc_datePicker.gridy = 2;
		frame.getContentPane().add(datePicker, gbc_datePicker);
		datePicker_1.setBorder(null);
		datePicker_1.getEditor().setBackground(SystemColor.control);
		datePicker_1.getEditor().setBorder(null);
		datePicker_1.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePicker_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_datePicker_1 = new GridBagConstraints();
		gbc_datePicker_1.fill = GridBagConstraints.BOTH;
		gbc_datePicker_1.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker_1.gridx = 6;
		gbc_datePicker_1.gridy = 2;
		frame.getContentPane().add(datePicker_1, gbc_datePicker_1);
		
		JLabel lblIdSupplier = new JLabel("ID Supplier");
		lblIdSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblIdSupplier = new GridBagConstraints();
		gbc_lblIdSupplier.anchor = GridBagConstraints.WEST;
		gbc_lblIdSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdSupplier.gridx = 1;
		gbc_lblIdSupplier.gridy = 3;
		frame.getContentPane().add(lblIdSupplier, gbc_lblIdSupplier);
		
		txtIdSupplier = new JTextField();
		txtIdSupplier.setBackground(SystemColor.control);
		txtIdSupplier.setBorder(null);
		txtIdSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtIdSupplier.setText("id supplier");
		GridBagConstraints gbc_txtIdSupplier = new GridBagConstraints();
		gbc_txtIdSupplier.fill = GridBagConstraints.BOTH;
		gbc_txtIdSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_txtIdSupplier.gridx = 3;
		gbc_txtIdSupplier.gridy = 3;
		frame.getContentPane().add(txtIdSupplier, gbc_txtIdSupplier);
		txtIdSupplier.setColumns(10);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FCariSupplier.main(null);
				window.frame.setEnabled(false);
			}
		});
		button.setIcon(new ImageIcon(FPembelian.class.getResource("/org/jdesktop/swingx/color/mag.png")));
		button.setOpaque(true);
		button.setForeground(Color.LIGHT_GRAY);
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 4;
		gbc_button.gridy = 3;
		frame.getContentPane().add(button, gbc_button);
		
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
		
		JLabel lblNamaSupplier = new JLabel("Nama Supplier");
		lblNamaSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNamaSupplier = new GridBagConstraints();
		gbc_lblNamaSupplier.anchor = GridBagConstraints.WEST;
		gbc_lblNamaSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblNamaSupplier.gridx = 1;
		gbc_lblNamaSupplier.gridy = 4;
		frame.getContentPane().add(lblNamaSupplier, gbc_lblNamaSupplier);
		
		txtNamaSupplier = new JTextField();
		txtNamaSupplier.setBackground(SystemColor.control);
		txtNamaSupplier.setBorder(null);
		txtNamaSupplier.setEditable(false);
		txtNamaSupplier.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNamaSupplier.setText("nama supplier");
		GridBagConstraints gbc_txtNamaSupplier = new GridBagConstraints();
		gbc_txtNamaSupplier.fill = GridBagConstraints.BOTH;
		gbc_txtNamaSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_txtNamaSupplier.gridwidth = 2;
		gbc_txtNamaSupplier.gridx = 3;
		gbc_txtNamaSupplier.gridy = 4;
		frame.getContentPane().add(txtNamaSupplier, gbc_txtNamaSupplier);
		txtNamaSupplier.setColumns(10);
		
		scrollPane = new JScrollPane();
		
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
		btnBatal.setIcon(new ImageIcon(FPembelian.class.getResource("/img/Batal.png")));
		btnBatal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBatal = new GridBagConstraints();
		gbc_btnBatal.anchor = GridBagConstraints.EAST;
		gbc_btnBatal.fill = GridBagConstraints.VERTICAL;
		gbc_btnBatal.insets = new Insets(0, 0, 5, 20);
		gbc_btnBatal.gridx = 8;
		gbc_btnBatal.gridy = 4;
		frame.getContentPane().add(btnBatal, gbc_btnBatal);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Kode Barang", "Nama Barang"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 5;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
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
		btnSimpan.setIcon(new ImageIcon(FPembelian.class.getResource("/img/Simpan.png")));
		btnSimpan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.anchor = GridBagConstraints.EAST;
		gbc_btnSimpan.fill = GridBagConstraints.VERTICAL;
		gbc_btnSimpan.insets = new Insets(0, 0, 5, 20);
		gbc_btnSimpan.gridx = 8;
		gbc_btnSimpan.gridy = 5;
		frame.getContentPane().add(btnSimpan, gbc_btnSimpan);
		scrollPane.setBorder(null);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.anchor = GridBagConstraints.NORTH;
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 6;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
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
		GridBagConstraints gbc_txtKodeNama = new GridBagConstraints();
		gbc_txtKodeNama.fill = GridBagConstraints.BOTH;
		gbc_txtKodeNama.insets = new Insets(0, 0, 5, 5);
		gbc_txtKodeNama.gridwidth = 2;
		gbc_txtKodeNama.gridx = 3;
		gbc_txtKodeNama.gridy = 5;
		frame.getContentPane().add(txtKodeNama, gbc_txtKodeNama);
		txtKodeNama.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 20);
		gbc_scrollPane_1.gridwidth = 8;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 6;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane_1.setViewportView(table);
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0)  {
					HapusTransaksiPembelian(table.getSelectedRow());
					model.removeRow(table.getSelectedRow()); 
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FPembelian.class.getResource("/img/Hapus.png")));
		btnHapus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.fill = GridBagConstraints.BOTH;
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 1;
		gbc_btnHapus.gridy = 8;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
		
		txtSubTotal = new JTextField();
		txtSubTotal.setBackground(SystemColor.control);
		txtSubTotal.setBorder(null);
		txtSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubTotal.setText("sub total");
		GridBagConstraints gbc_txtSubTotal = new GridBagConstraints();
		gbc_txtSubTotal.insets = new Insets(0, 0, 5, 20);
		gbc_txtSubTotal.fill = GridBagConstraints.BOTH;
		gbc_txtSubTotal.gridx = 8;
		gbc_txtSubTotal.gridy = 8;
		frame.getContentPane().add(txtSubTotal, gbc_txtSubTotal);
		txtSubTotal.setColumns(10);
		frame.setBounds(100, 100, 1366, 768);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
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
