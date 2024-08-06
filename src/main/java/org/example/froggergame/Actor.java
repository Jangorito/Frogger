package org.example.froggergame;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;


public abstract class Actor extends ImageView{

    public void move(double dx, double dy) {                    // move function that sets x and y coordinates of Actor
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public World getWorld() {
        return (World) getParent();
    }

    public double getWidth() {
        return this.getBoundsInLocal().getWidth();
    }

    public double getHeight() {
        return this.getBoundsInLocal().getHeight();
    }

    public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
        ArrayList<A> someArray = new ArrayList<A>();
        for (A actor: getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(this.getBoundsInLocal())) {
                someArray.add(actor);
            } // literally checking whether object collides another with .geometry functions
        }
        return someArray;
        // returns an array that represents two items that have collided
    }
    
//    public void manageInput(KeyEvent event) {
//        // TODO: Need to override in Animal class to handle Frogger input
//        // TODO: multiplayer?
//    }

    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        ArrayList<A> someArray = new ArrayList<A>();
        for (A actor: getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(this.getBoundsInLocal())) {
                someArray.add(actor);
                break;
            }
        }
        return someArray.getFirst();
    } // TODO: find out when you would need to access the item that has collided, perhaps with the animals and cars?

//    public abstract void manageInput(KeyEvent event);

    public abstract void act(long now);

}
