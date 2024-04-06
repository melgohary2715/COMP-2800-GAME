package objects;

import main.Game;

public class Spike extends GameObject {

    // Constructor for Spike
    public Spike(int x, int y, int objType) {
        super(x, y, objType); // Call the constructor of the parent class
        initHitbox(32, 16); // Initialize the hitbox for the spike

        // Set draw offsets
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 16);

        // Adjust the hitbox position based on the y-draw offset
        hitbox.y += yDrawOffset;
    }
}
