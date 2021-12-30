package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class FSatuan {

	JFrame frame;
	static FSatuan window;
	private JTextField txtId;
	private JTextField txtSatuan;
	private JTable table;
	private Database db;
	private JButton btnSimpan;
	private JButton btnTambah;
	private JButton btnEdit;
	private JButton btnBatal;
	private int konfirm = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FSatuan();
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
	public FSatuan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Satuan Barang");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				TampilData();
				txtId.setEditable(false);
				txtSatuan.setEditable(false);
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
		frame.setBounds(100, 100, 261, 401);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 11, 46, 16);
		frame.getContentPane().add(lblId);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(10, 36, 46, 16);
		frame.getContentPane().add(lblSatuan);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(66, 6, 178, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtSatuan = new JTextField();
		txtSatuan.setText("satuan");
		txtSatuan.setBounds(66, 31, 178, 26);
		frame.getContentPane().add(txtSatuan);
		txtSatuan.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 122, 234, 210);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setGridColor(Color.WHITE);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					TampilDetailData(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
			}
		});
		scrollPane.setViewportView(table);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.setIcon(new ImageIcon(FSatuan.class.getResource("/img/Simpan.png")));
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (txtId.getText().equals("") || txtSatuan.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Lengkapi data!");
						txtId.requestFocus();
					} else {
						Simpan();
						TampilData();
						DataBaru();
						txtId.setEditable(false);
						txtSatuan.setEditable(false);
						btnTambah.setEnabled(true);
						btnEdit.setEnabled(true);
						btnSimpan.setEnabled(false);
						btnBatal.setEnabled(false);
					}
				}
			}
		});
		btnSimpan.setBounds(10, 88, 110, 26);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.setIcon(new ImageIcon(FSatuan.class.getResource("/img/Hapus.png")));
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
		btnHapus.setBounds(156, 338, 89, 26);
		frame.getContentPane().add(btnHapus);
		
		btnTambah = new JButton("Tambah");
		btnTambah.setIcon(new ImageIcon(FSatuan.class.getResource("/img/add.png")));
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataBaru();
				txtId.setEditable(true);
				txtSatuan.setEditable(true);
				btnTambah.setEnabled(false);
				btnEdit.setEnabled(false);
				btnSimpan.setEnabled(true);
				btnBatal.setEnabled(true);
				txtId.requestFocus();
			}
		});
		btnTambah.setBounds(10, 61, 110, 26);
		frame.getContentPane().add(btnTambah);
		
		btnBatal = new JButton("Batal");
		btnBatal.setIcon(new ImageIcon(FSatuan.class.getResource("/img/cancel.png")));
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setEditable(false);
				txtSatuan.setEditable(false);
				btnTambah.setEnabled(true);
				btnEdit.setEnabled(true);
				btnSimpan.setEnabled(false);
				btnBatal.setEnabled(false);
			}
		});
		btnBatal.setBounds(134, 88, 110, 26);
		frame.getContentPane().add(btnBatal);
		
		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon(FSatuan.class.getResource("/img/edit.png")));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setEditable(false);
				txtSatuan.setEditable(true);
				btnTambah.setEnabled(false);
				btnEdit.setEnabled(false);
				btnSimpan.setEnabled(true);
				btnBatal.setEnabled(true);
				txtSatuan.requestFocus();
			}
		});
		btnEdit.setBounds(134, 61, 110, 26);
		frame.getContentPane().add(btnEdit);
	}
	
	private void DataBaru() {
		txtId.setText("");
		txtSatuan.setText("");
	}
	
	private void TampilData() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("ID");
		model.addColumn("Satuan");
		db.con();
		try {
			String query = "select * from tb_satuan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("satuan")
				});
			}
			st.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(110);
		} catch(Exception e) {
			System.out.println("error tampil data " + e);
		}
	}
	
	private void TampilDetailData(String id) {
		db.con();
		try {
			String query = "select * from tb_satuan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtId.setText(rs.getString("id"));
				txtSatuan.setText(rs.getString("satuan"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil detail data " + e);
		}
	}
	
	private void Simpan() {
		db.con();
		try {
			String query = "insert into tb_satuan values(?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, txtSatuan.getText());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch(Exception e) {
			System.out.println("error " + e);
			Update();
		}
	}
	
	private void Update() {
		db.con();
		try {
			String query = "update tb_satuan set satuan = ? where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtSatuan.getText());
			ps.setString(2, txtId.getText());
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
			String query = "delete from tb_satuan where id = ?";
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
