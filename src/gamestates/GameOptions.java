package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class GameOptions extends State implements Statemethods {

    // Attributes for background and button images
    private BufferedImage backgroundImg;
    // private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;

    // Constructor for GameOptions
    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButton();
    }

    // Loads the button for the game options
    private void loadButton() {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    // Loads the background image
    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    // Updates the state
    @Override
    public void update() {
        menuB.update();
    }

    // Draws the state
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        menuB.draw(g);
    }

    // Handles mouse dragged events
    public void mouseDragged(MouseEvent e) {

    }

    // Handles mouse pressed events
    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        }
    }

    // Handles mouse released events
    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed())
                Gamestate.state = Gamestate.MENU;
        }
        menuB.resetBools();
    }

    // Handles mouse moved events
    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
    }

    // Handles key pressed events
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Gamestate.state = Gamestate.MENU;
    }

    // Handles key released events
    @Override
    public void keyReleased(KeyEvent e) {

    }

    // Handles mouse clicked events
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    // Checks if the mouse is within the bounds of a button
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
