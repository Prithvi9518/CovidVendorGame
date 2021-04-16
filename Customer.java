
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
	private int maskNum; //1- fully masked 2- no mask  3- mask under nose
	private boolean barrierReached;
	private boolean served;
	
	private double speedX = 0.5; // walking speed

	//Constructor
	public Customer(double posX, double posY) 
	{
		this.posX = posX;
		this.posY = posY;
		ordered = false;
		walking = false;
		visible = true;
		barrierReached = false;
		served = false;
	}

	public Customer() {
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

	public boolean isBarrierReached() {
		return barrierReached;
	}

	public boolean isServed() {
		return served;
	}

	public double getSpeedX() {
		return speedX;
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

	public void setBarrierReached(boolean barrierReached) {
		this.barrierReached = barrierReached;
	}

	public void setServed(boolean served) {
		this.served = served;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
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
		
		maskNum = rand.nextInt(3) + 1; //1- fully masked 2- no mask, 3- mask under nose
	}
	
}
