package com.Team.GameName.Characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Weapons.Sword;

public class Pirate1 extends Enemy{

	public enum State{
		WALKRIGHT,
		WALKLEFT,
		STANDRIGHT,
		STANDLEFT,
		ATTACKRIGHT,
		ATTACKLEFT,
		DIE
	}
	
	public Pirate1(float positionX, float positionY) throws SlickException {
		super(positionX, positionY);
	}

	@Override
	void defineStates() throws SlickException {
		Image walk = new Image("res/Enemy/Pirate1/pirate_walk_right.jpg");
		Image stand = new Image("res/Enemy/Pirate1/pirate_stand_right.jpg");
		Image scare = new Image("res/Enemy/Pirate1/pirate_scare_right.jpg");
		super.states = new Animation[7];
		//WALK
		super.states[0] = super.getAnimation(walk, 6, 1, 41, 59, 6, 50);
		super.states[1] = super.getAnimation(walk, 6, 1, 41, 59, 6, 50);
		//STAND
		super.states[2] = super.getAnimation(stand, 3, 1, 42, 59, 3, 250);
		super.states[3] = super.getAnimation(stand, 3, 1, 42, 59, 3, 250);
		//ATTACK
		super.states[4] = super.getAnimation(scare, 8, 1, 108, 140, 8, 5);
		super.states[5] = super.getAnimation(scare, 8, 1, 108, 140, 8, 5);
		//DIE
		super.states[6] = super.getAnimation(stand, 8, 1, 108, 140, 8, 50);
		super.currentAnimation = states[0];
	}

	@Override
	void attack() {
		
	}
	
	public void Update2(Controller controller, int delta, MainCharacter Monillo) throws SlickException {
		this.gravity(controller, delta);
		if (checkSight(controller,Monillo))
			chase(controller, delta, Monillo);
		else
			returnPosition(controller,delta);
	}

	@Override
	public void Update(Controller controller, int delta) throws SlickException {
		
	}

	
	@Override
	public void init() throws SlickException {
		super.width = 27;
		super.height = 35;
		super.maxVelocityX = 0.2f;
		super.maxVelocityY = 2.0f;
		this.health = 100;
		this.currentWeapon = new Sword();
	}

	@Override
	public void Render(Graphics g, Controller controller) throws SlickException {
		if(super.currentAnimation != null){
			super.currentAnimation.draw(super.positionX, super.positionY, super.width, super.height);
		}
		this.applyDamage(g);
	}
}
