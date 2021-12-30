package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FDiskon {

	JFrame frame;
	static FDiskon window;
	private JTextField txtKode;
	private JComboBox<String> cmbSatuan;
	private JTextField txtHarga;
	private JTextField txtDiskon;
	private JTable table;
	private JComboBox<String> comboBox;
	private JList<String> list;
	private JScrollPane scrollPane;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FDiskon();
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
	public FDiskon() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Set Diskon");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				DataBaru();
				TampilDiskon();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 522, 434);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeNama = new JLabel("Kode / Nama Barang");
		lblKodeNama.setBounds(10, 11, 98, 16);
		frame.getContentPane().add(lblKodeNama);
		
		txtKode = new JTextField();
		txtKode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtKode.selectAll();
			}
		});
		txtKode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					list.requestFocus();
					list.setSelectedIndex(0);
				}
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					TampilSatuan(AmbilID(txtKode.getText()));
				}
			}
		});
		txtKode.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				CariBarang();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				CariBarang();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				CariBarang();
			}
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(125, 28, 273, 100);
		frame.getContentPane().add(scrollPane);
		
		list = new JList<>();
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtKode.setText(list.getSelectedValue().toString());
					scrollPane.setVisible(false);
					TampilSatuan(AmbilID(txtKode.getText()));
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ((arg0.getClickCount() == 2) && (!arg0.isConsumed())) {
					arg0.consume();
					txtKode.setText(list.getSelectedValue().toString());
					scrollPane.setVisible(false);
					TampilSatuan(AmbilID(txtKode.getText()));
				}
			}
		});
		scrollPane.setViewportView(list);
		txtKode.setText("kode");
		txtKode.setBounds(124, 6, 274, 26);
		frame.getContentPane().add(txtKode);
		txtKode.setColumns(10);
		
		JLabel lblSatuan = new JLabel("Satuan");
		lblSatuan.setBounds(10, 36, 46, 16);
		frame.getContentPane().add(lblSatuan);
		
		JLabel lblHarga = new JLabel("Harga");
		lblHarga.setBounds(255, 36, 46, 16);
		frame.getContentPane().add(lblHarga);
		
		txtHarga = new JTextField();
		txtHarga.setEditable(false);
		txtHarga.setText("harga");
		txtHarga.setBounds(313, 31, 86, 26);
		frame.getContentPane().add(txtHarga);
		txtHarga.setColumns(10);
		
		JButton btnHapusDiskon = new JButton("Hapus Diskon");
		btnHapusDiskon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus diskon tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					HapusDiskon(table.getValueAt(table.getSelectedRow(), 0).toString(), 
							table.getValueAt(table.getSelectedRow(), 2).toString());
					DataBaru();
					TampilDiskon();
				}
			}
		});
		btnHapusDiskon.setBounds(418, 131, 89, 26);
		frame.getContentPane().add(btnHapusDiskon);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan diskon tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (txtDiskon.getText().equals("") ||
							txtHarga.getText().equals("") ||
							txtKode.getText().equals("") ||
							cmbSatuan.getSelectedIndex() <= 0) {
						JOptionPane.showMessageDialog(null, "Lengkapi data!");
						txtKode.requestFocus();
					} else {
						SetDiskon();
						DataBaru();
						TampilDiskon();
						cmbSatuan.removeAllItems(); 
					}
				}
			}
		});
		btnSimpan.setBounds(418, 105, 89, 26);
		frame.getContentPane().add(btnSimpan);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 165, 496, 229);
		frame.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		cmbSatuan = new JComboBox<>();
		cmbSatuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbSatuan.getSelectedIndex() > 0) 
					txtHarga.setText(AmbilHarga(AmbilID(txtKode.getText()), cmbSatuan.getSelectedItem().toString()));				
			}
		});
		cmbSatuan.setBounds(125, 31, 92, 26);
		frame.getContentPane().add(cmbSatuan);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Jumlah Diskon", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 61, 269, 93);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Persen", "Harga"}));
		comboBox.setBounds(10, 38, 82, 26);
		panel.add(comboBox);
		
		txtDiskon = new JTextField();
		txtDiskon.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '')) {
		        	arg0.consume();
		        }
			}
		});
		txtDiskon.setText("diskon");
		txtDiskon.setBounds(102, 38, 157, 26);
		panel.add(txtDiskon);
		txtDiskon.setColumns(10);
	}
	
	private void DataBaru() {
		txtDiskon.setText("");
		txtHarga.setText("");
		txtKode.setText("");
	}
	
	private void TampilSatuan(String id) {
		cmbSatuan.removeAllItems();
		cmbSatuan.addItem("-PILIH-");
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cmbSatuan.addItem(rs.getString("satuan"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error tampil satuan " + e);
		}
	}
	
	private void TampilDiskon() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("satuan");
		model.addColumn("Harga Jual");
		model.addColumn("Diskon");
		model.addColumn("Harga Setelah diskon");
		db.con();
		try {
			String query = "select tb_barang.id, tb_barang.nama, "
					+ "tb_detail_barang.harga_jual, "
					+ "tb_diskon.satuan, tb_diskon.diskon, "
					+ "(tb_detail_barang.harga_jual - tb_diskon.diskon) "
					+ "as harga_setelah_diskon "
					+ "from tb_barang "
					+ "inner join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "inner join tb_diskon on tb_barang.id = tb_diskon.id_barang "
					+ "and tb_detail_barang.satuan = tb_diskon.satuan";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("satuan"),
						FMain.FormatAngka(rs.getInt("harga_jual")),
						FMain.FormatAngka(rs.getInt("diskon")),
						FMain.FormatAngka(rs.getInt("harga_setelah_diskon"))
				});
			}
			st.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(80);
			table.getColumnModel().getColumn(5).setPreferredWidth(80);
		} catch(Exception e) {
			System.out.println("error tampil diskon " + e);
		}
	}
	
	private String AmbilHarga(String id, String satuan) {
		db.con();
		try {
			String query = "select * from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return FMain.FormatAngka(rs.getInt("harga_jual"));
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil harga");
		}
		return "";
	}
	
	private void CariBarang () {
		Database db = new Database();
		db.con();
		try {
			String query = "select * from tb_barang where id like ? or nama like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + txtKode.getText() + "%");
			ps.setString(2, "%" + txtKode.getText() + "%");
			ResultSet rs = ps.executeQuery();
			
			int rowcount = 0;
			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst();
			}
			//System.out.println(id_jenis.get(comboBox.getSelectedIndex()));
			String[] hasil = new String[rowcount];
			if ((rowcount > 0) && (!txtKode.getText().equals(""))) {
				scrollPane.setVisible(true);
				int i = 0;
				while (rs.next()) {
					hasil[i] = rs.getString("id") + " - " + rs.getString("nama");
					i++;
				}
			} else {
				scrollPane.setVisible(false);
			}
			list.setListData(hasil);
			
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("ada error cari barang " + e);
		}
	}
	
	private String AmbilID(String teks) {
		return teks.substring(0, teks.indexOf(" "));
	}
	
	private void SetDiskon() {
		int diskon = 0;
		int harga = Integer.parseInt(txtHarga.getText().replace(",", ""));
		int jumlah_diskon = Integer.parseInt(txtDiskon.getText());
		switch (comboBox.getSelectedIndex()) {
		case 0:
			diskon = jumlah_diskon * harga / 100;
			System.out.println(harga);
			break;
		case 1:
			diskon = jumlah_diskon;
			break;
		default:
			break;
		}
		db.con();
		try {
			String query = "insert into tb_diskon values(?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, AmbilID(txtKode.getText()));
			ps.setString(2, cmbSatuan.getSelectedItem().toString());
			ps.setInt(3, diskon);
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error set diskon " + e);
		}
	}
	
	private void HapusDiskon(String id, String satuan) {
		db.con();
		try {
			String query = "delete from tb_diskon where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Diskon berhasil dihapus");
		} catch(Exception e) {
			System.out.println("error hapus diskon " + e);
		}
	}
}
