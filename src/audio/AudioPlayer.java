package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.sound.sampled.*;

public class AudioPlayer {

    // Constants for song and effect indices
    public static final int MENU_1 = 0;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int DIE = 0;
    public static final int JUMP = 1;
    public static final int GAMEOVER = 2;
    public static final int LVL_COMPLETED = 3;
    public static final int ATTACK_ONE = 4;
    public static final int ATTACK_TWO = 5;
    public static final int ATTACK_THREE = 6;

    // Arrays for storing song and effect clips
    private Clip[] songs, effects;

    // Current song ID and volume settings
    private int currentSongId;
    private float volume = 0.5f;
    private boolean songMute, effectMute;

    // Random number generator for attack sounds
    private Random rand = new Random();

    // Constructor loads songs, effects, and starts playing the menu song
    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(MENU_1);
    }

    // Loads song clips from files
    private void loadSongs() {
        String[] names = {"menu", "level1", "level2"};
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = getClip(names[i]);
        }
    }

    // Loads effect clips from files
    private void loadEffects() {
        String[] effectNames = {"die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3"};
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effects.length; i++) {
            effects[i] = getClip(effectNames[i]);
        }
        updateEffectsVolume();
    }

    // Helper method to load a clip from a file
    private Clip getClip(String name) {
        URL url = getClass().getResource("/audio/" + name + ".wav");
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Sets the volume for songs and effects
    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    // Stops the current song
    public void stopSong() {
        if (songs[currentSongId].isActive()) {
            songs[currentSongId].stop();
        }
    }

    // Sets the song for the specified level
    public void setLevelSong(int lvlIndex) {
        playSong(lvlIndex % 2 == 0 ? LEVEL_1 : LEVEL_2);
    }

    // Stops the current song and plays the level completed effect
    public void lvlCompleted() {
        stopSong();
        playEffect(LVL_COMPLETED);
    }

    // Plays a random attack sound
    public void playAttackSound() {
        playEffect(ATTACK_ONE + rand.nextInt(3));
    }

    // Plays the specified effect
    public void playEffect(int effect) {
        if (effects[effect].getMicrosecondPosition() > 0) {
            effects[effect].setMicrosecondPosition(0);
        }
        effects[effect].start();
    }

    // Plays the specified song
    public void playSong(int song) {
        stopSong();
        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Toggles the mute state for songs
    public void toggleSongMute() {
        songMute = !songMute;
        for (Clip clip : songs) {
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    // Toggles the mute state for effects
    public void toggleEffectMute() {
        effectMute = !effectMute;
        for (Clip clip : effects) {
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if (!effectMute) {
            playEffect(JUMP);
        }
    }

    // Updates the volume for the current song
    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    // Updates the volume for all effects
    private void updateEffectsVolume() {
        for (Clip clip : effects) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
