package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import java.awt.Color;

public class FStokKurang {

	JFrame frame;
	static FStokKurang window;
	private Database db;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FStokKurang();
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
	public FStokKurang() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("STOK HAMPIR HABIS!");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				db = new Database();
				TampilData();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1000, 700);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 589, 296, 89, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 51, 534, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblStokBnarangKurang = new JLabel("STOK BARANG KURANG DARI 5");
		lblStokBnarangKurang.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_lblStokBnarangKurang = new GridBagConstraints();
		gbc_lblStokBnarangKurang.fill = GridBagConstraints.BOTH;
		gbc_lblStokBnarangKurang.insets = new Insets(0, 0, 5, 5);
		gbc_lblStokBnarangKurang.gridx = 1;
		gbc_lblStokBnarangKurang.gridy = 1;
		frame.getContentPane().add(lblStokBnarangKurang, gbc_lblStokBnarangKurang);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnTutup = new JButton("Tutup");
		btnTutup.setIcon(new ImageIcon(FStokKurang.class.getResource("/img/Tutup.png")));
		btnTutup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		GridBagConstraints gbc_btnTutup = new GridBagConstraints();
		gbc_btnTutup.insets = new Insets(0, 0, 5, 5);
		gbc_btnTutup.anchor = GridBagConstraints.NORTH;
		gbc_btnTutup.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTutup.gridx = 3;
		gbc_btnTutup.gridy = 3;
		frame.getContentPane().add(btnTutup, gbc_btnTutup);
	}
	
	private void TampilData() {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Sisa Stok");
		db.con();
		try {
			String query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.stok "
					+ "from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "where tb_detail_barang.stok <= 5 order by tb_detail_barang.stok asc";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("satuan"),
						rs.getString("stok")
				});
			}
			
			rs.close();
			st.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
		    table.getColumnModel().getColumn(0).setPreferredWidth(200);
		    table.getColumnModel().getColumn(1).setPreferredWidth(250);
		    table.getColumnModel().getColumn(2).setPreferredWidth(200);
		    table.getColumnModel().getColumn(3).setPreferredWidth(200);
		    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
		    
		    table.getTableHeader().setFont(new Font("SansSerrif", Font.PLAIN, 24));
			table.setRowHeight(25);
			table.setFont(new Font("SansSerif", Font.PLAIN, 16));
		} catch(Exception e) {
			System.out.println("error tampil data " + e);
		}
	}
}
