package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    // Attributes for managing enemies
    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private Level currentLevel;

    // Constructor for EnemyManager
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    // Loads the enemies for a given level
    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }

    // Updates the state of all active enemies
    public void update(int[][] lvlData) {
        boolean isAnyActive = false;
        for (Crabby c : currentLevel.getCrabs()) {
            if (c.isActive()) {
                c.update(lvlData, playing);
                isAnyActive = true;
            }
        }

        // Set the level as completed if no enemies are active
        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }

    // Draws all active enemies
    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    // Draws all active Crabby enemies
    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : currentLevel.getCrabs()) {
            if (c.isActive()) {
                g.drawImage(crabbyArr[c.getState()][c.getAniIndex()], 
                            (int) c.getHitbox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX(),
                            (int) c.getHitbox().y - CRABBY_DRAWOFFSET_Y + (int) c.getPushDrawOffset(), 
                            CRABBY_WIDTH * c.flipW(), CRABBY_HEIGHT, null);
            }
        }
    }

    // Checks if any enemy is hit by the player's attack
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : currentLevel.getCrabs()) {
            if (c.isActive() && c.getState() != DEAD && c.getState() != HIT) {
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(20);
                    return;
                }
            }
        }
    }

    // Loads the images for the enemies
    private void loadEnemyImgs() {
        crabbyArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE), 9, 5, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
    }

    // Helper method to extract a 2D array of images from a sprite atlas
    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++) {
            for (int i = 0; i < tempArr[j].length; i++) {
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        }
        return tempArr;
    }

    // Resets all enemies to their initial states
    public void resetAllEnemies() {
        for (Crabby c : currentLevel.getCrabs()) {
            c.resetEnemy();
        }
    }

}
