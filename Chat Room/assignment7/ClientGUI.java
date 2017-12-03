/* Chat Room ClientGUI.java
 * EE422C Project 7 submission by
 * Puya Rahmanian
 * per495
 * 
 * Slip days used: <0>
 * Fall 2017
 */

package assignment7;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

public class ClientGUI {

	static void getButton(int index) {
		Button b = null;
		TextField t = null;
		
		switch(index) {
		
		case 0: 
			Text text = new Text();
			text.setWrappingWidth(500);
			text.setText("CHITTER");
			text.setTextAlignment(TextAlignment.CENTER);
			text.setFill(Color.BROWN);
			text.setFont(Font.font(null, FontWeight.BOLD, 30));
			Client.grid.add(text, 0, 0, 5, 1);
	        break;
	        
		case 1: 
			TextFlow messages = new TextFlow();
			
			messages.setMinWidth(400);
			messages.setMinHeight(155);
			
			
			
			messages.setTextAlignment(TextAlignment.LEFT);
			messages.appendText("View\n");
			
			messages.setTextAlignment(TextAlignment.RIGHT);
			
			messages.appendText("\thello\n");
			
	        
	        
	       
	        
	        Client.grid.add(messages, 1, 1);
	        break;
	        
		case 2: 
			b = new Button();
	        b.setText("Quit");
	        b.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	                System.exit(0);
	            }
	        });
	        Client.grid.add(b, 0, 0);
	        break;	
		}
	}
	
	public static void paint() {
		Client.grid.getChildren().clear(); // clean up grid.
		
		
		

		
		for (int i = 0 ; i <= 1; i++) {
			getButton(i);
		}
		
	}
}
