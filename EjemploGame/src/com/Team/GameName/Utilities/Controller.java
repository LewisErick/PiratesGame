package com.Team.GameName.Utilities;

import java.awt.Window.Type;
import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.opencl.CLSampler;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Circle;

public class Controller extends ArrayList<Rigid>{
	
	public int positionX = 0;
	public int positionY = 0;
	public int score = 0;

	public int width;
	public int height;
	
	public Controller(int w, int h){
		width = w;
		height = h;
	}
	
	public Rigid checkCollision(Rigid ob, float deltaX, float deltaY) throws SlickException{
		ob.boundingBox = new Rectangle(ob.positionX+deltaX,ob.positionY+deltaY,32,32);
		for(Rigid other : this){
			if(ob != other && ob.intersects(other)){
				return other;
			}
		}
		return null;
	}
	
	public Rigid checkCollision(Rigid ob, float deltaX, float deltaY, Class<?> cls) throws SlickException{
		ob.boundingBox = new Rectangle(ob.positionX+deltaX,ob.positionY+deltaY,32,32);
		for(Rigid other : this){
			if(ob != other && cls.isInstance(other) && ob.intersects(other)){
				return other;
			}
		}
		return null;
	}
	
	public LinkedList<Rigid> checkListCollision(Rigid ob, float deltaX, float deltaY) throws SlickException{
		ob.boundingBox = new Rectangle(ob.positionX+deltaX,ob.positionY+deltaY,32,32);
		LinkedList<Rigid> list = new LinkedList<Rigid>();
		for(Rigid other : this){
			if(ob != other && ob.intersects(other)){
				list.add(other);
			}
		}
		return (list.size() == 0) ? null : list;
	}
	
	public LinkedList<Rigid> doRayCastList(Rigid ob, float rayX, float rayY, float range) {
		Rectangle ray = new Rectangle(rayX,rayY,range,3);
		LinkedList<Rigid> list = new LinkedList<Rigid>();
		for(Rigid other : this){
			if(ob != other && ray.intersects(other.boundingBox)){
				list.add(other);
			}
		}
		return (list.size() == 0) ? null : list;
	}
	
	public boolean doRayCast(Rigid ob, float rayX, float rayY, float range, Class<?> cls) {
		// TODO Auto-generated method stub
		Rectangle ray = new Rectangle(rayX,rayY,range,3);
		for(Rigid other : this){
			if(ob != other && cls.isInstance(other) && ray.intersects(other.boundingBox)){
				return true;
			}
		}
		return false;
	}

	public LinkedList<Rigid> checkRangeList (Rigid ob, float rangeX, float rangeY, float radius) {
		// TODO Auto-generated method stub
		Circle range = new Circle(rangeX,rangeY,radius);
		LinkedList<Rigid> list = new LinkedList<Rigid>();
		for(Rigid other : this){
			if(ob != other && range.intersects(other.boundingBox)){
				list.add(other);
			}
		}
		return (list.size() == 0) ? null : list;
	}
	
	public boolean checkRange (Rigid ob, float rangeX, float rangeY, float radius) {
		// TODO Auto-generated method stub
		Circle range = new Circle(rangeX,rangeY,radius);
		for(Rigid other : this){
			if(ob != other && range.intersects(other.boundingBox)){
				return true;
			}
		}
		return false;
	}
	
	public double getRange (Rigid ob, Rigid target) {
		// TODO Auto-generated method stub
		double distanceX, distanceY, distance;
		distanceX = Math.pow(ob.positionX - target.positionX, 2);
		distanceY = Math.pow(ob.positionY - target.positionY, 2);
		distance = Math.sqrt(distanceX + distanceY);
		return distance;
	}
	
	public boolean deleteControl(Rigid ob) {
		return (this.remove(ob));
	}
	
	public boolean deleteControlList(Rigid ob) {
		boolean found = false;
		for(Rigid other : this){
			if (ob == other) {
				this.remove(ob);
				found = true;
			}
		}
		return found;
	}
	
	public void moveCamera(float deltaX, float deltaY) {
		if (positionX + deltaX >= 0 && positionX + deltaX <= width) {
			positionX += deltaX;
		} else {
			positionX += deltaX;
			this.get(0).setPositionX(this.get(0).getPositionX() + deltaX);
		}
		if (positionY + deltaY >= 0 && positionY + deltaY <= height) {
			positionY += deltaY;
		} else {
			positionY += deltaY;
			this.get(0).setPositionY(this.get(0).getPositionY() + deltaY);
		}
	}
	
	public void giveScore(int points) {
		score = score + points;
	}
}