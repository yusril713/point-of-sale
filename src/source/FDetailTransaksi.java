package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FDetailTransaksi {

	JFrame frame;
	static FDetailTransaksi window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtStok;
	private JTextField txtHarga;
	private JTextField txtQty;
	private Database db;
	static String key = null;
	static boolean edit = false;
	static String id = null;
	static String nama = null;
	static String satuan = null;
	static String qty = null;
	static String harga = null;
	static int row = 0;
	private JComboBox<String> comboBox;
	private JTextField txtDiskon;
	int harga_beli = 0;
	private SwingWorker<String, Object> sw;
	private int subtot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FDetailTransaksi();
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
	public FDetailTransaksi() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Detail Transaksi");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				if (!edit) {
					AmbilIdNamaBarang();
					TampilSatuan(txtId.getText());
				} else {
					txtId.setText(id);
					txtNama.setText(nama);
					TampilSatuan(txtId.getText());
					txtQty.setText(qty);
					txtHarga.setText(harga);
					txtQty.requestFocus();
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FTransaksi.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 341, 251);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		lblKodeBarang.setBounds(10, 11, 65, 16);
		frame.getContentPane().add(lblKodeBarang);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setBounds(10, 36, 65, 16);
		frame.getContentPane().add(lblNamaBarang);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(10, 61, 46, 16);
		frame.getContentPane().add(lblSatuan);
		
		JLabel lblHarga = new JLabel("Harga");
		lblHarga.setBounds(10, 136, 46, 16);
		frame.getContentPane().add(lblHarga);
		
		JLabel lblSisaStok = new JLabel("Sisa Stok");
		lblSisaStok.setBounds(10, 86, 46, 16);
		frame.getContentPane().add(lblSisaStok);
		
		JLabel lblQty = new JLabel("Qty");
		lblQty.setBounds(10, 161, 46, 16);
		frame.getContentPane().add(lblQty);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("id");
		txtId.setBounds(109, 6, 213, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setText("nama");
		txtNama.setBounds(109, 31, 213, 26);
		frame.getContentPane().add(txtNama);
		txtNama.setColumns(10);
		
		txtStok = new JTextField();
		txtStok.setEditable(false);
		txtStok.setBounds(109, 81, 213, 26);
		frame.getContentPane().add(txtStok);
		txtStok.setColumns(10);
		
		txtHarga = new JTextField();
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
		txtHarga.setBounds(109, 131, 213, 26);
		frame.getContentPane().add(txtHarga);
		txtHarga.setColumns(10);
		
		txtQty = new JTextField();
		txtQty.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtQty.selectAll();
			}
		});
		txtQty.addKeyListener(new KeyAdapter() {
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
		txtQty.setBounds(109, 156, 213, 26);
		frame.getContentPane().add(txtQty);
		txtQty.setColumns(10);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTransaksi.window.frame.setEnabled(true);
				FTransaksi.txtKodeNama.setText(null);
				FTransaksi.txtKodeNama.requestFocus();
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(222, 189, 100, 26);
		frame.getContentPane().add(btnBatal);
		
		JButton btnTambahkan = new JButton("Tambahkan");
		btnTambahkan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Double.parseDouble(txtQty.getText()) <= 
						Double.parseDouble(txtStok.getText())) {
					TambahkanKeList();
					FTransaksi.window.frame.setEnabled(true);
					FTransaksi.txtKodeNama.setText("");
					FTransaksi.txtKodeNama.requestFocus();
					window.frame.dispose();
				} else 
					JOptionPane.showMessageDialog(null, "Stok tidak mencukupi."
							+ "\nStok saat ini sebanyak: " + txtStok.getText());
			}
		});
		btnTambahkan.setBounds(109, 189, 100, 26);
		frame.getContentPane().add(btnTambahkan);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0) {
					AmbilDetailBarang(txtId.getText(), comboBox.getSelectedItem().toString());
					txtDiskon.setText(FMain.FormatAngka(AmbilDiskon(txtId.getText(), comboBox.getSelectedItem().toString())));
					txtQty.requestFocus();
					txtHarga.selectAll();
					txtQty.setText("1");
				}
			}
		});
		comboBox.setBounds(109, 56, 213, 26);
		frame.getContentPane().add(comboBox);
		
		JLabel lblDiskon = new JLabel("Diskon");
		lblDiskon.setBounds(10, 111, 46, 16);
		frame.getContentPane().add(lblDiskon);
		
		txtDiskon = new JTextField();
		txtDiskon.setEditable(false);
		txtDiskon.setBounds(109, 106, 213, 26);
		frame.getContentPane().add(txtDiskon);
		txtDiskon.setColumns(10);
	}
	
	private void AmbilIdNamaBarang() {
		db.con();
		try {
			String query = "select * from tb_barang where id = ? or nama = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, key);
			ps.setString(2, key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtId.setText(rs.getString("id"));
				txtNama.setText(rs.getString("nama"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil detail barang");
		}
	}
	
	private void TampilSatuan(String id) {
		db.con();
		comboBox.removeAllItems();
		try {
			String query = "select satuan from tb_detail_barang where id_barang = ? order by harga_jual";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				comboBox.addItem(rs.getString("satuan"));
			}
			rs.close();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil satuan " + e);
		}
	}
	
	private void AmbilDetailBarang(String id, String satuan) {
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				txtHarga.setText(FMain.FormatAngka(rs.getInt("harga_jual")));
				txtStok.setText(rs.getString("stok"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil detail barang " + e);
		}
	}
	
	private int AmbilDiskon(String id, String satuan) {
		int diskon = 0;
		db.con();
		try {
			String query = "select * from tb_diskon where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				diskon = rs.getInt("diskon");
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil diskon " + e);
		}
		return diskon;
	}
	
	private void TambahkanKeList() {
//		"Kode Barang", "Nama Barang", "Satuan", "Harga", "Qty", "Total"
		int harga = Integer.parseInt(txtHarga.getText().replace(",", ""));
		int diskon = Integer.parseInt(txtDiskon.getText().replace(",", ""));
		double qty = Double.parseDouble(txtQty.getText());
		harga = harga - diskon;
		if (!edit) {
			boolean cek = false;
			for (int i = 0; i < FTransaksi.model.getRowCount(); i++) {
				if (txtId.getText().equals(FTransaksi.model.getValueAt(i, 0).toString())) {
					cek = true;
					break;
				}
			}
			if (!cek) {
				FTransaksi.model.addRow(new Object[] {
						txtId.getText(),
						txtNama.getText(),
						comboBox.getSelectedItem().toString(),
						FMain.FormatAngka(harga),
						txtQty.getText(),
						FMain.FormatAngka((int) (harga * qty))
				}); 
			} else 
				JOptionPane.showMessageDialog(null, "Data barang telah ada!!!");
		} else {
			FTransaksi.table.setValueAt(comboBox.getSelectedItem().toString(), row, 2);
			FTransaksi.table.setValueAt(FMain.FormatAngka(harga), row, 3);
			FTransaksi.table.setValueAt(txtQty.getText(), row, 4);
			FTransaksi.table.setValueAt(FMain.FormatAngka((int)(harga * qty)), row, 5);
		}
		
		
		
		subtot = 0;
		for (int i = 0; i < FTransaksi.model.getRowCount(); i++) 
			subtot += Integer.parseInt(FTransaksi.model.getValueAt(i, 5).toString().replace(",", ""));
		
		sw = new SwingWorker<String, Object>() {
			@Override
			protected String doInBackground() throws Exception {
				if (FMain.d.portFound) {
					FMain.d.PrintFirstLine(txtQty.getText() + " " + txtNama.getText());
					FMain.d.PrintSecondLine(txtHarga.getText());
					FMain.d.PrintThirdLine("" + FMain.FormatAngka(subtot));
				}
				return null;
			}
		};
		Thread t1 = new Thread(sw);
		t1.setPriority(Thread.MAX_PRIORITY);
		t1.start();
		
		FTransaksi.txtSubTotal.setText(FMain.FormatAngka(subtot));
	}
}
