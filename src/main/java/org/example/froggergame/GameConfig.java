package org.example.froggergame;

/**
 * Represents the configuration settings for the Frogger game.
 * <p>
 * The {@code GameConfig} class contains various settings such as game difficulty,
 * level, lives, speed multiplier, character selection, and audio settings.
 * These settings can be used to customize the game experience.
 * </p>
 */
public class GameConfig {
    /** The difficulty level of the game (1 = easy, 2 = medium, 3 = hard). */
    private int difficulty;

    /** The current level in the game. */
    private int level;

    /** The number of lives available to the player. */
    private int lives;

    /** The speed multiplier affecting the game speed. */
    private double speedMultiplier;

    /** The character selected for the game. */
    private String character;

    /** Whether the background music is enabled. */
    private boolean musicEnabled;

    /** The volume level of the background music (0-10). */
    private int volumeLevel;

    /** The game mode (e.g., normal mode or capture-the-flag mode). */
    private int gameMode;

    /** The number of end points required to complete the level. */
    private int noEnds;

    /** The number of flags required to complete the capture-the-flag mode. */
    private int noFlags;

    /**
     * Constructs a {@code GameConfig} instance with default settings.
     * <ul>
     *   <li>Difficulty: 1 (easy)</li>
     *   <li>Level: 1</li>
     *   <li>Lives: 3</li>
     *   <li>Character: "Frogger"</li>
     *   <li>Music Enabled: true</li>
     *   <li>Volume Level: 5</li>
     *   <li>Speed Multiplier: 1.0</li>
     *   <li>Game Mode: 1</li>
     *   <li>Number of Ends: 5</li>
     *   <li>Number of Flags: 3</li>
     * </ul>
     */
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

    /** Getters and setters for each property. */
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
        System.out.println("just set difficulty");
        // easy
        if (mode == 1){
            this.lives = 15;
            this.speedMultiplier = 0.75;
            if (this.getGameMode() == 1){
                setNoFlags(3);
            }
        }

        // medium
        if (mode == 2){
            this.lives = 10;
            if (this.getGameMode() == 1){
                setNoFlags(4);
            }

        }

        // hard
        if (mode == 3){
            this.lives = 5;
            this.speedMultiplier = 1.5;
            if (this.getGameMode() == 1){
                setNoFlags(5);
            }

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

    // TODO: Load configuration from a file
    public void loadConfig(String filepath) {
        // Load settings from a file or resource
    }

    // TODO: Save configuration to a file
    public void saveConfig(String filepath) {
        // Save settings to a file or resource
    }
}
