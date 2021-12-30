package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class FEditSatuan {

	JFrame frame;
	static FEditSatuan window;
	private JTextField txtIsi;
	private JLabel lblSatuan;
	private JLabel lblSatuanIsi;
	private JLabel lblIsi;
	private JComboBox<String> comboBox;
	private JButton btnSimpan;
	private Database db;
	static String id = null;
	static String satuan = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FEditSatuan();
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
	public FEditSatuan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Satuan Isi");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				db = new Database();
				TampilSatuan();
				System.out.println(id + "  " + satuan);
				AmbilSatuanIsi(id, satuan);
				txtIsi.requestFocus();
				txtIsi.selectAll();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				id = satuan = null;
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 218, 121);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblSatuanIsi = new JLabel("Satuan Isi");
		lblSatuanIsi.setBounds(10, 11, 57, 16);
		frame.getContentPane().add(lblSatuanIsi);
		
		lblIsi = new JLabel("Isi");
		lblIsi.setBounds(10, 36, 46, 16);
		frame.getContentPane().add(lblIsi);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0)
					lblSatuan.setText(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setBounds(77, 6, 115, 26);
		frame.getContentPane().add(comboBox);
		
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
		txtIsi.setBounds(77, 31, 59, 26);
		frame.getContentPane().add(txtIsi);
		txtIsi.setColumns(10);
		
		lblSatuan = new JLabel("");
		lblSatuan.setBounds(135, 36, 57, 16);
		frame.getContentPane().add(lblSatuan);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Simpan(id, satuan);
					JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
					FStokBarangNew.window.frame.setEnabled(true);
					window.frame.dispose();
				}
			}
		});
		btnSimpan.setBounds(103, 61, 89, 26);
		frame.getContentPane().add(btnSimpan);
	}

	private void TampilSatuan() {
		comboBox.removeAllItems();
		db.con();
		try {
			String query = "select * from tb_satuan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				comboBox.addItem(rs.getString("satuan"));
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil satuan " + e);
		}
	}
	
	private void AmbilSatuanIsi(String id, String satuan) {
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comboBox.setSelectedItem(rs.getString("satuan_isi"));
				txtIsi.setText(rs.getString("isi"));
			}
		} catch(Exception e) {
			System.out.println("error ambil satuan isi " + e);
		}
	}
	
	private void Simpan(String id, String satuan) {
		db.con();
		try {
			String query = "update tb_detail_barang set satuan_isi = ?, isi = ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, comboBox.getSelectedItem().toString());
			ps.setString(2, txtIsi.getText());
			ps.setString(3, id);
			ps.setString(4, satuan);
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan: " + e);
		}
	}
}
