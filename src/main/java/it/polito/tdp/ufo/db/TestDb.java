package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDb {

	public static void main(String[] args) {
		
		String jdbcURL = "jdbc:mysql://localhost/ufo_sightings?user=root&password=root";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL); //apre la connessione
			
			//sono equivalential db viene inviata su una sola riga			
			// String sql = "SELECT DISTINCT shape FROM sighting WHERE shape <> '' ORDER BY shape ASC";
			
			String sql = "SELECT DISTINCT shape "
					+ "FROM sighting "
					+ "WHERE shape <> '' "
					+ "ORDER BY shape ASC";
			
			PreparedStatement st = conn.prepareStatement(sql); // preparo lo statament
			
			
			
			ResultSet res = st.executeQuery(); // eseguo la Query 
	        
			List<String> formeUFO = new ArrayList<>();
			while( res.next()) {
				String forma = res.getString("shape"); // nome della colonna nelle Query
				formeUFO.add(forma);				
			}
			st.close(); // chiude lo statament
			
			System.out.println(formeUFO);
			
			String sql2 = "SELECT count(*) AS cnt "
				   +	"FROM sighting "
				   +	"WHERE shape = ? ";
			
			String shapeScelta = "circle";
			
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.setString(1, shapeScelta);
			ResultSet res2 = st2.executeQuery();
			res2.first();//posiziono il cursore sulla prima riga
			int count = res2.getInt("cnt");
			st2.close();
			
			System.out.println("UFO di forma " + shapeScelta + " sono " + count);
			
			
			conn.close(); // chiude la connessione
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
