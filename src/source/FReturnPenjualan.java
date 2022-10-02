package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;

public class FReturnPenjualan {

	JFrame frame;
	static FReturnPenjualan window;
	private JTextField txtKodeReturn;
	static JTextField txtKodePelanggan;
	static JTextField txtNamaPelanggan;
	private JLabel lblKasir;
	private JLabel lblTanggal;
	private JComboBox<String> comboBox;
	private JTextField txtKasir;
	private JTextField txtTanggal;
	static JTextField txtKeyword;
	private JButton btnCariPelanggan;
	static JTable table;
	private JScrollPane scrollPane;
	private JList<String> list;
	private JScrollPane scrollPane_1;
	private Database db;
	static DefaultTableModel model;
	private Calendar cal = Calendar.getInstance();
	private DateFormat df;
	private JButton btnHapus;
	static JTextField txtSubTotal;
	private JButton btnSimpan;
	private JButton btnBatal;
	private JButton btnListReturn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FReturnPenjualan();
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
	public FReturnPenjualan() {
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
				scrollPane_1.setVisible(false);
				AturTabel();
				DataBaru();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1366, 768);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 0, 30, 215, 0, 60, 0, 240, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 170, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblKodeReturn = new JLabel("Kode Return");
		lblKodeReturn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblKodeReturn = new GridBagConstraints();
		gbc_lblKodeReturn.anchor = GridBagConstraints.WEST;
		gbc_lblKodeReturn.insets = new Insets(0, 0, 5, 5);
		gbc_lblKodeReturn.gridx = 1;
		gbc_lblKodeReturn.gridy = 1;
		frame.getContentPane().add(lblKodeReturn, gbc_lblKodeReturn);
		
		txtKodeReturn = new JTextField();
		txtKodeReturn.setEditable(false);
		txtKodeReturn.setBorder(null);
		txtKodeReturn.setBackground(SystemColor.menu);
		txtKodeReturn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtKodeReturn = new GridBagConstraints();
		gbc_txtKodeReturn.gridwidth = 2;
		gbc_txtKodeReturn.fill = GridBagConstraints.BOTH;
		gbc_txtKodeReturn.insets = new Insets(0, 0, 5, 5);
		gbc_txtKodeReturn.gridx = 3;
		gbc_txtKodeReturn.gridy = 1;
		frame.getContentPane().add(txtKodeReturn, gbc_txtKodeReturn);
		txtKodeReturn.setColumns(10);
		
		lblKasir = new JLabel("Kasir");
		lblKasir.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblKasir = new GridBagConstraints();
		gbc_lblKasir.anchor = GridBagConstraints.WEST;
		gbc_lblKasir.insets = new Insets(0, 0, 5, 5);
		gbc_lblKasir.gridx = 6;
		gbc_lblKasir.gridy = 1;
		frame.getContentPane().add(lblKasir, gbc_lblKasir);
		
		txtKasir = new JTextField();
		txtKasir.setEditable(false);
		txtKasir.setBackground(SystemColor.menu);
		txtKasir.setBorder(null);
		txtKasir.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtKasir = new GridBagConstraints();
		gbc_txtKasir.fill = GridBagConstraints.BOTH;
		gbc_txtKasir.insets = new Insets(0, 0, 5, 5);
		gbc_txtKasir.gridx = 7;
		gbc_txtKasir.gridy = 1;
		frame.getContentPane().add(txtKasir, gbc_txtKasir);
		txtKasir.setColumns(10);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getRowCount() > 0) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
					if (konfirm == 0) {
						System.out.println(table.getRowCount());
						System.out.println(txtKodePelanggan.getText());
						if (table.getRowCount() <= 0 || txtKodePelanggan.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
						} else 
							Simpan();
					}
				} 
			}
		});
		btnSimpan.setIcon(new ImageIcon(FReturnPenjualan.class.getResource("/img/Simpan.png")));
		btnSimpan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSimpan.insets = new Insets(0, 0, 5, 5);
		gbc_btnSimpan.gridx = 9;
		gbc_btnSimpan.gridy = 1;
		frame.getContentPane().add(btnSimpan, gbc_btnSimpan);
		
		JLabel lblKodePelanggan = new JLabel("Kode Pelanggan");
		lblKodePelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblKodePelanggan = new GridBagConstraints();
		gbc_lblKodePelanggan.insets = new Insets(0, 0, 5, 5);
		gbc_lblKodePelanggan.anchor = GridBagConstraints.WEST;
		gbc_lblKodePelanggan.gridx = 1;
		gbc_lblKodePelanggan.gridy = 2;
		frame.getContentPane().add(lblKodePelanggan, gbc_lblKodePelanggan);
		
		txtKodePelanggan = new JTextField();
		txtKodePelanggan.setEditable(false);
		txtKodePelanggan.setBackground(SystemColor.menu);
		txtKodePelanggan.setBorder(null);
		txtKodePelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtKodePelanggan = new GridBagConstraints();
		gbc_txtKodePelanggan.fill = GridBagConstraints.BOTH;
		gbc_txtKodePelanggan.insets = new Insets(0, 0, 5, 5);
		gbc_txtKodePelanggan.gridx = 3;
		gbc_txtKodePelanggan.gridy = 2;
		frame.getContentPane().add(txtKodePelanggan, gbc_txtKodePelanggan);
		txtKodePelanggan.setColumns(10);
		
		btnCariPelanggan = new JButton("");
		btnCariPelanggan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FReturnCariPelanggan.main(null);
				window.frame.setEnabled(true);
			}
		});
		btnCariPelanggan.setIcon(new ImageIcon(FReturnPenjualan.class.getResource("/org/jdesktop/swingx/plaf/windows/resources/search.gif")));
		GridBagConstraints gbc_btnCariPelanggan = new GridBagConstraints();
		gbc_btnCariPelanggan.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCariPelanggan.insets = new Insets(0, 0, 5, 5);
		gbc_btnCariPelanggan.gridx = 4;
		gbc_btnCariPelanggan.gridy = 2;
		frame.getContentPane().add(btnCariPelanggan, gbc_btnCariPelanggan);
		
		lblTanggal = new JLabel("Tanggal");
		lblTanggal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblTanggal = new GridBagConstraints();
		gbc_lblTanggal.anchor = GridBagConstraints.WEST;
		gbc_lblTanggal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTanggal.gridx = 6;
		gbc_lblTanggal.gridy = 2;
		frame.getContentPane().add(lblTanggal, gbc_lblTanggal);
		
		txtTanggal = new JTextField();
		txtTanggal.setEditable(false);
		txtTanggal.setBackground(SystemColor.menu);
		txtTanggal.setBorder(null);
		txtTanggal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtTanggal = new GridBagConstraints();
		gbc_txtTanggal.fill = GridBagConstraints.BOTH;
		gbc_txtTanggal.insets = new Insets(0, 0, 5, 5);
		gbc_txtTanggal.gridx = 7;
		gbc_txtTanggal.gridy = 2;
		frame.getContentPane().add(txtTanggal, gbc_txtTanggal);
		txtTanggal.setColumns(10);
		
		btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnBatal.setIcon(new ImageIcon(FReturnPenjualan.class.getResource("/img/Batal.png")));
		GridBagConstraints gbc_btnBatal = new GridBagConstraints();
		gbc_btnBatal.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBatal.insets = new Insets(0, 0, 5, 5);
		gbc_btnBatal.gridx = 9;
		gbc_btnBatal.gridy = 2;
		frame.getContentPane().add(btnBatal, gbc_btnBatal);
		
		JLabel lblNamaPelanggan = new JLabel("Nama Pelanggan");
		lblNamaPelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNamaPelanggan = new GridBagConstraints();
		gbc_lblNamaPelanggan.insets = new Insets(0, 0, 5, 5);
		gbc_lblNamaPelanggan.anchor = GridBagConstraints.WEST;
		gbc_lblNamaPelanggan.gridx = 1;
		gbc_lblNamaPelanggan.gridy = 3;
		frame.getContentPane().add(lblNamaPelanggan, gbc_lblNamaPelanggan);
		
		txtNamaPelanggan = new JTextField();
		txtNamaPelanggan.setEditable(false);
		txtNamaPelanggan.setBackground(SystemColor.menu);
		txtNamaPelanggan.setBorder(null);
		txtNamaPelanggan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtNamaPelanggan = new GridBagConstraints();
		gbc_txtNamaPelanggan.gridwidth = 2;
		gbc_txtNamaPelanggan.insets = new Insets(0, 0, 5, 5);
		gbc_txtNamaPelanggan.fill = GridBagConstraints.BOTH;
		gbc_txtNamaPelanggan.gridx = 3;
		gbc_txtNamaPelanggan.gridy = 3;
		frame.getContentPane().add(txtNamaPelanggan, gbc_txtNamaPelanggan);
		txtNamaPelanggan.setColumns(10);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Kode", "Nama"}));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.gridx = 6;
		gbc_comboBox.gridy = 3;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		txtKeyword = new JTextField();
		txtKeyword.getDocument().addDocumentListener(new DocumentListener() {
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
		txtKeyword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				boolean edit = false;
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
						scrollPane_1.setVisible(false);
						int i = 0;
						if (comboBox.getSelectedIndex() == 0) {
							for ( i = 0; i < table.getRowCount(); i++) {
								if (txtKeyword.getText().equals(table.getValueAt(i, 0).toString())) {
									edit = true;
									break;
								}
							}
						} else if (comboBox.getSelectedIndex() == 1) {
							for ( i = 0; i < table.getRowCount(); i++) {
								if (txtKeyword.getText().equals(table.getValueAt(i, 1).toString())) {
									edit = true;
									break;
								}
							}
						}
						if (edit) {
							FDetailReturnPenjualan.main(null);
							FDetailReturnPenjualan.row = i;
							FDetailReturnPenjualan.edit = true;
							FDetailReturnPenjualan.kode = table.getValueAt(i, 0).toString();
							FDetailReturnPenjualan.nama = table.getValueAt(i, 1).toString();
							FDetailReturnPenjualan.satuan = table.getValueAt(i, 2).toString();
							FDetailReturnPenjualan.harga = Integer.parseInt(table.getValueAt(i, 3).toString().replace(",", ""));
							FDetailReturnPenjualan.jumlah = Integer.parseInt(table.getValueAt(i, 4).toString());
							FDetailReturnPenjualan.kelayakan = table.getValueAt(i, 6).toString();
							FDetailReturnPenjualan.keterangan = table.getValueAt(i, 7).toString();
//							table.setValueAt((Integer.parseInt(table.getValueAt(i, 4).toString()) + 1), i, 4);
							txtKeyword.selectAll();
							txtKeyword.requestFocus();
						}
						else {
							FDetailReturnPenjualan.main(null);
							FDetailReturnPenjualan.edit = false;
							FDetailReturnPenjualan.key = txtKeyword.getText();
							FDetailReturnPenjualan.row = 0;
							window.frame.setEnabled(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Data barang tidak ditemukan");
					}
				}
			}
		});
		txtKeyword.setBackground(SystemColor.menu);
		txtKeyword.setBorder(null);
		txtKeyword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtKeyword = new GridBagConstraints();
		gbc_txtKeyword.insets = new Insets(0, 0, 5, 5);
		gbc_txtKeyword.fill = GridBagConstraints.BOTH;
		gbc_txtKeyword.gridx = 7;
		gbc_txtKeyword.gridy = 3;
		frame.getContentPane().add(txtKeyword, gbc_txtKeyword);
		txtKeyword.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 1;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 7;
		gbc_scrollPane_1.gridy = 4;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		list = new JList<String>();
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtKeyword.setText(list.getSelectedValue().toString());
					scrollPane_1.setVisible(false);
					FDetailReturnPenjualan.main(null);
					FDetailReturnPenjualan.edit = false;
					FDetailReturnPenjualan.key = txtKeyword.getText();
					FDetailReturnPenjualan.row = 0;
					window.frame.setEnabled(false);
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					txtKeyword.setText(list.getSelectedValue().toString());
					scrollPane_1.setVisible(false);
					FDetailReturnPenjualan.main(null);
					FDetailReturnPenjualan.edit = false;
					FDetailReturnPenjualan.key = txtKeyword.getText();
					FDetailReturnPenjualan.row = 0;
					window.frame.setEnabled(false);
				}
			}
		});
		scrollPane_1.setViewportView(list);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					FDetailReturnPenjualan.main(null);
					FDetailReturnPenjualan.row = table.getSelectedRow();
					FDetailReturnPenjualan.edit = true;
					FDetailReturnPenjualan.kode = table.getValueAt(table.getSelectedRow(), 0).toString();
					FDetailReturnPenjualan.nama = table.getValueAt(table.getSelectedRow(), 1).toString();
					FDetailReturnPenjualan.satuan = table.getValueAt(table.getSelectedRow(), 2).toString();
					FDetailReturnPenjualan.harga = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString().replace(",", ""));
					FDetailReturnPenjualan.jumlah = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 4).toString());
					FDetailReturnPenjualan.kelayakan = table.getValueAt(table.getSelectedRow(), 6).toString();
					FDetailReturnPenjualan.keterangan = table.getValueAt(table.getSelectedRow(), 7).toString();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		btnHapus = new JButton("HAPUS");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					model.removeRow(table.getSelectedRow());
				}
			}
		});
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnHapus.setIcon(new ImageIcon(FReturnPenjualan.class.getResource("/img/Hapus.png")));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.fill = GridBagConstraints.BOTH;
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 1;
		gbc_btnHapus.gridy = 7;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
		
		txtSubTotal = new JTextField();
		txtSubTotal.setBorder(null);
		txtSubTotal.setBackground(SystemColor.menu);
		txtSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubTotal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSubTotal.setText("total");
		GridBagConstraints gbc_txtSubTotal = new GridBagConstraints();
		gbc_txtSubTotal.fill = GridBagConstraints.BOTH;
		gbc_txtSubTotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtSubTotal.gridx = 9;
		gbc_txtSubTotal.gridy = 7;
		frame.getContentPane().add(txtSubTotal, gbc_txtSubTotal);
		txtSubTotal.setColumns(10);
		
		btnListReturn = new JButton("List Return");
		btnListReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FListReturnPenjualan.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnListReturn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_btnListReturn = new GridBagConstraints();
		gbc_btnListReturn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnListReturn.insets = new Insets(0, 0, 5, 5);
		gbc_btnListReturn.gridx = 1;
		gbc_btnListReturn.gridy = 8;
		frame.getContentPane().add(btnListReturn, gbc_btnListReturn);
	}
	
	private void AturTabel() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(4);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(0);
	    
	    String[] columnNames = { "Kode Barang", "Nama Barang", "Satuan", "Harga", "Jumlah", "Total", "Kelayakan", "Keterangan" };
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
	    table.getColumnModel().getColumn(6).setPreferredWidth(200);
	    table.getColumnModel().getColumn(7).setPreferredWidth(200);
		table.setRowHeight(25);
	}

	private void CariBarang(String str) {
		db.con();
		scrollPane_1.setVisible(true);
		try {
			String query = null;
			if (str.equals("Kode Barang"))
				query = "select id as str from tb_barang where id like ?";
			else 
				query = "select nama as str from tb_barang where nama like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + txtKeyword.getText() + "%");
			ResultSet rs = ps.executeQuery();
			
			int rowcount = 0;
			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst();
			}
			//System.out.println(id_jenis.get(comboBox.getSelectedIndex()));
			String[] hasil = new String[rowcount];
			if ((rowcount > 0) && (!txtKeyword.getText().equals(""))) {
				int i = 0;
				while (rs.next()) {
					hasil[i] = rs.getString("str");
					i++;
				}
			} else {
				scrollPane_1.setVisible(false);
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
			ps.setString(1, txtKeyword.getText());
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
	
	private String SetKodeReturn() {
		df = new SimpleDateFormat("ddMMyy");
		String kode = "RET/" + df.format(cal.getTime()) + "/";
		db.con();
		try {
			String query = "select count(id) as count from tb_return_penjualan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				kode += (rs.getInt("count") + 1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return kode;
	}
	
	private void DataBaru() {
		txtKodeReturn.setText(SetKodeReturn());
		
		df = new SimpleDateFormat("dd MMMM yyyy");
		txtTanggal.setText(df.format(cal.getTime()));
		txtKasir.setText(FMain.user);
	}
	
	@SuppressWarnings("resource")
	private void Simpan() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		db.con();
		try {
			String query = "insert into tb_return_penjualan values(?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKodeReturn.getText());
			ps.setString(2, txtKodePelanggan.getText());
			ps.setString(3, df.format(cal.getTime()));
			ps.setString(4, txtSubTotal.getText().replace(",", ""));
			ps.setString(5, txtKasir.getText());
			ps.execute();
			
			List<Integer> hargaBeli = new ArrayList<>();
			for (int i = 0; i < table.getRowCount(); i++) {
				query = "select harga_beli from tb_detail_barang where id_barang = ? and satuan = ?";
				ps = db.con.prepareStatement(query);
				ps.setString(1, table.getValueAt(i, 0).toString());
				ps.setString(2, table.getValueAt(i, 2).toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					hargaBeli.add(rs.getInt("harga_beli"));
				}
			}
			
			for (int i = 0; i < table.getRowCount(); i++) {
				query = "insert into tb_detail_return_penjualan values(?,?,?,?,?,?,?,?)";
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtKodeReturn.getText());
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.setString(3, table.getValueAt(i, 2).toString());
				ps.setString(4, table.getValueAt(i, 4).toString());
				ps.setString(5, table.getValueAt(i, 3).toString().replace(",", ""));
				ps.setInt(6, hargaBeli.get(i));
				ps.setString(7, table.getValueAt(i, 6).toString());
				ps.setString(8, table.getValueAt(i, 7).toString());
				ps.execute();
				
				if (table.getValueAt(i, 6).toString().equals("Layak dijual")) {
					query = "update tb_detail_barang set stok = stok + ? where id_barang = ? and satuan = ?";
					ps = db.con.prepareStatement(query);
					ps.setDouble(1, Double.parseDouble(table.getValueAt(i, 4).toString()));
					ps.setString(2, table.getValueAt(i, 0).toString());
					ps.setString(3, table.getValueAt(i, 2).toString());
					ps.execute();
				} else {
					query = "insert into tb_barang_rusak (id_barang, satuan, jumlah, keterangan) values(?,?,?,?)";
					ps = db.con.prepareStatement(query);
					ps.setString(1, table.getValueAt(i, 0).toString());
					ps.setString(2, table.getValueAt(i, 2).toString());
					ps.setString(3, table.getValueAt(i, 4).toString());
					ps.setString(4, table.getValueAt(i, 7).toString());
					ps.execute();
				}
			}
			
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
			AturTabel();
			DataBaru();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
