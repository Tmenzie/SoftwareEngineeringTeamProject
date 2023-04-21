package FarkleGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

//Author: 			Shandon Probst
//Description:		

public class LoginControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private FarkleClient chatClient;
  private JFrame jframe;
  private boolean playing;
  public boolean StartGame;
  
  // Constructor for the login controller.
  public LoginControl(JPanel container, FarkleClient chatClient, JFrame jframe, boolean StartGame)
  {
    this.container = container;
    this.chatClient = chatClient;
    this.jframe = jframe;
    this.StartGame = StartGame;
   
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Cancel")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }

    // The Submit button submits the login information to the server.
    else if (command == "Submit")
    {
      // Get the username and password the user entered.
      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
      LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());
      
      // Check the validity of the information locally first.
      if (data.getUsername().equals("") || data.getPassword().equals(""))
      {
        displayError("You must enter a username and password.");
        return;
      }
     
      // Submit the login information to the server.
      try {
    	  chatClient.sendToServer(data);
	  } 
      catch (IOException e) {
    	  // TODO Auto-generated catch block
    	  e.printStackTrace();
      } 
    }
   
  }
public boolean playing() {
	System.out.println("Entered PLaying");
	StartGame = playing;
	System.out.println(StartGame + " In LOGIN CONTROL");
	return StartGame;
}
  // After the login is successful, set the User object and display the contacts screen. - this method would be invoked by 
  //the ChatClient
  public void loginSuccess() {
	  System.out.println("Entered LoginSuccess");
		jframe.setVisible(false);
		//StartGame = true;
		//playing();
		jframe.dispose();
		GUI_Client game = new GUI_Client();
		this.StartGame = game.StartGame();
		System.out.println(StartGame + " In LOGIN Success");
		if(StartGame == true)
		{
			GUI_Client StartGame= new GUI_Client();
			StartGame.initialize();
		}
		System.exit(0);
	
		
		//return StartGame;
  }

  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
  public void displayError(String error)
  {
    LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
    loginPanel.setError(error);
    
  }
}
