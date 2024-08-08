package org.example.froggergame;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
	int carD = 0; // counter for death animation
	double w = 800; // max height reached by Frogger
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
		handleCollisions(now);
		checkInteractions();
		checkGameOver();
	}

	private void checkBoundaries() {										// TODO: implement bounds functionality
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

	private void handleCollisions(long now) {								// TODO: are there any more collisions to add?
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
				setX(300);
				setY(679.8+movement);
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
				setX(300);
				setY(679.8+movement);
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
			handleEndInteraction();
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
	}

	private void handleWetTurtleInteraction() {
		WetTurtle wetTurtle = getIntersectingObjects(WetTurtle.class).get(0);
		if (wetTurtle.isSunk()) {
			waterDeath = true;
		} else {
			move(-1, 0);
		}
	}

	private void handleEndInteraction() {
		End endPoint = getIntersectingObjects(End.class).get(0);
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

	private void checkGameOver() {
		if (end == 5) {
			stop = true;
		}
	}

	private void resetPosition() {
		setX(300);
		setY(679.8 + movement);
	}


	public boolean getStop() {
		return end==5;
	}										// TODO: if you've reached end 5 times the game is completed
	
	public int getPoints() {
		return points;
	}
	
	public boolean changeScore() {
		if (changeScore) {
			changeScore = false;
			return true;
		}
		return false;
		
	}
	

}
// TODO: improvements can be made to this class!!