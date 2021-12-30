package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class FTambahBarangRusak {

	JFrame frame;
	static FTambahBarangRusak window;
	static JTextField txtKode;
	static JTextField txtNama;
	private JTextField txtJumlah;
	static JLabel lblSatuan_1;
	private Database db;
	private JTextPane txtpnKeterangan;
	static JTextField txtStok;
	static JLabel lblSatuan;
	private JButton button;
	static boolean edit = false;
	static String id = null, satuan = null, jumlah = null, keterangan = null, id_r = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FTambahBarangRusak();
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
	public FTambahBarangRusak() {
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
				DataBaru();
				db = new Database();
				if(edit) {
					button.setEnabled(false);
					TampilBarang(id, satuan);
					txtJumlah.setText(jumlah);
					txtpnKeterangan.setText(keterangan);
					
					txtJumlah.requestFocus();
					txtJumlah.selectAll();
				}
			}
		});
		frame.setBounds(100, 100, 311, 239);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIdBrg = new JLabel("ID Brg");
		lblIdBrg.setBounds(6, 6, 52, 16);
		frame.getContentPane().add(lblIdBrg);
		
		JLabel lblNamaBrg = new JLabel("Nama Brg");
		lblNamaBrg.setBounds(6, 34, 69, 16);
		frame.getContentPane().add(lblNamaBrg);
		
		JLabel lblJumlah = new JLabel("Jumlah");
		lblJumlah.setBounds(6, 90, 52, 16);
		frame.getContentPane().add(lblJumlah);
		
		txtKode = new JTextField();
		txtKode.setEditable(false);
		txtKode.setText("kode");
		txtKode.setBounds(82, 1, 158, 26);
		frame.getContentPane().add(txtKode);
		txtKode.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setText("nama");
		txtNama.setBounds(82, 29, 207, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtJumlah = new JTextField();
		txtJumlah.setText("jumlah");
		txtJumlah.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtJumlah.selectAll();
			}
		});
		txtJumlah.addKeyListener(new KeyAdapter() {
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
		txtJumlah.setBounds(82, 85, 102, 26);
		frame.getContentPane().add(txtJumlah);
		txtJumlah.setColumns(10);
		
		lblSatuan_1 = new JLabel("satuan");
		lblSatuan_1.setBounds(196, 90, 52, 16);
		frame.getContentPane().add(lblSatuan_1);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtKode.getText().equals("") && !txtJumlah.getText().equals("") && !txtpnKeterangan.getText().equals("")) {
					if (Double.parseDouble(txtStok.getText()) < Double.parseDouble(txtJumlah.getText())) 
						JOptionPane.showMessageDialog(null, "Jumlah brg rusak melebihi stok saat ini");
					else {
						int konfirm = 0;
						konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
						if (konfirm == 0) {
							if (edit) 
								Update(id_r);
							else 
								Simpan();
						}
					}
				} else 
					JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
			}
		});
		btnSimpan.setBounds(94, 176, 97, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FBarangRusak.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(192, 176, 97, 26);
		frame.getContentPane().add(btnBatal);
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FCariBarangRusak.main(null);
				window.frame.setEnabled(false);
			}
		});
		button.setIcon(new ImageIcon(FTambahBarangRusak.class.getResource("/org/jdesktop/swingx/plaf/windows/resources/search.gif")));
		button.setBounds(243, 1, 46, 26);
		frame.getContentPane().add(button);
		
		JLabel lblKeterangan = new JLabel("Keterangan");
		lblKeterangan.setBounds(6, 118, 69, 16);
		frame.getContentPane().add(lblKeterangan);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(82, 114, 207, 54);
		frame.getContentPane().add(scrollPane);
		
		txtpnKeterangan = new JTextPane();
		scrollPane.setViewportView(txtpnKeterangan);
		txtpnKeterangan.setText("keterangan");
		
		JLabel lblStokSaatIni = new JLabel("Stok saat ini");
		lblStokSaatIni.setBounds(6, 62, 69, 16);
		frame.getContentPane().add(lblStokSaatIni);
		
		txtStok = new JTextField();
		txtStok.setEditable(false);
		txtStok.setText("stok");
		txtStok.setBounds(82, 57, 102, 26);
		frame.getContentPane().add(txtStok);
		txtStok.setColumns(10);
		
		lblSatuan = new JLabel("satuan");
		lblSatuan.setBounds(196, 62, 52, 16);
		frame.getContentPane().add(lblSatuan);
	}
	
	private void DataBaru() {
		txtKode.setText(null);
		txtNama.setText(null);
		txtJumlah.setText(null);
	}
	
	private void Simpan() {
		db.con();
		try {
			String query = "insert into tb_barang_rusak (id_barang, satuan, jumlah, keterangan) values(?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKode.getText());
			ps.setString(2, lblSatuan_1.getText());
			ps.setString(3, txtJumlah.getText());
			ps.setString(4, txtpnKeterangan.getText());
			ps.execute();
			
			query = "update tb_detail_barang set stok = stok - ? where id_barang = ? and satuan = ?";
			ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(txtJumlah.getText()));
			ps.setString(2, txtKode.getText());
			ps.setString(3, lblSatuan_1.getText());
			ps.execute();
			
			ps.close();
			db.con.close();
			
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
			FBarangRusak.window.frame.setEnabled(true);
			FBarangRusak.TampilBarangRusak("");
			FBarangRusak.TampilDetailBarangRusak("");
			window.frame.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void TampilBarang(String id, String satuan) {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = stok + ? where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(jumlah));
			ps.setString(2, id);
			ps.setString(3, satuan);
			
			query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.stok from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "where tb_barang.id = ? and tb_detail_barang.satuan = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				txtKode.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
				lblSatuan.setText(rs.getString("satuan"));
				lblSatuan_1.setText(rs.getString("satuan"));
				txtStok.setText(rs.getString("stok"));
			}
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Update(String id) {
		db.con();
		try {
			String query = "update tb_barang_rusak set jumlah = ?, keterangan = ? "
					+ "where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtJumlah.getText());
			ps.setString(2, txtpnKeterangan.getText());
			ps.setString(3, id);
			ps.execute();
			
			query = "update tb_detail_barang set stok = stok - ? where id_barang = ? and satuan = ?";
			ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(txtJumlah.getText()));
			ps.setString(2, txtKode.getText());
			ps.setString(3, lblSatuan_1.getText());
			ps.execute();
			
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil diubah...");
			FBarangRusak.window.frame.setEnabled(true);
			FBarangRusak.TampilBarangRusak("");
			FBarangRusak.TampilDetailBarangRusak("");
			window.frame.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
