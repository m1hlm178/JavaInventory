/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import stokbarangpddj.MenuUtama;
import stokbarangpddj.ToolAndClass.AllClass;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.ToolAndClass.NumberRenderer;
import stokbarangpddj.ToolAndClass.Tools;

/**
 *
 * @author Susanto
 */
public class Set_Price extends javax.swing.JInternalFrame {
private double OLD,AFTER;
private String ComboAdd=" ";
protected List<String> IDSearch = new ArrayList<String>();
    /**
     * Creates new form Set_Price
     */
    public Set_Price() {
        initComponents();
    try {
        IDSearch.add("0");
        Conn.rowSet.setCommand("select a.idinventory,a.name,b.name from inventory a,category b where a.idCategory = b.idCategory");
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                IDSearch.add(Conn.rowSet.getString(1));
                ComboAdd=Conn.rowSet.getString(2)+"-"+Conn.rowSet.getString(3);
                ComboSearch.addItem(ComboAdd); 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Set_Price.class.getName()).log(Level.SEVERE, null, ex);
    }
        updateTable();
    }
    
    public void updateTable(){
		try {
                        DefaultTableModel Table = (DefaultTableModel) ProductTab.getModel();
                        TableColumnModel CTableProduct = ProductTab.getColumnModel();
                        CTableProduct.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
//                        CTableProduct.getColumn(6).setCellRenderer(NumberRenderer.getCurrencyRenderer());
                        while(Table.getRowCount() > 0) Table.removeRow(0);
			Conn.rowSet.setCommand("select a.idinventory,b.name,a.name,a.price_Sale from inventory a,category b where a.idCategory = b.idCategory");
			Conn.rowSet.execute();
			while(Conn.rowSet.next())
                        {
                            Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getString(3),Conn.rowSet.getDouble(4)});
                        }
                        updateTableHistory();
		} catch (SQLException ex) {
			Logger.getLogger(ProductInv.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void updateTableHistory(){
		try {
                        DefaultTableModel Table = (DefaultTableModel) HistoryTab.getModel();
                        TableColumnModel CTableProduct = HistoryTab.getColumnModel();
                        CTableProduct.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
                        CTableProduct.getColumn(4).setCellRenderer(NumberRenderer.getCurrencyRenderer());
                        while(Table.getRowCount() > 0) Table.removeRow(0);
			Conn.rowSet.setCommand("select a.date,a.inventory_idinventory,b.name,a.status,a.before,a.after from hist_price a, inventory b where a.inventory_idinventory = b.idinventory order by a.idHist desc");
			Conn.rowSet.execute();
			while(Conn.rowSet.next())
                        {
                            Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getString(3),Conn.rowSet.getString(4),Conn.rowSet.getDouble(5),Conn.rowSet.getDouble(6)});
                        }
		} catch (SQLException ex) {
			Logger.getLogger(Set_Price.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void SearchTableHistory(){
        if(IDSearch.get(ComboSearch.getSelectedIndex()).equals("0")){
            updateTableHistory();
        }else{
            try {
                        DefaultTableModel Table = (DefaultTableModel) HistoryTab.getModel();
                        TableColumnModel CTableProduct = HistoryTab.getColumnModel();
                        CTableProduct.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
                        CTableProduct.getColumn(4).setCellRenderer(NumberRenderer.getCurrencyRenderer());
                        while(Table.getRowCount() > 0) Table.removeRow(0);
			Conn.rowSet.setCommand("select a.date,a.inventory_idinventory,b.name,a.status,a.before,a.after from hist_price a, inventory b where a.inventory_idinventory = b.idinventory and a.inventory_idinventory = '"+IDSearch.get(ComboSearch.getSelectedIndex())+"' order by a.idHist desc");
			Conn.rowSet.execute();
			while(Conn.rowSet.next())
                        {
                            Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getString(3),Conn.rowSet.getString(4),Conn.rowSet.getDouble(5),Conn.rowSet.getDouble(6)});
                        }
		} catch (SQLException ex) {
			Logger.getLogger(Set_Price.class.getName()).log(Level.SEVERE, null, ex);
		}
        }
    }
    
    public void Clear(){
        TXTPrice.setText("");
        IDInv.setText("");
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
        TXTPrice = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        UpdateBut = new javax.swing.JButton();
        IDInv = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTab = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        HistoryTab = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        ComboSearch = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        BackBut = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Set Price Product", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        TXTPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TXTPriceKeyReleased(evt);
            }
        });

        jLabel8.setText("Price");

        jLabel12.setText(":");

        jLabel1.setText("ID");

        jLabel13.setText(":");

        UpdateBut.setText("Set");
        UpdateBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButActionPerformed(evt);
            }
        });

        ProductTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category", "Name", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        jScrollPane1.setViewportView(ProductTab);

        HistoryTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "ID", "Name", "Status", "Before", "After"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(HistoryTab);

        jLabel3.setText("Search");

        ComboSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select..." }));
        ComboSearch.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboSearchItemStateChanged(evt);
            }
        });

        jLabel14.setText(":");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TXTPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IDInv, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 150, Short.MAX_VALUE)
                        .addComponent(UpdateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel13)
                            .addComponent(IDInv, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TXTPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(UpdateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        BackBut.setText("Back");
        BackBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void UpdateButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButActionPerformed
        if(IDInv.getText().isEmpty() == true)
        {
            JOptionPane.showMessageDialog(null, "Please Select Your Item First!!","ERROR!!",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try {
                AFTER = Double.parseDouble(TXTPrice.getText());
                String IDGenerate = AllClass.GenerateID("hist_price", "idHist");
                Conn.rowSet.setCommand("select * from hist_price where inventory_idinventory = '"+ IDInv.getText() +"'");
                Conn.rowSet.execute();
                Conn.rowSet.first();
                Conn.rowSet.moveToInsertRow();
                Conn.rowSet.updateString("idHist", IDGenerate);
                Conn.rowSet.updateString("inventory_idinventory", IDInv.getText());
                if(OLD>AFTER){
                    Conn.rowSet.updateString("status", "Down");
                }else if(OLD<AFTER){
                    Conn.rowSet.updateString("status", "Up");
                }else{
                    Conn.rowSet.updateString("status", "Normal");
                }
                Conn.rowSet.updateDouble("before", this.OLD);
                Conn.rowSet.updateDouble("after", Double.parseDouble(TXTPrice.getText()));
                Conn.rowSet.updateTimestamp("date", new Timestamp(System.currentTimeMillis()));
                Conn.rowSet.insertRow();
                Conn.rowSet.setCommand("select * from inventory where idinventory = '"+ IDInv.getText() +"'");
                Conn.rowSet.execute();
                Conn.rowSet.next();
                Conn.rowSet.updateDouble("price_Sale", Double.parseDouble(TXTPrice.getText()));
                Conn.rowSet.updateRow();
                updateTable();
            } catch (SQLException ex) {
                Logger.getLogger(ProductInv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Clear();
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateButActionPerformed

    private void BackButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButActionPerformed
        MenuUtama MenuUtama = new MenuUtama();
        Tools.change(this, MenuUtama);
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButActionPerformed

    private void ProductTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductTabMouseClicked
        int baris = ProductTab.getSelectedRow();
        IDInv.setText(ProductTab.getValueAt(baris, 0).toString());
        TXTPrice.setText(ProductTab.getValueAt(baris, 3).toString());
        this.OLD = Double.parseDouble(ProductTab.getValueAt(baris, 3).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductTabMouseClicked

    private void ComboSearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboSearchItemStateChanged
        // TODO add your handling code here:
//        System.out.println("GantiItem");
//        System.out.println(ComboSearch.getSelectedIndex());
//        System.out.println("List Index : "+ IDSearch.get(ComboSearch.getSelectedIndex()));
        SearchTableHistory();
    }//GEN-LAST:event_ComboSearchItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBut;
    private javax.swing.JComboBox ComboSearch;
    private javax.swing.JTable HistoryTab;
    private javax.swing.JLabel IDInv;
    private javax.swing.JTable ProductTab;
    private javax.swing.JTextField TXTPrice;
    private javax.swing.JButton UpdateBut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
