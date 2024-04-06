package ui;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {

    private Playing playing;
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;
    private UrmButton menu, play;

    // Constructor for GameOverOverlay
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImg(); // Create the overlay image
        createButtons(); // Create the overlay buttons
    }

    // Creates the buttons for the overlay
    private void createButtons() {
        int menuX = (int) (335 * Game.SCALE);
        int playX = (int) (440 * Game.SCALE);
        int y = (int) (220 * Game.SCALE);
        play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0); // Play button
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2); // Menu button
    }

    // Creates the overlay image
    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW = (int) (img.getWidth() * 2.7);
        imgH = (int) (img.getHeight() * 2.6);
        imgX = Game.GAME_WIDTH / 2 - imgW / 2;
        // imgY = (int) (100 * Game.SCALE); // Uncomment and set value if needed
    }

    // Draws the overlay
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // Semi-transparent black background
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT); // Fill the background

        g.drawImage(img, imgX, imgY, imgW, imgH, null); // Draw the overlay image

        menu.draw(g); // Draw the menu button
        play.draw(g); // Draw the play button
    }

    // Updates the overlay
    public void update() {
        menu.update(); // Update the menu button
        play.update(); // Update the play button
    }

    // Checks if the mouse is over a button
    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    // Handles mouse movement events
    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false); // Reset mouse over state for play button
        menu.setMouseOver(false); // Reset mouse over state for menu button

        if (isIn(menu, e))
            menu.setMouseOver(true); // Set mouse over state for menu button
        else if (isIn(play, e))
            play.setMouseOver(true); // Set mouse over state for play button
    }

    // Handles mouse release events
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll(); // Reset the playing state
                playing.setGamestate(Gamestate.MENU); // Go back to the menu
            }
        } else if (isIn(play, e))
            if (play.isMousePressed()) {
                playing.resetAll(); // Reset the playing state
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex()); // Set the level song
            }

        menu.resetBools(); // Reset the button states for menu button
        play.resetBools(); // Reset the button states for play button
    }

    // Handles mouse press events
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true); // Set the menu button as pressed
        else if (isIn(play, e))
            play.setMousePressed(true); // Set the play button as pressed
    }
}
