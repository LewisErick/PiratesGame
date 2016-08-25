package com.Team.GameName.Environment;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BoxPlatform extends Platform {
	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public BoxPlatform(float positionX, float positionY, int width, int height) throws SlickException {
		super(positionX, positionY, width, height);
	}

	public BoxPlatform(float positionX, float positionY, int width, int height, int widthImage, int heightImage,
			boolean repeat) {
		super(positionX, positionY, width, height, widthImage, heightImage, repeat);
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Update(int delta) throws SlickException {

	}

	@Override
	public void Render(Graphics g) throws SlickException {

	}
}
