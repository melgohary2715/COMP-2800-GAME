package objects;

import static utilz.Constants.ObjectConstants.*;

import main.Game;

public class GameContainer extends GameObject {

    // Constructor for GameContainer
    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox(); // Create the hitbox for the container
    }

    // Creates the hitbox for the container based on its type
    private void createHitbox() {
        if (objType == BOX) {
            initHitbox(25, 18); // Initialize hitbox for box

            // Set draw offsets for box
            xDrawOffset = (int) (7 * Game.SCALE);
            yDrawOffset = (int) (12 * Game.SCALE);
        } else {
            initHitbox(23, 25); // Initialize hitbox for other container types
            // Set draw offsets for other container types
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (5 * Game.SCALE);
        }

        // Adjust hitbox position based on draw offsets
        hitbox.y += yDrawOffset + (int) (Game.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    // Updates the container, including its animation if applicable
    public void update() {
        if (doAnimation)
            updateAnimationTick(); // Update animation tick if animation is enabled
    }
}
