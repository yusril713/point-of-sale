package source;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.Insets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class FListStokBarang {

	static FListStokBarang window ;
	JFrame frame;
	private JXTable table;
	private JTextField textField;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FListStokBarang();
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
	public FListStokBarang() {
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
				ListStokBarang(textField.getText());
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FStokBarangNew.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1084, 640);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 246, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				ListStokBarang("");
			}
		});
		comboBox.setForeground(Color.GRAY);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Semua", "Stok Terkecil", "Stok Ternbanyak", "DO"}));
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener() {
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
				if (!textField.getText().equals("")) {
					ListStokBarang(textField.getText());
				}
			}
		});
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JXTable() {
			private static final long serialVersionUID = 1L;
			 
			@Override 
			public Component prepareRenderer(TableCellRenderer renderer, int
					row, int col) { 
				Component comp = super.prepareRenderer(renderer, row, col);
				if (Double.parseDouble(getModel().getValueAt(row, 4).toString()) <= 10)  
					comp.setBackground(Color.red); 
				return comp;
			}
		};
		table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
	}

	private void ListStokBarang(String keyword) {
		String query = null;
		if (comboBox.getSelectedIndex() == 0) 
			query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.stok, "
					+ "sum(tb_do.jumlah_do) as jml_do from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "left join tb_do on (tb_do.id_barang = tb_detail_barang.id_barang and tb_do.satuan = tb_detail_barang.satuan) "
					+ "where tb_barang.id like ? or tb_barang.nama like ?"
					+ "group by tb_barang.id, tb_detail_barang.satuan "
					+ "order by tb_barang.nama";
		else if (comboBox.getSelectedIndex() == 1) 
			query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.stok, "
					+ "sum(tb_do.jumlah_do) as jml_do from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "left join tb_do on (tb_do.id_barang = tb_detail_barang.id_barang and tb_do.satuan = tb_detail_barang.satuan) "
					+ "where tb_barang.id like ? or tb_barang.nama like ?"
					+ "group by tb_barang.id, tb_detail_barang.satuan "
					+ "order by (tb_detail_barang.stok - tb_do.jumlah_do) asc";
		else if (comboBox.getSelectedIndex() == 2) 
			query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.stok, "
					+ "sum(tb_do.jumlah_do) as jml_do from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "left join tb_do on (tb_do.id_barang = tb_detail_barang.id_barang and tb_do.satuan = tb_detail_barang.satuan) "
					+ "where tb_barang.id like ? or tb_barang.nama like ?"
					+ "group by tb_barang.id, tb_detail_barang.satuan "
					+ "order by (tb_detail_barang.stok - tb_do.jumlah_do) desc";
		else 
			query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.stok, "
					+ "sum(tb_do.jumlah_do) as jml_do from tb_barang "
					+ "join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "join tb_do on (tb_do.id_barang = tb_detail_barang.id_barang and tb_do.satuan = tb_detail_barang.satuan) "
					+ "where tb_barang.id like ? or tb_barang.nama like ?"
					+ "group by tb_barang.id, tb_detail_barang.satuan "
					+ "order by tb_barang.nama";
		
		
		DefaultTableModel model = new DefaultTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Stok saat ini");
		model.addColumn("Jumlah DO");
		model.addColumn("Stok Jual");
		Database db = new Database();
		db.con();
		try {
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("stok"),
						rs.getString("jml_do"),
						rs.getDouble("stok") - rs.getDouble("jml_do")
				});
			}
			ps.close();
			rs.close();
			db.con.close();
			
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		    centerRenderer.setHorizontalAlignment(0);
		    
			table.setModel(model);
			table.setAutoResizeMode(0);
		    table.getColumnModel().getColumn(0).setPreferredWidth(200);
		    table.getColumnModel().getColumn(1).setPreferredWidth(250);
		    table.getColumnModel().getColumn(2).setPreferredWidth(200);
		    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(3).setPreferredWidth(200);
		    table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		    table.getColumnModel().getColumn(4).setPreferredWidth(200);
		    table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
		    
		    table.getTableHeader().setFont(new Font("SansSerrif", Font.PLAIN, 24));
			table.setRowHeight(25);
			table.setFont(new Font("SansSerif", Font.PLAIN, 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
