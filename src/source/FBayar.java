package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FBayar {

	JFrame frame;
	static FBayar window;
	private JTextField txtNoFaktur;
	private JTextField txtTotalBayar;
	private Database db;
	static boolean hutang = false;
	static String noFaktur = "";
	static String sisaHutang = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FBayar();
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
	public FBayar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Pembayaran");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				txtNoFaktur.setText(noFaktur);
				txtTotalBayar.setText(sisaHutang);
				txtTotalBayar.requestFocus();
				txtTotalBayar.selectAll();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FPiutang.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 310, 142);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNoFaktur = new JLabel("No. Faktur");
		lblNoFaktur.setBounds(10, 11, 93, 14);
		frame.getContentPane().add(lblNoFaktur);
		
		JLabel lblTotalPembayaran = new JLabel("Total Pembayaran");
		lblTotalPembayaran.setBounds(10, 40, 93, 14);
		frame.getContentPane().add(lblTotalPembayaran);
		
		txtNoFaktur = new JTextField();
		txtNoFaktur.setEditable(false);
		txtNoFaktur.setText("no faktur");
		txtNoFaktur.setBounds(115, 5, 179, 27);
		frame.getContentPane().add(txtNoFaktur);
		txtNoFaktur.setColumns(10);
		
		txtTotalBayar = new JTextField();
		txtTotalBayar.addKeyListener(new KeyAdapter() {
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
						txtTotalBayar.setText(FMain.FormatAngka(Integer.valueOf(txtTotalBayar.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtTotalBayar.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	txtTotalBayar.setText("");
					}
				}
			}
		});
		txtTotalBayar.setText("total bayar");
		txtTotalBayar.setBounds(115, 34, 179, 27);
		frame.getContentPane().add(txtTotalBayar);
		txtTotalBayar.setColumns(10);
		
		JButton btnBayar = new JButton("Bayar");
		btnBayar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan pembayaran tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					SimpanPembayaran();
					if (!hutang) {
						FPiutang.TampilDataPiutang("");
						FPiutang.TampilDetailPembayaran(noFaktur);
					} else {
						FPiutang.TampilHutang("");
					}
					FPiutang.window.frame.setEnabled(true);
					window.frame.dispose();
				}
			}
		});
		btnBayar.setBounds(115, 73, 89, 27);
		frame.getContentPane().add(btnBayar);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FPiutang.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(205, 73, 89, 27);
		frame.getContentPane().add(btnBatal);
	}
	
	private void SimpanPembayaran() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		db.con();
		try {
			String query = "insert into tb_detail_hutang(no_faktur, tgl_bayar, jml_bayar) "
					+ "values(?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNoFaktur.getText());
			ps.setString(2, df.format(cal.getTime()));
			ps.setString(3, txtTotalBayar.getText().replace(",", ""));
			ps.execute();
			
			query = "update tb_hutang set sisa_hutang = sisa_hutang - ? "
					+ "where no_faktur = ?";
			ps = db.con.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(txtTotalBayar.getText().replace(",", "")));
			ps.setString(2, txtNoFaktur.getText());
			ps.execute();
			ps.close();
			db.con.close();
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch(Exception e) {
			System.out.println("error simpan " + e);
		}
	}
}
