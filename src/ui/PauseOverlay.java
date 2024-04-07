package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB, replayB, unpauseB;
    private float scale; // Added scale variable

    // Constructor for PauseOverlay class
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();   // Load the background image for the pause overlay
        createUrmButtons(); // Create the buttons for the pause overlay
    }

    // Create the buttons for the pause overlay
    private void createUrmButtons() {
        // Calculate the center of the background
        int centerX = bgX + bgW / 2;

        // Calculate button positions based on the center of the background
        int buttonSize = (int) (URM_SIZE * scale); // Adjust button size based on scale
        int buttonSpacing = (int) (74 * scale); // Adjust button spacing based on scale
        int totalButtonsWidth = 3 * buttonSize + 2 * buttonSpacing; // Total width of all buttons and spacing
        int firstButtonX = centerX - totalButtonsWidth / 2;

        int bY = bgY + (int) (325 * scale); // Adjust Y position based on scale

        // Create the menu button
        menuB = new UrmButton(firstButtonX, bY, buttonSize, buttonSize, 2);
        // Create the replay button
        replayB = new UrmButton(firstButtonX + buttonSize + buttonSpacing, bY, buttonSize, buttonSize, 1);
        // Create the unpause button
        unpauseB = new UrmButton(firstButtonX + 2 * (buttonSize + buttonSpacing), bY, buttonSize, buttonSize, 0);
    }

    // Load the background image for the pause overlay
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);

        // Set the scale factor based on the smaller dimension of the screen
        float scaleWidth = Game.GAME_WIDTH / (float) backgroundImg.getWidth();
        float scaleHeight = Game.GAME_HEIGHT / (float) backgroundImg.getHeight();
        scale = Math.min(scaleWidth, scaleHeight);

        // Set the background width and height based on the scale factor
        bgW = (int) (backgroundImg.getWidth() * scale);
        bgH = (int) (backgroundImg.getHeight() * scale);

        // Center the background image in the game window
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = Game.GAME_HEIGHT / 2 - bgH / 2;
    }

    // Update method for the pause overlay
    public void update() {
        menuB.update();    // Update the menu button
        replayB.update();  // Update the replay button
        unpauseB.update(); // Update the unpause button
    }

    // Draw method for the pause overlay
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null); // Draw the background image
        menuB.draw(g);     // Draw the menu button
        replayB.draw(g);   // Draw the replay button
        unpauseB.draw(g);  // Draw the unpause button
    }

    // Handle mouse dragged event
    public void mouseDragged(MouseEvent e) {
        // Not used in this class
    }

    // Handle mouse pressed event
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB))
            menuB.setMousePressed(true);
        else if (isIn(e, replayB))
            replayB.setMousePressed(true);
        else if (isIn(e, unpauseB))
            unpauseB.setMousePressed(true);
    }

    // Handle mouse released event
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
                playing.unpauseGame();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed())
                playing.unpauseGame();
        }

        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    // Handle mouse moved event
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else if (isIn(e, replayB))
            replayB.setMouseOver(true);
        else if (isIn(e, unpauseB))
            unpauseB.setMouseOver(true);
    }

    // Check if the mouse event is inside a button's bounds
    private boolean isIn(MouseEvent e, UrmButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
