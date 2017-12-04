package assignment7;

import java.io.Serializable;

public class Message implements Serializable {
	/*
	 * type 0: message
	 * type 1: logoff request
	 * type 2: username change
	 * 
	 * 
	 */
	private int type;
	private String message;
	
	Message(String message, int type){
		this.type = type;
		this.message = message;
	}
	
	int getType() {
		return this.type;
	}
	
	String getMessage() {
		return this.message;
	}
	
	void setType(int type) {
		this.type = type;
	}
	
	void setMessage(String message) {
		this.message = message;
	}
}
