package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class FDetailReturnPenjualan {

	JFrame frame;
	static FDetailReturnPenjualan window;
	private JTextField txtKodeBarang;
	private JTextField txtNamaBarang;
	private JComboBox<String> cmbSatuan;
	private JTextField txtJumlah;
	private JLabel lblSatuan_1;
	private JRadioButton rdbtnTidakLayakUntuk;
	private JRadioButton rdbtnMasihLayakDijual;
	private ButtonGroup group;
	private Database db;
	
	static boolean edit = false;
	static String key = null;
	private JTextPane txtpnKeterangan;
	private JTextField txtHarga;
	static int row = 0;
	static String kode = null, nama = null, satuan = null, kelayakan = null, keterangan = null;
	static int harga = 0, jumlah = 0, total = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FDetailReturnPenjualan();
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
	public FDetailReturnPenjualan() {
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
				if (!edit) {
					DataBaru(); 
				} else {
					txtKodeBarang.setText(kode);
					txtNamaBarang.setText(nama);
					
					AmbilSatuan(kode);
					cmbSatuan.setSelectedItem(satuan);
					txtHarga.setText(FMain.FormatAngka(harga));
					txtJumlah.setText("" + jumlah);
					txtpnKeterangan.setText(keterangan);
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FReturnPenjualan.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 330, 381);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeBrg = new JLabel("Kode Brg");
		lblKodeBrg.setBounds(6, 19, 52, 16);
		frame.getContentPane().add(lblKodeBrg);
		
		JLabel lblNamaBrg = new JLabel("Nama Brg");
		lblNamaBrg.setBounds(6, 47, 76, 16);
		frame.getContentPane().add(lblNamaBrg);
		
		txtKodeBarang = new JTextField();
		txtKodeBarang.setText("kode barang");
		txtKodeBarang.setBounds(89, 14, 213, 26);
		txtKodeBarang.setEditable(false);
		frame.getContentPane().add(txtKodeBarang);
		txtKodeBarang.setColumns(10);
		
		txtNamaBarang = new JTextField();
		txtNamaBarang.setText("nama barang");
		txtNamaBarang.setBounds(89, 42, 213, 26);
		txtNamaBarang.setEditable(false);
		frame.getContentPane().add(txtNamaBarang);
		txtNamaBarang.setColumns(10);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(6, 75, 52, 16);
		frame.getContentPane().add(lblSatuan);
		
		cmbSatuan = new JComboBox<String>();
		cmbSatuan.setBounds(89, 70, 213, 26);
		frame.getContentPane().add(cmbSatuan);
		
		JLabel lblJumlah = new JLabel("Jumlah");
		lblJumlah.setBounds(6, 103, 52, 16);
		frame.getContentPane().add(lblJumlah);
		
		txtJumlah = new JTextField();
		txtJumlah.setText("jumlah");
		txtJumlah.setBounds(89, 98, 98, 26);
		frame.getContentPane().add(txtJumlah);
		txtJumlah.setColumns(10);
		
		JLabel lblKelayakan = new JLabel("Kelayakan");
		lblKelayakan.setBounds(6, 159, 63, 16);
		frame.getContentPane().add(lblKelayakan);
		
		rdbtnMasihLayakDijual = new JRadioButton("Masih layak untuk dijual");
		rdbtnMasihLayakDijual.setActionCommand("Layak dijual");
		rdbtnMasihLayakDijual.setBounds(89, 157, 213, 20);
		frame.getContentPane().add(rdbtnMasihLayakDijual);
		
		rdbtnTidakLayakUntuk = new JRadioButton("Tidak layak untuk dijual");
		rdbtnTidakLayakUntuk.setActionCommand("Tidak layak dijual");
		rdbtnTidakLayakUntuk.setBounds(89, 180, 213, 20);
		frame.getContentPane().add(rdbtnTidakLayakUntuk);
		
		group = new ButtonGroup();
		group.add(rdbtnMasihLayakDijual);
		group.add(rdbtnTidakLayakUntuk);
		
		JLabel lblKeterangan = new JLabel("Keterangan");
		lblKeterangan.setBounds(6, 207, 63, 16);
		frame.getContentPane().add(lblKeterangan);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(89, 205, 214, 88);
		frame.getContentPane().add(scrollPane);
		
		txtpnKeterangan = new JTextPane();
		scrollPane.setViewportView(txtpnKeterangan);
		txtpnKeterangan.setText("keterangan");
		
		lblSatuan_1 = new JLabel("satuan");
		lblSatuan_1.setBounds(199, 103, 52, 16);
		frame.getContentPane().add(lblSatuan_1);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnPenjualan.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(205, 305, 97, 26);
		frame.getContentPane().add(btnBatal);
		
		JButton btnTambahkan = new JButton("Tambahkan");
		btnTambahkan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TambahkanKeList();
			}
		});
		btnTambahkan.setBounds(99, 305, 97, 26);
		frame.getContentPane().add(btnTambahkan);
		
		JLabel lblHarga = new JLabel("Harga");
		lblHarga.setBounds(6, 131, 52, 16);
		frame.getContentPane().add(lblHarga);
		
		txtHarga = new JTextField();
		txtHarga.setText("harga");
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
		txtHarga.setBounds(89, 126, 213, 26);
		frame.getContentPane().add(txtHarga);
		txtHarga.setColumns(10);
	}
	
	private void AmbilSatuan(String id) {
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			cmbSatuan.removeAllItems();
			while (rs.next()) {
				cmbSatuan.addItem(rs.getString("satuan"));
			}
			rs.close();
			ps.close();
			db.con.close();
			
			lblSatuan_1.setText(cmbSatuan.getSelectedItem().toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		txtHarga.setText(FMain.FormatAngka(AmbilHarga(id, cmbSatuan.getSelectedItem().toString())));
	}
	
	private void DataBaru() {
		db.con();
		String kode = null, nama = null;
		try {
			String query = "select * from tb_barang where id = ? or nama = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, key);
			ps.setString(2, key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				kode = rs.getString("id");
				nama = rs.getString("nama");
			} 
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		txtKodeBarang.setText(kode);
		txtNamaBarang.setText(nama);
		txtJumlah.setText("1");
		lblSatuan_1.setText("");
		txtpnKeterangan.setText(null);
		
		txtJumlah.requestFocus();
		txtJumlah.selectAll();
		
		AmbilSatuan(kode);
	}
	
	private int AmbilHarga(String id, String satuan) {
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return harga;
	}
	
	private void TambahkanKeList() {
		if (!edit) {
			boolean cek = false;
			for (int i = 0; i < FReturnPenjualan.model.getRowCount(); i++) {
				if (txtKodeBarang.getText().equals(FReturnPenjualan.model.getValueAt(i, 0).toString())) {
					cek = true;
					break;
				} 	
			}
			
			if (!cek) {
				FReturnPenjualan.model.addRow(new Object[] {
						txtKodeBarang.getText(),
						txtNamaBarang.getText(),
						cmbSatuan.getSelectedItem().toString(),
						txtHarga.getText(),
						txtJumlah.getText(),
						FMain.FormatAngka((int) (Integer.parseInt(txtHarga.getText().replace(",", "")) * Double.parseDouble(txtJumlah.getText()))),
						group.getSelection().getActionCommand(),
						txtpnKeterangan.getText()
				});
			} else 
				JOptionPane.showMessageDialog(null, "Data barang telah ada!!!");
		} else {
			FReturnPenjualan.table.setValueAt(cmbSatuan.getSelectedItem().toString(), row, 2);
			FReturnPenjualan.table.setValueAt(txtHarga.getText(), row, 3);
			FReturnPenjualan.table.setValueAt(txtJumlah.getText(), row, 4);
			FReturnPenjualan.table.setValueAt(FMain.FormatAngka((int) (Integer.parseInt(txtHarga.getText().replace(",", "")) * Double.parseDouble(txtJumlah.getText()))), row, 5);
			FReturnPenjualan.table.setValueAt(group.getSelection().getActionCommand(), row, 6);
			FReturnPenjualan.table.setValueAt(txtpnKeterangan.getText(), row, 7);
		}
		
		int subtotal = 0;
		for (int i = 0; i < FReturnPenjualan.table.getRowCount(); i++) {
			int total = Integer.parseInt(FReturnPenjualan.table.getValueAt(i, 5).toString().replace(",", ""));
			subtotal += total;
		}
		
		FReturnPenjualan.window.frame.setEnabled(true);
		FReturnPenjualan.txtSubTotal.setText(FMain.FormatAngka(subtotal));
		FReturnPenjualan.txtKeyword.setText("");
		FReturnPenjualan.txtKeyword.requestFocus();
		window.frame.dispose();
	}
}
