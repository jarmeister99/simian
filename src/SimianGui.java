import components.SetTestCase;
import components.TestCase;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SimianGui extends JPanel {
    private MigLayout layout;

    // COLUMNS 1-2
    private JScrollPane scrollInputHeaderArea;
    private JLabel inputHeaderLabel;
    private JTextArea inputHeaderArea;
    private JButton inputHeaderButton;

    // COLUMNS 3-4
    private JScrollPane scrollAddTestCaseArea;
    private JLabel addTestCaseLabel;
    private JPanel addTestCaseArea;
    private JButton addTestCaseButton;
    private JButton generateButton;

    // COLUMNS 5-6
    private JScrollPane scrollEditTestCaseArea;
    private JPanel editTestCaseArea;

    // COLUMNS 7-8
    private JScrollPane scrollOutputCodeArea;
    private JTextArea outputCodeArea;

    private HeaderParser headerParser;

    private SetTestCase setTestCase;
    private ArrayList<String> inputs;

    public SimianGui() {
        this.layout = new MigLayout("wrap 8");
        this.headerParser = new HeaderParser();
        this.setLayout(this.layout);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buildGui();
        attachListeners();

    }

    private void buildGui() {
        this.inputHeaderLabel = new JLabel("Paste Module Header");
        this.add(this.inputHeaderLabel, "span 2 1");

        this.addTestCaseLabel = new JLabel("Add Test Cases");
        this.add(this.addTestCaseLabel, "span 2 1");

        this.editTestCaseArea = new JPanel();
        this.editTestCaseArea.setLayout(new MigLayout("wrap 1"));
        this.scrollEditTestCaseArea = new JScrollPane(this.editTestCaseArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollEditTestCaseArea.setPreferredSize(new Dimension(250, 400));
        this.scrollEditTestCaseArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollEditTestCaseArea, "span 2 6");

        this.outputCodeArea = new JTextArea();
        this.scrollOutputCodeArea = new JScrollPane(this.outputCodeArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollOutputCodeArea.setPreferredSize(new Dimension(250, 400));
        this.scrollOutputCodeArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollOutputCodeArea, "span 2 6");

        this.inputHeaderArea = new JTextArea(
                "module COOL_MODULE(\n" +
                        "    input in1, in2, in3, in4,\n" +
                        "    input [1:0] in5,\n" +
                        "    input [9:0] in6, in7,\n" +
                        "    output reg [9:0] out1\n" +
                        ");"
        );
        this.scrollInputHeaderArea = new JScrollPane(this.inputHeaderArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollInputHeaderArea.setPreferredSize(new Dimension(250, 350));
        this.scrollInputHeaderArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollInputHeaderArea, "span 2 4");

        this.addTestCaseArea = new JPanel();
        this.addTestCaseArea.setLayout(new MigLayout("wrap 2"));
        this.scrollAddTestCaseArea = new JScrollPane(this.addTestCaseArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollAddTestCaseArea.setPreferredSize(new Dimension(300, 350));
        this.scrollAddTestCaseArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollAddTestCaseArea, "span 2 4");

        this.inputHeaderButton = new JButton("Submit Header");
        this.inputHeaderButton.setPreferredSize(new Dimension(250, 20));
        this.add(this.inputHeaderButton, "span 2 1");

        this.addTestCaseButton = new JButton("Add");
        this.addTestCaseButton.setPreferredSize(new Dimension(150, 20));
        this.add(this.addTestCaseButton, "span 1 1");

        this.generateButton = new JButton("Gen Out");
        this.generateButton.setPreferredSize(new Dimension(150, 20));
        this.add(this.generateButton, "span 1 1");
    }

    private void attachListeners() {
        this.inputHeaderArea.addMouseListener(new InputHeaderAreaListener());
        this.inputHeaderButton.addActionListener(new InputHeaderButtonListener());
        this.addTestCaseButton.addActionListener(new AddTestCaseButtonListener());
        this.generateButton.addActionListener((new GenerateCodeButtonListener()));
    }

    private void addTestCase() {
        if (this.setTestCase != null) {
            HashMap<String, Integer> signals = getSignalInput();
            int delay = getDelay();
            if (signals.size() > 0 && delay != -1) {
                this.editTestCaseArea.add(createTestCase(), "span 1");
                this.editTestCaseArea.revalidate();
                this.editTestCaseArea.updateUI();
            }
        }

    }

    private JPanel createTestCase() {
        JPanel testCase = new TestCase(getSignalInput(), getDelay());
        resetSignalInputs();
        return testCase;
    }

    private HashMap<String, Integer> getSignalInput() {
        HashMap<String, Integer> signalInput = new HashMap<>();
        Component[] components = this.setTestCase.getComponents();
        if (components.length == 0) {
            return signalInput;
        }
        for (int i = 0; i < components.length - 2; i += 2) {
            JLabel label = (JLabel) components[i + 1];
            JTextField textField = (JTextField) components[i];
            if (!(textField.getText().equals(""))) {
                signalInput.put(label.getText(), Integer.parseInt(textField.getText()));
            }

        }
        return signalInput;
    }

    private void resetSignalInputs() {
        Component[] components = this.setTestCase.getComponents();
        if (components.length != 0) {
            for (int i = 0; i < components.length - 1; i++) {
                if (components[i] instanceof JTextField) {
                    JTextField textField = (JTextField) components[i];
                    textField.setText("");
                }
            }
        }
    }

    private int getDelay() {
        Component[] components = this.setTestCase.getComponents();
        if (components.length == 0) {
            return -1;
        }
        JTextField delay = (JTextField) components[components.length - 1];
        if (!(delay.getText().equals(""))) {
            return Integer.parseInt(delay.getText());
        }
        return -1;
    }

    private void submitHeader() {
        addSetTestCase();
    }

    private void addSetTestCase() {
        this.addTestCaseArea.removeAll();
        ArrayList<String> inputs = this.getInputs();
        this.inputs = inputs;
        if (inputs.size() != 0) {
            ArrayList<String> signalInputs = new ArrayList<String>(inputs.subList(1, inputs.size()));
            this.setTestCase = new SetTestCase(signalInputs);
            this.addTestCaseArea.add(this.setTestCase, "span 1");
        } else {
            this.editTestCaseArea.removeAll();
            this.editTestCaseArea.revalidate();
            this.editTestCaseArea.repaint();
            this.addTestCaseArea.removeAll();
            this.addTestCaseArea.revalidate();
            this.addTestCaseArea.repaint();
        }
        this.addTestCaseArea.revalidate();
    }

    private ArrayList<String> getInputs() {
        headerParser.load(inputHeaderArea.getText());
        return headerParser.getInputs();
    }


    private class InputHeaderButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            submitHeader();

        }
    }

    private class AddTestCaseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addTestCase();
        }
    }

    private class InputHeaderAreaListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            inputHeaderArea.setText("");
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class GenerateCodeButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

}
