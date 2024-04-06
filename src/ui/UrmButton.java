package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class UrmButton extends PauseButton {
    private BufferedImage[] imgs; // Array to hold the button images
    private int rowIndex; // The row index of the button image in the sprite sheet
    private int index; // Index to select the correct button image based on its state
    private boolean mouseOver; // Flag to check if the mouse is over the button
    private boolean mousePressed; // Flag to check if the button is pressed

    // Constructor
    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    // Method to load button images from the sprite sheet
    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3]; // Assuming there are 3 images for each button state
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }
    }

    // Method to update the button's state
    public void update() {
        index = 0; // Default state
        if (mouseOver) index = 1; // Mouse over state
        if (mousePressed) index = 2; // Mouse pressed state
    }

    // Method to draw the button
    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    // Method to reset the button's state
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    // Getter for mouseOver
    public boolean isMouseOver() {
        return mouseOver;
    }

    // Setter for mouseOver
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    // Getter for mousePressed
    public boolean isMousePressed() {
        return mousePressed;
    }

    // Setter for mousePressed
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
