/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.ToolAndClass.NumberRenderer;
import stokbarangpddj.ToolAndClass.Tools;

//Notes 
//Edit Bagian Sales untuk pengurangan QTY PRIMARY DENGAN QTY RETURN DISCOUNT DAN PRICE DI QTY Final 050215
//Masi terjadi error terhadap diskon harap diperbaiki 24/09/2015

/**
 *
 * @author Susanto
 */
public class Sales_Detail extends javax.swing.JInternalFrame {
private String ComboAddPay=" ",Dates,idpay,id;
private int TOTAL = 0, QTYR, QTY, TQTY, TTQTY,TTTQTY,BARIS_PILIH,COLOM_PILIH,FQTYR,OLDR,oldinv,totalinv;
private Double QTYPrice,Disc,UDisc,TOTALP,TTOTALP,TDISC,FSHIP = 0.0,AllProfit = 0.0;
private List<Double> PriceProduct = new ArrayList<Double>();
private List<String> IDProduct = new ArrayList<String>();
private List<Integer> OldQtyReturn = new ArrayList<Integer>();
    /**
     * Creates new form Sales_Detail
     */
    public Sales_Detail(String id_sales) {
        initComponents();
        this.id = id_sales;
        ComboxPayment();
        updatetableanddata();
    }
    
    public void ComboxPayment(){
        try {
            Conn.rowSet.setCommand("select Type_Payment from payment");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                    ComboAddPay=Conn.rowSet.getString(1);
                    COMBO_PAYMENT.addItem(ComboAddPay); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void updatetableanddata(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date t = new Date();
        DefaultTableModel Table = (DefaultTableModel) Table_Sales.getModel();
        TableColumnModel CTable = Table_Sales.getColumnModel();
        CTable.getColumn(6).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(7).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(8).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        if(PriceProduct.size()>0){
            PriceProduct.clear();
            IDProduct.clear();
            OldQtyReturn.clear();
        }
        try {
            Conn.rowSet.setCommand("select i.name, i.unit, s.qty, s.qtyreturn, s.fqty, i.price_Sale, s.Discount, s.price ,i.price_buy ,i.idinventory from sales_detail s, inventory i where s.idinventory = i.idinventory and s.idsales = '" + id + "'");
            Conn.rowSet.execute();
            Integer i = 1;
            while (Conn.rowSet.next()) {
                PriceProduct.add(Conn.rowSet.getDouble(9));
                IDProduct.add(Conn.rowSet.getString(10));
                OldQtyReturn.add(Conn.rowSet.getInt(4));
                Table.addRow(new Object[]{i,Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getInt(4),Conn.rowSet.getInt(5),Conn.rowSet.getDouble(6),Conn.rowSet.getDouble(7),Conn.rowSet.getDouble(8)});
                i++;
            }
            Conn.rowSet.setCommand("select c.name, p.Type_Payment, s.Total_Payment, s.Date, s.Total_QTY, s.Comment, s.Biaya_Kirim, s.idpayment from sales s, consumer c, payment p where s.idconsumer = c.idconsumer and s.idpayment = p.idpayment and s.idsales = '" + id + "'");
            Conn.rowSet.execute();
            Conn.rowSet.first();
            TXT_SALES.setText(String.valueOf(id));
            TXT_CUSTOMER.setText(Conn.rowSet.getString(1));
            TOTAL = (int) Conn.rowSet.getDouble(3);
            Dates = String.valueOf(Conn.rowSet.getTimestamp(4));
            LBL_QTY.setText(String.valueOf(Conn.rowSet.getInt(5)));
            TEXT_COMMENT.setText(Conn.rowSet.getString(6));
            idpay = Conn.rowSet.getString(7);
            FSHIP = Conn.rowSet.getDouble(7);
            LBL_TOTAL.setText("Rp. " + convertString(TOTAL) + ",00");
            Table.addRow(new Object[]{"0","Biaya Kirim","1","1","1","1",FSHIP,0,FSHIP});
            COMBO_PAYMENT.setSelectedItem(Conn.rowSet.getString(2));
        } catch (SQLException ex) {
            Logger.getLogger(Sales_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            t = sdf.parse(Dates);
            LBL_DATE.setText(t.toString());
        } catch (ParseException ex) {
            Logger.getLogger(Sales_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
    public void updatesales(){
        try{
            Conn.rowSet.setCommand("select * from sales where idsales = '" + id + "'");
            Conn.rowSet.execute();
            Conn.rowSet.next();
            Conn.rowSet.updateString("idpayment", idpay);
            Conn.rowSet.updateDouble("Discount", TDISC);
            Conn.rowSet.updateInt("Total_QTY_Return", FQTYR);
            Conn.rowSet.updateInt("Total_QTY", TTTQTY);
            Conn.rowSet.updateDouble("Total_Payment", TTOTALP+FSHIP);
            Conn.rowSet.updateString("Comment", TEXT_COMMENT.getText());
            Conn.rowSet.updateDouble("Laba", AllProfit);
            Conn.rowSet.updateRow();
//            System.out.println(idpay + "Discount, " + TDISC  + "Total_QTY, "+ TTTQTY + "TTOTALP, "+ TTOTALP);
        }catch (SQLException ex) {
            Logger.getLogger(Sales_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
    public String convertString(int value) {
        String target = "" + value, result = "";
        int j = 0;
        for(int i=target.length()-1; i>=0; --i){
            if(j == 3){
                result += ".";
                j -= 3;
            }
            result += target.charAt(i);
            ++j;
        }
        return new StringBuilder(result).reverse().toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Sales = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TEXT_COMMENT = new javax.swing.JTextArea();
        LBL_TOTAL = new javax.swing.JLabel();
        TXT_SALES = new javax.swing.JLabel();
        TXT_CUSTOMER = new javax.swing.JLabel();
        LBL_DATE = new javax.swing.JLabel();
        LBL_QTY = new javax.swing.JLabel();
        COMBO_PAYMENT = new javax.swing.JComboBox();
        B_UPDATE = new javax.swing.JButton();
        B_BACK = new javax.swing.JButton();
        B_PRINT = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 597));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sales Detail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel1.setText("ID Sales");

        jLabel2.setText("Customer");

        jLabel3.setText("Date Sales");

        jLabel4.setText("Total Payment");

        jLabel5.setText("QTY");

        Table_Sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDSS", "Name", "Unit", "QTY", "Return", "Final QTY", "Price @QTY", "Discount", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_SalesMouseClicked(evt);
            }
        });
        Table_Sales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Table_SalesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Sales);
        if (Table_Sales.getColumnModel().getColumnCount() > 0) {
            Table_Sales.getColumnModel().getColumn(0).setResizable(false);
            Table_Sales.getColumnModel().getColumn(0).setPreferredWidth(7);
            Table_Sales.getColumnModel().getColumn(2).setPreferredWidth(10);
            Table_Sales.getColumnModel().getColumn(3).setPreferredWidth(30);
            Table_Sales.getColumnModel().getColumn(4).setPreferredWidth(20);
            Table_Sales.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        jLabel6.setText(":");

        jLabel7.setText(":");

        jLabel8.setText(":");

        jLabel9.setText(":");

        jLabel10.setText(":");

        jLabel11.setText("Status");

        jLabel12.setText(":");

        jLabel13.setText("Comment");

        jLabel14.setText(":");

        TEXT_COMMENT.setColumns(20);
        TEXT_COMMENT.setRows(5);
        jScrollPane2.setViewportView(TEXT_COMMENT);

        LBL_TOTAL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LBL_TOTAL.setText("Rp.");

        COMBO_PAYMENT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select..." }));
        COMBO_PAYMENT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COMBO_PAYMENTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TXT_SALES)
                                            .addComponent(TXT_CUSTOMER)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LBL_DATE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 378, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addGap(34, 34, 34))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LBL_TOTAL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LBL_QTY))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                    .addComponent(COMBO_PAYMENT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(TXT_SALES))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(TXT_CUSTOMER))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(LBL_DATE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(LBL_TOTAL))
                            .addComponent(jLabel4)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(LBL_QTY))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12))
                            .addComponent(COMBO_PAYMENT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
        );

        B_UPDATE.setText("Update");
        B_UPDATE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_UPDATEActionPerformed(evt);
            }
        });

        B_BACK.setText("Back");
        B_BACK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_BACKActionPerformed(evt);
            }
        });

        B_PRINT.setText("Print");
        B_PRINT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_PRINTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(B_BACK, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(B_PRINT, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B_UPDATE, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(B_UPDATE, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B_BACK, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B_PRINT, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_BACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_BACKActionPerformed
        // TODO add your handling code here:
        Sales_Data Sales_Data = new Sales_Data();
        Tools.change(this, Sales_Data);
    }//GEN-LAST:event_B_BACKActionPerformed

    private void B_UPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_UPDATEActionPerformed
        boolean chk = true;
        QTYPrice = 0.0;
        Disc = 0.0;
        TOTALP = 0.0;
        TTOTALP = 0.0;
        TDISC = 0.0;
        TTTQTY = 0;
        QTY = 0;
        FQTYR = 0;
        if(COMBO_PAYMENT.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Not Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
        }else{
        for(int i=0; i<Table_Sales.getRowCount()-1; ++i){
            if(Table_Sales.getValueAt(i, 4).equals(""))
            {
                Table_Sales.setValueAt("0", i, 4);
            }
            QTYR = 0;
            TQTY = 0;
            UDisc = 0.0;
            QTY = Integer.parseInt(Table_Sales.getValueAt(i, 3).toString());
            QTYPrice = Double.parseDouble(Table_Sales.getValueAt(i, 6).toString());
            Disc = Double.parseDouble(Table_Sales.getValueAt(i, 7).toString());
            try {
                QTYR = Integer.parseInt(Table_Sales.getValueAt(i, 4).toString());
            } catch (Exception e){}
            try
            {
            Conn.rowSet.setCommand("select * from sales_detail where idinventory = '"+ IDProduct.get(i).toString() + "' and idsales = '" + id + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) 
            {
                OLDR = Conn.rowSet.getInt("qtyreturn");
            }
            Conn.rowSet.setCommand("select * from inventory where idinventory = '"+ IDProduct.get(i).toString() + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) 
            {
                oldinv = Conn.rowSet.getInt("qty");
            }
            Conn.rowSet.setCommand("select * from inventory where idinventory = '"+ IDProduct.get(i).toString() + "'");
            Conn.rowSet.execute();
            Conn.rowSet.next();
            totalinv = QTYR-OLDR;
            Conn.rowSet.updateInt("qty", oldinv+totalinv);
//            System.out.println("Total Inventory :" + totalinv);
            Conn.rowSet.updateRow();
            } catch (SQLException ex) {
                Logger.getLogger(Sales_Detail.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(QTY>=QTYR)
            {
            UDisc = Disc/QTY;
            System.out.println("Disc : " + Disc + " UDISC : " + UDisc);
//            System.out.println("Udisk : " + UDisc);
            TQTY = QTY-QTYR;
            TOTALP = (TQTY*QTYPrice)-UDisc;
            TDISC += UDisc*TQTY;
            TTOTALP += TOTALP;
            TTTQTY += TQTY;
            FQTYR += QTYR;
//            System.out.println("Baris ke " + i + "QTYR" +QTYR + ", TQTY" + TQTY + ",QTY " + QTY + ",Disc" + Disc);
            if(OldQtyReturn.get(i)!= QTYR && (QTYR > 0 || QTYR == QTY))
            {
                System.out.println(OldQtyReturn.get(i));
                System.out.println("Masuk");
            try{
                Double TotalProfit = (TOTALP-(PriceProduct.get(i)*TQTY));
                Conn.rowSet.setCommand("select * from sales_detail where idinventory = '"+ IDProduct.get(i).toString() + "' and idsales = '" + id + "'");
                Conn.rowSet.execute();
                Conn.rowSet.next();
                Conn.rowSet.updateInt("qtyreturn", QTYR);
                Conn.rowSet.updateInt("fqty", TQTY);
                Conn.rowSet.updateDouble("profit", TotalProfit);
                if(TOTALP > 0)
                {
                    Conn.rowSet.updateDouble("price", TOTALP);
                }
//                System.out.println(QTYR);
                if(TQTY == 0)
                {
//                    Conn.rowSet.updateDouble("Discount", 0);
                }
                else
                {
                    Conn.rowSet.updateDouble("Discount", UDisc*TQTY);
                }
                Conn.rowSet.updateRow();
                this.AllProfit += TotalProfit;
                } catch (SQLException ex) {
                    Logger.getLogger(Sales_Detail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Not Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
                chk = false;
                break;
            }
        }
        if(chk == true)
        {
            updatesales();
            JOptionPane.showMessageDialog(null, "You Update Is Complete!!");
        }
        }
        updatetableanddata();
    }//GEN-LAST:event_B_UPDATEActionPerformed

    private void COMBO_PAYMENTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_COMBO_PAYMENTActionPerformed
        // TODO add your handling code here:
        if(COMBO_PAYMENT.getSelectedItem().equals("Select..."))
        {
            idpay = "Empty";
        }
        else
        {    
            try {
                Conn.rowSet.setCommand("select idpayment from payment where Type_Payment = '" + COMBO_PAYMENT.getSelectedItem() + "'");
                Conn.rowSet.execute();
                while (Conn.rowSet.next()) {
                        idpay=Conn.rowSet.getString(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_COMBO_PAYMENTActionPerformed

    private void Table_SalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_SalesMouseClicked
        // TODO add your handling code here:
        BARIS_PILIH = Table_Sales.getSelectedRow(); 
        COLOM_PILIH = Table_Sales.getSelectedColumn();
        if(COLOM_PILIH == 4)
        {
            Table_Sales.setValueAt("", BARIS_PILIH, COLOM_PILIH);
        }
    }//GEN-LAST:event_Table_SalesMouseClicked

    private void Table_SalesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_SalesKeyPressed
        // TODO add your handling code here:
        if(!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() !=KeyEvent.VK_BACK_SPACE)
        {
            JOptionPane.showMessageDialog(null, "Not Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
            Table_Sales.setValueAt("0", BARIS_PILIH, 4);
        }
    }//GEN-LAST:event_Table_SalesKeyPressed

    private void B_PRINTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_PRINTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_B_PRINTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_BACK;
    private javax.swing.JButton B_PRINT;
    private javax.swing.JButton B_UPDATE;
    private javax.swing.JComboBox COMBO_PAYMENT;
    private javax.swing.JLabel LBL_DATE;
    private javax.swing.JLabel LBL_QTY;
    private javax.swing.JLabel LBL_TOTAL;
    private javax.swing.JTextArea TEXT_COMMENT;
    private javax.swing.JLabel TXT_CUSTOMER;
    private javax.swing.JLabel TXT_SALES;
    private javax.swing.JTable Table_Sales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
