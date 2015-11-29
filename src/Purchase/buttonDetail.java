/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Purchase;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Susanto
 */
public class buttonDetail extends JButton implements TableCellRenderer{
    public buttonDetail() { 
    setText("Detail"); 
    } 
     
 @Override 
 
public Component getTableCellRendererComponent(JTable table, Object value, 
boolean isSelected, boolean hasFocus, int row, int column) { 
         
setBackground(isSelected?table.getSelectionBackground():table.getBackground()); 
        return this; 
    }
}
