import java.util.ArrayList;
import java.util.Arrays;

public class SnakeMinimax {
    static ArrayList<Character> coup_possible = new ArrayList<>(Arrays.asList('D', 'U', 'R', 'L'));
    static int profondeur = 15;
    //profondeur de recherche

    public static void moveIA(int PROFONDEUR) {
        int taille = GamePanel.bodyParts;
        profondeur = PROFONDEUR + taille/4;
        System.out.println(profondeur);
        int score = 0;
        char meilleur_coupIA = GamePanel.direction;
        int meilleur_score_finalIA = -1000;
        //stock la position du serpent avant le move
        int[] previousx = new int[GamePanel.bodyParts];
        int[] previousy = new int[GamePanel.bodyParts];
        System.arraycopy(GamePanel.x, 0, previousx, 0, GamePanel.bodyParts);
        System.arraycopy(GamePanel.y, 0, previousy, 0, GamePanel.bodyParts);

        for (char value : coup_possible) {

            GamePanel.direction = value;
            GamePanel.move();

            score = Minimax(0, previousx, previousy, false, 0, 0);
            solvemove(previousx, previousy);
            if (score > meilleur_score_finalIA){
                meilleur_score_finalIA = score;
                meilleur_coupIA = value;
            }
        }

        GamePanel.bodyParts = taille;
        GamePanel.direction = meilleur_coupIA;
    }

    public static int Minimax(int profond, int[] finx, int[] finy, boolean isAppleEaten, int whenAppleEaten, int whenGrandirApple) {

        int meilleur_score = -1000;
        if (!isAppleEaten){
            if (isAppleEaten()) {
                isAppleEaten = true;
            }
        }
        if (isAppleEaten) {
            if (whenAppleEaten < 3) {
                GamePanel.grandir();
                whenGrandirApple++;
            }
            whenAppleEaten++;
        }
        if (profond >= profondeur) {
            if (isAppleEaten) {
                for (int i=0 ; i<=whenGrandirApple; i++) {
                    GamePanel.rendrepetit();
                }
                solvemove(finx, finy);
                return 1000 + whenAppleEaten;
            }
            solvemove(finx, finy);
            return 500;
        }
        if (isGameOver()) {
            if (isAppleEaten) {
                for (int i=0 ; i<=whenGrandirApple; i++) {
                    GamePanel.rendrepetit();
                }                solvemove(finx, finy);
                return 200 + profond;
            }
            solvemove(finx, finy);
            return profond;
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
            //GamePanel.checkApple(false);
            int score = Minimax(profond + 1, previousx, previousy, isAppleEaten, whenAppleEaten, whenGrandirApple);
            GamePanel.bodyParts = taille;
            solvemove(previousx, previousy);
            if (meilleur_score < score) {
                meilleur_score = score;
            }
        }
        return meilleur_score;

    }
    private static boolean isAppleEaten() {
        return (GamePanel.x[0] == GamePanel.appleX) && (GamePanel.y[0] == GamePanel.appleY);
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
        System.arraycopy(previousx, 0, GamePanel.x, 0, GamePanel.bodyParts-1);
        System.arraycopy(previousy, 0, GamePanel.y, 0, GamePanel.bodyParts-1);
    }
}