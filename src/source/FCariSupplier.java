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
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FCariSupplier {

	JFrame frame;
	static FCariSupplier window;
	private JTextField txtCari;
	private JTable table;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FCariSupplier();
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
	public FCariSupplier() {
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
				TampilData(txtCari.getText());
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				FPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 277, 328);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtCari = new JTextField();
		txtCari.setBounds(93, 10, 166, 26);
		txtCari.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				TampilData(txtCari.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				TampilData(txtCari.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				TampilData(txtCari.getText());
			}});
		frame.getContentPane().add(txtCari);
		txtCari.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 249, 210);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					FPembelian.txtIdSupplier.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
					FPembelian.txtNamaSupplier.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
					FPembelian.window.frame.setEnabled(true);
					window.frame.dispose();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					FPembelian.txtIdSupplier.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
					FPembelian.txtNamaSupplier.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
					FPembelian.window.frame.setEnabled(true);
					window.frame.dispose();
				}
			}
		});
		btnOk.setBounds(175, 255, 84, 26);
		frame.getContentPane().add(btnOk);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnBatal.setBounds(88, 255, 84, 26);
		frame.getContentPane().add(btnBatal);
		
		JLabel lblKodeNama = new JLabel("Kode / Nama");
		lblKodeNama.setBounds(10, 14, 71, 16);
		frame.getContentPane().add(lblKodeNama);
	}
	
	private void TampilData(String str) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model.addColumn("Kode Supplier");
		model.addColumn("Nama Supplier");;
		model.addColumn("Alamat");
		db.con();
		try {
			String query = "select * from tb_supplier where id like ? or nama like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + str + "%");
			ps.setString(2, "%" + str + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
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
			table.getColumnModel().getColumn(2).setPreferredWidth(140);
		} catch(Exception e) {
			System.out.println("error tampil data " + e);
		}
	}
}
