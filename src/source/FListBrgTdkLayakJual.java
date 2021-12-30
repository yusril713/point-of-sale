package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FListBrgTdkLayakJual {

	JFrame frame;
	static FListBrgTdkLayakJual window;
	static JTextField txtKeyword;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtSatuan;
	private JTextField txtHarga;
	private JTextField txtJumlah;
	private Database db;
	static JXTable table;
	static boolean edit = false;
	private JTextField txtIdr;
	static String id = null, idBarang = null, nama = null, satuan = null;
	static double jumlah = 0, brgRusak = 0;
	static int harga = 0, row = 0;
	private JTextField txtBarangRusak;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FListBrgTdkLayakJual();
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
	public FListBrgTdkLayakJual() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				if (!edit) {
					DataBaru();
					AmbilData("");
				} else {
					table.setEnabled(false);
					txtKeyword.setEnabled(false);
					txtIdr.setText(id);
					txtId.setText(idBarang);
					txtNama.setText(nama);
					txtSatuan.setText(satuan);
					txtBarangRusak.setText("" + brgRusak);
					txtHarga.setText(FMain.FormatAngka(harga));
					txtJumlah.setText("" + jumlah);
				}
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FReturnPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 539, 302);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtKeyword = new JTextField();
		txtKeyword.setText("keyword");
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
				AmbilData(txtKeyword.getText());
			}
		});
		txtKeyword.setBounds(19, 17, 250, 26);
		frame.getContentPane().add(txtKeyword);
		txtKeyword.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 46, 250, 209);
		frame.getContentPane().add(scrollPane);
		
		table = new JXTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					txtIdr.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
					txtJumlah.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
					txtBarangRusak.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
					txtId.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
					txtNama.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
					txtSatuan.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
					txtHarga.setText(FMain.FormatAngka(AmbilHargaJual(
							table.getValueAt(table.getSelectedRow(), 1).toString(), 
							table.getValueAt(table.getSelectedRow(), 3).toString())));
					txtJumlah.requestFocus();
					txtJumlah.selectAll();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FReturnPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnTutup.setBounds(412, 264, 97, 26);
		frame.getContentPane().add(btnTutup);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Detail Brg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(281, 17, 228, 235);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblIdBrg = new JLabel("Id Brg");
		lblIdBrg.setBounds(6, 65, 52, 16);
		panel.add(lblIdBrg);
		
		JLabel lblNamaBrg = new JLabel("Nama Brg");
		lblNamaBrg.setBounds(6, 93, 65, 16);
		panel.add(lblNamaBrg);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(6, 121, 52, 16);
		panel.add(lblSatuan);
		
		JLabel lblHarga = new JLabel("Harga");
		lblHarga.setBounds(6, 175, 52, 16);
		panel.add(lblHarga);
		
		JLabel lblJumlah = new JLabel("Jumlah");
		lblJumlah.setBounds(6, 203, 52, 16);
		panel.add(lblJumlah);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("id");
		txtId.setBounds(70, 60, 152, 26);
		panel.add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setText("nama");
		txtNama.setBounds(70, 88, 152, 26);
		panel.add(txtNama);
		txtNama.setColumns(10);
		
		txtSatuan = new JTextField();
		txtSatuan.setEditable(false);
		txtSatuan.setText("satuan");
		txtSatuan.setBounds(70, 116, 152, 26);
		panel.add(txtSatuan);
		txtSatuan.setColumns(10);
		
		txtHarga = new JTextField();
		txtHarga.setText("harga");
		txtHarga.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtHarga.selectAll();
			}
		});
		txtHarga.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '')) {
		        	arg0.consume();
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() != 37) && (e.getKeyCode() != 39)) {
					try {
						txtHarga.setText(FMain.FormatAngka(Integer.valueOf(txtHarga.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtHarga.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	txtHarga.setText("");
					}
				}
			}
		});
		txtHarga.setBounds(70, 170, 152, 26);
		panel.add(txtHarga);
		txtHarga.setColumns(10);
		
		txtJumlah = new JTextField();
		txtJumlah.setText("jumlah");
		txtJumlah.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtJumlah.selectAll();
			}
		});
		txtJumlah.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '') && (c != '.')) {
		        	arg0.consume();
		        }
			}
		});
		txtJumlah.setBounds(70, 198, 152, 26);
		panel.add(txtJumlah);
		txtJumlah.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(6, 37, 52, 16);
		panel.add(lblId);
		
		txtIdr = new JTextField();
		txtIdr.setText("idr");
		txtIdr.setEditable(false);
		txtIdr.setBounds(70, 32, 152, 26);
		panel.add(txtIdr);
		txtIdr.setColumns(10);
		
		JLabel lblBrgRusak = new JLabel("Brg Rusak");
		lblBrgRusak.setBounds(6, 149, 52, 16);
		panel.add(lblBrgRusak);
		
		txtBarangRusak = new JTextField();
		txtBarangRusak.setEditable(false);
		txtBarangRusak.setText("barang rusak");
		txtBarangRusak.setBounds(70, 144, 152, 26);
		panel.add(txtBarangRusak);
		txtBarangRusak.setColumns(10);
		
		JButton btnTambah = new JButton("Tambahkan");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtHarga.getText().equals("") && !txtJumlah.getText().equals("") && !txtIdr.getText().equals("")) {
					Tambah();
				} else {
					JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu...");
				}
			}
		});
		btnTambah.setBounds(315, 264, 97, 26);
		frame.getContentPane().add(btnTambah);
	}
	
	private void DataBaru() {
		txtHarga.setText(null);
		txtId.setText(null);
		txtJumlah.setText(null);
		txtKeyword.setText("");
		txtNama.setText(null);
		txtSatuan.setText(null);
		txtBarangRusak.setText(null);
	}
	
	private void AmbilData(String key) {
		DefaultTableModel model = new DefaultTableModel(){
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
	
	private int AmbilHargaJual(String id, String satuan) {
		int harga = 0;
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				harga = rs.getInt("harga_jual"); 
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return harga;
	}
	
	private void Tambah() {
		if(!edit) {
			boolean cek = false;
			for (int i = 0; i < FReturnPembelian.table.getRowCount(); i++) {
				if (txtIdr.getText().equals(FReturnPembelian.table.getValueAt(i, 0).toString())) {
					cek = true;
					break;
				}
			}
			
			if (!cek) {
				if (Double.parseDouble(txtJumlah.getText()) > Double.parseDouble(txtBarangRusak.getText())) {
					JOptionPane.showMessageDialog(null, "Jumlah return melebihi jumlah brg yang rusak...");
				} else {
					FReturnPembelian.model.addRow(new Object[] {
							txtIdr.getText(),
							txtId.getText(),
							txtNama.getText(),
							txtSatuan.getText(),
							txtHarga.getText(),
							txtJumlah.getText(),
							FMain.FormatAngka((int) (Double.parseDouble(txtHarga.getText().replace(",", "")) * Double.parseDouble(txtJumlah.getText()))),
							txtBarangRusak.getText()
					});
				}
			} else 
				JOptionPane.showMessageDialog(null, "Barang sudah ada pada list return...");
		} else {
			if (Double.parseDouble(txtJumlah.getText()) > Double.parseDouble(txtBarangRusak.getText())) {
				JOptionPane.showMessageDialog(null, "Jumlah return melebihi jumlah brg yang rusak...");
			} else {
				FReturnPembelian.table.setValueAt(txtHarga.getText(), row, 4);
				FReturnPembelian.table.setValueAt(txtJumlah.getText(), row, 5);
				FReturnPembelian.table.setValueAt(FMain.FormatAngka((int) (Double.parseDouble(txtHarga.getText().replace(",", "")) * Double.parseDouble(txtJumlah.getText()))), row, 6);
			}
		}
		
		int subtotal = 0;
		for (int i = 0; i < FReturnPembelian.table.getRowCount(); i++) {
			subtotal += Integer.parseInt(FReturnPembelian.table.getValueAt(i, 6).toString().replace(",", ""));
		}
		FReturnPembelian.window.frame.setEnabled(true);
		FReturnPembelian.txtSubTotal.setText(FMain.FormatAngka(subtotal));
		window.frame.dispose();
	}
}
