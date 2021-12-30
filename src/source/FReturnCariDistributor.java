package source;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class FReturnCariDistributor {

	JFrame frame;
	static FReturnCariDistributor window;
	private JTextField txtKeyword;
	private JTable table;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FReturnCariDistributor();
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
	public FReturnCariDistributor() {
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
				AmbilDataDistributor("");
			}
		});
		frame.setBounds(100, 100, 323, 300);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtKeyword = new JTextField();
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
				AmbilDataDistributor(txtKeyword.getText());
			}
		});
		txtKeyword.setBounds(18, 18, 280, 26);
		frame.getContentPane().add(txtKeyword);
		txtKeyword.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 45, 280, 176);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					FReturnPembelian.window.frame.setEnabled(true);
					FReturnPembelian.txtDistributor.setText(table.getValueAt(table.getSelectedRow(), 0).toString() + " - " + table.getValueAt(table.getSelectedRow(), 1).toString());
					window.frame.dispose();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblklikxPada = new JLabel("*Klik 2x pada tabel untuk memilih data");
		lblklikxPada.setForeground(Color.RED);
		lblklikxPada.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblklikxPada.setBounds(18, 221, 180, 16);
		frame.getContentPane().add(lblklikxPada);
		
		JButton btnTutup = new JButton("Tutup");
		btnTutup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FReturnPembelian.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		btnTutup.setBounds(201, 229, 97, 26);
		frame.getContentPane().add(btnTutup);
	}
	
	private void AmbilDataDistributor(String key) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		model.addColumn("Id");
		model.addColumn("Nama");
		model.addColumn("Alamat");
		model.addColumn("Telp");
		
		db.con();
		try {
			String query = "select * from tb_supplier where id like ? or nama like ? order by nama";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + key + "%");
			ps.setString(2, "%" + key + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("nama"),
						rs.getString("alamat"),
						rs.getString("telp")
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
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
