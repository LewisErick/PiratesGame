package com.Team.GameName.Characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Weapons.Sword;
import com.Team.GameName.Weapons.Weapon;

public class MainCharacter extends Character{
	
	public enum State{
		WALK,
		STAND,
		ATTACK,
		DIE
	}
	private float jumpVelocity;
	
	public MainCharacter(float positionX, float positionY) throws SlickException{
		super(positionX, positionY);
	}
	
	public void moverse(Controller controller, GameContainer gc, float delta) throws SlickException{
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_W)){
			this.jump();
		}else if(input.isKeyPressed(Input.KEY_SPACE)){ 
			if(super.currentWeapon.canAttack()){
				System.out.println("laa");
				this.attack(controller);
				super.setAnimation(State.ATTACK);
			}
		}else if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_D)){
			
			if(input.isKeyDown(Input.KEY_A)){
				this.move(controller, delta, Direction.Left);
				currentDirection = Direction.Left;
			}
			if(input.isKeyDown(Input.KEY_D)){
				this.move(controller, delta, Direction.Right);
				currentDirection = Direction.Right;
			}
			super.setAnimation(State.WALK);
			
		}else{
			super.setAnimation(State.STAND);
		}
		this.gravity(controller, delta);
	}
	
	public void jump(){
		super.inicialVelocityY = jumpVelocity;
	}
	
	@Override
	public void init() throws SlickException {
		super.width = 27;
		super.height = 35;
		super.maxVelocityX = 0.3f;
		super.maxVelocityY = 2.0f;
		this.jumpVelocity = 2f;
		this.health = 100;
		this.currentWeapon = new Sword();
	}

	@Override
	public void Render(Graphics g, Controller controller) throws SlickException {
		if(super.currentAnimation != null){
			if(currentDirection == Direction.Right)
				super.currentAnimation.draw(super.positionX, super.positionY, super.width, super.height);
			else
				super.currentAnimation.draw(super.positionX+super.width, super.positionY, -super.width, super.height);
		}
		g.drawRect(positionX, positionY, width, height);
		this.applyDamage(g);
	}
	
	@Override
	public void Update(Controller controller, int delta) throws SlickException{
		super.currentAnimation.update(delta);
		super.boundingBox = new Rectangle(positionX, positionY, super.width, super.height);
		
		if(currentDirection == Direction.Right){
			super.currentWeapon.setPositionX(this.positionX+this.width+2);
			super.currentWeapon.setDirection(Direction.Right);
		}else{
			super.currentWeapon.setPositionX(this.positionX-4);
			super.currentWeapon.setDirection(Direction.Left);
		}
		super.currentWeapon.setPositionY(this.positionY+(this.height/2.0f));
	}

	@Override
	public void defineStates() throws SlickException {
		Image walk = new Image("res/Enemy/Pirate1/pirate_walk_right.jpg");
		Image stand = new Image("res/Enemy/Pirate1/pirate_stand_right.jpg");
		Image scare = new Image("res/Enemy/Pirate1/pirate_scare_right.jpg");
		super.states = new Animation[4];
		//WALK
		super.states[0] = super.getAnimation(walk, 6, 1, 41, 59, 6, 50);
		//STAND
		super.states[1] = super.getAnimation(stand, 3, 1, 42, 59, 3, 250);
		//ATTACK
		super.states[2] = super.getAnimation(scare, 8, 1, 108, 140, 8, 5);
		//DIE
		super.states[3] = super.getAnimation(stand, 8, 1, 108, 140, 8, 50);
		super.currentAnimation = states[0];
	}

	
	@Override
	public void attack(Controller controller) throws SlickException {
		((Sword)super.currentWeapon).swing(controller);
	}
	

	@Override
	public void applyDamage(Graphics g) throws SlickException{
		g.setColor(Color.red);
		g.drawRect(super.positionX + 5, super.positionY - 11, 21, 6);
		g.setColor(Color.green);
		g.fillRect(super.positionX + 5, super.positionY - 10, (float) (super.health * 0.2), 5);
		g.setColor(Color.white);
	}

}
