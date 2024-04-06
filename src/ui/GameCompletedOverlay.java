package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameCompletedOverlay {
    private Playing playing;
    private BufferedImage img;
    private MenuButton quit;
    private int imgX, imgY, imgW, imgH;

    // Constructor for GameCompletedOverlay
    public GameCompletedOverlay(Playing playing) {
        this.playing = playing;
        createImg(); // Create the overlay image
        createButtons(); // Create the overlay buttons
    }

    // Creates the button for the overlay
    private void createButtons() {
        quit = new MenuButton(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 2, Gamestate.MENU);
    }

    // Creates the overlay image
    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.GAME_COMPLETED);
        imgW = (int) (img.getWidth() * Game.SCALE);
        imgH = (int) (img.getHeight() * Game.SCALE);
        imgX = Game.GAME_WIDTH / 2 - imgW / 2;
        imgY = (int) (100 * Game.SCALE);
    }

    // Draws the overlay
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // Semi-transparent black background
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT); // Fill the background

        g.drawImage(img, imgX, imgY, imgW, imgH, null); // Draw the overlay image

        quit.draw(g); // Draw the quit button
    }

    // Updates the overlay
    public void update() {
        quit.update(); // Update the quit button
    }

    // Checks if the mouse is over a button
    private boolean isIn(MenuButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    // Handles mouse movement events
    public void mouseMoved(MouseEvent e) {
        quit.setMouseOver(false); // Reset mouse over state

        if (isIn(quit, e))
            quit.setMouseOver(true); // Set mouse over state if over the quit button
    }

    // Handles mouse release events
    public void mouseReleased(MouseEvent e) {
        if (isIn(quit, e)) {
            if (quit.isMousePressed()) {
                playing.resetAll(); // Reset the playing state
                playing.resetGameCompleted(); // Reset the game completed state
                playing.setGamestate(Gamestate.MENU); // Go back to the menu
            }
        } 

        quit.resetBools(); // Reset the button states
    }

    // Handles mouse press events
    public void mousePressed(MouseEvent e) {
        if (isIn(quit, e))
            quit.setMousePressed(true); // Set the button as pressed
    }
}
