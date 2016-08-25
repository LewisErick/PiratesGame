package com.Team.GameName.Utilities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import com.Team.GameName.Characters.Enemy;
import com.Team.GameName.Characters.MainCharacter;
import com.Team.GameName.Utilities.Rigid.Direction;

public final class Controller {
	/********************************************************************************
	 *********************************** -FIELDS- ***********************************
	 ********************************************************************************/
	private float positionX;
	private float positionY;
	private int width;
	private int height;
	private int score;
	private boolean gameOver = false;
	private Iterator<Rigid> i;
	private HashSet<Rigid> objects = new HashSet<Rigid>();
	private HashSet<ASound> sounds = new HashSet<ASound>();
	private static Controller instance = null;
	
	private Music music;
	private Sound sound;
	private String musicPath;

	/********************************************************************************
	 ******************************** -CONSTRUCTORS- ********************************
	 ********************************************************************************/
	public void setController(int width, int height) {
		instance.positionX = 0;
		instance.positionY = 0;
		instance.width = width;
		instance.height = height;
		instance.gameOver = false;
		i = null;
	}

	public static Controller getInstance() {
		if(instance == null){
			instance = new Controller();
		}
		return instance;
	}

	/********************************************************************************
	 ********************************** -METHODS- ***********************************
	 ********************************************************************************/
	public void resetGame() {
		instance.positionX = 0;
		instance.positionY = 0;
		instance.gameOver = false;
		instance.i = null;
		instance.objects.clear();
	}

	public void iterate() {
		instance.i = instance.objects.iterator();
	}

	public static boolean hasNext() {
		return instance.i.hasNext();
	}

	public static Rigid next() {
		return instance.i.next();
	}

	public void add(Rigid ob) {
		instance.objects.add(ob);
	}
	
	public void playMusic(String path) throws SlickException {
		music = new Music(path);
		music.loop();
		musicPath = path;
	}

	public void addSound(String path, float volume, float positionX, float positionY) throws SlickException{
		sounds.add(new ASound(path, volume, positionX, positionY));
	}
	
	public void setSounds(Rigid ob){
		for(ASound sound : sounds){
			if(!sound.playing()){
				sounds.remove(sound);
				break;
			}
			sound.setVolume((500 - Controller.getInstance().getRange(positionX, positionY, ob))/5000);
		}
	}
	
	public void addAll(LinkedList<? extends Rigid> ob) {
		instance.addAll(ob);
	}

	public <T> T checkCollision(Rigid ob, float deltaX, float deltaY, Class<T> cls) throws SlickException {
		ob.setBoundingBox(
				new Rectangle(ob.getPositionX() + deltaX, ob.getPositionY() + deltaY, ob.getWidth(), ob.getHeight()));
		for (Rigid other : instance.objects) {
			if (ob != other && cls.isInstance(other) && ob.intersects(other)) {
				return cls.cast(other);
			}
		}
		return null;
	}

	public <T> LinkedList<T> checkCollisionList(Rigid ob, float deltaX, float deltaY, Class<T> cls)
			throws SlickException {
		ob.setBoundingBox(
				new Rectangle(ob.getPositionX() + deltaX, ob.getPositionY() + deltaY, ob.getWidth(), ob.getHeight()));
		LinkedList<T> list = new LinkedList<T>();
		for (Rigid other : instance.objects) {
			if (ob != other && cls.isInstance(other) && ob.intersects(other)) {
				list.add(cls.cast(other));
			}
		}
		return list;
	}

	public <T> T doRayCast(Rigid ob, float range, float width, Class<T> cls) {
		Rectangle ray = new Rectangle(
				ob.getDirection() == Direction.Right ? ob.getPositionX() : ob.getPositionX() - range,
				ob.getPositionY() - (width / 2), range, width);
		float distance = 0;
		T object = null;
		for (Rigid other : instance.objects) {
			if (ob != other && cls.isInstance(other) && other.getBoundingBox() != null
					&& ray.intersects(other.getBoundingBox())) {
				float newDistance = getRange(ob, other);
				if (distance <= 0 || newDistance < distance) {
					distance = newDistance;
					object = cls.cast(other);
				}
			}
		}
		return object;
	}

	public <T> LinkedList<T> doRayCastList(Rigid ob, float range, float width, Class<T> cls) {
		Rectangle ray = new Rectangle(
				ob.getDirection() == Direction.Right ? ob.getPositionX() : ob.getPositionX() - range,
				ob.getPositionY() - (width / 2), range, width);
		LinkedList<T> list = new LinkedList<T>();
		for (Rigid other : instance.objects) {
			if (ob != other && cls.isInstance(other) && ray.intersects(other.getBoundingBox())) {
				list.add(cls.cast(other));
			}
		}
		return list;
	}

	public <T> LinkedList<T> checkRangeList(Rigid ob, float rangeX, float rangeY, float radius, Class<T> cls) {
		Circle range = new Circle(rangeX, rangeY, radius);
		LinkedList<T> list = new LinkedList<T>();
		for (Rigid other : instance.objects) {
			if (ob != other && cls.isInstance(other) && other.getBoundingBox() != null
					&& range.intersects(other.getBoundingBox())) {
				list.add(cls.cast(other));
			}
		}
		return list;
	}

	public <T> T checkRange(Rigid ob, float radius, Class<T> cls) {
		Circle range = new Circle(ob.getPositionX(), ob.getPositionY(), radius);
		for (Rigid other : instance.objects) {
			if (ob != other && cls.isInstance(other) && other.getBoundingBox() != null
					&& range.intersects(other.getBoundingBox())) {
				return cls.cast(other);
			}
		}
		return null;
	}

	public float getRange(Rigid ob, Rigid target) {
		return (float) Math.sqrt(Math.pow(ob.getPositionX() - target.getPositionX(), 2)
				+ Math.pow(ob.getPositionY() - target.getPositionY(), 2));
	}
	
	public float getRange(float positionX, float positionY, Rigid target) {
		return (float) Math.sqrt(Math.pow(positionX - target.getPositionX(), 2)
				+ Math.pow(positionY - target.getPositionY(), 2));
	}

	public void remove(Rigid ob) {
		instance.objects.remove(ob);
		i = instance.objects.iterator();
	}

	public <T> boolean remove(Class<T> cls) {
		boolean found = false;
		for (Rigid other : instance.objects) {
			if (cls.isInstance(other)) {
				instance.objects.remove(other);
				found = true;
			}
		}
		return found;
	}
	
	public void removeAll() {
		remove(MainCharacter.class);
	}

	public void follow(Rigid ob, float deltaX, float deltaY) {
		if (ob.getPositionX() > 300 && ob.getPositionX() < width - 300) {
			positionX = ob.getPositionX() - 300;
		}
		if (ob.getPositionY() > 300 && ob.getPositionY() < height - 300) {
			positionY = ob.getPositionY() - 300;
		}
	}

	// GETTERS AND SETTERS
	public boolean isGameOver() {
		return instance.gameOver;
	}

	public void GameOver() {
		instance.gameOver = true;
	}

	public void resetGameOver() {
		instance.gameOver = false;
	}

	
	/********************************************************************************
	 **************************** -GETTERS AND SETTERS- *****************************
	 ********************************************************************************/
	public float getPositionX() {
		return instance.positionX;
	}

	public void setPositionX(float positionX) {
		instance.positionX = positionX;
	}

	public float getPositionY() {
		return instance.positionY;
	}

	public void setPositionY(float positionY) {
		instance.positionY = positionY;
	}

	public int getScore() {
		return instance.score;
	}

	public void addScore(int points) {
		instance.score += Math.abs(points);
	}

	public void decreaseScore(int points) {
		instance.score -= Math.abs(points);
	}

	public int getWidth() {
		return instance.width;
	}

	public int getHeight() {
		return instance.height;
	}

	public <T> T find(Class<T> cls) {
		for(Rigid ob : instance.objects){
			if(cls.isInstance(ob)){
				return cls.cast(ob);
			}
		}
		return null;
	}
}