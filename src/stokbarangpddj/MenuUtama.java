/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stokbarangpddj;

import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.ToolAndClass.Tools;
import Inventory.Category;
import Inventory.ProductInv;
import Inventory.Set_Price;
import Payment.PaymentManage;
import Purchase.Purchase;
import Purchase.Purchase_Data;
import Sales.Sales;
import Sales.Sales_Data;
import Store.Cust;
import Store.Distributor;
import UserAdmin.AdminUser;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import static stokbarangpddj.FrameUtama.DekstopFrame;
import static stokbarangpddj.Login.userLogin;

/**
 *
 * @author Susanto
 */
public class MenuUtama extends javax.swing.JInternalFrame {

    /**
     * Creates new form MenuMain
     */
    public MenuUtama() {
        initComponents();
        updateTable();
        updateTableSales();
        makecenter();
        
//        this.pack();
//        Dimension desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setSize(desktopSize.height-5, desktopSize.width+100);

    }
    
    public void makecenter(){
        Dimension desktopSize = DekstopFrame.getSize();
        Dimension jInternalFrameSize = this.getSize();
        this.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
        (desktopSize.height- jInternalFrameSize.height)/2);
    }
    
    public void updateTable(){
                DefaultTableModel Table = (DefaultTableModel) Login_Table.getModel();
                while(Table.getRowCount() > 0) Table.removeRow(0);
		try {
			Conn.rowSet.setCommand("select username, date from log order by idlog desc");
			Conn.rowSet.execute();
                        while (Conn.rowSet.next()) {
                            Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getTimestamp(2)});
                        }
		} catch (SQLException ex) {
			Logger.getLogger(AdminUser.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void updateTableSales(){
                DefaultTableModel Table = (DefaultTableModel) Sales_Table.getModel();
                while(Table.getRowCount() > 0) Table.removeRow(0);
                try {
			Conn.rowSet.setCommand("select s.idsales,c.name,s.Total_QTY,s.Total_Payment,s.Date from sales s, consumer c, payment p where s.idconsumer = c.idconsumer and s.idpayment = p.idpayment order by s.Date desc");
			Conn.rowSet.execute();
                        while (Conn.rowSet.next()) {
                            Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5)});
                        }
		} catch (SQLException ex) {
			Logger.getLogger(AdminUser.class.getName()).log(Level.SEVERE, null, ex);
		}
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
        jScrollPane1 = new javax.swing.JScrollPane();
        Login_Table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Sales_Table = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        UserMenu = new javax.swing.JMenu();
        UserControl_Menu = new javax.swing.JMenuItem();
        Logout = new javax.swing.JMenuItem();
        PembelianMenu = new javax.swing.JMenu();
        Purchase_Menu = new javax.swing.JMenuItem();
        Purchase_Detail_Menu = new javax.swing.JMenuItem();
        PenjualanMenu = new javax.swing.JMenu();
        Sales_Menu = new javax.swing.JMenuItem();
        Sales_Detail_Menu = new javax.swing.JMenuItem();
        PO_Sales_Menu = new javax.swing.JMenuItem();
        PO_Sales_Data_Menu = new javax.swing.JMenuItem();
        Inventory = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        AddProduct_Menu = new javax.swing.JMenuItem();
        AddCategory_Menu = new javax.swing.JMenuItem();
        Toko = new javax.swing.JMenu();
        Consumen_Menu = new javax.swing.JMenuItem();
        Distributor_Menu = new javax.swing.JMenuItem();
        DiscountAndPayment_Menu = new javax.swing.JMenu();
        PrintMenu = new javax.swing.JMenu();

        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 597));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Login"));

        Login_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Date"
            }
        ));
        jScrollPane1.setViewportView(Login_Table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Sales"));

        Sales_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "QTY", "Total Payment", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Sales_Table);
        if (Sales_Table.getColumnModel().getColumnCount() > 0) {
            Sales_Table.getColumnModel().getColumn(0).setResizable(false);
            Sales_Table.getColumnModel().getColumn(0).setPreferredWidth(3);
            Sales_Table.getColumnModel().getColumn(1).setResizable(false);
            Sales_Table.getColumnModel().getColumn(2).setResizable(false);
            Sales_Table.getColumnModel().getColumn(2).setPreferredWidth(5);
            Sales_Table.getColumnModel().getColumn(3).setResizable(false);
            Sales_Table.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );

        UserMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fhoto/Office-Customer-Male-Light-icon.png"))); // NOI18N

        UserControl_Menu.setText("User Control");
        UserControl_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserControl_MenuActionPerformed(evt);
            }
        });
        UserMenu.add(UserControl_Menu);

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });
        UserMenu.add(Logout);

        jMenuBar1.add(UserMenu);

        PembelianMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fhoto/shop-cart-icon.png"))); // NOI18N

        Purchase_Menu.setText("Purchase");
        Purchase_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Purchase_MenuActionPerformed(evt);
            }
        });
        PembelianMenu.add(Purchase_Menu);

        Purchase_Detail_Menu.setText("Purchase Data");
        Purchase_Detail_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Purchase_Detail_MenuActionPerformed(evt);
            }
        });
        PembelianMenu.add(Purchase_Detail_Menu);

        jMenuBar1.add(PembelianMenu);

        PenjualanMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fhoto/Cash-register-icon.png"))); // NOI18N

        Sales_Menu.setText("Sales");
        Sales_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sales_MenuActionPerformed(evt);
            }
        });
        PenjualanMenu.add(Sales_Menu);

        Sales_Detail_Menu.setText("Sales Data");
        Sales_Detail_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sales_Detail_MenuActionPerformed(evt);
            }
        });
        PenjualanMenu.add(Sales_Detail_Menu);

        PO_Sales_Menu.setText("PO Sales");
        PO_Sales_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PO_Sales_MenuActionPerformed(evt);
            }
        });
        PenjualanMenu.add(PO_Sales_Menu);

        PO_Sales_Data_Menu.setText("PO Sales Data");
        PenjualanMenu.add(PO_Sales_Data_Menu);

        jMenuBar1.add(PenjualanMenu);

        Inventory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fhoto/Inventory-maintenance-icon.png"))); // NOI18N

        jMenuItem1.setText("Set Product Price");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Inventory.add(jMenuItem1);

        AddProduct_Menu.setText("Add Product");
        AddProduct_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddProduct_MenuActionPerformed(evt);
            }
        });
        Inventory.add(AddProduct_Menu);

        AddCategory_Menu.setText("Add Category");
        AddCategory_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCategory_MenuActionPerformed(evt);
            }
        });
        Inventory.add(AddCategory_Menu);

        jMenuBar1.add(Inventory);

        Toko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fhoto/store-icon.png"))); // NOI18N

        Consumen_Menu.setText("Consumen");
        Consumen_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Consumen_MenuActionPerformed(evt);
            }
        });
        Toko.add(Consumen_Menu);

        Distributor_Menu.setText("Distributor");
        Distributor_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Distributor_MenuActionPerformed(evt);
            }
        });
        Toko.add(Distributor_Menu);

        jMenuBar1.add(Toko);

        DiscountAndPayment_Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fhoto/Invoice-icon.png"))); // NOI18N
        DiscountAndPayment_Menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DiscountAndPayment_MenuMouseClicked(evt);
            }
        });
        DiscountAndPayment_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiscountAndPayment_MenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(DiscountAndPayment_Menu);

        PrintMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Fhoto/print-icon.png"))); // NOI18N
        jMenuBar1.add(PrintMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UserControl_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserControl_MenuActionPerformed
        AdminUser AdminUser = new AdminUser();
        Tools.change(this, AdminUser);
        // TODO add your handling code here:
    }//GEN-LAST:event_UserControl_MenuActionPerformed

    private void Purchase_Detail_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Purchase_Detail_MenuActionPerformed
        Purchase_Data PurchaseList = new Purchase_Data();
        Tools.change(this, PurchaseList);
        // TODO add your handling code here:
    }//GEN-LAST:event_Purchase_Detail_MenuActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        Login Login = new Login();
        FrameUtama.DekstopFrame.add(Login);
        Login.setVisible(true);
        this.dispose();
        FrameUtama.UserStat.setText("Welcome, User");
        userLogin = "";
        // TODO add your handling code here:
    }//GEN-LAST:event_LogoutActionPerformed

    private void AddProduct_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddProduct_MenuActionPerformed
        try {
            ProductInv ProductInv;
            ProductInv = new ProductInv();
            Tools.change(this, ProductInv);
        } catch (SQLException ex) {
            Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
        }  
        // TODO add your handling code here:
    }//GEN-LAST:event_AddProduct_MenuActionPerformed

    private void AddCategory_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCategory_MenuActionPerformed
        Category Category = new Category();
        Tools.change(this, Category);
        // TODO add your handling code here:
    }//GEN-LAST:event_AddCategory_MenuActionPerformed

    private void Consumen_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Consumen_MenuActionPerformed
        Cust Cust = new Cust();
        Tools.change(this, Cust);
        // TODO add your handling code here:
    }//GEN-LAST:event_Consumen_MenuActionPerformed

    private void Distributor_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Distributor_MenuActionPerformed
        Distributor Distributor = new Distributor();
        Tools.change(this, Distributor);
        // TODO add your handling code here:
    }//GEN-LAST:event_Distributor_MenuActionPerformed

    private void Purchase_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Purchase_MenuActionPerformed
        Purchase Purchase = new Purchase();
        Tools.change(this, Purchase);
        // TODO add your handling code here:
    }//GEN-LAST:event_Purchase_MenuActionPerformed

    private void Sales_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sales_MenuActionPerformed
        Sales Sales = new Sales();
        Tools.change(this, Sales);
        // TODO add your handling code here:
    }//GEN-LAST:event_Sales_MenuActionPerformed

    private void DiscountAndPayment_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiscountAndPayment_MenuActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_DiscountAndPayment_MenuActionPerformed

    private void DiscountAndPayment_MenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiscountAndPayment_MenuMouseClicked
        PaymentManage PaymentManage = new PaymentManage();
        Tools.change(this, PaymentManage);
        // TODO add your handling code here:
    }//GEN-LAST:event_DiscountAndPayment_MenuMouseClicked

    private void Sales_Detail_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sales_Detail_MenuActionPerformed
        Sales_Data Sales_Data = new Sales_Data();
        Tools.change(this, Sales_Data);
        // TODO add your handling code here:
    }//GEN-LAST:event_Sales_Detail_MenuActionPerformed

    private void PO_Sales_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PO_Sales_MenuActionPerformed
        // TODO add your handling code here:
        Sales_Data Sales_Data = new Sales_Data();
        Tools.change(this, Sales_Data);
    }//GEN-LAST:event_PO_Sales_MenuActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        Set_Price Set_Price = new Set_Price();
        Tools.change(this, Set_Price);
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AddCategory_Menu;
    private javax.swing.JMenuItem AddProduct_Menu;
    private javax.swing.JMenuItem Consumen_Menu;
    private javax.swing.JMenu DiscountAndPayment_Menu;
    private javax.swing.JMenuItem Distributor_Menu;
    private javax.swing.JMenu Inventory;
    private javax.swing.JTable Login_Table;
    private javax.swing.JMenuItem Logout;
    private javax.swing.JMenuItem PO_Sales_Data_Menu;
    private javax.swing.JMenuItem PO_Sales_Menu;
    private javax.swing.JMenu PembelianMenu;
    private javax.swing.JMenu PenjualanMenu;
    private javax.swing.JMenu PrintMenu;
    private javax.swing.JMenuItem Purchase_Detail_Menu;
    private javax.swing.JMenuItem Purchase_Menu;
    private javax.swing.JMenuItem Sales_Detail_Menu;
    private javax.swing.JMenuItem Sales_Menu;
    private javax.swing.JTable Sales_Table;
    private javax.swing.JMenu Toko;
    private javax.swing.JMenuItem UserControl_Menu;
    private javax.swing.JMenu UserMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}