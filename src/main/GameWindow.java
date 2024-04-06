package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;

    // Constructor for GameWindow
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame(); // Create a new JFrame

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        jframe.add(gamePanel); // Add the game panel to the JFrame
        
        jframe.setResizable(false); // Make the window non-resizable
        jframe.pack(); // Pack the components within the window
        jframe.setLocationRelativeTo(null); // Center the window on the screen
        jframe.setVisible(true); // Make the window visible

        // Add a window focus listener to handle focus events
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                // When the window loses focus, call the windowFocusLost method in Game
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                // Not implemented, but can be used if needed
            }
        });
    }
}
