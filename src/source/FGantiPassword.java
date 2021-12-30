package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FGantiPassword {

	JFrame frame;
	static FGantiPassword window;
	static String username = null;
	private JPasswordField pwdPassword;
	private JPasswordField pwdKonfirmasiPassword;
	private Database db;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FGantiPassword();
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
	public FGantiPassword() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Ganti Password");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				pwdPassword.setText("");
				pwdKonfirmasiPassword.setText("");
				pwdPassword.requestFocus();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FUser.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 409, 118);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPasswordBaru = new JLabel("Password Baru");
		lblPasswordBaru.setBounds(10, 11, 139, 16);
		frame.getContentPane().add(lblPasswordBaru);
		
		JLabel lblKonfirmasiPasswordBaru = new JLabel("Konfirmasi Password Baru");
		lblKonfirmasiPasswordBaru.setBounds(10, 36, 139, 16);
		frame.getContentPane().add(lblKonfirmasiPasswordBaru);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("password");
		pwdPassword.setBounds(170, 6, 223, 26);
		frame.getContentPane().add(pwdPassword);
		
		pwdKonfirmasiPassword = new JPasswordField();
		pwdKonfirmasiPassword.setText("konfirmasi password");
		pwdKonfirmasiPassword.setBounds(170, 31, 223, 26);
		frame.getContentPane().add(pwdKonfirmasiPassword);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				int konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengubah password?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (pwdKonfirmasiPassword.getText().equals("") ||
							pwdPassword.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!");
						pwdPassword.requestFocus();
					} else {
						if (pwdKonfirmasiPassword.getText().equals(pwdPassword.getText())) {
							UbahPassword(username);
							JOptionPane.showMessageDialog(null, "Password berhasil diubah");
							FUser.window.frame.setEnabled(true);
							window.frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Konfirmasi password baru tidak valid!");
							pwdPassword.requestFocus();
						}
					}
				}
			}
		});
		btnSimpan.setBounds(170, 58, 105, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBatal.setBounds(288, 58, 105, 26);
		frame.getContentPane().add(btnBatal);
	}

	@SuppressWarnings("deprecation")
	private void UbahPassword(String username) {
		db.con();
		try {
			String query ="update tb_user set pass = password(?) where user = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, pwdPassword.getText());
			ps.setString(2, username);
			ps.execute();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("error ubah password: " + e);
		}
	}
}
