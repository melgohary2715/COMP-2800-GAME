package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import gamestates.Playing;

import static utilz.Constants.Directions.*;
import static utilz.Constants.*;

import main.Game;

public abstract class Enemy extends Entity {
    // Enemy-specific attributes
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;
    protected int attackBoxOffsetX;

    // Constructor for Enemy
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = Game.SCALE * 0.35f;
    }

    // Updates the position of the attack box
    protected void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    // Updates the position of the attack box when the enemy flips direction
    protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;

        attackBox.y = hitbox.y;
    }

    // Initializes the attack box
    protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
        attackBox = new Rectangle2D.Float(x, y, (int) (w * Game.SCALE), (int) (h * Game.SCALE));
        this.attackBoxOffsetX = (int) (Game.SCALE * attackBoxOffsetX);
    }

    // Checks if the enemy is on the floor during the first update
    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    // Performs checks when the enemy is in the air
    protected void inAirChecks(int[][] lvlData, Playing playing) {
        if (state != HIT && state != DEAD) {
            updateInAir(lvlData);
            playing.getObjectManager().checkSpikesTouched(this);
            if (IsEntityInWater(hitbox, lvlData))
                hurt(maxHealth);
        }
    }

    // Updates the enemy's position while in the air
    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    // Moves the enemy based on its walking direction
    protected void move(int[][] lvlData) {
        float xSpeed = walkDir == LEFT ? -walkSpeed : walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData) && IsFloor(hitbox, xSpeed, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            changeWalkDir();
        }
    }

    // Turns the enemy towards the player
    protected void turnTowardsPlayer(Player player) {
        walkDir = player.hitbox.x > hitbox.x ? RIGHT : LEFT;
    }

    // Checks if the enemy can see the player
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        return playerTileY == tileY && isPlayerInRange(player) && IsSightClear(lvlData, hitbox, player.hitbox, tileY);
    }

    // Checks if the player is within the enemy's attack range
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    // Checks if the player is close enough for the enemy to attack
    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return switch (enemyType) {
            case CRABBY -> absValue <= attackDistance;
            default -> false;
        };
    }

    // Reduces the enemy's health and updates its state if necessary
    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            newState(DEAD);
        } else {
            newState(HIT);
            pushBackDir = walkDir == LEFT ? RIGHT : LEFT;
            pushBackOffsetDir = UP;
            pushDrawOffset = 0;
        }
    }

    // Checks if the player is hit by the enemy's attack
    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
            player.changeHealth(-GetEnemyDmg(enemyType), this);
        }
        attackChecked = true;
    }

    // Updates the enemy's animation
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                if (enemyType == CRABBY) {
                    aniIndex = 0;
                    switch (state) {
                        case ATTACK, HIT -> state = IDLE;
                        case DEAD -> active = false;
                    }
                }
              
            }
        }
    }

    // Changes the walking direction of the enemy
    protected void changeWalkDir() {
        walkDir = walkDir == LEFT ? RIGHT : LEFT;
    }

    // Resets the enemy to its initial state
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;
        pushDrawOffset = 0;
    }

    // Returns the flip value for the x-coordinate based on walking direction
    public int flipX() {
        return walkDir == RIGHT ? width : 0;
    }

    // Returns the flip value for the width based on walking direction
    public int flipW() {
        return walkDir == RIGHT ? -1 : 1;
    }

    // Returns whether the enemy is active
    public boolean isActive() {
        return active;
    }

    // Returns the offset for drawing the pushback animation
    public float getPushDrawOffset() {
        return pushDrawOffset;
    }
}
