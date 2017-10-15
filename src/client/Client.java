package client;

import client.UI.Login;

import java.awt.EventQueue;

/*
The main class of this program
 */
public class Client {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.login_window();
					//window.loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
