package com.Team.GameName.Levels;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{

	public static final String gameName = "The Pirate Bay";
	public static final int menu = 0;
	public static final int nivel1 = 1;
	public static final int nivel2 = 2;
	public static final int nivel3 = 3;
	public static final int over = 4;
	public static final int intro = 5;
	public static final int width = 800;
	public static final int height = 600;
	public static int show = 5;
	public static int enemyCount = 0;
	
	public Game(String gameName){
		super(gameName);
		addState(new Menu(menu, this));
		addState(new Level1(nivel1));
		addState(new Level2(nivel2));
		addState(new Level3(nivel3));
		addState(new GameOver(over));
		addState(new IntroScene(intro));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		enterState(intro);
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer appgc;
		try{
			appgc = new AppGameContainer(new Game(gameName));
			appgc.setDisplayMode(width, height, false);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
}
