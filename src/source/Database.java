package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Database {
	static String user = null;
	static String pass = null;
	private String host = null;
	public Connection con = null;
	static String path = null;
	static String PORT = null;
	static String BITS_PER_SECOND = null;
	static String DATA_BITS = null;
	static String STOP_BITS = null;
	static String PARITY = null;
	
	public void con(){
		readUser();
		try {
			String url="jdbc:mysql://" + host + "/db_app_penjualan?autoReconnect=true&useSSL=false";
			Class.forName("com.mysql.jdbc.Driver");
			con =  DriverManager.getConnection(url, user , pass);
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, "Koneksi Database Gagal " + e);
		}
	}
	
	public  void readUser() {
		String fileName = "setserver";
		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			user = bufferedReader.readLine();
			pass = bufferedReader.readLine();
			host = bufferedReader.readLine();
			path = bufferedReader.readLine();
			
			PORT = bufferedReader.readLine();
			BITS_PER_SECOND = bufferedReader.readLine();
			DATA_BITS = bufferedReader.readLine();
			STOP_BITS = bufferedReader.readLine();
			PARITY = bufferedReader.readLine();
			bufferedReader.close();
		}catch (Exception e){
			e = null;
			System.gc();
		}
	}
}
