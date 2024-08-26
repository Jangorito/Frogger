package org.example.froggergame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {
    private Stage primaryStage;
    private Main mainApp;
    private GameConfig gameConfig;  // Reference to GameConfig

    public Menu(Stage primaryStage, Main mainApp) {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
        this.gameConfig = new GameConfig(); // Initialize gameConfig
    }

    public void showMenu() {
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // SetUp Buttons
        Button QuickSetUp = new Button("Easy Set Up");
        Button advancedSetUp = new Button("Advanced Set Up");
        QuickSetUp.setOnAction(event -> QuickSetUp(menuLayout));
        advancedSetUp.setOnAction(event -> advancedSetUp(menuLayout));
        // Start button TODO: possibly doesn't need to be here
        // TODO: also, could put this in an ArrayList/map of sorts that holds buttons that could be passed to other functions?
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> mainApp.startGame(primaryStage, gameConfig.getDifficulty(), gameConfig.getLevel()));

        // TODO: following items should prolly be on a settings menu separately_______________+
        // Checkbox for enabling/disabling music
        CheckBox musicCheckBox = new CheckBox("Enable Music");
        musicCheckBox.setSelected(gameConfig.isMusicEnabled());
        musicCheckBox.setOnAction(event -> gameConfig.setMusicEnabled(musicCheckBox.isSelected()));
        // ___________________________________________________________________________________+


        menuLayout.getChildren().addAll(QuickSetUp, advancedSetUp, musicCheckBox, startButton);

        Scene menuScene = new Scene(menuLayout, 600, 800);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void QuickSetUp(VBox menu) {

        Slider noFlags = new Slider(1, 5, 1);

        // ComboBox for difficulty selection
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Easy");
        difficultyComboBox.setOnAction(event -> {
            String selected = difficultyComboBox.getValue();
            switch (selected) {
                case "Easy":
                    gameConfig.setDifficulty(1);
                    break;
                case "Medium":
                    gameConfig.setDifficulty(2);
                    break;
                case "Hard":
                    gameConfig.setDifficulty(3);
                    break;
            }
        });

        // ComboBox for game mode selection
        ComboBox<String> gameModeComboBox = new ComboBox<>();
        gameModeComboBox.getItems().addAll("Normal Frogger", "Capture The Flag");
        gameModeComboBox.setValue("Normal Frogger");
        gameModeComboBox.setOnAction(event -> {
            String selected = gameModeComboBox.getValue();
            switch (selected) {
                case "Normal Frogger":
                    gameConfig.setGameMode(1);
                    break;
                case "Capture The Flag":
                    gameConfig.setGameMode(2);
                    break;
            }
        });


        // Start button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> mainApp.startGame(primaryStage, gameConfig.getDifficulty(), gameConfig.getLevel()));


        // Clear screen and add buttons
        menu.getChildren().clear();
        if (gameConfig.getGameMode() == 2){
            menu.getChildren().addAll(startButton, difficultyComboBox, gameModeComboBox, noFlags);
        } else{
            menu.getChildren().addAll(startButton, difficultyComboBox, gameModeComboBox);
        }


    }
    private void advancedSetUp(VBox menu) {
        Slider lives = new Slider(0, 20, 5);
        Slider speed = new Slider(-0.5, 2, 1);
        Slider noEnds = new Slider(1, 5, 1);

        // Start button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> mainApp.startGame(primaryStage, gameConfig.getDifficulty(), gameConfig.getLevel()));

        menu.getChildren().addAll(lives, speed, noEnds, startButton);
    }
}
