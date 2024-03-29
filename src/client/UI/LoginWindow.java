package client.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import client.controller.Controller;


public class LoginWindow implements ActionListener{
    public JFrame loginFrame = new JFrame("Mini-Canvas  Client LoginWindow");
    public JTextField ipAddress;
    public JTextField portNumberTextField;
    private Controller controller;
    JButton connect;

    
    public void showLoginWindow(){
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowWidth = 512;
        int windowHeight = 390;
        
        JPanel icon = new JPanel();
        JPanel text = new JPanel();
        JLabel background = new JLabel();
        JLabel ip = new JLabel("   Server     IP:");
        JLabel port = new JLabel("   Server port:");
        ipAddress = new JTextField(15);
        portNumberTextField = new JTextField(15);
        connect = new JButton(" Connect ");
        JButton exit = new JButton("     Exit    ");
        
		connect.addActionListener(this);
		connect.setActionCommand("connect");
		exit.addActionListener(this);
		exit.setActionCommand("exit");
        
        
        ImageIcon image = new ImageIcon(getClass().getResource("/icon/Loginbackground.jpg"));
        background.setIcon(image);
        icon.add(background);
        
        JPanel inputIp = new JPanel();
        inputIp.add(ip);
        inputIp.add(ipAddress);
        
        JPanel inputPort = new JPanel();
        inputPort.add(port);
        inputPort.add(portNumberTextField);
        

        JPanel buttons = new JPanel();
        JPanel bbuttons = new JPanel();
        buttons.add(bbuttons, BorderLayout.CENTER);
        bbuttons.add(connect);
        bbuttons.add(exit);
        
        text.setLayout( new BorderLayout() );
        text.add(inputIp,BorderLayout.NORTH);
        text.add(inputPort,BorderLayout.CENTER);
        
        loginFrame.setBounds((width - windowWidth) / 2,
                (height - windowHeight) / 2, windowWidth, windowHeight);
        loginFrame.setVisible(true);
        loginFrame.setLayout( new BorderLayout() );
        loginFrame.add(icon, BorderLayout.NORTH);
        loginFrame.add(text, BorderLayout.CENTER);
        loginFrame.add(bbuttons, BorderLayout.SOUTH);
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                if(controller != null){
                    controller.getTcpClient().stop("kick");
                }
                System.exit(0);
            }

        });
                
    }   
    
    public void actionPerformed(ActionEvent e) {  
        if(e.getActionCommand().equals("connect")){

        	String serverIp = ipAddress.getText();
            int serverPort;
            try{
                serverPort = Integer.parseInt(portNumberTextField.getText());
            } catch (NumberFormatException exception){
                return;
            }
            if(serverPort!=0){
                this.controller = new Controller();
                this.controller.setLoginWindow(this);
                connect.setText("Connecting...");
                controller.connectServer(serverIp, serverPort);
                ipAddress.setEnabled(false);
                portNumberTextField.setEnabled(false);
                connect.setEnabled(false);
            }
        }else if(e.getActionCommand().equals("exit")){
            System.exit(0);  
        }  
    }

    public void finish() {
        loginFrame.dispose();
        ipAddress = null;
        portNumberTextField = null;
    }
}