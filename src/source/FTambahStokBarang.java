package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class FTambahStokBarang {

	JFrame frame;
	static FTambahStokBarang window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtSatuan;
	private JTextField txtStokSaatIni;
	private JTextField txtStokYgAkan;
	private JTextField txtStokSetelahDitambahkan;
	private JLabel label_1;
	private JLabel label_2;
	private Database db;
	static String id = null;
	static String nama = null;
	static String satuan = null;
	static double stok = 0;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FTambahStokBarang();
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
	public FTambahStokBarang() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Tambah Stok Barang");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				txtId.setText(id);
				txtNama.setText(nama);
				txtSatuan.setText(satuan);
				label.setText(satuan);
				label_1.setText(satuan);
				label_2.setText(satuan);
				txtStokSaatIni.setText("" + stok);
				txtStokYgAkan.requestFocus();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 450, 221);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setBounds(10, 11, 142, 16);
		frame.getContentPane().add(lblKodeBarang);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(10, 36, 142, 16);
		frame.getContentPane().add(lblNamaBarang);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(10, 61, 46, 16);
		frame.getContentPane().add(lblSatuan);
		
		JLabel lblStokSaatIni = new JLabel("Stok Saat Ini");
		lblStokSaatIni.setBounds(10, 86, 142, 16);
		frame.getContentPane().add(lblStokSaatIni);
		
		JLabel lblJumlah = new JLabel("Stok yang Akan Ditambahkan");
		lblJumlah.setBounds(10, 111, 179, 16);
		frame.getContentPane().add(lblJumlah);
		
		JLabel lblStokSeletahDitambahkan = new JLabel("Stok Seletah Ditambahkan");
		lblStokSeletahDitambahkan.setBounds(10, 136, 142, 16);
		frame.getContentPane().add(lblStokSeletahDitambahkan);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(201, 6, 233, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setBounds(201, 31, 233, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtSatuan = new JTextField();
		txtSatuan.setEditable(false);
		txtSatuan.setBounds(201, 56, 233, 26);
		frame.getContentPane().add(txtSatuan);
		txtSatuan.setColumns(10);
		
		txtStokSaatIni = new JTextField();
		txtStokSaatIni.setEditable(false);
		txtStokSaatIni.setBounds(201, 81, 123, 26);
		frame.getContentPane().add(txtStokSaatIni);
		txtStokSaatIni.setColumns(10);
		
		txtStokYgAkan = new JTextField();
		txtStokYgAkan.addKeyListener(new KeyAdapter() {
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
		txtStokYgAkan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				StokSetelahDitambah();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				StokSetelahDitambah();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				StokSetelahDitambah();
			}
		});
		txtStokYgAkan.setBounds(201, 106, 123, 26);
		frame.getContentPane().add(txtStokYgAkan);
		txtStokYgAkan.setColumns(10);
		
		txtStokSetelahDitambahkan = new JTextField();
		txtStokSetelahDitambahkan.setEditable(false);
		txtStokSetelahDitambahkan.setBounds(201, 131, 123, 26);
		frame.getContentPane().add(txtStokSetelahDitambahkan);
		txtStokSetelahDitambahkan.setColumns(10);
		
		label = new JLabel("");
		label.setBounds(347, 86, 87, 16);
		frame.getContentPane().add(label);
		
		label_1 = new JLabel("");
		label_1.setBounds(347, 111, 87, 16);
		frame.getContentPane().add(label_1);
		
		label_2 = new JLabel("");
		label_2.setBounds(347, 136, 87, 16);
		frame.getContentPane().add(label_2);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menambahkan stok " + nama, "Konfirmasi", 0);
				if (konfirm == 0) {
					SimpanStokBaru();
					JOptionPane.showMessageDialog(null, "Stok berhasil ditambahkan");
					FStokBarangNew.window.frame.setEnabled(true);
					window.frame.dispose();
				}
			}
		});
		btnSimpan.setBounds(201, 158, 105, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(329, 158, 105, 26);
		frame.getContentPane().add(btnBatal);
	}
	
	private void StokSetelahDitambah() {
		double stokLama = Double.parseDouble(txtStokSaatIni.getText());
		double jumlah = (txtStokYgAkan.getText().equals("")) ? 0 : Double.parseDouble(txtStokYgAkan.getText());
		double stokBaru = stokLama + jumlah;
		txtStokSetelahDitambahkan.setText("" + stokBaru);
	}
	
	private void SimpanStokBaru() {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtStokSetelahDitambahkan.getText());
			ps.setString(2, id);
			ps.setString(3, satuan);
			ps.execute();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("error smpan " + e);
		}
	}
}
