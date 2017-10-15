package client.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import client.controller.Controller;
import client.shape.Shape;
import client.util.AES;
import client.util.JsonMessageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class TCPClient{
	private Controller controller;

	private DataInputStream input = null;
	private DataOutputStream output = null;

	private String serverIP;
	private int serverPort;

	private Socket socket = null;
	private volatile boolean running = false;

	public String getServerIP() {
		return serverIP;
	}
	public TCPClient(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public boolean connect() {
		// is already running
		if (running)
			return false;
		// not running
		try{
			this.socket = new Socket(serverIP, serverPort);
			System.out.println("local port : " + socket.getLocalPort());
			running = true;
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			//connect successfully
			this.controller.connectSuccessfully();
			// start hearbeat thread
			new Thread(new SendHeartbeat()).start();
			// start message receiving thread
			new Thread(new ReceiveWatchDog()).start();
			return true;
		}catch (UnknownHostException e){
			System.out.println("Connection excpetion to the server:"+e.getMessage());
			stop();
			return false;
		}catch (IOException e){
			System.out.println(e.getMessage() + System.getProperty("line.separator"));
			stop();
			return false;
		}
	}

	public boolean isConnected() {
		return running;
	}

	public void sendData(String msg) {
		try {
			output.writeUTF(AES.Encrypt(msg));
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
			stop();
		}
	}

	public void stop() {
		if (running)
			running = false;
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				socket = null;
				input = null;
				output = null;
			}
		}
        System.out.println("connection closed.");
	}

	class SendHeartbeat implements Runnable {
		String heartbeatData = "{\"cmd\":\"heartbeat\",\"content\":\"alive\"}";
		private long checkDelay = 100;
		// send heartbeat message every 10 seconds
		long hearbeatDelay = 10000;
		private long lastSendTime = System.currentTimeMillis();

		@Override
		public void run() {
			while (running) {
				if (System.currentTimeMillis() - lastSendTime > hearbeatDelay) {
					System.out.println(heartbeatData);
					sendData(heartbeatData);
					lastSendTime = System.currentTimeMillis();
				} else {
					try {
						Thread.sleep(checkDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
						stop();
					}
				}
			}
		}
	}

	class ReceiveWatchDog implements Runnable {
		private long checkDelay = 100;
		// longer than the delay of sending heartbeat in case of server or network delay.
		// 12 second
		long hearbeatDelay = 12000;
		private long lastReceiveTime = System.currentTimeMillis();

		@Override
		public void run() {
			while (running) {
				if (System.currentTimeMillis() - lastReceiveTime > hearbeatDelay) {
					stop();
				} else {
					try {
						if (input.available() > 0) {
							messageHandler(input.readUTF());
							lastReceiveTime = System.currentTimeMillis();

						} else {
							Thread.sleep(checkDelay);
						}

					} catch (Exception e) {
						e.printStackTrace();
						stop();
					}
				}
			}
		}
	}

	//handle message received from the server.
	private void messageHandler(String message) {
		String msg = AES.Decrypt(message);
		java.lang.reflect.Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
		Gson gson = new Gson();
		Map<String,Object> data = gson.fromJson(msg, mapType);
		System.out.println("received msg from server: "+socket.getInetAddress()+" port:"+socket.getPort() +" msg: " + data);
		switch (data.get("cmd").toString()) {
            case "heartbeat":
                break;
            case "check":
                String content = data.get("content").toString();
                if(content.equals("approve")){
                    this.controller.showWhiteBoardWindow();
                    String approveACKData = "{\"cmd\":\"checkACK\"}";
                    sendData(approveACKData);
                }else{
                    stop();
                    this.controller.rejectByServer();
                }
                break;
            case "addShape":
                String classType = data.get("classType").toString();
                String objectData = data.get("object").toString();
                Shape shape = JsonMessageUtil.GenerateShapeFromMessage(classType, objectData);
                controller.addShape(shape);
                break;
            case "clearCanvas":
                controller.clearCanvas();
                break;
            default:
                break;
		}
	}

}
