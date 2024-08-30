package org.example.froggergame;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.InputStream;

public class Menu {
    private Stage primaryStage;
    private Main mainApp;
    private GameConfig gameConfig;  // Reference to GameConfig
    private VBox rootLayout;  // Root layout to maintain the same screen
    Font font;
    boolean init;

    public Menu(Stage primaryStage, Main mainApp) {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
        this.gameConfig = new GameConfig(); // Initialize gameConfig
        this.rootLayout = new VBox();
        // Use class loader to get the font as a resource stream
        InputStream fontStream = getClass().getResourceAsStream("/RetrovilleNC.ttf");
        this.font = Font.loadFont(fontStream, 16);
        this.init = true;
        initializeInputHandlers();

    }

    private void initializeInputHandlers() {
        rootLayout.setOnKeyPressed(this::handleKeyPressed);
    }

    private void handleKeyPressed(KeyEvent event) {
        if (init){
            showMenu();
            init = false;
        }
    }

    public void showInit(){
        rootLayout.getChildren().clear();
        rootLayout.setPadding(Insets.EMPTY);  // Ensure no padding
        rootLayout.setSpacing(0);  // Set spacing to 0
        rootLayout.setStyle("-fx-alignment: center;"); // Only align center, no padding

        // Load the image from resources
        Image backgroundImage = new Image("file:src/main/resources/images/Frogger Front Screen 2.png");

        // Create a BackgroundSize to cover the VBox
        BackgroundSize backgroundSize = new BackgroundSize(
                600,                        // Set width to cover the full scene width
                800,                        // Set height to cover the full scene height
                false,                      // Not contain, but cover
                false,                      // Not contain, but cover
                false,                      // Do not preserve ratio, fill entire area
                false);                     // Do not maintain original aspect ratio

        // Create a BackgroundImage
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);

        // Set the Background of the VBox
        rootLayout.setBackground(new Background(bgImage));

        Scene menuScene = new Scene(rootLayout, 600, 800);

        // Link the CSS file to the scene
        menuScene.getStylesheets().add("file:src/main/resources/styles/arcadeStyle.css");

        Label initText = new Label("PRESS ANY BUTTON TO START");
        initText.getStyleClass().add("glow-label");

        // Flashing effect with FadeTransition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), initText);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.3);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

        rootLayout.getChildren().addAll(initText);

        rootLayout.setFocusTraversable(true); // allows the VBox to gain focus

        primaryStage.setScene(menuScene);
        primaryStage.show();
        rootLayout.requestFocus();

    }

    public void showMenu() {
        rootLayout.getChildren().clear();

        // SetUp Buttons & actions
        Button QuickSetUp = new Button("Quick Set Up");
        Button advancedSetUp = new Button("Advanced Set Up");
        QuickSetUp.setOnAction(event -> QuickSetUp());
        advancedSetUp.setOnAction(event -> advancedSetUp());
        QuickSetUp.setPrefWidth(316.0);

        rootLayout.getChildren().addAll(QuickSetUp, advancedSetUp);

    }


    private void QuickSetUp() {
        // Clear existing children in the root layout
        rootLayout.getChildren().clear();

        // Create GridPane layout for Quick Setup
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // ComboBox for difficulty selection
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Easy");
        gameConfig.setDifficulty(1);
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
        gameModeComboBox.getItems().addAll("Standard Frogger", "Capture The Flag");
        gameModeComboBox.setValue("Standard Frogger");
        gameModeComboBox.setOnAction(event -> {
            String selected = gameModeComboBox.getValue();
            switch (selected) {
                case "Standard Frogger":
                    gameConfig.setGameMode(1);
                    break;
                case "Capture The Flag":
                    gameConfig.setGameMode(2);
                    break;
            }
        });


        // Start button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> mainApp.startGame(primaryStage, gameConfig));

        // Back button
        Button backButton = new Button("Go Back");
        backButton.setOnAction(event -> showMenu());

        // Add elements to GridPane
        gridPane.add(new Label("Select Difficulty:"), 0, 0);
        gridPane.add(difficultyComboBox, 1, 0);
        gridPane.add(new Label("Select Game Mode:"), 0, 1);
        gridPane.add(gameModeComboBox, 1, 1);
        gridPane.add(startButton, 0, 3, 2, 1); // Span 2 columns for Start button
        gridPane.add(backButton, 0, 4, 2, 1);

        // centre buttons
        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setHalignment(backButton, HPos.CENTER);

        // Add GridPane to rootLayout
        rootLayout.getChildren().add(gridPane);

    }
    private void advancedSetUp() {
        // Clear existing children in the root layout
        rootLayout.getChildren().clear();

        // GridPane layout for Advanced Setup
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Sliders for advanced settings
        Label flagLabel = new Label("Number of Flags:");
        Slider lives = new Slider(0, 20, 5);
        lives.setShowTickLabels(true);
        lives.setShowTickMarks(true);
        Label livesValueLabel = new Label(String.valueOf((int) lives.getValue()));

        Slider speed = new Slider(-0.5, 2, 1);
        speed.setShowTickLabels(true);
        speed.setShowTickMarks(true);
        Label speedValueLabel = new Label(String.format("%.2f", speed.getValue()));

        Slider noEnds = new Slider(1, 5, 5);
        noEnds.setShowTickLabels(true);
        noEnds.setShowTickMarks(true);
        Label noEndsValueLabel = new Label(String.valueOf((int) noEnds.getValue()));

        Slider noFlags = new Slider(1, 5, 1);
        noFlags.setShowTickLabels(true);
        noFlags.setShowTickMarks(true);
        Label noFlagsValueLabel = new Label(String.valueOf((int) noFlags.getValue()));

        // Add listeners to update labels when sliders are moved
        lives.valueProperty().addListener((observable, oldValue, newValue) -> {
            livesValueLabel.setText(String.valueOf(newValue.intValue()));
        });

        speed.valueProperty().addListener((observable, oldValue, newValue) -> {
            speedValueLabel.setText(String.format("%.2f", newValue.doubleValue()));
        });

        noEnds.valueProperty().addListener((observable, oldValue, newValue) -> {
            noEndsValueLabel.setText(String.valueOf(newValue.intValue()));
        });

        noFlags.valueProperty().addListener((observable, oldValue, newValue) -> {
            noFlagsValueLabel.setText(String.valueOf(newValue.intValue()));
        });


        // ComboBox for game mode selection
        ComboBox<String> gameModeComboBox = new ComboBox<>();
        gameModeComboBox.getItems().addAll("Standard Frogger", "Capture The Flag");
        gameModeComboBox.setValue("Standard Frogger");
        gameModeComboBox.setOnAction(event -> {
            String selected = gameModeComboBox.getValue();
            switch (selected) {
                case "Standard Frogger":
                    if (gridPane.getChildren().contains(flagLabel)) {
                        gridPane.getChildren().remove(flagLabel);
                        gridPane.getChildren().remove(noFlags);
                        gridPane.getChildren().remove(noFlagsValueLabel);
                    }
                    gameConfig.setGameMode(1);
                    break;
                case "Capture The Flag":
                    gameConfig.setGameMode(2);
                    gridPane.add(flagLabel, 0, 4);
                    gridPane.add(noFlags, 1, 4);
                    gridPane.add(noFlagsValueLabel, 2, 4);
                    break;
            }
        });

        // Checkbox for enabling/disabling music
        CheckBox musicCheckBox = new CheckBox("Enable Music");
        musicCheckBox.setFont(this.font);
        musicCheckBox.setSelected(gameConfig.isMusicEnabled());
        musicCheckBox.setOnAction(event -> gameConfig.setMusicEnabled(musicCheckBox.isSelected()));

        // Start button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> {
            // Retrieve slider values and update gameConfig
            gameConfig.setLives((int) lives.getValue());
            gameConfig.setMultiplier(speed.getValue());
            gameConfig.setNoEnds((int) noEnds.getValue());
            if (gameConfig.getGameMode() == 2) { // Capture the Flag mode
                gameConfig.setNoFlags((int) noFlags.getValue());
            }

            // Start the game with the updated configuration
            mainApp.startGame(primaryStage, gameConfig);
        });


        // Back button
        Button backButton = new Button("Go Back");
        backButton.setOnAction(event -> showMenu());

        // Add elements to GridPane
        gridPane.add(new Label("Number of Lives:"), 0, 0);
        gridPane.add(lives, 1, 0);
        gridPane.add(livesValueLabel, 2, 0); // Display current value of 'lives'

        gridPane.add(new Label("Game Speed:"), 0, 1);
        gridPane.add(speed, 1, 1);
        gridPane.add(speedValueLabel, 2, 1); // Display current value of 'speed'

        gridPane.add(new Label("Number of Ends:"), 0, 2);
        gridPane.add(noEnds, 1, 2);
        gridPane.add(noEndsValueLabel, 2, 2); // Display current value of 'noEnds'

        gridPane.add(new Label("Select Game Mode:"), 0, 3);
        gridPane.add(gameModeComboBox, 1, 3);

        gridPane.add(musicCheckBox, 0, 5);

        gridPane.add(startButton, 0, 6, 2, 1);
        gridPane.add(backButton, 0, 7, 2, 1);

        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setHalignment(backButton, HPos.CENTER);


        // Add GridPane to rootLayout
        rootLayout.getChildren().add(gridPane);
    }
}
