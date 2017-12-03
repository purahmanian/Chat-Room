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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

	private static ArrayList<PrintWriter> clientOutputStreams;
	
	static class ClientHandler implements Runnable{
		
		private BufferedReader reader;
		public ClientHandler(Socket clientSocket) {
			Socket sock = clientSocket;
			try {
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					notifyClients(message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ServerSocket serverSock = null;

		try {
			clientOutputStreams = new ArrayList<PrintWriter>();
			serverSock = new ServerSocket(4242);
			while (true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				clientOutputStreams.add(writer);
				System.out.println("got a connection");
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

	
	

	
	
	private static void notifyClients(String message) {
		for (PrintWriter writer : clientOutputStreams) {
			writer.println(message);
			writer.flush();
		}
	}
}
