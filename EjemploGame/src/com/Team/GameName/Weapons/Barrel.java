package com.Team.GameName.Weapons;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.Character;
import com.Team.GameName.Characters.Explosion;
import com.Team.GameName.Utilities.ASound;
import com.Team.GameName.Utilities.Controller;

public class Barrel extends Weapon {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private float range = 80;
	
	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Barrel(float positionX, float positionY) throws SlickException {
		this(positionX, positionY, 50, 80);
	}
	
	public Barrel(float positionX, float positionY, float damage, float range) throws SlickException {
		super(positionX, positionY, 40, 25, damage, 1f);
		this.range = range;
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void doDamage() throws SlickException {
		LinkedList<Character> ch = Controller.getInstance().checkRangeList(this, getPositionX(), getPositionY(), range,Character.class);
		for(Character object : ch){
			object.takeAwayHealth(getDamage());
		}
		explode();
	}

	@Override
	public void Render(Graphics g) throws SlickException {
		Image pist = new Image("res/barrel.png");
		pist.draw(getRealPositionX() + 5, getRealPositionY() - 35);
	}
	
	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void explode() throws SlickException  {
		Controller.getInstance().addSound("res/Explosion.wav", 10, this.getPositionX(), this.getPositionY());
		Controller.getInstance().remove(this);
		Controller.getInstance().add(new Explosion(this.getPositionX(), this.getPositionY() - 100, 80, 128));
	}

	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}
	
}