package source;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Up {
	
	public static void main (String arg[]) {
		Database db = new Database();
		db.con();
		try {
			String query = "select no_faktur, date_format(tanggal, '%Y-%m-%d') as tanggal from tb_transaksi";
			Statement st = db.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String tanggal = rs.getString("tanggal").replace("09", "10");
				String query2 = "update tb_transaksi set tanggal = ? where no_faktur = ?";
				PreparedStatement ps = db.con.prepareStatement(query2);
				ps.setString(1, tanggal);
				ps.setString(2, rs.getString("no_faktur"));
				ps.execute();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
