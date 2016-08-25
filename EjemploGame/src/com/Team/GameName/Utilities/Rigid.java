package com.Team.GameName.Utilities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.Team.GameName.Characters.MainCharacter;

public abstract class Rigid {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private float positionX;
	private float positionY;
	private int width;
	private int height;
	private int widthImage;
	private int heightImage;
	private boolean repeat = false;
	private boolean visible = true;
	private Animation currentAnimation;
	private Shape boundingBox;
	private Direction currentDirection = Direction.Right;
	private Music currentSound;

	public static enum Direction {
		Left, Right
	}

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Rigid(float positionX, float positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public Rigid(float positionX, float positionY, int width, int height) {
		this(positionX, positionY);
		this.width = width;
		this.height = height;
		this.widthImage = width;
		this.heightImage = height;
	}
	
	public void setDrawingBox(int width, int height, float positionX, float positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.widthImage = width;
		this.heightImage = height;
	}

	public Rigid(float positionX, float positionY, int width, int height, int widthImage, int heightImage,
			boolean repeat) {
		this(positionX, positionY);
		this.width = width;
		this.height = height;
		this.widthImage = widthImage;
		this.heightImage = heightImage;
		this.repeat = repeat;
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void render(Graphics g) throws SlickException {
		if (isVisible()) {
			setBoundingBox();
			//drawObjectRelatively(g);
			Render(g);
		}
	}

	public void update(int delta) throws SlickException {
		if(isVisible()){
			if (currentAnimation != null) {
				currentAnimation.update(delta);
			}
			Update(delta);
		}
	}

	/**
	 * Función que checa si hay colisión entre un objeto y si mismo.
	 * 
	 * @param other
	 *            Objeto con el que se desea checar si tiene una colisión.
	 * @return Regresa si encontró o no un objeto en colisión consigo mismo.
	 */
	public boolean intersects(Rigid other) {
		return other.getBoundingBox() == null ? false : getBoundingBox().intersects(other.getBoundingBox());
	}

	public void drawObjectRelatively(Graphics g) {
		g.setColor(Color.green);
		g.setLineWidth(2);
		g.drawRect(getRealPositionX(), getRealPositionY(), width, height);
		g.setColor(Color.white);
	}

	public void drawObjectAbsolute(Graphics g) {
		g.setColor(Color.red);
		g.setLineWidth(1);
		g.drawRect(positionX, positionY, width, height);
		g.setColor(Color.white);
	}

	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public float getPositionX() {
		return this.positionX;
	}

	public float getRealPositionX() {
		return this.positionX - Controller.getInstance().getPositionX();
	}

	public void addPositionX(float deltaX) {
		this.positionX += deltaX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public float getRealPositionY() {
		return positionY - Controller.getInstance().getPositionY();
	}

	public void addPositionY(float deltaY) {
		this.positionY -= deltaY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidthImage() {
		return this.widthImage;
	}

	public int getHeightImage() {
		return this.heightImage;
	}

	public boolean repeat() {
		return this.repeat;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public Shape getBoundingBox() {
		return this.boundingBox;
	}

	public void setBoundingBox() {
		this.boundingBox = new Rectangle(positionX, positionY, width, height);
	}

	public void setBoundingBox(Shape shape) {
		this.boundingBox = shape;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible() {
		this.visible = true;
	}

	public void notVisible() {
		this.visible = false;
	}

	public Direction getDirection() {
		return this.currentDirection;
	}

	public void setDirection(Direction direction) {
		this.currentDirection = direction;
	}

	public Music getCurrentSound() {
		return currentSound;
	}

	public void setCurrentSound(String path) throws SlickException {
		if(this.currentSound != null && this.currentSound.playing()){
			return;
		}
		this.currentSound = new Music(path);
	}
	

	/********************************************************************************
	 ****************************** -ABSTRACT METHODS- ******************************
	 ********************************************************************************/
	/**
	 * Función que muestra algún grafico en la pantalla.
	 * 
	 * @param controller
	 *            Controlador que guarda las instancias de los objetos.
	 * @param g
	 *            Los graficos con los que se desea poder mostrar algo en la
	 *            pantalla.
	 * @throws SlickException
	 */
	public abstract void Render(Graphics g) throws SlickException;

	/**
	 * Función que Actualiza constantemente procesos del objeto.
	 * 
	 * @param controller
	 *            Controlador que guarda las instancias de los objetos.
	 * @param delta
	 *            Tiempo transcurrido entre frames.
	 * @throws SlickException
	 */
	public abstract void Update(int delta) throws SlickException;
}
