package com.zjo.mysteryisland.game;

import java.io.FileInputStream;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Sprite {
	
    private Image image;
    private double positionX = 0;
    private double positionY = 0;    
    private double velocityX = 0;
    private double velocityY = 0;
    private double width;
    private double height;

    public Sprite() {
    }
    
    public void setImage(String filename) throws Exception {
    	
    	    FileInputStream is = new FileInputStream(filename); 
        Image i = new Image(is);
        readImage(i);
    }

    public void readImage(Image i) {
    	
    		image = i;
    		width = i.getWidth();
    		height = i.getHeight();
    }
    
    public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {	
		this.positionY = positionY;
	}

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public void updatePosition(double t) {
        positionX += (velocityX * t);
        positionY += (velocityY * t);
    }

    public void render(GraphicsContext gc) { 	
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary() { 	
        return new Rectangle2D(positionX, positionY, width - 10, height - 10);
    }

	public boolean intersects(Sprite s) {	
        return s.getBoundary().intersects(this.getBoundary());
    }
    
    public String toString() {
        return "Position: [" + positionX + ", " + positionY + "] " 
        + "Velocity: [" + velocityX + ", " + velocityY + "]";
    }
}