package components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TestCase extends JPanel {

    // Example: {"DATA_IN" : 1101}
    private LinkedHashMap<String, Integer> signals;
    private int delay;

    public TestCase(LinkedHashMap<String, Integer> signals, int delay) {

        this.signals = signals;
        this.delay = delay;
        JButton deleteCase = new JButton("X");
        deleteCase.addActionListener(new DeleteCaseButtonListener());
        this.setLayout(new MigLayout("wrap 2"));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        for (String key : this.signals.keySet()) {
            this.addSignal(key, this.signals.get(key));
        }
        this.add(new JLabel(Integer.toString(delay)), "span 1");
        this.add(deleteCase, "span 1");
        this.repaint();
    }

    public LinkedHashMap<String, Integer> getSignals(){
        return this.signals;
    }

    public int getDelay(){
        return this.delay;
    }

    private void addSignal(String label, int value) {
        if (value != 0) {
            this.signals.put(label, value);
            this.add(new JLabel(label), "span 1");
            this.add(new JLabel(Integer.toString(value)), "span 1");
        }
    }


    private void deleteSelf() {
        Container grandParent = this.getParent();
        this.getParent().remove(this);
        grandParent.repaint();
        JViewport greatGrandParent = (JViewport) grandParent.getParent();
        greatGrandParent.updateUI();
    }

    private class DeleteCaseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteSelf();
        }
    }
}
