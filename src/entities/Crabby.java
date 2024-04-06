package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsFloor;
import static utilz.Constants.Dialogue.*;

import gamestates.Playing;

public class Crabby extends Enemy {

    // Constructor for Crabby
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(22, 19);
        initAttackBox(82, 19, 30);
    }

    // Updates the Crabby's state
    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBox();
    }

    // Updates the Crabby's behavior based on its current state and level data
    private void updateBehavior(int[][] lvlData, Playing playing) {
        // Check if this is the first update
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        // If Crabby is in the air, perform in-air checks
        if (inAir) {
            inAirChecks(lvlData, playing);
        } else {
            // Handle behavior based on current state
            switch (state) {
                case IDLE:
                    if (IsFloor(hitbox, lvlData))
                        newState(RUNNING);
                    else
                        inAir = true;
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer()))
                            newState(ATTACK);
                    }
                    move(lvlData);

                    if (inAir) {
                        // Uncomment and complete the following line to add dialogue when Crabby jumps
                        // playing.addDialogue((int) hitbox.x, (int) hitbox.y, EXCLAMATION);
                    }

                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
                    break;
                case HIT:
                    if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
                        pushBack(pushBackDir, lvlData, 2f);
                    updatePushBackDrawOffset();
                    break;
            }
        }
    }
}
