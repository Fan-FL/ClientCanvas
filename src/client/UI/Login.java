package client.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.ClientCommunication;




public class Login implements ActionListener{
    public JFrame loginFrame = new JFrame("Mini-Canvas  Client Login");
    public JTextField userName;
    public JTextField ipAddress;
    public JTextField portNumber;

    
    public void login_window(){
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowWidth = 512;
        int windowHeight = 425;
        
        JPanel icon = new JPanel();
        JPanel text = new JPanel();
        JLabel background = new JLabel();
        JLabel ip = new JLabel("   Server   IP  address:");
        JLabel port = new JLabel("   Server port number:");
        JLabel username = new JLabel("   Your   user   name:");
        ipAddress = new JTextField(15);
        portNumber = new JTextField(15);
        userName = new JTextField(15);
        JButton connect = new JButton(" Connect ");
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
        inputPort.add(portNumber);
        
        JPanel UserName = new JPanel();
        UserName.add(username);
        UserName.add(userName);
        
        JPanel buttons = new JPanel();
        JPanel bbuttons = new JPanel();
        buttons.add(bbuttons, BorderLayout.CENTER);
        bbuttons.add(connect);
        bbuttons.add(exit);
        
        text.setLayout( new BorderLayout() );
        text.add(inputIp,BorderLayout.NORTH);
        text.add(inputPort,BorderLayout.CENTER);
        text.add(UserName,BorderLayout.SOUTH);
        
        loginFrame.setBounds((width - windowWidth) / 2,
                (height - windowHeight) / 2, windowWidth, windowHeight);
        loginFrame.setVisible(true);
        loginFrame.setLayout( new BorderLayout() );
        loginFrame.add(icon, BorderLayout.NORTH);
        loginFrame.add(text, BorderLayout.CENTER);
        loginFrame.add(bbuttons, BorderLayout.SOUTH);
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
    }   
    
    public void actionPerformed(ActionEvent e) {  
        if(e.getActionCommand().equals("connect")){  
        	@SuppressWarnings("unused")
        	ClientCommunication clientCommunication = new ClientCommunication();
        	String ServerIp = ipAddress.getText();
        	String ServerPort = portNumber.getText();
        	String myUserName = userName.getText();
        	clientCommunication.connect(ServerIp, ServerPort);
        	//The whiteBoard needs to be put somewhere else.
            WhiteBoardClient whiteBoard = new WhiteBoardClient("Mini-Canvas Client");
        	loginFrame.dispose();
        	
        }  
        if(e.getActionCommand().equals("exit")){  
            System.exit(0);  
        }  
    }
}