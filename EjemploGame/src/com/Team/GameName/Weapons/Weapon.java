package com.Team.GameName.Weapons;

import org.newdawn.slick.SlickException;

import com.Team.GameName.Utilities.GameObject;
import com.Team.GameName.Utilities.States;

public abstract class Weapon extends GameObject {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private boolean attached = false;
	private float damage;
	private float attackTime;
	private float timerAttack = 0;

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Weapon(float positionX, float positionY, int width, int height) throws SlickException {
		super(positionX, positionY, width, height);
	}

	public Weapon(float positionX, float positionY, int width, int height, float damage, float attackInterval,
			boolean attached) throws SlickException {
		super(positionX, positionY, width, height);
		this.damage = damage;
		this.attackTime = attackInterval;
		this.attached = attached;
	}

	public Weapon(float positionX, float positionY, int width, int height, float damage, float attackInterval)
			throws SlickException {
		this(positionX, positionY, width, height, damage, attackInterval, false);
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Update(int delta) throws SlickException {
		if (!attached) {
			super.Update(delta);
		}
		if (timerAttack >= 0) {
			timerAttack -= delta / 1000f;
		}
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	/**
	 * Reduces the life of the character according to the damage of the weapon
	 */
	public abstract void doDamage() throws SlickException;

	/**
	 * Takes the attack interval and reduces it to zero, to not allow the player
	 * to attack for a period of time
	 */
	public boolean canAttack() {
		return timerAttack <= 0;
	}

	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public boolean isAttached() {
		return attached;
	}

	public void setAttached() {
		this.attached = true;
	}

	public void notAttached() {
		this.attached = false;
	}

	public float getDamage() {
		return damage;
	}
	

	public void setDamage(float damage) {
		this.damage = damage;
	}
	

	public float getTimerAttack() {
		return timerAttack;
	}

	public void startTimerAttack() {
		this.timerAttack = attackTime;
	}

	public float getAttackTime() {
		return attackTime;
	}
	

	public void setAttackTime(float attackInterval) {
		this.attackTime = attackInterval;
	}

}