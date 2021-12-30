package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class FStokBarangNew {

	JFrame frame;
	static FStokBarangNew window;
	static JTextField txtKode;
	static JTextField txtNama;
	static JTextField txtHargaBeli;
	private JTextField txtSatuan;
	static JTextField txtSupplier;
	private JLabel lblCari;
	private JTextField txtCari;
	private static JXTable table;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton btnListStokBrg;
	private JButton btnTambahBarang;
	private JButton btnHapusBarang;
	private JTable table_1;
	private JButton btnRefresh;
	static JButton btnEditHarga;
	static JButton btnEditStok;
	static JButton btnTambahStok;
	private JScrollPane scrollPane_1;
	static JButton btnHapusSatuan;
	static JButton btnEditSatuan;
	static JButton btnPecahSatuan;
	static JLabel lblModal;
	static Database db;
	private JLabel lblNewLabel;
	private JButton btnRugiLaba;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FStokBarangNew();
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
	public FStokBarangNew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("STOK BARANG");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				TampilBarang("");
				lblNewLabel.setText("Total Barang: "+ table.getRowCount());
				if (FMain.level.equals("kasir") || FMain.level.equals("kasir_utama")) {
					btnTambahBarang.setEnabled(false);
					btnTambahStok.setEnabled(false);
					btnHapusBarang.setEnabled(false);
					btnEditStok.setEnabled(false);
					btnEditHarga.setEnabled(false);
					btnHapusSatuan.setEnabled(false);
					btnEditSatuan.setEnabled(false);
					lblModal.setVisible(false);
					btnRugiLaba.setVisible(false);
				}

				lblModal.setText("Modal: Rp. " + FMain.FormatAngka(TampilModal()));
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1366, 768);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 134, 20, 231, -25, 50, 0, 0, 0, 179, 338, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 302, 20, 20, 36, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblKodeBarang = new GridBagConstraints();
		gbc_lblKodeBarang.fill = GridBagConstraints.BOTH;
		gbc_lblKodeBarang.insets = new Insets(0, 0, 5, 5);
		gbc_lblKodeBarang.gridx = 1;
		gbc_lblKodeBarang.gridy = 1;
		frame.getContentPane().add(lblKodeBarang, gbc_lblKodeBarang);
		
		txtKode = new JTextField();
		txtKode.setBorder(null);
		txtKode.setBackground(UIManager.getColor("Button.light"));
		txtKode.setSelectedTextColor(UIManager.getColor("Button.light"));
		txtKode.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtKode = new GridBagConstraints();
		gbc_txtKode.fill = GridBagConstraints.BOTH;
		gbc_txtKode.gridwidth = 3;
		gbc_txtKode.insets = new Insets(0, 0, 5, 5);
		gbc_txtKode.gridx = 3;
		gbc_txtKode.gridy = 1;
		frame.getContentPane().add(txtKode, gbc_txtKode);
		txtKode.setColumns(10);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNamaBarang = new GridBagConstraints();
		gbc_lblNamaBarang.fill = GridBagConstraints.BOTH;
		gbc_lblNamaBarang.insets = new Insets(0, 0, 5, 5);
		gbc_lblNamaBarang.gridx = 1;
		gbc_lblNamaBarang.gridy = 2;
		frame.getContentPane().add(lblNamaBarang, gbc_lblNamaBarang);
		
		txtNama = new JTextField();
		txtNama.setBorder(null);
		txtNama.setBackground(UIManager.getColor("Button.light"));
		txtNama.setSelectedTextColor(UIManager.getColor("Button.light"));
		txtNama.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtNama = new GridBagConstraints();
		gbc_txtNama.gridwidth = 3;
		gbc_txtNama.insets = new Insets(0, 0, 5, 5);
		gbc_txtNama.fill = GridBagConstraints.BOTH;
		gbc_txtNama.gridx = 3;
		gbc_txtNama.gridy = 2;
		frame.getContentPane().add(txtNama, gbc_txtNama);
		txtNama.setColumns(10);
		
		JLabel lblHargaBeli = new JLabel("Harga Beli");
		lblHargaBeli.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblHargaBeli = new GridBagConstraints();
		gbc_lblHargaBeli.fill = GridBagConstraints.BOTH;
		gbc_lblHargaBeli.insets = new Insets(0, 0, 5, 5);
		gbc_lblHargaBeli.gridx = 1;
		gbc_lblHargaBeli.gridy = 3;
		frame.getContentPane().add(lblHargaBeli, gbc_lblHargaBeli);
		
		txtHargaBeli = new JTextField();
		txtHargaBeli.setBorder(null);
		txtHargaBeli.setBackground(UIManager.getColor("Button.light"));
		txtHargaBeli.setSelectedTextColor(UIManager.getColor("Button.light"));
		txtHargaBeli.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtHargaBeli = new GridBagConstraints();
		gbc_txtHargaBeli.insets = new Insets(0, 0, 5, 5);
		gbc_txtHargaBeli.fill = GridBagConstraints.BOTH;
		gbc_txtHargaBeli.gridx = 3;
		gbc_txtHargaBeli.gridy = 3;
		frame.getContentPane().add(txtHargaBeli, gbc_txtHargaBeli);
		txtHargaBeli.setColumns(10);
		
		JLabel label = new JLabel("/");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 3;
		frame.getContentPane().add(label, gbc_label);
		
		txtSatuan = new JTextField();
		txtSatuan.setBorder(null);
		txtSatuan.setBackground(UIManager.getColor("Button.light"));
		txtSatuan.setSelectedTextColor(UIManager.getColor("Button.light"));
		txtSatuan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtSatuan = new GridBagConstraints();
		gbc_txtSatuan.fill = GridBagConstraints.BOTH;
		gbc_txtSatuan.insets = new Insets(0, 0, 5, 5);
		gbc_txtSatuan.gridx = 5;
		gbc_txtSatuan.gridy = 3;
		frame.getContentPane().add(txtSatuan, gbc_txtSatuan);
		txtSatuan.setColumns(10);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
		gbc_lblSupplier.fill = GridBagConstraints.BOTH;
		gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblSupplier.gridx = 1;
		gbc_lblSupplier.gridy = 4;
		frame.getContentPane().add(lblSupplier, gbc_lblSupplier);
		
		txtSupplier = new JTextField();
		txtSupplier.setBorder(null);
		txtSupplier.setBackground(UIManager.getColor("Button.light"));
		txtSupplier.setSelectedTextColor(UIManager.getColor("Button.light"));
		txtSupplier.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtSupplier = new GridBagConstraints();
		gbc_txtSupplier.gridwidth = 3;
		gbc_txtSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_txtSupplier.fill = GridBagConstraints.BOTH;
		gbc_txtSupplier.gridx = 3;
		gbc_txtSupplier.gridy = 4;
		frame.getContentPane().add(txtSupplier, gbc_txtSupplier);
		txtSupplier.setColumns(10);
		
		lblModal = new JLabel("modal");
		lblModal.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblModal.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblModal = new GridBagConstraints();
		gbc_lblModal.gridwidth = 2;
		gbc_lblModal.fill = GridBagConstraints.BOTH;
		gbc_lblModal.gridheight = 2;
		gbc_lblModal.insets = new Insets(0, 0, 5, 5);
		gbc_lblModal.gridx = 10;
		gbc_lblModal.gridy = 4;
		frame.getContentPane().add(lblModal, gbc_lblModal);
		
		lblCari = new JLabel("Cari");
		lblCari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblCari = new GridBagConstraints();
		gbc_lblCari.fill = GridBagConstraints.BOTH;
		gbc_lblCari.insets = new Insets(0, 0, 5, 5);
		gbc_lblCari.gridx = 1;
		gbc_lblCari.gridy = 5;
		frame.getContentPane().add(lblCari, gbc_lblCari);
		
		txtCari = new JTextField();
		txtCari.getDocument().addDocumentListener(new DocumentListener() {
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
				TampilBarang(txtCari.getText());
			}
		});
		txtCari.setBorder(null);
		txtCari.setBackground(UIManager.getColor("Button.light"));
		txtCari.setSelectedTextColor(UIManager.getColor("Button.light"));
		txtCari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtCari = new GridBagConstraints();
		gbc_txtCari.gridwidth = 4;
		gbc_txtCari.insets = new Insets(0, 0, 5, 5);
		gbc_txtCari.fill = GridBagConstraints.BOTH;
		gbc_txtCari.gridx = 3;
		gbc_txtCari.gridy = 5;
		frame.getContentPane().add(txtCari, gbc_txtCari);
		txtCari.setColumns(10);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 6;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JXTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					TampilDataBarang(table.getValueAt(table.getSelectedRow(), 1).toString());
					TampilDetailBarang(table.getValueAt(table.getSelectedRow(), 1).toString());
				}
				if (arg0.getClickCount() == 2) {
					FEditBarang.main(null);
					FEditBarang.id = table.getValueAt(table.getSelectedRow(), 1).toString();
					FEditBarang.nama = table.getValueAt(table.getSelectedRow(), 2).toString();
					FEditBarang.harga = txtHargaBeli.getText();
					FEditBarang.satuan = txtSatuan.getText();
					FEditBarang.supplier = txtSupplier.getText();
					window.frame.setEnabled(false);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Detail Barang", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 10;
		gbc_panel.gridy = 6;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 3;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridheight = 5;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_1.getSelectedRow() >= 0)
					TampilDetailBarang(table.getValueAt(table_1.getSelectedRow(), 1).toString());
			}
		});
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRefresh.insets = new Insets(0, 0, 5, 0);
		gbc_btnRefresh.gridx = 3;
		gbc_btnRefresh.gridy = 1;
		panel.add(btnRefresh, gbc_btnRefresh);
		
		btnEditHarga = new JButton("Edit Harga");
		btnEditHarga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table_1.getSelectedRow() >= 0) {
					FEditHarga.main(null);
					FEditHarga.id = txtKode.getText();
					FEditHarga.nama = txtNama.getText();
					FEditHarga.satuan = table_1.getValueAt(table_1.getSelectedRow(), 0).toString();
					window.frame.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_btnEditHarga = new GridBagConstraints();
		gbc_btnEditHarga.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditHarga.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditHarga.gridx = 3;
		gbc_btnEditHarga.gridy = 2;
		panel.add(btnEditHarga, gbc_btnEditHarga);
		
		btnEditStok = new JButton("Edit Stok");
		btnEditStok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_1.getSelectedRow() >= 0) {
					FEditStok.main(null);
					FEditStok.id = txtKode.getText();
					FEditStok.nama = txtNama.getText();
					FEditStok.satuan = table_1.getValueAt(table_1.getSelectedRow(), 0).toString();
					FEditStok.stok = table_1.getValueAt(table_1.getSelectedRow(), 1).toString();
					window.frame.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_btnEditStok = new GridBagConstraints();
		gbc_btnEditStok.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditStok.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditStok.gridx = 3;
		gbc_btnEditStok.gridy = 3;
		panel.add(btnEditStok, gbc_btnEditStok);
		
		btnTambahStok = new JButton("Tambah Stok");
		btnTambahStok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table_1.getSelectedRow() >= 0) {
					FTambahStokBarang.main(null);
					FTambahStokBarang.id = txtKode.getText();
					FTambahStokBarang.nama = txtNama.getText();
					FTambahStokBarang.satuan = table_1.getValueAt(table_1.getSelectedRow(), 0).toString();
					FTambahStokBarang.stok = Double.parseDouble(table_1.getValueAt(table_1.getSelectedRow(), 1).toString());
					window.frame.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_btnTambahStok = new GridBagConstraints();
		gbc_btnTambahStok.insets = new Insets(0, 0, 5, 0);
		gbc_btnTambahStok.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTambahStok.gridx = 3;
		gbc_btnTambahStok.gridy = 4;
		panel.add(btnTambahStok, gbc_btnTambahStok);
		
		btnPecahSatuan = new JButton("Pecah Satuan");
		btnPecahSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table_1.getSelectedRow() >= 0) {
					FPecahSatuan.main(null);
					FPecahSatuan.id = txtKode.getText();
					FPecahSatuan.nama = txtNama.getText();
					FPecahSatuan.satuan = table_1.getValueAt(table_1.getSelectedRow(), 0).toString();
					window.frame.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_btnPecahSatuan = new GridBagConstraints();
		gbc_btnPecahSatuan.fill = GridBagConstraints.BOTH;
		gbc_btnPecahSatuan.insets = new Insets(0, 0, 0, 5);
		gbc_btnPecahSatuan.gridx = 1;
		gbc_btnPecahSatuan.gridy = 5;
		panel.add(btnPecahSatuan, gbc_btnPecahSatuan);
		
		btnEditSatuan = new JButton("Edit Satuan");
		btnEditSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table_1.getSelectedRow() >= 0) {
					String id = txtKode.getText();
					String satuan = table_1.getValueAt(table_1.getSelectedRow(), 0).toString();
					if (!id.equals("") && !satuan.equals("")) {
						FEditSatuan.main(null);
						FEditSatuan.id = id;
						FEditSatuan.satuan = satuan;
						id = satuan = null;
						window.frame.setEnabled(false);
					}
				}
				
			}
		});
		GridBagConstraints gbc_btnEditSatuan = new GridBagConstraints();
		gbc_btnEditSatuan.fill = GridBagConstraints.BOTH;
		gbc_btnEditSatuan.insets = new Insets(0, 0, 0, 5);
		gbc_btnEditSatuan.gridx = 2;
		gbc_btnEditSatuan.gridy = 5;
		panel.add(btnEditSatuan, gbc_btnEditSatuan);
		
		btnHapusSatuan = new JButton("Hapus Satuan");
		btnHapusSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (table_1.getRowCount() > 1) {
						HapusSatuanBarang(txtKode.getText(), 
								table_1.getValueAt(table_1.getSelectedRow(), 0).toString());
						JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
						TampilDetailBarang(txtKode.getText());
					} else {
						JOptionPane.showMessageDialog(null, "Stok tidak boleh kosong!");
					}
				}
			}
		});
		GridBagConstraints gbc_btnHapusSatuan = new GridBagConstraints();
		gbc_btnHapusSatuan.fill = GridBagConstraints.BOTH;
		gbc_btnHapusSatuan.gridx = 3;
		gbc_btnHapusSatuan.gridy = 5;
		panel.add(btnHapusSatuan, gbc_btnHapusSatuan);
		
		btnListStokBrg = new JButton("List Stok Barang");
		btnListStokBrg.setForeground(new Color(255, 255, 255));
		btnListStokBrg.setBackground(new Color(30, 144, 255));
		btnListStokBrg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokKurang.main(null);
				window.frame.setEnabled(false);
			}
		});
		
		btnRugiLaba = new JButton("Rugi Laba");
		btnRugiLaba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FRugiLaba.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnRugiLaba.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnRugiLaba = new GridBagConstraints();
		gbc_btnRugiLaba.fill = GridBagConstraints.BOTH;
		gbc_btnRugiLaba.insets = new Insets(0, 0, 5, 5);
		gbc_btnRugiLaba.gridx = 11;
		gbc_btnRugiLaba.gridy = 9;
		frame.getContentPane().add(btnRugiLaba, gbc_btnRugiLaba);
		btnListStokBrg.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnListStokBrg = new GridBagConstraints();
		gbc_btnListStokBrg.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnListStokBrg.insets = new Insets(0, 0, 5, 5);
		gbc_btnListStokBrg.gridx = 11;
		gbc_btnListStokBrg.gridy = 10;
		frame.getContentPane().add(btnListStokBrg, gbc_btnListStokBrg);
		
		btnTambahBarang = new JButton("Tambah Barang");
		btnTambahBarang.setBackground(new Color(30, 144, 255));
		btnTambahBarang.setForeground(new Color(255, 255, 255));
		btnTambahBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FTambahBarang.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnTambahBarang.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnTambahBarang = new GridBagConstraints();
		gbc_btnTambahBarang.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTambahBarang.insets = new Insets(0, 0, 5, 5);
		gbc_btnTambahBarang.gridx = 11;
		gbc_btnTambahBarang.gridy = 11;
		frame.getContentPane().add(btnTambahBarang, gbc_btnTambahBarang);
		
		btnHapusBarang = new JButton("Hapus Barang");
		btnHapusBarang.setForeground(new Color(255, 255, 255));
		btnHapusBarang.setBackground(new Color(255, 0, 0));
		btnHapusBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus " + txtNama.getText() + "?", "Konfirmasi", 0);
				if (konfirm == 0) {
					HapusItem();
					JOptionPane.showMessageDialog(null, txtNama.getText() + " berhasil dihapus");
					TampilBarang("");
					DefaultTableModel model = (DefaultTableModel) table_1.getModel();
					while (model.getRowCount() > 0)
						model.removeRow(0);
				}
			}
		});
		btnHapusBarang.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapusBarang = new GridBagConstraints();
		gbc_btnHapusBarang.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHapusBarang.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapusBarang.gridx = 11;
		gbc_btnHapusBarang.gridy = 12;
		frame.getContentPane().add(btnHapusBarang, gbc_btnHapusBarang);
		
		lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 9;
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 13;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
	}
	
	static void TampilBarang(String cari) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("No");
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_barang where id like ? or nama like ? order by nama";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + cari + "%");
			ps.setString(2, "%" + cari + "%");
			ResultSet rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) {
				model.addRow(new Object[] {
						i++,
						rs.getString("id"),
						rs.getString("nama")
				});
			}
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setPreferredWidth(170);
			table.getColumnModel().getColumn(2).setPreferredWidth(400);
			
			table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
			table.setFont(new Font("SansSerif", Font.PLAIN, 16));
			table.setRowHeight(25);
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil barang: " + e);
		}
	}
	
	private void TampilDataBarang(String id) {
		db.con();
		try {
			String query = "SELECT tb_barang.id, tb_barang.nama, tb_barang.harga_beli, "
					+ "tb_barang.id_supplier, tb_detail_barang.satuan, "
					+ "tb_supplier.nama AS nama_supplier "
					+ "FROM tb_barang "
					+ "LEFT JOIN tb_detail_barang ON tb_barang.id = tb_detail_barang.id_barang "
					+ "LEFT JOIN tb_supplier ON tb_barang.id_supplier = tb_supplier.id "
					+ "GROUP BY tb_barang.id HAVING tb_barang.id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtHargaBeli.setText(FMain.FormatAngka(rs.getInt("harga_beli")));
				txtKode.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
				if(rs.getString("nama_supplier") == null)
					txtSupplier.setText(null);
				else
					txtSupplier.setText(rs.getString("id_supplier") + " - " + rs.getString("nama_supplier"));
				txtSatuan.setText(rs.getString("satuan"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil data barang: " + e);
		}
	}
	
	private void TampilDetailBarang(String id) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Satuan");
		model.addColumn("Stok");
		model.addColumn("Harga Jual");
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ? order by harga_jual desc";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("satuan"),
						rs.getString("stok"),
						FMain.FormatAngka(rs.getInt("harga_jual"))
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			table_1.setModel(model);
			table_1.setAutoResizeMode(0);
			table_1.getColumnModel().getColumn(0).setPreferredWidth(100);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(2).setPreferredWidth(150);
			

			table_1.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
			table_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
			table_1.setRowHeight(25);
		} catch(Exception e) {
			System.out.println("erro tampil detail barang");
		}
	}
	
	private void HapusSatuanBarang(String id, String satuan) {
		db.con();
		try {
			String query = "delete from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error hapus satuan barang " + e);
		}
	}
	
	private void HapusItem() {
		db.con();
		try {
			String query = "delete from tb_barang where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKode.getText());
			ps.execute();
			query = "delete from tb_detail_barang where id_barang = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, txtKode.getText());
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error happus item " + e);
		}
	}
	
	static int TampilModal() {
		int modal = 0; 
		db.con();
		try {
			String query = "select (stok * harga_beli) as modal from tb_detail_barang ";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				modal += rs.getInt("modal");
			}
			rs.close();
			st.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil modal " + e);
		}
		
		return modal;
	}

}
