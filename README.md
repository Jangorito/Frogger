#### COMP2013_CW_psyea6
# Frogger Game

This is an EmmanSTUDIOs production of Konami's classic Frogger game. It involves a frog trying to cross a treacherous path to reach its destination - and sometimes return!

## Table of Contents
- [Usage](#usage)
- [Maintenance & Development Evolution Documentation](#maintenance-development-evolution-documentation)


## Usage

- Press any key to reach the starting screen
- Choose '**Quick Setup**' or '**Advanced Setup**':
  - **Quick Setup** loads the game based on the preset choices 'Easy', 'Medium' & 'Hard'
  - You must also choose Classic Frogger or Capture the Flag
  - **Advanced Setup** allows more autonomy in how the game is played with configurable options like number of flags and lives.
- Use WASD keys to move the frog.
- Reach the other side without getting hit by cars or falling into the water.


## Maintenance & Development Evolution Documentation
###### Grouped by feature & categorised by Addition, Refactoring & Maintenance

### 1. Actor Movement

**Initial _Addition_**
- **Desc:** Updated Animal.java to include WASD movement.
- **Rationale:** This allows the user to control the Frogger.
- **Implementation Details:** `initializeInputHandlers()` method uses import: 'input.KeyEvent' to map `onKeyPressed` & `onKeyReleased` to methods that move Frogger based on WASD KeyCodes. 

**Subsequent _Refactoring_ @9f735ed1**
  - **Desc:** Obstacles speed are changed
  - **Rationale:** Previous type was `int` but after multiplier implementation, they need to take `double`  
  - **Implementation Details:** changed speed variable in `Obstacle.java`, `Turtle.java` & `WetTurtle.java` to take `double` 

**Further _Maintenance_ @0abe296**
- **Desc:** Modularised method for resetting position
- **Rationale:** Easier to deploy functionality from multiple places with consistency and variation 
- **Implementation Details:** Replaced code blocks with method: `resetPosition()`

### 2. Images

  - **Initial _Addition_ @b3aae74**
    - **Desc:** Designed new background image in PowerPoint
    - **Rationale:** The default image is very basic and not pretty
    - **Implementation Details:** Added file to resource folder and changed src link


  - **Further _Refactoring_**
    1.  @a63bf72 + @96a34d2
    - **Desc:** moving files and updating location
    - **Rationale:** the location of the files was changed for consistency 
    - **Implementation Details:** updated file links in `MyStage.java`, `WetTurtle.java` & `Turtle.java`
    2.  2cd4265e
    - **Desc:** dynamic score display revamped to allow transition between amount of digits
    - **Rationale:** previously left digits on screen erroneously 
    - **Implementation Details:** added ArrayLists: `scoreDigits` & `livesDigits` and cleared them everytime `setNumber()` & `displayLives`


2cd4265e
Main.java: dynamic score display revamped to allow transition between amount of digits

### 3. `Level.java`

  - **Initial _Addition_/Class Creation**
    - **Desc:** Created Level class.
    - **Rationale:** Splitting up `Main.java` responsibility and allow for potentially more levels to be implemented.
    - **Implementation Details:** Implemented a `setupLevel` function in `Level.java` which populates `background` and sets options for Level like obstacle speed


  - **Further _Additions_**
    1.  @c660f75a
    - **Desc:** Finalised class implementation
    - **Rationale:** Level class needed activating from `Main.java` class
    - **Implementation Details:** Level object created and `setupLevel` function called on object
    2.  @e2a9ab6
    - **Desc:** dynamic End instantiation + access 
    - **Rationale:** To allow for future customisation of game from user + to track and control end states during game
    - **Implementation Details:** wrote method `initializeEnds()`
  

  - **Further _Maintenance_**
    1.  @001bb4b
    - **Desc:** Added print statements around End functionality 
    - **Rationale:** As a means to test
    - **Implementation Details:** print statements in around functions to with End initialisation

  - **Subsequent _Refactoring_**
    1. @[2cd4265e](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/2cd4265e4ff3c7d6b26c1afb029c48219322b17c)
    - **Desc:** `initializeEnds()` has to capture both game modes' functionality
    - **Rationale:** this method is called regardless of the game mode
    - **Implementation Details:** Refactored method to do specific ctf only instructions + refactored `getCtfEndsArray` -> `getEndsArray`

### 4. Capture the Flag + Classic Frogger game modes

- **Initial Addition @[35fb9825](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/35fb982585b033533c6b744991a470dfca16b7a8)**
  - **Desc:** Started adding bare bone CTF functionality to `Animal.java`, `End.Java`, `Level.java` & `Main.java`
  - **Rationale:** A Capture the Flag game mode is very cool!
  - **Implementation Details** By Class:
    - `Animal Java`: added vars that represent CTF specific game states, updated `handleCollisions` to call a method responsible for colliding with an End that has a flag
    - `End.java`: getter for whether End is a CTF end
    - `Level.java`: added method that calls setter of game mode on animal object 


- **Further _Additions_**
    1. @[b95ddd6](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/b95ddd629625d17a33366cb697ca6b6ec9449e07)
    - **Desc:** Expanding ctf functionality
    - **Rationale:** Allowing for flags to be assigned/ordered at random
    - **Implementation Details:** Populated array with length of number of flags and populated randomly within `initializeEnds()` function
    2. @[ba0046f](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/ba0046f7dc48d76f3e17a7e74eb8a10e3c22e592)
    - **Desc:** Home Endpoint interaction
    - **Rationale:** Capture the Flag game mode needs a place to stash flags 
    - **Implementation Details:** Wrote a function to handle home endpoint interaction, which was later deleted in subsequent refactoring.
    3. @[001bb4b](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/001bb4bab643dad2a68da9cdb31e18a7278b5eda)
    - **Desc:** Enhanced CTF interactions and consolidated CTF endpoint functionality.
    - **Rationale:** Streamlined flag interactions and improved game logic consistency.
    - **Implementation Details** by Class:
      - `Animal.java`: Added variables for CTF and a `flagSetting()` function to print the next flags. Included additional getters.
    4. @[7bec327](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/7bec327d932f7500e587598d23111a88d67b0db6)
    - **Desc:** Adapted game modes to work with new endPoint (home).
    - **Rationale:** Ensure both normal game mode and CTF mode function correctly with the new game endpoint.
    - **Implementation Details** 
      - `Animal.java`: Adjusted normal game mode logic to incorporate the new home endpoint.
    5. @[9d8aed5](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/9d8aed52d88bd15da0c0244d21f38452996c42ad)
    - **Desc:** Added dynamic flag setting and improved game state handling.
    - **Rationale:** Enhance user experience by dynamically updating flags and game states.
    - **Implementation Details**
        - `Animal.java`: Added `changeLives` boolean to help display life status, modified variables to hold game state options, updated death handling to include decrementing lives, and added a delay before disabling NoMove. Refined `flagSetting()` to handle dying with a flag and needing to reset.
        - `GameConfig.java`: Updated `setDifficulty()` to properly set the number of flags.
    6. @[2cd4265e](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/2cd4265e4ff3c7d6b26c1afb029c48219322b17c)
    - **Desc:** Game ends on correct lives
    - **Rationale:** The game previously checked `end` var against hardcoded integer '5' 
    - **Implementation Details** created variable `noEnds` and fetched correct number of ends from GameConfig

- **Subsequent _Refactoring_**
    1. @[001bb4b](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/001bb4bab643dad2a68da9cdb31e18a7278b5eda)
    - **Desc:** Consolidated CTF endpoint functionality.
    - **Rationale:** Improve code maintainability and functionality by centralizing and refining key interactions.
    - **Implementation Details:** 
        - `Animal.java`: Consolidated the separate home interaction function into a comprehensive `EndInteraction()` method.
        - `Level.java`: Converted lists to ArrayLists to share the actual list of Ends between Level and Animal. Revamped the `ctfEndsArray` to ensure unique numbers for each flag.
    2. @[7bec327](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/7bec327d932f7500e587598d23111a88d67b0db6)
    - **Desc:** Adjusted Level instantiation for home endpoint.
    - **Rationale:** Ensure compatibility across game modes by refining the initialization process.
    - **Implementation Details:**
        - `Level.java`: Moved home instantiation outside the loop to support both normal and CTF game modes.
    3. @[9d8aed5](https://projects.cs.nott.ac.uk/psyea6/comp2013_cw_psyea6/-/commit/9d8aed52d88bd15da0c0244d21f38452996c42ad)
    - **Desc:** Refactored code for better clarity and functionality.
    - **Rationale:** Simplify code and improve maintainability.
    - **Implementation Details:**
        - `Level.java`: Refactored variable names for clarity, updated `setupLevel()` to accept a GameConfig instance, and added comments to `initializeEnds()`.
        - `Main.java`: Enhanced `handle()` method to display lives and score, adjusted `setNumber()` for design changes.


- **Further _Maintenance_**
    1. @ba0046f
    - **Desc:** Deleted function for home endpoint interaction.
    - **Rationale:** Removed obsolete or redundant code after subsequent refactoring.
    - **Implementation Details:** The function written in the `Animal.java` file for home endpoint interaction was deleted in a later commit after being refactored into a more comprehensive method.
    2. @9d8aed5
    - **Desc:** Enhanced user interface and experience.
    - **Rationale:** Improve the visual appeal and usability of the game.
    - **Implementation Details:**
        - `arcadeStyle.css`: Introduced new CSS styles, including button designs, glow effects for labels, and other UI enhancements.
        - `Menu.java`: Restructured menu functionality, added `showInit()` for the landing page, implemented flashing labels and focus management for key events, and updated `showMenu()` to manage game start options.
        - Miscellaneous: Added new fonts and backgrounds for the Frogger game and menu screens.
    
#### CTF Evolution [for demo]

1. [Class creation + collisions + `homeInteraction()` Evolution](src/main/resources/images/homeInteraction Evo.png)
   - var:[`homeInteraction()`](http://localhost:63342/Frogger%20Game/Javadocs/org.example.froggergame/org/example/froggergame/Animal.html#act(long))


2. [The `initializeEnds()` Evolution](src/main/resources/images/initializeEnds()Evo.png)
   - var:[`initializeEnds()`](http://localhost:63342/Frogger%20Game/Javadocs/org.example.froggergame/org/example/froggergame/Level.html#initializeEnds(int,int,int))


3. [The `flagSetting()` Evolution](src/main/resources/images/flagSetting()Evo.png)
   - var:[`flagSetting())`](http://localhost:63342/Frogger%20Game/Javadocs/org.example.froggergame/org/example/froggergame/Animal.html#flagSetting())

### 5. Menu

- **Initial _Addition_ @4c70e53**
    - **Desc:** Created Class
    - **Rationale:** User needs a UI to interact with
    - **Implementation Details:** Made class with methods that represent different Menu 'pages' and buttons that trigger `Main.java`'s `startGame()` and with `gameConfig` getters


- **Further _Additions_**
    1. @9d8aed5
    - **Desc:** Enhanced user interface and experience.
    - **Rationale:** Improve the visual appeal and usability of the game.
    - **Implementation Details:**
      - `Menu.java`: Restructured menu functionality, added `showInit()` for the landing page, implemented flashing labels and focus management for key events, and updated `showMenu()` to manage game start options.
      - Miscellaneous: Added new fonts and backgrounds for the Frogger game and menu screens.
    2. @54fd8fd
    - **Desc:** Added slider feedback for advanced setup in the menu.
    - **Rationale:** Provide visual feedback to users during advanced configuration to enhance usability.
    - **Implementation Details:**  Implemented slider feedback functionality within the `advancedSetup()` method, giving users immediate feedback on their choices to improve interaction clarity and satisfaction.
    3. @2cd4265e
    - **Desc:** The back button needs to trigger a reset of game modes
    - **Rationale:** The default option is Standard so if CTF option was pressed followed by back button then Quick Setup followed by Start Game, the system would CTF selected even though the option displayed Standard 
    - **Implementation Details:** `showMenu()` starts with a `gameConfig.setGameMode(1)`


### 6. GameConfig

- **Initial _Addition_ @4c70e53**
    - **Desc:** Created Class
    - **Rationale:** Centralised class for game configurations and presets
    - **Implementation Details:** Made class with methods getters and setters for game states 