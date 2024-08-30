package org.example.froggergame;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 * Represents the main player-controlled animal (frog) in the Frogger game.
 * This class manages the frog's movement, interactions with game objects,
 * game state, and various events such as collisions and deaths.
 *
 * <p> The frog can move in four directions using the keyboard keys (W, A, S, D).
 * It can die from colliding with cars or falling into the water, and the death
 * animations are managed by this class.
 *
 * <p> The game mode can either be normal or Capture The Flag (CTF), with
 * different rules for interacting with the end zones.
 */
public class Animal extends Actor {
	Image imgW1;
	Image imgA1;
	Image imgS1;
	Image imgD1;
	Image imgW2;
	Image imgA2;
	Image imgS2;
	Image imgD2;
	int points = 0;
	int end = 0;

	// movement vars
	double movement = 13.3333333*2; // movement cost in the Y direction
	double movementX = 10.666666*2;

	// game control states:
	private boolean second = false; // if frog is moving / is in midair
	boolean noMove = false; // when frogger can't move
	int imgSize = 40;
	boolean carDeath = false, waterDeath = false; // death states
	boolean stop = false; // game is won
	boolean changeScore = false; // if the score has changed
	boolean changeLives = false;
	int carD = 0; // counter for death animation
	double w = 800; // max height reached by Frogger
	boolean ctfGameMode = false; // whether the ends are capture the flag ends [GAMEMODE!!!!]
	boolean snagged = false;
	int flagsToGrab = 0;
	int[] endsInCtfOrder;
	int stashed = 0;
	int lives = 1;
	// list of ends
	ArrayList<End> inter = new ArrayList<End>();

	/**
	 * Constructs an Animal object with the specified image link and game configuration.
	 *
	 * @param imageLink the initial image file path for the frog
	 * @param states the game configuration settings for mode and difficulty
	 */
	public Animal(String imageLink, GameConfig states) {
		// setting frog image and position
		setImage(new Image(imageLink, imgSize, imgSize, true, true));
		resetPosition();

		// setting all the images of the frogger when you press wasd
		imgW1 = new Image("file:src/main/resources/images/froggerUp.png", imgSize, imgSize, true, true);
		imgA1 = new Image("file:src/main/resources/images/froggerLeft.png", imgSize, imgSize, true, true);
		imgS1 = new Image("file:src/main/resources/images/froggerDown.png", imgSize, imgSize, true, true);
		imgD1 = new Image("file:src/main/resources/images/froggerRight.png", imgSize, imgSize, true, true);

		imgW2 = new Image("file:src/main/resources/images/froggerUpJump.png", imgSize, imgSize, true, true);
		imgA2 = new Image("file:src/main/resources/images/froggerLeftJump.png", imgSize, imgSize, true, true);
		imgS2 = new Image("file:src/main/resources/images/froggerDownJump.png", imgSize, imgSize, true, true);
		imgD2 = new Image("file:src/main/resources/images/froggerRightJump.png", imgSize, imgSize, true, true);

		setImage(imgW1);
		initializeInputHandlers();

		// setting appropriate game states
        ctfGameMode = states.getGameMode() == 2;
		flagsToGrab = states.getNoFlags();
		lives = states.getLives();
	}
	private void initializeInputHandlers() {
		setOnKeyPressed(this::handleKeyPressed);
		setOnKeyReleased(this::handleKeyReleased);
	}

	/**
	 * Handles key pressed events for movement and game controls.
	 *
	 * @param event the key event triggered by the user's input
	 */
	private void handleKeyPressed(KeyEvent event) {
		if (noMove) return;

		KeyCode code = event.getCode();
		if (second) {
			handleSecondKeyPress(code);
		} else {
			handleFirstKeyPress(code);
		}
	}

	/**
	 * Processes the initial key press for movement.
	 *
	 * @param code the key code corresponding to the pressed key
	 */
	private void handleFirstKeyPress(KeyCode code) {
		if (code == KeyCode.W) {
			move(0, -movement);
			setImage(imgW2);
			second = true;
		} else if (code == KeyCode.A) {
			move(-movementX, 0);
			setImage(imgA2);
			second = true;
		} else if (code == KeyCode.S) {
			move(0, movement);
			setImage(imgS2);
			second = true;
		} else if (code == KeyCode.D) {
			move(movementX, 0);
			setImage(imgD2);
			second = true;
		}
//		else if (code == KeyCode.SPACE){
//			System.out.println(STR."\{getX()} and \{getY()}");
//			// setImage(new Image("file:src/main/resources/images/0.png", 30, 30, true, true));
//
//		}
	}

	/**
	 * Processes the second key press for continued movement.
	 *
	 * @param code the key code corresponding to the pressed key
	 */
	private void handleSecondKeyPress(KeyCode code) {
		if (code == KeyCode.W) {
			move(0, -movement);
			changeScore = false;
			setImage(imgW1);
			second = false;
		} else if (code == KeyCode.A) {
			move(-movementX, 0);
			setImage(imgA1);
			second = false;
		} else if (code == KeyCode.S) {
			move(0, movement);
			setImage(imgS1);
			second = false;
		} else if (code == KeyCode.D) {
			move(movementX, 0);
			setImage(imgD1);
			second = false;
		}
	}

	/**
	 * Handles key released events for stopping the frog's movement.
	 *
	 * @param event the key event triggered by the user's input
	 */
	private void handleKeyReleased(KeyEvent event) {
		if (noMove) return;

		KeyCode code = event.getCode();
		if (code == KeyCode.W) {
			if (getY() < w) {
				changeScore = true;
				w = getY();
				points += 10;
			}
			move(0, -movement);
			setImage(imgW1);
			second = false;
		} else if (code == KeyCode.A) {
			move(-movementX, 0);
			setImage(imgA1);
			second = false;
		} else if (code == KeyCode.S) {
			move(0, movement);
			setImage(imgS1);
			second = false;
		} else if (code == KeyCode.D) {
			move(movementX, 0);
			setImage(imgD1);
			second = false;
		}
	}

	/**
	 * Performs actions during each game tick, such as checking boundaries,
	 * handling collisions, and updating interactions.
	 *
	 * @param now the current timestamp in nanoseconds
	 */
	@Override
	public void act(long now) {
		checkBoundaries();
		handleCollisions(now);
		checkInteractions();
	}

	private void checkBoundaries() {
		if (getX()<0) {
			move(movement*2, 0);
		}
		if (getX() > 600) {
			move(-movement * 2, 0);
		}
		if (getY() < 0 || getY() > 734) {
			resetPosition();
		}
	}

	/**
	 * Handles all collision scenarios with different objects in the game.
	 *
	 * @param now the current timestamp in nanoseconds
	 */
	private void handleCollisions(long now) {
		if (getIntersectingObjects(Obstacle.class).size() >= 1) {
			carDeath = true;
		}
		if (carDeath) {
			handleCarDeath(now);
		}
		if (waterDeath) {
			handleWaterDeath(now);
		}
	}

	private void handleWaterDeath(long now) {
		if (waterDeath) {
			noMove = true;
			if ((now)% 11 ==0) {
				carD++;
			}
			if (carD==1) {
				setImage(new Image("file:src/main/resources/images/waterdeath1.png", imgSize,imgSize , true, true));
			}
			if (carD==2) {
				setImage(new Image("file:src/main/resources/images/waterdeath2.png", imgSize,imgSize , true, true));
			}
			if (carD==3) {
				setImage(new Image("file:src/main/resources/images/waterdeath3.png", imgSize,imgSize , true, true));
			}
			if (carD == 4) {
				setImage(new Image("file:src/main/resources/images/waterdeath4.png", imgSize,imgSize , true, true));
			}
			if (carD == 5) {
				lives -= 1;
				changeLives = true;
				resetPosition();
				waterDeath = false;
				carD = 0;
				setImage(new Image("file:src/main/resources/images/froggerUp.png", imgSize, imgSize, true, true));


				PauseTransition pause = new PauseTransition(Duration.seconds(1)); // 2-second delay
				pause.setOnFinished(event -> noMove = false); // Action to execute after the delay
				pause.play(); // Start the pause
				

				if (points>50) {
					points-=50;
					changeScore = true;
				}
			}

		}
	}

	private void handleCarDeath(long now){									// TODO: finalise method
		if (carDeath) {
			noMove = true;
			if ((now)% 11 ==0) {
				carD++;
			}
			if (carD==1) {
				setImage(new Image("file:src/main/resources/images/cardeath1.png", imgSize, imgSize, true, true));
			}
			if (carD==2) {
				setImage(new Image("file:src/main/resources/images/cardeath2.png", imgSize, imgSize, true, true));
			}
			if (carD==3) {
				setImage(new Image("file:src/main/resources/images/cardeath3.png", imgSize, imgSize, true, true));
			}
			if (carD == 4) {
				lives -= 1;
				changeLives = true;
				resetPosition();
				carDeath = false;
				carD = 0;
				setImage(new Image("file:src/main/resources/images/froggerUp.png", imgSize, imgSize, true, true));

				// Create a pause transition for the desired delay
				PauseTransition pause = new PauseTransition(Duration.seconds(1)); // 2-second delay
				pause.setOnFinished(event -> noMove = false); // Action to execute after the delay
				pause.play(); // Start the pause
				
				// noMove = false;
				if (points>50) {
					points-=50;
					changeScore = true;
				}
			}
		}
	}

	private void checkInteractions() {
		if (!getIntersectingObjects(Log.class).isEmpty() && !noMove) {
			handleLogInteraction();
		} else if (!getIntersectingObjects(Turtle.class).isEmpty() && !noMove) {
			handleTurtleInteraction();
		} else if (!getIntersectingObjects(WetTurtle.class).isEmpty()) {
			handleWetTurtleInteraction();
		} else if (!getIntersectingObjects(End.class).isEmpty()) {
			if (!ctfGameMode) {
				handleEndInteraction();
			}
			else{
				handleCtfEndInteraction();
			}
		} else if (getY() < 413) {
			waterDeath = true;
		}
	}

	private void handleLogInteraction() {
		Log log = getIntersectingObjects(Log.class).get(0);
		if (log.getLeft()) {
			move(-2, 0);
		} else {
			move(0.75, 0);
		}
	}

	private void handleTurtleInteraction() {
		move(-1, 0);
	} // TODO: change based on difficulty?

	private void handleWetTurtleInteraction() {
		WetTurtle wetTurtle = getIntersectingObjects(WetTurtle.class).get(0);
		if (wetTurtle.isSunk()) {
			waterDeath = true;
		} else {
			move(-1, 0);
		}
	}

	/**
	 * Handles interactions when the frog reaches an endpoint in normal game mode.
	 */
	private void handleEndInteraction() { //
		End endPoint = getIntersectingObjects(End.class).get(0);
		if (endPoint.getEndID() != 10){
			if (endPoint.isActivated()) {
				end--;
				points -= 50;
			}
			points += 50;
			changeScore = true;
			w = 800;
			endPoint.setEnd();
			end++;
			resetPosition();
		}
	}

	/**
	 * Handles interactions when the frog reaches an endpoint in Capture The Flag mode.
	 */
	private void handleCtfEndInteraction() { // TODO:
		End endPoint = getIntersectingObjects(End.class).get(0);

		// endpoint where flag is
		if (endPoint.isCtfActive()) {
			if (!snagged) {
				noMove = true;
				snagged = true;
				points += 70;
				setPosition(endPoint);
				endPoint.setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));

				// Create a pause transition for the desired delay
				PauseTransition pause = new PauseTransition(Duration.seconds(1)); // 2-second delay
				pause.setOnFinished(event -> noMove = false); // Action to execute after the delay
				pause.play(); // Start the pause
				
				//noMove = false;
			}
		}

		// endpoint where flag isn't
		if (!endPoint.isCtfActive() && endPoint.getEndID() != 10) {
			noMove = true;
			points -= 50;
			resetPosition();

			// Create a pause transition for the desired delay
			PauseTransition pause = new PauseTransition(Duration.seconds(1)); // 2-second delay
			pause.setOnFinished(event -> noMove = false); // Action to execute after the delay
			pause.play(); // Start the pause
			
			// noMove = false;
		}

		// home interaction
		if (endPoint.getEndID() == 10){
			if (snagged) {
				noMove = true;
				stashed += 1;
				points += 100;
				flagSetting();
				snagged = false;
				resetPosition();

				PauseTransition pause = new PauseTransition(Duration.seconds(1)); // 2-second delay
				pause.setOnFinished(event -> noMove = false); // Action to execute after the delay
				pause.play(); // Start the pause
				
				// noMove = false;
			}
		}
		changeScore = true;
	}

	/**
	 * Resets the frog's position to the starting point.
	 */
	private void resetPosition() {
		setImage(new Image("file:src/main/resources/images/froggerUp.png", imgSize, imgSize, true, true));
		setX(280);
		setY(679.8 + movement);

		if (ctfGameMode){
			if (snagged){
				snagged = false;
				flagSetting();
			}
		}
	}

	/**
	 * Sets the frog's position to a specific endpoint.
	 *
	 * @param endpoint the endpoint to move the frog to
	 */
	private void setPosition(End endpoint){
		// turn around ANIMAL frog
		setImage(new Image("file:src/main/resources/images/froggerDown.png", imgSize, imgSize, true, true));

		setX(endpoint.getX() + 15);
		setY(122.6666666);
	}

	/**
	 * Sets flags in the Capture The Flag game mode.
	 */
	public void flagSetting(){
		int previousFlagIndex;
		int prospectiveFlagIndex;

		if (!snagged){
			inter.get(endsInCtfOrder[stashed]).setImage(new Image("file:src/main/resources/images/ctfEnd.png", 60, 60, true, true));
			return;
		}
		else {
			previousFlagIndex = endsInCtfOrder[stashed - 1];
			prospectiveFlagIndex = endsInCtfOrder[stashed];

			if (stashed < flagsToGrab) {
				inter.get(previousFlagIndex).setCtfActive(false);
				inter.get(previousFlagIndex).setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));
				inter.get(prospectiveFlagIndex).setCtfActive(true);
				inter.get(prospectiveFlagIndex).setImage(new Image("file:src/main/resources/images/ctfEnd.png", 60, 60, true, true));
			}
		}
//		else{
//			System.out.println(STR."stashed = \{stashed} out of \{flagsToGrab} flags to grab. We should prolly be tryna end the game from here?");
//		}
	}


	/**
	 * Checks if the score has changed and resets the flag if it has.
	 *
	 * @return true if the score has changed, otherwise false
	 */
	public boolean changeScore() {
		if (changeScore) {
			changeScore = false;
			return true;
		}
		return false;
	}
	/**
	 * Checks if the lives have changed and resets the flag if they have.
	 *
	 * @return true if lives have changed, otherwise false
	 */
	public boolean changeLives() {
		if (changeLives) {
			changeLives = false;
			return true;
		}
		return false;
	}


	public boolean getCtfGameMode() { return ctfGameMode;}
	public boolean getStop() {
		if(lives == 0){
			System.out.println("you dead");
			return true;
		}
		else if (this.ctfGameMode){
			return stashed == flagsToGrab;
		}
		else if (!this.ctfGameMode){
			return end == 5;
		}
		return false;
	}
	public int getPoints() {
		return points;
	}

	public int getLives() {
		return lives;
	}
}