package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {
    // Attribute to hold the game panel instance
    private GamePanel gamePanel;

    // Constructor for MouseInputs
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Method to handle mouse dragged events
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> gamePanel.getGame().getPlaying().mouseDragged(e); // Call mouseDragged in Playing state
        }
    }

    // Method to handle mouse moved events
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseMoved(e); // Call mouseMoved in Menu state
            case PLAYING -> gamePanel.getGame().getPlaying().mouseMoved(e); // Call mouseMoved in Playing state
        }
    }

    // Method to handle mouse clicked events
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> gamePanel.getGame().getPlaying().mouseClicked(e); // Call mouseClicked in Playing state
        }
    }

    // Method to handle mouse pressed events
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mousePressed(e); // Call mousePressed in Menu state
            case PLAYING -> gamePanel.getGame().getPlaying().mousePressed(e); // Call mousePressed in Playing state
        }
    }

    // Method to handle mouse released events
    @SuppressWarnings("incomplete-switch")
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case MENU -> gamePanel.getGame().getMenu().mouseReleased(e); // Call mouseReleased in Menu state
            case PLAYING -> gamePanel.getGame().getPlaying().mouseReleased(e); // Call mouseReleased in Playing state
        }
    }

    // Method to handle mouse entered events (not in use)
    @Override
    public void mouseEntered(MouseEvent e) {
        // Not In use
    }

    // Method to handle mouse exited events (not in use)
    @Override
    public void mouseExited(MouseEvent e) {
        // Not In use
    }
}
