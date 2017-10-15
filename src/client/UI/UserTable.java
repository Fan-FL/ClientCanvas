package client.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserTable extends JPanel{

    private DefaultTableModel tableModel;
    private JTable table;
    private WhiteBoardClientWindow whiteboard = null;

    private String myUsername = "";

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
        scrollPane.setViewportView(table);
    }
}