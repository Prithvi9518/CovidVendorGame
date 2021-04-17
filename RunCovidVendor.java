package Programming_Project;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class RunCovidVendor extends JPanel {

	public static void main(String[] args) {
		//Setup Frame and Grpahics object
		JFrame frame = new JFrame("Test");

		//Set Details of Frame for graphics
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setResizable(false);
		frame.setBackground(Color.black);

		//Create my game and add to the frame
		CovidVendor myGame = new CovidVendor();
		frame.getContentPane().add(myGame);

		//Set up mouse
		myGame.setupMouse();
		
		//Play music
		playMusic("BGMmusic.wav");
	}
	
	
	public static void playMusic(String filePath)
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
