package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {

    private Playing playing;
    private UrmButton replay, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;

    // Constructor for LevelCompletedOverlay
    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg(); // Initialize the overlay image
        initButtons(); // Initialize the overlay buttons
    }

    // Initializes the buttons for the overlay
    private void initButtons() {
        int replayX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (265 * Game.SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0); // Next button
        replay = new UrmButton(replayX, y, URM_SIZE, URM_SIZE, 1); // replay button
    }

    // Initializes the overlay image
    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * 7.0);
        bgH = (int) (img.getHeight() * 5.0);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (-75 * Game.SCALE);
    }

    // Draws the overlay
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // Semi-transparent black background
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT); // Fill the background

        g.drawImage(img, bgX, bgY, bgW, bgH, null); // Draw the overlay image
        next.draw(g); // Draw the next button
        replay.draw(g); // Draw the replay button
    }

    // Updates the overlay
    public void update() {
        next.update(); // Update the next button
        replay.update(); // Update the replay button
    }

    // Checks if the mouse is over a button
    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    // Handles mouse movement events
    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false); // Reset mouse over state for next button
        replay.setMouseOver(false); // Reset mouse over state for replay button

        if (isIn(replay, e))
            replay.setMouseOver(true); // Set mouse over state for replay button
        else if (isIn(next, e))
            next.setMouseOver(true); // Set mouse over state for next button
    }

    // Handles mouse release events
    public void mouseReleased(MouseEvent e) {
        if (isIn(replay, e)) {
            if (replay.isMousePressed()) {
                playing.resetAll(); // Reset the playing state
                playing.unpauseGame(); //unpause the game
            }
        } else if (isIn(next, e))
            if (next.isMousePressed()) {
                playing.loadNextLevel(); // Load the next level
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex()); // Set the level song
            }

        replay.resetBools(); // Reset the button states for replay button
        next.resetBools(); // Reset the button states for next button
    }

    // Handles mouse press events
    public void mousePressed(MouseEvent e) {
        if (isIn(replay, e))
            replay.setMousePressed(true); // Set the replay button as pressed
        else if (isIn(next, e))
            next.setMousePressed(true); // Set the next button as pressed
    }
}
