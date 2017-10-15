package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.UI.Login;

public class ClientCommunication {
	
	public void connect(String Ip, String port){
		int portNumber = 0;
		try{
			portNumber = Integer.parseInt(Ip);
		}
		catch(Exception ex){
			System.out.println("You must input a valid port number!");
		}
		
		try {
			Socket socket = new Socket(Ip, portNumber);
			System.out.println("Connect successfully!");
		} catch (UnknownHostException e1) {
			System.out.println("Unknown server! Please try again!");
		} catch (IOException e1) {
			System.out.println("Unknown server! Please try again!");
		}
	}

}
