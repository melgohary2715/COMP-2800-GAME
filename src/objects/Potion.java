package objects;

import main.Game;

public class Potion extends GameObject {

    private float hoverOffset; // Offset for the hovering animation
    private int maxHoverOffset; // Maximum offset for the hover animation
    private int hoverDir = 1; // Direction of hover (1 for up, -1 for down)

    // Constructor for Potion
    public Potion(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true; // Enable animation

        initHitbox(7, 14); // Initialize hitbox

        // Set draw offsets
        xDrawOffset = (int) (3 * Game.SCALE);
        yDrawOffset = (int) (2 * Game.SCALE);

        // Set maximum hover offset
        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    // Updates the potion, including animation and hover effect
    public void update() {
        updateAnimationTick(); // Update animation
        updateHover(); // Update hover effect
    }

    // Updates the hover effect for the potion
    private void updateHover() {
        // Update hover offset based on direction
        hoverOffset += (0.075f * Game.SCALE * hoverDir);

        // Reverse direction if at max offset or at starting position
        if (hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;

        // Update hitbox position based on hover offset
        hitbox.y = y + hoverOffset;
    }
}
