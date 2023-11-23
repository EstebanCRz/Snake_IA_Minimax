package Frame;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame(){

        this.add(new GamePanel2());
        this.setTitle("projetSnake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
