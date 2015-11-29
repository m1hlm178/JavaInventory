/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stokbarangpddj.ToolAndClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Susanto
 */
public class AllClass {
    static String databack = "";
    public static String GenerateID(String Data,String Query) {
//        System.out.print(Data);
        String hasil = "";
        int count = 0;
        int id = 0;
        
        try {
            String sql = "select MAX(right("+Query+",2)) from " + Data ;
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            if(Data.equals("sales"))
            {
                while (Conn.rowSet.next())
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJS0001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "000" + no;
                        }else if(noLong==2){
                            no = "00" + no;
                        }else if(noLong==3){
                            no = "0" + no;
                        }
                        hasil = ("DJS" + no);
                }
            }
            else if(Data.equals("purchase"))
            {
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJP0001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "000" + no;
                        }else if(noLong==2){
                            no = "00" + no;
                        }else if(noLong==3){
                            no = "0" + no;
                        }
                        hasil = ("DJP" + no);
                }
            } 
            }else if(Data.equals("user")){
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJU001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "00" + no;
                        }else if(noLong==2){
                            no = "0" + no;
                        }
                        hasil = ("DJU" + no);
//                        System.out.println(hasil);
//                        count = Integer.parseInt(hasil);
                }
            }}else if(Data.equals("payment")){
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJTP001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "00" + no;
                        }else if(noLong==2){
                            no = "0" + no;
                        }
                        hasil = ("DJTP" + no);
//                        System.out.println(hasil);
//                        count = Integer.parseInt(hasil);
                }
            }}else if(Data.equals("distributor")){
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJD001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "00" + no;
                        }else if(noLong==2){
                            no = "0" + no;
                        }
                        hasil = ("DJD" + no);
//                        System.out.println(hasil);
//                        count = Integer.parseInt(hasil);
                }
            }}else if(Data.equals("category")){
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJIC001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "00" + no;
                        }else if(noLong==2){
                            no = "0" + no;
                        }
                        hasil = ("DJIC" + no);
//                        System.out.println(hasil);
//                        count = Integer.parseInt(hasil);
                }
            }}else if(Data.equals("inventory")){
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJI001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "00" + no;
                        }else if(noLong==2){
                            no = "0" + no;
                        }
                        hasil = ("DJI" + no);
//                        System.out.println(hasil);
//                        count = Integer.parseInt(hasil);
                }
            }}else if(Data.equals("hist_price")){
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJH001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "00" + no;
                        }else if(noLong==2){
                            no = "0" + no;
                        }
                        hasil = ("DJH" + no);
//                        System.out.println(hasil);
//                        count = Integer.parseInt(hasil);
                }
            }}else if(Data.equals("consumer")){
                while (Conn.rowSet.next()) {
                if(Conn.rowSet.first() == false)
                {
                        count = Integer.parseInt("DJC001");
                }
                else
                {
                        Conn.rowSet.last();
                        count = Conn.rowSet.getInt(1) + 1;
                        String no = String.valueOf(count);
                        int noLong = no.length();
                        if(noLong==1){
                            no = "00" + no;
                        }else if(noLong==2){
                            no = "0" + no;
                        }
                        hasil = ("DJC" + no);
//                        System.out.println(hasil);
//                        count = Integer.parseInt(hasil);
                }
            }}
        } catch (SQLException ex) {
            Logger.getLogger(AllClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    
    public static String GetIpPublic() throws IOException{
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
    //        System.out.println(ip);
            return databack = ip;
        } catch (Exception e) {
            return databack = "0.0.0.0";
        }
    }
}
