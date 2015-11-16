package com.Team.GameName.Environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.GameObject;
import com.Team.GameName.Utilities.Rigid;

public class BoxPlatform extends Rigid{
	public BoxPlatform(float positionX, float positionY, int width, int height) throws SlickException{
		super(positionX,positionY);
		super.width = width;
		super.height = height;
	}
	
	@Override
	public void init() {
		
	}
	
	public void Render(Graphics g, Controller controller) throws SlickException{
		super.boundingBox = new Rectangle(super.positionX, super.positionY, width, height);
		g.drawRect(super.positionX, super.positionY, width, height);
	}

	@Override
	public void Update(Controller controller, int delta) throws SlickException {
		
	}
}
