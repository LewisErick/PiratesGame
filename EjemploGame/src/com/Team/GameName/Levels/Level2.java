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

import com.Team.GameName.Characters.Enemy;
import com.Team.GameName.Characters.MainCharacter;
import com.Team.GameName.Characters.Pirate1;
import com.Team.GameName.Characters.Pirate2;
import com.Team.GameName.Environment.BoxPlatform;
import com.Team.GameName.Environment.Platform;
import com.Team.GameName.Environment.TrianglePlatform;
import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.Rigid;
import com.Team.GameName.Weapons.Barrel;
import com.Team.GameName.Weapons.Weapon;

public class Level2 extends BasicGameState {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	MainCharacter player;
	private GameContainer gameContainer; 
	private StateBasedGame stateBasedGame;
	/*
	 * LinkedList<Platform> platforms = new LinkedList<Platform>();
	 * LinkedList<Weapon> weapons = new LinkedList<Weapon>(); LinkedList<Enemy>
	 * enemies = new LinkedList<Enemy>();
	 */
	Rigid barco;
	boolean primera = true;
	boolean show = false;
	private boolean pause = false;
	private int pauseOption = 0;
	
	private Image got;
	private Image contin;
	private Image back;
	// Music sound;

	public Level2(int state) {

	}
	
	public GameContainer getGameContainer() {
		return gameContainer;
	}
	
	public StateBasedGame getStateBasedGame() {
		return stateBasedGame;
	}
	
	public void show(GameContainer gc, StateBasedGame sbg) throws SlickException {
		show = true;
		gc.setShowFPS(false);
		player = new MainCharacter(30, 0);
		Controller.getInstance().add(player);
		Controller.getInstance().setController(2960, 1000);
		if (primera) {
			primera = false;
			addEnemies(
					new Pirate1(700, 140), 
					new Pirate1(770, 140), 
					new Pirate1(1300, 20), 
					new Pirate1(1700, 20), 
					new Pirate1(2200, 20), 
					new Pirate1(2500, 20)
			);
			
			addPlatforms(
				new BoxPlatform(-20, 0, 20, 1900),
				new BoxPlatform(0, 210, 770, 200),
				new TrianglePlatform(770, 210, 100, 73, TrianglePlatform.Side.LEFT),
				new BoxPlatform(870, 280, 300, 200),
				new BoxPlatform(-20, 0, 20, 1900), 
				new BoxPlatform(1170, 430, 470, 200),
				new BoxPlatform(-20,0,20,1900),
				new BoxPlatform(1640, 370, 400, 73),
				new TrianglePlatform(2040, 310, 100, 73, TrianglePlatform.Side.RIGHT),
				new BoxPlatform(-20,0,20,1900),
				new BoxPlatform(2140, 310, 640, 73),
				new BoxPlatform(-20,0,20,1900),
				new BoxPlatform(2780, 220, 580, 83),
				new BoxPlatform(3360, 0, 20, 1900), 
				new BoxPlatform(0, 210, 770, 200)
			);
			
			addWeapons(new Barrel(680, 140), new Barrel(1400,20), new Barrel(1800,20), new Barrel(2400,20));

			barco = new Rigid(-40, 0) {
				Image im = new Image("res/Environment/Level2.png");

				@Override
				public void Render(Graphics g) throws SlickException {
					Image la = new Image("res/Environment/fondo2.jpg");
					la.draw(0, 0);
					im.draw(getRealPositionX(), getRealPositionY());
				}

				@Override
				public void Update(int delta) throws SlickException {

				}
			};

			// sound = new Music("res/closeredge.wav");
		}
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (!show) {
			return;
		}
		barco.Render(g);
		Controller.getInstance().iterate();
		while (Controller.hasNext()) {
			Rigid go = Controller.next();
			go.render(g);
		}
		g.drawString("Score: " + Controller.getInstance().getScore(), gc.getWidth() - 100, 30);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (Game.show == 2 && show == false) {
			show(gameContainer, stateBasedGame);
			show = true;
		}
		if (!show)
			return;
		if (player == null) {
			return;
		}
		if (Game.enemyCount == 0) {
			Game.show = 0;
			show = false;
			Controller.getInstance().resetGame();
			sbg.enterState(Game.menu, new FadeOutTransition(), new FadeInTransition());
			pause = false;
		}
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_P)){
			if (!pause) {
				back = new Image("res/Pause/pauseback.png");
				contin = new Image("res/Pause/continue_.png");
				got = new Image("res/Pause/goto.png");
				pause = true;
			}
			else {
				pause = false;
			}
		}
		if (pause) {
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
					pause = false;
				} else {
					Game.show = 0;
					show = false;
					Controller.getInstance().resetGame();
					sbg.enterState(Game.menu, new FadeOutTransition(), new FadeInTransition());
					pause = false;
				}
			}
			return;
		}
		Controller.getInstance().setSounds(player);
		player.playerControl(gc, delta);
		Controller.getInstance().follow(player, 0, 0);
		Controller.getInstance().iterate();
		while (Controller.hasNext()) {
			Rigid go = Controller.next();
			go.update(delta);
		}
		if (Controller.getInstance().isGameOver()) {
			show = false;
			pause = false;
			Game.show = 4;
			sbg.enterState(Game.over, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public int getID() {
		return 2;
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	private void addEnemies(Enemy... enemies) throws SlickException {
		for (Enemy enemy : enemies) {
			Controller.getInstance().add(enemy);
			Game.enemyCount = Game.enemyCount + 1;
		}
	}

	private void addPlatforms(Platform... platforms) throws SlickException {
		for (Platform platform : platforms) {
			Controller.getInstance().add(platform);
		}
	}

	private void addWeapons(Weapon... weapons) throws SlickException {
		for (Weapon weapon : weapons) {
			Controller.getInstance().add(weapon);
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		gameContainer = gc;
		stateBasedGame = sbg;
	}
}
