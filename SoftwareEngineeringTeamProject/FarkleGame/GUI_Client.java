package FarkleGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

//Author: 			Tyler Menzie
//Description: 		Farkle Game GUI

@SuppressWarnings("serial")
public class GUI_Client extends JFrame {

	// Client object
	private FarkleClient client;

	// JFrames and JPanels
	private JFrame LoginFrame;
	private JFrame FarkleGameFrame;

	// JButtons
	private JButton BankScoreButton;
	private JButton SetAsideButton;
	private JButton RollDiceButton;
	private JButton LogOutButton;

	private ArrayList<JButton> dice_buttons; 				// Stores the JToggleButtons associated with the dice
	private ArrayList<Boolean> dice_set_aside; 				// Stores the JButtons associated with the dice that are to be
															// set aside
	private ArrayList<Boolean> dice_selected; 				// Stores the boolean values associated with the dice. True if selected
	private ArrayList<Integer> dice_values;
	
	private Boolean farkled;
	
	// JTextAreas
	private JTextArea Player1ScoreNameArea;
	private JTextArea Player2ScoreNameArea;

	// Variables
	private int score;
	private int player_number;

	// Constructor
	public GUI_Client(FarkleClient client) {
		this.client = client;
		client.setGUI(this);
	}

	public JFrame getLoginFrame() {
		return this.LoginFrame;
	}

	public JFrame getFarkleGameFrame() {
		return this.FarkleGameFrame;
	}

	// Login method
	public void login(GUI_Client gui) {
		LoginFrame = new JFrame();

		// Set the title, default close operation, and resizable as false.
		LoginFrame.setTitle("Farkle Client Login");
		LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		// Create the Controllers next
		// Next, create the Controllers
		InitialControl ic = new InitialControl(container);
		LoginControl lc = new LoginControl(container, client, LoginFrame, gui);
		CreateAccountControl cac = new CreateAccountControl(container, client, LoginFrame, gui);

		// Set client info
		client.setLoginControl(lc);
		client.setCreateAccountControl(cac);

		// Create the four views. (need the controller to register with the Panels
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view3 = new CreateAccountPanel(cac);

		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");

		// Show the initial view in the card layout.
		cardLayout.show(container, "1");

		// Add the card layout container to the JFrame.
		LoginFrame.add(container, BorderLayout.CENTER);

		// Show the JFrame.
		LoginFrame.setSize(550, 350);
		LoginFrame.setVisible(true);
	}

	// Initialize the contents of the frame
	public void initialize(String username) {

		// Start of the Farkle Game Frame
		FarkleGameFrame = new JFrame();
		FarkleGameFrame.setTitle("Farkle Client - Playing as \"" + username + "\"");
		FarkleGameFrame.getContentPane().setLayout(null);
		FarkleGameFrame.setSize(700, 500);
		FarkleGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginFrame.setResizable(false);

		ImageIcon game_icon = new ImageIcon("/GUI/game_icon.jpg");
		FarkleGameFrame.setIconImage(game_icon.getImage());

		// Initialize score
		score = 0;

		// Creation of buttons
		SetAsideButton = new JButton("SetAside");
		RollDiceButton = new JButton("RollDice");
		BankScoreButton = new JButton("BankScore");
		LogOutButton = new JButton("Logout");

		// Initialize ArrayLists
		dice_selected = new ArrayList<Boolean>();
		dice_values = new ArrayList<Integer>();
		dice_set_aside = new ArrayList<Boolean>();
		
		// Creation of dice buttons
		dice_buttons = new ArrayList<JButton>();

		int bottom_pos = 90;	// temp position value
		
		// Creates all of the buttons and maps action listeners to them
		for (int i = 0; i < 6; i++) {
			// Creation of individual button
			JButton button = new JButton("");
			dice_buttons.add(button);
			
			// All dice are initialized as not selected
			dice_selected.add(false);
			dice_set_aside.add(false);

			// Add button to panel
			button.setBounds(bottom_pos += 65, 315, 53, 49);
			FarkleGameFrame.getContentPane().add(button);

			// Disables button and hides them
			button.setEnabled(false);
			button.setVisible(false);

			// Adds action listeners to each button
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// Checks if button is in dice_buttons. Moves over to set_aside_buttons if
					// selected
					
					for (int j = 0; j < dice_buttons.size(); j++) { 
						if (dice_selected.get(j).equals(false) && j == dice_buttons.indexOf(button)) {
							// Show button in new location
							button.setBounds((90 + ((dice_buttons.indexOf(button) + 1) * 65)), 255, 53, 49);
							
							// Set selected as true
							dice_selected.set(j, true);
							
							System.out.println("Selected Dice #" + j + " - value is: " + dice_values.get(j));
							
						}
						
						// Checks if button is in set_aside_buttons. Moves over to dice_buttons if
						// selected
						else if (dice_selected.get(j).equals(true) && j == dice_buttons.indexOf(button)) {
						    // Sets bounds
						    button.setBounds((90 + ((dice_buttons.indexOf(button) + 1) * 65)), 315, 53, 49);

						    // Set selected as false
						    dice_selected.set(j, false);
						}
					}
				}
			});
		}

		// Creation of JTextFields
		Player1ScoreNameArea = new JTextArea();
		Player2ScoreNameArea = new JTextArea();

		// Disabling buttons
		SetAsideButton.setEnabled(false);
		RollDiceButton.setEnabled(false);
		BankScoreButton.setEnabled(false);
		LogOutButton.setEnabled(false);

		// Disabling text areas
		Player1ScoreNameArea.setEditable(false);
		Player2ScoreNameArea.setEditable(false);

		// Setting JButton bounds
		SetAsideButton.setBounds(120, 385, 89, 36);
		RollDiceButton.setBounds(309, 385, 97, 36);
		BankScoreButton.setBounds(471, 385, 89, 36);
		LogOutButton.setBounds(571, 23, 89, 31);

		// Setting JTextArea bounds
		Player1ScoreNameArea.setBounds(575, 100, 90, 20);
		Player2ScoreNameArea.setBounds(575, 125, 90, 20);

		// Adding buttons to frame
		FarkleGameFrame.getContentPane().add(SetAsideButton);
		FarkleGameFrame.getContentPane().add(RollDiceButton);
		FarkleGameFrame.getContentPane().add(BankScoreButton);
		FarkleGameFrame.getContentPane().add(LogOutButton);

		// Addings text areas to frame
		FarkleGameFrame.getContentPane().add(Player1ScoreNameArea);
		FarkleGameFrame.getContentPane().add(Player2ScoreNameArea);

		// Button Listener to register when bank score button is submitted
		BankScoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.sendToServer("BankScore_" + player_number);
					client.setScore(client.getScore() + score);
					resetBoard();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		SetAsideButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Get list of selected dice values
		        ArrayList<Integer> selected_dice = new ArrayList<Integer>();
		        for (int i = 0; i < dice_buttons.size(); i++) {
		            if (dice_selected.get(i).equals(true)) {
		                selected_dice.add(dice_values.get(i));
		            }
		        }
		        
		        // Verify and score the selected dice
		        int round_score = getScore(selected_dice);
		        score += round_score;
		        System.out.println("Round score: " + round_score);
		        System.out.println("Total score: " + score);
		        
		        // Clear the list of selected dice
		        selected_dice.clear();
		        
		        // Loop through and disable dice buttons that were selected
		        for (int i = 0; i < dice_buttons.size(); i++) {
		            if (dice_selected.get(i).equals(true)) {
		                dice_selected.set(i, false);
		                dice_set_aside.set(i, true);
		                dice_buttons.get(i).setEnabled(false);
		            }
		        }
		        
		        // Enable the bank score button
		        BankScoreButton.setEnabled(true);
		    } 
		});


		// Button Listener to register when roll dice button is submitted
		RollDiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < dice_buttons.size(); i++) {
					if (dice_set_aside.get(i) == false && dice_selected.get(i) == true) {
						dice_buttons.get(i).setBounds((90 + ((dice_buttons.indexOf(dice_buttons.get(i)) + 1) * 65)), 315, 53, 49);
						dice_selected.set(i, false);
					}
				}
				
				roll();
			}
		});

		// Button Listener to register when Set Aside Button is submitted
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.sendToServer(LogOutButton);
					FarkleGameFrame.setVisible(false);
					FarkleGameFrame.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Sets the frame as visible
		LogOutButton.setEnabled(true);
		FarkleGameFrame.setVisible(true);
	}

	// Lets the chosen player play and interact with the game
	public void play(int player_number) {
		// Enables control for the user
		farkled = false;
		this.player_number = player_number;
		RollDiceButton.setEnabled(true);
	}

	// Lets the chosen player spectate and not interact with the game
	public void spectate(int player_number) {
		// Disables control for the user
		disableControl();
	}

	// Sets most buttons as disabled and invisible
	public void disableControl() {
		// Disabling buttons
		SetAsideButton.setEnabled(false);
		RollDiceButton.setEnabled(false);
		BankScoreButton.setEnabled(false);

		for (int i = 0; i < dice_buttons.size(); i++) {
			dice_buttons.get(i).setEnabled(false);
		}
	}

	// Sets most buttons as enabled and visible
	public void enableControl() {
		// Enabling buttons
		SetAsideButton.setEnabled(true);

		for (int i = 0; i < dice_buttons.size(); i++) {
			dice_buttons.get(i).setEnabled(true);
			dice_buttons.get(i).setVisible(true);
		}
	}

	// Resets components on the board
	private void resetBoard() {
		for (int i = 0; i < dice_buttons.size(); i++) {
			
			// Moves buttons back to where they belong :)
			dice_buttons.get(i).setBounds((90 + (i + 1) * 65), 315, 53, 49);
			
			dice_selected.set(i, false);
			dice_set_aside.set(i, false);
			
			// Sets the buttons as invisible
			dice_buttons.get(i).setVisible(false);
		}
		
		dice_values.clear();
	}

	// Randomizes the dice for the client
	private void roll () {
		// Selected Die that are moved into "Set Aside" the number of dice that are set
		// aside should be subtracted from NumofDie

		// Random number generator
		Random random = new Random();

		// Checks if the current dice count1 is greater than or equal to 6
		if (dice_values.isEmpty()) {
			for (int i = 0; i < 6; i++) {
				// Sets the dice button's text to its assigned number
				int random_num = 1 + random.nextInt(7 - 1);
				dice_values.add(random_num);
				dice_buttons.get(i).setIcon(new ImageIcon(GUI_Client.class.getResource("/GUI/" + (random_num) + "_dice.jpg")));
				enableControl();
			}
		}
		else {
			for (int i = 0; i < 6; i++) {
				if (dice_set_aside.get(i) == false) {
					int random_num = 1 + random.nextInt(7 - 1);
					dice_values.set(i, random_num);
					dice_buttons.get(i).setIcon(new ImageIcon(GUI_Client.class.getResource("/GUI/" + (random_num) + "_dice.jpg")));
				}
				else {
					dice_buttons.get(i).setEnabled(false);
				}
			}			
		}
	}

	// Gets the score of the currently selected dice
	public static int getScore(ArrayList<Integer> dice) {
	    int score = 0;
	    HashMap<Integer, Integer> countMap = new HashMap<>();
	    for (int die : dice) {
	        countMap.put(die, countMap.getOrDefault(die, 0) + 1);
	    }
	    for (int die : countMap.keySet()) {
	        int count = countMap.get(die);

	        
	        if (die == 1) {
	            score += count * 100;
	        } else if (die == 5) {
	            score += count * 50;
	        }
	    }
	    return score;
	}
}
