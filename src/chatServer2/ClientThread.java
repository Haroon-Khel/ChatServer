/*package chatServer2;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

/*Constructor should be fed a socket as a parameter, and then establish input/output streams form the socket.
 * The Run method should keep checking the server for any received messages
 

public class ClientThread extends Thread {
	
	ArrayList<ClientThread> list;
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String message;
	
	//The socket that will be passed to the constructor will be the return of ServerSocket.accept() in the Server2 class
	public ClientThread(Socket socket, ArrayList<ClientThread> list) {
		
		try {
			
			this.socket = socket;
			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();
		}
		
		catch (Exception e) {
			
		}
		
	}
	
	public void run() {
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		while (true) {
			try {
				
				message = bufferedReader.readLine();
				System.out.println(message);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
*/