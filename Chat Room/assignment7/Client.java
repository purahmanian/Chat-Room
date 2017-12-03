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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application{
	
	private BufferedReader reader;
	private PrintWriter writer;
	private JTextArea incoming;
	private JTextField outgoing;
	private ClientGUI gui;
	
	static GridPane grid = new GridPane();
	static Canvas canvas = new Canvas();
	static Stage canvasStage = new Stage();
	
	public static void main(String[] args){
		launch(args);
	}
	
	class IncomingReader implements Runnable {
		public void run() {
			boolean flag = false;
			while(true){
				String message;
				try {
					while ((message = reader.readLine()) != null) {
						flag = true;
						incoming.append(message + "\n");
					}
					if(flag) {
						flag = false;
						TextArea text = gui.getMessages();
						text.appendText("hello");
					}
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
			socket = new Socket("localhost", 4242);
			
			
			InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
			this.reader = new BufferedReader(streamReader);
			this.writer = new PrintWriter(socket.getOutputStream());
			Thread readerThread = new Thread(new IncomingReader());
			

			readerThread.start();
			
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

			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}


