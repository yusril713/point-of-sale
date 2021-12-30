package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class FReturnCariPelanggan {

	JFrame frame;
	static FReturnCariPelanggan window;
	private JTextField txtKeyword;
	private JTable table;
	private JScrollPane scrollPane;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FReturnCariPelanggan();
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
	public FReturnCariPelanggan() {
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
				txtKeyword.setText(null);
				txtKeyword.requestFocus();
				CariPelanggan("");
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				FReturnPenjualan.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 287, 300);
		frame.setLocationRelativeTo(null);
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
				CariPelanggan(txtKeyword.getText());
			}
		});
		txtKeyword.setBounds(6, 6, 259, 26);
		frame.getContentPane().add(txtKeyword);
		txtKeyword.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 35, 259, 192);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String kode = null;
				String nama = null;
				if (table.getSelectedRow() >= 0) {
					kode = table.getValueAt(table.getSelectedRow(), 0).toString();
					nama = table.getValueAt(table.getSelectedRow(), 1).toString();
					txtKeyword.setText(table.getValueAt(table.getSelectedRow(), 1).toString()); 
				}
				
				if (arg0.getClickCount() == 2) {
					FReturnPenjualan.window.frame.setEnabled(true);
					FReturnPenjualan.txtKodePelanggan.setText(kode);
					FReturnPenjualan.txtNamaPelanggan.setText(nama);
					window.frame.dispose();
				}
			}
		});
		scrollPane.setViewportView(table);
				
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnPenjualan.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(168, 229, 97, 26);
		frame.getContentPane().add(btnBatal);
		
		JLabel lblklikxPada = new JLabel("<html><body>*Klik 2x pada tabel <br>jika ingin memilih data pelanggan</body></html>");
		lblklikxPada.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblklikxPada.setBounds(6, 229, 150, 26);
		frame.getContentPane().add(lblklikxPada);
	}
	
	
	
	private void CariPelanggan (String key) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		model.addColumn("Kode Pelanggan");
		model.addColumn("Nama Pelanggan");
		model.addColumn("Alamat");
		db.con();
		try {
			String query = "select * from tb_pelanggan where id like ? or nama like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("Nama"),
						rs.getString("alamat")
				});
			}
			
			ps.close();
			rs.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
		} catch(Exception e) {
			System.out.println("error cari pelanggan " + e);
		}
	}
}
