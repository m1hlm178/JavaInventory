//    private void total_transaksi() {
//        try {
//            dfs.setCurrencySymbol("");
//            dfs.setMonetaryDecimalSeparator(',');
//            dfs.setGroupingSeparator('.');
//            df.setDecimalFormatSymbols(dfs);
//            String total = "select sum(qty), sum(price) from purchase_detail where idpurchase = " + IDP.getText() + "";
//            Conn.rowSet.setCommand(total);
//            Conn.rowSet.execute();
//            while (Conn.rowSet.next()) {
//                totalp = Conn.rowSet.getDouble(2);
//                TOTALQTY.setText(Integer.toString(Conn.rowSet.getInt(1)));
//            }
//            TOTALPRICE.setText("Rp. " + df.format(totalp));
//            String InsertTot = "select * from purchase where idpurchase = " + IDP.getText() + "";
//            Conn.rowSet.setCommand(InsertTot);
//            Conn.rowSet.execute();
//            Conn.rowSet.first();
//            Conn.rowSet.updateDouble("Total_Payment", totalp);
//            Conn.rowSet.updateRow();
//        } catch (SQLException ex) {
//            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    private void DellTrans(){
//        try {
//            Conn.rowSet.setCommand("select * from inventory where idinventory = '" + idprod + "'");
//            Conn.rowSet.execute();
//            while (Conn.rowSet.next()) {
//                oldinv = Conn.rowSet.getInt("qty");
//            }
//            totalinv = oldinv - Integer.parseInt(TXTQTY.getText());
//            Conn.rowSet.setCommand("select * from inventory where idinventory = '" + idprod + "'");
//            Conn.rowSet.execute();
//            Conn.rowSet.first();
//            Conn.rowSet.updateInt("qty", totalinv);
//            Conn.rowSet.updateRow();
//        } catch (SQLException ex) {
//            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        total_transaksi();
//        updateTable();
//    }
//    public void updateTable(){
//            DefaultTableModel Table = (DefaultTableModel) TablePurch.getModel();
//            TableColumnModel CTablePurch = TablePurch.getColumnModel();
//            CTablePurch.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
//            while(Table.getRowCount() > 0) Table.removeRow(0);
//		try {                        
//			Conn.rowSet.setCommand("select * from purchase_detail as P join inventory as I where P.idinventory = I.idinventory AND idpurchase = '" + IDP.getText() + "'");
//			Conn.rowSet.execute();
//			while(Conn.rowSet.next())
//                        {
//                            Table.addRow(new Object[]{Conn.rowSet.getInt("P.idinventory"),Conn.rowSet.getString("I.name"),Conn.rowSet.getInt("P.qty"),Conn.rowSet.getDouble("P.price")});
//                        }
//		} catch (SQLException ex) {
//			Logger.getLogger(Purchase_BC.class.getName()).log(Level.SEVERE, null, ex);
//		}
//    }