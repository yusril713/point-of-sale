package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FTambahUser {

	JFrame frame;
	static FTambahUser window;
	static String nama = null;
	private JTextField txtNama;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private Database db;
	static String id = null;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FTambahUser();
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
	public FTambahUser() {
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
				db = new Database();
				DataBaru();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FUser.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 324, 181);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNama = new JLabel("Nama");
		lblNama.setBounds(10, 11, 46, 16);
		frame.getContentPane().add(lblNama);
		
		JLabel lblBagian = new JLabel("Level User");
		lblBagian.setBounds(10, 36, 61, 16);
		frame.getContentPane().add(lblBagian);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 61, 61, 16);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 86, 61, 16);
		frame.getContentPane().add(lblPassword);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setText("nama");
		txtNama.setBounds(105, 6, 188, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(105, 31, 188, 26);
		frame.getContentPane().add(comboBox);
		
		txtUsername = new JTextField();
		txtUsername.setText("username");
		txtUsername.setBounds(105, 56, 188, 26);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("password");
		pwdPassword.setBounds(105, 81, 188, 26);
		frame.getContentPane().add(pwdPassword);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (txtNama.getText().equals("") || txtUsername.getText().equals("") 
							|| comboBox.getSelectedIndex() == 0 || pwdPassword.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
						txtUsername.requestFocus();
					} else {
						Simpan();
						JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
						FUser.window.frame.setEnabled(true);
						window.frame.dispose(); 
					}
				}
			}
		});
		btnSimpan.setBounds(105, 109, 89, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FUser.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(204, 109, 89, 26);
		frame.getContentPane().add(btnBatal);
	}
	
	private void DataBaru() {
		txtNama.setText(nama);
		txtUsername.setText("");
		pwdPassword.setText("");
		comboBox.removeAllItems();
		comboBox.addItem("-PILIH-");
		comboBox.addItem("Admin");
		comboBox.addItem("Kasir Utama");
		comboBox.addItem("Kasir");
	}
	
	@SuppressWarnings("deprecation")
	private void Simpan() {
		String level = null;
		switch (comboBox.getSelectedIndex()) {
		case 1:
			level = "admin";
			break;
		case 2:
			level = "kasir_utama";
			break;
		case 3:
			level = "kasir";
			break;
		default:
			break;
		}
		db.con();
		try {
			String query = "insert into tb_user values(?,password(?),?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtUsername.getText());
			ps.setString(2, pwdPassword.getText());
			ps.setString(3, level);
			ps.execute();
			
			query = "update tb_karyawan set username = ? where id = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, txtUsername.getText());
			ps.setString(2, id);
			ps.execute();
			
			ps.close();
			db.con.close();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Username telah tersedia, "
					+ "\nSilakan ganti username anda!");
			System.out.println("error simpan: " + e);
		}
	}
}
