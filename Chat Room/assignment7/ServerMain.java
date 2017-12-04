/* Chat Room ServerMain.java
 * EE422C Project 7 submission by
 * Puya Rahmanian
 * per495
 * 
 * Slip days used: <0>
 * Fall 2017
 */

package assignment7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

	private ArrayList<PrintWriter> clientOutputStreams;
	private ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
	private static int IDIndex;
	
	class ClientHandler extends Thread{
		ObjectInputStream Input;
		ObjectOutputStream Output;
		Socket socket;
		int ID;
		String user;
		Message message;
		
		public ClientHandler(Socket clientSocket) {
			IDIndex++;
			this.ID = IDIndex;
			this.socket = clientSocket;
			try{
				Output = new ObjectOutputStream(socket.getOutputStream());          
				Input  = new ObjectInputStream(socket.getInputStream());
				// read the username
				Message messageUser = (Message) Input.readObject();
				this.user = messageUser.getMessage();

			}
			catch (Exception e) {}

		}
		public void run() {
			boolean cont = true;
			while(cont) {
				try {
					this.message = (Message) Input.readObject();
				}catch(ClassNotFoundException e) {
					break;
				} catch (IOException e) {
					break;
				}
					
				switch(this.message.getType()) {
				case 0:
					showMessage(this.user + ": " + this.message.getMessage(), this);
					break;
				case 1:
					showMessage(this.user + " logged off...", this);
					System.out.println("Username set to :" + this.user);
					cont = false;
					break;
				case 2:
					this.user = this.message.getMessage();
					System.out.println("Username set to :" + this.user);
					showMessage("Username set to :" + this.user, this);
					break;
				}
			}
			remove(this.ID);
			try {
				this.Input.close();
				this.Output.close();
				this.socket.close();
			}catch(Exception e) {}
		}
		
			
	}
	
	
	synchronized void remove(int ID) {
		for(int i = 0; i < clientHandlers.size(); i++) {
			if(clientHandlers.get(i).ID == ID) {
				clientHandlers.remove(i);
				return;
			}
		}
	}
	
	private synchronized void showMessage(String message, ClientHandler client) {
		try {
			client.Output.writeBytes(message);;
		} catch (IOException e) {
			System.out.println("error sending message");
		}
	}
	
	public static void main(String[] args) {
		ServerMain server = new ServerMain();
		server.start();
	}
	
	public void start() {
		ServerSocket serverSock = null;

		boolean cont = true;
		
		try {
			clientOutputStreams = new ArrayList<PrintWriter>();
			serverSock = new ServerSocket(4242);
			while (cont) {
				Socket clientSocket = serverSock.accept();
				if(!cont) break;
				
				ClientHandler t = new ClientHandler(clientSocket);
				clientHandlers.add(t);
				t.start();
				
				System.out.println("got a connection");
			}	
			serverSock.close();
			for(int i = 0; i < clientHandlers.size(); ++i) {
				ClientHandler ch = clientHandlers.get(i);
	
				ch.Input.close();
				ch.Output.close();
				ch.socket.close();
			}

		}
		catch(Exception e) {
			
			e.printStackTrace();
			
			try {
				serverSock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	
	

	
	
	private void notifyClients(String message) {
		for (PrintWriter writer : clientOutputStreams) {
			writer.println(message);
			writer.flush();
		}
	}
}
