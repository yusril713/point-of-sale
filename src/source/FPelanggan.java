package source;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class FPelanggan {

	JFrame frame;
	static FPelanggan window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtToko;
	private JTextField txtAlamat;
	private JTextField txtTelp;
	private JButton btnTambah;
	private JButton btnSimpan;
	private JButton btnEdit;
	private JButton btnBatal;
	private JTable table;
	private JTextField txtCari;
	private Database db;
	private int konfirm = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FPelanggan();
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
	public FPelanggan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Data Pelanggan");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				TampilData(txtCari.getText());
				txtId.setEditable(false);
				txtNama.setEditable(false);
				txtToko.setEditable(false);
				txtAlamat.setEditable(false);
				txtTelp.setEditable(false);
				btnEdit.setEnabled(true);
				btnTambah.setEnabled(true);
				btnSimpan.setEnabled(false);
				btnBatal.setEnabled(false);
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 370, 515);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIdPelanggan = new JLabel("ID Pelanggan");
		lblIdPelanggan.setBounds(10, 11, 69, 16);
		frame.getContentPane().add(lblIdPelanggan);
		
		JLabel lblNamaPelanggan = new JLabel("Nama Pelanggan");
		lblNamaPelanggan.setBounds(10, 36, 80, 16);
		frame.getContentPane().add(lblNamaPelanggan);
		
		JLabel lblToko = new JLabel("Toko");
		lblToko.setBounds(10, 61, 46, 16);
		frame.getContentPane().add(lblToko);
		
		JLabel lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(10, 86, 46, 16);
		frame.getContentPane().add(lblAlamat);
		
		JLabel lblTelp = new JLabel("Telp");
		lblTelp.setBounds(10, 111, 46, 16);
		frame.getContentPane().add(lblTelp);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(119, 6, 234, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setText("nama");
		txtNama.setBounds(119, 31, 234, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtToko = new JTextField();
		txtToko.setText("toko");
		txtToko.setBounds(119, 56, 234, 26);
		frame.getContentPane().add(txtToko);
		txtToko.setColumns(10);
		
		txtAlamat = new JTextField();
		txtAlamat.setText("alamat");
		txtAlamat.setBounds(119, 81, 234, 26);
		frame.getContentPane().add(txtAlamat);
		txtAlamat.setColumns(10);
		
		txtTelp = new JTextField();
		txtTelp.setText("telp");
		txtTelp.setBounds(119, 106, 234, 26);
		frame.getContentPane().add(txtTelp);
		txtTelp.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Cari Data Pelanggan", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 195, 344, 280);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 324, 198);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0)
					TampilDetailData(table.getValueAt(table.getSelectedRow(), 0).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					Hapus(txtId.getText());
					DataBaru();
					TampilData("");
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FPelanggan.class.getResource("/img/Hapus.png")));
		btnHapus.setBounds(236, 246, 98, 26);
		panel.add(btnHapus);
		
		txtCari = new JTextField();
		txtCari.setText("");
		PromptSupport.setPrompt("Masukkan kode pelanggan atau nama pelanggan", txtCari);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIDE_PROMPT, txtCari);
        PromptSupport.setFontStyle(Font.ITALIC, txtCari);
		txtCari.setBounds(10, 16, 324, 26);
		txtCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				TampilData(txtCari.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				TampilData(txtCari.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				TampilData(txtCari.getText());
			}});
		panel.add(txtCari);
		txtCari.setColumns(10);
		
		btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtId.setEditable(true);
				txtNama.setEditable(true);
				txtToko.setEditable(true);
				txtAlamat.setEditable(true);
				txtTelp.setEditable(true);
				btnEdit.setEnabled(false);
				btnTambah.setEnabled(false);
				btnSimpan.setEnabled(true);
				btnBatal.setEnabled(true);
				DataBaru();
				txtId.requestFocus();
			}
		});
		btnTambah.setIcon(new ImageIcon(FPelanggan.class.getResource("/img/add.png")));
		btnTambah.setBounds(10, 136, 150, 26);
		frame.getContentPane().add(btnTambah);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setEditable(false);
				txtNama.setEditable(true);
				txtToko.setEditable(true);
				txtAlamat.setEditable(true);
				txtTelp.setEditable(true);
				btnEdit.setEnabled(false);
				btnTambah.setEnabled(false);
				btnSimpan.setEnabled(true);
				btnBatal.setEnabled(true);
				txtNama.requestFocus();
			}
		});
		btnEdit.setIcon(new ImageIcon(FPelanggan.class.getResource("/img/edit.png")));
		btnEdit.setBounds(205, 136, 149, 26);
		frame.getContentPane().add(btnEdit);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (txtAlamat.getText().equals("") || 
							txtId.getText().equals("") ||
							txtNama.getText().equals("") ||
							txtTelp.getText().equals("") ||
							txtToko.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!");
						txtId.requestFocus();
					} else {
						Simpan();
						DataBaru();
						TampilData("");
						txtId.setEditable(false);
						txtNama.setEditable(false);
						txtToko.setEditable(false);
						txtAlamat.setEditable(false);
						txtTelp.setEditable(false);
						btnEdit.setEnabled(true);
						btnTambah.setEnabled(true);
						btnSimpan.setEnabled(false);
						btnBatal.setEnabled(false);
					}
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon(FPelanggan.class.getResource("/img/Simpan.png")));
		btnSimpan.setBounds(10, 164, 150, 26);
		frame.getContentPane().add(btnSimpan);
		
		btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtId.setEditable(false);
				txtNama.setEditable(false);
				txtToko.setEditable(false);
				txtAlamat.setEditable(false);
				txtTelp.setEditable(false);
				btnEdit.setEnabled(true);
				btnTambah.setEnabled(true);
				btnSimpan.setEnabled(false);
				btnBatal.setEnabled(false);
			}
		});
		btnBatal.setIcon(new ImageIcon(FPelanggan.class.getResource("/img/cancel.png")));
		btnBatal.setBounds(204, 164, 150, 26);
		frame.getContentPane().add(btnBatal);
	}
	
	private void DataBaru() {
		txtAlamat.setText("");
		txtCari.setText("");
		txtId.setText("");
		txtNama.setText("");
		txtTelp.setText("");
		txtToko.setText(null);
	}
	
	private void TampilData(String key) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("ID Pelanggan");
		model.addColumn("Nama Pelanggan");
		model.addColumn("Toko");
		db.con();
		try {
			String query = "select * from tb_pelanggan where (id like ? or nama like ?) and not(id = 'UMUM')";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("nama_toko")
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
		} catch(Exception e) {
			System.out.println("error tampil data " + e);
		}
	}
	
	private void TampilDetailData(String id) {
		db.con();
		try {
			String query = "select * from tb_pelanggan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtAlamat.setText(rs.getString("alamat"));
				txtId.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
				txtTelp.setText(rs.getString("telp"));
				txtToko.setText(rs.getString("nama_toko"));
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
			String query = "insert into tb_pelanggan values(?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtId.getText());
			ps.setString(2, txtNama.getText());
			ps.setString(3, txtToko.getText());
			ps.setString(4, txtAlamat.getText());
			ps.setString(5, txtTelp.getText());
			ps.execute();
			ps.close();
			db.con.close();
			
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch(Exception e) {
			System.out.println("error simpan data: " + e);
			Update();
		}
	}
	
	private void Update() {
		db.con();
		try {
			String query = "update tb_pelanggan set nama =?, nama_toko = ?, "
					+ "alamat = ?, telp = ? where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNama.getText());
			ps.setString(2, txtToko.getText());
			ps.setString(3, txtAlamat.getText());
			ps.setString(4, txtTelp.getText());
			ps.setString(5, txtId.getText());	
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil diubah");
		} catch(Exception e) {
			System.out.println("error update " + e);
		}
	}
	
	
	private void Hapus(String id) {
		db.con();
		try {
			String query = "delete from tb_pelanggan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
		} catch(Exception e) {
			System.out.println("error hapus data: " + e);
		}
	}
}
