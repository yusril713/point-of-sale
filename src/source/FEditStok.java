package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;

public class FEditStok {

	JFrame frame;
	static FEditStok window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtSatuan;
	private JTextField txtSisaStok;
	private JTextField txtStokBaru;
	private Database db;
	static String id;
	static String nama;
	static String satuan;
	static String stok;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FEditStok();
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
	public FEditStok() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				db = new Database();
				txtId.setText(id);
				txtNama.setText(nama);
				txtSatuan.setText(satuan);
				txtSisaStok.setText(stok);
				txtStokBaru.setText("");
				txtStokBaru.requestFocus();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 351, 210);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKode = new JLabel("ID");
		lblKode.setBounds(10, 11, 75, 16);
		frame.getContentPane().add(lblKode);
		
		JLabel lblNama = new JLabel("Nama");
		lblNama.setBounds(10, 36, 75, 16);
		frame.getContentPane().add(lblNama);
		
		JLabel lblSisaStok = new JLabel("Sisa Stok");
		lblSisaStok.setBounds(10, 86, 75, 16);
		frame.getContentPane().add(lblSisaStok);
		
		JLabel lblStokBaru = new JLabel("Stok Baru");
		lblStokBaru.setBounds(10, 111, 75, 16);
		frame.getContentPane().add(lblStokBaru);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(10, 61, 75, 16);
		frame.getContentPane().add(lblSatuan);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("id");
		txtId.setBounds(95, 6, 225, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setText("nama");
		txtNama.setBounds(95, 31, 225, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtSatuan = new JTextField();
		txtSatuan.setEditable(false);
		txtSatuan.setText("satuan");
		txtSatuan.setBounds(95, 56, 225, 26);
		frame.getContentPane().add(txtSatuan);
		txtSatuan.setColumns(10);
		
		txtSisaStok = new JTextField();
		txtSisaStok.setEditable(false);
		txtSisaStok.setText("sisa stok");
		txtSisaStok.setBounds(95, 81, 225, 26);
		frame.getContentPane().add(txtSisaStok);
		txtSisaStok.setColumns(10);
		
		txtStokBaru = new JTextField();
		txtStokBaru.setText("stok baru");
		txtStokBaru.setBounds(95, 106, 225, 26);
		frame.getContentPane().add(txtStokBaru);
		txtStokBaru.setColumns(10);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0; 
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Simpan(txtId.getText(), txtSatuan.getText());
					FStokBarangNew.window.frame.setEnabled(true);
					window.frame.dispose();
				}
			}
		});
		btnSimpan.setBounds(130, 139, 89, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(231, 139, 89, 26);
		frame.getContentPane().add(btnBatal);
	}
	
	private void Simpan(String id, String satuan) {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtStokBaru.getText());
			ps.setString(2, id);
			ps.setString(3, satuan);
			ps.execute();
			
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error " + e);
		}
	}
}
