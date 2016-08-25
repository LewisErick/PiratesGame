package com.Team.GameName.Levels;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.Team.GameName.Levels.Game;
import com.Team.GameName.Utilities.Controller;

public class IntroScene extends BasicGameState{
	
	private Image land;
	private Image board;
	private Image enter;
	
	private int sceneIndex = -1;
	private String sceneText;
	
	private float textAlpha = 0f;
	private float imageAlpha = 0f;
	
	private int sceneTimer = 0;
	
	public IntroScene(int state){
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//Controller.getInstance().playMusic("res/title.ogg");
		land = new Image("res/Scenes/escena1.png");
		board = new Image("res/TitleScreen/titlesign.png");
		enter = new Image("res/TitleScreen/titlenter.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (Game.show != 5) {
			return;
		}
		Input input = gc.getInput();
		gc.setShowFPS(false);
		if(input.isKeyPressed(Input.KEY_SPACE)){
			Game.show = 0;
			//Controller.getInstance().playMusic("res/level1.ogg");
			sbg.enterState(Game.menu, new FadeOutTransition(), new FadeInTransition());
		}
		if (sceneTimer == 0) {
			System.out.println(sceneIndex);
			if (sceneIndex == -1) {
				land = new Image("res/Scenes/escena0.png");
				sceneText = "BACKGROUND CREDITS: QUINIT THE "
						+ "\nVISUAL AND AUDIO RESOURCES WERE"
						+ "\nNOT MADE BY THE TEAM AND CREDIT "
						+ "\nGOES TO THEIR CORRESPONDING CREATORS";
			} else if (sceneIndex == 0) {
				land = new Image("res/Scenes/escena3.png");
				sceneText = "Long ago, there was a pirate like anyone that loved\n to sail the seas...";
			} else if (sceneIndex == 1) {
				land = new Image("res/Scenes/escena1.png");
				sceneText = "However, one day a evil pirate attacked the ship\n and the captain's crew turned against him.";
			} else if (sceneIndex == 2) {
				land = new Image("res/Scenes/escena2.png");
				sceneText = "The pirate was left alone without his ship\n and his crew.";
			} else if (sceneIndex == 3) {
				land = new Image("res/Scenes/escena4.png");
				sceneText = "But he's determined to have his revenge.";
			} else {
				Game.show = 0;
				//Controller.getInstance().playMusic("res/level1.ogg");
				sbg.enterState(Game.menu, new FadeOutTransition(), new FadeInTransition());
				return;
			}
		}
		if (sceneTimer >= 30 && sceneTimer <= 300) {
			if (sceneTimer <= 70) {
				imageAlpha = imageAlpha + 0.05f;
			}
			if (imageAlpha > 1) {
				imageAlpha = 1;
			}
			land.setAlpha(imageAlpha);
			land.draw(0,0);
			//board.draw(150,310);
			//enter.draw(0,425);
			UnicodeFont font = new UnicodeFont("res/BerkshireSwash-Regular.ttf", 25, false, false);

			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
			
			g.setColor(new Color(0, 0, 0, 200));
			g.setFont(font);
			g.drawString("Press Space to Continue.", 245, 550);
			
			if (sceneTimer >= 70 && sceneTimer <= 170) {
				textAlpha = textAlpha + 0.1f;
				
				if (textAlpha > 1) {
					textAlpha = 1;
				}
				
				if (sceneIndex == -1) {
					g.setColor(new Color(0, 0, 0, textAlpha));
					g.setFont(font);
					g.drawString(sceneText, 130, 220);
				} else {
					g.setColor(new Color(0, 0, 0, textAlpha));
					g.setFont(font);
					g.drawString(sceneText, 130, 420);
				}
			}
			
			if (sceneTimer >= 170 && sceneTimer <= 200) {
				textAlpha = textAlpha - 0.1f;
				if (textAlpha < 0) {
					textAlpha = 0;
				}
				if (sceneIndex == -1) {
					g.setColor(new Color(0, 0, 0, textAlpha));
					g.setFont(font);
					g.drawString(sceneText, 130, 220);
				}
				else {
					g.setColor(new Color(0, 0, 0, textAlpha));
					g.setFont(font);
					g.drawString(sceneText, 130, 420);
				}
			}
			
			if (sceneTimer > 200) {
				imageAlpha = imageAlpha - 0.1f;
				if (imageAlpha < 0) {
					imageAlpha = 0;
				}
			}
		}
		if (sceneTimer == 250) {
			sceneTimer = 0;
			sceneIndex = sceneIndex + 1;
		} else {
			sceneTimer = sceneTimer + 1;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
	}

	@Override
	public int getID() {
		return 5;
	}

}
