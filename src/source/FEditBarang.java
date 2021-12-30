package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

import javax.swing.JComboBox;

public class FEditBarang {

	JFrame frame;
	private JTextField txtKode;
	private JTextField txtNama;
	static FEditBarang window;
	static String id;
	static String nama;
	static String harga;
	static String satuan;
	static String supplier;
	private JTextField textField;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FEditBarang();
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
	public FEditBarang() {
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
				txtKode.setText(id);
				txtNama.setText(nama);
				textField.setText(harga);
				txtKode.requestFocus();
				AmbilSupplier();
				
				if (!supplier.equals("")) {
					comboBox.setSelectedItem(supplier);
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 326, 189);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setBounds(10, 11, 88, 16);
		frame.getContentPane().add(lblKodeBarang);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(10, 36, 88, 16);
		frame.getContentPane().add(lblNamaBarang);
		
		txtKode = new JTextField();
		txtKode.setText("kode");
		txtKode.setBounds(108, 6, 188, 26);
		frame.getContentPane().add(txtKode);
		txtKode.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setText("nama");
		txtNama.setBounds(108, 31, 188, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0; 
				konfirm = JOptionPane.showConfirmDialog(null, "Yaking ingin mengubah data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (comboBox.getSelectedIndex() > 0) {
					Simpan(id);
					FStokBarangNew.window.frame.setEnabled(true);
					FStokBarangNew.TampilBarang("");
					FStokBarangNew.lblModal.setText("Modal: Rp. " + FMain.FormatAngka(FStokBarangNew.TampilModal()));
					FStokBarangNew.txtKode.setText(txtKode.getText());
					FStokBarangNew.txtNama.setText(txtNama.getText());
					FStokBarangNew.txtHargaBeli.setText(textField.getText());
					FStokBarangNew.txtSupplier.setText(comboBox.getSelectedItem().toString());
					window.frame.dispose();}
					else {
						JOptionPane.showMessageDialog(null, "Pilih supplier terlebih dahulu");
					}
				}
			}
		});
		btnSimpan.setBounds(207, 118, 89, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(106, 118, 89, 26);
		frame.getContentPane().add(btnBatal);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
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
						textField.setText(FMain.FormatAngka(Integer.valueOf(textField.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!textField.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	
						textField.setText("");
					}
				}
			}
		});
		textField.setText("nama");
		textField.setColumns(10);
		textField.setBounds(108, 60, 188, 26);
		frame.getContentPane().add(textField);
		
		JLabel lblNamaBarang_1 = new JLabel("Harga Beli");
		lblNamaBarang_1.setBounds(10, 65, 88, 16);
		frame.getContentPane().add(lblNamaBarang_1);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setBounds(10, 93, 52, 16);
		frame.getContentPane().add(lblSupplier);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(108, 88, 188, 26);
		frame.getContentPane().add(comboBox);
	}
	
	private void AmbilSupplier() {
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_supplier order by nama asc";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			comboBox.removeAllItems();
			comboBox.addItem("Pilih supplier");
			while(rs.next()) {
				comboBox.addItem(rs.getString("id") + " - " + rs.getString("nama"));
			}
			st.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Simpan(String id) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_barang set id = ?, nama = ?, harga_beli = ?, id_supplier = ? where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKode.getText());
			ps.setString(2, txtNama.getText());
			ps.setString(3, textField.getText().replace(",", ""));
			ps.setString(4, AmbilID(comboBox.getSelectedItem().toString()));
			ps.setString(5, id);
			
			ps.execute();
			
			query = "update tb_detail_barang set id_barang = ?, harga_beli = ? where id_barang = ? and satuan = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, txtKode.getText());
			ps.setString(2, textField.getText().replace(",", ""));
			ps.setString(3, id);
			ps.setString(4, satuan);
			ps.execute();
			ps.close();
			db.con.close();
			
			JOptionPane.showMessageDialog(null, "Data berhasil diubah");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String AmbilID(String teks) {
		return teks.substring(0, teks.indexOf(" "));
	}
}
