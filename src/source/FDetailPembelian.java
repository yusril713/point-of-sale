package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FDetailPembelian {

	JFrame frame;
	static FDetailPembelian window;
	private JTextField txtId;
	private JTextField txtNama;
	private JComboBox<String> comboBox;
	private JTextField txtStok;
	private JTextField txtQty;
	private JTextField txtHargaBeli;
	private JTextField txtHargaJual;
	private Database db;
	static String kodeNama = null;
	static boolean edit = false;
	private JTextField txtIsi;
	private JComboBox<String> comboBox_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FDetailPembelian();
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
	public FDetailPembelian() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Detail Pembelian");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				if (!edit) {
					AmbilDataBarang(kodeNama);
					AmbilSatuan();
					if (txtId.getText().equals("")) {
						txtId.setEditable(true);
						txtNama.setEditable(true);
					}
					else {
						txtId.setEditable(false);
						txtNama.setEditable(false);
					}
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 341, 267);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setBounds(10, 11, 91, 16);
		frame.getContentPane().add(lblKodeBarang);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(10, 36, 91, 16);
		frame.getContentPane().add(lblNamaBarang);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(10, 61, 91, 16);
		frame.getContentPane().add(lblSatuan);
		
		JLabel lblQty = new JLabel("Qty");
		lblQty.setBounds(10, 111, 91, 16);
		frame.getContentPane().add(lblQty);
		
		JLabel lblHargaBeli = new JLabel("Harga Beli");
		lblHargaBeli.setBounds(10, 136, 91, 16);
		frame.getContentPane().add(lblHargaBeli);
		
		JLabel lblHargaJual = new JLabel("Harga Jual");
		lblHargaJual.setBounds(10, 161, 91, 16);
		frame.getContentPane().add(lblHargaJual);
		
		JLabel lblStokSaatIni = new JLabel("Stok Saat Ini");
		lblStokSaatIni.setBounds(10, 86, 91, 16);
		frame.getContentPane().add(lblStokSaatIni);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(101, 6, 224, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setText("nama");
		txtNama.setBounds(101, 31, 224, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() >= 0) 
					AmbilDetailBarang(txtId.getText());
			}
		});
		comboBox.setBounds(101, 56, 224, 26);
		frame.getContentPane().add(comboBox);
		
		txtStok = new JTextField();
		txtStok.setText("stok");
		txtStok.setBounds(101, 81, 224, 26);
		frame.getContentPane().add(txtStok);
		txtStok.setColumns(10);
		
		txtQty = new JTextField();
		txtQty.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '') && (c != '.')) {
		        	arg0.consume();
		        }
			}
		});
		txtQty.setText("Qty");
		txtQty.setBounds(101, 106, 224, 26);
		frame.getContentPane().add(txtQty);
		txtQty.setColumns(10);
		
		txtHargaBeli = new JTextField();
		txtHargaBeli.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '')) {
		        	arg0.consume();
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() != 37) && (e.getKeyCode() != 39)) {
					try {
						txtHargaBeli.setText(FMain.FormatAngka(Integer.valueOf(txtHargaBeli.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtHargaBeli.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	txtHargaBeli.setText("");
					}
				}
			}
		});
		txtHargaBeli.setText("harga beli");
		txtHargaBeli.setBounds(101, 131, 224, 26);
		frame.getContentPane().add(txtHargaBeli);
		txtHargaBeli.setColumns(10);
		
		txtHargaJual = new JTextField();
		txtHargaJual.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '')) {
		        	arg0.consume();
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() != 37) && (e.getKeyCode() != 39)) {
					try {
						txtHargaJual.setText(FMain.FormatAngka(Integer.valueOf(txtHargaJual.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtHargaJual.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	txtHargaJual.setText("");
					}
				}
			}
		});
		txtHargaJual.setText("harga jual");
		txtHargaJual.setBounds(101, 158, 224, 26);
		frame.getContentPane().add(txtHargaJual);
		txtHargaJual.setColumns(10);
		
		JButton btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtId.getText().equals("") || 
						txtNama.getText().equals("") ||
						txtHargaBeli.getText().equals("") ||
						txtHargaJual.getText().equals("") ||
						comboBox.getSelectedIndex() <= 0) {
					JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!");
					txtHargaBeli.requestFocus();
				} else {
					TambahkanDaftarPembelian();
					FPembelian.window.frame.setEnabled(true);
					FPembelian.txtKodeNama.setText("");
					FPembelian.txtKodeNama.requestFocus();
					window.frame.dispose();
				}
			}
		});
		btnTambah.setBounds(105, 209, 105, 26);
		frame.getContentPane().add(btnTambah);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(220, 209, 105, 26);
		frame.getContentPane().add(btnBatal);
		
		JLabel lblIsi = new JLabel("Isi");
		lblIsi.setBounds(10, 186, 91, 16);
		frame.getContentPane().add(lblIsi);
		
		txtIsi = new JTextField();
		txtIsi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '') && (c != '.')) {
		        	arg0.consume();
		        }
			}
		});
		txtIsi.setText("isi");
		txtIsi.setBounds(101, 181, 128, 26);
		frame.getContentPane().add(txtIsi);
		txtIsi.setColumns(10);
		
		comboBox_1 = new JComboBox<>();
		comboBox_1.setBounds(239, 181, 86, 26);
		frame.getContentPane().add(comboBox_1);
	}
	
	private void DataBaru() {
		txtHargaBeli.setText("");
		txtHargaJual.setText("");
		txtId.setText("");
		txtNama.setText("");
		txtQty.setText("");
		txtStok.setText("");
	}
	
	private void AmbilDataBarang(String str) {
		db.con();
		try {
			String query = "select * from tb_barang where id = ? or nama = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, str);
			ps.setString(2, str);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtId.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error cari data " + e);
		}
	}
	
	private void AmbilSatuan() {
		db.con();
		try {
			String query = "select * from tb_satuan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			comboBox.removeAllItems();
			comboBox.addItem("-PILIH SATUAN-");
			comboBox_1.removeAllItems();
			comboBox_1.addItem("-PILIH-");
			while (rs.next()) {
				comboBox.addItem(rs.getString("satuan"));
				comboBox_1.addItem(rs.getString("satuan"));
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil satuan " + e);
		}
	}
	
	private void AmbilDetailBarang(String id) {
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, comboBox.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				txtStok.setText(rs.getString("stok"));
				txtHargaJual.setText(FMain.FormatAngka(rs.getInt("harga_jual")));
				txtIsi.setText(rs.getString("isi"));
				comboBox_1.setSelectedItem(rs.getString("satuan_isi"));
			} else {
				txtStok.setText("0");
				txtHargaJual.setText("");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error cari data " + e);
		}
	}
	
	private void TambahkanDaftarPembelian() {
		int harga = Integer.parseInt(txtHargaBeli.getText().replace(",", ""));
		double qty = Double.parseDouble(txtQty.getText());
		if (!edit) {
			boolean cek = false;
			for (int i = 0; i < FPembelian.model.getRowCount(); i++) {
				if (FPembelian.model.getValueAt(i, 0).toString().equals(txtId.getText())) {
					cek = true;
					break;
				}
			}
			if (!cek) {
				//"Kode Barang", "Nama Barang", "Satuan", "Harga Beli", "Qty", "Total"
				FPembelian.model.addRow(new Object[] {
						txtId.getText(),
						txtNama.getText(),
						comboBox.getSelectedItem().toString(),
						txtHargaBeli.getText(),
						txtQty.getText(),
						FMain.FormatAngka((int) (harga * qty))
				});
	
				TambahItem();
				TambahDetailBarang();
			} else {
				JOptionPane.showMessageDialog(null, "Data barang telah ada!!!");
			}
		}
		
		int subtotal = 0;
		for (int i = 0; i < FPembelian.model.getRowCount(); i++) {
			subtotal += Integer.parseInt(FPembelian.model.getValueAt(i, 5).toString().replace(",", ""));
		}
		FPembelian.txtSubTotal.setText(FMain.FormatAngka(subtotal));
	}
	
	private void TambahItem() {
		db.con();
		try {
			String query= "insert into tb_barang(id, nama, harga_beli) values(?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, txtNama.getText());
			ps.setString(3, txtHargaBeli.getText().replace(",", ""));
			ps.execute();
			
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tambah item " + e);
			UpdateItem();
		}
	}
	
	private void UpdateItem() {
		db.con();
		try {
			String query = "update tb_barang set harga_beli = ? where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtHargaBeli.getText().replace(",", ""));
			ps.setString(2, txtId.getText());
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error update item " + e);
		}
	}
	
	private void TambahDetailBarang() { 
		db.con();
		try {
			String query = "insert into tb_detail_barang values(?,?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, comboBox.getSelectedItem().toString());
			ps.setString(3, txtQty.getText());
			ps.setString(4, txtHargaJual.getText().replace(",", ""));
			ps.setString(5, txtIsi.getText());
			ps.setString(6, comboBox_1.getSelectedItem().toString());
			ps.setString(7, txtHargaBeli.getText().replace(",", ""));
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tambah detail barang " + e);
			UpdateDetailBarang();
			UpdateDetail(txtId.getText(), comboBox.getSelectedItem().toString());
		}
	}
	
	private void UpdateDetailBarang() {
		db.con();
		try {
			String query = "update tb_detail_barang set "
					+ "stok = stok + ? where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(txtQty.getText()));
			ps.setString(2, txtId.getText());
			ps.setString(3, comboBox.getSelectedItem().toString());
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error update detail barang " + e);
		}
	}
	
	private void UpdateDetail(String id_barang, String satuan) {
		db.con();
		try {
			String query = "update tb_detail_barang as a inner join "
					+ "(select (b.harga_beli/b.isi) as hrg from tb_detail_barang as b "
					+ "	where b.id_barang = ? and b.satuan_isi = ?) as c "
					+ "set a.harga_beli = c.hrg "
					+ "where a.id_barang = ? and a.satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id_barang);
			ps.setString(2, satuan);
			ps.setString(3, id_barang);
			ps.setString(4, satuan);
			ps.execute();
			db.con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
