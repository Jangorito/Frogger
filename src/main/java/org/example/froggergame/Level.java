package org.example.froggergame;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class Level {
    private MyStage background;
    private Animal animal;
    public End[] ends;
    public ArrayList<End> endArrayList;
    public End home;
    // int ctfEndNo;
    int[] ctfEndsArray;

    public Level(MyStage background, Animal animal) {
        this.background = background;
        this.animal = animal;
    }

    // 3 sets of logs; y: 166, 276, 329; s: .75, -2
    // 2 sets of turtles; y: 376, 217; s: -1
    // 2 sets of cars; y: 597, 490; s: -1, -5
    // 2 sets of trucks; y: 649, 540; s: 1

    // .75 LOGS
    // -1 TURTLES
    // -2 LOGS
    // .75 LOGS
    // -1 TURTLES
    // - 5 CAR
    // 1 TRUCK
    // -1 CAR
    // 1 TRUCK

    public void setupLevel(GameConfig options) {
        // setting default speeds
        double log1Speed = 0.75;
        double log2Speed = -2; //
        double turtleSpeed = -1;
        double vehicleSpeed = 1;
        double car2Speed = -5;
        // private double log1Speed = 0.75;
        // private double log2Speed = -2; // TODO: make sure multiplier still works with minuses
        // private double turtleSpeed = -1;
        // private double vehicleSpeed = 1;
        // private double car2Speed = -5;
        // TODO: can kill moretime
        double multiplier = options.getMultiplier();

        // gameConfig options: lives, speed, ends & TODO: obstacles

        // setting speed and end based on difficulty and game mode
        // setSpeeds(difficulty);
        // setEndCond(mode);

        // Add background image
        BackgroundImage froggerBack = new BackgroundImage("file:src/main/resources/images/Frogger Background V9.png");
        background.add(froggerBack);

        // Add logs
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 0, 166, log1Speed* multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 220, 166, log1Speed* multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 440, 166, log1Speed* multiplier));
        background.add(new Log("file:src/main/resources/images/logs.png", 300, 0, 276, log2Speed* multiplier));
        background.add(new Log("file:src/main/resources/images/logs.png", 300, 400, 276, log2Speed* multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 50, 329, log1Speed* multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 270, 329, log1Speed* multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 490, 329, log1Speed* multiplier));

        // Add turtles
        background.add(new Turtle(500, 376, turtleSpeed* multiplier, 130, 130));
        background.add(new Turtle(300, 376, turtleSpeed* multiplier, 130, 130));
        background.add(new WetTurtle(700, 376, turtleSpeed* multiplier, 130, 130));
        background.add(new WetTurtle(600, 217, turtleSpeed* multiplier, 130, 130));
        background.add(new WetTurtle(400, 217, turtleSpeed* multiplier, 130, 130));
        background.add(new WetTurtle(200, 217, turtleSpeed* multiplier, 130, 130));

        // ends setup
        initializeEnds(options.getNoEnds(), 60, 600);
        animal.inter = getCtfEndsArray();
        animal.endsInCtfOrder = getInOrder();

//        background.add(new End(13, 96)); // 13
//        background.add(new End(141, 96)); // 141
//        background.add(new End(141 + 141 - 13, 96)); // 269
//        background.add(new End(141 + 141 - 13 + 141 - 13 + 1, 96)); // 398
//        background.add(new End(141 + 141 - 13 + 141 - 13 + 141 - 13 + 3, 96)); // 528

        // Add animal
        background.add(animal);

        // Add obstacles
        background.add(new Obstacle("file:src/main/resources/images/truck1Right.png", 0, 649, vehicleSpeed* multiplier, 120, 120));
        background.add(new Obstacle("file:src/main/resources/images/truck1Right.png", 300, 649, vehicleSpeed* multiplier, 120, 120));
        background.add(new Obstacle("file:src/main/resources/images/truck1Right.png", 600, 649, vehicleSpeed* multiplier, 120, 120));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 100, 597, turtleSpeed* multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 250, 597, turtleSpeed* multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 400, 597, turtleSpeed* multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 550, 597, turtleSpeed* multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/truck2Right.png", 0, 540, vehicleSpeed* multiplier, 200, 200));
        background.add(new Obstacle("file:src/main/resources/images/truck2Right.png", 500, 540, vehicleSpeed* multiplier, 200, 200));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 500, 490, car2Speed* multiplier, 50, 50));

        // Add score digit

        // Add life digit
        animal.changeLives = true;
    }

    public void initializeEnds(int numberOfEnds, int endWidth, int windowWidth) {

        // randomising the order of flags to capture TODO: surely this is only relevant if there are flags to capture??
        Random random = new Random();
        int[] endsInCtfOrderList = new int[numberOfEnds-1];
        int count = 0;
        while (count < endsInCtfOrderList.length){
            int randomNum = random.nextInt(numberOfEnds);
            if (!contains(endsInCtfOrderList, randomNum)){
                endsInCtfOrderList[count] = randomNum;
                count += 1;
            }
        }
        // giving the ordered list to var after computing ^
        setInOrder(endsInCtfOrderList);

        // ArrayList that will hold the end objects in instantiation order (not including home)
        this.endArrayList = new ArrayList<End>();

        // works out distance between number of ends
        int remainingSpace = windowWidth - (numberOfEnds * endWidth);
        int gap = remainingSpace / (numberOfEnds + 1);

        // puts ends in array and sets CTF specific conditions
        for (int i = 0; i < numberOfEnds; i++) {
            int xPosition = gap + i * (endWidth + gap);

            // Initialize end and store in the array
            this.endArrayList.add(new End(xPosition, 96, i, numberOfEnds));

            // setting the first flag if the game mode is CTF
            if (i == endsInCtfOrderList[0] && animal.getCtfGameMode()){
                this.endArrayList.get(i).setCtfActive(true);
                this.endArrayList.get(i).setImage(new Image("file:src/main/resources/images/ctfEnd.png", 60, 60, true, true));
            }

            background.add(this.endArrayList.get(i));
        }

        // adding home endPoint
        this.home = new End(270, (int) (679.8 + 13.3333333*2), 10, 10);
        background.add(this.home);
    }

    public ArrayList<End> getCtfEndsArray(){
        return endArrayList;
    }
    public void setInOrder(int[] array){
        this.ctfEndsArray = array;
    }
    public int[] getInOrder() { return ctfEndsArray; }
    public boolean contains(int[] array, int value) {
        return Arrays.stream(array).anyMatch(i -> i == value);
    }

}
