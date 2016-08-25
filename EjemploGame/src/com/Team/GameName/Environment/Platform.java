package com.Team.GameName.Environment;

import com.Team.GameName.Utilities.Collision;
import com.Team.GameName.Utilities.Rigid;

public abstract class Platform extends Rigid implements Collision {
	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Platform(float positionX, float positionY, int width, int height) {
		super(positionX, positionY, width, height);
	}

	public Platform(float positionX, float positionY, int width, int height, int widthImage, int heightImage,
			boolean repeat) {
		super(positionX, positionY, width, height, widthImage, heightImage, repeat);
	}
}
