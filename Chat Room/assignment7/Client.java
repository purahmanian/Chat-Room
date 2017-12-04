/* Chat Room Client.java
 * EE422C Project 7 submission by
 * Puya Rahmanian
 * per495
 * 
 * Slip days used: <0>
 * Fall 2017
 */

package assignment7;

import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application{
	
	private ObjectInputStream incoming;
	private ObjectOutputStream outgoing;
	private ClientGUI gui;
	private Message user = new Message("", 2);
	
	boolean loop = true;
	
	static GridPane grid = new GridPane();
	static Canvas canvas = new Canvas();
	static Stage canvasStage = new Stage();
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	class IncomingReader implements Runnable {
		public void run() {
			boolean flag = false;
			while(true){
				Message message;
				try {
					while ((message = (Message) incoming.readObject()) != null) {
						flag = true;
						String text = message.getMessage() + "\n";
						gui.messages.appendText(text);
					}
					if(flag) {
						flag = false;
						TextArea text = gui.getMessages();
					}
				} catch (IOException e) {
				} catch(ClassNotFoundException e1) {}
			}
		}
	}
	
	class OutgoingWriter implements Runnable{
		public void run() {
			while(true){
				try {
					while (!gui.send) {}
					gui.send = false;
					Message message = new Message("", 0);
					
					if(gui.setUsername && gui.send) {
						message.setMessage(gui.getUser());
						message.setType(2);
					}else {
						String text = gui.write.getText();
						if(text == "**LOGOFF**") {
							message.setType(1);
						}else {
							message.setType(0);
						}
						message.setMessage(text);
					}
					
					outgoing.writeObject(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Socket socket;
		try {
			grid.setGridLinesVisible(true);
			grid.setHgap(10);
			grid.setVgap(10);
			
			Scene scene = new Scene(grid, 530, 500);
			primaryStage.setScene(scene);
			
			primaryStage.show();

			grid.getColumnConstraints().add(new ColumnConstraints(50));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(50));
			
			
			gui = new ClientGUI();
			
			socket = new Socket("localhost", 4242);
			
			incoming = new ObjectInputStream(socket.getInputStream());
			outgoing = new ObjectOutputStream(socket.getOutputStream());
		
			Thread readerThread = new Thread(new IncomingReader());
			Thread writerThread = new Thread(new OutgoingWriter());
			
			writerThread.start();
			readerThread.start();
			
			
			


			outgoing.writeObject(this.user);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		} catch (IOException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}
		
	}
	


}


