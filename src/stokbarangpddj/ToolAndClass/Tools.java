/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stokbarangpddj.ToolAndClass;

import java.beans.PropertyVetoException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.Timer;
import stokbarangpddj.FrameUtama;
import stokbarangpddj.Login;
import stokbarangpddj.ToolAndClass.Conn;

/**
 *
 * @author admin
 */
public class Tools {
        Timer waktu;
	public static final int maxTable=20;
	
	public static void change(JInternalFrame from, JInternalFrame to){
        FrameUtama.DekstopFrame.add(to);
        to.setVisible(true);
            try {
                to.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            }
        from.dispose();
	}
	
	public static void addCmb(JComboBox cmb) throws SQLException{
		while (Conn.rowSet.next()) {
			cmb.addItem(Conn.rowSet.getString(1)); System.out.println(Conn.rowSet.getString(1));
		}
	}
        
        public static boolean isNumeric(String str)
        {
          return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
        }
        
        public static String MD5Encrypt(String passwordToHash){
            String generatedPassword = null;
            try {
                // Create MessageDigest instance for MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                //Add password bytes to digest
                md.update(passwordToHash.getBytes());
                //Get the hash's bytes
                byte[] bytes = md.digest();
                //This bytes[] has bytes in decimal format;
                //Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                //Get complete hashed password in hex format
                generatedPassword = sb.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
            return generatedPassword;
        }
    }
