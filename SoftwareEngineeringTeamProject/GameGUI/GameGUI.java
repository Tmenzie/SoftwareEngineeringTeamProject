package GameGUI;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JPanel;
public class GameGUI {
	private JFrame FarkleGameFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI();
					window.FarkleGameFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FarkleGameFrame = new JFrame();
		FarkleGameFrame.setTitle("Farkle");
		FarkleGameFrame.setBounds(100, 100, 686, 471);
		FarkleGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FarkleGameFrame.getContentPane().setLayout(null);
		
		ImageIcon img = new ImageIcon("/GUI/DiceIcon.png");
		FarkleGameFrame.setIconImage(img.getImage());
		
		int NumofDie = 6;
		Random random = new Random();
		int[] arr = new int[NumofDie];
		
		for(int i = 0; i <arr.length; i++)
		{
			arr[i] = 1 + random.nextInt(7 - 1);
		
			System.out.println(arr[i]);
		}
		int rand = 0;
		
		
		
		//GetDice();
		
		String Dice1 = "/SoftwareEngineeringTeamProject/GameGUI/" + arr[0] + "_dice.jpg";
		String Dice2 = "/SoftwareEngineeringTeamProject/GameGUI/" + arr[1] + "_dice.jpg";
		String Dice3 = "/SoftwareEngineeringTeamProject/GameGUI/" + arr[2] + "_dice.jpg";
		String Dice4 = "/SoftwareEngineeringTeamProject/GameGUI/" + arr[3] + "_dice.jpg";
		String Dice5 = "/SoftwareEngineeringTeamProject/GameGUI/" + arr[4] + "_dice.jpg";
		String Dice6 = "/SoftwareEngineeringTeamProject/GameGUI/" + arr[5] + "_dice.jpg";
		
		JLabel DiceAreaPic6 = new JLabel("");
		DiceAreaPic6.setIcon(new ImageIcon(GameGUI.class.getResource(Dice6)));
		DiceAreaPic6.setBounds(501, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic6);
		
		JLabel DiceAreaPic5 = new JLabel("");
		DiceAreaPic5.setIcon(new ImageIcon(GameGUI.class.getResource(Dice5)));
		DiceAreaPic5.setBounds(424, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic5);
		
		JLabel DiceAreaPic4 = new JLabel("");
		DiceAreaPic4.setIcon(new ImageIcon(GameGUI.class.getResource(Dice4)));
		DiceAreaPic4.setBounds(341, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic4);
		
		JLabel DiceAreaPic3 = new JLabel("");
		DiceAreaPic3.setIcon(new ImageIcon(GameGUI.class.getResource(Dice3)));
		DiceAreaPic3.setBounds(262, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic3);
		
		JLabel DiceAreaPic2 = new JLabel("");
		DiceAreaPic2.setIcon(new ImageIcon(GameGUI.class.getResource(Dice2)));
		DiceAreaPic2.setBounds(184, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic2);
		//System.out.print("\n" + Dice1);
		
		JLabel DiceAreaPic1 = new JLabel("");
		DiceAreaPic1.setIcon(new ImageIcon(GameGUI.class.getResource(Dice1)));
		//ImageIcon icon = new ImageIcon(Dice1);
		//icon.getImage().flush();
		//imageLabel.setIcon(icon);
		DiceAreaPic1.setBounds(101, 309, 55, 51);
		FarkleGameFrame.getContentPane().add(DiceAreaPic1);
		
		JLabel FarkleGamePicture = new JLabel("");
		FarkleGamePicture.setIcon(new ImageIcon(GameGUI.class.getResource("/SoftwareEngineeringTeamProject/GameGUI/Farkle_TeamProject Game GUI.jpg")));
		FarkleGamePicture.setBounds(0, 0, 670, 432);
		FarkleGameFrame.getContentPane().add(FarkleGamePicture);
		
		JButton SetAsideButton = new JButton("");
		SetAsideButton.setEnabled(false);
		SetAsideButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("Set Aside Button Pressed\n");
			}
		});
		SetAsideButton.setBounds(120, 385, 89, 36);
		FarkleGameFrame.getContentPane().add(SetAsideButton);
		
		JButton RollDiceButton = new JButton("");
		RollDiceButton.setEnabled(false);
		RollDiceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.print("Roll Dice Pressed\n");
			}
		});
		RollDiceButton.setBounds(309, 385, 97, 36);
		FarkleGameFrame.getContentPane().add(RollDiceButton);
		
		JButton BankScoreButton = new JButton("");
		BankScoreButton.setEnabled(false);
		BankScoreButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("Bank Score Pressed\n");
			}
		});
		BankScoreButton.setBounds(471, 385, 89, 36);
		FarkleGameFrame.getContentPane().add(BankScoreButton);
		
		JButton LogOutButton = new JButton("");
		LogOutButton.setEnabled(false);
		LogOutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("Logout Button Pressed\n");
			}
		});
		LogOutButton.setBounds(571, 23, 89, 31);
		FarkleGameFrame.getContentPane().add(LogOutButton);
		
		JTextArea Player1ScoreNameArea = new JTextArea();
		Player1ScoreNameArea.setEditable(false);
		Player1ScoreNameArea.setBounds(10, 100, 64, 22);
		FarkleGameFrame.getContentPane().add(Player1ScoreNameArea);
		
		JTextArea Player2ScoreNameArea = new JTextArea();
		Player2ScoreNameArea.setEditable(false);
		Player2ScoreNameArea.setBounds(10, 127, 64, 22);
		FarkleGameFrame.getContentPane().add(Player2ScoreNameArea);
		
		
		JButton DiceArea2 = new JButton("");
		DiceArea2.setEnabled(false);
		DiceArea2.setBounds(184, 307, 55, 53);
		FarkleGameFrame.getContentPane().add(DiceArea2);
		
		JButton DiceArea3 = new JButton("");
		DiceArea3.setEnabled(false);
		DiceArea3.setBounds(262, 307, 55, 53);
		FarkleGameFrame.getContentPane().add(DiceArea3);
		
		JButton DiceArea4 = new JButton("");
		DiceArea4.setEnabled(false);
		DiceArea4.setBounds(341, 307, 55, 53);
		FarkleGameFrame.getContentPane().add(DiceArea4);
		
		JButton DiceArea5 = new JButton("");
		DiceArea5.setEnabled(false);
		DiceArea5.setBounds(424, 307, 55, 53);
		FarkleGameFrame.getContentPane().add(DiceArea5);
		
		JButton DiceArea6 = new JButton("");
		DiceArea6.setEnabled(false);
		DiceArea6.setBounds(501, 307, 55, 53);
		FarkleGameFrame.getContentPane().add(DiceArea6);
		
	}	
	

	private int[] GetDice()
	{
		// What we will use for the display of the Dice, then is surrounded by a button to be able to be selected to move to the bank
		//Take in the Random number generator score and allocate that same die png location
		int NumofDie = 6;
		Random random = new Random();
		int[] arr = new int[NumofDie];
		
		for(int i = 0; i <arr.length; i++)
		{
			arr[i] = 1 + random.nextInt(7 - 1);
		
			System.out.println(arr[i]);
		}
		int rand = 0;
		
		/*
		String[] TotalofDie = new String[NumofDie];
		
		for(int i = 0; i < arr.length;i++)
		{
			TotalofDie = 
		}
		*/
	
		
		System.out.print(rand);
		
		
		return arr;
	}
}
	

