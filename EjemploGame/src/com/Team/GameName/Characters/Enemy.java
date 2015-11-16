package com.Team.GameName.Characters;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.Pirate1.State;
import com.Team.GameName.Utilities.Controller;

public abstract class Enemy extends Character{
	
	protected float x;
	
	public Enemy(float positionX, float positionY) throws SlickException {
		super(positionX, positionY);
		x = super.getPositionX();
	}
	
	@Override
	void applyDamage(Graphics g) throws SlickException {
		
	}
	
	public boolean checkSight(Controller controller, MainCharacter mc){
		boolean OnSight = controller.doRayCast(this, positionX, positionY, 200, MainCharacter.class);
		boolean CheckRange = controller.checkRange(mc, positionX, positionY, 20);
		if (OnSight && CheckRange)
		{
			return true;
		}
		return false;
	}
	
	public void chase(Controller controller, float delta, MainCharacter Monillo) throws SlickException{
		if(controller.getRange(this,Monillo) > 35)
		{
			if(Monillo.getPositionX() < this.positionX){
				this.move(controller, delta, Direction.Left);
				setAnimation(State.WALKLEFT);
			}
			if(Monillo.getPositionX() > this.positionX){
				this.move(controller, delta, Direction.Right);
				setAnimation(State.WALKRIGHT);
			}
		}
	}
	
	public void returnPosition(Controller controller, float delta) throws SlickException{
		if(Math.abs(x - this.positionX) < 5){
			this.move(controller, delta, Direction.Right);
			setAnimation(State.WALKRIGHT);
		}
		if(Math.abs(x - this.positionX) > 5){
			this.move(controller, delta, Direction.Left);
			setAnimation(State.WALKLEFT);
		}
	}
	
	protected void setAnimation(State state){
		super.currentAnimation = super.states[state.ordinal()];
	}
	
	public void dead(){
		
	}
}
