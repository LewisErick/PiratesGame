package com.Team.GameName.Environment;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class TrianglePlatform extends Platform {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private float angle;
	private Side side;

	public enum Side {
		RIGHT, LEFT, DOWNRIGHT, DOWNLEFT
	}

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public TrianglePlatform(float positionX, float positionY, int width, int height, Side side) throws SlickException {
		super(positionX, positionY, width, height);
		this.angle = (float) Math.abs(Math.atan2(width, height));
		this.side = side;
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Render(Graphics g) throws SlickException {
		float[] points = new float[6];
		switch (side) {
		case DOWNLEFT:
			break;
		case DOWNRIGHT:
			break;
		case LEFT:
			points = new float[] { getPositionX(), getPositionY(), getPositionX(), getPositionY() + getHeight(),
					getPositionX() + getWidth(), getPositionY() + getHeight() };
			break;
		case RIGHT:
			points = new float[] { getPositionX() + getWidth(), getPositionY(), getPositionX(),
					getPositionY() + getHeight(), getPositionX() + getWidth(), getPositionY() + getHeight() };
			break;
		}
		setBoundingBox(new Polygon(points));
	}

	@Override
	public void Update(int delta) throws SlickException {

	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public float getAngle() {
		return (float) (angle * 180 / Math.PI);
	}

}
