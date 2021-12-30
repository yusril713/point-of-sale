package source;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class FRestore {

	JFrame frame;
	static FRestore window;
	private JTextField txtBrowse;
	Database db;
	String path = null;
	String filename = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FRestore();
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
	public FRestore() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Restore Database");
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
		frame.setBounds(100, 100, 437, 130);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtBrowse = new JTextField();
		txtBrowse.setBounds(16, 18, 277, 26);
		frame.getContentPane().add(txtBrowse);
		txtBrowse.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnBrowse);
				try {
					File f = fc.getSelectedFile();
					path = f.getAbsolutePath();
					path = path.replace("\\", "/");
					txtBrowse.setText(path);
				} catch(Exception er) {
					System.out.println(er);
				}
			}
		});
		btnBrowse.setBounds(305, 18, 97, 26);
		frame.getContentPane().add(btnBrowse);
		
		JButton btnRestore = new JButton("Restore");
		btnRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            /*NOTE: String s is the mysql file name including the .sql in its name*/
		            /*NOTE: Getting path to the Jar file being executed*/
		            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
		            CodeSource codeSource = FRestore.class.getProtectionDomain().getCodeSource();
		            File jarFile = new File(codeSource.getLocation().toURI().getPath());
		            @SuppressWarnings("unused")
					String jarDir = jarFile.getParentFile().getPath();

		            /*NOTE: Creating Database Constraints*/
		             String dbName = "db_app_penjualan";
		             String dbUser = Database.user;
		             String dbPass = Database.pass;

		            /*NOTE: Creating Path Constraints for restoring*/
		            String restorePath = path;

		            /*NOTE: Used to create a cmd command*/
		            /*NOTE: Do not create a single large string, this will cause buffer locking, use string array*/
		            String[] executeCmd = new String[]{"mysql", dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + restorePath};

		            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
		            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
		            int processComplete = runtimeProcess.waitFor();

		            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
		            if (processComplete == 0) {
		                JOptionPane.showMessageDialog(null, "Successfully restored from SQL : " + path);
		            } else {
		                JOptionPane.showMessageDialog(null, "Error at restoring");
		            }


		        } catch (URISyntaxException | IOException | InterruptedException | HeadlessException ex) {
		            JOptionPane.showMessageDialog(null, "Error at Restoredbfromsql" + ex.getMessage());
		        }
			}
		});
		btnRestore.setBounds(16, 53, 97, 26);
		frame.getContentPane().add(btnRestore);
	}
}
