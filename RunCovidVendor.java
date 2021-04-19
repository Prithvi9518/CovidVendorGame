package Programming_Project;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class RunCovidVendor extends JPanel {

	public static void main(String[] args) throws IOException {

		//Scanner
		Scanner scanner = new Scanner(System.in);
		
		//File setup
		File inputFile = new File("scores.txt");
		Scanner input = new Scanner(inputFile);
		
		
		//Set up array list of scores
		ArrayList<Score> listScores = new ArrayList<>();
		
		//Set up Tournament
		Tournament covidVendorTourney = new Tournament("Covid Vendor Tournament", 1000, "Covid Vendor", listScores);
		
		//Player Details
		String gamerTag;
		int Age;
		
		System.out.println("Enter 1- play game, 2- Display all scores, 3- Show highest score, 4- exit");
		int userInput = scanner.nextInt();
		
		while(userInput != 4)
		{
			if(userInput == 1) //Play the game
			{
				System.out.println("Enter gamer tag:");
				gamerTag = scanner.next();
				System.out.println("Enter your age:");
				Age = scanner.nextInt();
				
				//Setup Frame and Grpahics object
				JFrame frame = new JFrame("Covid Vendor");

				//Set Details of Frame for graphics
				frame.setSize(1000, 1000);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.setResizable(false);
				frame.setBackground(Color.black);

				//Create my game and add to the frame
				CovidVendor myGame = new CovidVendor(covidVendorTourney, gamerTag, Age);
				frame.getContentPane().add(myGame);

				//Set up mouse
				myGame.setupMouse();

				//Play music
				playMusic("BGMmusic.wav");
			}
			
			else if(userInput == 2) //Display all scores
			{
				String gamertag;
				int age;
				int score;
				int level;

				while (input.hasNext())
				{
					gamertag = input.next();
					age = input.nextInt();
					score = input.nextInt();
					level = input.nextInt();
					
					System.out.println("Gamer Tag: "+gamertag+" Age: "+age+" Score: "+score+" Level: "+level);
				}
				
				System.out.println("");
			}
			
			else if(userInput == 3) //Display high score
			{
				System.out.println();
				System.out.println("The highest Score is "+covidVendorTourney.maxValue());
			}
			
			System.out.println("Enter 1- play game, 2- Display all scores, 3- Show highest score, 4- exit");
			userInput = scanner.nextInt();
			
		}
		
		if(userInput == 4)
		{
			System.exit(0);
		}
		
	}
	
	
	public static void playMusic(String filePath) //Audio
	{
		InputStream music;
		
		try
		{
			music = new FileInputStream(new File(filePath));
			AudioStream audio = new AudioStream(music);
			AudioPlayer.player.start(audio);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Error");
		}
				
	}
}
