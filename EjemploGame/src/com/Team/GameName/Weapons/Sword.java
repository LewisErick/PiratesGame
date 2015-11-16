package com.Team.GameName.Weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Utilities.Controller;

public class Sword extends Weapon{
	public Sword() throws SlickException {
		super();
	}

	public void swing(){
		currentTime = attackInterval;
	}

	@Override
	public void init() throws SlickException {
		
	}

	@Override
	public void Render(Graphics g, Controller controller) throws SlickException {
		
	}

	@Override
	public void Update(Controller controller, int delta) throws SlickException {
		if(currentTime >= 0){
			currentTime -= delta / 1000f;
		}
	}
	
}
