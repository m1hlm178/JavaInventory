/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stokbarangpddj.ToolAndClass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author Susanto
 */
public class RunningTextActionListener implements ActionListener {  
    JLabel label;  
      
    public RunningTextActionListener(JLabel label){  
        this.label = label;  
    }  
      
    public void actionPerformed(ActionEvent e){  
        String oldText = label.getText();  
        String newText = oldText.substring(1)+oldText.substring(0, 1);  
        label.setText(newText);  
    }  
}  
