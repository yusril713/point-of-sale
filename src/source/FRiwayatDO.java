package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.Insets;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FRiwayatDO {

	JFrame frame;
	static FRiwayatDO window ;
	private JXDatePicker datePicker;
	private JTextField textField;
	private JTable table;
	private JTable table_1;
	private JComboBox comboBox;
	private JXDatePicker datePicker_1;
	private JLabel lblNewLabel;
	private JComboBox cmbJenis;
	private JButton btnOk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FRiwayatDO();
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
	public FRiwayatDO() {
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
				SetOrder();
				TampilData(textField.getText());
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FDO.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 971, 673);
		frame.setExtendedState(frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 0, 201, 0, 0, 195, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0) {
					SetOrder(); 
				}
			}
		});
		
		cmbJenis = new JComboBox();
		cmbJenis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbJenis.getSelectedIndex() >= 0) {
					TampilData(textField.getText());
				}
			}
		});
		cmbJenis.setModel(new DefaultComboBoxModel(new String[] {"Tunai", "Kredit"}));
		cmbJenis.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbJenis = new GridBagConstraints();
		gbc_cmbJenis.insets = new Insets(0, 0, 5, 5);
		gbc_cmbJenis.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbJenis.gridx = 1;
		gbc_cmbJenis.gridy = 1;
		frame.getContentPane().add(cmbJenis, gbc_cmbJenis);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Semua", "Berdasarkan Tanggal", "Berdasarkan Pencarian"}));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		datePicker = new JXDatePicker();
		datePicker.getEditor().setEditable(false);
		datePicker.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.fill = GridBagConstraints.BOTH;
		gbc_datePicker.gridx = 1;
		gbc_datePicker.gridy = 3;
		frame.getContentPane().add(datePicker, gbc_datePicker);
		
		lblNewLabel = new JLabel("s.d");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		datePicker_1 = new JXDatePicker();
		datePicker_1.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker_1.getEditor().setEditable(false);
		datePicker_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_datePicker_1 = new GridBagConstraints();
		gbc_datePicker_1.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker_1.fill = GridBagConstraints.BOTH;
		gbc_datePicker_1.gridx = 3;
		gbc_datePicker_1.gridy = 3;
		frame.getContentPane().add(datePicker_1, gbc_datePicker_1);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TampilData(textField.getText());
			}
		});
		btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk.gridx = 4;
		gbc_btnOk.gridy = 3;
		frame.getContentPane().add(btnOk, gbc_btnOk);
		
		textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener() {
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
				if (!textField.getText().equals("")) {
					TampilData(textField.getText());
				}
			}
		});
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 4;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Riwayat DO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 5;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0)
					TampilDetailRiwayat(table.getValueAt(table.getSelectedRow(), 0).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Detail Riwayat DO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 6;
		gbc_panel_1.gridy = 5;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{195, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
	}

	private void SetOrder() {
		if (comboBox.getSelectedIndex() == 0) {
			textField.setVisible(false);
			datePicker.setVisible(false);
			datePicker_1.setVisible(false);
			lblNewLabel.setVisible(false);
			btnOk.setVisible(false);
		} else if (comboBox.getSelectedIndex() == 1) {
			textField.setVisible(false);
			datePicker.setVisible(true);
			datePicker_1.setVisible(true);
			btnOk.setVisible(true);
			lblNewLabel.setVisible(true);
		} else {
			textField.setVisible(true);
			datePicker.setVisible(false);
			btnOk.setVisible(false);
			datePicker_1.setVisible(false);
			lblNewLabel.setVisible(false);
		}
	}
	
	private void TampilData(String keyword) {
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
		model.addColumn("No Faktur");
		model.addColumn("Pelanggan");
		model.addColumn("Tgl Transaksi");
		model.addColumn("Karyawan");
		
		String query = "";
		if (cmbJenis.getSelectedIndex() == 0) {
			if (comboBox.getSelectedIndex() == 0) {
				query = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
						+ "join tb_do on tb_do.no_faktur = tb_transaksi.no_faktur "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
						+ "group by tb_transaksi.no_faktur";
			} else if (comboBox.getSelectedIndex() == 1) {
				query  = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
						+ "join tb_do on tb_do.no_faktur = tb_transaksi.no_faktur "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
						+ "where tb_transaksi.tanggal between ? and ? "
						+ "group by tb_transaksi.no_faktur";
			} else {
				query  = "select tb_transaksi.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi "
						+ "join tb_do on tb_do.no_faktur = tb_transaksi.no_faktur "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi.id_karyawan "
						+ "where tb_transaksi.no_faktur like ? or tb_pelanggan.nama like ?"
						+ "group by tb_transaksi.no_faktur";
			}
		} else {
			if (comboBox.getSelectedIndex() == 0) {
				query = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
						+ "join tb_do on tb_do.no_faktur = tb_transaksi_kredit.no_faktur "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
						+ "group by tb_transaksi_kredit.no_faktur";
			} else if (comboBox.getSelectedIndex() == 1) {
				query  = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
						+ "join tb_do on tb_do.no_faktur = tb_transaksi_kredit.no_faktur "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
						+ "where tb_transaksi_kredit.tanggal between ? and ? "
						+ "group by tb_transaksi_kredit.no_faktur";
			} else {
				query  = "select tb_transaksi_kredit.*, tb_pelanggan.nama as pelanggan, tb_karyawan.nama as karyawan from tb_transaksi_kredit "
						+ "join tb_do on tb_do.no_faktur = tb_transaksi_kredit.no_faktur "
						+ "join tb_pelanggan on tb_pelanggan.id = tb_transaksi_kredit.id_pelanggan "
						+ "join tb_karyawan on tb_karyawan.id = tb_transaksi_kredit.id_karyawan "
						+ "where tb_transaksi_kredit.no_faktur like ? or tb_pelanggan.nama like ?"
						+ "group by tb_transaksi_kredit.no_faktur";
			}
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			PreparedStatement ps = null;
			Statement st = null;
			ResultSet rs = null;
			if (comboBox.getSelectedIndex() == 0) {
				st = db.con.createStatement();
				rs = st.executeQuery(query);
			} else if (comboBox.getSelectedIndex() == 1) {
				ps = db.con.prepareStatement(query);
				ps.setDate(1, Date.valueOf(df.format(datePicker.getDate())));
				ps.setDate(2, Date.valueOf(df.format(datePicker_1.getDate())));
				rs = ps.executeQuery();
			} else {
				ps = db.con.prepareStatement(query);
				ps.setString(1, "%" + keyword + "%");
				ps.setString(2, "%" + keyword + "%");
				rs = ps.executeQuery();
			}
			
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("no_faktur"),
						rs.getString("pelanggan"),
						rs.getString("tanggal"),
						rs.getString("karyawan")
				});
			}
			
			table.setModel(model);
			table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
    
			table.getTableHeader().setFont(new Font("SansSerrif", Font.PLAIN, 24));
			table.setRowHeight(25);
			table.setFont(new Font("SansSerif", Font.PLAIN, 16));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void TampilDetailRiwayat(String noFaktur) {
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
		model.addColumn("Tgl Ambil");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Jml Ambil");
		try {
			String query = "select tb_rincian_do.*, tb_barang.nama from tb_rincian_do "
					+ "join tb_barang on tb_barang.id = tb_rincian_do.id_barang "
					+ "where no_faktur = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("tanggal"),
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("jumlah")
				});
			}
			table_1.setModel(model);
			table_1.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
    
			table_1.getTableHeader().setFont(new Font("SansSerrif", Font.PLAIN, 24));
			table_1.setRowHeight(25);
			table_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
