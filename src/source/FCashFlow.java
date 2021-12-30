package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DefaultEditorKit;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jdesktop.swingx.JXDatePicker;
import javax.swing.JSpinner;
import javax.swing.JTextPane;

public class FCashFlow {

	JFrame frame;
	static FCashFlow window;
	static boolean edit = false;
	private JXTextField txtKode;
	private JTextField txtJumlah;
	private JLabel lblPengeluaranRp;
	private JComboBox<String> comboBox;
	private JXDatePicker datePicker;
	private JSpinner spinner;
	private Database db;
	private Calendar cal = Calendar.getInstance();
	static String kode;
	private JTextPane textPane;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FCashFlow();
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
	public FCashFlow() {
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
					SetKode();
					frame.setTitle("Tambah Cashflow"); 
				}
				else {
					txtKode.setText(kode);
					TampilData();
					frame.setTitle("Edit Cashflow"); 
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FCashflowData.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 420, 341);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKode = new JLabel("Kode");
		lblKode.setBounds(17, 17, 52, 16);
		frame.getContentPane().add(lblKode);
		
		txtKode = new JXTextField();
		txtKode.setEditable(false);
		txtKode.setBounds(109, 12, 284, 26);
		frame.getContentPane().add(txtKode);
		
		JLabel lblJenis = new JLabel("Jenis");
		lblJenis.setBounds(17, 76, 52, 16);
		frame.getContentPane().add(lblJenis);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0) {
					lblPengeluaranRp.setText(comboBox.getSelectedItem().toString() + " Rp.");
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Pengeluaran", "Pemasukan"}));
		comboBox.setBounds(109, 71, 284, 26);
		frame.getContentPane().add(comboBox);
		
		JLabel lblKeterangan = new JLabel("Keterangan");
		lblKeterangan.setBounds(17, 104, 65, 16);
		frame.getContentPane().add(lblKeterangan);
		
		lblPengeluaranRp = new JLabel("Pengeluaran Rp");
		lblPengeluaranRp.setBounds(17, 237, 93, 16);
		frame.getContentPane().add(lblPengeluaranRp);
		
		txtJumlah = new JTextField();
		txtJumlah.addKeyListener(new KeyAdapter() {
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
						txtJumlah.setText(FMain.FormatAngka(Integer.valueOf(txtJumlah.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtJumlah.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	
						txtJumlah.setText("");
					}
				}
			}
		});
		txtJumlah.setText("jumlah");
		txtJumlah.setBounds(109, 232, 284, 26);
		frame.getContentPane().add(txtJumlah);
		txtJumlah.setColumns(10);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FCashflowData.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(296, 263, 97, 26);
		frame.getContentPane().add(btnBatal);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menyimpan data tersebut", "Konfirmasi " + comboBox.getSelectedItem().toString(), 0);
				if (konfirm == 0)
					if(!textPane.getText().equals(null) || 
							txtJumlah.getText().equals(null)) {
						if(!edit)
							Simpan();
						else 
							Update();
						FCashflowData.window.frame.setEnabled(true);
						FCashflowData.AturTabel();
						FCashflowData.TampilData("");
						window.frame.dispose();
					}
			}
		});
		btnSimpan.setBounds(197, 263, 97, 26);
		frame.getContentPane().add(btnSimpan);
		
		JLabel lblTanggal = new JLabel("Tanggal");
		lblTanggal.setBounds(17, 48, 52, 16);
		frame.getContentPane().add(lblTanggal);
		
		datePicker = new JXDatePicker();
		datePicker.setBounds(111, 44, 180, 24);
		frame.getContentPane().add(datePicker);
		
		spinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
		spinner.setEditor(timeEditor);
		spinner.setValue(new Date());
		spinner.setBounds(296, 43, 93, 26);
		frame.getContentPane().add(spinner);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(109, 104, 284, 121);
		frame.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setContentType("text/html");	
		textPane.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "<br/>\n");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(textPane.getText());
			}
		});
		btnNewButton.setBounds(13, 263, 97, 26);
		frame.getContentPane().add(btnNewButton);
	}
	
	private void DataBaru() {
		txtKode.setText(null);
		txtJumlah.setText(null);
		textPane.setText(null);
	
		datePicker.setDate(cal.getTime());
	}
	
	private void TampilData() {
		db.con();
		try {
			String query = "select * from tb_cashflow where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKode.getText());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				datePicker.setDate(rs.getDate("tanggal"));
				spinner.setValue(rs.getTime("jam"));
				textPane.setText(rs.getString("keterangan"));
				txtJumlah.setText("" + FMain.FormatAngka(rs.getInt("jumlah")));
			}
			
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void SetKode() {
		db.con();
		int count = 0;
		try {
			 String query = "select count(id) as count from tb_cashflow";
			 Statement st = db.con.createStatement();
			 ResultSet rs = st.executeQuery(query);
			 if (rs.next()) {
				 count = rs.getInt("count") + 1;
			 }
			 rs.close();
			 st.close();
			 db.con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		DateFormat df = new SimpleDateFormat("ddMMyy");
		txtKode.setText("CF/" + df.format(cal.getTime()) + "/" + count);
	}
	
	private void Simpan() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		db.con();
		try {
			String query = "insert into tb_cashflow values(?,?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKode.getText());
			ps.setString(2, df.format(datePicker.getDate()));
			ps.setString(3, new SimpleDateFormat("HH:mm:ss").format(spinner.getValue()));
			ps.setString(4, comboBox.getSelectedItem().toString());
			ps.setString(5, txtJumlah.getText().replace(",", ""));
			ps.setString(6, textPane.getText());
			ps.setString(7, FMain.user);
			ps.execute();
			ps.close();
			db.con.close();
			
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Update() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		db.con();
		try {
			String query = "update tb_cashflow set tanggal = ?, "
					+ "jam = ?, jenis = ?, jumlah = ?, keterangan = ?"
					+ "where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			
			ps.setString(1, df.format(datePicker.getDate()));
			ps.setString(2, new SimpleDateFormat("HH:mm:ss").format(spinner.getValue()));
			ps.setString(3, comboBox.getSelectedItem().toString());
			ps.setString(4, txtJumlah.getText().replace(",", ""));
			ps.setString(5, textPane.getText());
			ps.setString(6, txtKode.getText());
			ps.execute();
			ps.close();
			db.con.close();
			
			JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
