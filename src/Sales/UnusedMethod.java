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
//    private void DeleteTrans() {
//        try {
//            int baris = CartTab.getSelectedRow();
//            Conn.rowSet.setCommand("select * from sales_detail where idsales = '" + IDS.getText() + "'");
//            Conn.rowSet.execute();
//            Conn.rowSet.absolute(baris+1);
//            Conn.rowSet.deleteRow();
//            Conn.rowSet.setCommand("select * from inventory where idinventory = '" + IDSPP.getText() + "'");
//            Conn.rowSet.execute();
//            Conn.rowSet.next();
//            oldinv = Conn.rowSet.getInt("qty");
//            totalinv = oldinv + Integer.parseInt(TXTQTY.getText());
//            Conn.rowSet.updateInt("qty", totalinv);
//            Conn.rowSet.updateRow();
//        } catch (SQLException ex) {
//            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        total_transaksi();
//        updatecart();
//        updateTableProduct();
//    }


//    private void total_transaksi() {
//        try {
//            dfs.setCurrencySymbol("");
//            dfs.setMonetaryDecimalSeparator(',');
//            dfs.setGroupingSeparator('.');
//            df.setDecimalFormatSymbols(dfs);
//            String total = "select sum(qty), sum(price), sum(Discount) from sales_detail where idsales = " + IDS.getText() + "";
//            Conn.rowSet.setCommand(total);
//            Conn.rowSet.execute();
//            while (Conn.rowSet.next()) {
//                totalrp = Conn.rowSet.getDouble(2);
//                totalqty = Conn.rowSet.getInt(1);
//                disc = Conn.rowSet.getInt(3);
//            }
//            TXTPayment.setText("Rp. " + df.format(totalrp));
//            TOTALQTY.setText(String.valueOf(totalqty));
//            TXTDisc.setText(String.valueOf(disc));
//            String InsertTot = "select * from sales where idsales = " + IDS.getText() + "";
//            Conn.rowSet.setCommand(InsertTot);
//            Conn.rowSet.execute();
//            Conn.rowSet.first();
//            Conn.rowSet.updateDouble("Discount", disc);
//            Conn.rowSet.updateDouble("Total_Payment", totalrp);
//            Conn.rowSet.updateRow();
//        } catch (SQLException ex) {
//            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
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

//    public void updatecart(){
//        try {
//                Conn.rowSet.setCommand("select s.idinventory as ID, i.name as Name, i.unit as Unit, s.qty as QTY, s.Discount as Potongan, s.price as Price from sales_detail s, inventory i where s.idinventory = i.idinventory and idsales = '" + IDS.getText()+ "'");
//                Conn.rowSet.execute();
//                CartTab.setModel(DbUtils.resultSetToTableModel(Conn.rowSet));
//        } catch (SQLException ex) {
//                Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }