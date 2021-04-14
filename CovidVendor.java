
package Programming_Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


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
	
	private boolean serveDialogueAppears = false; //Didn't get this feature to work yet.
	

	
//Constructor
	public CovidVendor()
	{
		//Initialize gameState
		gameState = 0;
		
		
		
		//Set up Vendor/Player inside the food truck
		player = new Vendor(98,640);
		
		
		
		//Set up Array of customers and randomize their orders
		setupCustomers();
		
		
		
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
			
			if(gameTimeSeconds>3 && gameTimeSeconds<=8) //Displays the customers' order dialogues for 4-5 secs
			{
				drawOrderDialogue(g);
			}
			
			
		
		}
        
        //Paint the panel
        repaint();
   }
	
   
	
	
	
	
	
   public void update()
   {
	   trackTime(); //Tracks time after player enters gameState = 1
	   
	   
	   if(gameTimeSeconds>3)  //Setup for their order dialogues to appear after 3 secs, by setting the boolean ordered = true for every customer.
	   {
		   for(int i=0; i<customers.length; i++)
		   {
			   customers[i].setOrdered(true);
		   }
	   }
	   
	   if(gameTimeSeconds>19)
	   {
		   customersWalk(); //Customers walk one by one to collect their orders.

		   
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
	   drawServeDialogue(g, serveDialogueAppears);
	   drawScore(g);
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
                    String customerImgFilename = "human-mask.png";
                    ImageIcon customerImg = new ImageIcon(customerImgFilename);
                    g.drawImage(customerImg.getImage(),(int)customers[i].getPosX(),(int)customers[i].getPosY(),null);
               }

               if(customers[i].getMaskNum()==2) //Not fully masked
               {
                    String customerImgFilename = "human-undernose.png";
                    ImageIcon customerImg = new ImageIcon(customerImgFilename);
                    g.drawImage(customerImg.getImage(),(int)customers[i].getPosX(),(int)customers[i].getPosY(),null);
               }

               if(customers[i].getMaskNum()==3) //No mask
               {
                    String customerImgFilename = "human-nomask.png";
                    ImageIcon customerImg = new ImageIcon(customerImgFilename);
                    g.drawImage(customerImg.getImage(),(int)customers[i].getPosX(),(int)customers[i].getPosY(),null);
               }
		   }
		   
       }
	   
	   
   }
   
   
   
  
   
   
//Drawing the main menu
   public void drawMenu(Graphics g)
   {
	   String startMenuFilename = "clickToStart.jpg";
       ImageIcon startMenuImg = new ImageIcon(startMenuFilename); 
	   g.drawImage(startMenuImg.getImage(), 0 ,0 , null); 
   }
   
   
   
 
   
   
//Drawing the customer order dialogues. 
   public void drawOrderDialogue(Graphics g) 
   {
	   for(int i=0; i<customers.length; i++)
	   {
		   if(customers[i].hasOrdered()) //Checking if customer has ordered or not
		   {
			   if(customers[i].getDishNum()==1) //Draw taco dialogue
			   {
				   String tacoDialogueFilename = "tacoDialogue.png";
				   ImageIcon tacoDialogueImg = new ImageIcon(tacoDialogueFilename);
				   g.drawImage(tacoDialogueImg.getImage(),(int)customers[i].getPosX()+7,(int)customers[i].getPosY()-40,null);
			   }
			   else if(customers[i].getDishNum()==2) //Draw pizza dialogue
			   {
				   String pizzaDialogueFilename = "pizzaDialogue.png";
				   ImageIcon pizzaDialogueImg = new ImageIcon(pizzaDialogueFilename);
				   g.drawImage(pizzaDialogueImg.getImage(),(int)customers[i].getPosX()+7,(int)customers[i].getPosY()-40,null);
			   }
			   else if(customers[i].getDishNum()==3) //Draw burger dialogue
			   {
				    String burgerDialogueFilename = "burgerDialogue.png";
					ImageIcon burgerDialogueImg = new ImageIcon(burgerDialogueFilename);
					g.drawImage(burgerDialogueImg.getImage(),(int)customers[i].getPosX()+7,(int)customers[i].getPosY()-40,null);
			   }
		   }
		   
	   }
	   
   }
   
   
   
   public void drawServeDialogue(Graphics g, boolean serveDialogueAppears) //Doesn't work yet.
   {	
	   if(serveDialogueAppears)
	   {
		   String serveDialogueFilename = "serveDialogue.png";
	       ImageIcon serveDialogueImg = new ImageIcon(serveDialogueFilename);
	       g.drawImage(serveDialogueImg.getImage(), (int)player.getPosX()+7, (int)player.getPosY()-40, null);
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
	   if(gameTimeSeconds>1 && gameTimeSeconds<6) //Order Phase
	   {
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
		   g.setColor(Color.RED);
		   g.drawString("Order Phase!",300,100);
		   
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		   g.setColor(Color.BLACK);
		   g.drawString("Memorize the customer orders from left to right",200,150);
		   
	   }
	   
	   if(gameTimeSeconds>9 && gameTimeSeconds<=17) //Food Prep Phase
	   {
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		   g.setColor(Color.RED);
		   g.drawString("Food Preparation Phase!",250,100);
		   
		   g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		   g.setColor(Color.BLACK);
		   g.drawString("Press the green buttons in the same sequence as the customer orders",170,150);
	   }
	   
	   if(gameTimeSeconds>17)
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
   
   
   
   
   
//Setting up array of customers and randomizing their orders and the way they put on their mask. See randomizeOrder method in Customer class.
   public void setupCustomers()
   {
	   for(int i=0; i<customers.length; i++)
		{
			customers[i] = new Customer((400 + 70*i), 617);
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
   
   
   
   
   
//Setting up mouse
   public void setupMouse()
	{
		
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) { //Method is called every click
				
				if(gameState==0)
				{
					gameState = 1;
					startTime = new Date(System.currentTimeMillis());
				    customerIndex = 0; //To check if the first button click matches with the first customer's order. See below for more details
				}
				
				
				if(gameState==1)
				{
					if(gameTimeSeconds>9 && gameTimeSeconds<17) //Checks for clicks on buttons between 10 and 17 secs into the level
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
					
					if(barrierReached) //Checks for clicks on serve button when a customer reaches the food stall
					{
						checkServe(me.getX(),me.getY());
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
	
	
	
	
	
	public void trackTime() //Tracks the time since gameState 1 is active
	{
		Date currentTime = new Date(System.currentTimeMillis());
		gameTimeSeconds = (currentTime.getTime()-startTime.getTime())/1000.0; 
	}
	
	
	
	
	
	
	public void customersWalk() //Customers walk to get their food items.
	{
		   customers[0].setWalking(true); //Enable walk for first customer
		   
	       for(int i=0; i<customers.length; i++) //Go through all the customers
		   {
			   customers[i].update(barrier); //Move customer until they reach the barrier
			   
			   if(customers[i].getPosX() <= barrier)
			   {
				   customers[i].setWalking(false); //Disable walking after they reach the barrier
				   
				   barrierReached = true; //Enables serve button press
				   
				   
				   
				   
				   if(i != customers.length-1 && served == true)
				   {   
						customers[i+1].setWalking(true); //Enable walking for the next customer after pressing Serve
				   }
				  
				   if(served == true) //Customer disappears after they get their food.
				   {
					   customers[i].setVisible(false);
					   customers[i].setPosX(1000);
					   customers[i].setPosY(1000); //sends customers off the map after they turn invisible
				   }
				  
			   }
			   
			   if(i == customers.length-1) //I still don't know why this works
			   {
				   served = false;
			   }
			   
			   
		   }
	}
	
	
	
	
	
	public void checkServe(int mouseX, int mouseY) //Called when a customer reaches the food stall, to check for clicks on Serve button
	{
		int buttonPressed = checkButton(mouseX,mouseY);
						
		if(buttonPressed ==  3)
		{
			barrierReached = false; //Disables serve button press until next customer reaches barrier.
			
			System.out.println("Here you go");
			
			checkOrders();
			
			wait(2000);
			
			served = true; //Sets the next customer in motion only when player clicks on the Serve button
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
	
   
	
	
	
	
	public void checkOrders() //If player gets order right- score+10. Else- lose a life
	{
		for(int i=0; i<customers.length; i++)
		{
			if(customers[i].getPosX() == barrier)
			{
				if(player.getOrderCheck(i))
				{
					player.increaseScore(10);
				}
				else
				{
					player.loseLife();
				}
				
			}
			
		}
		
	}
	
}
