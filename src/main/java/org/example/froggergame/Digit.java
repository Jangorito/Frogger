package org.example.froggergame;

import javafx.scene.image.Image;

public class Digit extends Actor{
	int dim;
	Image im1;
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
	
	public Digit(int n, int dim, int x, int y) {
		im1 = new Image(STR."file:src/main/resources/images/\{n}.png", dim, dim, true, true);
		setImage(im1);
		setX(x);
		setY(y);
	}
	
}
