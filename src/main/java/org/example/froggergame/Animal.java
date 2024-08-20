package org.example.froggergame;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

// TODO: Split class into Animal and something else that contains rules for game.
//  Adversely, add it into Level.Java? Particularly Game End
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
	int ctfEndGame = 0;

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
	int carD = 0; // counter for death animation
	double w = 800; // max height reached by Frogger
	boolean ctfEnd = false; // whether the ends are capture the flag ends [GAMEMODE!!!!]
	boolean snagged = false;
	int flagsToGrab = 1;
	End[] ctfEndsArray;
	int[] endsInCtfOrder;
	int stashed = 0;
	ArrayList<End> inter = new ArrayList<End>();


	public Animal(String imageLink) {
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

	}
	private void initializeInputHandlers() {
		setOnKeyPressed(this::handleKeyPressed);
		setOnKeyReleased(this::handleKeyReleased);
	}

	private void handleKeyPressed(KeyEvent event) {
		if (noMove) return;

		KeyCode code = event.getCode();
		if (second) {
			handleSecondKeyPress(code);
		} else {
			handleFirstKeyPress(code);
		}
	}

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
	}

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

	@Override
	public void act(long now) {
		checkBoundaries();
		// handleCollisions(now);
		checkInteractions();
		checkGameOver();
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

	private void handleCollisions(long now) {								// -TODO: are there any more collisions to add?-
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

	private void handleWaterDeath(long now) {								// TODO: finalise method
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
				resetPosition();
				waterDeath = false;
				carD = 0;
				setImage(new Image("file:src/main/resources/images/froggerUp.png", imgSize, imgSize, true, true));
				noMove = false;
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
				resetPosition();
				carDeath = false;
				carD = 0;
				setImage(new Image("file:src/main/resources/images/froggerUp.png", imgSize, imgSize, true, true));
				noMove = false;
				if (points>50) {
					points-=50;
					changeScore = true;
				}
			}
		}
	}

	private void checkInteractions() {
		if (getIntersectingObjects(Log.class).size() >= 1 && !noMove) {
			handleLogInteraction();
		} else if (getIntersectingObjects(Turtle.class).size() >= 1 && !noMove) {
			handleTurtleInteraction();
		} else if (getIntersectingObjects(WetTurtle.class).size() >= 1) {
			handleWetTurtleInteraction();
		} else if (getIntersectingObjects(End.class).size() >= 1) {
			if (!ctfEnd) {
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

	// End interaction for if game mode is set to normal
	private void handleEndInteraction() { //
		End endPoint = getIntersectingObjects(End.class).get(0);
		if (endPoint.isActivated() && endPoint.getEndID() != 10) {
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

	// End interaction for if game mode is set to CTF
	private void handleCtfEndInteraction() { // TODO:
		End endPoint = getIntersectingObjects(End.class).get(0);

		// endpoint where flag is
		if (endPoint.isCtfActive()) {

			// System.out.println(STR."\{endPoint.isCtfActive()} <-- CtfInteraction's ctfactivity state before being set");
			if (!snagged) {
				System.out.println(" ");
				System.out.println("___________flag interaction:");
				snagged = true;
				System.out.println("flag successfully snagged");
				points += 70;
				setPosition(endPoint);

				endPoint.setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));
				System.out.println(STR."\{endPoint} <-- snagged Flag End address");
				// TODO: could make this functionality automatic in the End act function...
			}
		}

		// endpoint where flag isn't
		if (!endPoint.isCtfActive() && endPoint.getEndID() != 10) {
			points -= 50;
			resetPosition();

		}

		// home interaction
		if (endPoint.getEndID() == 10){
			if (snagged) {
				stashed += 1;
				System.out.println(" ");
				System.out.println("___________home interaction:");
				System.out.println("flag successfully captured");
				// System.out.println(STR."\{endPoint} <-- CtfInteraction's HomeEnd");

				// System.out.println(STR."\{endPoint.isCtfActive()} <-- home ctfactive state before being set");

				snagged = false;
				points += 100;
				// endPoint.setCtfActive(false); // pretty sure this just means home is not gonna give you points for touching?
				// System.out.println(STR."\{endPoint.isCtfActive()} <-- home ctfactive state after being set");
				resetPosition();
				flagSetting();

				// TODO: need to trigger a function that checks whether there are any more flags to print + setting this endpoint as inactive
			}
		}
		changeScore = true;
	}

	private void checkGameOver() {
		// if (ctfEnd){
		// }else{
//
		// }
		if (end == 5) {
			stop = true;
		}
	}

	private void resetPosition() {
		setImage(new Image("file:src/main/resources/images/froggerUp.png", imgSize, imgSize, true, true));
		setX(280);
		setY(679.8 + movement);
	}


	private void setPosition(End endpoint){
		// turn around ANIMAL frog
		setImage(new Image("file:src/main/resources/images/froggerDown.png", imgSize, imgSize, true, true));


		// TODO: reset frogger to be in the middle of endpoint
		setX(endpoint.getX() + 15);
		setY(endpoint.getY());
	}
	public void setCtfEnd(boolean ctfEnd) {
		this.ctfEnd = ctfEnd;
	}
	public void setFlagsToGrab(int flags){
		this.flagsToGrab = flags;
	}
	public boolean getCtfEnd() { return ctfEnd;} // GET GAMEMODE

	public boolean getStop() {
		return end==5;
	}										// TODO: if you've reached end 5 times the game is completed
	//
	public int getPoints() {
		return points;
	}

	public void flagSetting(){
		int previousFlagIndex;
		int prospectiveFlagIndex;

		System.out.println(" ");
		System.out.println("___________flag Setting:");
//		System.out.println("firstly... compare following [inter] with above array");
//		System.out.println(STR."\{inter}");
		System.out.println(" ");

		if (stashed < flagsToGrab){
			previousFlagIndex = endsInCtfOrder[stashed-1];
			prospectiveFlagIndex = endsInCtfOrder[stashed];

			System.out.println(STR."stashed = \{stashed} so you have \{flagsToGrab} more to grab");
			System.out.println(STR."REMINDER: endsInCtfOrder = \{Arrays.toString(endsInCtfOrder)}");
			System.out.println(STR."previousFlagIndex = \{previousFlagIndex} <> prospectiveFlagIndex = \{prospectiveFlagIndex}");


//			System.out.println(STR."\{inter.get(previousFlagIndex)} <-- animal.inter reference to captured flag. Active?: \{inter.get(previousFlagIndex).isCtfActive()}");
//			System.out.println(STR."\{inter.get(prospectiveFlagIndex)} <-- animal.inter reference to next flag. Active?: \{inter.get(prospectiveFlagIndex).isCtfActive()}");
			System.out.println(STR."Captured:\{inter.get(previousFlagIndex).isCtfActive()} <> Next:\{inter.get(prospectiveFlagIndex).isCtfActive()}");

			inter.get(previousFlagIndex).setCtfActive(false);
			inter.get(previousFlagIndex).setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));
			inter.get(prospectiveFlagIndex).setCtfActive(true);
			inter.get(prospectiveFlagIndex).setImage(new Image("file:src/main/resources/images/ctfEnd.png", 60, 60, true, true));

			System.out.println("AFTER CHANGES TO BOTH:");
			System.out.println(STR."Captured:\{inter.get(previousFlagIndex).isCtfActive()} <> Next:\{inter.get(prospectiveFlagIndex).isCtfActive()}");

//			System.out.println(STR."\{inter.get(endsInCtfOrder[stashed-1])}<--flag setting's End(0)");
//			System.out.println(STR."\{inter.get(endsInCtfOrder[stashed]).endID} ^^ flag setting");
//			System.out.println(STR."\{inter.get(endsInCtfOrder[stashed]).isCtfActive()}<-- this is a test to see if the ctfActive has been affected in before flagSetting()");
			// inter.get(endsInCtfOrder[stashed]).
			// I said print them from animal allie

//			inter1.get(endsInCtfOrder[stashed])
//			//.setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));
//			inter1.get(endsInCtfOrder[stashed]).setCtfActive(true);
			System.out.println("Osa was here");
			// ctfEndsArray[endsInCtfOrder[stashed]].setCtfActive(true);
			// ctfEndsArray[endsInCtfOrder[stashed]].setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));
		}
		// TODO: 	- an else that means the game is done
		//  		- make prettier by making a var that represents the end to change
		//  		- tweak so that this function also has the responsibility of disactivating the last end
		//			- IMPORTANT: line 83 and 84 in Level aren't doing anything because all the [] lists need to become
		//						 array lists that get instantiated in the constructor
	}
	public boolean changeScore() {
		if (changeScore) {
			changeScore = false;
			return true;
		}
		return false;
	}
}