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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application{
	
	private static BufferedReader reader;
	private static PrintWriter writer;
	private static JTextArea incoming;
	private static JTextField outgoing;
	
	static GridPane grid = new GridPane();
	static Canvas canvas = new Canvas();
	static Stage canvasStage = new Stage();
	
	public static void main(String[] args){
		launch(args);
	}
	
	static class IncomingReader implements Runnable {
		public void run() {
			
			while(true){
				String message;
				try {
					while ((message = reader.readLine()) != null) {
						incoming.append(message + "\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	static class OutgoingWriter implements Runnable {
		public void run() {
			while(true){
				String message;
				try {
					while ((message = reader.readLine()) != null) {
						incoming.append(message + "\n");
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
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(socket.getOutputStream());
			Thread readerThread = new Thread(new IncomingReader());
			

			readerThread.start();
			
			grid.setGridLinesVisible(true);
			grid.setHgap(10);
			grid.setVgap(10);
			
			Scene scene = new Scene(grid, 530, 500);
			primaryStage.setScene(scene);
			
			primaryStage.show();
			//canvas.setWidth(250);
			//canvas.setHeight(250);
			grid.getColumnConstraints().add(new ColumnConstraints(50));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(100));
			grid.getColumnConstraints().add(new ColumnConstraints(50));
			ClientGUI.paint();

			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


