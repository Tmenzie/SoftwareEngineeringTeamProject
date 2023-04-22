package FarkleGame;

// Author: Tyler Menzie
// Objective: Farkle Game GUI


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

public class GUI_Client extends JFrame{
	
	// Client object
	private FarkleClient client;
	
	// JFrames and JPanels
	private JFrame FarkleGameFrame;
	
	// Booleans for die
	private boolean Dice1isSelected;
	private boolean Dice2isSelected;
	private boolean Dice3isSelected;
	private boolean Dice4isSelected;
	private boolean Dice5isSelected;
	private boolean Dice6isSelected;

	// Constructor
	public GUI_Client(FarkleClient client) {
		this.client = client;
	}
	
	public void login(GUI_Client gui) {
		System.out.println("Entered login");
		client.setHost("localhost");
		client.setPort(8300);
		
		try {
			client.openConnection();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // Set the title and default close operation.
	    this.setTitle("Farkle Client Login");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	    // Create the card layout container.
	    CardLayout cardLayout = new CardLayout();
	    JPanel container = new JPanel(cardLayout);
	    
	    //Create the Controllers next
	    //Next, create the Controllers
	    InitialControl ic = new InitialControl(container); 
	    LoginControl lc = new LoginControl(container, client, this, gui);
	    CreateAccountControl cac = new CreateAccountControl(container, client, this, gui);
	    
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
	    this.add(container, BorderLayout.CENTER);

	    // Show the JFrame.
	    this.setSize(550, 350);
	    this.setVisible(true);
	}
	
	// Initialize the contents of the frame.
	public void initialize() {
		
		// Start of the Farkle Game Frame
		FarkleGameFrame = new JFrame();
		FarkleGameFrame.setTitle("Farkle Client");
		FarkleGameFrame.setBounds(100, 100, 686, 471);
		FarkleGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FarkleGameFrame.getContentPane().setLayout(null);
		
		ImageIcon img = new ImageIcon("/GUI/DiceIcon.png");
		FarkleGameFrame.setIconImage(img.getImage());
	
		// Hard Coded Random Number Generator
		// Stores 6 Random Numbers into an array of int
		// Selected Die that are moved into "Set Aside" the  number of dice that are set aside should be subtracted from NumofDie 
		int NumofDie = 6;
		Random random = new Random();
		int[] arr = new int[NumofDie];
		
		for(int i = 0; i <arr.length; i++)
		{
			arr[i] = 1 + random.nextInt(7 - 1);
		
			System.out.println(arr[i]);
		}
		
		// Sets Each random number to a Dice, then Allocates that number into the JPG of the Image File
		String Dice1 = "/GUI/" + arr[0] + "_dice.jpg";
		String Dice2 = "/GUI/" + arr[1] + "_dice.jpg";
		String Dice3 = "/GUI/" + arr[2] + "_dice.jpg";
		String Dice4 = "/GUI/" + arr[3] + "_dice.jpg";
		String Dice5 = "/GUI/" + arr[4] + "_dice.jpg";
		String Dice6 = "/GUI/" + arr[5] + "_dice.jpg";
		
		// Area where the 6th Dice will be displayed
		JLabel DiceAreaPic6 = new JLabel("");
		DiceAreaPic6.setIcon(new ImageIcon(GUI_Client.class.getResource(Dice6)));
		DiceAreaPic6.setBounds(501, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic6);
		
		// Red Indicator that shows Dice 6 is Selected to be Set Aside
		JPanel Dice6SelectionIndicator = new JPanel();
		Dice6SelectionIndicator.setBackground(new Color(255, 0, 0));
		Dice6SelectionIndicator.setBounds(497, 305, 62, 58);
		FarkleGameFrame.getContentPane().add(Dice6SelectionIndicator);
		Dice6SelectionIndicator.setVisible(false);
		
		// Listener for mouse click on Dice area 6
		DiceAreaPic6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Dice6isSelected) {
                	//DiceAreaPic1.setForeground(Color.BLACK);
                	Dice6isSelected = false;
                	Dice6SelectionIndicator.setVisible(Dice6isSelected);
                    System.out.println("Dice 6 Un-Selected");
                } else {
                   // label.setForeground(Color.RED);
                	Dice6isSelected = true;
                	Dice6SelectionIndicator.setVisible(Dice6isSelected);
                    System.out.println("Dice 6 Selected");
                }
            }
        });
		
		// Area where the 5th Dice will be displayed
		JLabel DiceAreaPic5 = new JLabel("");		
		DiceAreaPic5.setIcon(new ImageIcon(GUI_Client.class.getResource(Dice5)));
		DiceAreaPic5.setBounds(424, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic5);
		
		// Red Indicator that shows Dice 5 is Selected to be Set Aside
		JPanel Dice5SelectionIndicator = new JPanel();
		Dice5SelectionIndicator.setBackground(Color.RED);
		Dice5SelectionIndicator.setBounds(420, 305, 62, 58);
		FarkleGameFrame.getContentPane().add(Dice5SelectionIndicator);
		Dice5SelectionIndicator.setVisible(false);
		
		// Listener for mouse click on Dice area 5
		DiceAreaPic5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Dice5isSelected) {
                	//DiceAreaPic1.setForeground(Color.BLACK);
                	Dice5isSelected = false;
                	Dice5SelectionIndicator.setVisible(Dice5isSelected);
                    System.out.println("Dice 5 Un-Selected");
                } else {
                   // label.setForeground(Color.RED);
                	Dice5isSelected = true;
                	Dice5SelectionIndicator.setVisible(Dice5isSelected);
                    System.out.println("Dice 5 Selected");
                }
            }
        });
		// Area where the 4th Dice will be displayed
		JLabel DiceAreaPic4 = new JLabel("");		
		DiceAreaPic4.setIcon(new ImageIcon(GUI_Client.class.getResource(Dice4)));
		DiceAreaPic4.setBounds(341, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic4);
		
		// Red Indicator that shows Dice 4 is Selected to be Set Aside
		JPanel Dice4SelectionIndicator = new JPanel();
		Dice4SelectionIndicator.setBackground(Color.RED);
		Dice4SelectionIndicator.setBounds(337, 305, 62, 58);
		FarkleGameFrame.getContentPane().add(Dice4SelectionIndicator);
		Dice4SelectionIndicator.setVisible(false);
		
		// Listener for mouse click on Dice area 4
		DiceAreaPic4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Dice4isSelected) {
                	//DiceAreaPic1.setForeground(Color.BLACK);
                	Dice4isSelected = false;
                	Dice4SelectionIndicator.setVisible(Dice4isSelected);
                    System.out.println("Dice 4 Un-Selected");
                } else {
                   // label.setForeground(Color.RED);
                	Dice4isSelected = true;
                	Dice4SelectionIndicator.setVisible(Dice4isSelected);
                    System.out.println("Dice 4 Selected");
                }
            }
        });
		
		// Area where the 3rd Dice will be displayed
		JLabel DiceAreaPic3 = new JLabel("");
		DiceAreaPic3.setIcon(new ImageIcon(GUI_Client.class.getResource(Dice3)));
		DiceAreaPic3.setBounds(262, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic3);
		
		// Red Indicator that shows Dice 3 is Selected to be Set Aside
		JPanel Dice3SelectionIndicator = new JPanel();
		Dice3SelectionIndicator.setBackground(Color.RED);
		Dice3SelectionIndicator.setBounds(258, 305, 62, 58);
		FarkleGameFrame.getContentPane().add(Dice3SelectionIndicator);
		Dice3SelectionIndicator.setVisible(false);
		
		// Listener for mouse click on Dice area 3
		DiceAreaPic3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Dice3isSelected) {
                	//DiceAreaPic1.setForeground(Color.BLACK);
                	Dice3isSelected = false;
                	Dice3SelectionIndicator.setVisible(Dice3isSelected);
                    System.out.println("Dice 3 Un-Selected");
                } else {
                   // label.setForeground(Color.RED);
                	Dice3isSelected = true;
                	Dice3SelectionIndicator.setVisible(Dice3isSelected);
                    System.out.println("Dice 3 Selected");
                }
            }
        });
		
		// Area where the 2nd Dice will be displayed
		JLabel DiceAreaPic2 = new JLabel("");		
		DiceAreaPic2.setIcon(new ImageIcon(GUI_Client.class.getResource(Dice2)));
		DiceAreaPic2.setBounds(184, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic2);
		
		// Red Indicator that shows Dice 2 is Selected to be Set Aside
		JPanel Dice2SelectionIndicator = new JPanel();
		Dice2SelectionIndicator.setBackground(Color.RED);
		Dice2SelectionIndicator.setBounds(180, 306, 62, 58);
		FarkleGameFrame.getContentPane().add(Dice2SelectionIndicator);
		Dice2SelectionIndicator.setVisible(false);
		
		// Listener for mouse click on Dice area 2
		DiceAreaPic2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Dice2isSelected) {
                	//DiceAreaPic1.setForeground(Color.BLACK);
                	Dice2isSelected = false;
                	Dice2SelectionIndicator.setVisible(Dice2isSelected);
                    System.out.println("Dice 2 Un-Selected");
                } else {
                   // label.setForeground(Color.RED);
                	Dice2isSelected = true;
                	Dice2SelectionIndicator.setVisible(Dice2isSelected);
                    System.out.println("Dice 2 Selected");
                }
            }
        });
	
		// Area where the 1st Dice will be displayed
		JLabel DiceAreaPic1 = new JLabel("");		
		DiceAreaPic1.setIcon(new ImageIcon(GUI_Client.class.getResource(Dice1)));
		DiceAreaPic1.setBounds(101, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic1);
		
		// Red Indicator that shows Dice 1 is Selected to be Set Aside
		JPanel Dice1SelectionIndicator = new JPanel();
		Dice1SelectionIndicator.setBackground(Color.RED);
		Dice1SelectionIndicator.setBounds(97, 305, 62, 58);
		FarkleGameFrame.getContentPane().add(Dice1SelectionIndicator);
		Dice1SelectionIndicator.setVisible(false);
		
		// Listener for mouse click on Dice area 1
		DiceAreaPic1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Dice1isSelected) {
                	//DiceAreaPic1.setForeground(Color.BLACK);
                	Dice1isSelected = false;
                	Dice1SelectionIndicator.setVisible(Dice1isSelected);
                    System.out.println("Dice 1 Un-Selected");
                } else {
                   // label.setForeground(Color.RED);
                	Dice1isSelected = true;
                	Dice1SelectionIndicator.setVisible(Dice1isSelected);
                    System.out.println("Dice 1 Selected");
                }
            }
        });
	
		// Sets the background of the Farkle game frame
		JLabel FarkleGamePicture = new JLabel("");
		FarkleGamePicture.setIcon(new ImageIcon(GUI_Client.class.getResource("/GUI/Farkle_TeamProject Game GUI.jpg")));
		FarkleGamePicture.setBounds(0, 0, 670, 432);
		FarkleGameFrame.getContentPane().add(FarkleGamePicture);
		
		// Button to Set Aside selected dice
		JButton SetAsideButton = new JButton("");
		SetAsideButton.setEnabled(false);
		
		// Button Listener to register when Set Aside Button is clicked by the mouse
		SetAsideButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("Set Aside Button Pressed\n");
			}
		});
		SetAsideButton.setBounds(120, 385, 89, 36);
		FarkleGameFrame.getContentPane().add(SetAsideButton);
		
		// Button to Roll Dice 
		JButton RollDiceButton = new JButton("");
		RollDiceButton.setEnabled(false);
		RollDiceButton.addMouseListener(new MouseAdapter() {
			
			// Button Listener to register when Roll Dice Button is clicked by the mouse
			// In-Game should reload frame, with changed NumofDie and selected dice moved into Set Aside if applicable
			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.print("Roll Dice Pressed\n");
			}
		});
		RollDiceButton.setBounds(309, 385, 97, 36);
		FarkleGameFrame.getContentPane().add(RollDiceButton);
		
		// Button to Bank score on dice that are Set Aside
		JButton BankScoreButton = new JButton("");
		BankScoreButton.setEnabled(false);
		BankScoreButton.addMouseListener(new MouseAdapter() {
			// Button Listener to register when Bank Score Button is clicked by the mouse
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("Bank Score Pressed\n");
			}
		});
		BankScoreButton.setBounds(471, 385, 89, 36);
		FarkleGameFrame.getContentPane().add(BankScoreButton);
		
		// Button to LogOut of game
		JButton LogOutButton = new JButton("");
		LogOutButton.setEnabled(false);
		LogOutButton.addMouseListener(new MouseAdapter() {
			// Button Listener to register when Log Out Button is clicked by the mouse
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("Logout Button Pressed\n");
			}
		});
		LogOutButton.setBounds(571, 23, 89, 31);
		FarkleGameFrame.getContentPane().add(LogOutButton);
		
		// Area to take in the Player1 Name received 
		// by the player1client and display name
		JTextArea Player1ScoreNameArea = new JTextArea();
		Player1ScoreNameArea.setEditable(false);
		Player1ScoreNameArea.setBounds(10, 100, 64, 22);
		FarkleGameFrame.getContentPane().add(Player1ScoreNameArea);
		
		// Area to take in the Player2 Name received 
		// by the player2client and display name
		JTextArea Player2ScoreNameArea = new JTextArea();
		Player2ScoreNameArea.setEditable(false);
		Player2ScoreNameArea.setBounds(10, 127, 64, 22);
		FarkleGameFrame.getContentPane().add(Player2ScoreNameArea);
		
		FarkleGameFrame.setVisible(true);
	}	
	
	private int[] GetDice() {
		// What we will use for the display of the Dice, then is surrounded by a button to be able to be selected to move to the bank
		// Take in the Random number generator score and allocate that same die .png location
		int NumofDie = 6;
		Random random = new Random();
		int[] arr = new int[NumofDie];
		
		for(int i = 0; i <arr.length; i++) {
			arr[i] = 1 + random.nextInt(7 - 1);
		
			System.out.println(arr[i]);
		}
		int rand = 0;
		
		System.out.print(rand);
		
		return arr;
	}
}
	