package com.Team.GameName.Characters;

import java.util.LinkedList;

import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.Character.Actions;
import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.States;
import com.Team.GameName.Weapons.Pistol;

public class Pirate2 extends Enemy {
	
	private int chaseTimer = -1;
	private float range;
	private int damage = 40;
	private int explodeState = 0;
	
	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Pirate2(float positionX, float positionY) throws SlickException {
		this(positionX, positionY, 100);
	}
	
	public Pirate2(float positionX, float positionY, float health) throws SlickException {
		super(positionX, positionY, 30, 40, 0.2f, 300, 150, health);
		setCurrentWeapon(new Pistol(0, 0, 50, true));
		setGiveScore(100);
	}
	
	public void explode() throws SlickException{
		LinkedList<Character> ch = Controller.getInstance().checkRangeList(this, this.getPositionX(), this.getPositionY(), this.range * 1.5f,Character.class);
		if(ch != null){
			for(Character object : ch){
				object.takeAwayHealth(this.damage);
			}
		}
		Controller.getInstance().add(new Explosion(this.getPositionX() - 50, this.getPositionY() - 100, 80, 128));
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Update(int delta) throws SlickException {
		if (chaseTimer > 0) {
			super.setMaxVelocityX(0.3f);
			chaseTimer -= 1;
		}
		if (chaseTimer == 0) {
			explode();
			setHealth(0);
		}
		if (super.getHealth() < 30 && chaseTimer == -1) {
			super.setAttackRange(10);
			chaseTimer = 2000;
		}
		super.Update(delta);
		enemyControl(delta);
	}
	

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void enemyControl(int delta) throws SlickException {
		MainCharacter player = checkSight();
		if (super.getShoot()) {
			player = Controller.getInstance().find(MainCharacter.class);
		}
		if (player != null) {
			if (chase(delta, player)) {
				if (this.getAttackRange() == 10 && chaseTimer > 0) {
					chaseTimer = 0;
				} else{
					if(attack()){
						changeAction(Actions.Attack);
					}else{
						changeAction(Actions.Stand);
					}
				}
			}
		} else {
			returnPosition(delta);
		}
	}
	
	@Override
	public void changeAction(Actions newAction) throws SlickException{
		if(getCurrentAnimation() != null){
			if((getCurrentAnimation() == States.ENEMYATTACK.getAnimation() || getCurrentAnimation() == States.ENEMYDIE.getAnimation()) && !getCurrentAnimation().isStopped()){
				return;
			}
			if(getCurrentAnimation().isStopped()){
				getCurrentAnimation().restart();
			}
		}
		States temp = null;
		if(newAction == Actions.Stand){
			temp = States.ENEMYSTAND;
		}else if(newAction == Actions.Walk){
			temp = States.ENEMYWALK;
		}else if(newAction == Actions.Attack){
			temp = States.ENEMYATTACK;
		}else if(newAction == Actions.Die){
			temp = States.ENEMYDIE;
		}else if(newAction == Actions.Explode){
			temp = States.ENEMYEXPLOSION;
		}
		setCurrentAnimation(temp.getAnimation());
	}
}
