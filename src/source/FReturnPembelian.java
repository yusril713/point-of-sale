package source;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

public class FReturnPembelian {

	JFrame frame;
	static FReturnPembelian window;
	private JTextField txtIdReturn;
	private JXDatePicker datePicker;
	static JTextField txtDistributor;
	static JTable table;
	private JLabel lblKaryawan;
	private JTextField txtKaryawan;
	private JButton btnListBrgTdk;
	private JButton btnHapus;
	private JButton button;
	static DefaultTableModel model;
	private Database db;
	static JTextField txtSubTotal;
	private JLabel lblklikxPada;
	private JButton btnSimpan;
	private JButton btnListReturnBrg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FReturnPembelian();
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
	public FReturnPembelian() {
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
				db = new Database();
				AturTabel();
				DataBaru();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1200, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 0, 30, 200, 0, 60, 0, 30, 230, 0, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblIdReturn = new JLabel("ID Return");
		lblIdReturn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblIdReturn = new GridBagConstraints();
		gbc_lblIdReturn.anchor = GridBagConstraints.WEST;
		gbc_lblIdReturn.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdReturn.gridx = 1;
		gbc_lblIdReturn.gridy = 1;
		frame.getContentPane().add(lblIdReturn, gbc_lblIdReturn);
		
		txtIdReturn = new JTextField();
		txtIdReturn.setEditable(false);
		txtIdReturn.setBorder(null);
		txtIdReturn.setBackground(SystemColor.menu);
		txtIdReturn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtIdReturn = new GridBagConstraints();
		gbc_txtIdReturn.gridwidth = 2;
		gbc_txtIdReturn.insets = new Insets(0, 0, 5, 5);
		gbc_txtIdReturn.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIdReturn.gridx = 3;
		gbc_txtIdReturn.gridy = 1;
		frame.getContentPane().add(txtIdReturn, gbc_txtIdReturn);
		txtIdReturn.setColumns(10);
		
		lblKaryawan = new JLabel("Karyawan");
		lblKaryawan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblKaryawan = new GridBagConstraints();
		gbc_lblKaryawan.anchor = GridBagConstraints.EAST;
		gbc_lblKaryawan.insets = new Insets(0, 0, 5, 5);
		gbc_lblKaryawan.gridx = 6;
		gbc_lblKaryawan.gridy = 1;
		frame.getContentPane().add(lblKaryawan, gbc_lblKaryawan);
		
		txtKaryawan = new JTextField();
		txtKaryawan.setEditable(false);
		txtKaryawan.setBorder(null);
		txtKaryawan.setBackground(SystemColor.menu);
		txtKaryawan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtKaryawan.setText("karyawan");
		GridBagConstraints gbc_txtKaryawan = new GridBagConstraints();
		gbc_txtKaryawan.insets = new Insets(0, 0, 5, 5);
		gbc_txtKaryawan.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtKaryawan.gridx = 8;
		gbc_txtKaryawan.gridy = 1;
		frame.getContentPane().add(txtKaryawan, gbc_txtKaryawan);
		txtKaryawan.setColumns(10);
		
		JLabel lblTanggalReturn = new JLabel("Tanggal Return");
		lblTanggalReturn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblTanggalReturn = new GridBagConstraints();
		gbc_lblTanggalReturn.anchor = GridBagConstraints.WEST;
		gbc_lblTanggalReturn.insets = new Insets(0, 0, 5, 5);
		gbc_lblTanggalReturn.gridx = 1;
		gbc_lblTanggalReturn.gridy = 2;
		frame.getContentPane().add(lblTanggalReturn, gbc_lblTanggalReturn);
		
		datePicker = new JXDatePicker();
		datePicker.getEditor().setBackground(SystemColor.menu);
		datePicker.getEditor().setBorder(null);
		datePicker.getEditor().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePicker.gridwidth = 2;
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.gridx = 3;
		gbc_datePicker.gridy = 2;
		frame.getContentPane().add(datePicker, gbc_datePicker);
		
		JLabel lblDistributor = new JLabel("Supplier");
		lblDistributor.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_lblDistributor = new GridBagConstraints();
		gbc_lblDistributor.anchor = GridBagConstraints.WEST;
		gbc_lblDistributor.insets = new Insets(0, 0, 5, 5);
		gbc_lblDistributor.gridx = 1;
		gbc_lblDistributor.gridy = 3;
		frame.getContentPane().add(lblDistributor, gbc_lblDistributor);
		
		txtDistributor = new JTextField();
		txtDistributor.setEditable(false);
		txtDistributor.setBorder(null);
		txtDistributor.setBackground(SystemColor.menu);
		txtDistributor.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDistributor.setText("distributor");
		GridBagConstraints gbc_txtDistributor = new GridBagConstraints();
		gbc_txtDistributor.insets = new Insets(0, 0, 5, 5);
		gbc_txtDistributor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDistributor.gridx = 3;
		gbc_txtDistributor.gridy = 3;
		frame.getContentPane().add(txtDistributor, gbc_txtDistributor);
		txtDistributor.setColumns(10);
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnCariDistributor.main(null);
				window.frame.setEnabled(false);
			}
		});
		button.setIcon(new ImageIcon(FReturnPembelian.class.getResource("/org/jdesktop/swingx/plaf/windows/resources/search.gif")));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.VERTICAL;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 4;
		gbc_button.gridy = 3;
		frame.getContentPane().add(button, gbc_button);
		
		btnListBrgTdk = new JButton("List brg tdk layak jual");
		btnListBrgTdk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FListBrgTdkLayakJual.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnListBrgTdk.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnListBrgTdk = new GridBagConstraints();
		gbc_btnListBrgTdk.insets = new Insets(0, 0, 5, 5);
		gbc_btnListBrgTdk.gridx = 10;
		gbc_btnListBrgTdk.gridy = 3;
		frame.getContentPane().add(btnListBrgTdk, gbc_btnListBrgTdk);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 10;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					if (table.getSelectedRow() >= 0) {
						FListBrgTdkLayakJual.main(null);
						FListBrgTdkLayakJual.edit = true;
						FListBrgTdkLayakJual.row = table.getSelectedRow();
						FListBrgTdkLayakJual.id = table.getValueAt(table.getSelectedRow(), 0).toString();
						FListBrgTdkLayakJual.idBarang = table.getValueAt(table.getSelectedRow(), 1).toString();
						FListBrgTdkLayakJual.nama = table.getValueAt(table.getSelectedRow(), 2).toString();
						FListBrgTdkLayakJual.satuan = table.getValueAt(table.getSelectedRow(), 3).toString();
						FListBrgTdkLayakJual.brgRusak = Double.parseDouble(table.getValueAt(table.getSelectedRow(), 7).toString());
						FListBrgTdkLayakJual.harga = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 4).toString().replace(",", ""));
						FListBrgTdkLayakJual.jumlah = Double.parseDouble(table.getValueAt(table.getSelectedRow(), 5).toString());
						window.frame.setEnabled(false);
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		
		lblklikxPada = new JLabel("*Klik 2x pada tabel untuk mengedit data");
		lblklikxPada.setForeground(Color.RED);
		GridBagConstraints gbc_lblklikxPada = new GridBagConstraints();
		gbc_lblklikxPada.gridwidth = 2;
		gbc_lblklikxPada.insets = new Insets(0, 0, 5, 5);
		gbc_lblklikxPada.gridx = 1;
		gbc_lblklikxPada.gridy = 5;
		frame.getContentPane().add(lblklikxPada, gbc_lblklikxPada);
		
		btnHapus = new JButton("Hapus");
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 1;
		gbc_btnHapus.gridy = 6;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
		
		txtSubTotal = new JTextField();
		txtSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubTotal.setEditable(false);
		txtSubTotal.setBorder(null);
		txtSubTotal.setBackground(SystemColor.menu);
		txtSubTotal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_txtSubTotal = new GridBagConstraints();
		gbc_txtSubTotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtSubTotal.fill = GridBagConstraints.BOTH;
		gbc_txtSubTotal.gridx = 10;
		gbc_txtSubTotal.gridy = 6;
		frame.getContentPane().add(txtSubTotal, gbc_txtSubTotal);
		txtSubTotal.setColumns(10);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtDistributor.getText().equals("") || table.getRowCount() <= 0) {
					JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
				} else {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut?", "Konfirmasi", 0);
					if (konfirm == 0)
						Simpan();
				}
			}
		});
		
		btnListReturnBrg = new JButton("List Return Brg");
		btnListReturnBrg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FListReturnPembelian.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnListReturnBrg.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_btnListReturnBrg = new GridBagConstraints();
		gbc_btnListReturnBrg.insets = new Insets(0, 0, 5, 5);
		gbc_btnListReturnBrg.gridx = 1;
		gbc_btnListReturnBrg.gridy = 7;
		frame.getContentPane().add(btnListReturnBrg, gbc_btnListReturnBrg);
		btnSimpan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.anchor = GridBagConstraints.EAST;
		gbc_btnSimpan.insets = new Insets(0, 0, 5, 5);
		gbc_btnSimpan.gridx = 10;
		gbc_btnSimpan.gridy = 7;
		frame.getContentPane().add(btnSimpan, gbc_btnSimpan);
	}

	private void DataBaru() {
		txtDistributor.setText(null);
		txtIdReturn.setText(null);
		txtKaryawan.setText(null);
		txtKaryawan.setText(FMain.user);
		txtIdReturn.setText(SetIdReturn());
		Calendar cal = Calendar.getInstance();
		datePicker.setDate(cal.getTime());
	}
	
	private String SetIdReturn() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String kode  = df.format(cal.getTime()) + "/";
		db.con();
		try {
			String query = "select count(id) as count from tb_return_pembelian";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				kode += rs.getInt("count") + 1;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return kode;
	}
	
	private void AturTabel() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(4);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(0);
	    
	    String[] columnNames = { "id", "Kode Barang", "Nama Barang", "Satuan", "Harga", "Jumlah", "Total", "Brg Rusak" };
	    model = new DefaultTableModel() {
	    	private static final long serialVersionUID = 1L;
	    	public boolean isCellEditable(int row, int column) {
	    		return false;
	    	}
	    };
	    model.setColumnIdentifiers(columnNames);
	    table.setModel(model);
	    table.setAutoResizeMode(0);
	    table.getColumnModel().getColumn(0).setPreferredWidth(50);
	    table.getColumnModel().getColumn(1).setPreferredWidth(200);
	    table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(4).setPreferredWidth(200);
	    table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(5).setPreferredWidth(100);
	    table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(6).setPreferredWidth(150);
	    table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(7).setPreferredWidth(100);
	    table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		table.setRowHeight(25);
	}
	
	private void Simpan() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		db.con();
		String[] supp = txtDistributor.getText().split(" - ", 2);
		try {
			String query = "insert into tb_return_pembelian values(?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtIdReturn.getText());
			ps.setString(2, df.format(datePicker.getDate()));
			ps.setString(3, supp[0]);
			ps.setString(4, txtKaryawan.getText());
			ps.setString(5, txtSubTotal.getText().replace(",", ""));
			ps.execute();
			
			for (int i = 0; i < table.getRowCount(); i++) {
				query = "insert into tb_detail_return_pembelian values(?,?,?,?,?,?)";
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtIdReturn.getText());
				ps.setString(2, table.getValueAt(i, 1).toString());
				ps.setString(3, table.getValueAt(i, 3).toString());
				ps.setString(4, table.getValueAt(i, 5).toString());
				ps.setString(5, table.getValueAt(i, 4).toString().replace(",", ""));
				ps.setString(6, table.getValueAt(i, 0).toString());
				ps.execute();
				
				query = "update tb_barang_rusak set jumlah = jumlah - ? where id = ?";
				ps = db.con.prepareStatement(query);
				ps.setDouble(1, Double.parseDouble(table.getValueAt(i, 5).toString()));
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.execute();
				
				double jmlBarangRusak = Double.parseDouble(table.getValueAt(i, 7).toString());
				double jmlReturn = Double.parseDouble(table.getValueAt(i, 5).toString());
				
				if (jmlBarangRusak - jmlReturn == 0){
					query = "delete from tb_barang_rusak where id = ?";
					ps = db.con.prepareStatement(query);
					ps.setString(1, table.getValueAt(i, 0).toString());
					ps.execute();
				}
			}
			
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
