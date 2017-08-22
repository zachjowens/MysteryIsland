package com.zjo.mysteryisland.game;

public class Player {
	
	private int lives;
	private int health;
	private int fruit;
	private int level;
	
	public Player(int lives, int health, int fruit, int level) {
		this.lives = lives;
		this.health = health;
		this.fruit = fruit;
		this.level = level;
	}

	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getFruit() {
		return fruit;
	}
	public void setFruit(int fruit) {
		this.fruit = fruit;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}