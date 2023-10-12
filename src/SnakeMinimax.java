import java.util.ArrayList;
import java.util.Arrays;

public class SnakeMinimax {
    static ArrayList<Character> coup_possible = new ArrayList<>(Arrays.asList('D', 'U', 'R', 'L'));
    static int profondeur = 11;

    public static void moveIA() {
        int score = 0;
        char meilleur_coupIA = GamePanel.direction;
        int meilleur_score_finalIA = -1000;
        //stock la position du serpent avant le move
        int[] previousx = new int[GamePanel.bodyParts];
        int[] previousy = new int[GamePanel.bodyParts];
        System.arraycopy(GamePanel.x, 0, previousx, 0, GamePanel.bodyParts);
        System.arraycopy(GamePanel.y, 0, previousy, 0, GamePanel.bodyParts);
        int taille = GamePanel.bodyParts;

        for (char value : coup_possible) {

            GamePanel.direction = value;
            GamePanel.move();

            score = Minimax(0, previousx, previousy, false, 0);
            solvemove(previousx, previousy);
            if (score > meilleur_score_finalIA){
                meilleur_score_finalIA = score;
                meilleur_coupIA = value;
            }
        }
        //System.out.println(meilleur_coupIA+" "+meilleur_score_finalIA);
        if (meilleur_score_finalIA < 230) {
            try {
                // Pause de 1 seconde (1000 millisecondes)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Gérer l'exception
            }

        }
        GamePanel.bodyParts = taille;
        GamePanel.direction = meilleur_coupIA;
    }

    public static int Minimax(int profond, int[] finx, int[] finy, boolean isAppleEaten, int whenAppleEaten) {

        int meilleur_score = -1000;
        if (!isAppleEaten){
            if (isAppleEaten()) {
                isAppleEaten = true;
            }
        }
        if (isAppleEaten) {
            whenAppleEaten++;
        }
        if (profond == profondeur) {
            if (isAppleEaten) {
                GamePanel.rendrepetit();
                solvemove(finx, finy);
                return 1000 + whenAppleEaten;
            }
            solvemove(finx, finy);
            return 500;
        }
        if (isGameOver()) {
            if (isAppleEaten) {
                GamePanel.rendrepetit();
                solvemove(finx, finy);
                return 200 + profond;
            }
            solvemove(finx, finy);
            return 0 + profond;
        }
        for (char value : coup_possible) {

            GamePanel.direction = value;
            //stock la position du serpent avant le move
            int[] previousx = new int[GamePanel.bodyParts];
            int[] previousy = new int[GamePanel.bodyParts];
            System.arraycopy(GamePanel.x, 0, previousx, 0, GamePanel.bodyParts);
            System.arraycopy(GamePanel.y, 0, previousy, 0, GamePanel.bodyParts);
            int taille = GamePanel.bodyParts;
            GamePanel.move();
            GamePanel.checkApple(false);
            int score = Minimax(profond + 1, previousx, previousy, isAppleEaten, whenAppleEaten);
            GamePanel.bodyParts = taille;
            solvemove(previousx, previousy);
            if (meilleur_score < score) {
                meilleur_score = score;
            }
        }
        return meilleur_score;

    }

	/*
	private static void newAppleOn(char value) {
		int pommex = GamePanel.appleX;
		int pommey = GamePanel.appleY;

		switch(value) {
		case 'U':
			GamePanel.appleY = GamePanel.y[0] - GamePanel.UNIT_SIZE;
			GamePanel.appleX = GamePanel.x[0];
			break;
		case 'D':
			GamePanel.appleY = GamePanel.y[0] + GamePanel.UNIT_SIZE;
			GamePanel.appleX = GamePanel.x[0];
		break;
		case 'L':
			GamePanel.appleY = GamePanel.y[0];
			GamePanel.appleX = GamePanel.x[0] - GamePanel.UNIT_SIZE;
			break;
		case 'R':
			GamePanel.appleY = GamePanel.y[0];
			GamePanel.appleX = GamePanel.x[0] + GamePanel.UNIT_SIZE;
			break;
		}

		GamePanel.appleY = pommex;
		GamePanel.appleX = pommey;
	}
	*/

    private static boolean isAppleEaten() {
        if((GamePanel.x[0]==GamePanel.appleX) && (GamePanel.y[0]==GamePanel.appleY)) {
            return true;
        }
        return false;

    }
    public static boolean isGameOver() {
        //checks if head collides with body
        for (int i = GamePanel.bodyParts - 1; i > 0; i--) {
            if ((GamePanel.x[0] == GamePanel.x[i]) && (GamePanel.y[0] == GamePanel.y[i])) {
                return true;
            }
        }
        //check if head touches left border
        if(GamePanel.x[0] < 0) {
            return true;
        }
        //check if head touches right border
        if(GamePanel.x[0] > GamePanel.SCREEN_WIDTH-GamePanel.UNIT_SIZE) {
            return true;
        }
        //check if head touches top border
        if(GamePanel.y[0] < 0) {
            return true;
        }
        //check if head touches bottom border
        if(GamePanel.y[0] > (GamePanel.SCREEN_HEIGHT-GamePanel.UNIT_SIZE)) {
            return true;
        }
        return false;
    }
    static void solvemove(int[] previousx, int[] previousy) {
        System.arraycopy(previousx, 0, GamePanel.x, 0, GamePanel.bodyParts);
        System.arraycopy(previousy, 0, GamePanel.y, 0, GamePanel.bodyParts);
    }
}