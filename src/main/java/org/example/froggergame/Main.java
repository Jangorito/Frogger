package org.example.froggergame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The main class for the Frogger game application. It extends {@link Application} and is responsible
 * for initializing the application, handling the game loop, and managing the game's user interface.
 */
public class Main extends Application {
	AnimationTimer timer;
	MyStage background;
	Animal animal;
	public static void main(String[] args) {
		launch(args);
	}
	// calls start

	/**
	 * Initializes the main menu of the game. This method is called when the application starts.
	 *
	 * @param primaryStage the primary stage for this application.
	 */
	@Override
	public void start(Stage primaryStage) {
		Menu menu = new Menu(primaryStage,this);
		menu.showInit();
	}

	/**
	 * Starts the game with the selected level and difficulty settings.
	 *
	 * @param primaryStage the primary stage for this application.
	 * @param options the {@link GameConfig} object containing game settings.
	 */
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

	/**
	 * Updates the display with the current score.
	 *
	 * @param n the score to display.
	 */
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

	/**
	 * Updates the display with the current number of lives.
	 *
	 * @param n the number of lives to display.
	 */
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
