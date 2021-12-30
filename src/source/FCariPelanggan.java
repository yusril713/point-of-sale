package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FCariPelanggan {

	JFrame frame;
	static FCariPelanggan window;
	private JTextField txtKode;
	private JTable table;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FCariPelanggan();
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
	public FCariPelanggan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				db = new Database();
				txtKode.setText("");
				txtKode.requestFocus();
				TampilData(txtKode.getText());
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(100, 100, 354, 274);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKodeNama = new JLabel("Kode/Nama Plg");
		lblKodeNama.setBounds(10, 11, 122, 16);
		frame.getContentPane().add(lblKodeNama);
		
		txtKode = new JTextField();
		txtKode.setBorder(null);
		txtKode.setBackground(SystemColor.control);
		txtKode.setText("kode");
		txtKode.setBounds(142, 6, 197, 25);
		txtKode.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				TampilData(txtKode.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				TampilData(txtKode.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				TampilData(txtKode.getText());
			}});
		frame.getContentPane().add(txtKode);
		txtKode.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 331, 194);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					FTransaksi.window.frame.setEnabled(true);
					FTransaksi.txtKodePelanggan.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
					FTransaksi.txtNamaPelanggan.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
					window.frame.dispose();
				}
			}
		});
		table.setGridColor(Color.WHITE);
		scrollPane.setViewportView(table);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					FTransaksi.window.frame.setEnabled(true);
					FTransaksi.txtKodePelanggan.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
					FTransaksi.txtNamaPelanggan.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
					window.frame.dispose();
				}
			}
		});
		btnOk.setBounds(142, 237, 89, 27);
		frame.getContentPane().add(btnOk);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTransaksi.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(250, 237, 89, 27);
		frame.getContentPane().add(btnBatal);
	}

	private void TampilData(String key) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("ID Pelanggan");
		model.addColumn("Nama Pelanggan");
		model.addColumn("Toko");
		db.con();
		try {
			String query = "select * from tb_pelanggan where (id like ? or nama like ?) and not(id = 'UMUM')";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("nama_toko")
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
		} catch(Exception e) {
			System.out.println("error tampil data " + e);
		}
	}
}
