package com.Team.GameName.Utilities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public abstract class Rigid{
	
	protected float positionX;
	protected float positionY;
	protected int width;
	protected int height;
	protected Animation currentAnimation;
	protected Shape boundingBox = null;
	private int widthImage;
	private int heightImage;
	private boolean repeat;
	
	protected enum Direction{
		Left,Right
	}
	
	//CONSTRUCTORS
	public Rigid() throws SlickException{
		init();
	}
	
	public Rigid(float positionX, float positionY) throws SlickException{
		this();
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public Rigid(float positionX, float positionY, int width, int height) throws SlickException{
		this(positionX, positionY);
		this.width = width;
		this.height = height;
	}
	
	public Rigid(Image image, float positionX, float positionY, int width, int height) throws SlickException{
		this(positionX, positionY);
		this.width = width;
		this.height = height;
	}
	
	public boolean intersects(Rigid other) {
	    return other.getBoundingBox() == null ? false : this.getBoundingBox().intersects(other.getBoundingBox());
	}
	
	//GETTERS AND SETTERS
	public Shape getBoundingBox() {
		return this.boundingBox;
	}
	
	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}
	
	
	//ABSTRACT METHODS
	public abstract void init() throws SlickException;
	
	public abstract void Render(Graphics g, Controller controller) throws SlickException;
	
	public abstract void Update(Controller controller, int delta) throws SlickException;
}
