package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FListReturnPenjualan {

	JFrame frame;
	static FListReturnPenjualan window;
	private JComboBox<String> comboBox;
	private Database db;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnTutup;
	private JTextField txtSubtotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FListReturnPenjualan();
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
	public FListReturnPenjualan() {
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
				TampilData();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FReturnPenjualan.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1280, 600);
		frame.setLocationRelativeTo(null);
//		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 144, 826, 0, 30, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0)
					TampilData();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Semua", "Layak dijual", "Tidak layak dijual"}));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FReturnPenjualan.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		
		txtSubtotal = new JTextField();
		txtSubtotal.setEditable(false);
		txtSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubtotal.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSubtotal.setText("subtotal");
		GridBagConstraints gbc_txtSubtotal = new GridBagConstraints();
		gbc_txtSubtotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtSubtotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSubtotal.gridx = 3;
		gbc_txtSubtotal.gridy = 3;
		frame.getContentPane().add(txtSubtotal, gbc_txtSubtotal);
		txtSubtotal.setColumns(10);
		btnTutup.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		GridBagConstraints gbc_btnTutup = new GridBagConstraints();
		gbc_btnTutup.anchor = GridBagConstraints.EAST;
		gbc_btnTutup.insets = new Insets(0, 0, 5, 5);
		gbc_btnTutup.gridx = 3;
		gbc_btnTutup.gridy = 4;
		frame.getContentPane().add(btnTutup, gbc_btnTutup);
	}
	
	private void TampilData() {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		model.addColumn("Tgl Return");
		model.addColumn("Kode Brg");
		model.addColumn("Nama Barang");
		model.addColumn("Jumlah");
		model.addColumn("Harga");
		model.addColumn("Total");
		model.addColumn("Kelayakan");
		model.addColumn("Keterangan");
		
		db.con();
		try {
			String query = null;
			ResultSet rs = null;
			if (comboBox.getSelectedIndex() == 0) {
				query = "select tb_return_penjualan.id, tb_return_penjualan.tanggal, tb_detail_return_penjualan.*, tb_barang.nama "
						+ "from tb_return_penjualan "
						+ "join tb_detail_return_penjualan on tb_detail_return_penjualan.id_return = tb_return_penjualan.id "
						+ "join tb_barang on tb_detail_return_penjualan.id_barang = tb_barang.id "
						+ "order by tb_return_penjualan.tanggal desc";
				Statement st = db.con.createStatement();
				rs = st.executeQuery(query); 
			}
			else {
				if (comboBox.getSelectedIndex() == 1) 
					query = "select tb_return_penjualan.id, tb_return_penjualan.tanggal, tb_detail_return_penjualan.*, tb_barang.nama "
							+ "from tb_return_penjualan "
							+ "join tb_detail_return_penjualan on tb_detail_return_penjualan.id_return = tb_return_penjualan.id "
							+ "join tb_barang on tb_detail_return_penjualan.id_barang = tb_barang.id "
							+ "where tb_detail_return_penjualan.kelayakan = 'Layak dijual'"
							+ "order by tb_return_penjualan.tanggal desc"; 
				else 
					query = "select tb_return_penjualan.id, tb_return_penjualan.tanggal, tb_detail_return_penjualan.*, tb_barang.nama "
							+ "from tb_return_penjualan "
							+ "join tb_detail_return_penjualan on tb_detail_return_penjualan.id_return = tb_return_penjualan.id "
							+ "join tb_barang on tb_detail_return_penjualan.id_barang = tb_barang.id "
							+ "where tb_detail_return_penjualan.kelayakan = 'Tidak layak dijual'"
							+ "order by tb_return_penjualan.tanggal desc"; 
				Statement st = db.con.createStatement();
				rs = st.executeQuery(query);
			}
			
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("tanggal"),
						rs.getString("id_barang"),
						rs.getString("nama"),
						rs.getString("jumlah"),
						FMain.FormatAngka(rs.getInt("harga")),
						FMain.FormatAngka((int) (rs.getDouble("jumlah") * rs.getInt("harga"))),
						rs.getString("kelayakan"),
						rs.getString("keterangan")
						
				});
				
			}
			rs.close();
			db.con.close();
			table.setModel(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int subtotal = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			subtotal += Integer.parseInt(table.getValueAt(i, 5).toString().replace(",", ""));
		}
		txtSubtotal.setText(FMain.FormatAngka(subtotal));
	}
}
