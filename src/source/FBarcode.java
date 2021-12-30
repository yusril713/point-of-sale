package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import org.jdesktop.swingx.JXSearchField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FBarcode {

	JFrame frame;
	static FBarcode window;
	private JTable table;
	private JTable table_1;
	private JTextField txtJumlah;
	private JXSearchField searchField;
	private Database db;
	private DefaultTableModel modelDetail;
	private JComboBox<String> cmbUkuran;
	private JButton btnPreview;
	private JButton btnLabelRak;
	private JComboBox<String> cmbJenis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FBarcode();
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
	public FBarcode() {
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
				txtJumlah.setText("1");
				txtJumlah.selectAll();
				TampilBarang(searchField.getText());
				AturTabel();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1000, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 0, 0, 117, 20, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Pilih data dengan cara klik 2x", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridheight = 11;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		searchField = new JXSearchField();
		searchField.getDocument().addDocumentListener(new DocumentListener() {
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
				TampilBarang(searchField.getText());
			}
		});
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.insets = new Insets(0, 0, 5, 0);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 0;
		gbc_searchField.gridy = 0;
		panel.add(searchField, gbc_searchField);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridheight = 12;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					MoveData(table.getSelectedRow());
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton button = new JButton(">>");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button.setBackground(Color.BLUE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					MoveData(table.getSelectedRow());
				} else
					JOptionPane.showMessageDialog(null, "Pilih data terlebih dahulu");
			}
		});
		
		cmbJenis = new JComboBox<String>();
		cmbJenis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbJenis.getSelectedIndex() == 0) {
					btnPreview.setVisible(true);
					btnLabelRak.setVisible(false);
				} else if (cmbJenis.getSelectedIndex() == 1) {
					btnPreview.setVisible(false);
					btnLabelRak.setVisible(true);
				}
			}
		});
		cmbJenis.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		cmbJenis.setModel(new DefaultComboBoxModel<String>(new String[] {"Barcode", "Label Rak"}));
		GridBagConstraints gbc_cmbJenis = new GridBagConstraints();
		gbc_cmbJenis.gridwidth = 2;
		gbc_cmbJenis.insets = new Insets(0, 0, 5, 5);
		gbc_cmbJenis.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbJenis.gridx = 4;
		gbc_cmbJenis.gridy = 1;
		frame.getContentPane().add(cmbJenis, gbc_cmbJenis);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridheight = 10;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 2;
		frame.getContentPane().add(button, gbc_button);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Data yang dipilih", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridheight = 11;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 3;
		gbc_panel_1.gridy = 1;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridheight = 8;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblUkuran = new JLabel("Ukuran");
		lblUkuran.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblUkuran = new GridBagConstraints();
		gbc_lblUkuran.gridwidth = 2;
		gbc_lblUkuran.fill = GridBagConstraints.BOTH;
		gbc_lblUkuran.insets = new Insets(0, 0, 5, 5);
		gbc_lblUkuran.gridx = 4;
		gbc_lblUkuran.gridy = 2;
		frame.getContentPane().add(lblUkuran, gbc_lblUkuran);
		
		cmbUkuran = new JComboBox<String>();
		cmbUkuran.setModel(new DefaultComboBoxModel<String>(new String[] {"Pilih Ukuran", "33 x 15mm x 1", "33 x 15mm x 2", "33 x 15mm x 3"}));
		cmbUkuran.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbUkuran = new GridBagConstraints();
		gbc_cmbUkuran.gridwidth = 2;
		gbc_cmbUkuran.insets = new Insets(0, 0, 5, 5);
		gbc_cmbUkuran.fill = GridBagConstraints.BOTH;
		gbc_cmbUkuran.gridx = 4;
		gbc_cmbUkuran.gridy = 3;
		frame.getContentPane().add(cmbUkuran, gbc_cmbUkuran);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while(modelDetail.getRowCount() > 0) {
					modelDetail.removeRow(0);
				}
			}
		});
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table_1.getSelectedRow() >= 0) {
					modelDetail.removeRow(table_1.getSelectedRow());
				}
			}
		});
		
		btnLabelRak = new JButton("Label Rak");
		btnLabelRak.setVisible(false);
		btnLabelRak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String toko = GetNamaToko();
				Simpan();
				switch (cmbUkuran.getSelectedIndex()) {
				case 1:
					PreviewLabelRak("rak_30x15x1", toko);
					break;
				case 2: 
					PreviewLabelRak("rak_30x15x2", toko);
					break;
				case 3:
					PreviewLabelRak("rak_30x15x3", toko);
					break;
				default:
					JOptionPane.showMessageDialog(null, "Pilih ukuran kertas terlebih dahulu");
					break;
				}
				Hapus();
			}
		});
		
		btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Simpan();
				switch (cmbUkuran.getSelectedIndex()) {
				case 1:
					PreviewBarcode("3015x1");
					break;
				case 2: 
					PreviewBarcode("3015x2");
					break;
				case 3:
					PreviewBarcode("3015x3");
					break;
				default:
					JOptionPane.showMessageDialog(null, "Pilih ukuran kertas terlebih dahulu");
					break;
				}
				Hapus();
			}
		});
		
		txtJumlah = new JTextField();
		txtJumlah.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '') && (c != '.')) {
		        	arg0.consume();
		        }
			}
		});
		
		JLabel lblJumlah = new JLabel("Jumlah");
		lblJumlah.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblJumlah = new GridBagConstraints();
		gbc_lblJumlah.gridwidth = 2;
		gbc_lblJumlah.fill = GridBagConstraints.BOTH;
		gbc_lblJumlah.insets = new Insets(0, 0, 5, 5);
		gbc_lblJumlah.gridx = 4;
		gbc_lblJumlah.gridy = 4;
		frame.getContentPane().add(lblJumlah, gbc_lblJumlah);
		txtJumlah.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtJumlah.setText("jumlah");
		GridBagConstraints gbc_txtJumlah = new GridBagConstraints();
		gbc_txtJumlah.gridwidth = 2;
		gbc_txtJumlah.insets = new Insets(0, 0, 5, 5);
		gbc_txtJumlah.fill = GridBagConstraints.BOTH;
		gbc_txtJumlah.gridx = 4;
		gbc_txtJumlah.gridy = 5;
		frame.getContentPane().add(txtJumlah, gbc_txtJumlah);
		txtJumlah.setColumns(10);
		btnPreview.setForeground(Color.WHITE);
		btnPreview.setBackground(Color.BLUE);
		btnPreview.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnPreview = new GridBagConstraints();
		gbc_btnPreview.fill = GridBagConstraints.BOTH;
		gbc_btnPreview.insets = new Insets(0, 0, 5, 5);
		gbc_btnPreview.gridx = 5;
		gbc_btnPreview.gridy = 6;
		frame.getContentPane().add(btnPreview, gbc_btnPreview);
		btnLabelRak.setForeground(Color.WHITE);
		btnLabelRak.setBackground(new Color(0, 0, 128));
		btnLabelRak.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnLabelRak = new GridBagConstraints();
		gbc_btnLabelRak.fill = GridBagConstraints.BOTH;
		gbc_btnLabelRak.insets = new Insets(0, 0, 5, 5);
		gbc_btnLabelRak.gridx = 5;
		gbc_btnLabelRak.gridy = 7;
		frame.getContentPane().add(btnLabelRak, gbc_btnLabelRak);
		btnHapus.setBackground(Color.RED);
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnHapus.setForeground(Color.WHITE);
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.fill = GridBagConstraints.BOTH;
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 5;
		gbc_btnHapus.gridy = 8;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnReset.setBackground(new Color(139, 0, 0));
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.insets = new Insets(0, 0, 5, 5);
		gbc_btnReset.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReset.gridx = 5;
		gbc_btnReset.gridy = 9;
		frame.getContentPane().add(btnReset, gbc_btnReset);
	}

	private void TampilBarang(String key) {
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Kode Barang");
		model.addColumn("Nama Barang");
		model.addColumn("Satuan");
		model.addColumn("Harga");
		db.con();
		try {
			String query = "select tb_barang.*, tb_detail_barang.satuan, tb_detail_barang.harga_jual "
					+ "from tb_barang "
					+ "inner join tb_detail_barang on tb_barang.id = tb_detail_barang.id_barang "
					+ "where tb_barang.id like ? or tb_barang.nama like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("satuan"), 
						FMain.FormatAngka(rs.getInt("harga_jual"))
				});
			}
			rs.close();
			ps.close();
			db.con.close();
			
			table.setModel(model);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void AturTabel() {
		modelDetail = new DefaultTableModel();
		String[] column = {"Kode", "Nama", "Satuan", "Harga"};
		modelDetail.setColumnIdentifiers(column);
		table_1.setModel(modelDetail);
		table_1.setAutoResizeMode(0);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(80);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
	}
	
	private void MoveData(int row) {
		boolean cek = false;
		for (int i = 0; i < table_1.getRowCount(); i++) {
			if (table.getValueAt(row, 0).toString().equals(table_1.getValueAt(i, 0).toString()) && 
					table.getValueAt(row, 2).toString().equals(table_1.getValueAt(i, 2).toString())) {
				cek = true;
				break;
			}
		}
		
		if (!cek)
			modelDetail.addRow(new Object[] {
					table.getValueAt(row, 0),
					table.getValueAt(row, 1),
					table.getValueAt(row, 2),
					table.getValueAt(row, 3),
			});
		else 
			JOptionPane.showMessageDialog(null, "Data sudah ada!!!");
	}
	
	private void Simpan() {
		db.con();
		try {
			String query = "insert into tb_barcode values(?,?,?)";
			PreparedStatement ps = null;
			for (int i = 0; i < table_1.getRowCount(); i++) {
				for (int j = 0; j < Integer.parseInt(txtJumlah.getText()); j++) {
					ps = db.con.prepareStatement(query);
					ps.setString(1, table_1.getValueAt(i, 0).toString());
					ps.setString(2, table_1.getValueAt(i, 1).toString());
					ps.setString(3, table_1.getValueAt(i, 3).toString().replace(",", ""));
					ps.execute();
				}
			}
			
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Hapus() {
		db.con();
		try {
			String query = "delete from tb_barcode";
			Statement st=  db.con.createStatement();
			st.execute(query);
			
			st.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String GetNamaToko() {
		String toko = null;
		db.con();
		try {
			String query = "select * from tb_properties";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				toko = rs.getString("nama_toko");
			}
			rs.close();
			st.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return toko;
	}
	
	private void PreviewBarcode(String file) {
		window.frame.setEnabled(false);
		try {
			db.con();
			String reportName = ".\\barcode\\" + file + ".jasper";
			
			JasperPrint print = JasperFillManager.fillReport(reportName, null, db.con);
			JRViewer jv = new JRViewer(print);
			final JFrame jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					FBarcode.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}
	
	private void PreviewLabelRak(String file, String namaToko) {
		window.frame.setEnabled(false);
		try {
			db.con();
			String reportName = ".\\barcode\\" + file + ".jasper";
			Map<String, Object> params = new HashMap<>();
			params.put("Parameter1", namaToko);
			
			JasperPrint print = JasperFillManager.fillReport(reportName, params, db.con);
			JRViewer jv = new JRViewer(print);
			final JFrame jf = new JFrame();
			jf.getContentPane().add(jv);
			jf.validate();
			jf.setVisible(true);
			jf.setExtendedState(jf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			WindowListener exitListener = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					FBarcode.window.frame.setEnabled(true);
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			window.frame.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
		}
	}
}
