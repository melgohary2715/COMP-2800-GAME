package gamestates;

// Enum to represent the different game states
public enum Gamestate {
    PLAYING,  // State for when the game is being played
    MENU,     // State for when the game is in the menu
    QUIT;     // State for when the game is being quit

    // Static variable to keep track of the current state
    public static Gamestate state = MENU;
}
