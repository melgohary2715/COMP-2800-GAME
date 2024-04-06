package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    // Constructor for GamePanel
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize(); // Set the size of the game panel
        addKeyListener(new KeyboardInputs(this)); // Add keyboard input listener
        addMouseListener(mouseInputs); // Add mouse input listener
        addMouseMotionListener(mouseInputs); // Add mouse motion listener
    }

    // Sets the size of the game panel
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    // Updates the game (currently empty)
    public void updateGame() {
        // Game update logic
    }

    // Paints the game components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g); // Render the game
    }

    // Getter for the game instance
    public Game getGame() {
        return game;
    }
}
