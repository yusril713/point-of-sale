package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Font;

public class FListReturnPembelian {

	JFrame frame;
	static FListReturnPembelian window;
	private Database db;
	private JXTable table;
	private JXSearchField srchfldKeyword;
	private JTextField txtTotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FListReturnPembelian();
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
	public FListReturnPembelian() {
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
				txtTotal.setText("");
				srchfldKeyword.setText("");
				TampilReturn("");
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FReturnPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 575, 471);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		srchfldKeyword = new JXSearchField();
		srchfldKeyword.getDocument().addDocumentListener(new DocumentListener() {
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
				TampilReturn((srchfldKeyword.getText()));
			}
		});
		srchfldKeyword.setBounds(18, 18, 274, 26);
		frame.getContentPane().add(srchfldKeyword);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 50, 519, 302);
		frame.getContentPane().add(scrollPane);
		
		table = new JXTable();
		scrollPane.setViewportView(table);
		
		JXButton btnTutup = new JXButton();
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FReturnPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnTutup.setText("Tutup");
		btnTutup.setBounds(456, 398, 76, 28);
		frame.getContentPane().add(btnTutup);
		
		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtTotal.setEditable(false);
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setText("total");
		txtTotal.setBounds(409, 360, 128, 26);
		frame.getContentPane().add(txtTotal);
		txtTotal.setColumns(10);
	}
	
	private void TampilReturn(String key) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		model.addColumn("No");
		model.addColumn("Tanggal");
		model.addColumn("Supplier");
		model.addColumn("Karyawan");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Jumlah");
		model.addColumn("Harga");
		model.addColumn("Total");
		db.con();
		
		try {
			String query = "select tb_return_pembelian.*, tb_detail_return_pembelian.satuan, "
					+ "tb_detail_return_pembelian.jumlah, tb_detail_return_pembelian.harga, "
					+ "tb_barang.nama, tb_supplier.nama as supplier from tb_return_pembelian "
					+ "join tb_detail_return_pembelian on tb_return_pembelian.id = tb_detail_return_pembelian.id_return "
					+ "join tb_barang on tb_detail_return_pembelian.id_barang = tb_barang.id "
					+ "join tb_supplier on tb_return_pembelian.distributor = tb_supplier.id "
					+ "where tb_barang.id like ? or tb_barang.nama like ? or "
					+ "tb_supplier.nama like ? "
					+ "order by tanggal desc";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ps.setString(3, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			int i = 1;
			while (rs.next()) {
				model.addRow(new Object[] {
						i++,
						rs.getString("tanggal"),
						rs.getString("supplier"),
						rs.getString("karyawan"),
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("jumlah"),
						rs.getString("Harga"),
						FMain.FormatAngka((int) (rs.getInt("harga") * rs.getDouble("jumlah")))
				});
			}
			rs.close();
			ps.close();
			db.con.close();
			
			table.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int subtotal = 0;
		for (int i = 0; i < table.getSelectedRow(); i++) {
			subtotal += Integer.parseInt(table.getValueAt(i, 6).toString().replace(",", ""));
		}
		txtTotal.setText(FMain.FormatAngka(subtotal));
	}
}
