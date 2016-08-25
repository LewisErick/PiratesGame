package com.Team.GameName.Levels;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.Team.GameName.Utilities.Controller;

import org.newdawn.slick.Input;

public class Menu extends BasicGameState{
	
	private Image land;
	private Image board;
	private Image enter;
	
	public Menu(int state, Game game){
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Controller.getInstance().playMusic("res/title.wav");
		land = new Image("res/TitleScreen/title.jpg");
		board = new Image("res/TitleScreen/titlesign.png");
		enter = new Image("res/TitleScreen/titlenter.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		System.out.println(Game.show);
		if (Game.show == 0) {
			land.draw(0,0);
			board.draw(150,310);
			enter.draw(0,425);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (Game.show != 0) {
			return;
		}
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			Game.show = 1;
			Controller.getInstance().playMusic("res/level1.wav");
			sbg.enterState(Game.nivel1, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
