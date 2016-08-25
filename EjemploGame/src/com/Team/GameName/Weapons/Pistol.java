package com.Team.GameName.Weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.Team.GameName.Characters.Character;
import com.Team.GameName.Utilities.Controller;
import com.Team.GameName.Utilities.Rigid.Direction;

public class Pistol extends Weapon {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private final float reloadTime;
	private final int ammoCapacity;
	private final int ammoTotalCapacity;
	private final float range;
	private int totalAmmo;
	private int currentAmmo;
	private float timerReload = 0;

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public Pistol(float positionX, float positionY, int totalAmmo, boolean attached) throws SlickException {
		this(positionX, positionY, totalAmmo, 20, 0.5f, attached);
	}
	
	public Pistol(float positionX, float positionY, int totalAmmo, float damage, float attackTime,
			boolean attached) throws SlickException {
		this(positionX, positionY, totalAmmo, damage, attackTime, attached, 2f, 5, 50, 300);
	}

	public Pistol(float positionX, float positionY, int totalAmmo, float damage, float attackTime,
			boolean attached, float reloadTime, int ammoCapacity, int ammoTotalCapacity, float range) throws SlickException {
		super(positionX, positionY, 15, 5, damage, attackTime, attached);
		this.reloadTime = reloadTime;
		this.ammoCapacity = ammoCapacity;
		this.ammoTotalCapacity = ammoTotalCapacity;
		this.range = range;
		setAmmo(totalAmmo);
	}
	
	/********************************************************************************
	 ***************************** -IMPLEMENTED METHODS- ****************************
	 ********************************************************************************/
	@Override
	public boolean canAttack() {
		return super.canAttack() && timerReload <= 0 && totalAmmo > 0;
	}
	
	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void shoot() throws SlickException {
		//if (timerReload <= 0 && totalAmmo > 0) {
			currentAmmo -= 1;
			if (currentAmmo <= 0) {
				reload();
			}
			startTimerAttack();
			doDamage();
			//return true;
		//} else {
			//return false;
		//}
	}

	public void reload() {
		if (currentAmmo - ammoCapacity < 0) {
			timerReload = reloadTime;
			totalAmmo += currentAmmo - ammoCapacity;
		}
	}

	@Override
	public void Update(int delta) throws SlickException {
		super.Update(delta);
		if (timerReload >= 0) {
			timerReload -= delta / 1000f;
		}
		if (timerReload > 0) {
			currentAmmo = (int) Math.floor(ammoCapacity * (1 - (timerReload / reloadTime)));
			if (currentAmmo == ammoCapacity - 1) {
				currentAmmo = ammoCapacity;
			}
		}
	}

	@Override
	public void doDamage() throws SlickException {
		Character ob = Controller.getInstance().doRayCast(this, range, 3, Character.class);
		Barrel barrel = Controller.getInstance().doRayCast(this, range, 3, Barrel.class);
		if (ob != null) {
			ob.takeAwayHealth(getDamage());
		}
		if (barrel != null) {
			barrel.doDamage();
		}
	}

	@Override
	public void Render(Graphics g) throws SlickException {
		if (!super.isAttached()) {
			Image pist = new Image("res/pistol.png");
			pist.draw(getRealPositionX(), getRealPositionY());
		}
	}

	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public int getCurrentAmmo() {
		return currentAmmo;
	}

	public int getTotalAmmo() {
		return totalAmmo;
	}

	public void addAmmo(int newAmmo) {
		totalAmmo = (totalAmmo + newAmmo < ammoTotalCapacity) ? totalAmmo + newAmmo : ammoTotalCapacity;
	}

	public void setAmmo(int ammo) {
		if (ammo >= ammoCapacity) {
			currentAmmo = ammoCapacity;
			totalAmmo = ammo - ammoCapacity;
		} else {
			currentAmmo = totalAmmo;
			totalAmmo = 0;
		}
	}

	public int getAmmoCapacity() {
		return ammoCapacity;
	}

}