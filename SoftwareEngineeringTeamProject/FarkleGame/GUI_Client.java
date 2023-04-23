package FarkleGame;

import java.awt.event.*;
import java.io.IOException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

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

	// JToggleButtons
	private ArrayList <JToggleButton> dice_buttons;

	// JTextAreas
	private JTextArea Player1ScoreNameArea;
	private JTextArea Player2ScoreNameArea;
	
	// Variables
	private ArrayList <Boolean> dice_selected;

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

	//
	public void login(GUI_Client gui) {
		LoginFrame = new JFrame();

		// Set the title and default close operation.
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

		ImageIcon game_icon = new ImageIcon("/GUI/game_icon.jpg");
		FarkleGameFrame.setIconImage(game_icon.getImage());

		// Sets the background of the Farkle game frame
		JLabel FarkleGamePicture = new JLabel("");
		FarkleGamePicture.setIcon(new ImageIcon(GUI_Client.class.getResource("/GUI/Farkle_TeamProject Game GUI.jpg")));
		FarkleGameFrame.getContentPane().add(FarkleGamePicture);

		// Creation of buttons
		SetAsideButton = new JButton("SetAside");
		RollDiceButton = new JButton("RollDice");
		BankScoreButton = new JButton("BankScore");
		LogOutButton = new JButton("Logout");
		
		// Initialize selected buttons
		dice_selected = new ArrayList<Boolean>();
		
		// Creation of dice buttons
		dice_buttons = new ArrayList<JToggleButton>();
		for (int i = 0; i < 6; i++) {
			// Creation of individual button
			JToggleButton button = new JToggleButton("Die_" + i);
			
			// Add button to panel
			button.setBounds(i * 50, 100, 50, 50);
			FarkleGameFrame.getContentPane().add(button);
			
			// Adds action listeners to each button
			button.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ie) {
					int state = ie.getStateChange();
					
					if (state == ItemEvent.SELECTED) {
						System.out.println(button.getText() + " selected") ;;
					}
					
					else {
						System.out.println(button.getText() + " deselected");
					}
				}
			});
			
			// Adds button to button ArrayList
			dice_buttons.add(button);
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
		Player1ScoreNameArea.setBounds(10, 100, 64, 22);
		Player2ScoreNameArea.setBounds(10, 127, 64, 22);

		// Adding buttons to frame
		FarkleGameFrame.getContentPane().add(SetAsideButton);
		FarkleGameFrame.getContentPane().add(RollDiceButton);
		FarkleGameFrame.getContentPane().add(BankScoreButton);
		FarkleGameFrame.getContentPane().add(LogOutButton);

		// Addings text areas to frame
		FarkleGameFrame.getContentPane().add(Player1ScoreNameArea);
		FarkleGameFrame.getContentPane().add(Player2ScoreNameArea);

		// Button Listener to register when Set Aside Button is submitted
		BankScoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("BankScoreButton pressed");
				try {
					client.sendToServer(BankScoreButton);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Button Listener to register when Set Aside Button is submitted
		SetAsideButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("SetAsideButton pressed");
				
				
				
				try {
					client.sendToServer(SetAsideButton);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Button Listener to register when Set Aside Button is submitted
		RollDiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("RollDiceButton pressed");
				
				roll(6);
				
				try {
					client.sendToServer(RollDiceButton);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Button Listener to register when Set Aside Button is submitted
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("LogoutButton Pressed");
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
		enableControl();
	}

	// Lets the chosen player spectate and not interact with the game
	public void spectate(int player_number) {
		// Disables control for the user
		disableControl();
	}

	public void disableControl() {
		// Disabling buttons
		SetAsideButton.setEnabled(false);
		RollDiceButton.setEnabled(false);
		BankScoreButton.setEnabled(false);
		
		for (int i = 0; i < dice_buttons.size(); i++) {
			dice_buttons.get(i).setEnabled(false);
		}
	}

	public void enableControl() {
		// Enabling buttons
		SetAsideButton.setEnabled(true);
		RollDiceButton.setEnabled(true);
		BankScoreButton.setEnabled(true);
		
		for (int i = 0; i < dice_buttons.size(); i++) {
			dice_buttons.get(i).setEnabled(true);
		}
	}

	// Randomizes the dice for the client
	private void roll(int available_dice_count) {
		// Selected Die that are moved into "Set Aside" the number of dice that are set
		// aside should be subtracted from NumofDie
		 
		// Random number generator
		Random random = new Random();
		ArrayList<Integer> dice = new ArrayList<Integer>();

		if (available_dice_count >= 6) {
			for (int i = 0; i < available_dice_count; i++) {
				dice.add(1 + random.nextInt(7 - 1));
				System.out.println("Die #" + (i + 1) + "'s value: " + dice.get(i));
			}
		}
		
		// Area where the 1st Dice will be displayed
		//DiceAreaPic1.setIcon(new ImageIcon(GUI_Client.class.getResource("/GUI/1_dice.jpg")));

	}
}
