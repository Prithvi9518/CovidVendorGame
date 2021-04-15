
package Programming_Project;


public class FloatText {
	//Fields
	private int posX, posY;
	private double speedX, speedY;
	private boolean visible;
	
	private boolean clicked;
	
	//Constructors
	public FloatText(int posX, int posY, double speedX, double speedY) {
		this.posX = posX;
		this.posY = posY;
		this.speedX = speedX;
		this.speedY = speedY;
		visible = false;
		clicked = false;
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

	public boolean isVisible() {
		return visible;
	}
	
	public boolean isClicked() {
		return clicked;
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

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	
	//Methods
	public void update()
	{
		posX+=speedX;
		posY+=speedY;
		
		if(posX>=700 || posX<=200)
		 {
			 speedX = -speedX;
		 }
		 
		 if(posY>=400 || posY<=100)
		 {
			 speedY = -speedY;
		 }
	}
	
	
}
