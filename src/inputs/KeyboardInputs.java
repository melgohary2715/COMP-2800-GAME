package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {
    // Attribute to hold the game panel instance
    private GamePanel gamePanel;

    // Constructor for KeyboardInputs
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Method to handle key release events
    @SuppressWarnings("incomplete-switch")
    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().keyReleased(e); // Call keyReleased in Menu state
            case PLAYING -> gamePanel.getGame().getPlaying().keyReleased(e); // Call keyReleased in Playing state
        }
    }

    // Method to handle key press events
    @SuppressWarnings("incomplete-switch")
    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().keyPressed(e); // Call keyPressed in Menu state
            case PLAYING -> gamePanel.getGame().getPlaying().keyPressed(e); // Call keyPressed in Playing state
        }
    }

    // Method to handle key typed events (not in use)
    @Override
    public void keyTyped(KeyEvent e) {
        // Not In Use
    }
}
