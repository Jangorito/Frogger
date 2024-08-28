package org.example.froggergame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		Menu menu = new Menu(primaryStage,this);
		menu.showInit();
	}

	// Method to start the game with selected level and difficulty
	public void startGame(Stage primaryStage, GameConfig options) {
		background = new MyStage();
		Scene gameScene = new Scene(background, 600, 800);

		animal = new Animal("file:src/main/resources/images/froggerUp.png", options);
		Level gameLevel = new Level(background, animal);
		gameLevel.setupLevel(options);

		background.start();
		primaryStage.setScene(gameScene);
		primaryStage.show();
		startGameLoop();
	}

	public void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
             	if (animal.changeScore()) {
            		setNumber(animal.getPoints());
				}
				if (animal.changeLives()){
					displayLives(animal.getLives());
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
		int gap = 0;
		if (n < 10){
			gap = 0;
		} else if (n < 100){
			gap = 30;
		} else{
			gap = 60;
		}
    	while (n > 0) {
		  int d = n / 10;
		  int k = n - d * 10;
		  n = d;
		  background.add(new Digit(k, 30, 412+gap - shift, 14));
		  shift+=30;
		}
    }
	public void displayLives(int n) {
		int shift = 0;
		while (n > 0) {
			int d = n / 10;
			int k = n - d * 10;
			n = d;
			background.add(new Digit(k, 30, 412+30 - shift, 43));
			shift+=30;
		}
	}

}
