package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FBackup {

	JFrame frame;
	static FBackup window;
	private JTextField txtBrowse;
	private JLabel lblNewLabel;
	String path = null;
	String filename = null;
	Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FBackup();
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
	public FBackup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Backup Database");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
			@Override
			public void windowOpened(WindowEvent e) {
				db = new Database();
				db.readUser();
			}
		});
		frame.setBounds(100, 100, 409, 146);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtBrowse = new JTextField();
		txtBrowse.setEditable(false);
		txtBrowse.setBounds(24, 29, 247, 26);
		frame.getContentPane().add(txtBrowse);
		txtBrowse.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnBrowse);
				String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				try {
					File file = fc.getSelectedFile();
					path = file.getAbsolutePath();
					path = path.replace("\\", "/");
					path = path + "_" + date + ".sql";
					txtBrowse.setText(path);
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		});
		btnBrowse.setBounds(283, 29, 97, 26);
		frame.getContentPane().add(btnBrowse);
		
		JButton btnBackup = new JButton("Backup");
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Process p = null;
				try {
					@SuppressWarnings("unused")
					Runtime runtime = Runtime.getRuntime();
//					String save = db.path + "mysqldump.exe\" -u" + db.user + " -p" + db.pass + " --add-drop-database db_app_penjualan -r\"" + path +"\"";
//					System.out.println(save);
					p = Runtime.getRuntime().exec(Database.path + " -u"+Database.user+" -p"+Database.pass+" db_app_penjualan -r \"" + path + "\"");
					System.out.println(Database.path + " -uroot -proot db_app_penjualan -r \"" + path + "\"");
					int processComplete = p.waitFor();
					if (processComplete == 0) {
						lblNewLabel.setText("Backup created successfull...");
					} else {
						lblNewLabel.setText("Failed created backup...");
					}
				} catch(Exception e) {
					System.out.println(e);
				}
				
			}
		});
		btnBackup.setBounds(24, 67, 97, 26);
		frame.getContentPane().add(btnBackup);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(126, 72, 254, 16);
		frame.getContentPane().add(lblNewLabel);
	}
}
