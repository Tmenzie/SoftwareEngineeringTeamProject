package FarkleGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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
		                System.out.println("Currently selected: Die #" + i);
		            }
		        }
		        
		        /*
		        // Get list of all valid combinations
		        ArrayList<ArrayList<Integer>> valid_combinations = checkDice(dice_values, dice_set_aside);
		        
		        // Create a list to store all the combinations that match the selected dice
		        ArrayList<ArrayList<Integer>> matching_combinations = new ArrayList<>();
		        
		        // Check if selected dice match any valid combinations
		        for (ArrayList<Integer> combination : valid_combinations) {
		            boolean match = true;
		            for (int die_value : combination) {
		                if (!selected_dice.contains(die_value)) {
		                    match = false;
		                    break;
		                }
		            }
		            if (match) {
		                // Add the matching combination to the list of matching combinations
		                matching_combinations.add(combination);
		            }
		        }
		        
		        // Add score for each matching combination and set corresponding dice_set_aside elements to true
		        for (ArrayList<Integer> combination : matching_combinations) {
		            score += scoreCombination(combination);
		            for (int i = 0; i < dice_values.size(); i++) {
		                if (selected_dice.contains(dice_values.get(i))) {
		                    dice_set_aside.set(i, true);
		                }
		            }
		        }
		        */
		        
		        // Loop through and disable dice buttons that were selected
		        for (int i = 0; i < dice_buttons.size(); i++) {
		            if (dice_selected.get(i).equals(true)) {
		                dice_buttons.get(i).setEnabled(false);
		                dice_set_aside.set(i, true);
		            }
		        }
		        
		        System.out.println("Current score: " + score);
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

	public ArrayList<ArrayList<Integer>> checkDice(ArrayList<Integer> dice_values) {
		
		// Creation of valid ArrayLists
	    ArrayList<ArrayList<Integer>> valid_combinations = new ArrayList<>();
	    ArrayList<ArrayList<Integer>> valid_triples1 = new ArrayList<>();
	    ArrayList<ArrayList<Integer>> valid_triples2 = new ArrayList<>();
	    
	    // Variables to count1 num of ones and fives
	    int num_ones = 0;
	    int num_fives = 0;
	    
	    // If all dice are set aside, there can be no valid combinations
	    if (dice_set_aside.stream().allMatch(b -> b == true)) {
	        return valid_combinations;
	    }
	    
	    // If all dice are unique, then it is a straight
	    HashSet<Integer> unique_values = new HashSet<>(dice_values);
	    if (unique_values.size() == 6) {
	        ArrayList<Integer> combination = new ArrayList<>(unique_values);
	        valid_combinations.add(combination);
	    }
	    
	    // Loop through all possible dice values
	    for (int i = 1; i <= 6; i++) {
	        int count1 = 0;
	        int count2 = 0;
	        
	        //ArrayLists to hold combinations
	        ArrayList<Integer> combination1 = new ArrayList<>();
	        ArrayList<Integer> combination2 = new ArrayList<>();
	        
	        // Counts how many dice have the current die value
	        for (int j = 0; j < dice_values.size(); j++) {
	            if (dice_values.get(j) == i && !dice_set_aside.get(j)) { // only consider dice that are still in play
	            	if (count1 < 3) {
	                    count1++;
	                    combination1.add(i);
	                } else {
	                    count2++;
	                    combination2.add(i);
	                }
	            }
	        }
	        
	        // If there are exactly six dice with the current die value
	        if (count1 == 6) {
	            valid_combinations.add(combination1);
	        }
	        
	        // If there are exactly five dice with the current die value
	        if (count1 == 5) {
	            valid_combinations.add(combination1);
	        }
	        
	        // If there are exactly three dice with the current die value
	        if (count1 == 3) {
	            valid_triples1.add(combination1);
	        }

	        // If there are exactly three dice with the second die value
	        if (count2 == 3) {
	            valid_triples2.add(combination2);
	        }
	        
	        // If there are exactly four dice with the current die value
	        if (count1 >= 4) {
	            valid_combinations.add(combination1);
	        }
	        
	        // Count the number of ones in the remaining dice
	        if (i == 1) {
	            num_ones = count1;
	        }
	        
	        // Count the number of fives in the remaining dice
	        else if (i == 5) {
	            num_fives = count1;
	        }
	    }
	    
	    // Adds individual instances of dice rolls one and five to the valid combinations
	    if (num_ones > 0) {
	        ArrayList<Integer> ones_combination = new ArrayList<>();
	        for (int i = 0; i < num_ones; i++) {
	            ones_combination.add(1);
	        }
	        valid_combinations.add(ones_combination);
	    }
	    
	    if (num_fives > 0) {
	        ArrayList<Integer> fives_combination = new ArrayList<>();
	        for (int i = 0; i < num_fives; i++) {
	            fives_combination.add(5);
	        }
	        valid_combinations.add(fives_combination);
	    }
	    
	    // Tests for four-of-a-kind plus a pair
	    for (int i = 1; i <= 6; i++) {
	        int count1 = 0;
	        ArrayList<Integer> combination1 = new ArrayList<>();
	        for (int j = 0; j < dice_values.size(); j++) {
	            if (dice_values.get(j) == i && !dice_set_aside.get(j)) {
	                count1++;
	                combination1.add(i);
	            }
	        }
	        if (count1 == 4) {
	            for (int k = 1; k <= 6; k++) {
	                if (k != i) {
	                    int pair_count = 0;
	                    ArrayList<Integer> pair_combination = new ArrayList<>();
	                    for (int j = 0; j < dice_values.size(); j++) {
	                        if (dice_values.get(j) == k && !dice_set_aside.get(j)) {
	                            pair_count++;
	                            pair_combination.add(k);
	                        }
	                    }
	                    if (pair_count == 2) {
	                        combination1.addAll(pair_combination);
	                        valid_combinations.add(combination1);
	                        break;
	                    }
	                }
	            }
	        }
	    }
	    
	    // Combines the the two sets into a single combination
	    valid_combinations.addAll(valid_triples1);
	    valid_combinations.addAll(valid_triples2);
	    
	    return valid_combinations;
	}
	
	public int scoreCombination(ArrayList<Integer> combination) {
	    int score = 0;

	    if (combination.size() == 0) {
	        return score;
	    }

	    Collections.sort(combination);

	    // Check for a straight
	    if (combination.size() == 6 && combination.get(0) == 1 && combination.get(5) == 6 && new HashSet<>(combination).size() == 6) {
	        score = 1500;
	    }
	    // Check for three pairs
	    else if (combination.size() == 6 && new HashSet<>(combination).size() == 3) {
	        boolean isThreePairs = true;
	        for (int i = 0; i < 6; i += 2) {
	            if (combination.get(i) != combination.get(i + 1)) {
	                isThreePairs = false;
	                break;
	            }
	        }
	        if (isThreePairs) {
	            score = 1500;
	        }
	    }
	    // Check for two triplets
	    else if (combination.size() == 6 && new HashSet<>(combination).size() == 2) {
	        boolean isTwoTriplets = true;
	        for (int i = 0; i < 6; i += 3) {
	            if (combination.get(i) != combination.get(i + 1) || combination.get(i + 1) != combination.get(i + 2)) {
	                isTwoTriplets = false;
	                break;
	            }
	        }
	        if (isTwoTriplets) {
	            score = 2500;
	        }
	    }
	    // Check for six-of-a-kind
	    else if (combination.size() == 6 && new HashSet<>(combination).size() == 1) {
	        score = 3000;
	    }
	    // Check for five-of-a-kind
	    else if (combination.size() == 5 && new HashSet<>(combination).size() == 1) {
	        score = 2000;
	    }
	    // Check for four-of-a-kind plus a pair
	    else if (combination.size() == 6 && new HashSet<>(combination).size() == 2) {
	        int count1 = Collections.frequency(combination, combination.get(0));
	        int count2 = Collections.frequency(combination, combination.get(3));
	        if ((count1 == 4 && count2 == 2) || (count1 == 2 && count2 == 4)) {
	            score = 1500;
	        }
	    }
	    // Check for four-of-a-kind
	    else if (combination.size() >= 4) {
	        int count = Collections.frequency(combination, combination.get(0));
	        if (count >= 4) {
	            score = 1000;
	        }
	    }
	    // Check for three-of-a-kind
	    else if (combination.size() == 3) {
	        int count = Collections.frequency(combination, combination.get(0));
	        if (count == 3) {
	            score = combination.get(0) == 1 ? 300 : combination.get(0) * 100;
	        }
	    }
	    // Check for single "1" or "5"
	    else if (combination.size() == 1) {
	        int num = combination.get(0);
	        if (num == 1) {
	            score = 100;
	        } else if (num == 5) {
	            score = 50;
	        }
	    }

	    return score;
	}
}
