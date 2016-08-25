package com.Team.GameName.Characters;

import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.Character.Actions;
import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.States;
import com.Team.GameName.Weapons.Pistol;
import com.Team.GameName.Weapons.Sword;

public class Pirate1 extends Enemy {
	
	private boolean lowHP = false;
	
	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Pirate1(float positionX, float positionY) throws SlickException {
		this(positionX, positionY, 100);
	}
	
	public Pirate1(float positionX, float positionY, float health) throws SlickException {
		super(positionX, positionY, 40, 50, 0.2f, 300, 50, health);
		setCurrentWeapon(new Sword(0, 0, this));
		setGiveScore(100);
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Update(int delta) throws SlickException {
		if (super.getHealth() < 30) {
			if (lowHP == false) {
				super.getCurrentWeapon().setDamage(super.getCurrentWeapon().getDamage() * 2f);
				lowHP = true;
			}
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
			if(chase(delta, player)){
				if(attack()){
					changeAction(Actions.Attack);
				}else{
					changeAction(Actions.Stand);
				}
			}
		} else {
			returnPosition(delta);
		}
	}
	
	@Override
	public void changeAction(Actions newAction) throws SlickException{
		if(getCurrentAnimation() != null){
			if(getCurrentAnimation() == States.ENEMY2ATTACK.getAnimation() && !getCurrentAnimation().isStopped()){
				return;
			}
			if(getCurrentAnimation().isStopped()){
				getCurrentAnimation().restart();
			}
		}
		States temp = null;
		if(newAction == Actions.Stand){
			temp = States.ENEMY2STAND;
		}else if(newAction == Actions.Walk){
			temp = States.ENEMY2WALK;
		}else if(newAction == Actions.Attack){
			temp = States.ENEMY2ATTACK;
		}
		setCurrentAnimation(temp.getAnimation());
	}
}
