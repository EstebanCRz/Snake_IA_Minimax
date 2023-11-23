package Snake;

public class Apple {
    static int appleX;
    static int appleY;

    public static void newApple() {
        boolean onSnake = true;
        while(onSnake) {
            onSnake = false;
            appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

            // Vérifier si les coordonnées de la pomme correspondent à une partie du corps du serpent
            for (int i = 0; i < bodyParts; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    onSnake = true;
                    break;
                }
            }
        }
    }
    public static void checkApple(boolean newApple) {
        if((x[0]==appleX) && (y[0]==appleY)) {
            grandir();
            if (newApple) {
                applesEaten++;
                newApple();
            }
        }

    }
}
