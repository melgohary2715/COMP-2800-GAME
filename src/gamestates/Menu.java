package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[2]; // Array of menu buttons
    private BufferedImage backgroundImg, backgroundImgPink; // Background images
    private int menuX, menuY, menuWidth, menuHeight; // Dimensions and position for the menu

    // Constructor for Menu
    public Menu(Game game) {
        super(game);
        loadButtons(); // Load menu buttons
        loadBackground(); // Load menu background
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG); // Load additional background image
    }

    // Loads the background
    private void loadBackground() {
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (25 * Game.SCALE);
    }

    // Loads the menu buttons
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (200 * Game.SCALE), 0, Gamestate.PLAYING); // Button to start playing
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 2, Gamestate.QUIT); // Button to quit the game
    }

    // Updates the state of the menu buttons
    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    // Draws the menu background and buttons
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null); // Draw background image

        for (MenuButton mb : buttons)
            mb.draw(g); // Draw each button
    }

    // Handles mouse pressed events for buttons
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    // Handles mouse released events for buttons
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed())
                    mb.applyGamestate(); // Apply the game state associated with the button
                if (mb.getState() == Gamestate.PLAYING)
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex()); // Set the level song if playing
                break;
            }
        }
        resetButtons(); // Reset button states
    }

    // Resets the state of all buttons
    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

    // Handles mouse movement for buttons
    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
    }

    // Handles key pressed events (if needed)
    @Override
    public void keyPressed(KeyEvent e) {
        // Implementation if needed
    }

    // Handles key released events (if needed)
    @Override
    public void keyReleased(KeyEvent e) {
        // Implementation if needed
    }

    // Handles mouse clicked events (if needed)
    @Override
    public void mouseClicked(MouseEvent e) {
        // Implementation if needed
    }

    // Additional methods like 'isIn' and any other overrides would be here...
    
    // Also, ensure any enum or constants (like Gamestate) and any other classes used (like MenuButton) are correctly defined elsewhere in your code.
}
