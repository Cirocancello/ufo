package it.polito.tdp.ufo.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SightingDAO {
	
	public List<String> readShapes(){		
		try {
			Connection conn = DBConnect.getConnection(); //apro la connessione
			
			//sono equivalential db viene inviata su una sola riga			
	    	// String sql = "SELECT DISTINCT shape FROM sighting WHERE shape <> '' ORDER BY shape ASC";
		
		    String sql = "SELECT DISTINCT shape " //stringa non parametrica che restituisce una lista di dati
		    			+ "FROM sighting "
		    			+ "WHERE shape <> '' "
					    + "ORDER BY shape ASC";
		
		    PreparedStatement st = conn.prepareStatement(sql); // preparo lo statament		
		
		    ResultSet res = st.executeQuery(); // eseguo la Query 
        
		    List<String> formeUFO = new ArrayList<>();
		    
		    //carico nella lista i dati ritornati dalla Query
		    while( res.next()) {
		    	String forma = res.getString("shape"); // nome della colonna nelle Query
		    	formeUFO.add(forma);				
		    }
		    
		    st.close(); // chiude lo statament
		    conn.close();// chiudo la connessione
		    return formeUFO;
		    
		} catch (SQLException e) {			
			throw new RuntimeException("Database error in readShape", e);
		} 
	}
	
	public int countByShape(String shape) {
		
		try {
			Connection conn = DBConnect.getConnection();

			String sql2 = "SELECT count(*) AS cnt "
					   +	"FROM sighting "
					   +	"WHERE shape = ? ";//contiene un parametro				
								
				PreparedStatement st2 = conn.prepareStatement(sql2);				
				st2.setString(1, shape); // 1 indica il primo parametro e lo sostituisce la stringa shapeScelte
				
				ResultSet res2 = st2.executeQuery();				
				res2.first();//posiziono il cursore sulla prima riga
				
				int count = res2.getInt("cnt");
				
				st2.close();
				conn.close();
				
				return count;
				
		} catch (SQLException e) {
			throw new RuntimeException("Database Error in countByShape",e);
		}
	}
}
