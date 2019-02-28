import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Simian extends JFrame {
    private final int WINDOW_WIDTH = 650;
    private final int WINDOW_HEIGHT = 500;
    public Simian(JPanel gui){
        this.add(gui);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simian");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }
    public static void main(String[] args){
        JPanel gui = new SimianGui();
        JFrame main = new Simian(gui);
    }

}
