package entities;

import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.CanMoveHere;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {
    // Basic attributes for all entities
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;

    // Attributes for handling pushback effects
    protected int pushBackDir;
    protected float pushDrawOffset;
    protected int pushBackOffsetDir = UP;

    // Constructor for Entity
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Updates the offset for drawing the pushback effect
    protected void updatePushBackDrawOffset() {
        float speed = 0.95f;
        float limit = -30f;

        if (pushBackOffsetDir == UP) {
            pushDrawOffset -= speed;
            if (pushDrawOffset <= limit)
                pushBackOffsetDir = DOWN;
        } else {
            pushDrawOffset += speed;
            if (pushDrawOffset >= 0)
                pushDrawOffset = 0;
        }
    }

    // Pushes the entity back when hit
    protected void pushBack(int pushBackDir, int[][] lvlData, float speedMulti) {
        float xSpeed = pushBackDir == LEFT ? -walkSpeed : walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed * speedMulti, hitbox.y, hitbox.width, hitbox.height, lvlData))
            hitbox.x += xSpeed * speedMulti;
    }

    // Draws the attack box for debugging purposes
    protected void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    // Draws the hitbox for debugging purposes
    protected void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) (hitbox.x - xLvlOffset), (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    // Initializes the hitbox
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    // Getter for hitbox
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    // Getter for state
    public int getState() {
        return state;
    }

    // Getter for animation index
    public int getAniIndex() {
        return aniIndex;
    }

    // Sets a new state for the entity and resets animation
    protected void newState(int state) {
        this.state = state;
        aniTick = 0;
        aniIndex = 0;
    }
}
