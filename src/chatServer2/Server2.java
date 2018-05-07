package chatServer2;

import java.net.*;
import java.util.ArrayList;
import java.io.*;


public class Server2 {
	
	private ArrayList<ClientThread> clientThreadList = new ArrayList<ClientThread>();
	private ServerSocket serverSocket;
	
	public Server2() {
		
		try {
			
			serverSocket = new ServerSocket(3000);
			System.out.println("Waiting for a Client to connect....");		
			
			while (true) {
				
				Socket client = serverSocket.accept();
				System.out.println(client.getPort() + " has connected.");
				//A new thread is created for each socket
				ClientThread newClient = new ClientThread(client);
				//The new thread is added to the clientThreadList list
				clientThreadList.add(newClient);
				//The new thread is started
				newClient.start();
				
			}			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void sendToClients(String message, int ID, String Name) {
		
		for (int i = 0; i < clientThreadList.size();i++) {
			
			ClientThread clientT = clientThreadList.get(i);
			
			if (clientT.isAlive()) {
				// To avoid echoing the message to the client who sent it
				if (clientT.getID() == ID) {
					continue;
				} 
				else {
					
					String messageToSend = "[" + Name + "]: " + message;
					clientT.printWriter.println(messageToSend);
					clientT.printWriter.flush();
	//	DEBUG			System.out.println(clientThreadList.size());
				}
			}
			else {
				clientThreadList.remove(i);
				continue;
			}
			
		}
	}
	
	//This needs to be an "inner class", because it needs access to the variables of the Server2 Class
	class ClientThread extends Thread {

		private int id;
		private String fullID;
		private String ID;
		private boolean run = true;
		private Socket socket;
		private InputStream inputStream;
		private OutputStream outputStream;
		private String message;
		private PrintWriter printWriter;
		private BufferedReader bufferedReader;
		
		
		//The socket that will be passed to the constructor will be the return object of ServerSocket.accept() in the Server2 class
		public ClientThread(Socket socket) {
			
			try {
				
				this.socket = socket;
				this.inputStream = socket.getInputStream();
				this.outputStream = socket.getOutputStream();
				this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				this.printWriter = new PrintWriter(outputStream, true);
				this.id = socket.getPort();
				
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public int getID() {
			return id;
		}
		
		public String getFullID() {
			return fullID;
		}
		
		public void run() {
			
			try {
				
				ID = bufferedReader.readLine();
				fullID = Integer.toString(getID()) + " - " + ID;
			
				while (run) { 
					
					message = bufferedReader.readLine();
							
					if (message == null) {
						stopThread();
					}
					else {
						sendToClients(message, getID(),getFullID());
					}
				} 
			} 
			catch (IOException e) {
				e.printStackTrace();
}	
		}
		
		public void stopThread() {
			
			try {
				run = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}

	}
}

/*  
	TODO Create a method to remove threads from thread list once the thread closes - needs work to solve the 1 message delay
	when reconnecting 
	TODO Create a method to prevent "echoing" - Done
	TODO Prompt the clients to input a username upon conneccting. Use the username as their unique ID
*/

