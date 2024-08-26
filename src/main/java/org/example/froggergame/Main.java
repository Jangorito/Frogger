package org.example.froggergame;

import java.io.File;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	AnimationTimer timer;
	MyStage background;
	Animal animal;
	public static void main(String[] args) {
		launch(args);
	}
	// calls start

	// TODO: recreate start method in a modular way + --find/design better background image-- + implement GUI + levels
	@Override
	public void start(Stage primaryStage) {
		Menu menu = new Menu(primaryStage,this );
		menu.showMenu();
	}

	// Method to start the game with selected level and difficulty
	public void startGame(Stage primaryStage, int difficulty, int level) {
		background = new MyStage();
		Scene gameScene = new Scene(background, 600, 800);

		animal = new Animal("file:src/main/resources/images/froggerUp.png");
		Level gameLevel = new Level(background, animal);
		gameLevel.setupLevel(difficulty, level);

		background.start();
		primaryStage.setScene(gameScene);
		primaryStage.show();
		startGameLoop();
	}

	// OLD_______________________________________________________________________________
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//	    background = new MyStage();
//	    Scene scene  = new Scene(background,600,800);
//
//		animal = new Animal("file:src/main/resources/images/froggerUp.png");
//		Level level1 = new Level(background, animal);
//		level1.setupLevel(2, 1);
//
//		background.start();
//		primaryStage.setScene(scene);
//		primaryStage.show();
//		start();
//	}


	public void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	if (animal.changeScore()) {
            		setNumber(animal.getPoints());
            	}
            	if (animal.getStop()) {
            		System.out.print("STOPP:");
            		// background.stopMusic();
            		stop();
            		background.stop();
            		Alert alert = new Alert(AlertType.INFORMATION);
            		alert.setTitle("You Have Won The Game!");
            		alert.setHeaderText("Your High Score: "+animal.getPoints()+"!");
            		alert.setContentText("Highest Possible Score: 800");
            		alert.show();
            	}
            }
        };
    }
	public void startGameLoop() {
		createTimer();
		timer.start();
	}

	public void stopGameLoop() {
		timer.stop();
	}
    
    public void setNumber(int n) {
    	int shift = 0;
    	while (n > 0) {
    		  int d = n / 10;
    		  int k = n - d * 10;
    		  n = d;
    		  background.add(new Digit(k, 30, 360 - shift, 25));
    		  shift+=30;
    		}
    }
}
