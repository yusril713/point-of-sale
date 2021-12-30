package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FCariBarangRusak {

	JFrame frame;
	static FCariBarangRusak window;
	private JTextField txtKeyword;
	private Database db;
	private JXTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FCariBarangRusak();
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
	public FCariBarangRusak() {
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
				txtKeyword.setText("");
				txtKeyword.requestFocus();
				TampilBarang("");
			}
		});
		frame.setBounds(100, 100, 383, 368);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtKeyword = new JTextField();
		txtKeyword.setText("keyword");
		txtKeyword.setBounds(17, 20, 186, 26);
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
				TampilBarang(txtKeyword.getText());
			}
		});
		frame.getContentPane().add(txtKeyword);
		txtKeyword.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 52, 334, 233);
		frame.getContentPane().add(scrollPane);
		
		table = new JXTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menambahkan data tersebut?", "Konfirmasi", 0);
					if (konfirm == 0) {
						FTambahBarangRusak.window.frame.setEnabled(true);
						FTambahBarangRusak.txtKode.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
						FTambahBarangRusak.txtNama.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
						FTambahBarangRusak.lblSatuan_1.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
						FTambahBarangRusak.txtStok.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
						FTambahBarangRusak.lblSatuan_1.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
						window.frame.dispose();
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTambahBarangRusak.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnTutup.setBounds(251, 286, 97, 26);
		frame.getContentPane().add(btnTutup);
		
		JLabel lblklikxPada = new JLabel("*Klik 2x pada tabel untuk memilih brg");
		lblklikxPada.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblklikxPada.setBounds(17, 291, 186, 16);
		frame.getContentPane().add(lblklikxPada);
	}
	
	private void TampilBarang(String key) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		model.addColumn("Kode Brg");
		model.addColumn("Nama Brg");
		model.addColumn("Satuan");
		model.addColumn("Stok");
		
		db.con();
		try {
			String query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.stok "
					+ "from tb_barang join tb_detail_barang on tb_detail_barang.id_barang = tb_barang.id "
					+ "where tb_barang.id like ? or tb_barang.nama like ? "
					+ "order by tb_barang.nama";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("stok")
				});
			}
			
			rs.close();
			ps.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
