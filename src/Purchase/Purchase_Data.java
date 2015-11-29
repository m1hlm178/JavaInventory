/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Purchase;

import Sales.Sales_Data;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.MenuUtama;
import stokbarangpddj.ToolAndClass.ComboBoxList;
import stokbarangpddj.ToolAndClass.NumberRenderer;
import stokbarangpddj.ToolAndClass.Tools;

/**
 *
 * @author Susanto
 */
public class Purchase_Data extends javax.swing.JInternalFrame {
public String id_pruch;
Vector vectorName = new Vector();
    /**
     * Creates new form PurchaseList
     */
    public Purchase_Data() {
        initComponents();
        updatetable();
        SetUpComboBox();
//        LoadDataName();
    }
    
    public void LoadDataName(){
        try {
            
            Conn.rowSet.setCommand("select name from distributor");
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                    vectorName.add(Conn.rowSet.getString(1));
            }
            } catch (SQLException ex) {
                Logger.getLogger(Sales_Data.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void SetUpComboBox(){
        LoadDataName();
        ComboBoxName.setModel(new DefaultComboBoxModel(vectorName));
        ComboBoxName.setSelectedIndex(-1);
        JTextField textField = (JTextField) ComboBoxName.getEditor().getEditorComponent();
        textField.setFocusable(true);
        textField.setText("");
        textField.addKeyListener(new ComboBoxList(ComboBoxName, vectorName));
    }
    
    public void updatetable(){        
        DefaultTableModel Table = (DefaultTableModel) TableList.getModel();
        TableColumnModel CTable = TableList.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        TableList.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        try {
            String sql = "select p.idpurchase, d.name, p.Total_QTY, p.total_payment, p.date, p.status from purchase p, distributor d where p.iddistributor = d.iddistributor order by p.idpurchase desc";
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Purchase_Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchdate(){
        DefaultTableModel Table = (DefaultTableModel) TableList.getModel();
        TableColumnModel CTable = TableList.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        TableList.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dates = dateFormat.format(ComboDate.getDate());
            String sql = "select p.idpurchase, d.name, p.Total_QTY, p.total_payment, p.date, p.status from purchase p, distributor d where p.iddistributor = d.iddistributor and p.date between '" + dates + " 01:00:00' AND '" + dates + " 23:59:59' order by p.idpurchase desc";
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Purchase_Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchname(){
        DefaultTableModel Table = (DefaultTableModel) TableList.getModel();
        TableColumnModel CTable = TableList.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        TableList.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
        try {
            String sql = "select p.idpurchase, d.name, p.Total_QTY, p.total_payment, p.date, p.status from purchase p, distributor d where p.iddistributor = d.iddistributor and d.name = '" + ComboBoxName.getSelectedItem() + "' order by p.idpurchase desc";
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Purchase_Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchboth(){
        DefaultTableModel Table = (DefaultTableModel) TableList.getModel();
        TableColumnModel CTable = TableList.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        TableList.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
    try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dates = dateFormat.format(ComboDate.getDate());
        String sql = "select p.idpurchase, d.name, p.Total_QTY, p.total_payment, p.date, p.status from purchase p, distributor d where p.iddistributor = d.iddistributor and p.date between '" + dates + " 01:00:00' AND '" + dates + " 23:59:59' and d.name = '" + ComboBoxName.getSelectedItem() + "' order by p.idpurchase desc";
        Conn.rowSet.setCommand(sql);
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
            Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
        }
        } catch (SQLException ex) {
            Logger.getLogger(Purchase_Data.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableList = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ComboDate = new org.freixas.jcalendar.JCalendarCombo();
        ScDateBut = new javax.swing.JButton();
        ResetBut = new javax.swing.JButton();
        ComboBoxName = new javax.swing.JComboBox();
        ScNameBut = new javax.swing.JButton();
        ScBothBut = new javax.swing.JButton();
        BackBut = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 597));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Purchase List", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        TableList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Purchase", "Distributor", "Total QTY", "Total Payment", "Date", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableList);
        if (TableList.getColumnModel().getColumnCount() > 0) {
            TableList.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableList.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        jLabel1.setText("Date  :");

        jLabel2.setText("Name  :");

        ScDateBut.setText("Search Date");
        ScDateBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScDateButActionPerformed(evt);
            }
        });

        ResetBut.setText("Reset");
        ResetBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButActionPerformed(evt);
            }
        });

        ComboBoxName.setEditable(true);

        ScNameBut.setText("Search Name");
        ScNameBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScNameButActionPerformed(evt);
            }
        });

        ScBothBut.setText("Search Both");
        ScBothBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScBothButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboDate, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ScDateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ScNameBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ScBothBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboBoxName, 0, 348, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ResetBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(ComboDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ScDateBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResetBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ScNameBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ScBothBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void ScDateButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScDateButActionPerformed
        searchdate();
        // TODO add your handling code here:
    }//GEN-LAST:event_ScDateButActionPerformed

    private void ResetButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButActionPerformed
        updatetable();
        // TODO add your handling code here:
    }//GEN-LAST:event_ResetButActionPerformed

    private void BackButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButActionPerformed
        MenuUtama MenuUtama = new MenuUtama();
        Tools.change(this, MenuUtama);
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButActionPerformed

    private void TableListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableListMouseClicked
        id_pruch = TableList.getValueAt(TableList.getSelectedRow(), 0).toString();
        if (TableList.getSelectedColumn() == 6) {
            Purchase_Detail Purchase_Detail = new Purchase_Detail(id_pruch);
            Tools.change(this, Purchase_Detail);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TableListMouseClicked

    private void ScNameButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScNameButActionPerformed
        searchname();
        // TODO add your handling code here:
    }//GEN-LAST:event_ScNameButActionPerformed

    private void ScBothButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScBothButActionPerformed
        searchboth();
        // TODO add your handling code here:
    }//GEN-LAST:event_ScBothButActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBut;
    private javax.swing.JComboBox ComboBoxName;
    private org.freixas.jcalendar.JCalendarCombo ComboDate;
    private javax.swing.JButton ResetBut;
    private javax.swing.JButton ScBothBut;
    private javax.swing.JButton ScDateBut;
    private javax.swing.JButton ScNameBut;
    private javax.swing.JTable TableList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
