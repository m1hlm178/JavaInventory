/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Purchase;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.ToolAndClass.NumberRenderer;
import stokbarangpddj.ToolAndClass.Tools;
//MASI BELOM SELSAI
/**
 *
 * @author Susanto
 */
public class Purchase_Detail extends javax.swing.JInternalFrame {
private String id,idpay ="",Dates,ComboAddPay=" ";
private int TOTAL = 0, QTYR, QTY, TQTY, TTQTY,TTTQTY,BARIS_PILIH,COLOM_PILIH,FQTYR,OLDR,oldinv,totalinv;
private Double QTYPrice,TOTALP,TTOTALP;
private List<Integer> IdUniq = new ArrayList<Integer>();
    /**
     * Creates new form Purchase_Detail
     */
    public Purchase_Detail(String id_pruch) {
        initComponents();
        this.id = id_pruch;
        ComboxPayment();
        updatetableanddata();
    }
    
    public void ComboxPayment(){
    try {
        Conn.rowSet.setCommand("select Type_Payment from payment");
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                ComboAddPay=Conn.rowSet.getString(1);
                ComboPayment.addItem(ComboAddPay); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Purchase_Detail.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 
    
    public void updatetableanddata(){
        DefaultTableModel Table = (DefaultTableModel) TablePurch.getModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date t = new Date();
        TableColumnModel CTable = TablePurch.getColumnModel();
        CTable.getColumn(6).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(7).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        if(IdUniq.size()>0){
            IdUniq.clear();
        }
        try {
            Conn.rowSet.setCommand("select i.name, i.unit, p.qty, p.qtyreturn, p.fqty, p.price, p.total_price,p.uniqid from purchase_detail p, inventory i where p.idinventory = i.idinventory and p.idpurchase = '" + id + "'");
            Conn.rowSet.execute();
            Integer i = 1;
            while (Conn.rowSet.next()) {
                IdUniq.add(Conn.rowSet.getInt(8));
                Table.addRow(new Object[]{i,Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getInt(4),Conn.rowSet.getInt(5),Conn.rowSet.getDouble(6),Conn.rowSet.getDouble(7)});
                i++;
            }
            Conn.rowSet.setCommand("select d.name, p.Comment, p.Total_Payment, p.Date, p.Total_QTY, p.idpayment, pp.Type_Payment from purchase p, distributor d, payment pp where p.iddistributor = d.iddistributor and pp.idpayment = p.idpayment and p.idpurchase = '" + id + "'");
            Conn.rowSet.execute();
            Conn.rowSet.first();
            TXTPurcId.setText(String.valueOf(id));
            TXTStore.setText(Conn.rowSet.getString(1));
//            status = Conn.rowSet.getString(2);
            TEXT_COMMENT.setText(Conn.rowSet.getString(2));
            TOTAL = (int) Conn.rowSet.getDouble(3);
            Dates = String.valueOf(Conn.rowSet.getTimestamp(4));
            LBL_QTY.setText(String.valueOf(Conn.rowSet.getInt(5)));
            idpay = Conn.rowSet.getString(6);
            ComboPayment.setSelectedItem(Conn.rowSet.getString(7));
            LBL_TOTAL.setText("Rp. " + convertString(TOTAL) + ",00");
        } catch (SQLException ex) {
            Logger.getLogger(Purchase_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            t = sdf.parse(Dates);
            LBL_DATE.setText(t.toString());
        } catch (ParseException ex) {
            Logger.getLogger(Purchase_Detail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void updatepurchases(){
        try{
            Conn.rowSet.setCommand("select * from purchase where idpurchase = '" + id + "'");
            Conn.rowSet.execute();
            Conn.rowSet.next();
            Conn.rowSet.updateString("idpayment", idpay);
            Conn.rowSet.updateInt("Total_QTY_Return", FQTYR);
            Conn.rowSet.updateInt("Total_QTY", TTTQTY);
            Conn.rowSet.updateDouble("Total_Payment", TTOTALP);
            Conn.rowSet.updateString("Status", ComboPayment.getSelectedItem().toString());
            Conn.rowSet.updateString("Comment", TEXT_COMMENT.getText());
            Conn.rowSet.updateRow();
        }catch (SQLException ex) {
            Logger.getLogger(Purchase_Detail.class.getName()).log(Level.SEVERE, null, ex);
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

        GroupPay = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TXTPurcId = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablePurch = new javax.swing.JTable();
        TXTStore = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TEXT_COMMENT = new javax.swing.JTextArea();
        ComboPayment = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LBL_QTY = new javax.swing.JLabel();
        LBL_TOTAL = new javax.swing.JLabel();
        LBL_DATE = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        UpdateButton = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 597));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Puchase Detail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel1.setText("ID Purchase");

        jLabel2.setText("Dist Name");

        jLabel3.setText("Payment Status");

        TXTPurcId.setText("Purchase ID");

        TablePurch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDPP", "Name", "Unit", "QTY", "Return", "FQTY", "Price @QTY", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablePurch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablePurchMouseClicked(evt);
            }
        });
        TablePurch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablePurchKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TablePurch);
        if (TablePurch.getColumnModel().getColumnCount() > 0) {
            TablePurch.getColumnModel().getColumn(0).setPreferredWidth(7);
            TablePurch.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        TXTStore.setText("Store Name");

        jLabel6.setText("Total Payment");

        jLabel4.setText("Comment");

        jLabel5.setText(":");

        jLabel8.setText(":");

        jLabel9.setText(":");

        jLabel10.setText(":");

        jLabel12.setText(":");

        TEXT_COMMENT.setColumns(20);
        TEXT_COMMENT.setRows(5);
        jScrollPane2.setViewportView(TEXT_COMMENT);

        ComboPayment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select..." }));
        ComboPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboPaymentActionPerformed(evt);
            }
        });

        jLabel7.setText("QTY");

        jLabel11.setText(":");

        LBL_QTY.setText("100");

        LBL_TOTAL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LBL_TOTAL.setText("Rp.");

        LBL_DATE.setText("100");

        jLabel13.setText(":");

        jLabel14.setText("Date Sales");

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
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LBL_TOTAL))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LBL_DATE)
                                    .addComponent(TXTStore)
                                    .addComponent(TXTPurcId, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LBL_QTY)
                                    .addComponent(ComboPayment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TXTPurcId)
                    .addComponent(jLabel5)
                    .addComponent(LBL_QTY)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TXTStore)
                    .addComponent(jLabel8)
                    .addComponent(ComboPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(LBL_DATE)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14)))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(LBL_TOTAL))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        UpdateButton.setText("Update");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });

        jButton3.setText("Print");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 433, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Purchase_Data PurchaseList = new Purchase_Data();
        Tools.change(this, PurchaseList);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed
        // TODO add your handling code here:
        boolean chk = true;
        QTYPrice = 0.0;
        TOTALP = 0.0;
        TTOTALP = 0.0;
        TTTQTY = 0;
        QTY = 0;
        FQTYR = 0;
        for(int i=0; i<TablePurch.getRowCount(); ++i){
            if(TablePurch.getValueAt(i, 4).equals(""))
            {
                TablePurch.setValueAt("0", i, 4);
            }
            QTYR = 0;
            TQTY = 0;
            QTY = Integer.parseInt(TablePurch.getValueAt(i, 3).toString());
            QTYPrice = Double.parseDouble(TablePurch.getValueAt(i, 6).toString());
            try {
                QTYR = Integer.parseInt(TablePurch.getValueAt(i, 4).toString());
            } catch (Exception e){}
            try
            {
            Conn.rowSet.setCommand("select * from purchase_detail where uniqid = '"+ IdUniq.get(i) + "' and idpurchase = '" + id + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) 
            {
                OLDR = Conn.rowSet.getInt("qtyreturn");
            }
            Conn.rowSet.setCommand("select * from inventory where name = '" + TablePurch.getValueAt(i, 1).toString() + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) 
            {
                oldinv = Conn.rowSet.getInt("qty");
            }
            if((QTYR > 0 || QTYR == QTY) && QTYR <= QTY)
            {
                Conn.rowSet.setCommand("select * from inventory where name = '" + TablePurch.getValueAt(i, 1).toString() + "'");
                System.out.println("Masuk Bagian Update : " + TablePurch.getValueAt(i, 1).toString());
                Conn.rowSet.execute();
                Conn.rowSet.next();
                totalinv = QTYR-OLDR;
                totalinv = oldinv+(totalinv * -1);
                Conn.rowSet.updateInt("qty", totalinv);
                System.out.println("Inventory Yang Ada : " + totalinv);
                Conn.rowSet.updateRow();
            }
            } catch (SQLException ex) {
                Logger.getLogger(Purchase_Detail.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(QTY>=QTYR)
            {
            TQTY = QTY-QTYR;
            TOTALP = (TQTY*QTYPrice);
            TTOTALP += TOTALP;
            TTTQTY += TQTY;
            FQTYR += QTYR;
            if(QTYR > 0 || QTYR == QTY)
            {
            try{
                Conn.rowSet.setCommand("select * from purchase_detail where uniqid = '"+ IdUniq.get(i) + "' and idpurchase = '" + id + "'");
                Conn.rowSet.execute();
                Conn.rowSet.next();
                Conn.rowSet.updateInt("qtyreturn", QTYR);
                Conn.rowSet.updateInt("fqty", TQTY);
                if(TOTALP > 0)
                {
                    Conn.rowSet.updateDouble("total_price", TOTALP);
                }
                Conn.rowSet.updateRow();
                } catch (SQLException ex) {
                    Logger.getLogger(Purchase_Detail.class.getName()).log(Level.SEVERE, null, ex);
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
            updatepurchases();
            JOptionPane.showMessageDialog(null, "You Update Is Complete!!");
        }
        updatetableanddata();
    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void ComboPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboPaymentActionPerformed
        // TODO add your handling code here:
    if(ComboPayment.getSelectedItem().equals("Select..."))
        {
            idpay = "0";
        }
        else
        {    
            try {
                Conn.rowSet.setCommand("select idpayment from payment where Type_Payment = '" + ComboPayment.getSelectedItem() + "'");
                Conn.rowSet.execute();
                while (Conn.rowSet.next()) {
                        this.idpay=Conn.rowSet.getString(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Purchase_Detail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ComboPaymentActionPerformed

    private void TablePurchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablePurchKeyPressed
        // TODO add your handling code here:
        if(!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() !=KeyEvent.VK_BACK_SPACE)
        {
            JOptionPane.showMessageDialog(null, "Not Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
            TablePurch.setValueAt("0", BARIS_PILIH, 4);
        }
    }//GEN-LAST:event_TablePurchKeyPressed

    private void TablePurchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablePurchMouseClicked
        // TODO add your handling code here:
        BARIS_PILIH = TablePurch.getSelectedRow(); 
        COLOM_PILIH = TablePurch.getSelectedColumn();
        if(COLOM_PILIH == 4)
        {
            TablePurch.setValueAt("", BARIS_PILIH, COLOM_PILIH);
        }
    }//GEN-LAST:event_TablePurchMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboPayment;
    private javax.swing.ButtonGroup GroupPay;
    private javax.swing.JLabel LBL_DATE;
    private javax.swing.JLabel LBL_QTY;
    private javax.swing.JLabel LBL_TOTAL;
    private javax.swing.JTextArea TEXT_COMMENT;
    private javax.swing.JLabel TXTPurcId;
    private javax.swing.JLabel TXTStore;
    private javax.swing.JTable TablePurch;
    private javax.swing.JButton UpdateButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
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
