package Game;

public class Snake {
    final static int[] Co_x = new int[GAME_UNITS+2];
    final static int[] Co_y = new int[GAME_UNITS+2];
    static int bodyParts = 5;
    static int applesEaten = 0;
    static char direction = 'R';

    public static void move(){
        for (int i = bodyParts;i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0 ] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public static void rendrepetit() {
        x[bodyParts] = 0;
        y[bodyParts] = 0;
        bodyParts--;
    }
    public static void grandir() {
        bodyParts++;
    }

    public void checkCollisions() {
        // Vérifier si la tête entre en collision avec le corps
        for (int i = bodyParts - 1; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        // Vérifier si la tête entre en collision avec les bords
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }
}
