import javax.swing.*;

public class Simian extends JFrame {
    private final int WINDOW_WIDTH = 650;
    private final int WINDOW_HEIGHT = 500;
    public Simian(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simian");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    public static void main(String[] args){
        JFrame main = new Simian();
        JPanel gui = new SimianGui();
        main.add(gui);
        main.setVisible(true);
        main.pack();
    }
}
