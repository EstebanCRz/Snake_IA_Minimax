import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {


    static final int SCREEN_WIDTH_TAILLE = 10;
    //taille de l'écran
    static final int SCREEN_HEIGHT_TAILLE = 10;
    //taille de l'écran
    final static int UNIT_SIZE = 55;
    //taille d'une case
    static final int SCREEN_WIDTH = SCREEN_WIDTH_TAILLE*UNIT_SIZE;
    static final int SCREEN_HEIGHT = SCREEN_HEIGHT_TAILLE*UNIT_SIZE;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 10;
    //temps entre chaque coup de l'IA
    final static int[] x = new int[GAME_UNITS+2];
    final static int[] y = new int[GAME_UNITS+2];
    static int bodyParts = 5;
    static int applesEaten = 0;
    static int appleX;
    static int appleY;
    static char direction = 'R';
    boolean running = false;
    Timer timer;
    static Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension (SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        //this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        appleX = (SCREEN_WIDTH_TAILLE/2)*UNIT_SIZE;
        appleY = (SCREEN_HEIGHT_TAILLE/2)*UNIT_SIZE;
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

        // Initialisation de la position du serpent
        for (int i = 0; i < bodyParts; i++) {
            x[i] = (SCREEN_WIDTH_TAILLE / 2 - (i+3)) * UNIT_SIZE;
            y[i] = (SCREEN_HEIGHT_TAILLE / 2) * UNIT_SIZE;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        //if win
        if(GAME_UNITS == (bodyParts+1)){
            win(g);
            running = false;
            timer.stop();
        }
        else if(running) {
            //quadrillage
            for(int i=1;i<SCREEN_WIDTH/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            }
            for(int i=1;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            //pomme
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //serpent
            //tête
            g.setColor(Color.green);
            g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

            //corps
            for(int i=1; i<bodyParts; i++) {
                // Calculer les composantes RVB en fonction de la position dans le serpent
                int red = (i * 255) / bodyParts;
                int green = 255 - ((i * 255) / bodyParts);
                int blue = 0;

                Color color = new Color(red, green, blue);
                g.setColor(color);
                g.fillRect(x[i]+i/2, y[i]+i/2, UNIT_SIZE - i, UNIT_SIZE - i);
            }
            //score
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 45));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
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
    public static void checkApple(boolean newApple) {
        if((x[0]==appleX) && (y[0]==appleY)) {
            bodyParts++;
            if (newApple) {
                applesEaten++;
                newApple();
            }
        }

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
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, SCREEN_WIDTH/7));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }
    public void win(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, SCREEN_WIDTH/7));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("You Win!!!!", (SCREEN_WIDTH - metrics.stringWidth("You Win!!!!"))/2, SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            //System.out.println("test1");
            SnakeMinimax.moveIA();
            move();
            checkApple(true);
            checkCollisions();
        }
        repaint();

    }
}