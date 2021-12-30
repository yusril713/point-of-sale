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

public class FTambahBarang {

	JFrame frame;
	static FTambahBarang window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtHargaBeli;
	private JTextField txtHargaJual;
	private JTextField txtIsi;
	private JComboBox<String> cmbSupplier;
	private JTextField txtJumlah;
	private JComboBox<String> cmbSatuan;
	private JTextField txtSatuan;
	private JComboBox<String> cmbSatuanIsi;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FTambahBarang();
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
	public FTambahBarang() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Tambah Barang");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				TampilSatuan();
				TampilSupplier();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 374, 255);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setBounds(10, 11, 74, 16);
		frame.getContentPane().add(lblKodeBarang);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(10, 36, 74, 16);
		frame.getContentPane().add(lblNamaBarang);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setBounds(10, 136, 74, 16);
		frame.getContentPane().add(lblSupplier);
		
		JLabel lblHargaBeli = new JLabel("Harga Beli");
		lblHargaBeli.setBounds(10, 61, 74, 16);
		frame.getContentPane().add(lblHargaBeli);
		
		JLabel lblHargaJual = new JLabel("Harga Jual");
		lblHargaJual.setBounds(10, 86, 74, 16);
		frame.getContentPane().add(lblHargaJual);
		
		JLabel lblIsi = new JLabel("Isi");
		lblIsi.setBounds(10, 111, 74, 16);
		frame.getContentPane().add(lblIsi);
		
		JLabel lblJumlahBarang = new JLabel("Jumlah Brg");
		lblJumlahBarang.setBounds(10, 161, 74, 16);
		frame.getContentPane().add(lblJumlahBarang);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(94, 6, 253, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setText("nama");
		txtNama.setBounds(94, 31, 253, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
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
						}	
						txtHargaBeli.setText("");
					}
				}
			}
		});
		txtHargaBeli.setText("harga beli");
		txtHargaBeli.setBounds(94, 56, 138, 26);
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
						}	
						txtHargaJual.setText("");
					}
				}
			}
		});
		txtHargaJual.setText("harga jual");
		txtHargaJual.setBounds(94, 81, 138, 26);
		frame.getContentPane().add(txtHargaJual);
		txtHargaJual.setColumns(10);
		
		txtIsi = new JTextField();
		txtIsi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '')) {
		        	arg0.consume();
		        }
			}
		});
		txtIsi.setText("isi");
		txtIsi.setBounds(94, 106, 46, 26);
		frame.getContentPane().add(txtIsi);
		txtIsi.setColumns(10);
		
		cmbSupplier = new JComboBox<>();
		cmbSupplier.setBounds(94, 131, 253, 26);
		frame.getContentPane().add(cmbSupplier);
		
		txtJumlah = new JTextField();
		txtJumlah.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '')) {
		        	arg0.consume();
		        }
			}
		});
		txtJumlah.setText("jumlah");
		txtJumlah.setBounds(94, 156, 86, 26);
		frame.getContentPane().add(txtJumlah);
		txtJumlah.setColumns(10);
		
		JLabel label = new JLabel("/");
		label.setBounds(238, 61, 4, 14);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(238, 86, 4, 14);
		frame.getContentPane().add(label_1);
		
		cmbSatuan = new JComboBox<>();
		cmbSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbSatuan.getSelectedIndex() >= 0) 
					txtSatuan.setText(cmbSatuan.getSelectedItem().toString());
			}
		});
		cmbSatuan.setBounds(252, 56, 95, 26);
		frame.getContentPane().add(cmbSatuan);
		
		txtSatuan = new JTextField();
		txtSatuan.setText("satuan");
		txtSatuan.setBounds(252, 81, 95, 26);
		frame.getContentPane().add(txtSatuan);
		txtSatuan.setColumns(10);
		
		cmbSatuanIsi = new JComboBox<>();
		cmbSatuanIsi.setBounds(146, 106, 86, 26);
		frame.getContentPane().add(cmbSatuanIsi);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(258, 182, 89, 26);
		frame.getContentPane().add(btnBatal);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if(txtHargaBeli.getText().equals("") || 
							txtHargaJual.getText().equals("") ||
							txtId.getText().equals("") || 
							txtIsi.getText().equals("") ||
							txtJumlah.getText().equals("") || 
							txtNama.getText().equals("") ||
							txtSatuan.getText().equals("") ||
							cmbSatuanIsi.getSelectedIndex() <= 0 ||
							cmbSupplier.getSelectedIndex() <= 0) {
						JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
						txtId.requestFocus();
					} else {
						if (!CekBarang(txtId.getText())) {
							SimpanBarang();
							SimpanDetailBarang();
							JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
							FStokBarangNew.window.frame.setEnabled(true);
							FStokBarangNew.TampilBarang("");
							window.frame.dispose();
						}
					}
				}
			}
		});
		btnSimpan.setBounds(159, 182, 89, 26);
		frame.getContentPane().add(btnSimpan);
	}
	
	private void DataBaru() {
		txtHargaBeli.setText("");
		txtHargaJual.setText("");
		txtId.setText("");
		txtIsi.setText("");
		txtJumlah.setText("");
		txtNama.setText("");
		txtSatuan.setText("");
	} 
	
	private void TampilSatuan() {
		cmbSatuan.removeAllItems();
		cmbSatuanIsi.removeAllItems();
		cmbSatuan.addItem("-PILIH-");
		cmbSatuanIsi.addItem("-PILIH-");
		db.con();
		try {
			String query = "select * from tb_satuan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				cmbSatuan.addItem(rs.getString("satuan"));
				cmbSatuanIsi.addItem(rs.getString("satuan"));
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil satuan: " + e);
		}
 	}
	
	private void TampilSupplier() {
		cmbSupplier.removeAllItems();
		cmbSupplier.addItem("-PILIH-");
		db.con();
		try {
			String query = "select * from tb_supplier";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				cmbSupplier.addItem(rs.getString("id") + " - " + rs.getString("nama"));
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil suplier " + e);
		}
	}
	
	private String AmbilID(String teks) {
		return teks.substring(0, teks.indexOf(" "));
	}
	
	private boolean CekBarang(String id) {
		db.con();
		try {
			String query = "select * from tb_barang where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch(Exception e) {
			System.out.println("error cek barang " + e);
		}
		return false;
	}
	
	private void SimpanBarang() {
		db.con();
		try {
			String query = "insert into tb_barang values(?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, txtNama.getText());
			ps.setString(3, txtHargaBeli.getText().replace(",", ""));
			ps.setString(4, AmbilID(cmbSupplier.getSelectedItem().toString()));
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan barang " + e);
		}
	}
	
	private void SimpanDetailBarang() {
		db.con();
		try {
			String query = "insert into tb_detail_barang values(?,?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, txtSatuan.getText());
			ps.setString(3, txtJumlah.getText());
			ps.setString(4, txtHargaJual.getText().replace(",", ""));
			ps.setString(5, txtIsi.getText());
			ps.setString(6, cmbSatuanIsi.getSelectedItem().toString());
			ps.setString(7, txtHargaBeli.getText().replace(",", ""));
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan detail barang " + e);
		}
	}
}
