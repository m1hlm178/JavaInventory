/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Purchase;
import Sales.Sales;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import stokbarangpddj.Login;
import stokbarangpddj.MenuUtama;
import stokbarangpddj.ToolAndClass.AllClass;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.ToolAndClass.NumberRenderer;
import stokbarangpddj.ToolAndClass.Tools;
// Still WRONG DUPLICATE CHECK!!!
/**
 *
 * @author Susanto
 */
public class Purchase extends javax.swing.JInternalFrame {
private String ComboAdd=" ";
private String idpay = "0",iddist = "0";
private int idprod = 0, BARIS_PILIH, TOTAL = 0, TOTALQTYS = 0;
private int oldinv,totalinv;
private NumberFormat amountFormat;
private List<String> IDProd = new ArrayList<String>();
private List<String> IDDist = new ArrayList<String>();
private List<String> NameCat = new ArrayList<String>();
    /**
     * Creates new form Purchase
     */
    public Purchase() {
        amountFormat = NumberFormat.getNumberInstance();
        initComponents();
        ComboxProd();
        ComboxDist();
        ComboxPayment();
        startup();
    }
    
    public void ComboxProd(){
    try {
        if(ComboProd.getItemCount()>1){
                ComboProd.removeAllItems();
                IDProd.clear();
                NameCat.clear();
                ComboProd.addItem("Select..");
                IDProd.add("0");
        }
        IDProd.add("0");
        NameCat.add("0");
        Conn.rowSet.setCommand("select a.name,a.qty,b.name,a.idinventory from inventory a, category b where a.idCategory = b.idCategory");
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                IDProd.add(Conn.rowSet.getString(4));
                NameCat.add(Conn.rowSet.getString(3));
                this.ComboAdd=Conn.rowSet.getString(1)+"-"+Conn.rowSet.getString(3)+" ("+Conn.rowSet.getInt(2)+")";
                ComboProd.addItem(this.ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public void ComboxDist(){
    try {
        if(this.IDDist.size()>0){
            this.IDDist.clear();
        }
        this.IDDist.add("0");
        Conn.rowSet.setCommand("select name,iddistributor from distributor");
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                this.IDDist.add(Conn.rowSet.getString(2));
                this.ComboAdd=Conn.rowSet.getString(1);
                ComboDist.addItem(this.ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public void ComboxPayment(){
    try {
        Conn.rowSet.setCommand("select Type_Payment from payment");
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                this.ComboAdd=Conn.rowSet.getString(1);
                StatusPay.addItem(this.ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 
    
    private void startup(){
        IDP.setText(AllClass.GenerateID("purchase","idpurchase"));
        try {
                // TODO add your handling code here:
                Conn.rowSet.setCommand("select * from purchase");
                Conn.rowSet.execute();
                Conn.rowSet.moveToInsertRow();
                Conn.rowSet.updateString("idpurchase", IDP.getText());
                Conn.rowSet.updateString("idusername", Login.userID);
                Conn.rowSet.updateTimestamp("Date", new Timestamp(System.currentTimeMillis()));
                Conn.rowSet.insertRow();
        } catch (SQLException ex) {
                Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void AddTrans() {
        for(int i=0;i<TablePurch.getRowCount();i++)
        {
            try {     
                Conn.rowSet.setCommand("select * from purchase_detail");
                Conn.rowSet.execute();
                Conn.rowSet.moveToInsertRow();
                Conn.rowSet.updateString("idpurchase", IDP.getText());
                Conn.rowSet.updateString("idinventory", TablePurch.getValueAt(i, 0).toString());
                Conn.rowSet.updateInt("qty", Integer.parseInt(TablePurch.getValueAt(i, 4).toString()));
                Conn.rowSet.updateInt("fqty", Integer.parseInt(TablePurch.getValueAt(i, 4).toString()));
                Conn.rowSet.updateDouble("price", Double.parseDouble(TablePurch.getValueAt(i, 5).toString()));
                Conn.rowSet.updateDouble("total_price", Double.parseDouble(TablePurch.getValueAt(i, 6).toString()));
                Conn.rowSet.insertRow();
                Conn.rowSet.setCommand("select * from inventory where idinventory = '" + TablePurch.getValueAt(i, 0).toString() + "'");
                Conn.rowSet.execute();
                while (Conn.rowSet.next()) 
                {
                    oldinv = Conn.rowSet.getInt("qty");
                }
                totalinv = oldinv + Integer.parseInt(TablePurch.getValueAt(i, 4).toString());
                System.out.println(totalinv);
                System.out.println(oldinv);
                Conn.rowSet.setCommand("select * from inventory where idinventory = '" + TablePurch.getValueAt(i, 0).toString() + "'");
                Conn.rowSet.execute();
                Conn.rowSet.next();
                Conn.rowSet.updateInt("qty", totalinv);
                Conn.rowSet.updateDouble("price_buy", Double.parseDouble(TablePurch.getValueAt(i, 5).toString()));
                Conn.rowSet.updateRow();
            } catch (SQLException ex) {
                Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void DoneDist() {
        try {
            Conn.rowSet.setCommand("select * from distributor where iddistributor = '" + IDDist.get(ComboDist.getSelectedIndex()) + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                iddist = Conn.rowSet.getString("iddistributor");
            }
            Conn.rowSet.setCommand("select * from purchase where idpurchase = '" + IDP.getText() + "'");
            Conn.rowSet.execute();
            Conn.rowSet.first();
            Conn.rowSet.updateString("iddistributor", iddist);
            Conn.rowSet.updateString("idpayment", idpay);
            Conn.rowSet.updateString("Status", StatusPay.getSelectedItem().toString());
            Conn.rowSet.updateDouble("Total_Payment", TOTAL);
            Conn.rowSet.updateInt("Total_QTY", Integer.parseInt(TOTALQTY.getText()));
            Conn.rowSet.updateString("Comment", TXT_COMMENT.getText());
            Conn.rowSet.updateRow();
        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void clear(){
        TXTQTY.setText("");
        TXTPrice.setText("");
        ComboProd.setSelectedIndex(0);
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
        DoneBut = new javax.swing.JButton();
        BackBut = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        LBL_ID = new javax.swing.JLabel();
        LBL_Product = new javax.swing.JLabel();
        LBL_Distributor = new javax.swing.JLabel();
        LBL_QTY = new javax.swing.JLabel();
        LBL_Price = new javax.swing.JLabel();
        LBL_1 = new javax.swing.JLabel();
        LBL_2 = new javax.swing.JLabel();
        LBL_3 = new javax.swing.JLabel();
        LBL_4 = new javax.swing.JLabel();
        LBL_5 = new javax.swing.JLabel();
        IDP = new javax.swing.JLabel();
        LBL_Payment = new javax.swing.JLabel();
        LBL_TOTAL_QTY = new javax.swing.JLabel();
        LBL_Total = new javax.swing.JLabel();
        TOTALPRICE = new javax.swing.JLabel();
        TOTALQTY = new javax.swing.JLabel();
        ComboProd = new javax.swing.JComboBox();
        ComboDist = new javax.swing.JComboBox();
        TXTQTY = new javax.swing.JTextField();
        StatusPay = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablePurch = new javax.swing.JTable();
        AddBut = new javax.swing.JButton();
        DeleteBut = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TXT_COMMENT = new javax.swing.JTextArea();
        TXTPrice = new javax.swing.JTextField();

        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 597));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Purchase Form", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        DoneBut.setText("Done");
        DoneBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneButActionPerformed(evt);
            }
        });

        BackBut.setText("Back");
        BackBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        LBL_ID.setText("ID Purchase   ");

        LBL_Product.setText("Product");

        LBL_Distributor.setText("Distributor");

        LBL_QTY.setText("QTY");

        LBL_Price.setText("Price");

        LBL_1.setText(":");

        LBL_2.setText(":");

        LBL_3.setText(":");

        LBL_4.setText(":");

        LBL_5.setText(":");

        IDP.setText("AUTO GENERATE NUMBER");

        LBL_Payment.setText("Payment");

        LBL_TOTAL_QTY.setText("QTY");

        LBL_Total.setText("Total");

        TOTALPRICE.setText("Rp.0");

        TOTALQTY.setText("0");

        ComboProd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select.." }));

        ComboDist.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select.." }));

        TXTQTY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTQTYActionPerformed(evt);
            }
        });
        TXTQTY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TXTQTYKeyReleased(evt);
            }
        });

        StatusPay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select.." }));
        StatusPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusPayActionPerformed(evt);
            }
        });

        TablePurch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Unit", "QTY", "Price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, false
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
        jScrollPane1.setViewportView(TablePurch);
        if (TablePurch.getColumnModel().getColumnCount() > 0) {
            TablePurch.getColumnModel().getColumn(0).setPreferredWidth(10);
        }

        AddBut.setText("Add");
        AddBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButActionPerformed(evt);
            }
        });

        DeleteBut.setText("Delete");
        DeleteBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButActionPerformed(evt);
            }
        });

        jLabel14.setText(":");

        jLabel15.setText(":");

        jLabel16.setText("Comment :");

        jLabel17.setText(":");

        TXT_COMMENT.setColumns(20);
        TXT_COMMENT.setRows(5);
        TXT_COMMENT.setText("-");
        jScrollPane2.setViewportView(TXT_COMMENT);

        TXTPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TXTPriceKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(DeleteBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LBL_ID)
                                    .addComponent(LBL_Distributor)
                                    .addComponent(LBL_QTY)
                                    .addComponent(LBL_Price)
                                    .addComponent(LBL_Product, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(LBL_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(LBL_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(LBL_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(LBL_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(LBL_5))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LBL_Total)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(LBL_Payment)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ComboProd, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                    .addComponent(TOTALPRICE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(LBL_TOTAL_QTY)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel15)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(TOTALQTY, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(ComboDist, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addComponent(TXTQTY)
                                                    .addGap(234, 234, 234)))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(IDP, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(StatusPay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(240, 240, 240)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(TXTPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IDP)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ComboProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LBL_2)
                                    .addComponent(LBL_Product))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ComboDist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LBL_3)
                                    .addComponent(LBL_Distributor))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TXTQTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LBL_4)
                                    .addComponent(LBL_QTY))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LBL_Payment)
                                    .addComponent(StatusPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(LBL_5)
                                        .addComponent(TXTPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(LBL_Price)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(LBL_Total))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(TOTALPRICE)
                                .addComponent(LBL_TOTAL_QTY)
                                .addComponent(TOTALQTY)
                                .addComponent(jLabel15))))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LBL_ID)
                        .addComponent(LBL_1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeleteBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DoneBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DoneBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void DoneButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneButActionPerformed
        if(TOTALQTY.getText().equals("0"))
        {
            JOptionPane.showMessageDialog(null, "Sorry You Not Buy AnyThing Please Buy Product First!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            if(StatusPay.getSelectedIndex() == 0 && ComboDist.getSelectedItem().equals("Select.."))
            {
                JOptionPane.showMessageDialog(null, "Please Complete Data!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                AddTrans();
                DoneDist();
                clear();
                startup();
                DefaultTableModel Table = (DefaultTableModel) TablePurch.getModel();
                while(Table.getRowCount() > 0) Table.removeRow(0);
                JOptionPane.showMessageDialog(null, "Your Data IS Complete Added!!","Notice!!",JOptionPane.INFORMATION_MESSAGE);
                TOTALPRICE.setText("Rp.0");
                TOTALQTY.setText("0");
                ComboDist.enable(true);
                ComboxProd();
            }
        }
        //         TODO add your handling code here:
    }//GEN-LAST:event_DoneButActionPerformed

    private void BackButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButActionPerformed
        if(TOTALQTY.getText().equals("0"))
        {
            MenuUtama MenuUtama = new MenuUtama();
            Tools.change(this, MenuUtama);
            try{
                Conn.rowSet.setCommand("select * from purchase where idpurchase = '" + IDP.getText() + "'");
                Conn.rowSet.execute();
                while(Conn.rowSet.next())
                {
                    Conn.rowSet.deleteRow();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please Select Done Button First!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButActionPerformed

    private void TablePurchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablePurchMouseClicked
        this.BARIS_PILIH = TablePurch.getSelectedRow();
        System.out.println(this.BARIS_PILIH);
        TXTQTY.setText(TablePurch.getValueAt(this.BARIS_PILIH, 4).toString());
        TXTPrice.setText(TablePurch.getValueAt(this.BARIS_PILIH, 5).toString());
        ComboProd.setSelectedItem(TablePurch.getValueAt(this.BARIS_PILIH, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_TablePurchMouseClicked

    private void AddButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButActionPerformed
        Integer IRows = null;
        String Checks = null;
        if(TXTQTY.getText().isEmpty() == true || TXTPrice.getText().isEmpty() == true || ComboProd.getSelectedItem().equals("Select..") || ComboDist.getSelectedItem().equals("Select..") || StatusPay.getSelectedItem().equals("Select.."))
        {
            JOptionPane.showMessageDialog(null, "Please Complete Your Data First!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
//            String[] partcomb = ComboProd.getSelectedItem().toString().split("-");
            TOTAL = 0;
            TOTALQTYS = 0;
            DefaultTableModel Table = (DefaultTableModel) TablePurch.getModel();
            TableColumnModel CTable = TablePurch.getColumnModel();
            CTable.getColumn(5).setCellRenderer(NumberRenderer.getCurrencyRenderer());
            CTable.getColumn(6).setCellRenderer(NumberRenderer.getCurrencyRenderer());
            //Duplicate Check
            if(Table.getRowCount()==0)
            {
                    try {
                    Conn.rowSet.setCommand("select * from inventory where idinventory = '" + IDProd.get(ComboProd.getSelectedIndex()) + "'");
                    Conn.rowSet.execute();
                    while (Conn.rowSet.next())
                    {
                        Table.addRow(new Object[]{Conn.rowSet.getString("idinventory"),Conn.rowSet.getString("name"),NameCat.get(ComboProd.getSelectedIndex()),Conn.rowSet.getString("unit"),Integer.parseInt(TXTQTY.getText()),Double.parseDouble(TXTPrice.getText()),Double.parseDouble(TXTPrice.getText())*Integer.parseInt(TXTQTY.getText())});
                    }
                    } catch (SQLException ex) {
                        Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }else{
//                System.out.println(Table.getRowCount());
                for(int i=0; i<Table.getRowCount(); ++i)
                {
                    Checks = null;
                    if(IDProd.get(ComboProd.getSelectedIndex()).equalsIgnoreCase(TablePurch.getValueAt(i, 0).toString())){
                        IRows = i;
                        Checks = "NotNull";
                        break;
                    }
                }
                if(Checks!=null)
                {
                    Table.removeRow(IRows);
                    try {
                        Conn.rowSet.setCommand("select * from inventory where idinventory = '" + IDProd.get(ComboProd.getSelectedIndex()) + "'");
                        Conn.rowSet.execute();
                        while (Conn.rowSet.next())
                        {
                            Table.addRow(new Object[]{Conn.rowSet.getString("idinventory"),Conn.rowSet.getString("name"),NameCat.get(ComboProd.getSelectedIndex()),Conn.rowSet.getString("unit"),Integer.parseInt(TXTQTY.getText()),Double.parseDouble(TXTPrice.getText()),Double.parseDouble(TXTPrice.getText())*Integer.parseInt(TXTQTY.getText())});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    try {
                    Conn.rowSet.setCommand("select * from inventory where idinventory = '" + IDProd.get(ComboProd.getSelectedIndex()) + "'");
                    Conn.rowSet.execute();
                    while (Conn.rowSet.next())
                    {
                        Table.addRow(new Object[]{Conn.rowSet.getString("idinventory"),Conn.rowSet.getString("name"),NameCat.get(ComboProd.getSelectedIndex()),Conn.rowSet.getString("unit"),Integer.parseInt(TXTQTY.getText()),Double.parseDouble(TXTPrice.getText()),Double.parseDouble(TXTPrice.getText())*Integer.parseInt(TXTQTY.getText())});
                    }
                    } catch (SQLException ex) {
                        Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            for(int i=0; i<Table.getRowCount(); ++i)
            {
                TOTAL += Double.parseDouble(Table.getValueAt(i, 6).toString());
                TOTALQTYS += Integer.parseInt(Table.getValueAt(i, 4).toString());
            }
            TOTALPRICE.setText("Rp. " + convertString(TOTAL)+ ",00");
            TOTALQTY.setText(String.valueOf(TOTALQTYS));
            clear();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AddButActionPerformed

    private void DeleteButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButActionPerformed
        TOTAL = 0;
        TOTALQTYS = 0;
        DefaultTableModel Table = (DefaultTableModel) TablePurch.getModel();
        if(Table.getRowCount() == 0) return;
        System.out.println(this.BARIS_PILIH);
        Table.removeRow(this.BARIS_PILIH);
        for(int i=0; i<Table.getRowCount(); ++i)
        {
            TOTAL += Double.parseDouble(Table.getValueAt(i, 6).toString());
            TOTALQTYS += Integer.parseInt(Table.getValueAt(i, 4).toString());
        }
        TOTALPRICE.setText("Rp. " + convertString(TOTAL)+ ",00");
        TOTALQTY.setText(String.valueOf(TOTALQTYS));
        clear();
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteButActionPerformed

    private void TXTQTYKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TXTQTYKeyReleased
        // TODO add your handling code here:
        if(TXTQTY.getText().isEmpty() == false)
        {
            if(Tools.isNumeric(TXTQTY.getText())==false)
            {
                JOptionPane.showMessageDialog(null, "Not Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
                TXTQTY.setText("");
            }
        }
    }//GEN-LAST:event_TXTQTYKeyReleased

    private void StatusPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusPayActionPerformed
        // TODO add your handling code here:
        if(StatusPay.getSelectedItem().equals("Select..."))
        {
            idpay = "0";
        }
        else
        {    
            try {
                Conn.rowSet.setCommand("select idpayment from payment where Type_Payment = '" + StatusPay.getSelectedItem() + "'");
                Conn.rowSet.execute();
                while (Conn.rowSet.next()) {
                        idpay=Conn.rowSet.getString(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_StatusPayActionPerformed

    private void TXTQTYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTQTYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXTQTYActionPerformed

    private void TXTPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TXTPriceKeyReleased
        // TODO add your handling code here:
        if(TXTPrice.getText().isEmpty() == false)
        {
            if(Tools.isNumeric(TXTPrice.getText())==false)
            {
                JOptionPane.showMessageDialog(null, "Not Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
                TXTPrice.setText("");
            }
        }   
    }//GEN-LAST:event_TXTPriceKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBut;
    private javax.swing.JButton BackBut;
    private javax.swing.JComboBox ComboDist;
    private javax.swing.JComboBox ComboProd;
    private javax.swing.JButton DeleteBut;
    private javax.swing.JButton DoneBut;
    private javax.swing.JLabel IDP;
    private javax.swing.JLabel LBL_1;
    private javax.swing.JLabel LBL_2;
    private javax.swing.JLabel LBL_3;
    private javax.swing.JLabel LBL_4;
    private javax.swing.JLabel LBL_5;
    private javax.swing.JLabel LBL_Distributor;
    private javax.swing.JLabel LBL_ID;
    private javax.swing.JLabel LBL_Payment;
    private javax.swing.JLabel LBL_Price;
    private javax.swing.JLabel LBL_Product;
    private javax.swing.JLabel LBL_QTY;
    private javax.swing.JLabel LBL_TOTAL_QTY;
    private javax.swing.JLabel LBL_Total;
    private javax.swing.JComboBox StatusPay;
    private javax.swing.JLabel TOTALPRICE;
    private javax.swing.JLabel TOTALQTY;
    private javax.swing.JTextField TXTPrice;
    private javax.swing.JTextField TXTQTY;
    private javax.swing.JTextArea TXT_COMMENT;
    private javax.swing.JTable TablePurch;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
