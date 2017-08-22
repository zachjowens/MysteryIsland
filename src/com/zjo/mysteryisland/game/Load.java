package com.zjo.mysteryisland.game;

public class Load {	
	
	public void loadGame() {	
		
		Player player = new Player(5, 100, 0, 1);
		try {
			Save save = (Save) ResourceManager.load("1.save");
			player.setLives(save.l);
			player.setHealth(save.h);
			player.setFruit(save.f);
			player.setLevel(save.lvl);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}