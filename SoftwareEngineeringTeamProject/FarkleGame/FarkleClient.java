package FarkleGame;

import java.awt.EventQueue;

import ocsf.client.AbstractClient;

//Author: 			Shandon Probst
//Description:		

public class FarkleClient extends AbstractClient{
	
	// Objects
	GUI_Client gui;
	
	// Controllers
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	
	// Variables
	private String username;
	private int player_number;
	private int score;
	
	public FarkleClient() {
	
		super("localhost", 8300);
	}
	
	// Sets loginControl
	public void setLoginControl(LoginControl loginControl) {
	    this.loginControl = loginControl;
	}
	
	// Sets createAccountControl
	public void setCreateAccountControl(CreateAccountControl createAccountControl) {
	    this.createAccountControl = createAccountControl;
	}
	
	public void connectionEstablished() {
		System.out.println("Connected successfully");
	}
	
	public void setUser(String username) {
		this.username = username;
	}
	
	public String getUser() {
		
		return this.username;
	}
	
	@Override
	protected void handleMessageFromServer(Object arg0) {

		// Checks if argument being sent is a string
	    if (arg0 instanceof String) {
	    	String message = (String)arg0;
	      
	    	// Tells controller login was successful
	    	if (message.equals("LoginSuccessful")) {
	    		loginControl.loginSuccess();
	    	}
	      
	    	// Tells controller account creation was successful
	    	else if (message.equals("CreateAccountSuccessful"))
	    		createAccountControl.createAccountSuccess();
	    }
	    
	    else if (arg0 instanceof Error) {
	    	// Typecast argo0 as Error
	    	Error error = (Error)arg0;
	      
	    	// Show login error using login controller
	    	if (error.getType().equals("Login"))
	    		loginControl.displayError(error.getMessage());
	      
	    	// Show createAccount error using createAccount controller
	    	else if (error.getType().equals("CreateAccount"))
	    		createAccountControl.displayError(error.getMessage());
	    }
	}
	
	// Main driver
	// Creates farkle client GUI
	public static void main(String[] args) {
		FarkleClient client = new FarkleClient();
		System.out.println("ENTERED MAIN");
		GUI_Client client_gui = new GUI_Client(client);
		System.out.println("Still in main before setting to visible");
		
		client_gui.login(client_gui);
	}
}