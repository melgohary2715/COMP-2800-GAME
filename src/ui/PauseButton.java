package ui;

import java.awt.Rectangle;

public class PauseButton {

    protected int x, y, width, height;
    protected Rectangle bounds;

    // Constructor for PauseButton
    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBounds(); // Create the bounds rectangle
    }

    // Creates the bounds rectangle based on the button's position and size
    private void createBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    // Getters and setters for the button's x-coordinate
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    // Getters and setters for the button's y-coordinate
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Getters and setters for the button's width
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    // Getters and setters for the button's height
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // Getter for the button's bounds rectangle
    public Rectangle getBounds() {
        return bounds;
    }

    // Setter for the button's bounds rectangle
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
