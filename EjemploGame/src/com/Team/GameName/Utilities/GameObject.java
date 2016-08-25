package com.Team.GameName.Utilities;

import org.newdawn.slick.SlickException;

import com.Team.GameName.Environment.TrianglePlatform;

public abstract class GameObject extends Rigid {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private float maxVelocityX;
	private float currentVelocityX = 0;
	private float initialVelocityY = 0;
	private float timerGravity = 0;
	private boolean inGround;
	private static final float aceleration = 9.8f;

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public GameObject(float positionX, float positionY) {
		super(positionX, positionY);
	}

	public GameObject(float positionX, float positionY, int width, int height) {
		super(positionX, positionY, width, height);
	}

	public GameObject(float positionX, float positionY, int width, int height, float maxVelocityX) {
		super(positionX, positionY, width, height);
		this.maxVelocityX = maxVelocityX;
	}

	public GameObject(float positionX, float positionY, int width, int height, int widthImage, int heightImage,
			boolean repeat, float maxVelocityX) {
		super(positionX, positionY, width, height, widthImage, heightImage, repeat);
		this.maxVelocityX = maxVelocityX;
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Update(int delta) throws SlickException {
		fall(delta);
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public boolean move(int delta) throws SlickException {
		if (currentVelocityX < maxVelocityX) {
			currentVelocityX += 0.001f * (Math.pow(2, currentVelocityX));
		}
		float deltaPosition = (getDirection() == Direction.Right ? 1 : -1) * Math.abs(delta) * currentVelocityX;
		Rigid collision = (Rigid) Controller.getInstance().checkCollision(this, deltaPosition, 0, Collision.class);
		if (collision == null) {
			addPositionX(deltaPosition);
		} else {
			if (collision instanceof TrianglePlatform) {
				float angle = ((TrianglePlatform) collision).getAngle();
				float deltaX = deltaPosition * (float) Math.sin(angle);
				float deltaY = Math.abs(deltaPosition * (float) Math.cos(angle));
				addPositionX(deltaX);
				addPositionY(deltaY);
				return true;
			} else {
				currentVelocityX = 0;
			}
			return false;
		}
		return true;
	}

	public boolean moveTo(int delta, float positionX, float range) throws SlickException {
		float deltaX = positionX - getPositionX();
		if (Math.abs(deltaX) < range) {
			return true;
		} else {
			setDirection(deltaX > 0 ? Direction.Right : Direction.Left);
			move(delta);
			return false;
		}
	}

	public boolean fall(int delta) throws SlickException {
		float deltaY = initialVelocityY * timerGravity - aceleration * timerGravity * timerGravity / 2;
		if (inGround = Controller.getInstance().checkCollision(this, 0f, -deltaY, Collision.class) != null) {
			timerGravity = 0.05f;
			initialVelocityY = 0;
		} else {
			timerGravity += delta / 1000f;
			addPositionY(deltaY);
		}
		return inGround;
	}

	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public boolean isInGround() {
		return inGround;
	}

	public float getInicialVelocityY() {
		return initialVelocityY;
	}

	public void setInicialVelocityY(float inicialVelocityY) {
		this.initialVelocityY = inicialVelocityY;
	}

	public float getMaxVelocityX() {
		return maxVelocityX;
	}

	public void setMaxVelocityX(float maxVelocityX) {
		this.maxVelocityX = maxVelocityX;
	}

	public float getCurrentVelocityX() {
		return currentVelocityX;
	}

	public void resetCurrentVelocityX() {
		this.currentVelocityX = 0;
	}
}
