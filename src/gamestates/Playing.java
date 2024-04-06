package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameCompletedOverlay;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods {
    // Attributes for the playing state
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private GameCompletedOverlay gameCompletedOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;

    private boolean paused = false; // Flag to check if the game is paused

    private int xLvlOffset; // Horizontal level offset for scrolling
    private int leftBorder = (int) (0.25 * Game.GAME_WIDTH); // Left border for player movement
    private int rightBorder = (int) (0.75 * Game.GAME_WIDTH); // Right border for player movement
    private int maxLvlOffsetX; // Maximum level offset

    private BufferedImage backgroundImg, bigCloud, smallCloud;
		
    private int[] smallCloudsPos; // Positions of small clouds
    private Random rnd = new Random(); // Random number generator

    private boolean gameOver; // Flag to check if the game is over
    private boolean lvlCompleted; // Flag to check if the level is completed
    private boolean gameCompleted; // Flag to check if the game is completed
    private boolean playerDying; // Flag to check if the player is dying

    // Constructor for Playing state
    public Playing(Game game) {
        super(game);
        initClasses(); // Initialize classes

        // Load background and cloud images
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));

        calcLvlOffset(); // Calculate level offset
        loadStartLevel(); // Load the start level
    }

    // Loads the next level
    public void loadNextLevel() {
        levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    // Loads the start level
    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    // Calculates the level offset based on the current level
    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    // Initializes classes used in the playing state
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);

        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        gameCompletedOverlay = new GameCompletedOverlay(this);
    }

    // Updates the playing state
    @Override
    public void update() {
        if (paused)
            pauseOverlay.update();
        else if (lvlCompleted)
            levelCompletedOverlay.update();
        else if (gameCompleted)
            gameCompletedOverlay.update();
        else if (gameOver)
            gameOverOverlay.update();
        else if (playerDying)
            player.update();
        else {
            levelManager.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            checkCloseToBorder();
        }
    }

    // Checks if the player is close to the left or right border and updates the level offset accordingly
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        xLvlOffset = Math.max(Math.min(xLvlOffset, maxLvlOffsetX), 0);
    }

    // Draws the playing state
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g); // Draw clouds

        // Draw level, objects, enemies, and player
        levelManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        // Draw overlays based on the game state
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver)
            gameOverOverlay.draw(g);
        else if (lvlCompleted)
            levelCompletedOverlay.draw(g);
        else if (gameCompleted)
            gameCompletedOverlay.draw(g);
    }

    // Draws clouds in the background
    private void drawClouds(Graphics g) {
        for (int i = 0; i < 4; i++)
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    // Sets the game as completed
    public void setGameCompleted() {
        gameCompleted = true;
    }

    // Resets the game completed flag
    public void resetGameCompleted() {
        gameCompleted = false;
    }

    // Resets all attributes for the playing state
    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    // Sets the game over flag
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // Checks if the player's attack box has hit any objects
    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    // Checks if the player's attack box has hit any enemies
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    // Checks if the player has touched any potions
    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    // Checks if the player has touched any spikes
    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    // Handles mouse clicked events
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
        }
    }

    // Handles key pressed events
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
            }
    }

    // Handles key released events
    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
            }
    }

    // Handles mouse dragged events
    public void mouseDragged(MouseEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    // Handles mouse pressed events for overlays
    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mousePressed(e);
        else if (paused)
            pauseOverlay.mousePressed(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mousePressed(e);
        else if (gameCompleted)
            gameCompletedOverlay.mousePressed(e);
    }

    // Handles mouse released events for overlays
    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mouseReleased(e);
        else if (paused)
            pauseOverlay.mouseReleased(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mouseReleased(e);
        else if (gameCompleted)
            gameCompletedOverlay.mouseReleased(e);
    }

    // Handles mouse moved events for overlays
    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameOver)
            gameOverOverlay.mouseMoved(e);
        else if (paused)
            pauseOverlay.mouseMoved(e);
        else if (lvlCompleted)
            levelCompletedOverlay.mouseMoved(e);
        else if (gameCompleted)
            gameCompletedOverlay.mouseMoved(e);
    }

    // Sets the level as completed and checks if the game is completed
    public void setLevelCompleted(boolean levelCompleted) {
        game.getAudioPlayer().lvlCompleted();
        if (levelManager.getLevelIndex() + 1 >= levelManager.getAmountOfLevels()) {
            // No more levels
            gameCompleted = true;
            levelManager.setLevelIndex(0);
            levelManager.loadNextLevel();
            resetAll();
            return;
        }
        this.lvlCompleted = levelCompleted;
    }

    // Sets the maximum level offset
    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    // Unpauses the game
    public void unpauseGame() {
        paused = false;
    }

    // Resets player direction booleans when the window loses focus
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    // Getters for various managers and player
    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    // Sets the player dying flag
    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }
}
