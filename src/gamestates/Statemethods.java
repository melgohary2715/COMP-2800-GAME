package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Statemethods {
    // Method to update the state
    public void update();

    // Method to draw the state on the screen
    public void draw(Graphics g);

    // Method to handle mouse click events
    public void mouseClicked(MouseEvent e);

    // Method to handle mouse press events
    public void mousePressed(MouseEvent e);

    // Method to handle mouse release events
    public void mouseReleased(MouseEvent e);

    // Method to handle mouse movement events
    public void mouseMoved(MouseEvent e);

    // Method to handle key press events
    public void keyPressed(KeyEvent e);

    // Method to handle key release events
    public void keyReleased(KeyEvent e);
}
