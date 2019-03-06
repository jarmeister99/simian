package components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SetTestCase extends JPanel {
    private LinkedHashMap<String, Integer> signals;
    public SetTestCase(LinkedHashMap<String, Integer> signals){
        this.signals = signals;
        this.setLayout(new MigLayout("wrap 2"));
        for (String signal : signals.keySet()){
            this.addSignal(signal, signals.get(signal));
        }
        this.add(new JLabel("Wait for"), "span 1");
        JTextField waitField = new JTextField("");
        waitField.setPreferredSize(new Dimension(90, 20));
        this.add(waitField, "span 1");
        this.repaint();
    }

    private void addSignal(String signal, int signalWidth){
        JTextField field = new JTextField("");
        field.setPreferredSize(new Dimension(90, 20));
        this.add(field, "span 1");
        JLabel label;
        if (signalWidth > 1){
            label = new JLabel(signal + " [" + (signalWidth - 1) + ":0]");
        }
        else{
            label = new JLabel(signal);
        }
        this.add(label, "span 1");

    }


}
