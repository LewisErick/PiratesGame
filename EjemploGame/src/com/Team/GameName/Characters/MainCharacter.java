package com.Team.GameName.Characters;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Utilities.ASound;
import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.States;
import com.Team.GameName.Characters.Character.Actions;
import com.Team.GameName.Weapons.Pistol;
import com.Team.GameName.Weapons.Sword;
import com.Team.GameName.Weapons.Weapon;

public class MainCharacter extends Character {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private float jumpVelocity = 2.5f;
	private float recoverHealthRate = 2f;
	private float rangePickWeapon = 50;
	private Sword sword;
	private Pistol pistol;

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public MainCharacter(float positionX, float positionY) throws SlickException {
		super(positionX, positionY, 60, 50, 0.3f, 200);
		setPistol(new Pistol(0, 0, 50, true));
		setSword(new Sword(0, 0, true));
		setCurrentWeapon(getPistol());
	}

	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public void Update(int delta) throws SlickException {
		super.Update(delta);
		addHealth(recoverHealthRate / 1000);
		takeWeapon();
	}

	@Override
	public void drawUtilities(Graphics g) throws SlickException {
		drawHealth(g, 30, 30, 500, 20, 10, Color.white, Color.green, true);
		drawBullets(g, 30, 60, 200, 10, 20);
		drawTotalAmmo(g, "Ammo: ", 30, 115, Color.white);
	}

	@Override
	public boolean move(int delta) throws SlickException {
		changeAction(Actions.Walk);
		return super.move(delta);
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void playerControl(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if (input.isKeyPressed(Input.KEY_W)) {
			jump();
		} else if (input.isKeyPressed(Input.KEY_SPACE) && attack()) {
			changeAction(Actions.Attack);
		} else if (input.isKeyPressed(Input.KEY_R)) {
			reloadPistol();
		} else if (input.isKeyPressed(Input.KEY_1)) {
			changeWeapon(Sword.class);
		} else if (input.isKeyPressed(Input.KEY_2)) {
			changeWeapon(Pistol.class);
		} else if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_D)) {
			if (input.isKeyDown(Input.KEY_A)) {
				setDirection(Direction.Left);
			}
			if (input.isKeyDown(Input.KEY_D)) {
				setDirection(Direction.Right);
			}
			if (!move(delta)) {
				stand();
			}
		} else {
			stand();
		}
	}

	public void stand() throws SlickException {
		changeAction(Actions.Stand);
		resetCurrentVelocityX();
	}

	public void jump() {
		setInicialVelocityY(jumpVelocity);
	}

	public boolean checkDead() {
		if (getHealth() <= 0) {
			Controller.getInstance().GameOver();
			Controller.getInstance().decreaseScore(100);
			Controller.getInstance().remove(this);
			Controller.getInstance().remove(getPistol());
			Controller.getInstance().remove(getSword());
			return true;
		}
		return false;
	}

	public <T extends Weapon> void changeWeapon(Class<T> cls) {
		if (pistol != null && cls.isInstance(pistol)) {
			setCurrentWeapon(pistol);
		} else if (sword != null && cls.isInstance(sword)) {
			setCurrentWeapon(sword);
		}
	}

	private boolean takeWeapon() {
		boolean took = false;
		LinkedList<Pistol> pistolas = Controller.getInstance().checkRangeList(this, getPositionX(), getPositionY(), rangePickWeapon,
				Pistol.class);
		for (Pistol pistola : pistolas) {
			if (getPistol() != pistola && !pistola.isAttached()) {
				getPistol().addAmmo(pistola.getTotalAmmo());
				Controller.getInstance().remove(pistola);
				took = true;
			}
		}
		return took;
	}

	public boolean reloadPistol() {
		if (getCurrentWeapon() != null && getCurrentWeapon() instanceof Pistol) {
			((Pistol) getCurrentWeapon()).reload();
			return true;
		}
		return false;
	}

	private void drawBullets(Graphics g, int positionX, int positionY, float widthDraw, float width, float height) throws SlickException {
		if (!(getCurrentWeapon() instanceof Pistol)) {
			return;
		}
		Pistol pistol = (Pistol) getCurrentWeapon();
		float intervalWidth = (widthDraw - width) / (float) (pistol.getAmmoCapacity() - 1);
		for (int i = 0; i < pistol.getCurrentAmmo(); i++) {
			g.drawImage(new Image("res/bullet.png"), i * intervalWidth + positionX * 1f, positionY * 1f);
		}
	}

	private void drawTotalAmmo(Graphics g, String text, int positionX, int positionY, Color color) {
		if (!(getCurrentWeapon() instanceof Pistol)) {
			return;
		}
		Pistol pistol = (Pistol) getCurrentWeapon();
		g.setColor(color);
		g.drawString(text + pistol.getTotalAmmo(), positionX, positionY);
	}

	@Override
	public void changeAction(Actions newAction) throws SlickException{
		if(getCurrentAnimation() != null){
			if((getCurrentAnimation() == States.PLAYERPISTOLATTACK.getAnimation() || getCurrentAnimation() == States.PLAYERATTACK.getAnimation()) && !getCurrentAnimation().isStopped()){
				return;
			}
			if(getCurrentAnimation().isStopped()){
				getCurrentAnimation().restart();
			}
		}
		States temp = null;
		if(newAction == Actions.Stand){
			temp = getCurrentWeapon() instanceof Sword ? States.PLAYERSTAND : States.PLAYERPISTOLSTAND;
			//Controller.getInstance().addSound("res/stand.wav", getPositionX(), getPositionY());
		}else if(newAction == Actions.Walk){
			temp = getCurrentWeapon() instanceof Sword ? States.PLAYERWALK : States.PLAYERPISTOLWALK;
			//Controller.getInstance().addSound("res/walk.wav", getPositionX(), getPositionY());
		}else if(newAction == Actions.Attack){
			if(getCurrentWeapon() instanceof Sword){
				temp = States.PLAYERATTACK;
				//Controller.getInstance().addSound("res/swing.wav", getPositionX(), getPositionY());
			}else{
				temp = States.PLAYERPISTOLATTACK;
				Controller.getInstance().addSound("res/shoot.wav", 1000000f, getPositionX(), getPositionY());
			}
		}
		setCurrentAnimation(temp.getAnimation());
	}
	
	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public Sword getSword() {
		return sword;
	}

	public void setSword(Sword sword) {
		if (this.sword != null) {
			this.sword.notAttached();
		}
		Controller.getInstance().add(sword);
		sword.notVisible();
		sword.setAttached();
		this.sword = sword;
	}

	public Pistol getPistol() {
		return pistol;
	}

	public void setPistol(Pistol pistol) {
		if (this.pistol != null) {
			this.pistol.notAttached();
		}
		Controller.getInstance().add(pistol);
		pistol.notVisible();
		pistol.setAttached();
		this.pistol = pistol;
	}
}
