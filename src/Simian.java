import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Simian extends JFrame {
    public Simian(JPanel gui){
        this.add(gui);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simian");
        this.setSize(SimianSettings.WINDOW_WIDTH, SimianSettings.WINDOW_HEIGHT);
        this.setResizable(true);
        this.setVisible(true);
        this.pack();
    }
    public static void main(String[] args){
        JPanel gui = new SimianGui();
        JFrame main = new Simian(gui);
    }

}
