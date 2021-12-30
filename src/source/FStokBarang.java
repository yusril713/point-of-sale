package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class FStokBarang {

	JFrame frame;
	static FStokBarang window;
	static JTextField txtId;
	static JTextField txtNama;
	static JTextField txtHargaBeli;
	private JTextField txtSupplier;
	private JTextField txtSatuan;
	private static JTable tableBarang;
	private JTable tableDetailBarang;
	private static Database db;
	private JTextField textField;
	private JLabel lblNewLabel;
	static JLabel lblModal;
	private JButton btnTambahBarang;
	private JButton btnHapusBarang;
	private JButton btnTambahStok;
	private JButton btnEditStok;
	private JButton btnEditHarga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FStokBarang();
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
	public FStokBarang() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Stok Barang");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				TampilBarang("");
				lblNewLabel.setText("Total Barang: "+ tableBarang.getRowCount());
				if (FMain.level.equals("kasir")) {
					btnTambahBarang.setEnabled(false);
					btnTambahStok.setEnabled(false);
					btnHapusBarang.setEnabled(false);
					btnEditStok.setEnabled(false);
					btnEditHarga.setEnabled(false);
					lblModal.setVisible(false);
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
		frame.setBounds(100, 100, 847, 592);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setBounds(10, 11, 69, 14);
		frame.getContentPane().add(lblKodeBarang);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(10, 36, 69, 14);
		frame.getContentPane().add(lblNamaBarang);
		
		JLabel lblHargaBeli = new JLabel("Harga Beli");
		lblHargaBeli.setBounds(10, 61, 69, 14);
		frame.getContentPane().add(lblHargaBeli);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setBounds(10, 86, 46, 14);
		frame.getContentPane().add(lblSupplier);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(89, 8, 108, 20);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setBounds(89, 33, 258, 20);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtHargaBeli = new JTextField();
		txtHargaBeli.setEditable(false);
		txtHargaBeli.setBounds(89, 58, 162, 20);
		frame.getContentPane().add(txtHargaBeli);
		txtHargaBeli.setColumns(10);
		
		txtSupplier = new JTextField();
		txtSupplier.setEditable(false);
		txtSupplier.setBounds(89, 83, 258, 20);
		frame.getContentPane().add(txtSupplier);
		txtSupplier.setColumns(10);
		
		JLabel label = new JLabel("/");
		label.setBounds(254, 61, 11, 14);
		frame.getContentPane().add(label);
		
		txtSatuan = new JTextField();
		txtSatuan.setEditable(false);
		txtSatuan.setBounds(261, 58, 86, 20);
		frame.getContentPane().add(txtSatuan);
		txtSatuan.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 148, 467, 368);
		frame.getContentPane().add(scrollPane);
		
		tableBarang = new JTable();
		tableBarang.setGridColor(Color.WHITE);
		tableBarang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableBarang.getSelectedRow() >= 0) {
					TampilDataBarang(tableBarang.getValueAt(tableBarang.getSelectedRow(), 1).toString());
					TampilDetailBarang(tableBarang.getValueAt(tableBarang.getSelectedRow(), 1).toString());
				}
				if (arg0.getClickCount() == 2) {
					FEditBarang.main(null);
					FEditBarang.id = tableBarang.getValueAt(tableBarang.getSelectedRow(), 1).toString();
					FEditBarang.nama = tableBarang.getValueAt(tableBarang.getSelectedRow(), 2).toString();
					FEditBarang.harga = txtHargaBeli.getText();
					FEditBarang.satuan = txtSatuan.getText();
					window.frame.setEnabled(false);
				}
			}
		});

		scrollPane.setViewportView(tableBarang);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Detail Barang", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(487, 112, 332, 190);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 18, 213, 127);
		panel.add(scrollPane_1);
		
		tableDetailBarang = new JTable();
		tableDetailBarang.setGridColor(Color.WHITE);
		scrollPane_1.setViewportView(tableDetailBarang);
		
		JButton btnPecahSatuan = new JButton("Pecah Satuan");
		btnPecahSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tableDetailBarang.getSelectedRow() >= 0) {
					FPecahSatuan.main(null);
					FPecahSatuan.id = txtId.getText();
					FPecahSatuan.nama = txtNama.getText();
					FPecahSatuan.satuan = tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 0).toString();
					window.frame.setEnabled(false);
				}
			}
		});
		btnPecahSatuan.setBounds(10, 156, 99, 23);
		panel.add(btnPecahSatuan);
		
		JButton btnEditSatuan = new JButton("Edit Satuan");
		btnEditSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tableDetailBarang.getSelectedRow() >= 0) {
					String id = txtId.getText();
					String satuan = tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 0).toString();
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
		btnEditSatuan.setBounds(125, 156, 99, 23);
		panel.add(btnEditSatuan);
		
		btnEditHarga = new JButton("Edit Harga");
		btnEditHarga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tableDetailBarang.getSelectedRow() >= 0) {
					FEditHarga.main(null);
					FEditHarga.id = txtId.getText();
					FEditHarga.nama = txtNama.getText();
					FEditHarga.satuan = tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 0).toString();
					window.frame.setEnabled(false);
				}
			}
		});
		btnEditHarga.setBounds(233, 83, 89, 23);
		panel.add(btnEditHarga);
		
		btnTambahStok = new JButton("Tambah Stok");
		btnTambahStok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tableDetailBarang.getSelectedRow() >= 0) {
					FTambahStokBarang.main(null);
					FTambahStokBarang.id = txtId.getText();
					FTambahStokBarang.nama = txtNama.getText();
					FTambahStokBarang.satuan = tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 0).toString();
					FTambahStokBarang.stok = Double.parseDouble(tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 1).toString());
					window.frame.setEnabled(false);
				}
			}
		});
		btnTambahStok.setToolTipText("Tambah Stok Barang");
		btnTambahStok.setBounds(233, 132, 89, 23);
		panel.add(btnTambahStok);
		
		JButton btnHapusStok = new JButton("Hapus Satuan");
		btnHapusStok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (tableDetailBarang.getRowCount() > 1) {
						HapusSatuanBarang(txtId.getText(), 
								tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 0).toString());
						JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
						TampilDetailBarang(txtId.getText());
					} else {
						JOptionPane.showMessageDialog(null, "Stok tidak boleh kosong!");
					}
				}
			}
		});
		btnHapusStok.setToolTipText("Hapus Satuan Barang");
		btnHapusStok.setBounds(233, 156, 89, 23);
		panel.add(btnHapusStok);
		
		btnEditStok = new JButton("Edit Stok");
		btnEditStok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableDetailBarang.getSelectedRow() >= 0) {
					FEditStok.main(null);
					FEditStok.id = txtId.getText();
					FEditStok.nama = txtNama.getText();
					FEditStok.satuan = tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 0).toString();
					FEditStok.stok = tableDetailBarang.getValueAt(tableDetailBarang.getSelectedRow(), 1).toString();
					window.frame.setEnabled(false);
				}
			}
		});
		btnEditStok.setBounds(233, 108, 89, 23);
		panel.add(btnEditStok);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableDetailBarang.getSelectedRow() >= 0)
					TampilDetailBarang(tableBarang.getValueAt(tableBarang.getSelectedRow(), 1).toString());
			}
		});
		btnRefresh.setBounds(233, 58, 89, 23);
		panel.add(btnRefresh);
		
		btnHapusBarang = new JButton("Hapus Barang");
		btnHapusBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus " + txtNama.getText() + "?", "Konfirmasi", 0);
				if (konfirm == 0) {
					HapusItem();
					JOptionPane.showMessageDialog(null, txtNama.getText() + " berhasil dihapus");
					TampilBarang("");
					DefaultTableModel model = (DefaultTableModel) tableDetailBarang.getModel();
					while (model.getRowCount() > 0)
						model.removeRow(0);
				}
			}
		});
		btnHapusBarang.setBounds(701, 511, 118, 25);
		frame.getContentPane().add(btnHapusBarang);
		
		btnTambahBarang = new JButton("Tambah Barang");
		btnTambahBarang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FTambahBarang.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnTambahBarang.setBounds(701, 478, 118, 25);
		frame.getContentPane().add(btnTambahBarang);
		
		JLabel lblKodenama = new JLabel("Kode/Nama");
		lblKodenama.setBounds(10, 123, 69, 14);
		frame.getContentPane().add(lblKodenama);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(89, 120, 332, 20);
		textField.getDocument().addDocumentListener(new DocumentListener() {
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
				TampilBarang(textField.getText());
			}
		});
		frame.getContentPane().add(textField);
		
		JLabel label_1 = new JLabel("*klik 2x pada barang yang akan diedit");
		label_1.setBounds(10, 516, 222, 14);
		frame.getContentPane().add(label_1);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(224, 516, 253, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnStokKurang = new JButton("Stok kurang");
		btnStokKurang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokKurang.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnStokKurang.setBounds(701, 444, 118, 23);
		frame.getContentPane().add(btnStokKurang);
		
		lblModal = new JLabel("Modal");
		lblModal.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblModal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModal.setVerticalAlignment(SwingConstants.BOTTOM);
		lblModal.setBounds(487, 33, 332, 56);
		frame.getContentPane().add(lblModal);
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
			tableBarang.setModel(model);
			tableBarang.setAutoResizeMode(0);
			tableBarang.getColumnModel().getColumn(0).setPreferredWidth(40);
			tableBarang.getColumnModel().getColumn(1).setPreferredWidth(100);
			tableBarang.getColumnModel().getColumn(2).setPreferredWidth(300);
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
					+ "INNER JOIN tb_detail_barang ON tb_barang.id = tb_detail_barang.id_barang "
					+ "INNER JOIN tb_supplier ON tb_barang.id_supplier = tb_supplier.id "
					+ "GROUP BY tb_barang.id HAVING tb_barang.id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtHargaBeli.setText(FMain.FormatAngka(rs.getInt("harga_beli")));
				txtId.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
				txtSupplier.setText(rs.getString("nama_supplier"));
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
			tableDetailBarang.setModel(model);
			tableDetailBarang.setAutoResizeMode(0);
			tableDetailBarang.getColumnModel().getColumn(0).setPreferredWidth(80);
			tableDetailBarang.getColumnModel().getColumn(1).setPreferredWidth(50);
			tableDetailBarang.getColumnModel().getColumn(2).setPreferredWidth(100);
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
			ps.setString(1, txtId.getText());
			ps.execute();
			query = "delete from tb_detail_barang where id_barang = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
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
