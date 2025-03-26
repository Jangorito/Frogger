package org.example.froggergame;

import javafx.scene.image.Image;

public class MenuAnimal extends Actor{

    Image left;
    Image right;
    Image leftUp;
    Image rightUp;

    double movement = 10; // TODO: work out x distance for animation

    // TODO:
    //  - spawn default Frogger between arrows
    //  - use the 'move' function to code a function that animates frogger
    //    to run between arrows and disappear

    public MenuAnimal(){
        setImage(new Image("file:src/main/resources/images/froggerUp.png", 40, 40, true, true));
        // TODO: work out the x & y that places the default frogger in between the arrows
        setX(280);
        setY(679.8 + movement);

    }
    @Override
    public void act(long now) {

    }
}
