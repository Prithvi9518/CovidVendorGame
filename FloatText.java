
package Programming_Project;


public class FloatText {
	//Fields
	private int posX, posY;
	private double speedX, speedY;
	
	//Constructors
	public FloatText(int posX, int posY, double speedX, double speedY) {
		this.posX = posX;
		this.posY = posY;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	//Getters
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}
	
	//Setters
	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	
	//Methods
	public void update()
	{
		posX+=speedX;
		posY+=speedY;
		
		if(posX>=300 || posX<=600)
		{
			speedX = -speedX;
		}
		 
		if(posY>=820 || posY<=0)
		{
			speedY = -speedY;
		}
	}
	
	
}
