package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXSearchField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FCashflowData {

	JFrame frame;
	static FCashflowData window;
	private JLabel lblTotalPemasukan;
	private JLabel lblTotalPengeluaran;
	private static JTable table;
	private JXSearchField srchfldCari;
	private static Database db;
	static DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(FlatIntelliJLaf.class.getName());
		} catch(Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FCashflowData();
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
	public FCashflowData() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("ARUS KAS");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				db = new Database();
				AturTabel();
				TampilData("");
				lblTotalPengeluaran.setText("Rp. " + FMain.FormatAngka(GetTotalPengeluaran()));
				lblTotalPemasukan.setText("Rp. " + FMain.FormatAngka(GetTotalPemasukan()));
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1366, 768);
		frame.setExtendedState(frame.getExtendedState()| JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 125, 125, 125, 125, 125, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 5, 0, 20, 37, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblPemasukanKas = new JLabel("PEMASUKAN KAS");
		lblPemasukanKas.setFont(new Font("Segoe UI", Font.BOLD, 18));
		GridBagConstraints gbc_lblPemasukanKas = new GridBagConstraints();
		gbc_lblPemasukanKas.gridwidth = 2;
		gbc_lblPemasukanKas.insets = new Insets(0, 0, 5, 5);
		gbc_lblPemasukanKas.gridx = 1;
		gbc_lblPemasukanKas.gridy = 1;
		frame.getContentPane().add(lblPemasukanKas, gbc_lblPemasukanKas);
		
		JLabel lblPengeluaranKas = new JLabel("PENGELUARAN KAS");
		lblPengeluaranKas.setFont(new Font("Segoe UI", Font.BOLD, 18));
		GridBagConstraints gbc_lblPengeluaranKas = new GridBagConstraints();
		gbc_lblPengeluaranKas.gridwidth = 2;
		gbc_lblPengeluaranKas.insets = new Insets(0, 0, 5, 5);
		gbc_lblPengeluaranKas.gridx = 4;
		gbc_lblPengeluaranKas.gridy = 1;
		frame.getContentPane().add(lblPengeluaranKas, gbc_lblPengeluaranKas);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 2;
		frame.getContentPane().add(separator, gbc_separator);
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 2;
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 4;
		gbc_separator_1.gridy = 2;
		frame.getContentPane().add(separator_1, gbc_separator_1);
		
		lblTotalPemasukan = new JLabel("Total Pemasukan");
		lblTotalPemasukan.setFont(new Font("Segoe UI", Font.BOLD, 18));
		GridBagConstraints gbc_lblTotalPemasukan = new GridBagConstraints();
		gbc_lblTotalPemasukan.gridwidth = 2;
		gbc_lblTotalPemasukan.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPemasukan.gridx = 1;
		gbc_lblTotalPemasukan.gridy = 3;
		frame.getContentPane().add(lblTotalPemasukan, gbc_lblTotalPemasukan);
		
		lblTotalPengeluaran = new JLabel("Total Pengeluaran");
		lblTotalPengeluaran.setFont(new Font("Segoe UI", Font.BOLD, 18));
		GridBagConstraints gbc_lblTotalPengeluaran = new GridBagConstraints();
		gbc_lblTotalPengeluaran.gridwidth = 2;
		gbc_lblTotalPengeluaran.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPengeluaran.gridx = 4;
		gbc_lblTotalPengeluaran.gridy = 3;
		frame.getContentPane().add(lblTotalPengeluaran, gbc_lblTotalPengeluaran);
		
		JLabel lblCari = new JLabel("Cari");
		lblCari.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_lblCari = new GridBagConstraints();
		gbc_lblCari.fill = GridBagConstraints.VERTICAL;
		gbc_lblCari.insets = new Insets(0, 0, 5, 5);
		gbc_lblCari.gridx = 1;
		gbc_lblCari.gridy = 5;
		frame.getContentPane().add(lblCari, gbc_lblCari);
		
		srchfldCari = new JXSearchField();
		srchfldCari.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				TampilData(srchfldCari.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				TampilData(srchfldCari.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				TampilData(srchfldCari.getText());
			}});
		srchfldCari.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		srchfldCari.setBackground(new Color(211, 211, 211));
		GridBagConstraints gbc_srchfldCari = new GridBagConstraints();
		gbc_srchfldCari.gridwidth = 2;
		gbc_srchfldCari.insets = new Insets(0, 0, 5, 5);
		gbc_srchfldCari.fill = GridBagConstraints.BOTH;
		gbc_srchfldCari.gridx = 2;
		gbc_srchfldCari.gridy = 5;
		frame.getContentPane().add(srchfldCari, gbc_srchfldCari);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 6;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRow() >= 0) {
					if (arg0.getClickCount() == 2) {
						FCashFlow.main(null);
						FCashFlow.edit = true;
						FCashFlow.kode = table.getValueAt(table.getSelectedRow(), 1).toString();
						window.frame.setEnabled(false);
					}
				}
			}
		});
		table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		scrollPane.setViewportView(table);
		
		JButton btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FCashFlow.main(null);
				FCashFlow.edit = false;
				window.frame.setEnabled(false);
			}
		});
		btnTambah.setForeground(new Color(255, 255, 255));
		btnTambah.setBackground(new Color(0, 153, 255));
		btnTambah.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnTambah = new GridBagConstraints();
		gbc_btnTambah.fill = GridBagConstraints.BOTH;
		gbc_btnTambah.insets = new Insets(0, 0, 5, 5);
		gbc_btnTambah.gridx = 1;
		gbc_btnTambah.gridy = 7;
		frame.getContentPane().add(btnTambah, gbc_btnTambah);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FCashFlow.main(null);
				FCashFlow.edit = true;
				FCashFlow.kode = table.getValueAt(table.getSelectedRow(), 1).toString();
				window.frame.setEnabled(false);
			}
		});
		btnEdit.setForeground(new Color(255, 255, 255));
		btnEdit.setBackground(new Color(0, 153, 255));
		btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.fill = GridBagConstraints.BOTH;
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.gridx = 2;
		gbc_btnEdit.gridy = 7;
		frame.getContentPane().add(btnEdit, gbc_btnEdit);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					int konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data tersebut?", "Konfirmasi", 0);
					if (konfirm == 0) {
						Hapus(table.getValueAt(table.getSelectedRow(), 1).toString());
					}
				} else 
					JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus");
			}
		});
		btnHapus.setForeground(new Color(255, 255, 255));
		btnHapus.setBackground(new Color(255, 0, 0));
		btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.fill = GridBagConstraints.BOTH;
		gbc_btnHapus.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapus.gridx = 3;
		gbc_btnHapus.gridy = 7;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
	}
	
	static void AturTabel() {
		model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		model.addColumn("No.");
		model.addColumn("Kode");
		model.addColumn("Tanggal");
		model.addColumn("Jam");
		model.addColumn("Jenis Kas");
		model.addColumn("Jumlah");
		model.addColumn("Keterangan");
		model.addColumn("User");
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(4);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(0);
	    table.setModel(model);
	    table.setAutoResizeMode(0);
	    table.getColumnModel().getColumn(0).setPreferredWidth(50);
	    table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(1).setPreferredWidth(150);
	    table.getColumnModel().getColumn(2).setPreferredWidth(150);
	    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(3).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(4).setPreferredWidth(150);
	    table.getColumnModel().getColumn(5).setPreferredWidth(170);
	    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(6).setPreferredWidth(250);
	    table.getColumnModel().getColumn(7).setPreferredWidth(170);
	    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
		table.setRowHeight(25);
	}
	
	static void TampilData(String key) {
		AturTabel();
		db.con();
		try {
			String query = "select * from tb_cashflow where id like ? "
					+ "or jenis like ? "
					+ "or keterangan like ? "
					+ "or user_id like ? "
					+ "order by tanggal,jam desc";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ps.setString(3, "%" + key + "%");
			ps.setString(4, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			int no = 1;
			while(rs.next()) {
				model.addRow(new Object[] {
						no++,
						rs.getString("id"),
						rs.getString("tanggal"),
						rs.getString("jam"),
						rs.getString("jenis"),
						FMain.FormatAngka(rs.getInt("jumlah")),
						rs.getString("Keterangan"),
						rs.getString("user_id")
				});
			}
			rs.close();
			ps.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		updateRowHeights();
	}
	
	private int GetTotalPengeluaran() {
		db.con();
		try {
			String query = "select sum(jumlah) as total from tb_cashflow where jenis = 'Pengeluaran'";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next())
				return rs.getInt("total");
			rs.close();
			st.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int GetTotalPemasukan() {
		db.con();
		try {
			String query = "select sum(jumlah) as total from tb_cashflow where jenis = 'Pemasukan'";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next())
				return rs.getInt("total");
			rs.close();
			st.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private void Hapus(String id) {
		db.con();
		try {
			String query = "delete from tb_cashflow where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.execute();
			ps.close();
			db.con.close();
			
			JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
			
			AturTabel();
			TampilData("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void updateRowHeights()
	{
	    for (int row = 0; row < table.getRowCount(); row++)
	    {
	        int rowHeight = table.getRowHeight();

	        for (int column = 0; column < table.getColumnCount(); column++)
	        {
	            Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
	            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
	        }

	        table.setRowHeight(row, rowHeight);
	    }
	}
}
