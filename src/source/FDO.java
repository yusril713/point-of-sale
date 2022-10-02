package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JTable;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FDO {

	JFrame frame;
	static FDO window;
	private JTextField txtKeyword;
	private JTable table;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel;
	private JTable table_2;
	private JLabel lblNewLabel_1;
	private JScrollPane scrollPane_2;
	private JButton btnHapus;
	private JComboBox comboBox;
	private JButton btnRiwayat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FDO();
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
	public FDO() {
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
				TampilDO("");
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1041, 710);
		frame.setExtendedState(frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 242, 138, 361, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0) {
					txtKeyword.setText("");
					TampilDO("");
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Tunai", "Kredit"}));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		txtKeyword = new JTextField();
		txtKeyword.getDocument().addDocumentListener(new DocumentListener() {
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
				if (!txtKeyword.getText().equals("")) {
					TampilDO(txtKeyword.getText());
				}
			}
		});
		txtKeyword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_txtKeyword = new GridBagConstraints();
		gbc_txtKeyword.insets = new Insets(0, 0, 5, 5);
		gbc_txtKeyword.fill = GridBagConstraints.BOTH;
		gbc_txtKeyword.gridx = 1;
		gbc_txtKeyword.gridy = 2;
		frame.getContentPane().add(txtKeyword, gbc_txtKeyword);
		txtKeyword.setColumns(10);
		
		lblNewLabel = new JLabel("Detail Barang");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 2;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0) {
					TampilDetailDO(table.getValueAt(table.getSelectedRow(), 0).toString());
					TampilRincianDO(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
			}
		});
		scrollPane.setViewportView(table);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 3;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 3;
		gbc_scrollPane_1.gridy = 3;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					FDetailDO.main(null);
					FDetailDO.noFaktur = table.getValueAt(table.getSelectedRow(), 0).toString();
					FDetailDO.idBarang = table_1.getValueAt(table_1.getSelectedRow(), 0).toString();
					FDetailDO.namaBarang = table_1.getValueAt(table_1.getSelectedRow(), 1).toString();
					FDetailDO.satuan = table_1.getValueAt(table_1.getSelectedRow(), 2).toString();
					FDetailDO.jumlahDo = Double.parseDouble(table_1.getValueAt(table_1.getSelectedRow(),3).toString());
					window.frame.setEnabled(false);
				}
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		lblNewLabel_1 = new JLabel("Rincian Pengambilan Barang");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 3;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 4;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 3;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 3;
		gbc_scrollPane_2.gridy = 5;
		frame.getContentPane().add(scrollPane_2, gbc_scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (table_2.getSelectedRow() >= 0)
						Hapus(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
			}
		});
		
		btnRiwayat = new JButton("Riwayat DO");
		btnRiwayat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FRiwayatDO.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnRiwayat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnRiwayat = new GridBagConstraints();
		gbc_btnRiwayat.insets = new Insets(0, 0, 5, 5);
		gbc_btnRiwayat.gridx = 4;
		gbc_btnRiwayat.gridy = 6;
		frame.getContentPane().add(btnRiwayat, gbc_btnRiwayat);
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 5;
		gbc_btnHapus.gridy = 6;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
	}
	
	private void TampilDO(String keyword) {
		Database db = new Database();
		db.con();
		
		DefaultTableModel model = new DefaultTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}};
		model.addColumn("No Faktur");
		model.addColumn("Pelanggan");
		model.addColumn("Tanggal Transaksi");
		model.addColumn("Karyawan");
		try {
			String query = "";
			if (comboBox.getSelectedIndex() == 0)
				query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
					+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
					+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
					+ "join tb_do on tb_do.no_faktur = tb_transaksi.no_faktur "
					+ "where (tb_transaksi.no_faktur like ? or tb_pelanggan.nama like ?) and status = '0'"
					+ "group by tb_transaksi.no_faktur";
			else 
				query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan,  tb_karyawan.nama as karyawan from tb_transaksi_kredit "
						+ "join tb_pelanggan on tb_pelanggan.id  = tb_transaksi_kredit.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
						+ "join tb_do on tb_do.no_faktur = tb_transaksi_kredit.no_faktur "
						+ "where (tb_transaksi_kredit.no_faktur like ? or tb_pelanggan.nama like ?) and status = '0'"
						+ "group by tb_transaksi_kredit.no_faktur";
			
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("pelanggan"),
						rs.getString("tanggal"), 
						rs.getString("karyawan")
				});
			}
			table.setModel(model);
			table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
			table.setFont(new Font("SansSerif", Font.PLAIN, 16));
			table.setRowHeight(25);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void TampilDetailDO(String noFaktur) {
		Database db = new Database();
		db.con();
		DefaultTableModel model = new DefaultTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}};
		model.addColumn("ID");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Jumlah DO");
		try {
			String query = "select tb_do.*, tb_barang.nama from tb_do "
					+ "join tb_barang on tb_barang.id = tb_do.id_barang "
					+ "where tb_do.no_faktur = ? and tb_do.jumlah_do > ? and status = '0'";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ps.setDouble(2, 0);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id_barang"),
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("jumlah_do")
				});
			}
			table_1.setModel(model);
			table_1.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
			table_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
			table_1.setRowHeight(25);
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void TampilRincianDO(String noFaktur) {
		Database db = new Database();
		db.con();
		
		DefaultTableModel model = new DefaultTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
			
		};
		model.addColumn("ID");
		model.addColumn("Nama Brg");
		model.addColumn("Satuan");
		model.addColumn("Tgl Ambil");
		model.addColumn("Jumlah Ambil");
		model.addColumn("ID DO");
		try {
			String query = "select tb_rincian_do.*, tb_barang.nama from tb_rincian_do "
					+ "join tb_barang on tb_rincian_do.id_barang = tb_barang.id "
					+ "where no_faktur = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id_barang"),
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("tanggal"),
						rs.getString("jumlah"),
						rs.getString("id"),
				});
			}
			table_2.setModel(model);
			table_2.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
			table_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
			table_2.setRowHeight(25);
			
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Hapus(String noFaktur) {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_do set status = '0', jumlah_do = jumlah_do + ? where no_faktur = ? and id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(table_2.getValueAt(table_2.getSelectedRow(), 4).toString()));
			ps.setString(2, noFaktur);
			ps.setString(3, table_2.getValueAt(table_2.getSelectedRow(), 0).toString());
			ps.setString(4, table_2.getValueAt(table_2.getSelectedRow(), 2).toString());
			ps.execute();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String query = "update tb_detail_barang set stok = stok + ? where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(table_2.getValueAt(table_2.getSelectedRow(), 4).toString()));
			ps.setString(2, table_2.getValueAt(table_2.getSelectedRow(), 0).toString());
			ps.setString(3, table_2.getValueAt(table_2.getSelectedRow(), 2).toString());
			ps.execute();
			
			query = "delete from tb_rincian_do where id = ?";
			ps = db.con.prepareStatement(query);
			ps.setString(1, table_2.getValueAt(table_2.getSelectedRow(), 5).toString());
			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TampilDetailDO(noFaktur);
		TampilRincianDO(noFaktur);
	}
}
