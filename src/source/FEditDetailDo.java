package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FEditDetailDo {

	JFrame frame;
	static FEditDetailDo window;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtSatuan;
	private JTextField txtJumlahDo;
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
					window = new FEditDetailDo();
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
	public FEditDetailDo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 366, 221);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID Barang");
		lblNewLabel.setBounds(6, 9, 52, 16);
		frame.getContentPane().add(lblNewLabel);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setColumns(10);
		txtId.setBounds(152, 6, 192, 22);
		frame.getContentPane().add(txtId);
		
		txtNama = new JTextField();
		txtNama.setEditable(false);
		txtNama.setColumns(10);
		txtNama.setBounds(152, 34, 192, 22);
		frame.getContentPane().add(txtNama);
		
		JLabel lblNewLabel_1 = new JLabel("Nama Barang");
		lblNewLabel_1.setBounds(6, 37, 77, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Satuan");
		lblNewLabel_2.setBounds(6, 65, 52, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		txtSatuan = new JTextField();
		txtSatuan.setEditable(false);
		txtSatuan.setColumns(10);
		txtSatuan.setBounds(152, 62, 192, 22);
		frame.getContentPane().add(txtSatuan);
		
		txtJumlahDo = new JTextField();
		txtJumlahDo.setEditable(false);
		txtJumlahDo.setColumns(10);
		txtJumlahDo.setBounds(152, 90, 192, 22);
		frame.getContentPane().add(txtJumlahDo);
		
		txtAmbil = new JTextField();
		txtAmbil.setColumns(10);
		txtAmbil.setBounds(152, 118, 192, 22);
		frame.getContentPane().add(txtAmbil);
		
		JLabel lblNewLabel_4 = new JLabel("Jumlah yg akan diambil");
		lblNewLabel_4.setBounds(6, 121, 125, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("Jumlah DO");
		lblNewLabel_3.setBounds(6, 93, 77, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.setBounds(152, 152, 93, 22);
		frame.getContentPane().add(btnSimpan);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.setBounds(251, 152, 93, 22);
		frame.getContentPane().add(btnBatal);
	}

}
