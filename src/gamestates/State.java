package gamestates;

import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import main.Game;
import ui.MenuButton;

public class State {
    // Attribute to hold the game instance
    protected Game game;

    // Constructor for State
    public State(Game game) {
        this.game = game;
    }

    // Method to check if a mouse event is within the bounds of a MenuButton
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    // Getter for the game instance
    public Game getGame() {
        return game;
    }

    // Method to set the game state and play the corresponding audio
    @SuppressWarnings("incomplete-switch")
    public void setGamestate(Gamestate state) {
        switch (state) {
            case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
            case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
        }

        Gamestate.state = state;
    }
}
