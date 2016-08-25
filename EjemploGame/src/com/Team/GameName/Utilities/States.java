package com.Team.GameName.Utilities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum States {
	PLAYERWALK("res/MainCharacter/principalEspada.png", 6, 104, 364, 45, 6, 100, false, true),
	PLAYERSTAND("res/MainCharacter/principalEspada.png", 6, 36, 185, 45, 3, 250, false, true),
	PLAYERATTACK("res/MainCharacter/principalEspada.png", 6, 170, new int[]{70,55,77}, 45, 200, false, false),
	PLAYERDIE("res/MainCharacter/principalEspada.png", 0, 1110, 5, 1, 95, 115, 5, 250, false, false),
	
	PLAYERPISTOLWALK("res/MainCharacter/principalPistola.png", 10, 104, 130, 45, 2, 200, false, true),
	PLAYERPISTOLSTAND("res/MainCharacter/principalPistola.png", 6, 36, 185, 45, 3, 250, false, true),
	PLAYERPISTOLATTACK("res/MainCharacter/principalPistola.png", 10, 237, 3, 1, 74, 45, 3, 150, false, false),
	PLAYERPISTOLDIE("res/MainCharacter/principalPistola.png", 0, 1110, 5, 1, 95, 115, 5, 250, false, false),
	
	ENEMYWALK("res/Enemy/Pirate2/Pirata2.png", 7, 86, 6, 1,  40,  46, 6,  250, true, true),
	ENEMYSTAND("res/Enemy/Pirate2/Pirata2.png", 7, 28, 2, 1,  40,  46, 2, 2000, true, true),
	ENEMYATTACK("res/Enemy/Pirate2/Pirata2.png", 7, 145, new int[]{40,46,68,62}, 46, 200, true, false),
	ENEMYDIE("res/Enemy/Pirate2/Pirata2.png", 7, 200, new int[]{40,40,40,50,65,60}, 46, 700, true, false),

	ENEMY2WALK("res/Enemy/Pirate1/pirata1.png", 10, 410, 3, 1, 80, 115, 3, 150, false, true),
	ENEMY2STAND("res/Enemy/Pirate1/pirata1.png", 16, 530, 4, 1, 70, 115, 4, 250, false, true),
	ENEMY2ATTACK("res/Enemy/Pirate1/pirata1.png", 0, 826, 4, 1, 90, 115, 4, 250, false, false),
	ENEMY2DIE("res/Enemy/Pirate1/pirata1.png", 0, 1110, 5, 1, 95, 115, 5, 250, false, false),
	
	ENEMYEXPLOSION("res/explosion.png", 0, 0, 20, 1, 101, 256, 20, 100, false, false),
	PISTOL("res/pistol.png", 0, 0, 0, 0, 48, 16, 14, 2000, false, false);
	
	private Animation animation;
	
	States(String path, int positionX, int positionY, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int frames, int duration, boolean right, boolean loop){
		Image i = null;
		try {
			i = new Image(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		animation = new Animation(false);
		for(int y = 0; y < spriteY; y++){
			for(int x = 0; x < spriteX; x++){
				if(right){
					animation.addFrame(i.getSubImage(positionX + x * spriteWidth,positionY + y * spriteHeight, spriteWidth, spriteHeight), duration);
				}else{
					animation.addFrame(i.getSubImage(positionX + x * spriteWidth,positionY + y * spriteHeight, spriteWidth, spriteHeight).getFlippedCopy(true, false), duration);
				}
			}
		}
		animation.setLooping(loop);
	}
	
	States(String path, int positionX, int positionY, int[] spriteWidth, int spriteHeight, int duration, boolean right, boolean loop){
		Image i = null;
		try {
			i = new Image(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		animation = new Animation(false);
		int temp = positionX;
		for(int x = 0; x < spriteWidth.length; x++){
			if(right){
				animation.addFrame(i.getSubImage(temp, positionY, spriteWidth[x], spriteHeight), duration);
			}else{
				animation.addFrame(i.getSubImage(temp, positionY, spriteWidth[x], spriteHeight).getFlippedCopy(true, false), duration);
			}
			temp += spriteWidth[x];
		}
		animation.setLooping(loop);
	}
	
	States(String path, int positionX, int positionY, float totalWidth, float totalHeight, int divisions, int duration, boolean right, boolean loop){
		Image i = null;
		try {
			i = new Image(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		animation = new Animation(false);
		float division = totalWidth/divisions;
		for(int x = 0; x < divisions; x++){
			Image la = right ? 
					i.getSubImage((int) Math.ceil(positionX + x * division), positionY , (int)division, (int)totalHeight)
					: i.getSubImage((int) Math.ceil(positionX + x * division), positionY , (int)division, (int)totalHeight).getFlippedCopy(true, false);

			animation.addFrame(la, duration);
		}
		animation.setLooping(loop);
	}

	public Animation getAnimation() {
		return animation;
	}
}
