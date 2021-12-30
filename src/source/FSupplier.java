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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FSupplier {

	JFrame frame;
	static FSupplier window; 
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtAlamat;
	private JTextField txtTelp;
	private JTable table;
	private Database db;
	private JButton btnTambah;
	private JButton btnEdit;
	private JButton btnSimpan;
	private JButton btnBatal;
	private int konfirm = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FSupplier();
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
	public FSupplier() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Data Supplier");
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
		frame.setBounds(100, 100, 318, 471);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIdSupplier = new JLabel("ID Supplier");
		lblIdSupplier.setBounds(10, 11, 70, 16);
		frame.getContentPane().add(lblIdSupplier);
		
		JLabel lblNamaSupplier = new JLabel("Nama Supplier");
		lblNamaSupplier.setBounds(10, 36, 70, 16);
		frame.getContentPane().add(lblNamaSupplier);
		
		JLabel lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(10, 61, 46, 16);
		frame.getContentPane().add(lblAlamat);
		
		JLabel lblTelp = new JLabel("Telp");
		lblTelp.setBounds(10, 86, 46, 16);
		frame.getContentPane().add(lblTelp);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(109, 6, 191, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setText("nama");
		txtNama.setBounds(109, 31, 191, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtAlamat = new JTextField();
		txtAlamat.setText("alama");
		txtAlamat.setBounds(109, 56, 191, 26);
		frame.getContentPane().add(txtAlamat);
		txtAlamat.setColumns(10);
		
		txtTelp = new JTextField();
		txtTelp.setText("telp");
		txtTelp.setBounds(109, 81, 191, 26);
		frame.getContentPane().add(txtTelp);
		txtTelp.setColumns(10);
		
		btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnTambah.setIcon(new ImageIcon(FSupplier.class.getResource("/img/TambahUser.png")));
		btnTambah.setBounds(10, 111, 130, 26);
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
		btnEdit.setIcon(new ImageIcon(FSupplier.class.getResource("/img/EditKaryawan.png")));
		btnEdit.setBounds(170, 111, 130, 26);
		frame.getContentPane().add(btnEdit);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (konfirm == 0) {
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
					if (txtId.getText().equals("") || 
							txtNama.getText().equals("") ||
							txtAlamat.getText().equals("") ||
							txtTelp.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Lengkapi data!");
						txtId.requestFocus();
					} else {
						Simpan();
						TampilData();
						DataBaru();
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
		btnSimpan.setIcon(new ImageIcon(FSupplier.class.getResource("/img/Simpan.png")));
		btnSimpan.setBounds(10, 141, 130, 26);
		frame.getContentPane().add(btnSimpan);
		
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
		btnBatal.setIcon(new ImageIcon(FSupplier.class.getResource("/img/cancel.png")));
		btnBatal.setBounds(170, 141, 130, 26);
		frame.getContentPane().add(btnBatal);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 177, 290, 226);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRow() >= 0) 
					TampilDetailData(table.getValueAt(table.getSelectedRow(), 0).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Hapus();
					TampilData();
					DataBaru();
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FSupplier.class.getResource("/img/HapusUser.png")));
		btnHapus.setBounds(170, 408, 130, 26);
		frame.getContentPane().add(btnHapus);
	}

	private void DataBaru() {
		txtAlamat.setText("");
		txtId.setText("");
		txtNama.setText("");
		txtTelp.setText("");
	}
	
	private void TampilData() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("ID Supplier");
		model.addColumn("Nama Supplier");
		model.addColumn("Alamat");
		model.addColumn("No. Telp");
		db.con();
		try {
			String query = "select * from tb_supplier";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
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
			table.getColumnModel().getColumn(1).setPreferredWidth(110);
			table.getColumnModel().getColumn(2).setPreferredWidth(140);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
		} catch(Exception e) {
			System.out.println("error tampil data " + e);
		}
	}
	
	private void TampilDetailData(String id) {
		db.con();
		try {
			String query = "select * from tb_supplier where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtAlamat.setText(rs.getString("alamat"));
				txtId.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
				txtTelp.setText(rs.getString("telp"));
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
			String query = "insert into tb_supplier values(?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, txtNama.getText());
			ps.setString(3, txtAlamat.getText());
			ps.setString(4, txtTelp.getText());
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
			String query = "update tb_supplier set nama = ?, "
					+ "alamat = ?, telp = ? where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNama.getText());
			ps.setString(2, txtAlamat.getText());
			ps.setString(3, txtTelp.getText());
			ps.setString(4, txtId.getText());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil diubah");
		} catch(Exception e) {
			System.out.println("error update " + e);
		}
	}
	
	private void Hapus() {
		db.con();
		try {
			String query = "delete from tb_supplier where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
		} catch(Exception e) {
			System.out.println("error hapus " + e);
		}
	}
}
