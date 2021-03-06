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
	TextArea messages;
	TextArea write;
	Button button;
	boolean send = false;
	boolean setUsername = false;
	String usernameText = "";
	
	ClientGUI(){
		this.messages = null;
		this.write = null;
		this.button = null;
		
		findUser();

	}
	
	public String getUser() {
		return this.usernameText;
	}
	
	public TextArea getMessages() {
		return this.messages;
	}
	
	public void findUser() {
		Client.grid.getChildren().clear();
		
		
		
		TextArea username = new TextArea();
		username.setMinWidth(320);
		username.setMinHeight(20);
		username.setMaxHeight(100);
		username.setWrapText(true);
		Client.grid.add(username, 1, 2);

		
        Button button = new Button();
        button.setText("Send");
        button.setMinHeight(100);
        button.setMinWidth(100);
        button.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
                send = true;
                setUsername = true;
                usernameText = username.getText();
                paint();
            }
        });
        Client.grid.add(button, 4, 2);
	}
	
	
	public void paint() {
		Client.grid.getChildren().clear(); // clean up grid.
	
		Text text = new Text();
		text.setWrappingWidth(500);
		text.setText("CHITTER");
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.BROWN);
		text.setFont(Font.font(null, FontWeight.BOLD, 30));
		Client.grid.add(text, 0, 0, 5, 1);
        
	        
		this.messages = new TextArea();
		this.messages.setMinWidth(430);
		this.messages.setMinHeight(300);
		this.messages.setWrapText(true);
		this.messages.setEditable(false);	
		Client.grid.add(this.messages, 1, 1);
		
		this.write = new TextArea();
		this.write.setMinWidth(320);
		this.write.setMinHeight(20);
		this.write.setMaxHeight(100);
		this.write.setWrapText(true);
		Client.grid.add(this.write, 1, 2);

        this.button = new Button();
        this.button.setText("Send");
        this.button.setMinHeight(100);
        this.button.setMinWidth(100);
        this.button.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
                send = true;
                write.setText("");
            }
        });
        Client.grid.add(this.button, 4, 2);
        

        

        /*case 2: 
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
		}*/
        
	}
}
