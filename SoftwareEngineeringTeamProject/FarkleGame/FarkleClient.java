package FarkleGame;

import java.io.IOException;
import java.io.Serializable;

import ocsf.client.AbstractClient;

//Author: 			Shandon Probst
//Description:		

public class FarkleClient extends AbstractClient {

	// Objects
	private GUI_Client client_gui;

	// Controllers
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;

	// Variables
	private String username;
	private int player_number;
	private int score;

	// Constructor
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

	// Sets the GUI for... reasons
	public void setGUI(GUI_Client client_gui) {
		this.client_gui = client_gui;
	}

	// Prints to console when connection is established
	public void connectionEstablished() {
		System.out.println("Connected successfully");
	}

	// Prints to console when connection is closed
	public void connectionClosed() {
		System.out.println("Connection closed");
	}

	// Sets the current player's username
	public void setUsername(String username) {
		this.username = username;
	}

	// Gets the current player's username
	public String getUsername() {
		return this.username;
	}

	// Sets the current player's score
	public void setScore(int score) {
		this.score = score;
	}

	// Gets the current player's score
	public int getScore() {
		return this.score;
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {

		// Checks if argument being sent is a string
		if (arg0 instanceof String) {
			String message = (String) arg0;

			// Tells controller login was successful
			if (message.startsWith("LoginSuccessful_")) {
				this.setUsername(message.substring(16));
				loginControl.loginSuccess();

			}
			// Tells controller account creation was successful
			else if (message.startsWith("CreateAccountSuccessful_")) {
				this.setUsername(message.substring(24));
				loginControl.loginSuccess();
			}

			// Tells the client to close the connection with the server
			else if (message.startsWith("Disconnect")) {
				try {
					this.closeConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Tells the client the game is starting
			else if (message.startsWith("StartGame_")) {
				this.player_number = Integer.parseInt(message.substring(10));
				System.out.println("I am player #" + player_number);
			}

			else if (message.startsWith("EndGame_")) {
				// int winner_number = Integer.parseInt(message.substring(8));
				// System.out.println("Player #" + winner_number + " won the game.");

				client_gui.disableControl();
			}

			// Tells the client whose turn it is
			else if (message.startsWith("PlayerTurn_")) {
				int player_turn = Integer.parseInt(message.substring(11));

				// Enables control for the current player
				if (player_turn == player_number) {
					client_gui.play(player_number);
				}

				// Disables control the other player
				else {
					client_gui.spectate(player_number);
				}
			}
		}

		else if (arg0 instanceof Error) {
			// Typecast argo0 as Error
			Error error = (Error) arg0;

			// Show login error using login controller
			if (error.getType().equals("Login"))
				loginControl.displayError(error.getMessage());

			// Show createAccount error using createAccount controller
			else if (error.getType().equals("CreateAccount"))
				createAccountControl.displayError(error.getMessage());
		}
	}

	// Main driver
	public static void main(String[] args) {

		// Creation of Farkle client object
		FarkleClient client = new FarkleClient();

		// Client establishes connection with localhost:8300
		client.setHost("localhost");
		client.setPort(8300);

		// Attempts connect to the server using host and port info
		try {
			client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Creation of Farkle GUI for client
		GUI_Client client_gui = new GUI_Client(client);

		// Tells the client GUI to start the login process
		// The rest of the processes from here are handled by other classes/methods
		client_gui.login(client_gui);
	}
}
