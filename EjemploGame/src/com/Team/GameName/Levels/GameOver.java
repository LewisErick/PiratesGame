package com.Team.GameName.Levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.Team.GameName.Utilities.Controller;

public class GameOver extends BasicGameState {
	private Image overBack;
	private Image contin;
	private Image got;
	private int pauseOption = 0;
	
	public GameOver(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		overBack = new Image("res/Pause/gameover.png");
		contin = new Image("res/Pause/continue_.png");
		got = new Image("res/Pause/goto.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (Game.show == 4) {
			overBack.draw(0,0);
			contin.draw(190,320);
			got.draw(200,400);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			pauseOption = pauseOption + 1;
			pauseOption = pauseOption % 2;
			if (pauseOption == 0) {
				contin = new Image("res/Pause/continue_.png");
				got = new Image("res/Pause/goto.png");
			} else {
				contin = new Image("res/Pause/continue.png");
				got = new Image("res/Pause/goto_.png");
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)) {
			if (pauseOption == 0) {
				Game.show = 1;
				Controller.getInstance().resetGame();
				sbg.enterState(Game.nivel1, new FadeOutTransition(), new FadeInTransition());
			} else {
				Game.show = 0;
				Controller.getInstance().resetGame();
				Controller.getInstance().playMusic("res/title.wav");
				sbg.enterState(Game.menu, new FadeOutTransition(), new FadeInTransition());
				return;
			}
		}
	}

	@Override
	public int getID() {
		return 4;
	}
}
