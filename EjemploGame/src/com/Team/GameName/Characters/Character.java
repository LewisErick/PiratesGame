package com.Team.GameName.Characters;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.GameObject;
import com.Team.GameName.Weapons.Pistol;
import com.Team.GameName.Weapons.Sword;
import com.Team.GameName.Weapons.Weapon;

public abstract class Character extends GameObject {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private final float maxHealth;
	private float currentHealth;
	private Weapon currentWeapon;
	
	public enum Actions{
		Stand,
		Walk,
		Attack,
		Die,
		Explode
	}

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Character(float positionX, float positionY) {
		super(positionX, positionY);
		this.maxHealth = 100;
		this.currentHealth = 100;
	}

	public Character(float positionX, float positionY, int width, int height, float maxVelocityX) {
		this(positionX, positionY, width, height, maxVelocityX, 100);
	}

	public Character(float positionX, float positionY, int width, int height, float maxVelocityX, float health) {
		super(positionX, positionY, width, height, maxVelocityX);
		this.maxHealth = health;
		this.currentHealth = health;
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Render(Graphics g) throws SlickException {
		drawUtilities(g);
		drawCurrentAnimation();
	}

	@Override
	public void Update(int delta) throws SlickException {
		super.Update(delta);
		checkDead();
		updateWeaponPosition();
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void takeAwayHealth(float damage) {
		this.currentHealth -= damage;
		if (this.currentHealth < 0) {
			this.currentHealth = 0;
		}
	}

	public void addHealth(float moreHealth) {
		currentHealth += moreHealth;
		if (currentHealth > maxHealth) {
			currentHealth = maxHealth;
		}
	}

	public boolean attack() throws SlickException {
		if (getCurrentWeapon() != null) {
			if (getCurrentWeapon().canAttack()) {
				if (getCurrentWeapon() instanceof Sword) {
					((Sword) getCurrentWeapon()).swing();
				} else if (getCurrentWeapon() instanceof Pistol) {
					((Pistol) getCurrentWeapon()).shoot();
				}
				changeAction(Actions.Attack);
				return true;
			}
		}
		changeAction(Actions.Stand);
		return false;
	}
	
	@Override
	public boolean moveTo(int delta, float positionX, float range) throws SlickException {
		if(!super.moveTo(delta, positionX, range)){
			changeAction(Actions.Stand);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean move(int delta) throws SlickException{
		if(super.move(delta)){
			changeAction(Actions.Walk);
			return true;
		}else{
			stand();
			return false;
		}
	}
	
	public void stand() throws SlickException {
		resetCurrentVelocityX();
		changeAction(Actions.Stand);
	}
	
	public void setHealth(float health) {
		currentHealth = health;
	}

	private void drawCurrentAnimation() {
		if (getCurrentAnimation() != null) {
			if (getDirection() == Direction.Right) {
				getCurrentAnimation().draw(getRealPositionX(), getRealPositionY(), getWidth(), getHeight());
			} else {
				getCurrentAnimation().draw(getRealPositionX() + getWidth(), getRealPositionY(), -getWidth(),
						getHeight());
			}
		}
	}

	public void drawHealth(Graphics g, float positionX, float positionY, float width, float height, int radius,
			Color backgroundColor, Color healthColor, boolean backgroundFill) {
		g.setColor(backgroundColor);
		if (backgroundFill) {
			g.fillRoundRect(positionX, positionY, width, height, radius);
		} else {
			g.drawRoundRect(positionX, positionY, width, height, radius);
		}
		g.setColor(healthColor);
		g.fillRoundRect(positionX, positionY, getHealth() * width / getMaxHealth(), height, radius);
	}

	private void updateWeaponPosition() {
		if (getCurrentWeapon() != null) {
			if (getDirection() == Direction.Right) {
				getCurrentWeapon().setPositionX(getPositionX() + getWidth() + 2);
				getCurrentWeapon().setDirection(Direction.Right);
			} else {
				getCurrentWeapon().setPositionX(getPositionX() - getCurrentWeapon().getWidth() - 4);
				getCurrentWeapon().setDirection(Direction.Left);
			}
			getCurrentWeapon().setPositionY(getPositionY() + getHeight() / 2.0f);
		}
	}

	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public Weapon getCurrentWeapon() {
		return this.currentWeapon;
	}

	public void setCurrentWeapon(Weapon weapon) {
		if(this.currentWeapon != null){
			this.currentWeapon.notVisible();
		}
		Controller.getInstance().add(weapon);
		weapon.setVisible();
		this.currentWeapon = weapon;
	}

	public float getHealth() {
		return currentHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	/********************************************************************************
	 ****************************** -ABSTRACT METHODS- ******************************
	 ********************************************************************************/
	abstract void drawUtilities(Graphics g) throws SlickException;

	abstract boolean checkDead();
	
	abstract void changeAction(Actions newAction) throws SlickException;
}
