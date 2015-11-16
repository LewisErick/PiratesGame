package com.Team.GameName.Weapons;

import org.newdawn.slick.SlickException;

import com.Team.GameName.Utilities.Rigid;

public abstract class Weapon extends Rigid{

	protected float attackInterval = 0.2f;
	protected float currentTime = 0;
	
	public Weapon() throws SlickException {
		super();
	}
	
	public boolean canAttack() {
		return currentTime <= 0;
	}

}
