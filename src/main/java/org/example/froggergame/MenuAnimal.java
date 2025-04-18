package org.example.froggergame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.security.Principal;
import java.util.ArrayList;

public class MenuAnimal extends Actor{

    Image up;
    Image left;
    Image right;
    Image leftUp;
    Image rightUp;
    boolean secondFrame;
    Integer number;

    double movement = 10; // TODO: work out x distance for animation

    // TODO:
    //  - spawn default Frogger between arrows
    //  - use the 'move' function to code a function that animates frogger
    //    to run between arrows and disappear

    public MenuAnimal(int spriteNo){
        up = new Image("file:src/main/resources/images/froggerUp.png", 40, 40, true, true);
        left = new Image("file:src/main/resources/images/froggerLeft.png", 40, 40, true, true);
        right = new Image("file:src/main/resources/images/froggerRight.png", 40, 40, true, true);
        leftUp = new Image("file:src/main/resources/images/froggerLeftJump.png", 40, 40, true, true);
        rightUp = new Image("file:src/main/resources/images/froggerRightJump.png", 40, 40, true, true);

        setImage(up);
        number = spriteNo;

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
                // setLayoutX(minX); // snap to start
                // parentPane.getChildren().remove(this); // kill object
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
                // setLayoutX(maxX); // snap to end
                // parentPane.getChildren().remove(this); // kill object
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

    public void rightTurn(){
        setImage(right);
    }
    public void leftTurn(){
        setImage(left);
    }
    public void faceUp(){
        setImage(up);
    }

    private boolean checkStop(double cur, double hop, double target) {
        System.out.println(STR."hit checkStop() with cur=\{cur}, hop=\{hop}, target=\{target}");
        if (hop > 0) {
            return  (cur + hop > target);
        } else return cur + hop < target;
        //TODO: left button still works using this function so check the maths of this logic here^
    }
    public void btmRowMove(double distance){

        double hopDistance = distance/2;
        double target = (getBoundsInParent().getMinX() + distance);
        System.out.println(STR."\nbtmRowMove()'ing sprite \{getNo()}, distance = \{distance}, target = \{target}");
        boolean quickImg;
        if (distance > 0){

            if (getImage() != right) {
                quickImg = true;
            }
            else{
                quickImg = false;
            }
        } else{
            if (getImage() != left) {
                quickImg = true;
            }
            else{
                quickImg = false;
            }

        }

        Duration hopDuration = Duration.millis(500);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(hopDuration, e -> {
            double currentX = getBoundsInParent().getMinX();
            if (checkStop(currentX, hopDistance, target)){
                faceUp();
                System.out.println("hit timeline stop");
                timeline.stop();
            }
            else{
                if (quickImg){
                    rightTurn();
                    System.out.println("rightTurn()");
                }
                move(hopDistance, 0);
                System.out.println("move()");
                if (currentX == target){
                    faceUp();
                    System.out.println("just told them man face up");
                }
            }
        });

//        System.out.println("^---------------^\n");
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

    }

    public int getNo(){
        return number;
    }

}
