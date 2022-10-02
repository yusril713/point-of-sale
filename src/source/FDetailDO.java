package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FDetailDO {

	JFrame frame;
	static FDetailDO window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtSatuan;
	private JTextField txtJumlahDO;
	private JTextField txtAmbil;
	static String noFaktur;
	static String idBarang;
	static String namaBarang;
	static String satuan;
	static double jumlahDo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FDetailDO();
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
	public FDetailDO() {
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
				txtId.setText(idBarang);
				txtNama.setText(namaBarang);
				txtSatuan.setText(satuan);
				txtJumlahDO.setText("" + jumlahDo);
				txtAmbil.requestFocus();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FDO.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 384, 238);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID Barang");
		lblNewLabel.setBounds(17, 21, 52, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nama Barang");
		lblNewLabel_1.setBounds(17, 49, 77, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Satuan");
		lblNewLabel_2.setBounds(17, 77, 52, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Jumlah DO");
		lblNewLabel_3.setBounds(17, 105, 77, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Jumlah yg akan diambil");
		lblNewLabel_4.setBounds(17, 133, 125, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(163, 18, 192, 22);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setColumns(10);
		txtNama.setBounds(163, 46, 192, 22);
		frame.getContentPane().add(txtNama);
		
		txtSatuan = new JTextField();
		txtSatuan.setEditable(false);
		txtSatuan.setColumns(10);
		txtSatuan.setBounds(163, 74, 192, 22);
		frame.getContentPane().add(txtSatuan);
		
		txtJumlahDO = new JTextField();
		txtJumlahDO.setEditable(false);
		txtJumlahDO.setColumns(10);
		txtJumlahDO.setBounds(163, 102, 192, 22);
		frame.getContentPane().add(txtJumlahDO);
		
		txtAmbil = new JTextField();
		txtAmbil.setColumns(10);
		txtAmbil.setBounds(163, 130, 192, 22);
		frame.getContentPane().add(txtAmbil);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (!txtAmbil.getText().equals("")) { 
						if (Double.parseDouble(txtAmbil.getText()) <= jumlahDo) {
							Simpan();
							UpdateDo();
							if (Double.parseDouble(txtAmbil.getText()) == jumlahDo) {
								UpdateStatusDo();
							}
							FDO.window.frame.setEnabled(true);
							window.frame.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu....");
					}
				}
			}
		});
		btnSimpan.setBounds(163, 162, 93, 22);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.setBounds(262, 162, 93, 22);
		frame.getContentPane().add(btnBatal);
	}
	
	private void Simpan() {
		Database db = new Database();
		db.con();
		
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String query = "insert into tb_rincian_do (no_faktur, tanggal, id_barang, satuan, jumlah) values(?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ps.setString(2, df.format(cal.getTime()));
			ps.setString(3, txtId.getText());
			ps.setString(4, txtSatuan.getText());
			ps.setString(5, txtAmbil.getText());
			ps.execute();
			
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void UpdateDo() {
		Database db = new Database();
		db.con();
		try {
			String query = "update tb_do set jumlah_do = jumlah_do - ? where no_faktur = ? and id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(txtAmbil.getText()));
			ps.setString(2, noFaktur);
			ps.setString(3, txtId.getText());
			ps.setString(4, txtSatuan.getText());
			ps.execute();
			
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String query = "update tb_detail_barang set stok = stok - ? where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setDouble(1, Double.parseDouble(txtAmbil.getText()));
			ps.setString(2, txtId.getText());
			ps.setString(3, txtSatuan.getText());
			ps.execute();
			
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void UpdateStatusDo() {
		Database db = new Database();
		db.con();
		
		boolean cek = false;
		try {
			String query = "select * from tb_do where no_faktur = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, noFaktur);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getDouble("jumlah_do") > 0) {
					cek = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!cek) {
			try {
				String query = "update tb_do set status = '1' where no_faktur = ? and id_barang = ? and satuan = ?";
				PreparedStatement ps = db.con.prepareStatement(query);
				ps.setString(1, noFaktur);
				ps.setString(2, txtId.getText());
				ps.setString(3, txtSatuan.getText());
				ps.execute();
				
				ps.close();
				db.con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
