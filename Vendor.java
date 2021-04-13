
package Programming_Project;


public class Vendor {
	//Fields
	private double posX;
	private double posY;
	private int lives;
	private boolean[] ordersCheck;
	
	//Constructor
	public Vendor(double posX, double posY)
	{
		this.posX = posX;
		this.posY = posY;
		lives = 5;
	}
	
	//Getters
	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public int getLives() {
		return lives;
	}
	
	public boolean getOrderCheck(int index)
	{
		return ordersCheck[index];
	}
	
	//Setters
	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public void setOrderCheck(int index, boolean orderCheck)
	{
		this.ordersCheck[index] = orderCheck;
	}
	
//Methods
	public void update()
	{
		
	}
	
	//Method to set length of ordersCheck array.
	public void setLengthOrdersCheck(int length)
	{
		this.ordersCheck = new boolean[length];
	}
	
}