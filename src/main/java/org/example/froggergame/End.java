package org.example.froggergame;

import javafx.scene.image.Image;

public class End extends Actor{
	boolean activated = false;
	boolean ctfActive = false;
	@Override
	public void act(long now) {
		// TODO animation that shows it's a CTF End?

	}
	
	public End(int x, int y) {
		setX(x);
		setY(y);
		setImage(new Image("file:src/main/resources/images/End.png", 60, 60, true, true));
	}
	
	public void setEnd() {
		setImage(new Image("file:src/main/resources/images/FrogEnd.png", 60, 60, true, true));
		activated = true;
	}
	
	public boolean isActivated() {
		return activated;
	}
	public boolean isCtfActive() { return ctfActive; }
	

}
