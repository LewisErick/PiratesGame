package com.Team.GameName.Utilities;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.Team.GameName.Characters.MainCharacter;

public class ASound extends Sound{
	private float positionX;
	private float positionY;
	private float volume;
	
	public ASound(String path, float volume, float positionX, float positionY) throws SlickException{
		super(path);
		super.play();
		this.positionX = positionX;
		this.positionY = positionY;
		this.volume = volume;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}
}
