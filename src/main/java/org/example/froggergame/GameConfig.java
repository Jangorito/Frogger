package org.example.froggergame;

public class GameConfig {
    private int difficulty;
    private int level;
    private int lives;
    private double speedMultiplier;
    private String character;
    private boolean musicEnabled;
    private int volumeLevel;
    private int gameMode;
    private int noEnds;


    private int noFlags;

    public GameConfig() {
        // Set default values
        this.difficulty = 1;
        this.level = 1;
        this.lives = 3;
        this.character = "Frogger";
        this.musicEnabled = true;
        this.volumeLevel = 5;
        this.speedMultiplier = 1;
        this.gameMode = 1;
        this.noEnds = 5;
        this.noFlags = 3;
    }

    // Getters and setters for each property

    public int getNoFlags() {
        return noFlags;
    }

    public void setNoFlags(int noFlags) {
        this.noFlags = noFlags;
    }

    public double getMultiplier(){
        return speedMultiplier;
    }
    public void setMultiplier(double multi){
        this.speedMultiplier = multi;
    }
    public int getNoEnds() {
        return noEnds;
    }
    public void setNoEnds(int noEnds) {
        this.noEnds = noEnds;
    }
    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode (int gameMode) {
        this.gameMode = gameMode;
    }

    public void setDifficulty(int mode){
        // easy
        if (mode == 1){
            this.lives = 15;
            this.speedMultiplier = 0.75;
        }

        // medium
        if (mode == 2){
            this.lives = 10;
        }

        // hard
        if (mode == 3){
            this.lives = 5;
            this.speedMultiplier = 1.5;
        }
    }
    public int getDifficulty() {
        return difficulty;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
    }

    public int getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(int volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    // Optional: Load configuration from a file
    public void loadConfig(String filepath) {
        // Load settings from a file or resource
    }

    // Optional: Save configuration to a file
    public void saveConfig(String filepath) {
        // Save settings to a file or resource
    }
}
