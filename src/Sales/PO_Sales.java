/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sales;

import Store.Cust;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.Login;
import stokbarangpddj.MenuUtama;
import stokbarangpddj.ToolAndClass.AllClass;
import stokbarangpddj.ToolAndClass.NumberRenderer;
import stokbarangpddj.ToolAndClass.Tools;
// MEMBUAT PO SALES DAN LAIN - LAIN TINGGAL ITU SAJA 2 WEEK TO GO YES!!!
/**
 *
 * @author Susanto
 */
public class PO_Sales extends javax.swing.JInternalFrame {
private String sqlcombobox;
private String ComboAdd=" ";
private int num,shipping = 0,idcus = 0,idpay = 0,iddist = 0;
private int TOTALS = 0;
private int totalqty, BARIS_PILIH, COLOM_PILIH, ID, QTY, TOTAL = 0, TOTALQTYS = 0, TOTALDISC = 0;
DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
DecimalFormatSymbols dfs = new DecimalFormatSymbols();

    /**
     * Creates new form Sales
     */
    public PO_Sales() {
        initComponents();
        ComboxConsumen();
        ComboxPayment();
        updateTableProduct();
//        updatecart();
        ComboxCategory();
        ComboxDist();
        startup();
    }
    
    public void ClearQTY(){
        DefaultTableModel model = (DefaultTableModel) ProductTab.getModel();
        for(int i=0; i<ProductTab.getRowCount(); ++i){
            model.setValueAt("", i, 5);
            model.setValueAt(0, i, 6);
        }
    }
    
    public void ClearAll(){
        TOTALQTY.setText("0");
        TXTDisc.setText("Rp.");
//        TXTPayment.setText("Rp.");
        Combo_Kirim.setSelectedIndex(0);
        ComboCons.setSelectedIndex(0);
        ComboxPayment.setSelectedIndex(0);
        TXTComment.setText("");
    }
    
    public void ComboxCategory(){
    try {
        this.sqlcombobox="select Name from Category";
        Conn.rowSet.setCommand(this.sqlcombobox);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                this.ComboAdd=Conn.rowSet.getString(1);
                ComboxCategory.addItem(this.ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
    }
    }  
    
    public void ComboxConsumen(){
    try {
        this.sqlcombobox="select name from consumer";
        Conn.rowSet.setCommand(this.sqlcombobox);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                this.ComboAdd=Conn.rowSet.getString(1);
                ComboCons.addItem(this.ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public void ComboxPayment(){
    try {
        this.sqlcombobox="select Type_Payment from payment";
        Conn.rowSet.setCommand(this.sqlcombobox);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                this.ComboAdd=Conn.rowSet.getString(1);
                ComboxPayment.addItem(this.ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public void ComboxDist(){
    try {
        this.sqlcombobox="select name from distributor";
        Conn.rowSet.setCommand(this.sqlcombobox);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                this.ComboAdd=Conn.rowSet.getString(1);
                ComboxPayment.addItem(this.ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
    }
    }    

    public void updateTableProduct(){
        DefaultTableModel TableProduct = (DefaultTableModel) ProductTab.getModel();
        TableColumnModel CTableProduct = ProductTab.getColumnModel();
        CTableProduct.getColumn(4).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTableProduct.getColumn(6).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        while(TableProduct.getRowCount() > 0) TableProduct.removeRow(0);
        if(ComboxCategory.getSelectedItem().equals("All"))
            {
                try {
                    Conn.rowSet.setCommand("select * from inventory where qty > 0");
                    Conn.rowSet.execute();
                    while(Conn.rowSet.next())
                    {
                        TableProduct.addRow(new Object[]{Conn.rowSet.getInt("idinventory"),Conn.rowSet.getString("name"),Conn.rowSet.getString("unit"),Conn.rowSet.getInt("qty"),Conn.rowSet.getDouble("price_Sale"),"",0});
                    }  
                } catch (SQLException ex) {
                        Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
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
                    Conn.rowSet.setCommand("select * from inventory where idCategory = '" + num + "'");
                    Conn.rowSet.execute();
                    while(Conn.rowSet.next())
                    {
                        TableProduct.addRow(new Object[]{Conn.rowSet.getInt("idinventory"),Conn.rowSet.getString("name"),Conn.rowSet.getString("unit"),Conn.rowSet.getInt("qty"),Conn.rowSet.getDouble("price_Sale"),"",0});
                    }  
                } catch (SQLException ex) {
                        Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
    }
      
    private void startup(){
        IDS.setText(AllClass.GenerateID("po_sales_detail","idsales"));
        try {
                // TODO add your handling code here:
                Conn.rowSet.setCommand("select * from sales");
                Conn.rowSet.execute();
                Conn.rowSet.moveToInsertRow();
                Conn.rowSet.updateInt("idsales", Integer.parseInt(IDS.getText()));
                Conn.rowSet.updateString("idusername", Login.userID);
                Conn.rowSet.updateTimestamp("Date", new Timestamp(System.currentTimeMillis()));
                Conn.rowSet.insertRow();
                updateTableProduct();
        } catch (SQLException ex) {
                Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel CartTable = (DefaultTableModel) CartTab.getModel();
        while(CartTable.getRowCount() > 0) CartTable.removeRow(0);
    }
    
    private void AddTrans() {
        for(int i=0;i<CartTab.getRowCount();i++)
        {
            try {
            Conn.rowSet.setCommand("select * from po_sales_detail");
            Conn.rowSet.execute();
            Conn.rowSet.moveToInsertRow();
            Conn.rowSet.updateInt("idsales", Integer.parseInt(IDS.getText()));
            Conn.rowSet.updateInt("idinventory", Integer.parseInt(CartTab.getValueAt(i, 0).toString()));
            Conn.rowSet.updateInt("qty", Integer.parseInt(CartTab.getValueAt(i, 3).toString()));
            Conn.rowSet.updateInt("fqty", Integer.parseInt(CartTab.getValueAt(i, 3).toString()));
            Conn.rowSet.updateDouble("price", Double.parseDouble(CartTab.getValueAt(i, 6).toString()));
            Conn.rowSet.updateDouble("Discount", Double.parseDouble(CartTab.getValueAt(i, 5).toString()));
            Conn.rowSet.insertRow();
        } catch (SQLException ex) {
            Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }
    
    private void DoneTrans(){
        try{
            Conn.rowSet.setCommand("select * from sales where idsales = " + IDS.getText() + "");
            Conn.rowSet.execute();
            Conn.rowSet.first();
            Conn.rowSet.updateInt("idconsumer", idcus);
            Conn.rowSet.updateInt("idpayment", idpay);
            Conn.rowSet.updateInt("iddistributor",iddist);
            Conn.rowSet.updateDouble("Biaya_Kirim", shipping);
            Conn.rowSet.updateDouble("Discount", TOTALDISC);
            if(TXTComment.getText().equals(""))
            {
                Conn.rowSet.updateString("Comment", "-");
            }else
            {
                Conn.rowSet.updateString("Comment", TXTComment.getText());
            }
            Conn.rowSet.updateInt("Total_QTY", Integer.parseInt(TOTALQTY.getText()));
            Conn.rowSet.updateDouble("Total_Payment", TOTAL);
            Conn.rowSet.updateRow();
        }catch (SQLException ex) {
            Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(idcus + "" + idpay);
    }
    

    public void ShippingCal(){
        if(Combo_Kirim.getSelectedItem().equals("Rp.0"))
        {
            shipping = 0;
        }
        else if (Combo_Kirim.getSelectedItem().equals("Rp.50.000"))
        {
            shipping = 50000;           
        }
        else if (Combo_Kirim.getSelectedItem().equals("Rp.55.000"))
        {
            shipping = 55000;           
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
        TOTAL = TOTALS+shipping; 
        System.out.print(TOTALS);
        TXTPayment.setText("Rp. " + convertString(TOTAL) + ",00");
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
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        TOTALQTY = new javax.swing.JLabel();
        TXTDisc = new javax.swing.JLabel();
        TXTPayment = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ComboxCategory = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Combo_Kirim = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Combo_Dist = new javax.swing.JComboBox();
        BackBut = new javax.swing.JButton();
        DoneBut = new javax.swing.JButton();
        DEL_BUTTON = new javax.swing.JButton();

        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 597));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sales Form", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("ID Sales :");

        IDS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        IDS.setText("AUTO GENERATE");

        jLabel3.setText("Consumer");

        ComboCons.setEditable(true);
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

        jLabel4.setText("Payment");

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
        ProductTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProductTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(ProductTab);
        if (ProductTab.getColumnModel().getColumnCount() > 0) {
            ProductTab.getColumnModel().getColumn(0).setResizable(false);
            ProductTab.getColumnModel().getColumn(0).setPreferredWidth(10);
            ProductTab.getColumnModel().getColumn(2).setResizable(false);
            ProductTab.getColumnModel().getColumn(2).setPreferredWidth(30);
            ProductTab.getColumnModel().getColumn(3).setResizable(false);
            ProductTab.getColumnModel().getColumn(3).setPreferredWidth(30);
            ProductTab.getColumnModel().getColumn(5).setResizable(false);
            ProductTab.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cart", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        CartTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Unit", "QTY", "Price", "Disc", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        CartTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CartTabMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(CartTab);
        if (CartTab.getColumnModel().getColumnCount() > 0) {
            CartTab.getColumnModel().getColumn(0).setPreferredWidth(20);
            CartTab.getColumnModel().getColumn(2).setPreferredWidth(20);
            CartTab.getColumnModel().getColumn(3).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        );

        jLabel9.setText("Total QTY :");

        jLabel10.setText("Discount   :");

        jLabel11.setText("Total Payment :");

        TOTALQTY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TOTALQTY.setText("0");

        TXTDisc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TXTDisc.setText("0");

        TXTPayment.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TXTPayment.setText("0");

        jLabel15.setText("Product Category :");

        ComboxCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All" }));
        ComboxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboxCategoryActionPerformed(evt);
            }
        });

        jLabel12.setText("Biaya Kirim :");

        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Combo_Kirim.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rp.0", "Rp.50.000", "Rp.55.000", "Rp.100.000", "Rp.150.000", "Rp.200.000", "Rp.250.000", "Rp.300.000", "Rp.350.000", "Rp.400.000", "Rp.450.000", "Rp.500.000", "Rp.550.000", "Rp.600.000" }));
        Combo_Kirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_KirimActionPerformed(evt);
            }
        });

        jLabel2.setText("Distributor");

        jLabel6.setText(":");

        jLabel7.setText(":");

        jLabel8.setText(":");

        Combo_Dist.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select..." }));
        Combo_Dist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_DistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Combo_Kirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TOTALQTY, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TXTDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TXTPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ComboCons, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RegisterBut, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ComboxPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TXTComment, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(22, 22, 22))
                            .addComponent(IDS, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Combo_Dist, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(ComboCons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RegisterBut)
                        .addComponent(jLabel15)
                        .addComponent(ComboxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ComboxPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(TXTComment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDS)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2)
                    .addComponent(Combo_Dist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(Combo_Kirim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TXTPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)))
                        .addComponent(TXTDisc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(TOTALQTY, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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

        DEL_BUTTON.setText("DELETE");
        DEL_BUTTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEL_BUTTONActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 404, Short.MAX_VALUE)
                .addComponent(DEL_BUTTON, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DoneBut, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackBut)
                    .addComponent(DEL_BUTTON, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DoneBut)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboxCategoryActionPerformed
        updateTableProduct();
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboxCategoryActionPerformed

    private void ComboxPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboxPaymentActionPerformed
    if(ComboxPayment.getSelectedItem().equals("Select..."))
    {
        idpay = 0;
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
            Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please Select Done Button First!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
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
                Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
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
                Conn.rowSet.setCommand("select * from sales where po_sales_detail = '" + IDS.getText() + "'");
                Conn.rowSet.execute();
                while(Conn.rowSet.next())
                {
                    Conn.rowSet.deleteRow();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_RegisterButActionPerformed

    private void ProductTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductTabMouseClicked
        // TODO add your handling code here:
    BARIS_PILIH = ProductTab.getSelectedRow(); 
    COLOM_PILIH = ProductTab.getSelectedColumn();
    if(COLOM_PILIH >= 5)
    {
        ProductTab.setValueAt("", BARIS_PILIH, COLOM_PILIH);
    }
    }//GEN-LAST:event_ProductTabMouseClicked

    private void DoneButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneButActionPerformed
        //Custom button text
    if(TOTALQTY.getText().equals("0"))
    {
        JOptionPane.showMessageDialog(null,"Please Input The Transaction First Before Press Done!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
    }
    else
    {
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
                JOptionPane.showMessageDialog(null, "Please Select Your Payment Type Or Customer Name.","ERROR!!",JOptionPane.ERROR_MESSAGE);
            }
            else
            {

            }
        }
        else if(n == JOptionPane.CANCEL_OPTION)
        {
            if(idcus == 0 || idpay == 0)
            {
                JOptionPane.showMessageDialog(null, "Please Select Your Payment Type Or Customer Name.","ERROR!!",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                AddTrans();
                DoneTrans();
                startup();
                ClearAll();
            }
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
            Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    // TODO add your handling code here:
    }//GEN-LAST:event_ComboConsActionPerformed

    private void Combo_KirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_KirimActionPerformed
        // TODO add your handling code here:
        ShippingCal();
    }//GEN-LAST:event_Combo_KirimActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try{
        String NAME,UNIT;
        double Price, Disc;
        TOTAL = 0;
        TOTALQTYS = 0;
        TOTALDISC = 0;
        DefaultTableModel TableCart = (DefaultTableModel) CartTab.getModel();
        TableColumnModel CTableCart = CartTab.getColumnModel();
        CTableCart.getColumn(4).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTableCart.getColumn(5).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTableCart.getColumn(6).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        for(int i=0; i<ProductTab.getRowCount(); ++i){
            if(ProductTab.getValueAt(i, 6).equals(""))
            {
                ProductTab.setValueAt("0", i, 6);
            }
            QTY = 0;
            ID = Integer.parseInt(ProductTab.getValueAt(i, 0).toString());
            NAME = ProductTab.getValueAt(i, 1).toString();
            UNIT = ProductTab.getValueAt(i, 2).toString();
            Price = Double.parseDouble(ProductTab.getValueAt(i, 4).toString());
            Disc = Double.parseDouble(ProductTab.getValueAt(i, 6).toString());
            if(Disc>Price)
            {
                i = ProductTab.getRowCount();
                JOptionPane.showMessageDialog(null,"No Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
                while(TableCart.getRowCount() > 0) TableCart.removeRow(0);
            }
            try {
                QTY = Integer.parseInt(ProductTab.getValueAt(i, 5).toString());
            } catch (Exception e){}
            if(QTY > 0) TableCart.addRow(new Object[]{ID, NAME, UNIT, QTY, Price, Disc*QTY, (Price-Disc)*QTY});
        }
        if(TableCart.getRowCount() == 0) return;
        for(int i=0; i<TableCart.getRowCount(); ++i)
        {
            TOTAL += Double.parseDouble(TableCart.getValueAt(i, 6).toString());
            TOTALQTYS += Integer.parseInt(TableCart.getValueAt(i, 3).toString());
            TOTALDISC += Double.parseDouble(TableCart.getValueAt(i, 5).toString());
        }
        TOTALS = TOTAL;
        TXTPayment.setText("Rp. " + convertString(TOTAL)+ ",00");
        TOTALQTY.setText(String.valueOf(TOTALQTYS));
        TXTDisc.setText("Rp. " + convertString(TOTALDISC)+ ",00");
        ClearQTY();
        ShippingCal();
        } catch ( Exception err ) {
            JOptionPane.showMessageDialog(null,"No Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void DEL_BUTTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEL_BUTTONActionPerformed
        // TODO add your handling code here:
    TOTAL = 0;
    TOTALQTYS = 0;
    TOTALDISC = 0;
    DefaultTableModel TableCart = (DefaultTableModel) CartTab.getModel();
    if(TableCart.getRowCount() == 0) return;
    TableCart.removeRow(BARIS_PILIH);
    for(int i=0; i<TableCart.getRowCount(); ++i)
    {
        TOTAL += Double.parseDouble(TableCart.getValueAt(i, 6).toString());
        TOTALQTYS += Integer.parseInt(TableCart.getValueAt(i, 3).toString());
        TOTALDISC += Double.parseDouble(TableCart.getValueAt(i, 5).toString());
    }
    TOTALS = TOTAL;
    TXTPayment.setText("Rp. " + convertString(TOTAL)+ ",00");
    TOTALQTY.setText(String.valueOf(TOTALQTYS));
    TXTDisc.setText("Rp. " + convertString(TOTALDISC)+ ",00");
    ShippingCal();
    }//GEN-LAST:event_DEL_BUTTONActionPerformed

    private void CartTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CartTabMouseClicked
        // TODO add your handling code here:
        BARIS_PILIH = CartTab.getSelectedRow();
    }//GEN-LAST:event_CartTabMouseClicked

    private void ProductTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProductTabKeyPressed
        // TODO add your handling code here:
    if(!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() !=KeyEvent.VK_BACK_SPACE)
    {
        JOptionPane.showMessageDialog(null, "Not Valid Input!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
        ProductTab.setValueAt("0", BARIS_PILIH, COLOM_PILIH);
    }
    }//GEN-LAST:event_ProductTabKeyPressed

    private void Combo_DistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_DistActionPerformed
        // TODO add your handling code here:
    if(Combo_Dist.getSelectedItem().equals("Select..."))
    {
        iddist = 0;
    }
    else
    {
        try {
            Conn.rowSet.setCommand("select iddistributor from distributor where name = '" + Combo_Dist.getSelectedItem() + "'");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                    iddist=Conn.rowSet.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PO_Sales.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    }//GEN-LAST:event_Combo_DistActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBut;
    private javax.swing.JTable CartTab;
    private javax.swing.JComboBox ComboCons;
    private javax.swing.JComboBox Combo_Dist;
    private javax.swing.JComboBox Combo_Kirim;
    private javax.swing.JComboBox ComboxCategory;
    private javax.swing.JComboBox ComboxPayment;
    private javax.swing.JButton DEL_BUTTON;
    private javax.swing.ButtonGroup DiscAndPriceBut;
    private javax.swing.JButton DoneBut;
    private javax.swing.JLabel IDS;
    private javax.swing.JTable ProductTab;
    private javax.swing.JButton RegisterBut;
    private javax.swing.JLabel TOTALQTY;
    private javax.swing.JTextField TXTComment;
    private javax.swing.JLabel TXTDisc;
    private javax.swing.JLabel TXTPayment;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
