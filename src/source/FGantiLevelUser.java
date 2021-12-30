package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FGantiLevelUser {

	JFrame frame;
	static FGantiLevelUser window;
	private JTextField txtUsername;
	private JComboBox<String> comboBox;
	private Database db;
	static String id;
	private List<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FGantiLevelUser();
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
	public FGantiLevelUser() {
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
				list = new ArrayList<>();
				list = AmbilUser(id);
				txtUsername.setText(null);
				txtUsername.setEditable(false);
				comboBox.setSelectedIndex(0);
				txtUsername.setText(list.get(0));
				if (list.get(1).equals("admin"))
					comboBox.setSelectedIndex(1);
				else if (list.get(1).equals("kasir_utama"))
					comboBox.setSelectedIndex(2);
				else if (list.get(1).equals("kasir"))
					comboBox.setSelectedIndex(3);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FUser.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 345, 158);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(20, 20, 78, 16);
		frame.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setText("username");
		txtUsername.setBounds(110, 15, 206, 26);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblLevel = new JLabel("Level");
		lblLevel.setBounds(20, 48, 52, 16);
		frame.getContentPane().add(lblLevel);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Pilih Level", "Admin", "Kasir Utama", "Kasir"}));
		comboBox.setBounds(110, 43, 206, 26);
		frame.getContentPane().add(comboBox);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (comboBox.getSelectedIndex() <= 0) {
						JOptionPane.showMessageDialog(null, "Pilih level user terlebih dahulu");
					} else {
						Simpan();
						FUser.window.frame.setEnabled(true);
						window.frame.dispose();
					}
				}
			}
		});
		btnSimpan.setBounds(110, 81, 97, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FUser.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(219, 81, 97, 26);
		frame.getContentPane().add(btnBatal);
	}
	
	private List<String> AmbilUser(String id) {
		List<String> listUser = new ArrayList<>();
		String username = null;
		db.con();
		try {
			String query = "select username from tb_karyawan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				username = rs.getString("username");
			}
			
			query = "select * from tb_user where user = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				listUser.add(rs.getString("user"));
				listUser.add(rs.getString("level"));
			}
			
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listUser;
	}
	
	private void Simpan() {
		db.con();
		String level = null;
		if (comboBox.getSelectedIndex() == 1) 
			level = "admin";
		else if (comboBox.getSelectedIndex() == 2)
			level = "kasir_utama";
		else if (comboBox.getSelectedIndex() == 3) 
			level = "kasir";
		try {
			String query = "update tb_user set level = ? where user = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, level);
			ps.setString(2, txtUsername.getText());
			ps.execute();
			ps.close();
			db.con.close();
			
			JOptionPane.showMessageDialog(null, "Data berhasil diubah");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
