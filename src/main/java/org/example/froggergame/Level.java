package org.example.froggergame;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class Level {
    private MyStage background;
    private Animal animal;
    public End[] ends;
    public ArrayList<End> ends1;
    public End home;
    // int ctfEndNo;
    int[] ctfEndsArray;
    // private double log1Speed = 0.75;
    // private double log2Speed = -2; // TODO: make sure multiplier still works with minuses
    // private double turtleSpeed = -1;
    // private double vehicleSpeed = 1;
    // private double car2Speed = -5;
    private double multiplier;

    public Level(MyStage background, Animal animal) {
        this.background = background;
        this.animal = animal;
        this.multiplier = 1;


//        this.ends = new End[0];
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

    public void setupLevel(int mode, int difficulty) {
        // setting default speeds
        double log1Speed = 0.75;
        double log2Speed = -2; //
        double turtleSpeed = -1;
        double vehicleSpeed = 1;
        double car2Speed = -5;

        // setting speed and end based on difficulty and game mode
        setSpeeds(difficulty);
        setEndCond(mode);

        // Add background image
        BackgroundImage froggerBack = new BackgroundImage("file:src/main/resources/images/Frogger Background V7.png");
        background.add(froggerBack);


        // Add logs
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 0, 166, log1Speed*multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 220, 166, log1Speed*multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 440, 166, log1Speed*multiplier));
        background.add(new Log("file:src/main/resources/images/logs.png", 300, 0, 276, log2Speed*multiplier));
        background.add(new Log("file:src/main/resources/images/logs.png", 300, 400, 276, log2Speed*multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 50, 329, log1Speed*multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 270, 329, log1Speed*multiplier));
        background.add(new Log("file:src/main/resources/images/log3.png", 150, 490, 329, log1Speed*multiplier));

        // Add turtles
        background.add(new Turtle(500, 376, turtleSpeed*multiplier, 130, 130));
        background.add(new Turtle(300, 376, turtleSpeed*multiplier, 130, 130));
        background.add(new WetTurtle(700, 376, turtleSpeed*multiplier, 130, 130));
        background.add(new WetTurtle(600, 217, turtleSpeed*multiplier, 130, 130));
        background.add(new WetTurtle(400, 217, turtleSpeed*multiplier, 130, 130));
        background.add(new WetTurtle(200, 217, turtleSpeed*multiplier, 130, 130));

        // ends setup
        initializeEnds(5, 60, 600);
        animal.inter = getCtfEndsArray();
        animal.endsInCtfOrder = getInOrder();
        System.out.println(STR."\{this.ends1} <-- this.ends1 outside of intialize Ends");
        System.out.println(STR."\{animal.inter} <-- animal.inter outside of intialize Ends");
        System.out.println(STR."\{Arrays.toString(animal.endsInCtfOrder)} <-- animal.endsInCtfOrder outside of intialize Ends");

//        background.add(new End(13, 96)); // 13
//        background.add(new End(141, 96)); // 141
//        background.add(new End(141 + 141 - 13, 96)); // 269
//        background.add(new End(141 + 141 - 13 + 141 - 13 + 1, 96)); // 398
//        background.add(new End(141 + 141 - 13 + 141 - 13 + 141 - 13 + 3, 96)); // 528

        // Add animal
        background.add(animal);

        // Add obstacles
        background.add(new Obstacle("file:src/main/resources/images/truck1Right.png", 0, 649, vehicleSpeed*multiplier, 120, 120));
        background.add(new Obstacle("file:src/main/resources/images/truck1Right.png", 300, 649, vehicleSpeed*multiplier, 120, 120));
        background.add(new Obstacle("file:src/main/resources/images/truck1Right.png", 600, 649, vehicleSpeed*multiplier, 120, 120));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 100, 597, turtleSpeed*multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 250, 597, turtleSpeed*multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 400, 597, turtleSpeed*multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 550, 597, turtleSpeed*multiplier, 50, 50));
        background.add(new Obstacle("file:src/main/resources/images/truck2Right.png", 0, 540, vehicleSpeed*multiplier, 200, 200));
        background.add(new Obstacle("file:src/main/resources/images/truck2Right.png", 500, 540, vehicleSpeed*multiplier, 200, 200));
        background.add(new Obstacle("file:src/main/resources/images/car1Left.png", 500, 490, car2Speed*multiplier, 50, 50));

        // Add score digit
        background.add(new Digit(0, 30, 360, 25));
    }

    public void setSpeeds(int lvl){
        if (lvl == 1){
            this.multiplier = 0.75;
        } 
        if (lvl == 3){
            this.multiplier = 1.5;
        }
    }

    public void setEndCond(int mode){
        if (mode == 1) {
            animal.setCtfEnd(true);
            animal.setFlagsToGrab(3);
        }
    }

    public void initializeEnds(int numberOfEnds, int endWidth, int windowWidth) { // TODO: will prolly encapsulate functionality in separate class
        // works out distance between number of ends, puts ends in array and sets CTF specific conditions
        System.out.println(" ");
        System.out.println("___Initialize Ends____________________:");

        Random random = new Random();
        int[] ctfEndsArray = new int[numberOfEnds-1];
        int count = 0;
        while (count < ctfEndsArray.length){
            int randomNum = random.nextInt(numberOfEnds);
            if (!contains(ctfEndsArray, randomNum)){
                ctfEndsArray[count] = randomNum;
                count += 1;
            }
        }
//        for (int i = 0; i < ctfEndsArray.length; i++) {
//            int randomNum = random.nextInt(numberOfEnds);
//            // while (randomNum )
//            ctfEndsArray[i] = random.nextInt(numberOfEnds); // Populate array with random indices
//            ctfEndsArray
//        }

        this.ends1 = new ArrayList<End>();

        // this.ends = new End[numberOfEnds];  // Array to hold the End objects
        int remainingSpace = windowWidth - (numberOfEnds * endWidth);
        int gap = remainingSpace / (numberOfEnds + 1);

        for (int i = 0; i < numberOfEnds; i++) {
            int xPosition = gap + i * (endWidth + gap);
            // this.ends[i] = new End(xPosition, 96, i, numberOfEnds);  // Initialize and store in the array
            this.ends1.add(new End(xPosition, 96, i, numberOfEnds)); // Initialize and store in the array

            if (i == ctfEndsArray[0] && animal.getCtfEnd()){ // TODO: turn this into a function can set the next object in the array to be the next active endpoint
                // System.out.println(STR."\{this.ends[i]} <-- unsnagged/(1st) Flag End (original?) address");
                // System.out.println(STR."\{this.ends[i].isCtfActive()} <-- Level's orignal flag state before being set");
                // this.ends[i].setCtfActive(true);
                // System.out.println(STR."\{this.ends[i].isCtfActive()} <-- Level's orignal flag state after being set");
                // this.ends[i].setImage(new Image("file:src/main/resources/images/ctfEnd.png", 60, 60, true, true));

                System.out.println(STR."\{this.ends1.get(i)} <-- unsnagged/(1st) Flag End (original?) address with an index of: ");
                System.out.println(STR."\{this.ends1.get(i).endID}");
                System.out.println(STR."\{this.ends1.get(i).isCtfActive()} <-- Level's orignal flag state before being set");
                this.ends1.get(i).setCtfActive(true);
                System.out.println(STR."\{this.ends1.get(i).isCtfActive()} <-- Level's orignal flag state after being set");
                this.ends1.get(i).setImage(new Image("file:src/main/resources/images/ctfEnd.png", 60, 60, true, true));

                this.ends1.get(i).setCtfActive(true);
                this.ends1.get(i).setImage(new Image("file:src/main/resources/images/ctfEnd.png", 60, 60, true, true));
            }

            if (i == 2 && animal.getCtfEnd()){ // setting home endPoint to be in the middle
                this.home = new End(xPosition, (int) (679.8 + 13.3333333*2), 10, 10);
            }
            // background.add(this.ends[i]);  // Add each End to the background
            background.add(this.ends1.get(i));

        }
        setCtfEndsArray(ctfEndsArray);
        background.add(this.home);
        System.out.println("___Initialize Ends____________________");
        System.out.println(" ");
    }

    public ArrayList<End> getCtfEndsArray(){
        return ends1;
    }
    public void setCtfEndsArray(int[] array){
        this.ctfEndsArray = array;
    }
    public int[] getInOrder() { return ctfEndsArray; }

    public boolean contains(int[] array, int value) {
        return Arrays.stream(array).anyMatch(i -> i == value);
    }

}
