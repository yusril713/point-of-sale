package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class FKaryawan {

	JFrame frame;
	static FKaryawan window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtAlamat;
	private JComboBox<String> comboBox;
	private JTextField txtTelp;
	private JTable table;
	private JButton btnTambah;
	private JButton btnEdit;
	private JButton btnSimpan;
	private JButton btnBatal;
	private JButton btnHapus;
	private Database db;
	private String username = null;
	private int konfirm = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FKaryawan();
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
	public FKaryawan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Data Karyawan");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				TampilData();
				txtId.setEditable(false);
				txtNama.setEditable(false);
				txtAlamat.setEditable(false);
				txtTelp.setEditable(false);
				btnTambah.setEnabled(true);
				btnEdit.setEnabled(true);
				btnSimpan.setEnabled(false);
				btnBatal.setEnabled(false);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 313, 492);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIdKaryawan = new JLabel("ID Karyawan");
		lblIdKaryawan.setBounds(10, 11, 62, 16);
		frame.getContentPane().add(lblIdKaryawan);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(102, 6, 185, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		JLabel lblNama = new JLabel("Nama");
		lblNama.setBounds(10, 36, 46, 16);
		frame.getContentPane().add(lblNama);
		
		txtNama = new JTextField();
		txtNama.setText("nama");
		txtNama.setBounds(102, 31, 185, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		JLabel lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(10, 61, 46, 16);
		frame.getContentPane().add(lblAlamat);
		
		txtAlamat = new JTextField();
		txtAlamat.setText("alamat");
		txtAlamat.setBounds(102, 56, 185, 26);
		frame.getContentPane().add(txtAlamat);
		txtAlamat.setColumns(10);
		
		JLabel lblBagian = new JLabel("Bagian");
		lblBagian.setBounds(10, 86, 46, 16);
		frame.getContentPane().add(lblBagian);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"-PILIH BAGIAN-", "Admin", "Kasir", "Supir"}));
		comboBox.setBounds(102, 81, 185, 26);
		frame.getContentPane().add(comboBox);
		
		JLabel lblTelp = new JLabel("Telp");
		lblTelp.setBounds(10, 111, 46, 16);
		frame.getContentPane().add(lblTelp);
		
		txtTelp = new JTextField();
		txtTelp.setText("telp");
		txtTelp.setBounds(102, 106, 185, 26);
		frame.getContentPane().add(txtTelp);
		txtTelp.setColumns(10);
		
		btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataBaru();
				txtId.setEditable(true);
				txtNama.setEditable(true);
				txtAlamat.setEditable(true);
				txtTelp.setEditable(true);
				btnTambah.setEnabled(false);
				btnEdit.setEnabled(false);
				btnSimpan.setEnabled(true);
				btnBatal.setEnabled(true);
				txtId.requestFocus();
			}
		});
		btnTambah.setIcon(new ImageIcon(FKaryawan.class.getResource("/img/TambahUser.png")));
		btnTambah.setBounds(10, 136, 125, 26);
		frame.getContentPane().add(btnTambah);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setEditable(false);
				txtNama.setEditable(true);
				txtAlamat.setEditable(true);
				txtTelp.setEditable(true);
				btnTambah.setEnabled(false);
				btnEdit.setEnabled(false);
				btnSimpan.setEnabled(true);
				btnBatal.setEnabled(true);
				txtNama.requestFocus();
			}
		});
		btnEdit.setIcon(new ImageIcon(FKaryawan.class.getResource("/img/EditKaryawan.png")));
		btnEdit.setBounds(162, 136, 125, 26);
		frame.getContentPane().add(btnEdit);
		
		btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setEditable(false);
				txtNama.setEditable(false);
				txtAlamat.setEditable(false);
				txtTelp.setEditable(false);
				btnTambah.setEnabled(true);
				btnEdit.setEnabled(true);
				btnSimpan.setEnabled(false);
				btnBatal.setEnabled(false);
			}
		});
		btnBatal.setIcon(new ImageIcon(FKaryawan.class.getResource("/img/cancel.png")));
		btnBatal.setBounds(162, 163, 125, 26);
		frame.getContentPane().add(btnBatal);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (txtAlamat.getText().equals("") || txtId.getText().equals("") 
							|| txtNama.getText().equals("") || txtTelp.getText().equals("") 
							|| comboBox.getSelectedIndex() <= 0) {
						JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
						txtId.requestFocus();
					} else {
						Simpan();
						DataBaru();
						TampilData();
						username = null;
						txtId.setEditable(false);
						txtNama.setEditable(false);
						txtAlamat.setEditable(false);
						txtTelp.setEditable(false);
						btnTambah.setEnabled(true);
						btnEdit.setEnabled(true);
						btnSimpan.setEnabled(false);
						btnBatal.setEnabled(false);
					}
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon(FKaryawan.class.getResource("/img/Simpan.png")));
		btnSimpan.setBounds(10, 163, 125, 26);
		frame.getContentPane().add(btnSimpan);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 197, 277, 216);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setGridColor(Color.WHITE);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0)
					TampilDetailData(table.getValueAt(table.getSelectedRow(), 0).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Hapus(txtId.getText(), username);
					DataBaru();
					TampilData();
					username = null;
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FKaryawan.class.getResource("/img/HapusUser.png")));
		btnHapus.setBounds(162, 419, 125, 26);
		frame.getContentPane().add(btnHapus);
	}
	
	private void DataBaru() {
		txtAlamat.setText("");
		txtId.setText("");
		txtNama.setText("");
		txtTelp.setText("");
		comboBox.setSelectedIndex(0);
	}
	
	private void TampilData() {
		db.con();
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override 
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("ID");
		model.addColumn("Nama Karyawan");
		model.addColumn("Bagian");
		model.addColumn("Alamat");
		model.addColumn("No. Telp");
		try {
			String query = "select * from tb_karyawan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("bagian"),
						rs.getString("alamat"),
						rs.getString("telp")
				});
			}
			st.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(150);
			table.getColumnModel().getColumn(4).setPreferredWidth(80);
		} catch(Exception e) {
			System.out.println("error tampil data: " + e);
		} 
	}
	
	private void TampilDetailData(String id) {
		db.con();
		try {
			String query = "select * from tb_karyawan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtAlamat.setText(rs.getString("alamat"));
				txtId.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
				txtTelp.setText(rs.getString("telp"));
				comboBox.setSelectedItem(rs.getString("bagian"));
				username = rs.getString("username");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil detail data: " + e);
		}
	}
	
	private void Simpan() {
		db.con();
		try {
			String query = "insert into tb_karyawan values(?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, "-");
			ps.setString(3, txtNama.getText());
			ps.setString(4, comboBox.getSelectedItem().toString());
			ps.setString(5, txtAlamat.getText());
			ps.setString(6, txtTelp.getText());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch(Exception e) {
			Update();
		}
	}
	
	private void Update() {
		db.con();
		try {
			String query = "update tb_karyawan set nama = ?, bagian = ?, alamat = ?, "
					+ "telp = ? where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNama.getText());
			ps.setString(2, comboBox.getSelectedItem().toString());
			ps.setString(3, txtAlamat.getText());
			ps.setString(4, txtTelp.getText());
			ps.setString(5, txtId.getText());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil diubah");
		} catch(Exception e) {
			System.out.println("error update: " + e);
		}
	}
	
	private void Hapus(String id, String username) {
		db.con();
		try {
			String query = "delete from tb_karyawan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.execute();
			query = "delete from tb_user where user = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, username);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil data berhasil dihapus");
		} catch(Exception e) {
			System.out.println("error hapus: " + e);
		}
	}
}
