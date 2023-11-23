package IA;

import java.util.ArrayList;
import java.util.Arrays;

public class SnakeMinimax {
    static final int PROFONDEUR = 9;
    static ArrayList<Character> coup_possible = new ArrayList<>(Arrays.asList('D', 'U', 'R', 'L'));
    static int profondeur = 15;
    //profondeur de recherche

    public static void moveIA(int PROFONDEUR) {
        int taille = GamePanel2.bodyParts;
        profondeur = PROFONDEUR + taille/6;
        System.out.println(profondeur);
        char ancienCoup = GamePanel2.direction;
        int score = 0;
        char meilleur_coupIA = GamePanel2.direction;
        int meilleur_score_finalIA = -1000;
        //stock la position du serpent avant le move
        int[] previousx = new int[GamePanel2.bodyParts];
        int[] previousy = new int[GamePanel2.bodyParts];
        System.arraycopy(GamePanel2.x, 0, previousx, 0, GamePanel2.bodyParts);
        System.arraycopy(GamePanel2.y, 0, previousy, 0, GamePanel2.bodyParts);

        for (char value : coup_possible) {

            // Skip the opposite direction
            if (!isOppositeDirection(ancienCoup, value)) {
                GamePanel2.direction = value;
                GamePanel2.move();

                score = Minimax(value, 0, previousx, previousy, false, 0, 0);
                solvemove(previousx, previousy);

                if (score > meilleur_score_finalIA){
                    meilleur_score_finalIA = score;
                    meilleur_coupIA = value;
                }
            }
        }

        GamePanel2.bodyParts = taille;
        GamePanel2.direction = meilleur_coupIA;
    }

    public static int Minimax(char lastMove, int profond, int[] finx, int[] finy, boolean isAppleEaten, int whenAppleEaten, int whenGrandirApple) {

        int meilleur_score = -1000;
        if (!isAppleEaten){
            if (isAppleEaten()) {
                isAppleEaten = true;
            }
        }
        if (isAppleEaten) {
            if (whenAppleEaten < 3) {
                GamePanel2.grandir();
                whenGrandirApple++;
            }
            whenAppleEaten++;
        }
        if (profond >= profondeur) {
            if (isAppleEaten) {
                for (int i=0 ; i<=whenGrandirApple; i++) {
                    GamePanel2.rendrepetit();
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
                    GamePanel2.rendrepetit();
                }
                solvemove(finx, finy);
                return 200 + whenAppleEaten;
            }
            solvemove(finx, finy);
            return profond;
        }
        for (char value : coup_possible) {
            if (!isOppositeDirection(lastMove, value)) {
                GamePanel2.direction = value;
                //stock la position du serpent avant le move
                int[] previousx = new int[GamePanel2.bodyParts];
                int[] previousy = new int[GamePanel2.bodyParts];
                System.arraycopy(GamePanel2.x, 0, previousx, 0, GamePanel2.bodyParts);
                System.arraycopy(GamePanel2.y, 0, previousy, 0, GamePanel2.bodyParts);
                int taille = GamePanel2.bodyParts;
                GamePanel2.move();
                //GamePanel.checkApple(false);
                int score = Minimax(value, profond + 1, previousx, previousy, isAppleEaten, whenAppleEaten, whenGrandirApple);
                GamePanel2.bodyParts = taille;
                solvemove(previousx, previousy);
                if (meilleur_score < score) {
                    meilleur_score = score;
                }
            }
        }
        return meilleur_score;

    }
    private static boolean isAppleEaten() {
        return (GamePanel2.x[0] == GamePanel2.appleX) && (GamePanel2.y[0] == GamePanel2.appleY);
    }
    public static boolean isGameOver() {
        //checks if head collides with body
        for (int i = GamePanel2.bodyParts - 1; i > 0; i--) {
            if ((GamePanel2.x[0] == GamePanel2.x[i]) && (GamePanel2.y[0] == GamePanel2.y[i])) {
                return true;
            }
        }
        //check if head touches left border
        if(GamePanel2.x[0] < 0) {
            return true;
        }
        //check if head touches right border
        if(GamePanel2.x[0] > GamePanel2.SCREEN_WIDTH- GamePanel2.UNIT_SIZE) {
            return true;
        }
        //check if head touches top border
        if(GamePanel2.y[0] < 0) {
            return true;
        }
        //check if head touches bottom border
        if(GamePanel2.y[0] > (GamePanel2.SCREEN_HEIGHT- GamePanel2.UNIT_SIZE)) {
            return true;
        }
        return false;
    }
    static void solvemove(int[] previousx, int[] previousy) {
        System.arraycopy(previousx, 0, GamePanel2.x, 0, GamePanel2.bodyParts-1);
        System.arraycopy(previousy, 0, GamePanel2.y, 0, GamePanel2.bodyParts-1);
    }
    private static boolean isOppositeDirection(char dir1, char dir2) {
        return (dir1 == 'U' && dir2 == 'D') || (dir1 == 'D' && dir2 == 'U') || (dir1 == 'L' && dir2 == 'R') || (dir1 == 'R' && dir2 == 'L');
    }
}