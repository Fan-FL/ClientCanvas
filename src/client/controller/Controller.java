package client.controller;

import client.UI.LoginWindow;
import client.UI.WhiteBoardClientWindow;
import client.net.TCPClient;
import client.shape.*;

import javax.swing.*;


public class Controller {
    private TCPClient tcpClient;
    private LoginWindow loginWindow;
    private WhiteBoardClientWindow whiteBoardWindow;

    public WhiteBoardClientWindow getWhiteBoardWindow() {
        return whiteBoardWindow;
    }

    public void setWhiteBoardWindow(WhiteBoardClientWindow whiteBoardWindow) {
        this.whiteBoardWindow = whiteBoardWindow;
    }

    public void setLoginWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    public LoginWindow getLoginWindow() {
        return loginWindow;
    }

    public TCPClient getTcpClient() {
        return tcpClient;
    }

    public void setTcpClient(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public void connectServer(String serverIP, int serverPort) {
        this.tcpClient = new TCPClient(serverIP, serverPort);
        this.tcpClient.setController(this);
        this.tcpClient.connect();
    }

    public void connectSuccessfully() {
        //waiting approval window
    	//JOptionPane.showMessageDialog(null, "waiting for server approval", "notice", JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(null, "waiting for server approval", "notice", JOptionPane.WARNING_MESSAGE);
    }

    public void showWhiteBoardWindow(String username) {
        // close waiting window
        this.whiteBoardWindow = new WhiteBoardClientWindow("Mini-Canvas Client", this);
        this.loginWindow.finish();
        this.whiteBoardWindow.getUserTable().setMyUsername(username);
    }

    public void sendToServerData(String msg) {
        this.getTcpClient().sendData(msg);
    }

    public void addShape(Shape shape) {
        this.whiteBoardWindow.getDrawarea().addShape(shape);
    }

    public void clearCanvas() {
        this.whiteBoardWindow.getDrawarea().clearCanvas();
    }

    public void quit() {
        //add pop up window here
        System.exit(0);
    }

    public void addUser(String username) {
        this.whiteBoardWindow.getUserTable().addUser(username);
    }

    public void deleteUser(String username){
        this.whiteBoardWindow.getUserTable().deleteUser(username);
    }

    public void sendChatMessage(String text) {
        sendToServerData("{\"cmd\":\"addChat\",\"content\":\"" + text + "\"}");
    }

    public void addChatMessage(String text){
        this.whiteBoardWindow.addChatMessage(text);
    }

    public void serverClosed() {
        System.exit(0);
    }

    public void close(String type) {
        if(type != null){
            switch (type) {
                case "reject":
                    JOptionPane.showMessageDialog(null, "You are rejected by the server", "warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case "kick":
                    JOptionPane.showMessageDialog(null, "You are kicked out by the server", "warning", JOptionPane.WARNING_MESSAGE);
                    break;
                case "serverClosed":
                    JOptionPane.showMessageDialog(null, "Server is closed", "alert", JOptionPane.WARNING_MESSAGE);
                    break;
                default:
                    break;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Disconnected or cannot connect to the server", "alert", JOptionPane.WARNING_MESSAGE);
        }
        System.exit(0);
    }
}
