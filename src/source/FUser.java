package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class FUser {

	JFrame frame;
	static FUser window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtBagian;
	private JTextField txtAlamat;
	private JTextField txtUsername;
	private JTextField txtTelp;
	private JTextField txtLevel;
	private JTable table;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FUser();
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
	public FUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Data User");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				TampilData();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 300, 453);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIdKaryawan = new JLabel("ID Karyawan");
		lblIdKaryawan.setBounds(10, 11, 68, 14);
		frame.getContentPane().add(lblIdKaryawan);
		
		JLabel lblNama = new JLabel("Nama");
		lblNama.setBounds(10, 36, 46, 14);
		frame.getContentPane().add(lblNama);
		
		JLabel lblBagian = new JLabel("Bagian");
		lblBagian.setBounds(10, 61, 46, 14);
		frame.getContentPane().add(lblBagian);
		
		JLabel lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(10, 86, 46, 14);
		frame.getContentPane().add(lblAlamat);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 111, 58, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblTelp = new JLabel("Telp");
		lblTelp.setBounds(10, 136, 46, 14);
		frame.getContentPane().add(lblTelp);
		
		JLabel lblLevelUser = new JLabel("Level User");
		lblLevelUser.setBounds(10, 161, 58, 14);
		frame.getContentPane().add(lblLevelUser);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(110, 5, 174, 25);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setText("nama");
		txtNama.setBounds(110, 30, 174, 25);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtBagian = new JTextField();
		txtBagian.setText("bagian");
		txtBagian.setBounds(110, 55, 174, 25);
		frame.getContentPane().add(txtBagian);
		txtBagian.setColumns(10);
		
		txtAlamat = new JTextField();
		txtAlamat.setText("alamat");
		txtAlamat.setBounds(110, 80, 174, 25);
		frame.getContentPane().add(txtAlamat);
		txtAlamat.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setText("username");
		txtUsername.setBounds(110, 105, 174, 25);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtTelp = new JTextField();
		txtTelp.setText("telp");
		txtTelp.setBounds(110, 130, 174, 25);
		frame.getContentPane().add(txtTelp);
		txtTelp.setColumns(10);
		
		txtLevel = new JTextField();
		txtLevel.setText("level");
		txtLevel.setBounds(110, 155, 174, 25);
		frame.getContentPane().add(txtLevel);
		txtLevel.setColumns(10);
		
		JButton btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					if (txtUsername.getText().equals("-")) {
						FTambahUser.main(null);
						FTambahUser.nama = txtNama.getText();
						FTambahUser.id = txtId.getText();
						window.frame.setEnabled(false);
					} else 
						JOptionPane.showMessageDialog(null, "User sudah ada!");
				}
			}
		});
		btnTambah.setIcon(new ImageIcon(FUser.class.getResource("/img/TambahUser.png")));
		btnTambah.setBounds(10, 186, 120, 27);
		frame.getContentPane().add(btnTambah);
		
		JButton btnGantiPassword = new JButton("Ganti Password");
		btnGantiPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUsername.getText().equals("") || txtUsername.getText().equals("-"))
					JOptionPane.showMessageDialog(null, "Buat user terlebih dahulu!");
				else {
					FGantiPassword.main(null);
					FGantiPassword.username = txtUsername.getText();
					window.frame.setEnabled(false);
				}
			}
		});
		btnGantiPassword.setToolTipText("Ganti Password");
		btnGantiPassword.setIcon(new ImageIcon(FUser.class.getResource("/img/ubah_pass.png")));
		btnGantiPassword.setBounds(164, 186, 120, 27);
		frame.getContentPane().add(btnGantiPassword);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 225, 274, 154);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setGridColor(Color.WHITE);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					TampilDetailData(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
				if (arg0.getClickCount() == 2) {
					if (table.getSelectedRow() >= 0) {
						if (txtUsername.getText().equals("-")) {
							FTambahUser.main(null);
							FTambahUser.nama = txtNama.getText();
							FTambahUser.id = txtId.getText();
							window.frame.setEnabled(false);
						} else 
							JOptionPane.showMessageDialog(null, "User sudah ada!");
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data user tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					HapusUser();
					DataBaru();
					TampilData();
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FUser.class.getResource("/img/HapusUser.png")));
		btnHapus.setBounds(164, 390, 120, 27);
		frame.getContentPane().add(btnHapus);
		
		JButton btnGantiLevelUser = new JButton("Ganti Level User");
		btnGantiLevelUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FGantiLevelUser.main(null);
				FGantiLevelUser.id = txtId.getText();
				window.frame.setEnabled(false);
			}
		});
		btnGantiLevelUser.setToolTipText("Ganti Level User");
		btnGantiLevelUser.setBounds(10, 390, 120, 26);
		frame.getContentPane().add(btnGantiLevelUser);
	}
	
	private void DataBaru() {
		txtId.setText("");
		txtAlamat.setText("");
		txtBagian.setText("");
		txtLevel.setText("");
		txtNama.setText("");
		txtTelp.setText("");
		txtUsername.setText("");
		
		txtAlamat.setEditable(false);
		txtBagian.setEditable(false);
		txtId.setEditable(false);
		txtLevel.setEditable(false);
		txtNama.setEditable(false);
		txtTelp.setEditable(false);
		txtUsername.setEditable(false);
	}
	
	private void TampilData() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("ID Karyawan");
		model.addColumn("Nama Karyawan");
		model.addColumn("Bagian");
		db.con();
		try {
			String query = "select * from tb_karyawan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("bagian")
				});
			}
			st.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(70);
		} catch(Exception e) {
			System.out.println("error tampil data: " + e);
		}
	}
	
	private void TampilDetailData(String id) {
		db.con();
		try {
			String query = "select tb_karyawan.*, tb_user.* from tb_karyawan "
					+ "left join tb_user on tb_karyawan.username = tb_user.user "
					+ "where tb_karyawan.id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtAlamat.setText(rs.getString("alamat"));
				txtBagian.setText(rs.getString("bagian"));
				txtId.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
				txtTelp.setText(rs.getString("telp"));
				txtUsername.setText(rs.getString("username"));
				txtLevel.setText(rs.getString("level"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil detail data: " + e);
		} 
	}

	private void HapusUser() {
		db.con();
		try {
			String query = "delete from tb_user where user = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtUsername.getText());
			ps.execute();
			
			query = "update tb_karyawan set username = ? where id = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, "-");
			ps.setString(2, txtId.getText());
			ps.execute();
			
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error hapus user " + e);
		}
	}
}