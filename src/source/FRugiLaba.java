package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Insets;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.DefaultComboBoxModel;
import org.jdesktop.swingx.JXTable;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXSearchField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;

public class FRugiLaba {

	JFrame frame;
	static FRugiLaba window;
	private JLabel lblMulai;
	private JXDatePicker datePicker;
	private JLabel lblSd;
	private JXDatePicker datePicker_1;
	private JXSearchField searchField;
	private JLabel lblSubtotal;
	private JLabel lblTotalPiutang;
	private JLabel lblLabaKotor;
	private JLabel lblPengeluaranBiaya;
	private JLabel lblLabaUsaha;
	private JLabel lblPemasukanBiayaLainnya;
	private JLabel lblLabaBersih;
	private JTextField txtSubtotal;
	private JTextField txtTotalPiutang;
	private JTextField txtLabaKotor;
	private JTextField txtPengeluaran;
	private JTextField txtLabaUsaha;
	private JTextField txtPemasukan;
	private JTextField txtLabaBersih;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private Database db;
	private long subtotal, totalPituang, 
		labaKotor, pengeluaran, labaUsaha, 
		pemasukan, labaBersih;
	private JXTable table;
	private DateFormat df;
	private JButton btnOk;
	private JLabel lblReturnPenjualan;
	private JLabel lblReturnPembelian;
	private JLabel lblRetrunPenjualan;
	private JLabel lblReturnPembelian_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(FlatIntelliJLaf.class.getName());
		} catch(Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FRugiLaba();
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
	public FRugiLaba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				lblMulai.setVisible(false);
				lblSd.setVisible(false);
				datePicker.setVisible(false);
				datePicker_1.setVisible(false);
				btnOk.setVisible(false);
				db = new Database();
				df = new SimpleDateFormat("yyyy-MM-dd");
				TampilSemua();
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1366, 768);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 0, 462, 0, 200, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblOpsi = new JLabel("Opsi");
		lblOpsi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblOpsi = new GridBagConstraints();
		gbc_lblOpsi.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOpsi.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpsi.gridx = 1;
		gbc_lblOpsi.gridy = 1;
		frame.getContentPane().add(lblOpsi, gbc_lblOpsi);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0) {
					if (comboBox.getSelectedIndex() == 0) {
						lblMulai.setVisible(false);
						lblSd.setVisible(false);
						datePicker.setVisible(false);
						datePicker_1.setVisible(false);
						btnOk.setVisible(false);
						TampilSemua();
					} else {
						lblMulai.setVisible(true);
						lblSd.setVisible(true);
						datePicker.setVisible(true);
						datePicker_1.setVisible(true);
						btnOk.setVisible(true);
					}
				}
			}
		});
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Semua Transaksi", "Berdasarkan Tanggal"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		lblMulai = new JLabel("Mulai");
		lblMulai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblMulai = new GridBagConstraints();
		gbc_lblMulai.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMulai.insets = new Insets(0, 0, 5, 5);
		gbc_lblMulai.gridx = 1;
		gbc_lblMulai.gridy = 2;
		frame.getContentPane().add(lblMulai, gbc_lblMulai);
		
		datePicker = new JXDatePicker();
		datePicker.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.gridx = 2;
		gbc_datePicker.gridy = 2;
		frame.getContentPane().add(datePicker, gbc_datePicker);
		
		lblSd = new JLabel("s.d");
		lblSd.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSd = new GridBagConstraints();
		gbc_lblSd.insets = new Insets(0, 0, 5, 5);
		gbc_lblSd.gridx = 3;
		gbc_lblSd.gridy = 2;
		frame.getContentPane().add(lblSd, gbc_lblSd);
		
		datePicker_1 = new JXDatePicker();
		datePicker_1.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		datePicker_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_datePicker_1 = new GridBagConstraints();
		gbc_datePicker_1.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker_1.gridx = 4;
		gbc_datePicker_1.gridy = 2;
		frame.getContentPane().add(datePicker_1, gbc_datePicker_1);
		
		searchField = new JXSearchField();
		searchField.getDocument().addDocumentListener(new DocumentListener() {
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
				if (comboBox.getSelectedIndex() == 0)
					TampilPencarian(searchField.getText());
				else if (comboBox.getSelectedIndex() == 1) 
					TampilPencarianDurasi(searchField.getText());
			}
		});
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TampilBerdasarkanTgl();
			}
		});
		btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk.gridx = 5;
		gbc_btnOk.gridy = 2;
		frame.getContentPane().add(btnOk, gbc_btnOk);
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.gridwidth = 2;
		gbc_searchField.insets = new Insets(0, 0, 5, 5);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 1;
		gbc_searchField.gridy = 3;
		frame.getContentPane().add(searchField, gbc_searchField);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 20;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JXTable();
		table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		scrollPane.setViewportView(table);
		
		lblSubtotal = new JLabel("Subtotal");
		lblSubtotal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSubtotal = new GridBagConstraints();
		gbc_lblSubtotal.fill = GridBagConstraints.BOTH;
		gbc_lblSubtotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubtotal.gridx = 8;
		gbc_lblSubtotal.gridy = 4;
		frame.getContentPane().add(lblSubtotal, gbc_lblSubtotal);
		
		txtSubtotal = new JTextField();
		txtSubtotal.setBackground(Color.WHITE);
		txtSubtotal.setBorder(null);
		txtSubtotal.setEditable(false);
		txtSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubtotal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSubtotal.setText("subtotal");
		GridBagConstraints gbc_txtSubtotal = new GridBagConstraints();
		gbc_txtSubtotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtSubtotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSubtotal.gridx = 9;
		gbc_txtSubtotal.gridy = 4;
		frame.getContentPane().add(txtSubtotal, gbc_txtSubtotal);
		txtSubtotal.setColumns(10);
		
		lblTotalPiutang = new JLabel("Total Piutang");
		lblTotalPiutang.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblTotalPiutang = new GridBagConstraints();
		gbc_lblTotalPiutang.fill = GridBagConstraints.BOTH;
		gbc_lblTotalPiutang.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPiutang.gridx = 8;
		gbc_lblTotalPiutang.gridy = 5;
		frame.getContentPane().add(lblTotalPiutang, gbc_lblTotalPiutang);
		
		txtTotalPiutang = new JTextField();
		txtTotalPiutang.setBackground(Color.WHITE);
		txtTotalPiutang.setBorder(null);
		txtTotalPiutang.setEditable(false);
		txtTotalPiutang.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalPiutang.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTotalPiutang.setText("total piutang");
		GridBagConstraints gbc_txtTotalPiutang = new GridBagConstraints();
		gbc_txtTotalPiutang.insets = new Insets(0, 0, 5, 5);
		gbc_txtTotalPiutang.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTotalPiutang.gridx = 9;
		gbc_txtTotalPiutang.gridy = 5;
		frame.getContentPane().add(txtTotalPiutang, gbc_txtTotalPiutang);
		txtTotalPiutang.setColumns(10);
		
		lblRetrunPenjualan = new JLabel("Return Penjualan");
		lblRetrunPenjualan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblRetrunPenjualan.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblRetrunPenjualan = new GridBagConstraints();
		gbc_lblRetrunPenjualan.anchor = GridBagConstraints.WEST;
		gbc_lblRetrunPenjualan.insets = new Insets(0, 0, 5, 5);
		gbc_lblRetrunPenjualan.gridx = 8;
		gbc_lblRetrunPenjualan.gridy = 6;
		frame.getContentPane().add(lblRetrunPenjualan, gbc_lblRetrunPenjualan);
		
		lblReturnPenjualan = new JLabel("return penjualan");
		lblReturnPenjualan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReturnPenjualan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblReturnPenjualan = new GridBagConstraints();
		gbc_lblReturnPenjualan.anchor = GridBagConstraints.EAST;
		gbc_lblReturnPenjualan.insets = new Insets(0, 0, 5, 5);
		gbc_lblReturnPenjualan.gridx = 9;
		gbc_lblReturnPenjualan.gridy = 6;
		frame.getContentPane().add(lblReturnPenjualan, gbc_lblReturnPenjualan);
		
		lblReturnPembelian_1 = new JLabel("Return Pembelian");
		lblReturnPembelian_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblReturnPembelian_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblReturnPembelian_1 = new GridBagConstraints();
		gbc_lblReturnPembelian_1.anchor = GridBagConstraints.WEST;
		gbc_lblReturnPembelian_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblReturnPembelian_1.gridx = 8;
		gbc_lblReturnPembelian_1.gridy = 7;
		frame.getContentPane().add(lblReturnPembelian_1, gbc_lblReturnPembelian_1);
		
		lblReturnPembelian = new JLabel("return pembelian");
		lblReturnPembelian.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblReturnPembelian = new GridBagConstraints();
		gbc_lblReturnPembelian.anchor = GridBagConstraints.EAST;
		gbc_lblReturnPembelian.insets = new Insets(0, 0, 5, 5);
		gbc_lblReturnPembelian.gridx = 9;
		gbc_lblReturnPembelian.gridy = 7;
		frame.getContentPane().add(lblReturnPembelian, gbc_lblReturnPembelian);
		
		separator = new JSeparator();
		separator.setBorder(new LineBorder(new Color(0, 0, 0)));
		separator.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 8;
		gbc_separator.gridy = 8;
		frame.getContentPane().add(separator, gbc_separator);
		
		lblLabaKotor = new JLabel("Laba Kotor");
		lblLabaKotor.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLabaKotor = new GridBagConstraints();
		gbc_lblLabaKotor.fill = GridBagConstraints.BOTH;
		gbc_lblLabaKotor.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabaKotor.gridx = 8;
		gbc_lblLabaKotor.gridy = 9;
		frame.getContentPane().add(lblLabaKotor, gbc_lblLabaKotor);
		
		txtLabaKotor = new JTextField();
		txtLabaKotor.setBackground(Color.WHITE);
		txtLabaKotor.setBorder(null);
		txtLabaKotor.setEditable(false);
		txtLabaKotor.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLabaKotor.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtLabaKotor.setText("laba kotor");
		GridBagConstraints gbc_txtLabaKotor = new GridBagConstraints();
		gbc_txtLabaKotor.insets = new Insets(0, 0, 5, 5);
		gbc_txtLabaKotor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLabaKotor.gridx = 9;
		gbc_txtLabaKotor.gridy = 9;
		frame.getContentPane().add(txtLabaKotor, gbc_txtLabaKotor);
		txtLabaKotor.setColumns(10);
		
		lblPengeluaranBiaya = new JLabel("Pengeluaran Biaya");
		lblPengeluaranBiaya.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblPengeluaranBiaya = new GridBagConstraints();
		gbc_lblPengeluaranBiaya.fill = GridBagConstraints.BOTH;
		gbc_lblPengeluaranBiaya.insets = new Insets(0, 0, 5, 5);
		gbc_lblPengeluaranBiaya.gridx = 8;
		gbc_lblPengeluaranBiaya.gridy = 10;
		frame.getContentPane().add(lblPengeluaranBiaya, gbc_lblPengeluaranBiaya);
		
		txtPengeluaran = new JTextField();
		txtPengeluaran.setBackground(Color.WHITE);
		txtPengeluaran.setBorder(null);
		txtPengeluaran.setEditable(false);
		txtPengeluaran.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPengeluaran.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtPengeluaran.setText("pengeluaran");
		GridBagConstraints gbc_txtPengeluaran = new GridBagConstraints();
		gbc_txtPengeluaran.insets = new Insets(0, 0, 5, 5);
		gbc_txtPengeluaran.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPengeluaran.gridx = 9;
		gbc_txtPengeluaran.gridy = 10;
		frame.getContentPane().add(txtPengeluaran, gbc_txtPengeluaran);
		txtPengeluaran.setColumns(10);
		
		separator_1 = new JSeparator();
		separator_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		separator_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 2;
		gbc_separator_1.fill = GridBagConstraints.BOTH;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 8;
		gbc_separator_1.gridy = 11;
		frame.getContentPane().add(separator_1, gbc_separator_1);
		
		lblLabaUsaha = new JLabel("Laba Usaha");
		lblLabaUsaha.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLabaUsaha = new GridBagConstraints();
		gbc_lblLabaUsaha.fill = GridBagConstraints.BOTH;
		gbc_lblLabaUsaha.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabaUsaha.gridx = 8;
		gbc_lblLabaUsaha.gridy = 12;
		frame.getContentPane().add(lblLabaUsaha, gbc_lblLabaUsaha);
		
		txtLabaUsaha = new JTextField();
		txtLabaUsaha.setBackground(Color.WHITE);
		txtLabaUsaha.setBorder(null);
		txtLabaUsaha.setEditable(false);
		txtLabaUsaha.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLabaUsaha.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtLabaUsaha.setText("laba usaha");
		GridBagConstraints gbc_txtLabaUsaha = new GridBagConstraints();
		gbc_txtLabaUsaha.insets = new Insets(0, 0, 5, 5);
		gbc_txtLabaUsaha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLabaUsaha.gridx = 9;
		gbc_txtLabaUsaha.gridy = 12;
		frame.getContentPane().add(txtLabaUsaha, gbc_txtLabaUsaha);
		txtLabaUsaha.setColumns(10);
		
		lblPemasukanBiayaLainnya = new JLabel("Pemasukan Biaya Lainnya");
		lblPemasukanBiayaLainnya.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblPemasukanBiayaLainnya = new GridBagConstraints();
		gbc_lblPemasukanBiayaLainnya.fill = GridBagConstraints.BOTH;
		gbc_lblPemasukanBiayaLainnya.insets = new Insets(0, 0, 5, 5);
		gbc_lblPemasukanBiayaLainnya.gridx = 8;
		gbc_lblPemasukanBiayaLainnya.gridy = 13;
		frame.getContentPane().add(lblPemasukanBiayaLainnya, gbc_lblPemasukanBiayaLainnya);
		
		txtPemasukan = new JTextField();
		txtPemasukan.setBackground(Color.WHITE);
		txtPemasukan.setBorder(null);
		txtPemasukan.setEditable(false);
		txtPemasukan.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPemasukan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtPemasukan.setText("pemasukan");
		GridBagConstraints gbc_txtPemasukan = new GridBagConstraints();
		gbc_txtPemasukan.insets = new Insets(0, 0, 5, 5);
		gbc_txtPemasukan.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPemasukan.gridx = 9;
		gbc_txtPemasukan.gridy = 13;
		frame.getContentPane().add(txtPemasukan, gbc_txtPemasukan);
		txtPemasukan.setColumns(10);
		
		separator_2 = new JSeparator();
		separator_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		separator_2.setBackground(Color.WHITE);
		separator_2.setForeground(Color.BLACK);
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridwidth = 2;
		gbc_separator_2.fill = GridBagConstraints.BOTH;
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 8;
		gbc_separator_2.gridy = 14;
		frame.getContentPane().add(separator_2, gbc_separator_2);
		
		lblLabaBersih = new JLabel("Laba Bersih");
		lblLabaBersih.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLabaBersih = new GridBagConstraints();
		gbc_lblLabaBersih.fill = GridBagConstraints.BOTH;
		gbc_lblLabaBersih.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabaBersih.gridx = 8;
		gbc_lblLabaBersih.gridy = 15;
		frame.getContentPane().add(lblLabaBersih, gbc_lblLabaBersih);
		
		txtLabaBersih = new JTextField();
		txtLabaBersih.setBackground(Color.WHITE);
		txtLabaBersih.setBorder(null);
		txtLabaBersih.setEditable(false);
		txtLabaBersih.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLabaBersih.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtLabaBersih.setText("laba bersih");
		GridBagConstraints gbc_txtLabaBersih = new GridBagConstraints();
		gbc_txtLabaBersih.insets = new Insets(0, 0, 5, 5);
		gbc_txtLabaBersih.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLabaBersih.gridx = 9;
		gbc_txtLabaBersih.gridy = 15;
		frame.getContentPane().add(txtLabaBersih, gbc_txtLabaBersih);
		txtLabaBersih.setColumns(10);
	}
	
	protected void TampilPencarianDurasi(String key) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		String[] column = {"No", "Nama", "Satuan", "Qty", "Harga Beli", "Harga Jual", "Laba"};
		model.setColumnIdentifiers(column);
		db.con();
		try {
			String query = "select tb_barang.nama, sum(tb_detail_transaksi.qty) as qty, (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty)) as hrg_beli, "
					+ "	(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) as hrg_jual, ("
					+ "(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) - (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty))) as laba, tb_detail_transaksi.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi on tb_detail_transaksi.id_barang = tb_detail_barang.id_barang "
					+ "and tb_detail_barang.satuan = tb_detail_transaksi.satuan "
					+ "join tb_transaksi on tb_transaksi.no_faktur = tb_detail_transaksi.no_faktur "
					+ "where tb_transaksi.tanggal between ? and ? "
					+ "and (tb_barang.id like ? or tb_barang.nama like ?) "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan "
					+ "UNION "
					+ "select tb_barang.nama, sum(tb_detail_transaksi_kredit.qty) as qty, (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty)) as hrg_beli, "
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) as hrg_jual, ("
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) - (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty))) as laba, tb_detail_transaksi_kredit.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi_kredit on tb_detail_transaksi_kredit.id_barang = tb_detail_barang.id_barang "
					+ "and tb_detail_barang.satuan = tb_detail_transaksi_kredit.satuan "
					+ "join tb_transaksi_kredit on tb_transaksi_kredit.no_faktur = tb_detail_transaksi_kredit.no_faktur "
					+ "where tb_transaksi_kredit.tanggal between ? and ? "
					+ "and (tb_barang.id like ? or tb_barang.nama like ?)"
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, df.format(datePicker.getDate()));
			ps.setString(2, df.format(datePicker_1.getDate()));
			ps.setString(3, "%" + key + "%");
			ps.setString(4, "%" + key + "%");
			ps.setString(5, df.format(datePicker.getDate()));
			ps.setString(6, df.format(datePicker_1.getDate()));
			ps.setString(7, "%" + key + "%");
			ps.setString(8, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			int no = 1;
			subtotal = 0;
			while (rs.next()) {
				subtotal += rs.getInt("laba");
				model.addRow(new Object[] {
						no++,
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("qty"),
						FMain.FormatAngka(rs.getInt("hrg_beli")),
						FMain.FormatAngka(rs.getInt("hrg_jual")),
						FMain.FormatAngka(rs.getInt("laba"))
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			table.setModel(model);
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		    rightRenderer.setHorizontalAlignment(4);
		    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		    centerRenderer.setHorizontalAlignment(0);
		    
		    table.setAutoResizeMode(0);
		    table.getColumnModel().getColumn(0).setPreferredWidth(50);
		    table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(1).setPreferredWidth(200);
		    table.getColumnModel().getColumn(2).setPreferredWidth(150);
		    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(3).setPreferredWidth(80);
		    table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(4).setPreferredWidth(150);
		    table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(5).setPreferredWidth(150);
		    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(6).setPreferredWidth(150);
		    table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(25);
		} catch(Exception e) {
			System.out.println("RL" + e);
		}
	}

	protected void TampilPencarian(String key) {
		db.con();
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		String[] column = {"No", "Nama", "Satuan", "Qty", "Harga Beli", "Harga Jual", "Laba"};
		model.setColumnIdentifiers(column);
		try {
			String query = "select tb_barang.nama, sum(tb_detail_transaksi.qty) as qty, (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty)) as hrg_beli, " 
					+ "(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) as hrg_jual, ( "
						+ "	(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) - (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty))) as laba, tb_detail_transaksi.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi on tb_detail_transaksi.id_barang = tb_detail_barang.id_barang " 
					+ "and tb_detail_barang.satuan = tb_detail_transaksi.satuan "
					+ "join tb_transaksi on tb_transaksi.no_faktur = tb_detail_transaksi.no_faktur "
					+ "where tb_barang.nama like ? or tb_barang.id like ? "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan "
					+ "UNION "
					+ "select tb_barang.nama, sum(tb_detail_transaksi_kredit.qty) as qty, (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty)) as hrg_beli, " 
						+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) as hrg_jual, ("
							+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) - (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty))) as laba, tb_detail_transaksi_kredit.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi_kredit on tb_detail_transaksi_kredit.id_barang = tb_detail_barang.id_barang " 
					+ "and tb_detail_barang.satuan = tb_detail_transaksi_kredit.satuan "
					+ "join tb_transaksi_kredit on tb_transaksi_kredit.no_faktur = tb_detail_transaksi_kredit.no_faktur "
					+ "where tb_barang.nama like ? or tb_barang.id like ? or tb_detail_transaksi_kredit.id_barang like ? "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ps.setString(3, "%" + key + "%");
			ps.setString(4, "%" + key + "%");
			ps.setString(5, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			int no = 1;
			subtotal = 0;
			while(rs.next()) {
				model.addRow(new Object[] {
						no++,
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("qty"),
						FMain.FormatAngka(rs.getInt("hrg_beli")),
						FMain.FormatAngka(rs.getInt("hrg_jual")),
						FMain.FormatAngka(rs.getInt("laba"))
				});
			}
			
			rs.close();
			ps.close();
			db.con.close();
			
			table.setModel(model);
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		    rightRenderer.setHorizontalAlignment(4);
		    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		    centerRenderer.setHorizontalAlignment(0);
		    
		    table.setAutoResizeMode(0);
		    table.getColumnModel().getColumn(0).setPreferredWidth(50);
		    table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(1).setPreferredWidth(200);
		    table.getColumnModel().getColumn(2).setPreferredWidth(150);
		    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(3).setPreferredWidth(80);
		    table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(4).setPreferredWidth(150);
		    table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(5).setPreferredWidth(150);
		    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(6).setPreferredWidth(150);
		    table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(25);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void TampilSemua() {
		db.con();
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		String[] column = {"No", "Nama", "Satuan", "Qty", "Harga Beli", "Harga Jual", "Laba"};
		model.setColumnIdentifiers(column);
		try {
			String query = "select tb_detail_transaksi.id_barang, tb_barang.nama, sum(tb_detail_transaksi.qty) as qty, (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty)) as hrg_beli, " 
					+ "(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) as hrg_jual, ( "
						+ "	(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) - (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty))) as laba, tb_detail_transaksi.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi on tb_detail_transaksi.id_barang = tb_detail_barang.id_barang " 
					+ "and tb_detail_barang.satuan = tb_detail_transaksi.satuan "
					+ "join tb_transaksi on tb_transaksi.no_faktur = tb_detail_transaksi.no_faktur "
					+ "GROUP BY tb_detail_transaksi.id_barang, tb_detail_barang.satuan "
					+ "UNION "
					+ "select tb_detail_transaksi_kredit.id_barang, tb_barang.nama, sum(tb_detail_transaksi_kredit.qty) as qty, (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty)) as hrg_beli, " 
						+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) as hrg_jual, ("
							+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) - (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty))) as laba, tb_detail_transaksi_kredit.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi_kredit on tb_detail_transaksi_kredit.id_barang = tb_detail_barang.id_barang " 
					+ "and tb_detail_barang.satuan = tb_detail_transaksi_kredit.satuan "
					+ "join tb_transaksi_kredit on tb_transaksi_kredit.no_faktur = tb_detail_transaksi_kredit.no_faktur "
					+ "GROUP BY tb_detail_transaksi_kredit.id_barang, tb_detail_barang.satuan ";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			int no = 1;
			subtotal = 0;
			while(rs.next()) {
				subtotal += rs.getLong("laba");
				model.addRow(new Object[] {
						no++,
						rs.getString("id_barang") + " " + rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("qty"),
						FMain.FormatAngka(rs.getInt("hrg_beli")),
						FMain.FormatAngka(rs.getInt("hrg_jual")),
						FMain.FormatAngka(rs.getInt("laba"))
				});
			}
			
			rs.close();
			st.close();
			db.con.close();
			
			table.setModel(model);
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		    rightRenderer.setHorizontalAlignment(4);
		    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		    centerRenderer.setHorizontalAlignment(0);
		    
		    table.setAutoResizeMode(0);
		    table.getColumnModel().getColumn(0).setPreferredWidth(50);
		    table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(1).setPreferredWidth(200);
		    table.getColumnModel().getColumn(2).setPreferredWidth(150);
		    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(3).setPreferredWidth(80);
		    table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(4).setPreferredWidth(150);
		    table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(5).setPreferredWidth(150);
		    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(6).setPreferredWidth(150);
		    table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(25);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long total_return_penjualan = AmbilReturnPenjualanAll();
		long total_return_pembelian = AmbilReturnPembelianAll();
		
		totalPituang = AmbilTotalPiutangAll();
		pengeluaran = AmbilCashFlowAll("Pengeluaran");
		pemasukan = AmbilCashFlowAll("Pemasukan");
		labaKotor = subtotal - totalPituang - total_return_penjualan + total_return_pembelian;
		labaUsaha = labaKotor - pengeluaran;
		labaBersih = labaUsaha + pemasukan;

		txtSubtotal.setText("Rp. " + FMain.FormatAngka(subtotal));
		txtTotalPiutang.setText("Rp. " + FMain.FormatAngka(totalPituang));
		txtLabaKotor.setText("Rp. " + FMain.FormatAngka(labaKotor));
		txtPengeluaran.setText("Rp. " + FMain.FormatAngka(pengeluaran));
		txtLabaUsaha.setText("Rp. " + FMain.FormatAngka(labaUsaha));
		txtPemasukan.setText("Rp. " + FMain.FormatAngka(pemasukan));
		txtLabaBersih.setText("Rp. " + FMain.FormatAngka(labaBersih));
		
		lblReturnPenjualan.setText("Rp. " + FMain.FormatAngka(total_return_penjualan));
		lblReturnPembelian.setText("Rp. " + FMain.FormatAngka(total_return_pembelian));
	}
	
	private long AmbilCashFlowAll(String jenis) {
		db.con();
		try {
			String query = "select sum(jumlah) as jumlah from tb_cashflow where jenis = ? ";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, jenis);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getLong("jumlah");
			
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private long AmbilTotalPiutangAll() {
		db.con();
		try {
			String query = "select sum(sisa_hutang)as tot from tb_hutang "
					+ "join tb_transaksi_kredit on tb_hutang.no_faktur = tb_transaksi_kredit.no_faktur";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				return rs.getLong("tot");
			}
		} catch(Exception e) {
			System.out.println("Piutang " + e);
		}
		return 0;
	}
	
	private long AmbilTotalPiutang() {
		db.con();
		try {
			String query = "select sum(sisa_hutang)as tot from tb_hutang "
					+ "join tb_transaksi_kredit on tb_hutang.no_faktur = tb_transaksi_kredit.no_faktur "
					+ "where tb_transaksi_kredit.tanggal between ? and ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, df.format(datePicker.getDate()));
			ps.setString(2, df.format(datePicker_1.getDate()));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getLong("tot");
			}
		} catch(Exception e) {
			System.out.println("Piutang " + e);
		}
		return 0;
	}
	
	private void TampilBerdasarkanTgl() {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		String[] column = {"No", "Nama", "Satuan", "Qty", "Harga Beli", "Harga Jual", "Laba"};
		model.setColumnIdentifiers(column);
		db.con();
		try {
			String query = "select tb_barang.nama, sum(tb_detail_transaksi.qty) as qty, (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty)) as hrg_beli, "
					+ "	(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) as hrg_jual, ("
					+ "(tb_detail_transaksi.harga_jual * sum(tb_detail_transaksi.qty)) - (tb_detail_transaksi.harga_beli * sum(tb_detail_transaksi.qty))) as laba, tb_detail_transaksi.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi on tb_detail_transaksi.id_barang = tb_detail_barang.id_barang "
					+ "and tb_detail_barang.satuan = tb_detail_transaksi.satuan "
					+ "join tb_transaksi on tb_transaksi.no_faktur = tb_detail_transaksi.no_faktur "
					+ "where tb_transaksi.tanggal between ? and ? "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan "
					+ "UNION "
					+ "select tb_barang.nama, sum(tb_detail_transaksi_kredit.qty) as qty, (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty)) as hrg_beli, "
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) as hrg_jual, ("
					+ "(tb_detail_transaksi_kredit.harga_jual * sum(tb_detail_transaksi_kredit.qty)) - (tb_detail_transaksi_kredit.harga_beli * sum(tb_detail_transaksi_kredit.qty))) as laba, tb_detail_transaksi_kredit.satuan from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "right join tb_detail_transaksi_kredit on tb_detail_transaksi_kredit.id_barang = tb_detail_barang.id_barang "
					+ "and tb_detail_barang.satuan = tb_detail_transaksi_kredit.satuan "
					+ "join tb_transaksi_kredit on tb_transaksi_kredit.no_faktur = tb_detail_transaksi_kredit.no_faktur "
					+ "where tb_transaksi_kredit.tanggal between ? and ? "
					+ "GROUP BY tb_detail_barang.id_barang, tb_detail_barang.satuan";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, df.format(datePicker.getDate()));
			ps.setString(2, df.format(datePicker_1.getDate()));
			ps.setString(3, df.format(datePicker.getDate()));
			ps.setString(4, df.format(datePicker_1.getDate()));
			ResultSet rs = ps.executeQuery();
			int no = 1;
			subtotal = 0;
			while (rs.next()) {
				subtotal += rs.getInt("laba");
				model.addRow(new Object[] {
						no++,
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("qty"),
						FMain.FormatAngka(rs.getInt("hrg_beli")),
						FMain.FormatAngka(rs.getInt("hrg_jual")),
						FMain.FormatAngka(rs.getInt("laba"))
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			table.setModel(model);
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		    rightRenderer.setHorizontalAlignment(4);
		    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		    centerRenderer.setHorizontalAlignment(0);
		    
		    table.setAutoResizeMode(0);
		    table.getColumnModel().getColumn(0).setPreferredWidth(50);
		    table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(1).setPreferredWidth(200);
		    table.getColumnModel().getColumn(2).setPreferredWidth(150);
		    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(3).setPreferredWidth(80);
		    table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(4).setPreferredWidth(150);
		    table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(5).setPreferredWidth(150);
		    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		    table.getColumnModel().getColumn(6).setPreferredWidth(150);
		    table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
			table.setRowHeight(25);
		} catch(Exception e) {
			System.out.println("RL" + e);
		}
		long total_return_penjualan = AmbilReturnPenjualan();
		long total_return_pembelian = AmbilReturnPembelian();
		
		totalPituang = AmbilTotalPiutang();
		pengeluaran = AmbilCashFlow("Pengeluaran");
		pemasukan = AmbilCashFlow("Pemasukan");
		labaKotor = subtotal - totalPituang - total_return_penjualan + total_return_pembelian;
		labaUsaha = labaKotor - pengeluaran;
		labaBersih = labaUsaha + pemasukan;

		txtSubtotal.setText("Rp. " + FMain.FormatAngka(subtotal));
		txtTotalPiutang.setText("Rp. " + FMain.FormatAngka(totalPituang));
		txtLabaKotor.setText("Rp. " + FMain.FormatAngka(labaKotor));
		txtPengeluaran.setText("Rp. " + FMain.FormatAngka(pengeluaran));
		txtLabaUsaha.setText("Rp. " + FMain.FormatAngka(labaUsaha));
		txtPemasukan.setText("Rp. " + FMain.FormatAngka(pemasukan));
		txtLabaBersih.setText("Rp. " + FMain.FormatAngka(labaBersih));
		
		lblReturnPenjualan.setText("Rp." + FMain.FormatAngka(AmbilReturnPenjualan()));
		lblReturnPembelian.setText("Rp. " + FMain.FormatAngka(AmbilReturnPembelian()));
	}
	
	private long AmbilCashFlow(String jenis) {
		db.con();
		try {
			String query = "select sum(jumlah) as jumlah from tb_cashflow where jenis = ? "
					+ "AND tanggal between ? and ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, jenis);
			ps.setString(2, df.format(datePicker.getDate()));
			ps.setString(3, df.format(datePicker_1.getDate()));
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getLong("jumlah");
			
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private long AmbilReturnPenjualan() {
		db.con();
		try {
			String query = "select (tb_detail_return_penjualan.harga * tb_detail_return_penjualan.jumlah) as total from tb_return_penjualan "
					+ "join tb_detail_return_penjualan on tb_detail_return_penjualan.id_return = tb_return_penjualan.id "
					+ "where tanggal between ? and ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, df.format(datePicker.getDate()));
			ps.setString(2, df.format(datePicker_1.getDate()));
			ResultSet rs = ps.executeQuery();
			long subtotal = 0;
			while (rs.next()) {
				subtotal += rs.getLong("total");
			}
			
			rs.close();
			ps.close();
			db.con.close();
			
			return subtotal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private long AmbilReturnPenjualanAll() {
		db.con();
		try {
			String query = "select (tb_detail_return_penjualan.harga * tb_detail_return_penjualan.jumlah) as total from tb_return_penjualan "
					+ "join tb_detail_return_penjualan on tb_detail_return_penjualan.id_return = tb_return_penjualan.id ";
			Statement st = db.con.createStatement();
			ResultSet rs  = st.executeQuery(query);
			long subtotal = 0;
			while (rs.next()) {
				subtotal += rs.getLong("total");
			}
			
			rs.close();
			st.close();
			db.con.close();
			
			return subtotal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private long AmbilReturnPembelianAll() {
		db.con();
		try {
			String query = "select (tb_detail_return_pembelian.harga * tb_detail_return_pembelian.jumlah) as total from tb_return_pembelian "
					+ "join tb_detail_return_pembelian on tb_detail_return_pembelian.id_return = tb_return_pembelian.id ";
			Statement st = db.con.createStatement();
			ResultSet rs  = st.executeQuery(query);
			long subtotal = 0;
			while (rs.next()) {
				subtotal += rs.getLong("total");
			}
			
			rs.close();
			st.close();
			db.con.close();
			
			return subtotal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private long AmbilReturnPembelian() {
		db.con();
		try {
			String query = "select (tb_detail_return_pembelian.harga * tb_detail_return_pembelian.jumlah) as total from tb_return_pembelian "
					+ "join tb_detail_return_pembelian on tb_detail_return_pembelian.id_return = tb_return_pembelian.id "
					+ "where tanggal between ? and ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, df.format(datePicker.getDate()));
			ps.setString(2, df.format(datePicker_1.getDate()));
			ResultSet rs = ps.executeQuery();
			long subtotal = 0;
			while (rs.next()) {
				subtotal += rs.getLong("total");
			}
			
			rs.close();
			ps.close();
			db.con.close();
			
			return subtotal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
