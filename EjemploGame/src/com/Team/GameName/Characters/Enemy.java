package com.Team.GameName.Characters;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Levels.Game;
import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.States;
import com.Team.GameName.Weapons.Pistol;
import com.Team.GameName.Weapons.Sword;;

public abstract class Enemy extends Character {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private float initialPositionX;
	private float visionRange;
	private float attackRange;
	private boolean shootChase = false;
	private int patternTimer = 0;
	private int patternDir;
	private float variance;
	private int giveScore;

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Enemy(float positionX, float positionY, int width, int height, float maxVelocityX) throws SlickException {
		super(positionX, positionY, width, height, maxVelocityX);
		this.initialPositionX = getPositionX();
	}

	public Enemy(float positionX, float positionY, int width, int height, float maxVelocityX, float visionRange,
			float attackRange) throws SlickException {
		super(positionX, positionY, width, height, maxVelocityX);
		this.initialPositionX = getPositionX();
		this.visionRange = visionRange;
		this.attackRange = attackRange;
	}

	public Enemy(float positionX, float positionY, int width, int height, float maxVelocityX, float visionRange,
			float attackRange, float health) throws SlickException {
		super(positionX, positionY, width, height, maxVelocityX, health);
		this.initialPositionX = getPositionX();
		this.visionRange = visionRange;
		this.attackRange = attackRange;
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void drawUtilities(Graphics g) throws SlickException {
		drawHealth(g, getRealPositionX() + 5, getRealPositionY() - 11, 25, 6, 2, Color.red, Color.green, false);
	}
	
	public void takeAwayHealth(float damage){
		shootChase = true;
		super.takeAwayHealth(damage);
	}

	@Override
	public boolean checkDead() {
		if (getHealth() <= 0) {
			if (getCurrentWeapon() instanceof Pistol) {
				((Pistol) getCurrentWeapon()).notAttached();
			} else if (getCurrentWeapon() instanceof Sword) {
				Controller.getInstance().remove(getCurrentWeapon());
			}
			Controller.getInstance().addScore(giveScore);
			Game.enemyCount = Game.enemyCount - 1;
			System.out.println(Game.enemyCount);
			Controller.getInstance().remove(this);
			return true;
		}
		return false;
	}
	
	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public MainCharacter checkSight() {
		MainCharacter playerCast = Controller.getInstance().doRayCast(this, visionRange, 400, MainCharacter.class);
		MainCharacter playerRange = Controller.getInstance().checkRange(this, visionRange, MainCharacter.class);
		if (playerCast != null && playerRange != null && playerCast == playerRange)
			return playerCast;
		else
			return null;
	}

	public boolean chase(int delta, Character character) throws SlickException {
		patternTimer = 0;
		if (Controller.getInstance().getRange(this, character) > attackRange) {
			if (character.getPositionX() > getPositionX()) {
				setDirection(Direction.Right);
				move(delta);
			} else if (character.getPositionX() < getPositionX()) {
				setDirection(Direction.Left);
				move(delta);
			} else {
				stand();
			}
			return false;
		} else {
			return true;
		}
	}

	public boolean returnPosition(int delta) throws SlickException {
		variance = 1;
		if (patternTimer == 0) {
			if(!moveTo(delta, this.getInitialPositionX(), 6)){
				return false;
			}else{
				resetCurrentVelocityX();
				patternTimer = 5000;
				Random rand = new Random();
			    int randomNum = rand.nextInt((1 - 0) + 1) + 0;
				patternDir = randomNum - 1;
				if (patternDir == 0) {
					patternDir = 1;
				}
				return true;
			}
		} else {
			if (patternTimer > 0  && patternTimer <= 2000) {
				if (!moveTo(delta, this.getInitialPositionX() + (150 * patternDir * variance), 6)) {
					changeAction(Actions.Walk);
				} else {
					resetCurrentVelocityX();
					if (patternDir == -1) {
						super.setDirection(Direction.Left);
					}
					changeAction(Actions.Stand);
				}
			}
			patternTimer = patternTimer - 1;
			return true;
		}
	}

	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public boolean getShoot() {
		return shootChase;
	}
	
	public float getInitialPositionX() {
		return initialPositionX;
	}

	public void setInitialPositionX(float initialPositionX) {
		this.initialPositionX = initialPositionX;
	}

	public float getVisionRange() {
		return visionRange;
	}

	public void setVisionRange(float visionRange) {
		this.visionRange = visionRange;
	}

	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}

	public void setGiveScore(int score) {
		this.giveScore = score;
	}
}
