package utilz;

import main.Game;

public class Constants {

    // Gravity constant scaled by the game's scale factor
    public static final float GRAVITY = 0.04f * Game.SCALE;
    // Animation speed constant
    public static final int ANI_SPEED = 25;

    // Nested class for dialogue constants
    public static class Dialogue {
        public static final int QUESTION = 0;
        public static final int EXCLAMATION = 1;

        // Dimensions of dialogue boxes scaled by the game's scale factor
        public static final int DIALOGUE_WIDTH = (int) (14 * Game.SCALE);
        public static final int DIALOGUE_HEIGHT = (int) (12 * Game.SCALE);

        // Method to get the number of sprites for a given dialogue type
        public static int GetSpriteAmount(int type) {
            switch (type) {
                case QUESTION, EXCLAMATION:
                    return 5;
            }
            return 0;
        }
    }

    // Nested class for object constants
    public static class ObjectConstants {

        // Constants for different object types
        public static final int RED_POTION = 0;
        public static final int BOX = 3;
        public static final int SPIKE = 4;

        // Constants for potion values
        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        // Dimensions of containers scaled by the game's scale factor
        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        // Dimensions of potions scaled by the game's scale factor
        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        // Dimensions of spikes scaled by the game's scale factor
        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);

        // Method to get the number of sprites for a given object type
        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case RED_POTION:
                    return 7;
                case BOX:
                    return 8;
            }
            return 1;
        }
    }

    // Nested class for enemy constants
    public static class EnemyConstants {
        // Constants for different enemy types
        public static final int CRABBY = 0;

        // Constants for enemy states
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        // Dimensions of the crabby enemy scaled by the game's scale factor
        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

        // Method to get the number of sprites for a given enemy type and state
        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_state) {
                case IDLE:
                    if (enemy_type == CRABBY)
                        return 9;
                case RUNNING:
                    return 6;
                case ATTACK:
                    return 7;
                case HIT:
                    return 4;
                case DEAD:
                    return 5;
            }
            return 0;
        }

        // Method to get the max health for a given enemy type
        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 50;
                default:
                    return 1;
            }
        }

        // Method to get the damage for a given enemy type
        public static int GetEnemyDmg(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 15;
                default:
                    return 0;
            }
        }
    }

    // Nested class for environment constants
    public static class Environment {
        // Dimensions of clouds scaled by the game's scale factor
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
    }

    // Nested class for UI constants
    public static class UI {
        // Nested class for button constants
        public static class Buttons {
            // Dimensions of buttons scaled by the game's scale factor
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        // Nested class for pause button constants
        public static class PauseButtons {
            // Dimensions of sound buttons scaled by the game's scale factor
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        // Nested class for URM button constants
        public static class URMButtons {
            // Dimensions of URM buttons scaled by the game's scale factor
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
        }
    }

    // Nested class for direction constants
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    // Nested class for player constants
    public static class PlayerConstants {
        // Constants for player actions
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int ATTACK = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        // Method to get the number of sprites for a given player action
        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case DEAD:
                    return 8;
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK:
                    return 3;
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}
