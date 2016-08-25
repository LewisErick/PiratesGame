package com.Team.GameName.Characters;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.Character.Actions;
import com.Team.GameName.Levels.Game;
import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.States;
import com.Team.GameName.Weapons.Pistol;
import com.Team.GameName.Weapons.Sword;

public class Explosion extends Enemy {
	
	private int explosionTimer = 600;
	
	public Explosion(float positionX, float positionY, int width, int height) throws SlickException {
		super(positionX, positionY, width, height, 0);
		States temp = null;
		temp = States.ENEMYEXPLOSION;
		setCurrentAnimation(temp.getAnimation());
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Update(int delta) throws SlickException {
		if (explosionTimer == 0) {
			if (getCurrentWeapon() instanceof Pistol) {
				((Pistol) getCurrentWeapon()).notAttached();
			} else if (getCurrentWeapon() instanceof Sword) {
				Controller.getInstance().remove(getCurrentWeapon());
			}
			Controller.getInstance().remove(this);
		} else {
			explosionTimer = explosionTimer - 1;
		}
		super.Update(delta);
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/

	@Override
	void changeAction(Actions newAction) throws SlickException {
		// TODO Auto-generated method stub
		
	}
}
