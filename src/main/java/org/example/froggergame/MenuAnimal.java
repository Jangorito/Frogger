package org.example.froggergame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MenuAnimal extends Actor{

    Image up;
    Image left;
    Image right;
    Image leftUp;
    Image rightUp;
    boolean secondFrame;

    double movement = 10; // TODO: work out x distance for animation

    // TODO:
    //  - spawn default Frogger between arrows
    //  - use the 'move' function to code a function that animates frogger
    //    to run between arrows and disappear

    public MenuAnimal(){
        up = new Image("file:src/main/resources/images/froggerUp.png", 40, 40, true, true);
        left = new Image("file:src/main/resources/images/froggerLeft.png", 40, 40, true, true);
        right = new Image("file:src/main/resources/images/froggerRight.png", 40, 40, true, true);
        leftUp = new Image("file:src/main/resources/images/froggerLeftJump.png", 40, 40, true, true);
        rightUp = new Image("file:src/main/resources/images/froggerRightJump.png", 40, 40, true, true);

        setImage(up);
        // TODO: work out the x & y that places the default frogger in between the arrows

    }
    @Override
    public void act(long now) {

    }

    public void leftAnimation(Pane parentPane) {
        // setting the:
        // - lower bound for the Frogger to move
        // - hop distance
        // - amount of time between animations
        double minX = 0;
        double hopDistance = 20;
        Duration hopDuration = Duration.millis(200);

        // using the Timeline function to automate our movement animation
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(hopDuration, e -> {
            // retrieves the x value of the sprite to compare with min bound
            double currentX = getBoundsInParent().getMinX();

            // animate the frog until there's no space to animate
            // TODO: need to remove frogger either through parent deletion or setting invisible
            //  EXTENSION: maybe make a little house for each frogger that works with current animation
            //  ALSO: we need to hop on pc and make different sprite images
            if (currentX - hopDistance <= minX) {
                setLayoutX(minX); // snap to start
                timeline.stop();
            } else {
                move(-hopDistance, 0);
                setImage(secondFrame ? left : leftUp);
                secondFrame = !secondFrame;
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void rightAnimation(Pane parentPane) {
        double maxX = parentPane.getWidth() - getFitWidth();
        double hopDistance = 20;
        Duration hopDuration = Duration.millis(200);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(hopDuration, e -> {
            double currentX = getBoundsInParent().getMinX();
            System.out.print(currentX);
            if (currentX + hopDistance >= maxX) {
                setLayoutX(maxX); // snap to end
                timeline.stop();
            } else {
                move(hopDistance, 0);
                setImage(secondFrame ? right : rightUp);
                secondFrame = !secondFrame;
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

}
