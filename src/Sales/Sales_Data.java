/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import Purchase.buttonDetail;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import stokbarangpddj.MenuUtama;
import stokbarangpddj.ToolAndClass.ComboBoxList;
import stokbarangpddj.ToolAndClass.Conn;
import stokbarangpddj.ToolAndClass.NumberRenderer;
import stokbarangpddj.ToolAndClass.Tools;

/**
 *
 * @author Susanto
 */
public class Sales_Data extends javax.swing.JInternalFrame {
private String id_sales;
Vector vectorName = new Vector();
    /**
     * Creates new form Sales_Data
     */
    public Sales_Data() {
        initComponents();
        updatetable();
        SetUpComboBox();
    }
    
    public void SetUpComboBox(){
        LoadDataName();
        ComboCust.setModel(new DefaultComboBoxModel(vectorName));
        ComboCust.setSelectedIndex(-1);
        JTextField textField = (JTextField) ComboCust.getEditor().getEditorComponent();
        textField.setFocusable(true);
        textField.setText("");
        textField.addKeyListener(new ComboBoxList(ComboCust, vectorName));
    }
    
    public void LoadDataName(){
        try {
        Conn.rowSet.setCommand("select name from consumer");
        Conn.rowSet.execute();
        while (Conn.rowSet.next()) {
                vectorName.add(Conn.rowSet.getString(1));
        }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updatetable(){        
        DefaultTableModel Table = (DefaultTableModel) Table_List.getModel();
        TableColumnModel CTable = Table_List.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        Table_List.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        try {
            String sql = "select s.idsales,c.name,s.Total_QTY,s.Total_Payment,s.Date,p.Type_Payment from sales s, consumer c, payment p where s.idconsumer = c.idconsumer and s.idpayment = p.idpayment order by s.date desc";
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchdate(){
        DefaultTableModel Table = (DefaultTableModel) Table_List.getModel();
        TableColumnModel CTable = Table_List.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        Table_List.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dates = dateFormat.format(ComboDate.getDate());
            String sql = "select s.idsales,c.name,s.Total_QTY,s.Total_Payment,s.Date,p.Type_Payment from sales s, consumer c, payment p where s.idconsumer = c.idconsumer and s.idpayment = p.idpayment and s.date between '" + dates + " 01:00:00' AND '" + dates + " 23:59:59' order by s.date desc";
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchname(){
        DefaultTableModel Table = (DefaultTableModel) Table_List.getModel();
        TableColumnModel CTable = Table_List.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        Table_List.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
        try {
            String sql = "select s.idsales,c.name,s.Total_QTY,s.Total_Payment,s.Date,p.Type_Payment from sales s, consumer c, payment p where s.idconsumer = c.idconsumer and s.idpayment = p.idpayment and c.name = '" + ComboCust.getSelectedItem() + "' order by s.date desc";
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchboth(){
        DefaultTableModel Table = (DefaultTableModel) Table_List.getModel();
        TableColumnModel CTable = Table_List.getColumnModel();
        CTable.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        CTable.getColumn(4).setCellRenderer(NumberRenderer.getDateTimeRenderer());
        while(Table.getRowCount() > 0) Table.removeRow(0);
        Table_List.getColumnModel().getColumn(6).setCellRenderer(new buttonDetail());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dates = dateFormat.format(ComboDate.getDate());
            String sql = "select s.idsales,c.name,s.Total_QTY,s.Total_Payment,s.Date,p.Type_Payment from sales s, consumer c, payment p where s.idconsumer = c.idconsumer and s.idpayment = p.idpayment and s.date between '" + dates + " 01:00:00' AND '" + dates + " 23:59:59' and c.name = '" + ComboCust.getSelectedItem() + "' order by s.date desc";
            Conn.rowSet.setCommand(sql);
            Conn.rowSet.execute();
            while (Conn.rowSet.next()) {
                Table.addRow(new Object[]{Conn.rowSet.getString(1),Conn.rowSet.getString(2),Conn.rowSet.getInt(3),Conn.rowSet.getDouble(4),Conn.rowSet.getTimestamp(5),Conn.rowSet.getString(6)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_Data.class.getName()).log(Level.SEVERE, null, ex);
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
        B_BACK = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_List = new javax.swing.JTable();
        B_Date = new javax.swing.JButton();
        B_Name = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ComboDate = new org.freixas.jcalendar.JCalendarCombo();
        jLabel1 = new javax.swing.JLabel();
        B_Reset = new javax.swing.JButton();
        B_Both = new javax.swing.JButton();
        ComboCust = new javax.swing.JComboBox();

        setPreferredSize(new java.awt.Dimension(900, 597));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sales Data", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel1.setPreferredSize(new java.awt.Dimension(845, 464));

        B_BACK.setText("Back");
        B_BACK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_BACKActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Table_List.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sales", "Customer", "Total QTY", "Total Payment", "Date", "Status", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_List.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_List);
        if (Table_List.getColumnModel().getColumnCount() > 0) {
            Table_List.getColumnModel().getColumn(0).setPreferredWidth(30);
            Table_List.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        B_Date.setText("Search By Date");
        B_Date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_DateActionPerformed(evt);
            }
        });

        B_Name.setText("Search By Name");
        B_Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_NameActionPerformed(evt);
            }
        });

        jLabel2.setText("Date :");

        jLabel1.setText("Customer Name :");

        B_Reset.setText("Reset");
        B_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_ResetActionPerformed(evt);
            }
        });

        B_Both.setText("Search By Name And Date");
        B_Both.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_BothActionPerformed(evt);
            }
        });

        ComboCust.setEditable(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboDate, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboCust, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(B_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B_Both, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 276, Short.MAX_VALUE)))
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
                    .addComponent(ComboCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(B_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B_Both, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(B_BACK, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(B_BACK, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_DateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_DateActionPerformed
        // TODO add your handling code here:
        searchdate();
    }//GEN-LAST:event_B_DateActionPerformed

    private void B_NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_NameActionPerformed
        // TODO add your handling code here:
        searchname();
    }//GEN-LAST:event_B_NameActionPerformed

    private void B_BothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_BothActionPerformed
        // TODO add your handling code here:
        searchboth();
    }//GEN-LAST:event_B_BothActionPerformed

    private void B_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_ResetActionPerformed
        // TODO add your handling code here:
        ComboCust.setSelectedIndex(-1);
        updatetable();
    }//GEN-LAST:event_B_ResetActionPerformed

    private void Table_ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ListMouseClicked
        id_sales = Table_List.getValueAt(Table_List.getSelectedRow(), 0).toString();
        if (Table_List.getSelectedColumn() == 6) {
            Sales_Detail Sales_Detail = new Sales_Detail(id_sales);
            Tools.change(this, Sales_Detail);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_Table_ListMouseClicked

    private void B_BACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_BACKActionPerformed
        // TODO add your handling code here:
        MenuUtama MenuUtama = new MenuUtama();
        Tools.change(this, MenuUtama);
    }//GEN-LAST:event_B_BACKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_BACK;
    private javax.swing.JButton B_Both;
    private javax.swing.JButton B_Date;
    private javax.swing.JButton B_Name;
    private javax.swing.JButton B_Reset;
    private javax.swing.JComboBox ComboCust;
    private org.freixas.jcalendar.JCalendarCombo ComboDate;
    private javax.swing.JTable Table_List;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
