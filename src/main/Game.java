package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private AudioPlayer audioPlayer;

    public final static int TILES_DEFAULT_SIZE = 32;
    public static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private final boolean SHOW_FPS_UPS = true;

    // Constructor for Game
    public Game() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Adjust the scale factor based on screen size
        SCALE = (float) Math.min(width / (TILES_DEFAULT_SIZE * TILES_IN_WIDTH), height / (TILES_DEFAULT_SIZE * TILES_IN_HEIGHT));

        // Recalculate tile size and game dimensions based on the new scale
        TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
        GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
        GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

        initClasses();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    }

    // Initializes classes used in the game
    private void initClasses() {
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
    }

    // Starts the game loop in a new thread
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Updates the game based on the current game state
    public void update() {
        switch (Gamestate.state) {
            case MENU -> menu.update();
            case PLAYING -> playing.update();
            case QUIT -> System.exit(0);
        }
    }

    // Renders the game based on the current game state
    @SuppressWarnings("incomplete-switch")
    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU -> menu.draw(g);
            case PLAYING -> playing.draw(g);
        }
    }

    // The main game loop, which controls updates and rendering
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (SHOW_FPS_UPS)
                if (System.currentTimeMillis() - lastCheck >= 1000) {
                    lastCheck = System.currentTimeMillis();
                    frames = 0;
                    updates = 0;
                }
        }
    }

    // Resets player direction booleans when the window loses focus
    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    // Getters for menu, playing, and audio player
    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
