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
import com.Team.GameName.Characters.Explosion;
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

public class Level1 extends BasicGameState {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	MainCharacter player;
	/*
	 * LinkedList<Platform> platforms = new LinkedList<Platform>();
	 * LinkedList<Weapon> weapons = new LinkedList<Weapon>(); LinkedList<Enemy>
	 * enemies = new LinkedList<Enemy>();
	 */
	Rigid barco;
	boolean primera = true;
	private boolean pause = false;
	private int pauseOption = 0;
	
	private Image got;
	private Image contin;
	private Image back;
	
	private boolean show = false;
	
	GameContainer gameContainer;
	StateBasedGame stateBasedGame;
	
	// Music sound;

	public Level1(int state) {

	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gameContainer = gc;
		stateBasedGame = sbg;
	}

	public void show(GameContainer gc, StateBasedGame sbg) throws SlickException {
		show = true;
		gc.setShowFPS(false);
		player = new MainCharacter(2220, 10);
		Controller.getInstance().add(player);
		Controller.getInstance().setController(3200, 1000);
		if (primera) {
			//primera = false;
			addEnemies(
					new Pirate1(600, 20), 
					new Pirate1(1200, 20),
					new Pirate2(200, 20),
					new Pirate1(1800, 20)
			);

			addPlatforms(new BoxPlatform(-20, 0, 20, 1400), new BoxPlatform(0, 210, 340, 200),
				new BoxPlatform(340, 280, 180, 300), new BoxPlatform(520, 340, 460, 100),
				new BoxPlatform(1080, 280, 400, 200), new BoxPlatform(1480, 340, 660, 200),
				new BoxPlatform(2220, 280, 420, 200), new BoxPlatform(2640, 340, 220, 200),
				new BoxPlatform(2860, 280, 490, 200), new BoxPlatform(3440, 220, 170, 200),
				new BoxPlatform(3600, 0, 20, 1400), new TrianglePlatform(520, 280, 100, 60, TrianglePlatform.Side.LEFT),
				new TrianglePlatform(980, 280, 100, 60, TrianglePlatform.Side.RIGHT),
				new TrianglePlatform(1480, 280, 100, 60, TrianglePlatform.Side.LEFT),
				new TrianglePlatform(1480, 280, 100, 60, TrianglePlatform.Side.LEFT),
				new TrianglePlatform(2140, 280, 80, 60, TrianglePlatform.Side.RIGHT),
				new TrianglePlatform(3350, 220, 90, 60, TrianglePlatform.Side.RIGHT)
			);

			addWeapons(new Barrel(1300, 175));

			barco = new Rigid(0, 0) {
				Image im = new Image("res/Environment/barco.png");

				@Override
				public void Render(Graphics g) throws SlickException {
					Image la = new Image("res/ocean.gif");
					la.draw(0,0);
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
		if (!show)
			return;
		barco.Render(g);
		Controller.getInstance().iterate();
		while (Controller.hasNext()) {
			Rigid go = Controller.next();
			go.render(g);
		}
		g.drawString("Score: " + Controller.getInstance().getScore(), gc.getWidth() - 100, 30);
		if (pause) {
			back.draw(-15,0);
			contin.draw(190,320);
			got.draw(200,400);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (Game.show == 1 && show == false) {
			show(gameContainer, stateBasedGame);
			show = true;
		}
		if (!show)
			return;
		if (player == null) {
			return;
		}
		if (Game.enemyCount == 0) {
			Game.enemyCount = -2;
			Game.show = 2;
			show = false;
			Controller.getInstance().resetGame();
			sbg.enterState(Game.nivel2, new FadeOutTransition(), new FadeInTransition());
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
					Controller.getInstance().playMusic("res/title.wav");
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
		return 1;
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
}
