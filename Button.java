
package Programming_Project;


public class Button {
	//Fields
	private double posX;
	private double posY;
	private int type; //Tells you what the button does. Type 0 = taco, 1 = pizza, 2 = burger 3 = serve
	private boolean pressed; //checks whether the button is pressed or not (hasn't been used yet-- not sure if it's needed or not)
	
	//Constructors
	public Button(double posX, double posY, int type) 
	{
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.pressed = false;
	}
	
	public Button(double posX, double posY) 
	{
		this.posX = posX;
		this.posY = posY;
		this.pressed = false;
	}
	

	public Button() 
	{
		this.pressed = false;
	}
	
	//Getters
	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public int getType() {
		return type;
	}

	public boolean isPressed() {
		return pressed;
	}
	

	
	//Setters
	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void setType(int type) {
		this.type = type;
	}
	

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	
	//Methods
	public void update()
	{
		
	}
	
	
	
	
}
