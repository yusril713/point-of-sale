package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatIntelliJLaf;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FLogin {

	JFrame frame;
	static FLogin window;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private Database db;
	private int konfirm = 0;
	private boolean reportInitial = false;
	private String user = null;
	private String level = null;
	private String nama = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(FlatIntelliJLaf.class.getName());
		} catch(Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
	/*	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FLogin();
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
	public FLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Login");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				DataBaru();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menutup aplikasi?", "Konfirmasi", 0);
				if (konfirm == 0)
					System.exit(0);
			}
		});
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FLogin.class.getResource("/img/Login.png")));
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 423, 204);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "LOGIN", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(17, 22, 367, 119);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBorder(null);
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtUsername.setBounds(95, 17, 260, 28);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		pwdPassword.setBorder(null);
		pwdPassword.setEchoChar('φ');
		pwdPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
					Login();
			}
		});
		pwdPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pwdPassword.setBounds(95, 50, 260, 28);
		panel.add(pwdPassword);
		
		lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblUsername.setBounds(6, 24, 80, 14);
		panel.add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBackground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblPassword.setBounds(6, 57, 80, 14);
		panel.add(lblPassword);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menutup aplikasi?", "Konfirmasi", 0);
				if (konfirm == 0)
					System.exit(0);
			}
		});
		btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnBatal.setIcon(new ImageIcon(FLogin.class.getResource("/img/Batal.png")));
		btnBatal.setBorder(null);
		btnBatal.setBounds(266, 84, 89, 25);
		panel.add(btnBatal);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login();
			}
		});
		btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnLogin.setIcon(new ImageIcon(FLogin.class.getResource("/img/Log_in.png")));
		btnLogin.setBorder(null);
		btnLogin.setBounds(170, 84, 89, 25);
		panel.add(btnLogin);
	}
	
	private void DataBaru() {
		txtUsername.setText("");
		pwdPassword.setText("");
		txtUsername.requestFocus();
	}
	
	private void InisialisasiReport() {
		reportInitial = true;
		JFrame jf = null;
		db = new Database();
		db.con();
		try {
			String reportName = ".\\laporan\\Init.jasper";
			JasperPrint jp = JasperFillManager.fillReport(reportName, null, db.con);
			JRViewer jv= new JRViewer(jp);
			jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | 0x6);
			jf.setDefaultCloseOperation(2);
			db.con.close();
		} catch(Exception e) {
			System.out.println("error inisialisasi: " + e);
		}
		jf.dispose();
	}
	
	@SuppressWarnings("deprecation")
	private void Login() {
		db = new Database();
		db.con();
		int i = 0;
		try {
			String query = "select * from tb_user where user = ? and pass = password(?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtUsername.getText());
			ps.setString(2, pwdPassword.getText());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				i++;
				user = rs.getString("user");
				level = rs.getString("level");
				FMain.BIT_PER_SECOND = Integer.parseInt(Database.BITS_PER_SECOND);
				FMain.PORT = Database.PORT;
				FMain.DATA_BITS = Integer.parseInt(Database.DATA_BITS);
				FMain.PARITY = Integer.parseInt(Database.PARITY);
				FMain.STOP_BITS = Integer.parseInt(Database.STOP_BITS);
			}
			
			ps.close();
			rs.close();
			db.con.close();
			
			AmbilNama(txtUsername.getText());
			if (i == 0) {
				JOptionPane.showMessageDialog(null, "Username dan password tidak valid");
				DataBaru();
			} else {
				FMain.main(null);
				FMain.user = user;
				FMain.level = level;
				FMain.nama = nama;
				user = level = null;
				if (!reportInitial)
					InisialisasiReport();
				window.frame.dispose();
			}
		} catch(Exception e) {
			System.out.println("error login");
		}
	}
	
	private void AmbilNama(String user) {
		db = new Database();
		db.con();
		try {
			String query = "select nama from tb_karyawan where username = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				nama = rs.getString("nama");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil nama: " + e);
		}
	}	
}
