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

public class FEditHarga {

	JFrame frame;
	static FEditHarga window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtSatuan;
	private JTextField txtHargaLama;
	private JTextField txtHargaBaru;
	private Database db;
	static String id = null;
	static String nama = null;
	static String satuan = null;
	private JLabel lblSatuanHargaLama;
	private JLabel lblSatuanHargaBaru;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FEditHarga();
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
	public FEditHarga() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Edit Harga");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				txtId.setText(id);
				txtNama.setText(nama);
				txtHargaLama.setText(FMain.FormatAngka(AmbilHargaLama(id, satuan)));
				txtSatuan.setText(satuan);
				lblSatuanHargaLama.setText(" / " + satuan);
				lblSatuanHargaBaru.setText(" / " + satuan);
				txtId.setEditable(false);
				txtNama.setEditable(false);
				txtSatuan.setEditable(false);
				txtHargaLama.setEditable(false);
				txtHargaBaru.requestFocus();
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 323, 210);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setBounds(10, 11, 72, 16);
		frame.getContentPane().add(lblKodeBarang);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(10, 36, 72, 16);
		frame.getContentPane().add(lblNamaBarang);
		
		JLabel lblHargaLama = new JLabel("Harga Lama");
		lblHargaLama.setBounds(10, 86, 72, 16);
		frame.getContentPane().add(lblHargaLama);
		
		JLabel lblHargaBaru = new JLabel("Harga Baru");
		lblHargaBaru.setBounds(10, 111, 72, 16);
		frame.getContentPane().add(lblHargaBaru);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(10, 61, 46, 16);
		frame.getContentPane().add(lblSatuan);
		
		txtId = new JTextField();
		txtId.setBounds(105, 6, 192, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setBounds(105, 31, 192, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtSatuan = new JTextField();
		txtSatuan.setBounds(105, 56, 192, 26);
		frame.getContentPane().add(txtSatuan);
		txtSatuan.setColumns(10);
		
		txtHargaLama = new JTextField();
		txtHargaLama.setBounds(105, 81, 120, 26);
		frame.getContentPane().add(txtHargaLama);
		txtHargaLama.setColumns(10);
		
		txtHargaBaru = new JTextField();
		txtHargaBaru.addKeyListener(new KeyAdapter() {
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
						txtHargaBaru.setText(FMain.FormatAngka(Integer.valueOf(txtHargaBaru.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtHargaBaru.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	
						txtHargaBaru.setText("");
					}
				}
			}
		});
		txtHargaBaru.setBounds(105, 106, 120, 26);
		frame.getContentPane().add(txtHargaBaru);
		txtHargaBaru.setColumns(10);
		
		lblSatuanHargaLama = new JLabel("");
		lblSatuanHargaLama.setBounds(235, 86, 62, 16);
		frame.getContentPane().add(lblSatuanHargaLama);
		
		lblSatuanHargaBaru = new JLabel("");
		lblSatuanHargaBaru.setBounds(235, 111, 62, 16);
		frame.getContentPane().add(lblSatuanHargaBaru);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengubah harga "
						+ "\n" + nama + " menjadi Rp." + txtHargaBaru.getText(), "Konfirmasi", 0);
				if (konfirm == 0) {
					UpdateHarga(txtId.getText(), txtSatuan.getText());
					JOptionPane.showMessageDialog(null, "Harga berhasil diubah");
					FStokBarangNew.window.frame.setEnabled(true);
					window.frame.dispose();
				}
			}
		});
		btnSimpan.setBounds(105, 139, 89, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(208, 139, 89, 26);
		frame.getContentPane().add(btnBatal);
	}
	
	private void UpdateHarga(String id, String satuan) {
		db.con();
		try {
			String query = "update tb_detail_barang set harga_jual = ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtHargaBaru.getText().replace(",", ""));
			ps.setString(2, id);
			ps.setString(3, satuan);
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error update harga " + e);
		}
	}
	
	private int AmbilHargaLama(String id, String satuan) {
		db.con();
		try {
			String query = "select harga_jual from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("harga_jual");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil harga lama " + e);
		}
		return 0;
	}
}
