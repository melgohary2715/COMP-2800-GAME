package levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.ObjectConstants.*;

public class Level {
    // Attributes for the level
    private BufferedImage img; // Level image
    private int[][] lvlData; // Level data

    // Lists to hold different objects and entities
    private ArrayList<Crabby> crabs = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<GameContainer> containers = new ArrayList<>();

    // Attributes for level offsets
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn; // Player spawn point

    // Constructor for Level
    public Level(BufferedImage img) {
        this.img = img;
        lvlData = new int[img.getHeight()][img.getWidth()];
        loadLevel(); // Load level from the image
        calcLvlOffsets(); // Calculate level offsets
    }

    // Loads the level from the image
    private void loadLevel() {
        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
                loadObjects(blue, x, y);
            }
    }

    // Loads level data based on the red value of the image pixel
    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50)
            lvlData[y][x] = 0;
        else
            lvlData[y][x] = redValue;
    }

    // Loads entities based on the green value of the image pixel
    private void loadEntities(int greenValue, int x, int y) {
        switch (greenValue) {
            case CRABBY -> crabs.add(new Crabby(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
        }
    }

    // Loads objects based on the blue value of the image pixel
    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_POTION -> potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
            case BOX -> containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
            case SPIKE -> spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
        }
    }

    // Calculates level offsets
    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    // Getter for sprite index
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    // Getter for level data
    public int[][] getLevelData() {
        return lvlData;
    }

    // Getter for level offset
    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    // Getter for player spawn point
    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    // Getter for crabs list
    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    // Getter for potions list
    public ArrayList<Potion> getPotions() {
        return potions;
    }

    // Getter for containers list
    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    // Getter for spikes list
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
