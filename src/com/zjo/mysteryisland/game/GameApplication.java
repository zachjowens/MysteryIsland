package com.zjo.mysteryisland.game;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class GameApplication extends Application {	
	
	private Group root, trunk;
	private ImageView imgBg, imgLogo, imgFrame;
	private MainMenu mainMenu;
	private Scene menuScene, gameScene;
	private Stage primaryStage;
	private long lastNanoTime = System.nanoTime();
	ArrayList<Sprite> fruitSpriteList, heartIconList;
	
	@Override
	public void start(Stage stage) throws Exception {		
		
		primaryStage = stage;
		
		FileInputStream is1 = new FileInputStream("res/images/mysterybg.jpg");
		Image bg = new Image(is1);
		imgBg = new ImageView(bg);
		imgBg.setFitWidth(600);
		imgBg.setFitHeight(400);
		
		FileInputStream is2 = new FileInputStream("res/images/mysterylogo.png");
		Image logo = new Image(is2);
		imgLogo = new ImageView(logo);
		imgLogo.setFitWidth(455);
		imgLogo.setFitHeight(105);
		imgLogo.setLayoutX(75);
		imgLogo.setLayoutY(55);
		
		FileInputStream is3 = new FileInputStream("res/images/mysteryframe.png");
		Image frame = new Image(is3);
		imgFrame = new ImageView(frame);
		imgFrame.setFitWidth(773);
		imgFrame.setFitHeight(565);
		imgFrame.setLayoutX(-73);
		imgFrame.setLayoutY(-80);
		
		mainMenu = new MainMenu();
		mainMenu.setLayoutX(225);
		mainMenu.setLayoutY(205);
		
		root = new Group();
		root.getChildren().addAll(imgBg, imgLogo, mainMenu);
		
		menuScene = new Scene(root, 600, 400);
		
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Mystery Island");
		primaryStage.setResizable(false);
		primaryStage.show();	
	}
	
	public class MenuItem extends StackPane {
		
		public MenuItem(String lbl, int width, int height, int fontSize, Color txtColor, 
				Color bgColor, double bgOpacity, Pos alignment, String click) {		
			
			Font font = new Font("Comic Sans MS", fontSize);
			
			Text text = new Text(lbl);
			text.setFont(font);
			text.setFill(txtColor);
			
			Rectangle bg = new Rectangle(width, height);
			bg.setFill(bgColor);
			bg.setOpacity(bgOpacity);
			
			setAlignment(alignment);
			
			if (click.equals("Y")) {		
				
				setOnMouseEntered (event -> {
					bg.setEffect(new Glow(0.3));
				});	
				
				setOnMouseExited (event -> {
					bg.setEffect(null);
				});	
				
				setOnMousePressed (event -> {
					bg.setEffect(new Glow(0.5));
				});	
				
				setOnMouseReleased (event -> {
					bg.setEffect(null);
				});
			}
			
			getChildren().addAll(bg, text);
		}	
	}
	
	public class MainMenu extends Parent {		
		
		public MainMenu() {
			
			VBox vMain = new VBox(10);
			
			MenuItem btnContinue = new MenuItem("Continue", 150, 40, 19, Color.WHITE, 
					Color.CHOCOLATE, 1, Pos.CENTER, "Y");
			btnContinue.setOnMouseClicked(event -> {
				//
			});
			
			MenuItem btnNewGame = new MenuItem("New Game", 150, 40, 19, Color.WHITE, 
					Color.CHOCOLATE, 1, Pos.CENTER, "Y");
			btnNewGame.setOnMouseClicked(event -> {	
				startGame();
			});
			
			vMain.getChildren().addAll(btnContinue, btnNewGame);
			getChildren().addAll(vMain);
		}
	}

	public void startGame() {
		
		primaryStage.close();
		
		Player player = new Player(5, 100, 0, 1);
		
		Canvas canvas = new Canvas(600, 400);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Font font = new Font("Comic Sans MS", 17);
	    gc.setFont(font);
	    gc.setStroke(Color.BLACK);
	    gc.setFill(Color.BLACK);
	    gc.setLineWidth(0.1);
		
		trunk = new Group();
		trunk.getChildren().addAll(imgFrame, canvas);
		
		gameScene = new Scene(trunk, 600, 400);
		
		ArrayList<String> input = new ArrayList<String>();

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) input.add(code);
                }
            });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
        	
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    input.remove(code);
                }
            });
		 
		Sprite playerSprite = new Sprite();
		try {
			playerSprite.setImage("res/images/gorilla.png");
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}
		playerSprite.setPosition(200, 0);
		
		Sprite fruitIcon = new Sprite();
		try {
			fruitIcon.setImage("res/images/fruiticon.png");
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}
		fruitIcon.setPosition(512, 9);
		
		addFruit();
		
		addHeart(5);
		
		lastNanoTime = System.nanoTime();
		
		new AnimationTimer() {	
			
			public void handle(long currentNanoTime) {			
				//Calculate time since last update.
				double elapsedTime = (currentNanoTime - lastNanoTime)/ 1000000000.0;
				lastNanoTime = currentNanoTime;
				
				//Player movement.
				
				playerSprite.setVelocity(0, 0);
				
				if (input.contains("LEFT")) playerSprite.addVelocity(-300, 0);
				if (input.contains("RIGHT")) playerSprite.addVelocity(300, 0);
				if (input.contains("UP")) playerSprite.addVelocity(0, -300);
				if (input.contains("DOWN")) playerSprite.addVelocity(0, 300);
				
				playerSprite.updatePosition(elapsedTime);
				
				//Limit player movement.
				if (playerSprite.getPositionX() < 0) playerSprite.setPositionX(0);
				if (playerSprite.getPositionX() > 505) playerSprite.setPositionX(505);
				if (playerSprite.getPositionY() < 0) playerSprite.setPositionY(0);
				if (playerSprite.getPositionY() > 300) playerSprite.setPositionY(300);
				
				//Collision detection.
				Iterator<Sprite> fruitSpriteIter = fruitSpriteList.iterator();
				
				while (fruitSpriteIter.hasNext()) {
					Sprite fruitSprite = fruitSpriteIter.next();
					if (playerSprite.intersects(fruitSprite)) {
		                    fruitSpriteIter.remove(); 
		                    player.setFruit(player.getFruit() + 1);
					}
				}
				
				//Replace fruits.
				if (fruitSpriteList.isEmpty()) addFruit();
				
				//Render game.
				gc.clearRect(0, 0, 600, 400);

				String currentLives = "Lives: ";
				gc.fillText(currentLives, 15, 25);
				gc.strokeText(currentLives, 15, 25);
				
				String fruitCollected = "X " + player.getFruit();
				gc.fillText(fruitCollected, 537, 25);
				gc.strokeText(fruitCollected, 537, 25);
				
				fruitIcon.render(gc);
				
				for (Sprite heartIcon : heartIconList) heartIcon.render(gc);
				
				for (Sprite fruitSprite : fruitSpriteList) fruitSprite.render(gc);
			
				playerSprite.render(gc);
			}
		}.start();
        
		primaryStage.setScene(gameScene);
		primaryStage.show();
	}
	
	public void addFruit() {		
		
		fruitSpriteList = new ArrayList<Sprite>();

		for (int i = 0; i < 15; i++) {		
			
			double x = 500 * (Math.random() + 0.1);
			double y = 300 * (Math.random() + 0.1);
			
			Sprite fruitSprite = new Sprite();
			try {
				fruitSprite.setImage("res/images/fruit.png");
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			}
			fruitSprite.setPosition(x, y);
			
			fruitSpriteList.add(fruitSprite);
		}
	}
	
	public void addHeart(int lives) {
		
		heartIconList = new ArrayList<Sprite>();

		for (int i = 0; i < lives; i++) {
			
			int x = 65 + (i * 21);
			
			Sprite heartIcon = new Sprite();
			try {
				heartIcon.setImage("res/images/heart.png");
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			heartIcon.setPosition(x, 11);
			
			heartIconList.add(heartIcon);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
