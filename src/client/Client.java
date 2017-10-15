package client;

import client.UI.LoginWindow;

import java.awt.EventQueue;

/*
The main class of this program
 */
public class Client {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.showLoginWindow();
					//window.loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
