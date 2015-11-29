/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stokbarangpddj.ToolAndClass.Backup;

import stokbarangpddj.ToolAndClass.Tools;
import Sales.*;
import Store.Cust;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.Login;
import stokbarangpddj.MenuUtama;
import stokbarangpddj.ToolAndClass.AllClass;

/**
 *
 * @author Susanto
 */
public class Sales_BC extends javax.swing.JInternalFrame {
private String sqlcomboboxCons;
private String ComboAddCons=" ";
private String sqlcomboboxPay;
private String ComboAddPay=" ";
private String sqlcomboboxCat;
private String ComboAddCat=" ";
private int num,shipping,idcus = 0,idpay = 0;
private double disc = 0,totalrp,oldprice,timesprice,finalprice,discprice;
public int oldinv = 0;
public int totalinv,totalqty;
DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
DecimalFormatSymbols dfs = new DecimalFormatSymbols();
AllClass StoreClass = new AllClass();
    /**
     * Creates new form Sales
     */
    public Sales_BC() {
        initComponents();
//        IDS.setText(AllClass.GenerateID("sales"));
////        ComboxConsumen();
////        ComboxPayment();
////        updateTableProduct();
////        updatecart();
////        ComboxCategory();
////        startup();
    }
    
    public void clear1(){
        IDSPP.setText("0");
        pr.setText("");
        txtdisc.setText("");
        TXTQTY.setText("");
        
    }
    
    public void ComboxCategory(){
    try {
        sqlcomboboxCat="select Name from Category";
        Conn.rowSet.setCommand(sqlcomboboxCat);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                ComboAddCat=Conn.rowSet.getString(1);
                ComboxCategory.addItem(ComboAddCat); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
    }
    }  
    
    public void ComboxConsumen(){
    try {
        sqlcomboboxCons="select name from consumer";
        Conn.rowSet.setCommand(sqlcomboboxCons);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                ComboAddCons=Conn.rowSet.getString(1);
                ComboCons.addItem(ComboAddCons); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public void ComboxPayment(){
    try {
        sqlcomboboxPay="select Type_Payment from payment";
        Conn.rowSet.setCommand(sqlcomboboxPay);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                ComboAddPay=Conn.rowSet.getString(1);
                ComboxPayment.addItem(ComboAddPay); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
    }
    }    

    public void updateTableProduct(){
        if(ComboxCategory.getSelectedItem().equals("All"))
            {
                try {
                    Conn.rowSet.setCommand("select idinventory As ID, name As Name, unit as Unit, qty As QTY, price_Sale As Price from inventory where qty > 0");
                    Conn.rowSet.execute();
                    ProductTab.setModel(DbUtils.resultSetToTableModel(Conn.rowSet));
                } catch (SQLException ex) {
                        Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    Conn.rowSet.setCommand("select idCategory from Category where name = '" + ComboxCategory.getSelectedItem() + "'");
                    Conn.rowSet.execute();
                    while (Conn.rowSet.next()) {
                        num = Conn.rowSet.getInt(1);
                    }
                    Conn.rowSet.setCommand("select idinventory As ID, name As Name, unit as Unit, qty As QTY, price_Sale As Price from inventory where qty > 0 AND idCategory = '" + num + "'");
                    Conn.rowSet.execute();
                    ProductTab.setModel(DbUtils.resultSetToTableModel(Conn.rowSet));
                } catch (SQLException ex) {
                        Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
    public void updatecart(){
        try {
                Conn.rowSet.setCommand("select s.idinventory as ID, i.name as Name, i.unit as Unit, s.qty as QTY, s.Discount as Potongan, s.price as Price from sales_detail s, inventory i where s.idinventory = i.idinventory and idsales = '" + IDS.getText()+ "'");
                Conn.rowSet.execute();
                CartTab.setModel(DbUtils.resultSetToTableModel(Conn.rowSet));
        } catch (SQLException ex) {
                Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    private void startup(){
        try {
                // TODO add your handling code here:
                Conn.rowSet.setCommand("select * from sales");
                Conn.rowSet.execute();
                Conn.rowSet.moveToInsertRow();
                Conn.rowSet.updateInt("idsales", Integer.parseInt(IDS.getText()));
//                Conn.rowSet.updateInt("idusername", Login.userID);
                Conn.rowSet.updateTimestamp("Date", new Timestamp(System.currentTimeMillis()));
                Conn.rowSet.insertRow();
                updateTableProduct();
        } catch (SQLException ex) {
                Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void AddTrans() {
        try {
            String Insert = "select * from sales_detail";
            Conn.rowSet.setCommand(Insert);
            Conn.rowSet.execute();
            Conn.rowSet.moveToInsertRow();
            Conn.rowSet.updateInt("idsales", Integer.parseInt(IDS.getText()));
            Conn.rowSet.updateInt("idinventory", Integer.parseInt(IDSPP.getText()));
            Conn.rowSet.updateInt("qty", Integer.parseInt(TXTQTY.getText()));
            timesprice = Integer.parseInt(TXTQTY.getText()) * Double.parseDouble(pr.getText());
            if(PriceBut.isSelected() == true)
            {
                discprice = ((oldprice * Integer.parseInt(TXTQTY.getText())) - timesprice);
                Conn.rowSet.updateDouble("price", timesprice);
                Conn.rowSet.updateDouble("Discount", discprice);
            }
            else if(DiscBut.isSelected() == true)
            {
                discprice = ((timesprice * Integer.parseInt(txtdisc.getText())) / 100);
                finalprice = timesprice - discprice;
                Conn.rowSet.updateDouble("Discount", discprice);
                Conn.rowSet.updateDouble("price", finalprice);
            }
            Conn.rowSet.insertRow();
            Conn.rowSet.setCommand("select * from inventory where idinventory = '" + IDSPP.getText() + "'");
            Conn.rowSet.execute();
            Conn.rowSet.next();
            oldinv = Conn.rowSet.getInt("qty");
            totalinv = oldinv - Integer.parseInt(TXTQTY.getText());
            System.out.print(totalinv);
            Conn.rowSet.updateInt("qty", totalinv);
            Conn.rowSet.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }
        total_transaksi();
        updatecart();
        updateTableProduct();
    }
    
    private void DeleteTrans() {
        try {
            int baris = CartTab.getSelectedRow();
            Conn.rowSet.setCommand("select * from sales_detail where idsales = '" + IDS.getText() + "'");
            Conn.rowSet.execute();
            Conn.rowSet.absolute(baris+1);
            Conn.rowSet.deleteRow();
            Conn.rowSet.setCommand("select * from inventory where idinventory = '" + IDSPP.getText() + "'");
            Conn.rowSet.execute();
            Conn.rowSet.next();
            oldinv = Conn.rowSet.getInt("qty");
            totalinv = oldinv + Integer.parseInt(TXTQTY.getText());
            Conn.rowSet.updateInt("qty", totalinv);
            Conn.rowSet.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }
        total_transaksi();
        updatecart();
        updateTableProduct();
    }
    
    private void DoneTrans(){
        try{
            String DoneOnly = "select * from sales where idsales = " + IDS.getText() + "";
            Conn.rowSet.setCommand(DoneOnly);
            Conn.rowSet.execute();
            Conn.rowSet.first();
            Conn.rowSet.updateInt("idconsumer", idcus);
            Conn.rowSet.updateInt("idpayment", idpay);
            Conn.rowSet.updateDouble("Biaya_Kirim", shipping);
            Conn.rowSet.updateDouble("Discount", disc);
            Conn.rowSet.updateString("Comment", TXTComment.getText());
            Conn.rowSet.updateDouble("Total_Payment", totalrp);
            Conn.rowSet.updateRow();
        }catch (SQLException ex) {
            Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(idcus + "" + idpay);
    }
    
    private void total_transaksi() {
        try {
            dfs.setCurrencySymbol("");
            dfs.setMonetaryDecimalSeparator(',');
            dfs.setGroupingSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            String total = "select sum(qty), sum(price), sum(Discount) from sales_detail where idsales = " + IDS.getText() + "";
            Conn.rowSet.setCommand(total);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                totalrp = Conn.rowSet.getDouble(2);
                totalqty = Conn.rowSet.getInt(1);
                disc = Conn.rowSet.getInt(3);
            }
            TXTPayment.setText("Rp. " + df.format(totalrp));
            TOTALQTY.setText(String.valueOf(totalqty));
            TXTDisc.setText(String.valueOf(disc));
            String InsertTot = "select * from sales where idsales = " + IDS.getText() + "";
            Conn.rowSet.setCommand(InsertTot);
            Conn.rowSet.execute();
            Conn.rowSet.first();
            Conn.rowSet.updateDouble("Discount", disc);
            Conn.rowSet.updateDouble("Total_Payment", totalrp);
            Conn.rowSet.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    private void profitcal(){
//        try {
//            Conn.rowSet.setCommand("select price_Sale from inventory where idinventory = '" + IDSPP.getText() + "'");
//            Conn.rowSet.execute();
//            sellprice = (Double.parseDouble(TXTQTY.getText()) * Conn.rowSet.getDouble(1));
//            Conn.rowSet.setCommand("select * from inventory where idinventory = '" + IDSPP.getText() + "'");
//            Conn.rowSet.execute();
//            Conn.rowSet.next();
//            Conn.rowSet.updateInt("qty", totalinv);
//            Conn.rowSet.updateRow();
//        } catch (SQLException ex) {
//            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DiscAndPriceBut = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        IDS = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ComboCons = new javax.swing.JComboBox();
        RegisterBut = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        ComboxPayment = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        TXTComment = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ProductTab = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        CartTab = new javax.swing.JTable();
        AddToCartBut = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        IDSPP = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TXTQTY = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        TOTALQTY = new javax.swing.JLabel();
        TXTDisc = new javax.swing.JLabel();
        TXTPayment = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ComboxCategory = new javax.swing.JComboBox();
        DeleteBut = new javax.swing.JButton();
        pr = new javax.swing.JTextField();
        txtdisc = new javax.swing.JTextField();
        PriceBut = new javax.swing.JRadioButton();
        DiscBut = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Combo_Kirim = new javax.swing.JComboBox();
        BackBut = new javax.swing.JButton();
        DoneBut = new javax.swing.JButton();

        setResizable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sales Form", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel1.setText("ID Sales :");

        IDS.setText("AUTO GENERATE");

        jLabel3.setText("Consumer :");

        ComboCons.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select..." }));
        ComboCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboConsActionPerformed(evt);
            }
        });

        RegisterBut.setText("Register New");
        RegisterBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterButActionPerformed(evt);
            }
        });

        jLabel4.setText("Payment   :");

        ComboxPayment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select..." }));
        ComboxPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboxPaymentActionPerformed(evt);
            }
        });

        jLabel5.setText("Comment  :");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "List Product", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        ProductTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Unit", "Stock", "Price", "QTY", "Disc/Unit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ProductTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductTabMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(ProductTab);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cart", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        CartTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(CartTab);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        AddToCartBut.setText("Add To Cart");
        AddToCartBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToCartButActionPerformed(evt);
            }
        });

        jLabel6.setText("ID Product :");

        IDSPP.setText("0");

        jLabel8.setText("QTY :");

        jLabel9.setText("Total QTY :");

        jLabel10.setText("Potongan   :");

        jLabel11.setText("Total Payment :");

        TOTALQTY.setText("0");

        TXTDisc.setText("0");

        TXTPayment.setText("0");

        jLabel15.setText("Product Category :");

        ComboxCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All" }));
        ComboxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboxCategoryActionPerformed(evt);
            }
        });

        DeleteBut.setText("Delete");
        DeleteBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButActionPerformed(evt);
            }
        });

        DiscAndPriceBut.add(PriceBut);
        PriceBut.setSelected(true);
        PriceBut.setText("Price :");

        DiscAndPriceBut.add(DiscBut);
        DiscBut.setText("Potongan :");

        jLabel2.setText("%");

        jLabel12.setText("Biaya Kirim :");

        Combo_Kirim.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rp. 0", "Rp. 50.000", "-", "Rp. 55.000", "-", "Rp. 100.000", "-", "Rp. 150.000", "-", "Rp. 200.000", "-", "Rp. 250.000", "-", "Rp. 300.000", "-", "Rp. 350.000", "-", "Rp. 400.000", "-", "Rp. 450.000", "-", "Rp. 500.000", "-", "Rp. 550.000", "-", "Rp. 600.000", "-" }));
        Combo_Kirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_KirimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jLabel10))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TXTPayment)
                                            .addComponent(TXTDisc)
                                            .addComponent(TOTALQTY)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Combo_Kirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(DiscBut)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(IDSPP, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(PriceBut)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(pr, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(txtdisc, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel2)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(DeleteBut, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TXTComment, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboxPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IDS))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboCons, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RegisterBut, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TXTQTY, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddToCartBut, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(54, 54, 54))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(IDS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(ComboCons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RegisterBut))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ComboxPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(TXTComment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(ComboxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DiscBut)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DeleteBut)
                            .addComponent(pr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PriceBut))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(IDSPP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(TXTQTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddToCartBut))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TOTALQTY)
                    .addComponent(jLabel12)
                    .addComponent(Combo_Kirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TXTDisc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(TXTPayment))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BackBut.setText("Back");
        BackBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButActionPerformed(evt);
            }
        });

        DoneBut.setText("Done");
        DoneBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 550, Short.MAX_VALUE)
                .addComponent(DoneBut, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackBut, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DoneBut)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboxCategoryActionPerformed
        updateTableProduct();
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboxCategoryActionPerformed

    private void ComboxPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboxPaymentActionPerformed
//    try {
//            dfs.setCurrencySymbol("");
//            dfs.setMonetaryDecimalSeparator(',');
//            dfs.setGroupingSeparator('.');
//            df.setDecimalFormatSymbols(dfs);
//            Conn.rowSet.setCommand("select Discount from payment where Type_Payment = '" + ComboxPayment.getSelectedItem() + "'");
//            Conn.rowSet.execute();
//            while(Conn.rowSet.next()){
//            dis = Conn.rowSet.getInt(1);
//            }
//            disc = ((Double.parseDouble(TXTPayment.getText()) * dis )/100);
//            TXTDisc.setText("Rp." + df.format(Double.toString(disc)));
//    } catch (SQLException ex) {
//            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
//    }
        // TODO add your handling code here:
        
    if(ComboxPayment.getSelectedItem().equals("Select..."))
    {
        idcus = 0;
    }
    else
    {    
        try {
            Conn.rowSet.setCommand("select idpayment from payment where Type_Payment = '" + ComboxPayment.getSelectedItem() + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                    idpay=Conn.rowSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }//GEN-LAST:event_ComboxPaymentActionPerformed

    private void BackButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButActionPerformed
    if(TOTALQTY.getText().equals("0"))
        {
            MenuUtama MenuUtama = new MenuUtama();
            Tools.change(this, MenuUtama);
            try{
                Conn.rowSet.setCommand("select * from sales where idsales = '" + IDS.getText() + "'");
                Conn.rowSet.execute();
                while(Conn.rowSet.next())
                {
                    Conn.rowSet.deleteRow();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please Select Done Button First!!");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButActionPerformed

    private void RegisterButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterButActionPerformed
            Cust Cust = new Cust();
            Tools.change(this, Cust);
        if(TOTALQTY.getText().equals("0"))
        {
            try{
                Conn.rowSet.setCommand("select * from sales where idsales = '" + IDS.getText() + "'");
                Conn.rowSet.execute();
                while(Conn.rowSet.next())
                {
                    Conn.rowSet.deleteRow();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        else
        {
                        try{
                Conn.rowSet.setCommand("select * from sales where idsales = '" + IDS.getText() + "'");
                Conn.rowSet.execute();
                while(Conn.rowSet.next())
                {
                    Conn.rowSet.deleteRow();
                }
                Conn.rowSet.setCommand("select * from sales where sales_detail = '" + IDS.getText() + "'");
                Conn.rowSet.execute();
                while(Conn.rowSet.next())
                {
                    Conn.rowSet.deleteRow();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_RegisterButActionPerformed

    private void ProductTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductTabMouseClicked
    int baris = ProductTab.getSelectedRow();
    IDSPP.setText(ProductTab.getValueAt(baris, 0).toString());
    pr.setText(ProductTab.getValueAt(baris, 4).toString());
    oldprice = Double.parseDouble(pr.getText());
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductTabMouseClicked

    private void DoneButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneButActionPerformed
        //Custom button text
        Object[] options = {"Cancel","Done + Print","Done"};
        int n = JOptionPane.showOptionDialog(null,
            "Order ID : " + IDS.getText() +"\n"
            + "Total Payment : " + TXTPayment.getText() +"\n"
            + "Total Items : " + TOTALQTY.getText() +"\n"
            + "Shipping : " + Combo_Kirim.getSelectedItem(),
            "Confirmation Orders",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[2]);
        if(n == JOptionPane.NO_OPTION)
        {
            if(idcus == 0 || idpay == 0)
            {
                JOptionPane.showMessageDialog(null, "Please Select Your Payment Type Or Customer Name.");
            }
            else
            {
                
            }
        }
        else if(n == JOptionPane.CANCEL_OPTION)
        {
            if(idcus == 0 || idpay == 0)
            {
                JOptionPane.showMessageDialog(null, "Please Select Your Payment Type Or Customer Name.");
            }
            else
            {
                DoneTrans();
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_DoneButActionPerformed

    private void ComboConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboConsActionPerformed
    if(ComboCons.getSelectedItem().equals("Select..."))
    {
        idcus = 0;
    }
    else
    {
        try {
            Conn.rowSet.setCommand("select idconsumer from consumer where name = '" + ComboCons.getSelectedItem() + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                    idcus=Conn.rowSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_BC.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    // TODO add your handling code here:
    }//GEN-LAST:event_ComboConsActionPerformed

    private void Combo_KirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_KirimActionPerformed
    if(Combo_Kirim.getSelectedItem().equals("Rp.0"))
    {
        shipping = 0;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.50.000"))
    {
        shipping = 50000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.100.000"))
    {
        shipping = 100000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.150.000"))
    {
        shipping = 150000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.200.000"))
    {
        shipping = 200000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.250.000"))
    {
        shipping = 250000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.300.000"))
    {
        shipping = 300000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.350.000"))
    {
        shipping = 350000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.400.000"))
    {
        shipping = 400000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.450.000"))
    {
        shipping = 450000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.500.000"))
    {
        shipping = 500000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.550.000"))
    {
        shipping = 550000;
    }
    else if (Combo_Kirim.getSelectedItem().equals("Rp.600.000"))
    {
        shipping = 600000;
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_Combo_KirimActionPerformed

    private void DeleteButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButActionPerformed
        if(IDSPP.getText().equals("0") || TXTQTY.getText().isEmpty() == true)
        {
            JOptionPane.showMessageDialog(null, "Please Select Product On Your Cart!!");
        }
        else
        {
            DeleteTrans();
            JOptionPane.showMessageDialog(null, "Delete IS Complete!!");
            clear1();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteButActionPerformed

    private void AddToCartButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToCartButActionPerformed
        if(TXTQTY.getText().isEmpty() == true || ComboCons.getSelectedItem().equals("Select..."))
        {
            JOptionPane.showMessageDialog(null, "Please Insert QTY Product!! OR Select Your Customer Name!!");
        }
        else
        {
            AddTrans();
            clear1();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AddToCartButActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddToCartBut;
    private javax.swing.JButton BackBut;
    private javax.swing.JTable CartTab;
    private javax.swing.JComboBox ComboCons;
    private javax.swing.JComboBox Combo_Kirim;
    private javax.swing.JComboBox ComboxCategory;
    private javax.swing.JComboBox ComboxPayment;
    private javax.swing.JButton DeleteBut;
    private javax.swing.ButtonGroup DiscAndPriceBut;
    private javax.swing.JRadioButton DiscBut;
    private javax.swing.JButton DoneBut;
    private javax.swing.JLabel IDS;
    private javax.swing.JLabel IDSPP;
    private javax.swing.JRadioButton PriceBut;
    private javax.swing.JTable ProductTab;
    private javax.swing.JButton RegisterBut;
    private javax.swing.JLabel TOTALQTY;
    private javax.swing.JTextField TXTComment;
    private javax.swing.JLabel TXTDisc;
    private javax.swing.JLabel TXTPayment;
    private javax.swing.JTextField TXTQTY;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField pr;
    private javax.swing.JTextField txtdisc;
    // End of variables declaration//GEN-END:variables
}
