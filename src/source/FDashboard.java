package source;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

public class FDashboard {

	JFrame frame;
	static FDashboard window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FDashboard();
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
	public FDashboard() {
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
			
			}
			@Override
			public void windowClosing(WindowEvent e) {
				FMain.window.frame.setEnabled(true);
				window.frame.dispose();
			}
		});
		frame.setBounds(100, 100, 1113, 778);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 255, 255, 255, 0};
		gridBagLayout.rowHeights = new int[]{0, 111, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setForeground(UIManager.getColor("Button.default.foreground"));
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnNewButton.setBackground(UIManager.getColor("Actions.Red"));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setForeground(UIManager.getColor("Button.default.foreground"));
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnNewButton_1.setBackground(UIManager.getColor("Actions.Blue"));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 1;
		frame.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("New button");
		btnNewButton_1_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		btnNewButton_1_1.setForeground(UIManager.getColor("Button.default.foreground"));
		btnNewButton_1_1.setBackground(UIManager.getColor("Actions.Green"));
		GridBagConstraints gbc_btnNewButton_1_1 = new GridBagConstraints();
		gbc_btnNewButton_1_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1_1.gridx = 3;
		gbc_btnNewButton_1_1.gridy = 1;
		frame.getContentPane().add(btnNewButton_1_1, gbc_btnNewButton_1_1);
	}

}
