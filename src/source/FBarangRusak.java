package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.SystemColor;
import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Color;
import org.jdesktop.swingx.JXSearchField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FBarangRusak {

	JFrame frame;
	static FBarangRusak window;
	static JXTable table;
	static DefaultTableModel model;
	static DefaultTableModel modelBrg;
	private JScrollPane scrollPane_1;
	static JXTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FBarangRusak();
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
	public FBarangRusak() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				TampilBarangRusak("");
				TampilDetailBarangRusak("");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1200, 550);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDaftarBarangTidak = new JLabel("DAFTAR BARANG TIDAK LAYAK JUAL / RUSAK");
		lblDaftarBarangTidak.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblDaftarBarangTidak.setHorizontalAlignment(SwingConstants.CENTER);
		lblDaftarBarangTidak.setBounds(6, 6, 672, 16);
		frame.getContentPane().add(lblDaftarBarangTidak);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.windowBorder);
		separator.setBounds(0, 23, 684, 9);
		frame.getContentPane().add(separator);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Detail Barang Tidak Layak Jual", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(511, 54, 651, 407);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 18, 641, 343);
		panel.add(scrollPane);
		
		table = new JXTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					FTambahBarangRusak.main(null);
					FTambahBarangRusak.edit = true;
					FTambahBarangRusak.id_r = table.getValueAt(table.getSelectedRow(), 0).toString();
					FTambahBarangRusak.id = table.getValueAt(table.getSelectedRow(), 1).toString();
					FTambahBarangRusak.satuan = table.getValueAt(table.getSelectedRow(), 3).toString();
					FTambahBarangRusak.jumlah = table.getValueAt(table.getSelectedRow(), 4).toString();
					FTambahBarangRusak.keterangan = table.getValueAt(table.getSelectedRow(), 5).toString();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblKlikx = new JLabel("* Klik 2x pada tabel untuk mengedit barang");
		lblKlikx.setBounds(5, 373, 269, 16);
		panel.add(lblKlikx);
		lblKlikx.setForeground(Color.RED);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.setBounds(549, 368, 97, 26);
		panel.add(btnHapus);
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus barang tersebut?", "Konfirmasi", 0);
					if (konfirm == 0) {
						Hapus();
						TampilBarangRusak("");
						TampilDetailBarangRusak("");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Pilih data yg ingin dihapus");
				}
			}
		});
		
		JButton btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTambahBarangRusak.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnTambah.setBounds(6, 489, 97, 26);
		frame.getContentPane().add(btnTambah);
		
		JButton btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnTutup.setBounds(1067, 473, 97, 26);
		frame.getContentPane().add(btnTutup);
		
		JXSearchField srchfldCari = new JXSearchField();
		srchfldCari.getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		srchfldCari.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				EventChanged();
			}
			public void EventChanged() {
				TampilDetailBarangRusak(srchfldCari.getText());
				TampilBarangRusak(srchfldCari.getText());
			}
		});
		srchfldCari.setBounds(6, 30, 247, 26);
		frame.getContentPane().add(srchfldCari);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 68, 490, 415);
		frame.getContentPane().add(scrollPane_1);
		
		table_1 = new JXTable();
		scrollPane_1.setViewportView(table_1);
	}
	
	static void TampilDetailBarangRusak(String key) {
		model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("id");
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Jumlah");
		model.addColumn("Keterangan");
		
		Database db = new Database();
		db.con();
		try {
			String query = "select tb_barang_rusak.*, tb_barang.nama from tb_barang_rusak join "
					+ "tb_barang on tb_barang.id = tb_barang_rusak.id_barang "
					+ "where tb_barang.id like ? or tb_barang.nama like ? "
					+ "order by tb_barang.nama";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
					rs.getString("id"),
					rs.getString("id_barang"),
					rs.getString("nama"),
					rs.getString("satuan"),
					rs.getString("jumlah"), 
					rs.getString("keterangan")
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setPreferredWidth(80);
			table.getColumnModel().getColumn(2).setPreferredWidth(170);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(60);
			table.getColumnModel().getColumn(5).setPreferredWidth(150);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static void TampilBarangRusak(String key) {
		modelBrg = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		modelBrg.addColumn("Kode Barang");
		modelBrg.addColumn("Nama Barang");
		modelBrg.addColumn("Satuan");
		modelBrg.addColumn("Jumlah");
		
		Database db = new Database();
		db.con();
		try {
			String query = "select tb_barang_rusak.*, sum(tb_barang_rusak.jumlah) as jml, "
					+ "tb_barang.nama from tb_barang_rusak join "
					+ "tb_barang on tb_barang.id = tb_barang_rusak.id_barang "
					+ "where tb_barang.id like ? or tb_barang.nama like ? "
					+ "group by tb_barang_rusak.id_barang, tb_barang_rusak.satuan "
					+ "order by tb_barang.nama ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				modelBrg.addRow(new Object[] {
					rs.getString("id_barang"),
					rs.getString("nama"),
					rs.getString("satuan"),
					rs.getString("jml"), 
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			
			table_1.setModel(modelBrg);
			table_1.setAutoResizeMode(0);
			table_1.getColumnModel().getColumn(0).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(170);
			table_1.getColumnModel().getColumn(2).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(3).setPreferredWidth(60);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Hapus() {
		Database db = new Database();
		db.con();
		try {
			String query = "delete from tb_barang_rusak where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, table.getValueAt(table.getSelectedRow(), 0).toString());
			ps.execute();
			
			query = "update tb_barang set stok = stok + ? where id_barang = ? and satuan = ?";
			ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(table.getValueAt(table.getSelectedRow(), 4).toString()));
			ps.setString(2, table.getValueAt(table.getSelectedRow(), 1).toString());
			ps.setString(3, table.getValueAt(table.getSelectedRow(), 3).toString());
			ps.execute();
			
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil dihapus...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
