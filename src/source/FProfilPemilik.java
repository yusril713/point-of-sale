package source;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FProfilPemilik {

	JFrame frame;
	static FProfilPemilik window;
	private JTextField txtNpwp;
	private JTextField txtNamaToko;
	private JTextField txtAlamat;
	private JTextField txtNoTelp;
	private JButton btnSimpan;
	private Database db;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FProfilPemilik();
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
	public FProfilPemilik() {
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
				TampilData();
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 385, 179);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNamaToko = new JLabel("Nama Toko");
		lblNamaToko.setBounds(10, 36, 61, 14);
		frame.getContentPane().add(lblNamaToko);
		
		JLabel lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(10, 61, 46, 14);
		frame.getContentPane().add(lblAlamat);
		
		JLabel lblTelp = new JLabel("No. Telpon");
		lblTelp.setBounds(10, 86, 61, 14);
		frame.getContentPane().add(lblTelp);
		
		JLabel lblNpwp = new JLabel("NPWP");
		lblNpwp.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(lblNpwp);
		
		txtNpwp = new JTextField();
		txtNpwp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtNamaToko.requestFocus();
					txtNamaToko.selectAll();
				}
			}
		});
		txtNpwp.setFocusTraversalKeysEnabled(false);
		txtNpwp.setEditable(false);
		txtNpwp.setBorder(null);
		txtNpwp.setBackground(SystemColor.control);
		txtNpwp.setText("npwp");
		txtNpwp.setBounds(111, 8, 241, 20);
		frame.getContentPane().add(txtNpwp);
		txtNpwp.setColumns(10);
		
		txtNamaToko = new JTextField();
		txtNamaToko.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtAlamat.requestFocus();
					txtAlamat.selectAll();
				}
			}
		});
		txtNamaToko.setFocusTraversalKeysEnabled(false);
		txtNamaToko.setEditable(false);
		txtNamaToko.setBorder(null);
		txtNamaToko.setBackground(SystemColor.control);
		txtNamaToko.setText("nama toko");
		txtNamaToko.setBounds(111, 33, 241, 20);
		frame.getContentPane().add(txtNamaToko);
		txtNamaToko.setColumns(10);
		
		txtAlamat = new JTextField();
		txtAlamat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtNoTelp.requestFocus();
					txtNoTelp.selectAll();
				}
			}
		});
		txtAlamat.setFocusTraversalKeysEnabled(false);
		txtAlamat.setEditable(false);
		txtAlamat.setBorder(null);
		txtAlamat.setBackground(SystemColor.control);
		txtAlamat.setText("alamat");
		txtAlamat.setBounds(111, 58, 241, 20);
		frame.getContentPane().add(txtAlamat);
		txtAlamat.setColumns(10);
		
		txtNoTelp = new JTextField();
		txtNoTelp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Simpan();
					txtAlamat.setEditable(false);
					txtNamaToko.setEditable(false);
					txtNoTelp.setEditable(false);
					txtNpwp.setEditable(false);
				} else if (e.getKeyCode() == KeyEvent.VK_TAB) {
					btnSimpan.requestFocus();
				}
			}
		});
		txtNoTelp.setFocusTraversalKeysEnabled(false);
		txtNoTelp.setEditable(false);
		txtNoTelp.setBorder(null);
		txtNoTelp.setBackground(SystemColor.control);
		txtNoTelp.setText("no telp");
		txtNoTelp.setBounds(111, 83, 241, 20);
		frame.getContentPane().add(txtNoTelp);
		txtNoTelp.setColumns(10);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAlamat.setEditable(true);
				txtNamaToko.setEditable(true);
				txtNoTelp.setEditable(true);
				txtNpwp.setEditable(true);
				txtNpwp.selectAll();
				txtNpwp.requestFocus();
			}
		});
		btnEdit.setBounds(173, 111, 89, 26);
		frame.getContentPane().add(btnEdit);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Simpan();
				txtAlamat.setEditable(false);
				txtNamaToko.setEditable(false);
				txtNoTelp.setEditable(false);
				txtNpwp.setEditable(false);
			}
		});
		btnSimpan.setBounds(263, 111, 89, 26);
		frame.getContentPane().add(btnSimpan);
	}

	private void TampilData() {
		db.con();
		try {
			String query = "select * from tb_properties";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				txtNpwp.setText(rs.getString("npwp"));
				txtNamaToko.setText(rs.getString("nama_toko"));
				txtAlamat.setText(rs.getString("alamat"));
				txtNoTelp.setText(rs.getString("telp"));
			}
			
			st.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("error tampil " + e);
		}
	}
	
	private void Simpan() {
		db.con();
		try {
			String query = "update tb_properties set nama_toko = ?, "
					+ "alamat = ?, telp = ?, npwp = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNamaToko.getText());
			ps.setString(2, txtAlamat.getText());
			ps.setString(3, txtNoTelp.getText());
			ps.setString(4, txtNpwp.getText());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Data berhasil diubah...");
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan " + e);
		}
	}
}
