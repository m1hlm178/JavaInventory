/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stokbarangpddj.ToolAndClass;

import com.sun.rowset.JdbcRowSetImpl;
import javax.sql.RowSet;

/**
 *
 * @author admin
 */
public class Conn {
    private static String username = "root";
    private static String password = "samsung";
//    private static String password = "";
    private static String driver = "com.mysql.jdbc.Driver";
//    private static String url = "jdbc:mysql://home.liatcctv.us:3306/dianjayan";
    private static String url = "jdbc:mysql://192.168.2.2:3306/dianjayan";
//    private static String url = "jdbc:mysql://localhost:3306/dianjayan";
    public static RowSet rowSet;
//    public static RowSet rowSet2;
	
    public static void setConnection() {
        try {
            Class.forName(driver);
            // Create a row set
			rowSet = new JdbcRowSetImpl();

			// Set RowSet properties
			rowSet.setUrl(url);
			rowSet.setUsername(username);
			rowSet.setPassword(password);
			
//			// Create a row set
//			rowSet2 = new JdbcRowSetImpl();
//
//			// Set RowSet properties
//			rowSet2.setUrl(url);
//			rowSet2.setUsername(username);
//			rowSet2.setPassword(password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
