package com.Team.GameName.Characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.MainCharacter.State;
import com.Team.GameName.Utilities.GameObject;
import com.Team.GameName.Weapons.Weapon;

public abstract class Character extends GameObject{
	
	protected float health;
	protected Weapon currentWeapon;
	protected int currentState;
	protected Animation[] states;
	
	//CONSTRUCTORS
	public Character() throws SlickException {
		super();
		defineStates();
	}
	
	public Character(float positionX, float positionY) throws SlickException {
		super(positionX, positionY);
		defineStates();
	}
	
	public Character(float positionX, float positionY, int width, int height) throws SlickException {
		super(positionX, positionY, width, height);
		defineStates();
	}
	
	//GETTERS AND SETTERS
	public Weapon getCurrentWeapon(){
		return this.currentWeapon;
	}
	
	public void setCurrentWeapon(Weapon weapon){
		this.currentWeapon = weapon;
	}
	
	protected Animation getAnimation(State state){
		return states[state.ordinal()];
	}
	
	protected void setAnimation(State state){
		this.currentAnimation = this.states[state.ordinal()];
	}
	
	//ABSTRACT METHODS
	abstract void defineStates() throws SlickException;
	
	abstract void attack();
	
	abstract void applyDamage(Graphics g) throws SlickException;
}
