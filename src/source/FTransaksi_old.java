package source;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import java.awt.Component;

public class FTransaksi_old {

	JFrame frame;
	static FTransaksi_old window;
	private JTextField txtNoFaktur;
	static JTextField txtKodePelanggan;
	static JTextField txtNamaPelanggan;
	static JTextField txtKodeNama;
	private JLabel label_2;
	private JTextField txtTanggal;
	private JList<String> list;
	private JScrollPane scrollPane;
	static JTable table;
	static JTextField txtSubTotal;
	private JLabel label_3;
	private JXDatePicker datePicker;
	private JComboBox<String> comboBox;
	static boolean transkaksiKredit =  false;
	private Calendar cal = Calendar.getInstance(); 
	private Database db;
	static DefaultTableModel model;
	private JTextField txtIdKaryawan;
	private JTextField txtJumlahBayar;
	private JTextField txtKembali;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FTransaksi_old();
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
	public FTransaksi_old() {
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
				if (!transkaksiKredit) {
					frame.setTitle("Transaksi Tunai");
					datePicker.setVisible(false);
					label_3.setVisible(false);
					txtKodeNama.requestFocus();
				} else {
					frame.setTitle("Transaksi Kredit");
					datePicker.setVisible(true);
					label_3.setVisible(true);
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
		frame.setBounds(100, 100, 1444, 788);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		txtSubTotal = new JTextField();
		txtSubTotal.setBounds(871, 93, 340, 49);
		txtSubTotal.setEditable(false);
		txtSubTotal.setBorder(null);
		txtSubTotal.setBackground(SystemColor.text);
		txtSubTotal.setFont(new Font("Tahoma", Font.PLAIN, 40));
		txtSubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txtSubTotal.setText("sub total");
		txtSubTotal.setColumns(10);
		
		JLabel lblNoFaktur = new JLabel("No. Faktur");
		lblNoFaktur.setBounds(7, 11, 64, 17);
		lblNoFaktur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label = new JLabel("Kode Pelanggan");
		label.setBounds(7, 73, 123, 17);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label_1 = new JLabel("Nama Pelanggan");
		label_1.setBounds(7, 107, 123, 17);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(7, 142, 123, 27);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Kode Barang", "Nama Barang"}));
		
		txtNoFaktur = new JTextField();
		txtNoFaktur.setBounds(171, 7, 256, 25);
		txtNoFaktur.setAlignmentY(Component.TOP_ALIGNMENT);
		txtNoFaktur.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtNoFaktur.setBorder(null);
		txtNoFaktur.setBackground(SystemColor.control);
		txtNoFaktur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNoFaktur.setText("no faktur");
		txtNoFaktur.setColumns(10);
		
		txtKodePelanggan = new JTextField();
		txtKodePelanggan.setBounds(171, 65, 195, 32);
		txtKodePelanggan.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtKodePelanggan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					CariDataPelanggan(txtKodePelanggan.getText());
			}
		});
		txtKodePelanggan.setBorder(null);
		txtKodePelanggan.setBackground(SystemColor.control);
		txtKodePelanggan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtKodePelanggan.setText("kode pelanggan");
		txtKodePelanggan.setColumns(10);
		
		txtNamaPelanggan = new JTextField();
		txtNamaPelanggan.setBounds(171, 101, 256, 28);
		txtNamaPelanggan.setAlignmentY(Component.TOP_ALIGNMENT);
		txtNamaPelanggan.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtNamaPelanggan.setEditable(false);
		txtNamaPelanggan.setBorder(null);
		txtNamaPelanggan.setBackground(SystemColor.control);
		txtNamaPelanggan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNamaPelanggan.setText("Nama pelanggan");
		txtNamaPelanggan.setColumns(10);
		
		txtKodeNama = new JTextField();
		txtKodeNama.setBounds(171, 142, 256, 27);
		txtKodeNama.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtKodeNama.setAlignmentY(Component.TOP_ALIGNMENT);
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
						scrollPane.setVisible(false);
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
		txtKodeNama.setBorder(null);
		txtKodeNama.setBackground(SystemColor.control);
		txtKodeNama.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtKodeNama.setText("kode nama");
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
		txtKodeNama.setColumns(10);
		
		label_2 = new JLabel("Tanggal");
		label_2.setBounds(7, 40, 123, 17);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtTanggal = new JTextField();
		txtTanggal.setBounds(171, 36, 256, 25);
		txtTanggal.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtTanggal.setAlignmentY(Component.TOP_ALIGNMENT);
		txtTanggal.setBackground(SystemColor.control);
		txtTanggal.setBorder(null);
		txtTanggal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTanggal.setText("tanggal");
		txtTanggal.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(7, 199, 1347, 434);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		scrollPane_1.setViewportView(table);
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.setBounds(7, 645, 123, 27);
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > 0) {
					model.removeRow(table.getSelectedRow());
				}
			}
		});
		btnHapus.setIcon(new ImageIcon(FTransaksi_old.class.getResource("/img/Hapus.png")));
		btnHapus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.setBounds(1255, 141, 99, 27);
		btnBatal.setAlignmentX(Component.RIGHT_ALIGNMENT);
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
		btnBatal.setIcon(new ImageIcon(FTransaksi_old.class.getResource("/img/Batal.png")));
		btnBatal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.setBounds(1255, 101, 99, 28);
		btnSimpan.setAlignmentX(Component.RIGHT_ALIGNMENT);
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
								Cetak();
								UpdateStokBarang();
								SimpanIndeksTransaksi();
								while (model.getRowCount() > 0) 
									model.removeRow(0);
								DataBaru();
							} else {
								JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!");
							}
						} else {
							SimpanTransaksiKredit();
							SimpanDetailTransaksiKredit();
							SimpanHutang();
							CetakKredit();
							UpdateStokBarang();
							SimpanIndeksTransaksi();
							while (model.getRowCount() > 0) 
								model.removeRow(0);
							DataBaru();
						}
					}
				}
			}
		});
		btnSimpan.setIcon(new ImageIcon(FTransaksi_old.class.getResource("/img/Simpan.png")));
		btnSimpan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		label_3 = new JLabel("Jatuh Tempo");
		label_3.setBounds(614, 40, 89, 17);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		datePicker = new JXDatePicker();
		datePicker.setBounds(735, 36, 187, 25);
		datePicker.getEditor().setBorder(null);
		datePicker.getEditor().setBackground(SystemColor.control);
		datePicker.getEditor().setFont(new Font("Tahoma", Font.PLAIN, 14));
		datePicker.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton button = new JButton("");
		button.setBounds(377, 65, 50, 32);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FCariPelanggan.main(null);
				window.frame.setEnabled(false);
			}
		});
		button.setFocusPainted(false);
		button.setForeground(Color.LIGHT_GRAY);
		button.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				button.setForeground(Color.LIGHT_GRAY);
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				button.setForeground(Color.LIGHT_GRAY);
			}
		});
		button.setIcon(new ImageIcon(FTransaksi_old.class.getResource("/org/jdesktop/swingx/color/mag.png")));
		button.setOpaque(true);
		button.setContentAreaFilled(false);
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label_4 = new JLabel("ID Karyawan");
		label_4.setBounds(614, 11, 89, 17);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtIdKaryawan = new JTextField();
		txtIdKaryawan.setBounds(735, 7, 187, 25);
		txtIdKaryawan.setEditable(false);
		txtIdKaryawan.setText("no faktur");
		txtIdKaryawan.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtIdKaryawan.setColumns(10);
		txtIdKaryawan.setBorder(null);
		txtIdKaryawan.setBackground(SystemColor.menu);
		
		txtJumlahBayar = new JTextField();
		txtJumlahBayar.setBounds(1159, 639, 195, 27);
		txtJumlahBayar.setText("sub total");
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
									Cetak();
									UpdateStokBarang();
									SimpanIndeksTransaksi();
									while (model.getRowCount() > 0) 
										model.removeRow(0);
									DataBaru();
								} else {
									JOptionPane.showMessageDialog(null, "Lengkapi data terlebih dahulu!");
								}
							} else {
								SimpanTransaksiKredit();
								SimpanDetailTransaksiKredit();
								SimpanHutang();
								CetakKredit();
								UpdateStokBarang();
								SimpanIndeksTransaksi();
								while (model.getRowCount() > 0) 
									model.removeRow(0);
								DataBaru();
							}
						}
					}
				}
			}
		});
		txtJumlahBayar.setHorizontalAlignment(SwingConstants.RIGHT);
		txtJumlahBayar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtJumlahBayar.setColumns(10);
		txtJumlahBayar.setBorder(null);
		txtJumlahBayar.setBackground(SystemColor.menu);
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
		
		txtKembali = new JTextField();
		txtKembali.setBounds(1159, 670, 195, 30);
		txtKembali.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtKembali.setAlignmentY(Component.TOP_ALIGNMENT);
		txtKembali.setEditable(false);
		txtKembali.setText("sub total");
		txtKembali.setHorizontalAlignment(SwingConstants.RIGHT);
		txtKembali.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtKembali.setColumns(10);
		txtKembali.setBorder(null);
		txtKembali.setBackground(SystemColor.menu);
		
		JLabel label_6 = new JLabel("Jumlah Bayar");
		label_6.setBounds(1040, 649, 89, 17);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label_7 = new JLabel("Kembali");
		label_7.setBounds(1040, 677, 89, 17);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(171, 175, 256, 128);
		scrollPane.setVisible(false);
		scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setBorder(null);
		
		list = new JList<>();
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtKodeNama.setText(list.getSelectedValue().toString());
					scrollPane.setVisible(false);
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
					scrollPane.setVisible(false);
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
		list.setBorder(null);
		scrollPane.setColumnHeaderView(list);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNoFaktur);
		frame.getContentPane().add(txtNoFaktur);
		frame.getContentPane().add(label_4);
		frame.getContentPane().add(txtIdKaryawan);
		frame.getContentPane().add(label_2);
		frame.getContentPane().add(txtTanggal);
		frame.getContentPane().add(label_3);
		frame.getContentPane().add(datePicker);
		frame.getContentPane().add(scrollPane_1);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(label_6);
		frame.getContentPane().add(txtJumlahBayar);
		frame.getContentPane().add(btnHapus);
		frame.getContentPane().add(label_7);
		frame.getContentPane().add(txtKembali);
		frame.getContentPane().add(label);
		frame.getContentPane().add(label_1);
		frame.getContentPane().add(txtKodePelanggan);
		frame.getContentPane().add(button);
		frame.getContentPane().add(txtNamaPelanggan);
		frame.getContentPane().add(txtSubTotal);
		frame.getContentPane().add(comboBox);
		frame.getContentPane().add(txtKodeNama);
		frame.getContentPane().add(btnSimpan);
		frame.getContentPane().add(btnBatal);
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
	
	private String SetNoFaktur() {
		String noFaktur = "";
		int count = 0;
		DateFormat df = new SimpleDateFormat("ddMMyy");
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
		DateFormat df = new SimpleDateFormat("ddMMyy");
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
	
	private void CariBarang(String str) {
		db.con();
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
				scrollPane.setVisible(true);
				int i = 0;
				while (rs.next()) {
					hasil[i] = rs.getString("str");
					i++;
				}
			} else {
				scrollPane.setVisible(false);
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
	
	private void AturTabel() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(4);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(0);
	    
	    String[] columnNames = { "Kode Barang", "Nama Barang", "Satuan", "Harga", "Qty", "Total" };
	    model = new DefaultTableModel() {
	    	private static final long serialVersionUID = 1L;
	    	public boolean isCellEditable(int row, int column) {
	    		return false;
	    	}
	    };
	    model.setColumnIdentifiers(columnNames);
	    table.setModel(model);
	    table.setAutoResizeMode(0);
	    table.getColumnModel().getColumn(0).setPreferredWidth(200);
	    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(1).setPreferredWidth(200);
	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
	    table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(3).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	    table.getColumnModel().getColumn(4).setPreferredWidth(100);
	    table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(5).setPreferredWidth(200);
	    table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
	    table.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
		table.setRowHeight(25);
	}
	
	private void SimpanIndeksTransaksi() {
		db.con();
		try {
			String query = "update tb_info set indeks = ?";
			PreparedStatement ps = db.con.prepareStatement(query);
			ps.setString(1, "" +comboBox.getSelectedIndex());
		} catch(Exception e) {
			System.out.println("error simpan indeks transaksi " + e);
		}
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
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan detail transaksi " + e);
		}
	}
	
	private void SimpanTransaksiKredit() {
		String idKaryawan = AmbiIdKaryawan();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat tf = new SimpleDateFormat("HH:mm:ss");
		db.con();
		try {
			String query = "insert into tb_transaksi_kredit values(?,?,?,?,?,?)";
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
			System.out.println("error simpan transaksi " + e);
			JOptionPane.showMessageDialog(null, "Lengkapi data!");
		}
	}
	
	private void SimpanDetailTransaksiKredit() {
		db.con();
		try {
			String query = "insert into tb_detail_transaksi_kredit values(?,?,?,?,?)";
			PreparedStatement ps = null;
			for (int i = 0; i < table.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				ps.setString(1, txtNoFaktur.getText());
				ps.setString(2, table.getValueAt(i, 0).toString());
				ps.setString(3, table.getValueAt(i, 3).toString().replace(",", ""));
				ps.setString(4, table.getValueAt(i, 2).toString());
				ps.setString(5, table.getValueAt(i, 4).toString());
				ps.execute();
			}
			ps.close();
			db.con.close();
		} catch(Exception e) {
			System.out.println("error simpan detail transaksi " + e);
		}
	}
	
	private void UpdateStokBarang() {
		db.con();
		try {
			String query = "update tb_detail_barang set stok = stok - ? "
					+ "where id_barang = ? and satuan = ?";
			PreparedStatement ps = null;
			for (int i = 0; i < table.getRowCount(); i++) {
				ps = db.con.prepareStatement(query);
				ps.setDouble(1, Double.parseDouble(table.getValueAt(i, 4).toString()));
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
					FTransaksi_old.window.frame.setEnabled(true);
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
					FTransaksi_old.window.frame.setEnabled(true);
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
