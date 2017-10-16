package client.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class UserTable extends JPanel{

    private DefaultTableModel tableModel;
    private JTable table;
    private WhiteBoardClientWindow whiteboard = null;

    private String myUsername = "";
    
	Toolkit tool = getToolkit();
	Dimension dim = tool.getScreenSize();// Get the size of current screen

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public UserTable(WhiteBoardClientWindow whiteboard){
        super();
        this.whiteboard = whiteboard;
        String[] columnNames = {"Username"};
        tableModel = new DefaultTableModel(columnNames,0);
        table = new JTable(tableModel){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(250,dim.height - 270));
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(table);
    }

    public synchronized void addUser(String username){
        String []rowValues = {username};
        tableModel.addRow(rowValues);
    }

    public synchronized void deleteUser(String username){
        int rowCount = tableModel.getRowCount();
        if(rowCount != -1){
            for (int i=0; i<rowCount; i++){
                if(table.getValueAt(i,0).equals(username)){
                    tableModel.removeRow(i);
                    return;
                }
            }
        }else{
            return;
        }
    }
}