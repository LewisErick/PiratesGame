package com.Team.GameName.Weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.Character;
import com.Team.GameName.Characters.Enemy;
import com.Team.GameName.Characters.MainCharacter;
import com.Team.GameName.Utilities.Controller;

public class Sword extends Weapon {
	
	Enemy enemy = null;
	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Sword(float positionX, float positionY, boolean attached) throws SlickException {
		this(positionX, positionY, 5, 0.2f, attached);
	}
	
	public Sword(float positionX, float positionY, Enemy en) throws SlickException {
		super(positionX, positionY, 20, 5, 5, 0.2f);
		enemy = en;
	}

	public Sword(float positionX, float positionY, float damage, float attackTime, boolean attached)
			throws SlickException {
		super(positionX, positionY, 15, 5, damage, attackTime, attached);
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void doDamage() throws SlickException {
		Character ch = Controller.getInstance().checkCollision(this, 0, 0, Character.class);
		if(ch != null){
			if (enemy != null) {
				if (ch instanceof MainCharacter) {
					ch.takeAwayHealth(this.getDamage());
				}
			} else {
				ch.takeAwayHealth(this.getDamage());
			}
		}
	}

	@Override
	public void Render(Graphics g) throws SlickException {

	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void swing() throws SlickException {
		startTimerAttack();
		doDamage();
	}
}

