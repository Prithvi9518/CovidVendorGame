
package Programming_Project;

import java.util.Random;


public class Customer {
	//Fields
	private double posX;
	private double posY;
	private boolean ordered; //to track whether a customer has ordered or not.
	private int dishNum; //1-Taco 2-Pizza 3-Burger
	private boolean walking; //Controls when the customers start walking to pick up their food.
	private boolean visible;
	private int maskNum; //1- fully masked 2- mask under nose  3- no mask
	
	private double speedX = 0.5; // walking speed

	//Constructor
	public Customer(double posX, double posY) 
	{
		this.posX = posX;
		this.posY = posY;
		ordered = false;
		walking = false;
		visible = true;
	}
	
	//Getters
	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}
	
	public boolean hasOrdered() {
		return ordered;
	}
	
	public int getDishNum()
	{
		return dishNum;
	}

	public boolean isWalking() {
		return walking;
	}

	public boolean isVisible() {
		return visible;
	}

	public int getMaskNum() {
		return maskNum;
	}
	
	
	
	
	
	//Setters
	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}
	
	public void setDishNum(int dishNum)
	{
		this.dishNum = dishNum;
	}

	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setMaskNum(int maskNum) {
		this.maskNum = maskNum;
	}
	
	
	
	
	//Methods
	public void update(int barrier) // makes customer walk until they reach a posX value of barrier, if boolean walking is set to true.
	{
		if(walking && posX>barrier)
		{
			posX -= speedX;
		}
	}
	
	public void randomizeOrder()
	{
		Random rand = new Random();
		
		dishNum = rand.nextInt(3)+1;  //1- Taco, 2- Pizza, 3- Burger
	}
	
	public void randomizeMask()
	{
		Random rand = new Random();
		
		maskNum = rand.nextInt(3) + 1; //1- fully masked 2- mask under nose  3- no mask
	}
	
}
