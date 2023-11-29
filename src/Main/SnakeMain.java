package Main;

import Game.*;

public class SnakeMain {
    public static void main(String[] args) {
        Game game = new Game(new Board(), new Snake(), new Apple());
    }
}