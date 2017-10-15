package client.controller;

import client.UI.LoginWindow;
import client.UI.WhiteBoardClientWindow;
import client.net.TCPClient;
import client.shape.*;


public class Controller {
    private TCPClient tcpClient;
    private LoginWindow loginWindow;
    private WhiteBoardClientWindow whiteBoardWindow;

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
    }

    public void showWhiteBoardWindow() {
        // close waiting window
        this.whiteBoardWindow = new WhiteBoardClientWindow("Mini-Canvas Client", this);
        this.loginWindow.finish();
    }


    public void rejectByServer() {
        // do something
    }

    public void sendToServerData(String msg) {
        this.getTcpClient().sendData(msg);
    }

    public void addShape(Shape shape) {
        this.whiteBoardWindow.getDrawarea().addShape(shape);
    }
}
