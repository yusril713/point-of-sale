package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class FPecahSatuan {

	JFrame frame;
	static FPecahSatuan window;
	private JTextField txtNama;
	private JTextField txtJumlah;
	private JTextField txtHarga;
	private Database db;
	static String nama = null;
	static String id = null;
	static String satuan = null;
	private JLabel lblSatuanJumlah;
	private JLabel lblSatuanHarga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FPecahSatuan();
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
	public FPecahSatuan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Pecah Satuan Brg");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				db = new Database();
				lblSatuanJumlah.setText(satuan);
				if (AmbilSatuanIsi(id, satuan).equals("-") ||
						AmbilSatuanIsi(id, satuan).equals("")) {
					JOptionPane.showMessageDialog(null, "Satuan isi belum ada! "
							+ "\nSet satuan isi terlebih dahulu");
					FStokBarangNew.window.frame.setEnabled(true);
					window.frame.dispose();
				}
				
				lblSatuanHarga.setText(" / " + AmbilSatuanIsi(id, satuan));
				txtNama.setText(nama);
				txtJumlah.setText("");
				txtHarga.setText("");
				txtHarga.setText(AmbilHarga(id, AmbilSatuanIsi(id, satuan)));
				txtJumlah.requestFocus();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 279, 152);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNamaBarang = new JLabel("Nama Brg");
		lblNamaBarang.setBounds(10, 11, 64, 16);
		frame.getContentPane().add(lblNamaBarang);
		
		JLabel lblJumlah = new JLabel("Jumlah");
		lblJumlah.setBounds(10, 36, 46, 16);
		frame.getContentPane().add(lblJumlah);
		
		JLabel lblHargaJual = new JLabel("Harga Jual");
		lblHargaJual.setBounds(10, 61, 64, 16);
		frame.getContentPane().add(lblHargaJual);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setText("nama");
		txtNama.setBounds(96, 6, 163, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
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
		txtJumlah.setBounds(96, 31, 58, 26);
		frame.getContentPane().add(txtJumlah);
		txtJumlah.setColumns(10);
		
		txtHarga = new JTextField();
		txtHarga.addKeyListener(new KeyAdapter() {
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
						txtHarga.setText(FMain.FormatAngka(Integer.valueOf(txtHarga.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtHarga.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	
						txtHarga.setText("");
					}
				}
			}
		});
		txtHarga.setText("harga");
		txtHarga.setBounds(96, 56, 114, 26);
		frame.getContentPane().add(txtHarga);
		txtHarga.setColumns(10);
		
		lblSatuanJumlah = new JLabel("");
		lblSatuanJumlah.setBounds(164, 36, 46, 16);
		frame.getContentPane().add(lblSatuanJumlah);
		
		lblSatuanHarga = new JLabel("");
		lblSatuanHarga.setBounds(213, 61, 46, 16);
		frame.getContentPane().add(lblSatuanHarga);
		
		JButton btnPecahSatuan = new JButton("Pecah Satuan");
		btnPecahSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin memecah satuan barag "
						+ "\n" + nama + "?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (txtJumlah.getText().equals("") || txtHarga.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Lengkapi data!");
						txtJumlah.requestFocus();
					} else {
						PecahSatuan();
						UpdateDetailBarang();
						String satuanIsi = AmbilSatuanIsi(id, satuan);
						UpdateDetail(id, satuanIsi);
						JOptionPane.showMessageDialog(null, "Satuan berhasil dipecah");
						FStokBarangNew.window.frame.setEnabled(true);
						window.frame.dispose(); 
					}
				}
			}
		});
		btnPecahSatuan.setBounds(20, 86, 115, 26);
		frame.getContentPane().add(btnPecahSatuan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(144, 86, 115, 26);
		frame.getContentPane().add(btnBatal);
	}
	
	private int AmbilIsi(String id, String satuan) {
		int isi = 0;
		db.con();
		try {
			String query = "select isi from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				isi = rs.getInt("isi");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil isi " + e);
		}
		return isi;
	}
	
	private String AmbilSatuanIsi(String id, String satuan) {
		String satuanIsi = null;
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				satuanIsi = rs.getString("satuan_isi");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil isi " + e);
		}
		return satuanIsi;
	}
	
	private int UpdateDetail(String id_barang, String satuan) {
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
		return 0;
	}
	
	private void PecahSatuan() {
		String satuanIsi = AmbilSatuanIsi(id, satuan);
		int isi = AmbilIsi(id, satuan);
		int jumlah = Integer.parseInt(txtJumlah.getText());
		int totalStok = jumlah * isi;
		db.con();
		try {
			String query = "insert into tb_detail_barang (id_barang, satuan, stok, harga_jual, isi, satuan_isi) "
					+ "values(?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuanIsi);
			ps.setInt(3, totalStok);
			ps.setString(4, txtHarga.getText().replace(",", ""));
			ps.setString(5, "0");
			ps.setString(6, "-");
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			UpdateStok();
		}
	}
	
	private void UpdateDetailBarang() {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = stok - ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(txtJumlah.getText()));
			ps.setString(2, id);
			ps.setString(3, satuan);
			ps.execute();
		} catch(Exception e) {
			System.out.println("error update detail barang " + e);
		}
	}
	
	private void UpdateStok() {
		String satuanIsi = AmbilSatuanIsi(id, satuan);
		int isi = AmbilIsi(id, satuan);
		int jumlah = Integer.parseInt(txtJumlah.getText());
		int totalStok = jumlah * isi;
		db.con();
		try {
			String query = "update tb_detail_barang set stok = stok + ? where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setInt(1, totalStok);
			ps.setString(2, id);
			ps.setString(3, satuanIsi);
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error pecah satuan: " + e);
		}
	}
	
	private String AmbilHarga(String id, String satuan) {
		db.con();
		try {
			String query = "select harga_jual from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return FMain.FormatAngka(rs.getInt("harga_jual"));
			}
		} catch(Exception e) {
			System.out.println("error ambil harga " + e);
		}
		return "";
	}
}
