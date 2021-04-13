package Programming_Project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

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
	}

}
