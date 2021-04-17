
package Programming_Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.Timer;


//Have to clean up the code and segregate it into various functions later. 



public class CovidVendor extends JPanel {
	
	//Fields
	private Vendor player;
	private Customer[] customers = new Customer[3];
	
	private int gameState; //0-Main Menu 1-Level 1
	
	private Date startTime; //starts timer after you click from the main menu.
	private double gameTimeSeconds; //counts the time for which the level has been running.
	
	private Button buttons[] = new Button[4]; 

	private int customerIndex; //Used to check if the buttons are pressed in the same sequence as the customer's orders. See setupMouse method for more details
	
	private int barrier = 200; //Used to set the x-position at which the customer stops walking and picks up their food.
	
	private boolean barrierReached = false; //Used to enable pressing of Serve Button whenever a customer reaches the barrier point.
	
	private boolean served = false; //Checks if a customer has been served or not. The next customer doesn't move forward until player presses the Serve button.
	
	private FloatText[] floatTexts = new FloatText[2]; //Initializes floating texts
	
	private double orderStart=4, orderEnd=10, prepStart=12,prepEnd=22,serveStart=23, countdown; //Will use later to track time

	private int floatTextPressed = -2; //Used to check whether a floating text is clicked or not
	
	private boolean orderPhase = false, prepPhase = false, servePhase = false;
	
	private UIButton playButton = new UIButton(260,270), exitButton = new UIButton(320,470);

	
//Constructor
	public CovidVendor()
	{
		//Initialize gameState
		gameState = 0;
		
		
		
		//Set up Vendor/Player inside the food truck
		player = new Vendor(98,640);
		
		
		
		//Set up Array of customers and randomize their orders
		setupCustomers();
		
		//Sets up 2 floating texts
		setupFloatTexts();
		
		//Set up array of buttons
		setupButtons();
		
		//Sets length of ordersCheck array in Vendor class equal to the number of customers
		player.setLengthOrdersCheck(customers.length);
	}
	

	
	
	
	

	//Class Methods
    public void paintComponent(Graphics g) 
	{ 
		
        //This method runs repeatedly
        super.paintComponent(g);
        //Draw Frame elements
        g.setColor(Color.black);
        g.fillRect(0, 0, 1000, 1000);
       
		
		
		if(gameState == 0) //Main Menu
		{
			drawMenu(g);
		}
		else if(gameState == 1) //Level
		{
			drawElements(g);  
			update();
			
			if(orderPhase) //Displays the customers' order dialogues for 4 secs
			{
				drawOrderDialogue(g);
			}
			
			if(prepPhase) //Draws countdown timer for food prep phase 
			{
				drawPrepTimer(g);
			}
			
		}
		else if(gameState == -1)
		{
			drawMadeIt(g);
		}
		else if(gameState == -2)
		{
			drawGameOver(g);
		}
        
        //Paint the panel
        repaint();
   }
	
   
	
	
	
	
	
   public void update()
   {
	   trackTime(); //Tracks time after player enters gameState = 1
	   updateFloatTexts(); //Causes float texts to bounce. Check update method in FloatText class.
	   
	   if(gameTimeSeconds>orderStart)  //Setup for their order dialogues to appear after 4 secs, by setting the boolean ordered = true for every customer.
	   {
		   for(int i=0; i<customers.length; i++)
		   {
			   customers[i].setOrdered(true);
		   }
	   }
	   
	   if(servePhase)
	   {
		   customersWalk(); //Customers walk one by one to collect their orders.
		   checkGameOver();
		   checkMadeIt();
	   }
	   
	   
   }
   
   
   
   
   
   
//Drawing all elements
   public void drawElements(Graphics g)
   {
	   drawBackground(g);
	   drawCustomers(g, customers);
	   drawButtons(g, buttons);
	   drawLives(g);
	   drawPhaseText(g);
	   drawScore(g);
//	   drawServeDialogue(g);
	   drawFloatText(g);
   }
   
   
   
   
   
   
//Drawing the background
   public void drawBackground(Graphics g)
   {
	   String backgroundImgFilename = "background3.jpg";
       ImageIcon backgroundImg = new ImageIcon(backgroundImgFilename); 
       g.drawImage(backgroundImg.getImage(), 0 ,0 , null); 
   }
   
   
   
   
   
//Drawing the customers
   public void drawCustomers(Graphics g, Customer[] customers)
   {
	   for(int i=0; i<customers.length; i++)
       {
		   
		   if(customers[i].isVisible()) //Customers disappear after they get their food.
		   {
			   if(customers[i].getMaskNum()==1) //Masked
               {
                    String maskedImgFilename = "human-mask.png";
                    ImageIcon maskedImg = new ImageIcon(maskedImgFilename);
                    g.drawImage(maskedImg.getImage(),(int)customers[i].getPosX(),(int)customers[i].getPosY(),null);
               }

			   else if(customers[i].getMaskNum()==2) //No mask
               {
                    String noMaskImgFilename = "human-nomask.png";
                    ImageIcon noMaskImg = new ImageIcon(noMaskImgFilename);
                    g.drawImage(noMaskImg.getImage(),(int)customers[i].getPosX(),(int)customers[i].getPosY(),null);
               }
			   
			   else if(customers[i].getMaskNum()==3) //Not fully masked
               {
                    String partialMaskImgFilename = "human-undernose.png";
                    ImageIcon partialMaskImg = new ImageIcon(partialMaskImgFilename);
                    g.drawImage(partialMaskImg.getImage(),(int)customers[i].getPosX(),(int)customers[i].getPosY(),null);
               }
		   }
		   
       }
	   
	   
   }
   
   
   
  
   
   
//Drawing the main menu
   public void drawMenu(Graphics g)
   {
//	   String startMenuFilename = "clickToStart.jpg";
//       ImageIcon startMenuImg = new ImageIcon(startMenuFilename); 
//	   g.drawImage(startMenuImg.getImage(), 0 ,0 , null); 
	   
	   String playButtonFilename = "playButton.png";
	   ImageIcon playButtonImg = new ImageIcon(playButtonFilename);
	   g.drawImage(playButtonImg.getImage(),(int)playButton.getPosX(),(int)playButton.getPosY(),null);
	   
	   String exitButtonFilename = "exitButton.png";
	   ImageIcon exitButtonImg = new ImageIcon(exitButtonFilename);
	   g.drawImage(exitButtonImg.getImage(),(int)exitButton.getPosX(),(int)exitButton.getPosY(),null);
   }
   
 //Drawing game over screen
   public void drawGameOver(Graphics g)
   {
	   g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
	   g.setColor(Color.RED);
	   g.drawString("You Lose!",200,400);
	   g.drawString("Try and get a score more than 0",200,550);
	   g.drawString("without losing all your lives!",200, 600);
   }
   
 //Drawing win screen
   public void drawMadeIt(Graphics g)
   {
	    g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		g.setColor(Color.WHITE);
		g.drawString("You made it!",400,400);
		g.drawString("Score: "+player.getScore(),400, 600);
   }
   
   
//Drawing the customer order dialogues. 
   public void drawOrderDialogue(Graphics g) 
   {
	   for(int i=0; i<customers.length; i++)
	   {
		   if(customers[i].hasOrdered()) //Checking if customer has ordered or not
		   {
			   switch(customers[i].getDishNum())
			   {
				   case 1: //Draw Taco Dialogue
					   String tacoDialogueFilename = "tacoDialogue.png";
					   ImageIcon tacoDialogueImg = new ImageIcon(tacoDialogueFilename);
			           g.drawImage(tacoDialogueImg.getImage(),(int)customers[i].getPosX()+7,(int)customers[i].getPosY()-40,null);
					   break;
					   
				   case 2: //Draw pizza dialogue
					   String pizzaDialogueFilename = "pizzaDialogue.png";
					   ImageIcon pizzaDialogueImg = new ImageIcon(pizzaDialogueFilename);
					   g.drawImage(pizzaDialogueImg.getImage(),(int)customers[i].getPosX()+7,(int)customers[i].getPosY()-40,null);
					   break;
					   
				   case 3: //Draw burger dialogue
					   String burgerDialogueFilename = "burgerDialogue.png";
					   ImageIcon burgerDialogueImg = new ImageIcon(burgerDialogueFilename);
					   g.drawImage(burgerDialogueImg.getImage(),(int)customers[i].getPosX()+7,(int)customers[i].getPosY()-40,null);
					   break;
					   
				   default:
					   break;
			   }
		   }
		   
	   }
	   
   }
   
   
   
   
   
//Drawing the buttons
   public void drawButtons(Graphics g, Button[] buttons)
   {
	   for(int i=0; i<buttons.length; i++)
	   {
		   switch(buttons[i].getType())
		   {
			   case 0: //Draw taco button
				   String tacoButtonFilename = "tacoButton.png";
				   ImageIcon tacoButtonImg = new ImageIcon(tacoButtonFilename);
				   g.drawImage(tacoButtonImg.getImage(), (int)buttons[i].getPosX(), (int)buttons[i].getPosY(),null);
				   break;
				   
			   case 1: //Draw pizza button
				   String pizzaButtonFilename = "pizzaButton.png";
				   ImageIcon pizzaButtonImg = new ImageIcon(pizzaButtonFilename);
				   g.drawImage(pizzaButtonImg.getImage(), (int)buttons[i].getPosX(), (int)buttons[i].getPosY(),null);
				   break;
				   
			   case 2: //Draw burger button
				   String burgerButtonFilename = "burgerButton.png";
				   ImageIcon burgerButtonImg = new ImageIcon(burgerButtonFilename);
				   g.drawImage(burgerButtonImg.getImage(), (int)buttons[i].getPosX(), (int)buttons[i].getPosY(),null);
				   break;
				   
			   case 3: //Draw serve button
				   String serveButtonFilename = "serveButton.png";
				   ImageIcon serveButtonImg = new ImageIcon(serveButtonFilename);
				   g.drawImage(serveButtonImg.getImage(), (int)buttons[i].getPosX(), (int)buttons[i].getPosY(),null);
				   break;
				   
			   default:
				   break;
				   
		   }
		   
	   }
	   
   }
   
   
   
   
   
   
//Drawing player lives
   public void drawLives(Graphics g)
   {
	   for(int i=0; i<player.getLives(); i++)
	   {
		    String heartImgFilename = "heart.png";
			ImageIcon heartImg = new ImageIcon(heartImgFilename);
			g.drawImage(heartImg.getImage(), 30+32*i, 50, null);
	   }
   }
   
   
   
   
   
 
 //Drawing text that tells player what phase the level is on- ordering, food prep, serving, etc, with some walkthrough text(will add later)
   public void drawPhaseText(Graphics g)
   {
	   if(gameTimeSeconds>orderStart-2 && gameTimeSeconds<orderEnd-2) //Order Phase
	   {
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
		   g.setColor(Color.RED);
		   g.drawString("Order Phase!",300,100);
		   
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		   g.setColor(Color.BLACK);
		   g.drawString("Memorize the customer orders from left to right",205,150);
		   
	   }
	   
	   if(gameTimeSeconds>prepStart-1 && gameTimeSeconds<=prepEnd-4) //Food Prep Phase
	   {
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		   g.setColor(Color.RED);
		   g.drawString("Food Preparation Phase!",250,100);
		   
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		   g.setColor(Color.BLACK);
		   g.drawString("Press the green buttons in the same sequence as the customer orders",200,150);
	   }
	   
	   if(gameTimeSeconds>serveStart-2)
	   {
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
		   g.setColor(Color.RED);
		   g.drawString("Serving Phase!",290,100);
	   }
   }
   
   
   //Drawing score text
   public void drawScore(Graphics g)
   {
	   g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
	   g.setColor(Color.BLACK);
	   g.drawString("Score: "+player.getScore(),750,220);
   }
   
   
   
   
   
   //Drawing floating text
   public void drawFloatText(Graphics g)
   {
	   for(int i=0; i<floatTexts.length; i++)
	   {
		   if(floatTexts[i].isVisible() && floatTexts[i].isClicked() == false)
		   {
			   switch(i)
			   {
				   case 0:
					   String wearAMaskImgFilename = "wearAMask.png";
					   ImageIcon wearAMaskImg = new ImageIcon(wearAMaskImgFilename);
					   g.drawImage(wearAMaskImg.getImage(), floatTexts[i].getPosX(), floatTexts[i].getPosY(), null);
					   break;

				   case 1:
					   String pullUpMaskImgFilename = "pullUpMask.png";
					   ImageIcon pullUpMaskImg = new ImageIcon(pullUpMaskImgFilename);
					   g.drawImage(pullUpMaskImg.getImage(), floatTexts[i].getPosX(), floatTexts[i].getPosY(), null);
					   break;
			   }
		   }
		   
	   }
	   
   }
   
   
 //Drawing countdown timer for the food preparation phase
   public void drawPrepTimer(Graphics g)
	{
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		g.setColor(Color.BLACK);
		g.drawString("Time Left: "+(int)countdown,400,450);
	}
   
   
   
   
//   public void drawServeDialogue(Graphics g) //Doesn't work
//   {
//	   for(int i=0; i<customers.length; i++)
//	   {
//		   if(customers[i].isServed())
//		   {
//			   String serveDialogueFilename = "serveDialogue.png";
//			   ImageIcon serveDialogueImg = new ImageIcon(serveDialogueFilename);
//			   g.drawImage(serveDialogueImg.getImage(),(int)player.getPosX()+7,(int)player.getPosY()-40,null);
//		   }
//	   }
//   }
   
   
   
//Setting up array of customers and randomizing their orders and the way they put on their mask. See randomizeOrder method in Customer class.
   public void setupCustomers()
   {
	   for(int i=0; i<customers.length; i++)
		{
			customers[i] = new Customer((500 + 70*i), 617);
			customers[i].randomizeOrder();
			customers[i].randomizeMask();
		}
   }
   
   
   
   
   
 //Setting up array of buttons and sets their type to the same value as their index in the array.
   public void setupButtons()
   {
	   for(int i=0; i<buttons.length; i++)
		{
			buttons[i] = new Button((50 + 200*i), 750);
			buttons[i].setType(i);
		}
   }
   
   public void setupFloatTexts() //Sets up floating texts
   {
	   for(int i=0; i<floatTexts.length; i++)
	   {
//			floatTexts[i] = new FloatText(250 - (30*i), 250 - (50*i), 1+i, 1+i);
			//Debugging
			floatTexts[i] = new FloatText(300-(100*i), 300 - (100*i), 0 , 0);
	   }
   }
   
   
   
//Setting up mouse
   public void setupMouse()
	{
		
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) { //Method is called every click
				
				if(gameState==0)
				{
					checkMenuButtonPress(me.getX(), me.getY());
				}
				
				
				if(gameState==1)
				{
					if(prepPhase) //Checks for clicks on buttons between 10 and 18 secs into the level
					{

						int buttonPressed = checkButton(me.getX(),me.getY());  //stores the button pressed on every click.
						

						if(buttonPressed >=0 && customerIndex<customers.length) //Checks only when a button is clicked and when the number of clicks is not greater than number of customers
						{
							if(buttonPressed+1 == customers[customerIndex].getDishNum()) //Checking if button value matches customer DishNum
							{
	
								player.setOrderCheck(customerIndex,true);
								System.out.println(player.getOrderCheck(customerIndex));
								
							}
							else
							{
								player.setOrderCheck(customerIndex,false);
								System.out.println(player.getOrderCheck(customerIndex));
							}
							
							customerIndex++;

						}
						
					}
					else if(servePhase)
					{
						checkServePressed(me.getX(),me.getY());
						checkFloatTextPressed(me.getX(),me.getY());
					}
				}
				 
			}
		});
	}
   
   
   
   
   
    public int checkButton(int mouseX, int mouseY) //Returns an integer denoting the type of button pressed
	{
		int buttonPressed  = -1; //If player clicks anywhere other than on the button
		
		for(int i=0; i<buttons.length; i++)
		{
			if(mouseX>=buttons[i].getPosX() && mouseX<=buttons[i].getPosX()+100 && mouseY>=buttons[i].getPosY() && mouseY<=buttons[i].getPosY()+39)
			{
				buttonPressed = buttons[i].getType(); //To store the type of button clicked
			}
		}
		
		return buttonPressed;
	}
	
	
	
	
	
	public void trackTime() //Tracks the time and phases since gameState 1 is active
	{
		Date currentTime = new Date(System.currentTimeMillis());
		gameTimeSeconds = (currentTime.getTime()-startTime.getTime())/1000.0; 
		
		if(gameTimeSeconds>orderStart && gameTimeSeconds<=orderEnd)
		{
			orderPhase = true;
		}
		else 
		{
			orderPhase = false;
		}
		
		if(gameTimeSeconds>=prepStart && gameTimeSeconds<=prepEnd)
		{
			prepPhase = true;
		}
		else
		{
			prepPhase = false;
		}
		
		if(gameTimeSeconds>serveStart)
		{
			servePhase = true;
		}
		else
		{
			servePhase = false;
		}
		
		
		countdown = prepEnd-gameTimeSeconds; //Countdown timer for food preparation phase
	}
	
	
	
	
	

	
	public void customersWalk() //Makes customers move towards the food truck one by one
	{
		//First, enable the first customer to walk.
		customers[0].setWalking(true);
		
		for(int i=0; i<customers.length; i++)
		{
			if(customers[i].isWalking())
			{
				setFloatTextVisibility(true); //Makes floating texts visible whenever a customer is walking.
			}
//			else
//			{
//				setFloatTextVisibility(false);
//			}
			
			
			//Make customers walk till the barrier point, if their boolean walking is set to true
			customers[i].update(barrier);
			
			if(customers[i].getPosX()<=barrier)
			{
				setFloatTextVisibility(false); //Turns off visibility once they reach the barrier
				
				customers[i].setWalking(false); //Stops the customer when they reach the barrier point.
				customers[i].setBarrierReached(true);
			}
		}
	}
	
	
	
	

	
	public void checkServePressed(int mouseX, int mouseY) //Responsible for checking whether serve button is pressed or not
	{
		for(int i=0; i<customers.length; i++)
		{
			if(customers[i].isBarrierReached()) //Checks only when a customer is near the barrier
			{
				int buttonPressed = checkButton(mouseX, mouseY);
				
				if(buttonPressed == 3) //Only checks for clicks on serve button
				{
					customers[i].setServed(true);
					wait(750); //Delay of 750 millisecs
					validateOrders(); //Changes score and lives depending on whether player gets the orders right				
				}
			}
		}
		
	}
	
	
	
	
	public void wait(int ms) //Used to create a delay. The code that follows wait() will be called after 'ms' milliseconds
	{
		try
		{
			Thread.sleep(ms);
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}
	
  
	
	
	
	
	public void validateOrders() //If order right- score+10, else lives-1
	{
		for(int i=0; i<customers.length; i++)
		{
			if(customers[i].isServed() && customers[i].getPosX()==barrier)
			{
				if(player.getOrderCheck(i) && customers[i].getMaskNum() == 1) //Checks the boolean array that stored whether the player pressed 
				{															  //the right food buttons in the preparation phase, and if the customer has a mask on
					player.increaseScore(10);
				}
				else
				{
					player.loseLife();
				}
				
				customers[i].setServed(false);
				moveAwayCustomer(i); //moves customer off map and makes them invisible

			}
		}
	}
	
	
	
	public void updateFloatTexts() //Causes bouncing of the float texts across the screen
	{
		for(int i=0; i<floatTexts.length; i++)
	   {
		   floatTexts[i].update();
	   }
	}
	

	
	
	
	public void checkFloatTextPressed(int mouseX, int mouseY)
	{
		for(int i=0; i<customers.length; i++)
		{
			if(customers[i].isBarrierReached() == false && customers[i].isWalking()) //Checks for clicks only if a customer is walking, and hasn't reached the barrier yet
			{
				int pressedText = getFloatTextIndex(mouseX, mouseY);
				setFloatTextVisibility(false); //If either one of them is clicked, both turn invisible

				
				if(pressedText != -1) //debug
				{
					System.out.println(pressedText);
				}
				
				
				if(pressedText != -1 && customers[i].getPosX()>barrier)
				{
					if(pressedText + 2 == customers[i].getMaskNum())
					{
						customers[i].setWalking(false);
						wait(1250);
						customers[i].setMaskNum(1);
						customers[i].setWalking(true);
					}
					else
					{
						System.out.println("Hi"); //debug
						customers[i].setWalking(false);
						player.loseLife();
						wait(1250);
						customers[i].setWalking(true);
					}
				}
				
			}
		}
	}
	
	
	
	
	
	public void setFloatTextVisibility(boolean visible) //Controls visibility of float texts
	{
		if(visible)
		{
			 for(int j=0; j<floatTexts.length; j++)
			 {
				 floatTexts[j].setVisible(true);
			 }
		}
		else
		{
			for(int j=0; j<floatTexts.length; j++) //Makes floating texts disappear after customer reaches barrier
			{
				floatTexts[j].setVisible(false);
			}
		}
	}

	
	
	
	
	public void moveAwayCustomer(int i) //Moves customer off the map and enables walking for the next customer
	{
		customers[i].setVisible(false);
		customers[i].setPosX(-10000);
		customers[i].setPosY(-10000);
		
		if(i != customers.length-1)
		{
			customers[i+1].setWalking(true);
			setFloatTextVisibility(true);
			for(int j=0; j<floatTexts.length; j++)
			{
				floatTexts[j].setClicked(false);
			}
		}
	}
	
	
	public int getFloatTextIndex(int mouseX, int mouseY)
	{
		int xThreshold=0, yThreshold=0, index=-1;
		
		for(int i=0; i<floatTexts.length; i++)
		{
			switch(i) //Needed different x and y thresholds due to different image sizes
			{
				case 0:
					xThreshold = 270;
					yThreshold = 24;
					break;
					
				case 1:
					xThreshold = 220;
					yThreshold = 70;
					break;
					
				default:
					break;
			}
			
			if(mouseX>=floatTexts[i].getPosX() && mouseX<=floatTexts[i].getPosX()+xThreshold && mouseY>=floatTexts[i].getPosY() && mouseY<=floatTexts[i].getPosY()+yThreshold)
			{
				if(floatTexts[i].isClicked())
				{
					index = -1;
				}
				
				else
				{
					index = i;
				
					for(int j=0; j<floatTexts.length; j++) //If one text is clicked, both of them turn invisible
					{
						floatTexts[j].setClicked(true);
					}
				}
				
			}
		}
		
		return index;
	}
	
	
	
	
	public void checkGameOver() //Checks if player has lost all their lives, or if their score is 0
	{
		int count = 0;
		
		if(player.getLives() <= 0)
		{
			gameState = -2;
		}
		else
		{
			for(int i=0; i<customers.length; i++)
			{
				if(customers[i].isVisible() == false && customers[i].getPosX()<0)
				{
					count ++;
				}
			}
			
			if(count == customers.length && player.getScore() <=0)
			{
				gameState = -2;
			}
		}
	}
	
	public void checkMadeIt()
	{
		if(customers[customers.length-1].isVisible() == false)
		{
			if(player.getScore()>0 && player.getLives()>0)
			{
				gameState = -1;
			}
		}
	}
	
	
	
	public void checkMenuButtonPress(int mouseX, int mouseY)
	{
		if(mouseX>=playButton.getPosX() && mouseX<=playButton.getPosX()+512 && mouseY>=playButton.getPosY() && mouseY<=playButton.getPosY()+223)
		{
			gameState = 1;
			startTime = new Date(System.currentTimeMillis());
			customerIndex = 0; //To check if the first button click matches with the first customer's order. See below for more details
		}
		else if(mouseX>=exitButton.getPosX() && mouseX<=exitButton.getPosX()+350 && mouseY>=exitButton.getPosY() && mouseY<=exitButton.getPosY()+150)
		{
			System.exit(0);
		}
	}
	
}