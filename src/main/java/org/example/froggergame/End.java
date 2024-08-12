package org.example.froggergame;

import javafx.scene.image.Image;

public class End extends Actor{
	boolean activated = false;
	boolean ctfActive = false;
	int endID;
	int noEnds;

	@Override
	public void act(long now) {
		// TODO animation that shows it's a CTF End?
		if (isCtfActive()){

		} else {

		}
	}
	
	public End(int x, int y, int num, int no) {
		setX(x);
		setY(y);
		setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));
		setEndID(num);
		setNoEnds(no);

	}

	private void setEndID(int num){
		endID = num;
	}

	public void setNoEnds(int noEnds) {
		this.noEnds = noEnds;
	}

	public void setEnd() {
		setImage(new Image("file:src/main/resources/images/FrogEnd.png", 60, 60, true, true));
		activated = true;
	}

	public boolean isActivated() {
		return activated;
	}
	public boolean isCtfActive() { return ctfActive; }

	public void setCtfActive(boolean ctfActive) {
		this.ctfActive = ctfActive;
	}

}
