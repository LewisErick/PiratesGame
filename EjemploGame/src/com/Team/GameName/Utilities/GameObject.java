package com.Team.GameName.Utilities;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Environment.TrianglePlatform;

public abstract class GameObject extends Rigid{
	
	protected float inicialVelocityY = 0;
	protected float maxVelocityY;
	protected float maxVelocityX;
	protected boolean inGround;
	private float timer = 0;
	private static final float aceleration = 9.8f;
	
	//CONSTRUCTORS
	public GameObject() throws SlickException{
		super();
	}
	
	public GameObject(float positionX, float positionY) throws SlickException{
		super();
		super.positionX = positionX;
		super.positionY = positionY;
	}
	
	public GameObject(float positionX, float positionY, int width, int height) throws SlickException{
		this(positionX, positionY);
		this.width = width;
		this.height = height;
	}
	
	public GameObject(Image image, float positionX, float positionY, int width, int height, int spriteX, int spriteY, int frames, int duration) throws SlickException{
		super(image, positionX, positionY, width, height);
		//this.animation = this.getAnimation(this.image, spriteX, spriteY, this.width, this.height, frames, duration);
	}
	
	//FUNCTIONS
	/*public void moveRight(float delta){
		this.positionX += Math.abs(delta) * maxVelocityX;
	}
	
	public void moveLeft(float delta){
		this.positionX -= Math.abs(delta) * maxVelocityX;
	}
	
	public void moveUp(float delta){
		this.positionY -= Math.abs(delta) * maxVelocityX;
	}
	
	public void moveDown(float delta){
		this.positionY += Math.abs(delta) * maxVelocityX;
	}*/
	
	public boolean move(Controller controller, float delta, Direction direction) throws SlickException{
		float deltaPosition = (direction == Direction.Right ? 1 : -1) * Math.abs(delta) * maxVelocityX;
		float deltaX;
		float deltaY;
		LinkedList<Rigid> collision = controller.checkListCollision(this, deltaPosition, 0);
		if(collision == null){
			controller.moveCamera(deltaPosition,0);
		}else{
			if(collision.size() == 1 && collision.get(0) instanceof TrianglePlatform){
				deltaX = deltaPosition * (float)Math.sin(((TrianglePlatform)collision.get(0)).getAngle());
				deltaY = Math.abs(deltaPosition * (float)Math.cos(((TrianglePlatform)collision.get(0)).getAngle()));
				controller.moveCamera(deltaX,-deltaY);
				return true;
			}
			return false;
		}
		return true;
	}
	
	/*public boolean checkRange(GameObject go, float range){
		if(distance(this.positionX+(bounds[2]/2),this.positionY+(bounds[3]/2),go.positionX+(go.bounds[2]/2),go.positionY+(go.bounds[3]/2)) <= range){
			return true;
		}
		return false;
	}
	
	private float distance(float dist1X, float dist1Y, float dist2X, float dist2Y){
		return (float) Math.sqrt(Math.pow(dist1X - dist2X,2) + Math.pow(dist1Y - dist2Y,2));
	}*/
	
	public void gravity(Controller controller, float delta) throws SlickException{
		float timer2 = timer / 1000f;
		float deltaY = this.inicialVelocityY * timer2 - aceleration * timer2 * timer2 / 2f;
		if(controller.checkCollision(this, 0f,-deltaY) != null){
			this.inicialVelocityY = 0;
			timer = 0;
			System.out.println("Ground");
			inGround = true;
		}else{
			timer += delta;
			System.out.println("Falling");
			controller.moveCamera(0, -deltaY);
			inGround = false;
		}
	}
	
	//GETTERS AND SETTERS
	public boolean isInGround(){
		return inGround;
	}
	
	public Animation getAnimation(Image i, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int frames, int duration){
		Animation a = new Animation(false);
		for(int y = 0; y < spriteY; y++){
			for(int x = 0; x < spriteX; x++){
				a.addFrame(i.getSubImage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight), duration);
			}
		}
		return a;
	}

}
