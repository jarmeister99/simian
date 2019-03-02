package components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class SetTestCase extends JPanel {
    private ArrayList<String> signals;
    public SetTestCase(ArrayList<String> signals){
        this.signals = signals;
        this.setLayout(new MigLayout("wrap 2"));
        for (String signal : signals){
            this.addSignal(signal);
        }
        this.add(new JLabel("Wait for"), "span 1");
        JTextField waitField = new JTextField("");
        waitField.setPreferredSize(new Dimension(90, 20));
        this.add(waitField, "span 1");
        this.repaint();
    }

    private void addSignal(String signal){
        JTextField field = new JTextField("");
        field.setPreferredSize(new Dimension(90, 20));
        this.add(field, "span 1");
        JLabel label = new JLabel(signal);
        this.add(label, "span 1");

    }


}
