package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.jdesktop.swingx.JXDatePicker;

import com.formdev.flatlaf.FlatIntelliJLaf;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FTransaksi {

	JFrame frame;
	static FTransaksi window; 
	private JTextField txtNoFaktur;
	private JTextField txtTanggal;
	static JTextField txtKodePelanggan;
	static JTextField txtNamaPelanggan;
	static JTextField txtKodeNama;
	private JButton btnNewButton;
	private JLabel lblNewLabel_4;
	private JTextField txtIdKaryawan;
	private JComboBox<String> comboBox;
	private JXDatePicker datePicker;
	private JButton btnSimpan;
	private JButton btnBatal;
	static JTable table;
	private JScrollPane scrollPane;
	private JList<String> list;
	private JScrollPane scrollPane_1;
	private JTextField txtKembali;
	private JTextField txtJumlahBayar;
	private JButton btnHapus;
	static JTextField txtSubTotal;
	static boolean transkaksiKredit =  false;
	private Calendar cal = Calendar.getInstance(); 
	private Database db;
	static DefaultTableModel model;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private SwingWorker<String, Object> sw;
	private JButton btnRiwayatTransaksi;
	private JButton btnHapusText;

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
					window = new FTransaksi();
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
	public FTransaksi() {
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
				DataBaru();
				AturTabel();

				btnRiwayatTransaksi.setVisible(false);
				if (FMain.level.equals("admin"))
					btnRiwayatTransaksi.setVisible(true);
				else
					btnRiwayatTransaksi.setVisible(false);

				scrollPane_1.setVisible(false);
				if (!transkaksiKredit) {
					frame.setTitle("Transaksi Tunai");
					datePicker.setVisible(false);
					lblNewLabel_5.setVisible(false);
					txtKodeNama.requestFocus();
				} else {
					frame.setTitle("Transaksi Kredit");
					datePicker.setVisible(true);
					lblNewLabel_5.setVisible(true);
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				if (model.getRowCount() > 0) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengakhiri transaksi?", "Konfirmasi", 0);
					if (konfirm == 0) {
						while (model.getRowCount() > 0)
							model.removeRow(0);
						FMain.window.frame.setEnabled(true);
						window.frame.dispose();
					}
				} else {
					while (model.getRowCount() > 0)
						model.removeRow(0);
					FMain.window.frame.setEnabled(true);
					window.frame.dispose(); 
				}
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1366, 768);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{114, 226, 40, 0, 0, 231, 0, 0, 219, 120, 0, 0};
		gridBagLayout.rowHeights = new int[]{54, 35, 30, 30, 30, 180, 280, 39, 37, 32, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("No Faktur");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(20, 20, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		txtNoFaktur = new JTextField();
		txtNoFaktur.setEditable(false);
		txtNoFaktur.setBackground(UIManager.getColor("Button.light"));
		txtNoFaktur.setBorder(null);
		txtNoFaktur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtNoFaktur = new GridBagConstraints();
		gbc_txtNoFaktur.gridwidth = 3;
		gbc_txtNoFaktur.insets = new Insets(20, 20, 5, 5);
		gbc_txtNoFaktur.fill = GridBagConstraints.BOTH;
		gbc_txtNoFaktur.gridx = 1;
		gbc_txtNoFaktur.gridy = 0;
		frame.getContentPane().add(txtNoFaktur, gbc_txtNoFaktur);
		txtNoFaktur.setColumns(10);
		
		lblNewLabel_4 = new JLabel("ID Karwayan");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_4.insets = new Insets(20, 40, 5, 5);
		gbc_lblNewLabel_4.gridx = 4;
		gbc_lblNewLabel_4.gridy = 0;
		frame.getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		txtIdKaryawan = new JTextField();
		txtIdKaryawan.setEditable(false);
		txtIdKaryawan.setBackground(UIManager.getColor("Button.light"));
		txtIdKaryawan.setBorder(null);
		txtIdKaryawan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_txtIdKaryawan = new GridBagConstraints();
		gbc_txtIdKaryawan.insets = new Insets(20, 20, 5, 5);
		gbc_txtIdKaryawan.fill = GridBagConstraints.BOTH;
		gbc_txtIdKaryawan.gridx = 5;
		gbc_txtIdKaryawan.gridy = 0;
		frame.getContentPane().add(txtIdKaryawan, gbc_txtIdKaryawan);
		txtIdKaryawan.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tanggal");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 20, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtTanggal = new JTextField();
		txtTanggal.setEditable(false);
		txtTanggal.setBackground(UIManager.getColor("Button.light"));
		txtTanggal.setBorder(null);
		txtTanggal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTanggal.setColumns(10);
		GridBagConstraints gbc_txtTanggal = new GridBagConstraints();
		gbc_txtTanggal.gridwidth = 3;
		gbc_txtTanggal.insets = new Insets(0, 20, 5, 5);
		gbc_txtTanggal.fill = GridBagConstraints.BOTH;
		gbc_txtTanggal.gridx = 1;
		gbc_txtTanggal.gridy = 1;
		frame.getContentPane().add(txtTanggal, gbc_txtTanggal);
		
		lblNewLabel_5 = new JLabel("Jatuh Tempo");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_5.insets = new Insets(0, 40, 5, 5);
		gbc_lblNewLabel_5.gridx = 4;
		gbc_lblNewLabel_5.gridy = 1;
		frame.getContentPane().add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		datePicker = new JXDatePicker();
		datePicker.getEditor().setEditable(false);
		datePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.fill = GridBagConstraints.BOTH;
		gbc_datePicker.insets = new Insets(0, 20, 5, 5);
		gbc_datePicker.gridx = 5;
		gbc_datePicker.gridy = 1;
		frame.getContentPane().add(datePicker, gbc_datePicker);
		
		btnBatal = new JButton("Batal");
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getRowCount() > 0) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengakhiri transaksi?", "Konfirmasi", 0);
					if (konfirm == 0) {
						while (model.getRowCount() > 0)
							model.removeRow(0);
						FMain.window.frame.setEnabled(true);
						window.frame.dispose();
					}
				} else {
					while (model.getRowCount() > 0)
						model.removeRow(0);
					FMain.window.frame.setEnabled(true);
					window.frame.dispose(); 
				}
			}
		});
		btnBatal.setIcon(new ImageIcon(FTransaksi.class.getResource("/img/Batal.png")));
		btnBatal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBatal = new GridBagConstraints();
		gbc_btnBatal.fill = GridBagConstraints.BOTH;
		gbc_btnBatal.insets = new Insets(0, 0, 5, 20);
		gbc_btnBatal.gridx = 10;
		gbc_btnBatal.gridy = 1;
		frame.getContentPane().add(btnBatal, gbc_btnBatal);
		
		JLabel lblNewLabel_2 = new JLabel("Kode Pelanggan");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 20, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtKodePelanggan = new JTextField();
		txtKodePelanggan.setEditable(false);
		txtKodePelanggan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					CariDataPelanggan(txtKodePelanggan.getText());
			}
		});
		txtKodePelanggan.setBackground(UIManager.getColor("Button.light"));
		txtKodePelanggan.setBorder(null);
		txtKodePelanggan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtKodePelanggan.setColumns(10);
		GridBagConstraints gbc_txtKodePelanggan = new GridBagConstraints();
		gbc_txtKodePelanggan.insets = new Insets(0, 20, 5, 5);
		gbc_txtKodePelanggan.fill = GridBagConstraints.BOTH;
		gbc_txtKodePelanggan.gridx = 1;
		gbc_txtKodePelanggan.gridy = 2;
		frame.getContentPane().add(txtKodePelanggan, gbc_txtKodePelanggan);
		
		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FCariPelanggan.main(null);
				window.frame.setEnabled(false);
			}
		});
		btnNewButton.setFocusPainted(false);
		btnNewButton.setForeground(Color.LIGHT_GRAY);
		btnNewButton.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				btnNewButton.setForeground(Color.LIGHT_GRAY);
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnNewButton.setForeground(Color.LIGHT_GRAY);
			}
		});
		btnNewButton.setIcon(new ImageIcon(FTransaksi.class.getResource("/org/jdesktop/swingx/plaf/windows/resources/search.gif")));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setOpaque(true);
		btnNewButton.setContentAreaFilled(false);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 2;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int konfirm = 0;
				konfirm = JOptionPane.showConfirmDialog(btnSimpan, "Yakin ingin menyimpan data transaksi tersebut?", "Konfirmasi", 0);
				if (konfirm == 0) {
					if (table.getRowCount() > 0) {
						if (!transkaksiKredit) {
							if ((!txtJumlahBayar.getText().equals("")) && 
									(Integer.parseInt(txtJumlahBayar.getText().replace(",", "")) >= 
									Integer.parseInt(txtSubTotal.getText().replace(",", "")))) {
								SimpanTransaksi();
								SimpanDetailTransaksi();
								UpdateStokBarang();
//								SimpanIndeksTransaksi();
								while (model.getRowCount() > 0) 
									model.removeRow(0);
								DataBaru();
							} else {
								JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!");
							}
						} else {
							if (!txtIdKaryawan.getText().equals("") && !txtKodePelanggan.getText().equals("") && datePicker.getDate() != null) {
								SimpanTransaksiKredit();
								SimpanDetailTransaksiKredit();
								SimpanHutang();
//								CetakKredit();
								UpdateStokBarang();
//								SimpanIndeksTransaksi();
								while (model.getRowCount() > 0) 
									model.removeRow(0);
								DataBaru();
							} else {
								JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!!!");
							}
						}
					}
				}
			}
		});
		
		btnHapusText = new JButton("");
		btnHapusText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtKodePelanggan.setText("");
				txtNamaPelanggan.setText("");
			}
		});
		btnHapusText.setIcon(new ImageIcon(FTransaksi.class.getResource("/img/Tutup.png")));
		btnHapusText.setOpaque(true);
		btnHapusText.setForeground(Color.LIGHT_GRAY);
		btnHapusText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHapusText.setFocusPainted(false);
		btnHapusText.setContentAreaFilled(false);
		GridBagConstraints gbc_btnHapusText = new GridBagConstraints();
		gbc_btnHapusText.insets = new Insets(0, 0, 5, 5);
		gbc_btnHapusText.gridx = 3;
		gbc_btnHapusText.gridy = 2;
		frame.getContentPane().add(btnHapusText, gbc_btnHapusText);
		btnSimpan.setIcon(new ImageIcon(FTransaksi.class.getResource("/img/Simpan.png")));
		btnSimpan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSimpan = new GridBagConstraints();
		gbc_btnSimpan.fill = GridBagConstraints.BOTH;
		gbc_btnSimpan.insets = new Insets(0, 0, 5, 20);
		gbc_btnSimpan.gridx = 10;
		gbc_btnSimpan.gridy = 2;
		frame.getContentPane().add(btnSimpan, gbc_btnSimpan);
		
		JLabel lblNewLabel_3 = new JLabel("Nama Pelanggan");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_3.insets = new Insets(0, 20, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		frame.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		txtNamaPelanggan = new JTextField();
		txtNamaPelanggan.setEditable(false);
		txtNamaPelanggan.setBackground(UIManager.getColor("Button.light"));
		txtNamaPelanggan.setBorder(null);
		txtNamaPelanggan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtNamaPelanggan.setColumns(10);
		GridBagConstraints gbc_txtNamaPelanggan = new GridBagConstraints();
		gbc_txtNamaPelanggan.gridwidth = 3;
		gbc_txtNamaPelanggan.insets = new Insets(0, 20, 5, 5);
		gbc_txtNamaPelanggan.fill = GridBagConstraints.BOTH;
		gbc_txtNamaPelanggan.gridx = 1;
		gbc_txtNamaPelanggan.gridy = 3;
		frame.getContentPane().add(txtNamaPelanggan, gbc_txtNamaPelanggan);
		
		txtSubTotal = new JTextField();
		txtSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubTotal.setBorder(null);
		txtSubTotal.setEditable(false);
		txtSubTotal.setBackground(SystemColor.text);
		txtSubTotal.setFont(new Font("Tahoma", Font.BOLD, 42));
		txtSubTotal.setText("SUB TOTAL");
		GridBagConstraints gbc_txtSubTotal = new GridBagConstraints();
		gbc_txtSubTotal.gridwidth = 3;
		gbc_txtSubTotal.gridheight = 2;
		gbc_txtSubTotal.insets = new Insets(0, 0, 5, 20);
		gbc_txtSubTotal.fill = GridBagConstraints.BOTH;
		gbc_txtSubTotal.gridx = 8;
		gbc_txtSubTotal.gridy = 3;
		frame.getContentPane().add(txtSubTotal, gbc_txtSubTotal);
		txtSubTotal.setColumns(10);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() >= 0)
					SimpanIndeksTransaksi();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Kode Barang", "Nama Barang"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 20, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 4;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		txtKodeNama = new JTextField();
		txtKodeNama.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				boolean edit = false;
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					list.requestFocus();
					list.setSelectedIndex(0);
				}
				
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					boolean cek = false;
					switch (comboBox.getSelectedIndex()) {
					case 0:
						cek = CekDataBarang("Kode Barang");
						break;

					default:
						cek = CekDataBarang("Nama Barang");
						break;
					}
					if (cek) {
						scrollPane_1.setVisible(false);
						int i = 0;
						if (comboBox.getSelectedIndex() == 0) {
							for ( i = 0; i < table.getRowCount(); i++) {
								if (txtKodeNama.getText().equals(table.getValueAt(i, 0).toString())) {
									edit = true;
									break;
								}
							}
						} else if (comboBox.getSelectedIndex() == 1) {
							for ( i = 0; i < table.getRowCount(); i++) {
								if (txtKodeNama.getText().equals(table.getValueAt(i, 1).toString())) {
									edit = true;
									break;
								}
							}
						}
						if (edit) {
							/*FDetailTransaksi.main(null);
							FDetailTransaksi.key = null;
							FDetailTransaksi.edit = true;
							FDetailTransaksi.harga = table.getValueAt(i, 3).toString();
							FDetailTransaksi.id = table.getValueAt(i, 0).toString();
							FDetailTransaksi.nama = table.getValueAt(i, 1).toString();
							FDetailTransaksi.qty = table.getValueAt(i, 4).toString();
							FDetailTransaksi.satuan = table.getValueAt(i, 2).toString();
							FDetailTransaksi.row = i;*/
							table.setValueAt((Integer.parseInt(table.getValueAt(i, 4).toString()) + 1), i, 4);
							txtKodeNama.selectAll();
							txtKodeNama.requestFocus();
						}
						else {
							FDetailTransaksi.main(null);
							FDetailTransaksi.edit = false;
							FDetailTransaksi.key = txtKodeNama.getText();
							FDetailTransaksi.id = null;
							FDetailTransaksi.nama = null;
							FDetailTransaksi.harga = null;
							FDetailTransaksi.qty = null;
							FDetailTransaksi.row = 0;
							window.frame.setEnabled(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Data barang tidak ditemukan");
					}
				}
			}
		});
		txtKodeNama.getDocument().addDocumentListener(new DocumentListener() {
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
				switch (comboBox.getSelectedIndex()) {
				case 0:
					CariBarang("Kode Barang");
					break;
				default:
					CariBarang("Nama Barang");
					break;
				}
			}
		});
		txtKodeNama.setBackground(UIManager.getColor("Button.light"));
		txtKodeNama.setBorder(null);
		txtKodeNama.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtKodeNama.setColumns(10);
		GridBagConstraints gbc_txtKodeNama = new GridBagConstraints();
		gbc_txtKodeNama.gridwidth = 3;
		gbc_txtKodeNama.insets = new Insets(0, 20, 5, 5);
		gbc_txtKodeNama.fill = GridBagConstraints.BOTH;
		gbc_txtKodeNama.gridx = 1;
		gbc_txtKodeNama.gridy = 4;
		frame.getContentPane().add(txtKodeNama, gbc_txtKodeNama);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 20, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 5;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);
		
		list = new JList<String>();
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtKodeNama.setText(list.getSelectedValue().toString());
					scrollPane_1.setVisible(false);
					FDetailTransaksi.main(null);
					FDetailTransaksi.edit = false;
					FDetailTransaksi.key = txtKodeNama.getText();
					FDetailTransaksi.id = null;
					FDetailTransaksi.nama = null;
					FDetailTransaksi.harga = null;
					FDetailTransaksi.qty = null;
					FDetailTransaksi.row = 0;
					window.frame.setEnabled(false);
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					txtKodeNama.setText(list.getSelectedValue().toString());
					scrollPane_1.setVisible(false);
					FDetailTransaksi.main(null);
					FDetailTransaksi.edit = false;
					FDetailTransaksi.key = txtKodeNama.getText();
					FDetailTransaksi.id = null;
					FDetailTransaksi.nama = null;
					FDetailTransaksi.harga = null;
					FDetailTransaksi.qty = null;
					FDetailTransaksi.row = 0;
					window.frame.setEnabled(false);
				}
			}
		});
		list.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane_1.setViewportView(list);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 11;
		gbc_scrollPane.insets = new Insets(0, 20, 5, 20);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2 ) {
					FDetailTransaksi.main(null);
					FDetailTransaksi.key = null;
					FDetailTransaksi.edit = true;
					FDetailTransaksi.harga = table.getValueAt(table.getSelectedRow(), 3).toString();
					FDetailTransaksi.id = table.getValueAt(table.getSelectedRow(), 0).toString();
					FDetailTransaksi.nama = table.getValueAt(table.getSelectedRow(), 1).toString();
					FDetailTransaksi.qty = table.getValueAt(table.getSelectedRow(), 4).toString();
					FDetailTransaksi.satuan = table.getValueAt(table.getSelectedRow(), 2).toString();
					FDetailTransaksi.row = table.getSelectedRow();
					window.frame.setEnabled(false);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					model.removeRow(table.getSelectedRow());
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FTransaksi.class.getResource("/img/Hapus.png")));
		btnHapus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnHapus = new GridBagConstraints();
		gbc_btnHapus.fill = GridBagConstraints.BOTH;
		gbc_btnHapus.insets = new Insets(0, 20, 5, 5);
		gbc_btnHapus.gridx = 0;
		gbc_btnHapus.gridy = 7;
		frame.getContentPane().add(btnHapus, gbc_btnHapus);
		
		btnRiwayatTransaksi = new JButton("Riwayat Transaksi");
		btnRiwayatTransaksi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (transkaksiKredit) {
					FRiwayatTransaksi.main(null);
					FRiwayatTransaksi.transaksiKredit = true;
					window.frame.setEnabled(false);
				} else {
					FRiwayatTransaksi.main(null);
					FRiwayatTransaksi.transaksiKredit = false;
					window.frame.setEnabled(false);
				}
			}
		});
		
		txtJumlahBayar = new JTextField();
		txtJumlahBayar.setHorizontalAlignment(SwingConstants.RIGHT);
		txtJumlahBayar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
		        if (((c < '0') || (c > '9')) && 
		        		(c != '\b') && 
		        		(c != '')) {
		        	arg0.consume();
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() != 37) && (e.getKeyCode() != 39)) {
					try {
						txtJumlahBayar.setText(FMain.FormatAngka(Integer.valueOf(txtJumlahBayar.getText().replace(",", "")).intValue()));
					}
					catch (Exception er) {
						if (!txtJumlahBayar.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Batas maksimum = 2.147.483.647!!!");
						}	txtJumlahBayar.setText("");
					}
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int konfirm = 0;
					konfirm = JOptionPane.showConfirmDialog(btnSimpan, "Yakin ingin menyimpan data transaksi tersebut?", "Konfirmasi", 0);
					if (konfirm == 0) {
						if (table.getRowCount() > 0) {
							if (!transkaksiKredit) {
								if ((!txtJumlahBayar.getText().equals("")) && 
										(Integer.parseInt(txtJumlahBayar.getText().replace(",", "")) >= 
										Integer.parseInt(txtSubTotal.getText().replace(",", "")))) {
									SimpanTransaksi();
									SimpanDetailTransaksi();
//									Cetak();
									UpdateStokBarang();
//									SimpanIndeksTransaksi();
									while (model.getRowCount() > 0) 
										model.removeRow(0);
									DataBaru();
								} else {
									JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!");
								}
							} else {
								if (!txtIdKaryawan.getText().equals("") && !txtKodePelanggan.getText().equals("") && datePicker.getDate() != null) {
									SimpanTransaksiKredit();
									SimpanDetailTransaksiKredit();
									SimpanHutang();
//									CetakKredit();
									UpdateStokBarang();
//									SimpanIndeksTransaksi();
									while (model.getRowCount() > 0) 
										model.removeRow(0);
									DataBaru();
								} else {
									JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu");
								}
							}
						}
					}
					
				}
				
				
			}
		});
		txtJumlahBayar.getDocument().addDocumentListener(new DocumentListener() {
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
				//EventChanged();
			}
			public void EventChanged() {
				int jmlBayar = Integer.parseInt(txtJumlahBayar.getText().replace(",", ""));
				int subTotal = Integer.parseInt(txtSubTotal.getText().replace(",", ""));
				if (jmlBayar <= subTotal) {
					txtKembali.setText("0");
				} else {
					int kembali = jmlBayar - subTotal;
					txtKembali.setText(FMain.FormatAngka(kembali));
				}
			}
		});
		
		lblNewLabel_7 = new JLabel("JUMLAH BAYAR");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 22));
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.gridx = 8;
		gbc_lblNewLabel_7.gridy = 7;
		frame.getContentPane().add(lblNewLabel_7, gbc_lblNewLabel_7);
		txtJumlahBayar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtJumlahBayar.setColumns(10);
		txtJumlahBayar.setBorder(null);
		txtJumlahBayar.setBackground(SystemColor.controlHighlight);
		GridBagConstraints gbc_txtJumlahBayar = new GridBagConstraints();
		gbc_txtJumlahBayar.gridwidth = 2;
		gbc_txtJumlahBayar.insets = new Insets(0, 0, 5, 20);
		gbc_txtJumlahBayar.fill = GridBagConstraints.BOTH;
		gbc_txtJumlahBayar.gridx = 9;
		gbc_txtJumlahBayar.gridy = 7;
		frame.getContentPane().add(txtJumlahBayar, gbc_txtJumlahBayar);
		btnRiwayatTransaksi.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		GridBagConstraints gbc_btnRiwayatTransaksi = new GridBagConstraints();
		gbc_btnRiwayatTransaksi.fill = GridBagConstraints.BOTH;
		gbc_btnRiwayatTransaksi.insets = new Insets(0, 20, 5, 5);
		gbc_btnRiwayatTransaksi.gridx = 0;
		gbc_btnRiwayatTransaksi.gridy = 8;
		frame.getContentPane().add(btnRiwayatTransaksi, gbc_btnRiwayatTransaksi);
		
		lblNewLabel_6 = new JLabel("KEMBALI");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 22));
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.gridx = 8;
		gbc_lblNewLabel_6.gridy = 8;
		frame.getContentPane().add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		txtKembali = new JTextField();
		txtKembali.setHorizontalAlignment(SwingConstants.RIGHT);
		txtKembali.setEditable(false);
		txtKembali.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtKembali.setColumns(10);
		txtKembali.setBorder(null);
		txtKembali.setBackground(SystemColor.controlHighlight);
		GridBagConstraints gbc_txtKembali = new GridBagConstraints();
		gbc_txtKembali.gridwidth = 2;
		gbc_txtKembali.insets = new Insets(0, 0, 5, 20);
		gbc_txtKembali.fill = GridBagConstraints.BOTH;
		gbc_txtKembali.gridx = 9;
		
		
		gbc_txtKembali.gridy = 8;
		frame.getContentPane().add(txtKembali, gbc_txtKembali);
	}
	
	private void DataBaru() {
		txtKodeNama.setText("");
		txtKodePelanggan.setText("");
		txtNamaPelanggan.setText("");
		txtSubTotal.setText("");
		txtJumlahBayar.setText(null);
		txtKembali.setText(null);
		txtNoFaktur.setEditable(false);
		txtTanggal.setEditable(false);
		txtKodePelanggan.requestFocus();
		comboBox.setSelectedIndex(AmbilIndeksCari());
		
		DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		txtTanggal.setText(df.format(cal.getTime()));
		Calendar cal = Calendar.getInstance();
		cal.add(2, 1);
		datePicker.setDate(cal.getTime());
		datePicker.setFormats(new SimpleDateFormat("dd MMMM yyyy"));
		txtIdKaryawan.setText(AmbiIdKaryawan());
		if (!transkaksiKredit)
			txtNoFaktur.setText(SetNoFaktur());
		else
			txtNoFaktur.setText(SetNoFakturKredit());
	}
	
	private int AmbilIndeksCari() {
		int indeks = 0;
		db.con();
		try {
			String query = "select * from tb_info";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				indeks = rs.getInt("indeks");
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil indeks cari " + e);
		}
		return indeks;
	}
	
	private String AmbiIdKaryawan() {
		db.con();
		String id = "";
		try {
			String query = "select * from tb_karyawan where username = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, FMain.user);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getString("id");
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error ambil id karyawab " + e);
		}
		return id;
	}
	
	private String SetNoFaktur() {
		String noFaktur = "";
		int count = 0;
		DateFormat df = new SimpleDateFormat("ddMMyyHms");
		db.con();
		try {
			String query = "select count(no_faktur) as count from tb_transaksi";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("count")  + 1;
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error set no faktur " + e);
		}
		noFaktur = "TU/" + df.format(cal.getTime()) + "/" + count;
		return noFaktur;
	}
	
	private String SetNoFakturKredit() {
		String noFaktur = "";
		int count = 0;
		DateFormat df = new SimpleDateFormat("ddMMyyHms");
		db.con();
		try {
			String query = "select count(no_faktur) as count from tb_transaksi_kredit";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("count")  + 1;
			}
			st.close();
			rs.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error set no faktur " + e);
		}
		noFaktur = "KR/" + df.format(cal.getTime()) + "/" + count;
		return noFaktur;
	}
	
	private void AturTabel() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(4);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(0);
	    
	    String[] columnNames = { "Kode Barang", "Nama Barang", "Satuan", "Harga", "Qty", "Total", "Barang Diambil", "Barang disimpan" };
	    model = new DefaultTableModel() {
	    	private static final long serialVersionUID = 1L;
	    	public boolean isCellEditable(int row, int column) {
	    		return false;
	    	}
	    };
	    model.setColumnIdentifiers(columnNames);
	    table.setModel(model);
	    
	    table.setAutoResizeMode(0);
	    table.getColumnModel().getColumn(0).setPreferredWidth(160);
	    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(1).setPreferredWidth(230);
	    table.getColumnModel().getColumn(2).setPreferredWidth(125);
	    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(3).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(4).setPreferredWidth(70);
	    table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(5).setPreferredWidth(180);
	    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(6).setPreferredWidth(175);
	    table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(7).setPreferredWidth(175);
	    table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
	    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
		table.setRowHeight(25);
	}
	
	private void CariBarang(String str) {
		db.con();
		scrollPane_1.setVisible(true);
		try {
			String query = null;
			if (str.equals("Kode Barang"))
				query = "select id as str from tb_barang where id like ?";
			else 
				query = "select nama as str from tb_barang where nama like ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "%" + txtKodeNama.getText() + "%");
			ResultSet rs = ps.executeQuery();
			
			int rowcount = 0;
			if (rs.last()) {
				rowcount = rs.getRow();
				rs.beforeFirst();
			}
			//System.out.println(id_jenis.get(comboBox.getSelectedIndex()));
			String[] hasil = new String[rowcount];
			if ((rowcount > 0) && (!txtKodeNama.getText().equals(""))) {
				int i = 0;
				while (rs.next()) {
					hasil[i] = rs.getString("str");
					i++;
				}
			} else {
				scrollPane_1.setVisible(false);
			}
			list.setListData(hasil);
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("ada error cari barang " + e);
		}
	}
	
	private boolean CekDataBarang(String str) {
		db.con();
		boolean cek = false;
		try {
			String query = null;
			if (str.equals("Kode Barang"))
				query = "select id as str from tb_barang where id =  ?";
			else 
				query = "select nama as str from tb_barang where nama = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtKodeNama.getText());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cek = true;
			}
			ps.close();
			rs.close();
			db.con.close();
		} catch (Exception e) {
			System.out.println("ada error cari barang " + e);
		}
		return cek;
	}
	
	private void CariDataPelanggan(String kode) {
		db.con();
		try {
			String query = "select * from tb_pelanggan where id = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, kode);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				txtNamaPelanggan.setText(rs.getString("nama"));
			else
				JOptionPane.showMessageDialog(null, "Data pelanggan tidak diketahui."
						+ "\nSilakan daftar data pelanggan terlebih dahulu!");
			ps.close();
			rs.close();
			db.con.close();
			txtKodeNama.requestFocus();
		} catch(Exception e) {
			System.out.println("error cari pelanggan " + e);
		}
	}
	
	private void SimpanIndeksTransaksi() {
		db.con();
		try {
			String query = "update tb_info set indeks = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "" +comboBox.getSelectedIndex());
			ps.execute();
		} catch(Exception e) {
			System.out.println("error simpan indeks transaksi " + e);
		}
	}
	
	private void SimpanTransaksi() {
		String idKaryawan = AmbiIdKaryawan();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat tf = new SimpleDateFormat("HH:mm:ss");
		System.out.println();
		db.con();
		try {
			String query = "insert into tb_transaksi values(?,?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNoFaktur.getText());
			if (txtKodePelanggan.getText().equals("")) {
				ps.setString(2, "UMUM");
				txtKodePelanggan.setText("");
				txtNamaPelanggan.setText("");
			} else
				ps.setString(2, txtKodePelanggan.getText());
			ps.setString(3, txtSubTotal.getText().replace(",", ""));
			ps.setString(4, df.format(cal.getTime()));
			ps.setString(5, idKaryawan);
			ps.setString(6, txtJumlahBayar.getText().replace(",", ""));
			ps.setString(7, tf.format(cal.getTime()));
			ps.execute();
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan transaksi " + e);
		}
	}
	
	private int AmbilHargaBeli(String id, String satuan) {
		db.con();
		int hrg = 0;
		try {
			String query= "select harga_beli from tb_detail_barang where id_barang = ? and satuan = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, satuan);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				hrg = rs.getInt("harga_beli");
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return hrg;
	}
	
	private void SimpanDetailTransaksi() {
		db.con();
		double counter = 0;
		try {
			String query = "insert into tb_detail_transaksi values(?,?,?,?,?,?)";
			PreparedStatement ps = null;
			for (int i = 0; i < table.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.setString(3, table.getValueAt(i, 3).toString().replace(",", ""));
				ps.setString(4, table.getValueAt(i, 2).toString());
				ps.setString(5, table.getValueAt(i, 4).toString());
				ps.setString(6, ""+ AmbilHargaBeli(table.getValueAt(i, 0).toString(), table.getValueAt(i, 2).toString()));
				ps.execute();
				
				counter += Double.parseDouble(table.getValueAt(i, 7).toString());
				
			}
			ps.close();
			db.con.close();
			
			
		} catch(Exception e) {
			System.out.println("error simpan detail transaksi tunai" + e);
		}
		
		if (counter > 0) {
			SimpanDo();
			CetakDo();
		} else 
			Cetak();
	}
	
	private void SimpanDo() {
		db.con();
		try {
			String query = "insert into tb_do values(?,?,?,?,?,?)";
			PreparedStatement ps = null;
			for (int i = 0; i < table.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				if (Double.parseDouble(table.getValueAt(i, 7).toString()) > 0) {
					ps.setString(1, txtNoFaktur.getText());
					ps.setString(2, table.getValueAt(i, 0).toString());
					ps.setString(3, table.getValueAt(i, 2).toString());
					ps.setString(4, table.getValueAt(i, 7).toString());
					ps.setString(5, table.getValueAt(i, 6).toString());
					ps.setString(6, "0"); 
					ps.execute();
				}
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan do " + e);
		}
	}
	
	private void SimpanTransaksiKredit() {
		String idKaryawan = AmbiIdKaryawan();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat tf = new SimpleDateFormat("HH:mm:ss");
		db.con();
		try {
			String query = "insert into tb_transaksi_kredit values(?,?,?,?,?,?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNoFaktur.getText());
			ps.setString(2, txtKodePelanggan.getText());
			ps.setString(3, txtSubTotal.getText().replace(",", ""));
			ps.setString(4, df.format(cal.getTime()));
			ps.setString(5, idKaryawan);
			ps.setString(6, df.format(datePicker.getDate()));
			ps.setString(7, tf.format(cal.getTime()));
			ps.execute();
			
			if (!txtKembali.getText().equals("")) {
				query = "insert into tb_detail_hutang (no_faktur, tgl_bayar, jml_bayar)values(?,?,?)";
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, df.format(cal.getTime()));
				ps.setString(3, txtJumlahBayar.getText().replace(",", ""));
				ps.execute();
			}
					
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan transaksi kredit " + e);
		}
	}
	
	private void SimpanDetailTransaksiKredit() {
		db.con();
		double counter = 0;
		try {
			String query = "insert into tb_detail_transaksi_kredit values(?,?,?,?,?,?)";
			PreparedStatement ps = null;
			for (int i = 0; i < table.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.setString(3, table.getValueAt(i, 3).toString().replace(",", ""));
				ps.setString(4, table.getValueAt(i, 2).toString());
				ps.setString(5, table.getValueAt(i, 4).toString());
				ps.setString(6, ""+ AmbilHargaBeli(table.getValueAt(i, 0).toString(), table.getValueAt(i, 2).toString()));
				ps.execute();
				
				counter += Double.parseDouble(table.getValueAt(i, 7).toString());
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan detail transaksi " + e);
		}
		
		if (counter > 0) {
			SimpanDo();
			CetakDoKredit();
		} else
			CetakKredit();
	}
	
	private void UpdateStokBarang() {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = stok - ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = null;
			for (int i = 0; i < table.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				ps.setDouble(1, Double.parseDouble(table.getValueAt(i, 6).toString()));
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.setString(3, table.getValueAt(i, 2).toString());
				ps.execute();
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error update stok barang "  + e);
		}
	}
	
	private void SimpanHutang() {
		db.con();
		try {
			String query = "insert into tb_hutang values(?,?)";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, txtNoFaktur.getText());
			ps.setString(2, txtSubTotal.getText().replace(",", ""));
			ps.execute();
		} catch(Exception e) {
			System.out.println("error simpan hutang " + e);
		}
	}
	
	private void Cetak() {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\Struk.jasper";
			param.put("paramNoFaktur", txtNoFaktur.getText());
			param.put("paramJumlahBayar", txtJumlahBayar.getText());
			param.put("paramKembali", txtKembali.getText());
			
			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
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
					FTransaksi.window.frame.setEnabled(true);
					sw = new SwingWorker<String, Object>() {
						@Override
						protected String doInBackground() throws Exception {
							if(FMain.d.portFound) {
								FMain.d.ShowGreeting();
								FMain.d.ShowOpening("    ***WELCOME***   ", 
									       " MEGA DEPO BANGUNAN ");
							}
							return null;
						}
					};
					Thread t1 = new Thread(sw);
					t1.setPriority(Thread.MAX_PRIORITY);
					t1.start();
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void CetakDo() {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\StrukDo.jasper";
			param.put("paramNoFaktur", txtNoFaktur.getText());
			param.put("paramJumlahBayar", txtJumlahBayar.getText());
			param.put("paramKembali", txtKembali.getText());
			
			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
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
					FTransaksi.window.frame.setEnabled(true);
					sw = new SwingWorker<String, Object>() {
						@Override
						protected String doInBackground() throws Exception {
							if(FMain.d.portFound) {
								FMain.d.ShowGreeting();
								FMain.d.ShowOpening("    ***WELCOME***   ", 
									       " MEGA DEPO BANGUNAN ");
							}
							return null;
						}
					};
					Thread t1 = new Thread(sw);
					t1.setPriority(Thread.MAX_PRIORITY);
					t1.start();
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void CetakKredit() {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\StrukKredit.jasper";
			param.put("paramNoFaktur", txtNoFaktur.getText());
			param.put("paramJumlahBayar", txtJumlahBayar.getText());
			param.put("paramKembali", txtKembali.getText());
			
			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
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
					FTransaksi.window.frame.setEnabled(true);
					sw = new SwingWorker<String, Object>() {
						@Override
						protected String doInBackground() throws Exception {
							if(FMain.d.portFound) {
								FMain.d.ShowGreeting();
								FMain.d.ShowOpening("    ***WELCOME***   ", 
									       " MEGA DEPO BANGUNAN ");
							}
							return null;
						}
					};
					Thread t1 = new Thread(sw);
					t1.setPriority(Thread.MAX_PRIORITY);
					t1.start();
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void CetakDoKredit() {
		window.frame.setEnabled(false);
		try {
			db.con();
			Map<String, Object> param = new HashMap<String, Object>();
			
			String reportName = ".\\laporan\\StrukDoKredit.jasper";
			param.put("paramNoFaktur", txtNoFaktur.getText());
			param.put("paramJumlahBayar", txtJumlahBayar.getText());
			param.put("paramKembali", txtKembali.getText());
			
			JasperPrint print = JasperFillManager.fillReport(reportName, param, db.con);
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
					FTransaksi.window.frame.setEnabled(true);
					sw = new SwingWorker<String, Object>() {
						@Override
						protected String doInBackground() throws Exception {
							if(FMain.d.portFound) {
								FMain.d.ShowGreeting();
								FMain.d.ShowOpening("    ***WELCOME***   ", 
									       " MEGA DEPO BANGUNAN ");
							}
							return null;
						}
					};
					Thread t1 = new Thread(sw);
					t1.setPriority(Thread.MAX_PRIORITY);
					t1.start();
					jf.dispose();
				}
			};
			jf.addWindowListener(exitListener);
			
			db.con.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
