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
import java.util.HashMap;

public class SimianGui extends JPanel {
    private MigLayout layout;

    // COLUMNS 1-2
    private JScrollPane scrollInputHeaderTextArea;
    private JLabel inputHeaderLabel;
    private JTextArea inputHeaderTextArea;
    private JButton submitHeaderButton;

    // COLUMNS 3-4
    private JScrollPane scrollInputSignalsArea;
    private JLabel inputSignalsLabel;
    private JPanel inputSignalsArea;
    private JButton addTestCaseButton;
    private JButton generateCodeButton;

    // COLUMNS 5-6
    private JScrollPane scrollEditTestCaseArea;
    private JPanel editTestCaseArea;

    // COLUMNS 7-8
    private JScrollPane scrollOutputCodeTextArea;
    private JTextArea outputCodeTextArea;

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

        this.inputSignalsLabel = new JLabel("Add Test Cases");
        this.add(this.inputSignalsLabel, "span 2 1");

        this.editTestCaseArea = new JPanel();
        this.editTestCaseArea.setLayout(new MigLayout("wrap 1"));
        this.scrollEditTestCaseArea = new JScrollPane(this.editTestCaseArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollEditTestCaseArea.setPreferredSize(new Dimension(250, 400));
        this.scrollEditTestCaseArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollEditTestCaseArea, "span 2 6");

        this.outputCodeTextArea = new JTextArea();
        this.scrollOutputCodeTextArea = new JScrollPane(this.outputCodeTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollOutputCodeTextArea.setPreferredSize(new Dimension(250, 400));
        this.scrollOutputCodeTextArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollOutputCodeTextArea, "span 2 6");

        this.inputHeaderTextArea = new JTextArea(
                "module COOL_MODULE(\n" +
                        "    input in1, in2, in3, in4,\n" +
                        "    input [1:0] in5,\n" +
                        "    input [9:0] in6, in7,\n" +
                        "    output reg [9:0] out1\n" +
                        ");"
        );
        this.scrollInputHeaderTextArea = new JScrollPane(this.inputHeaderTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollInputHeaderTextArea.setPreferredSize(new Dimension(250, 350));
        this.scrollInputHeaderTextArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollInputHeaderTextArea, "span 2 4");

        this.inputSignalsArea = new JPanel();
        this.inputSignalsArea.setLayout(new MigLayout("wrap 2"));
        this.scrollInputSignalsArea = new JScrollPane(this.inputSignalsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollInputSignalsArea.setPreferredSize(new Dimension(300, 350));
        this.scrollInputSignalsArea.getVerticalScrollBar().setUnitIncrement(16);
        this.add(this.scrollInputSignalsArea, "span 2 4");

        this.submitHeaderButton = new JButton("Submit Header");
        this.submitHeaderButton.setPreferredSize(new Dimension(250, 20));
        this.add(this.submitHeaderButton, "span 2 1");

        this.addTestCaseButton = new JButton("Add");
        this.addTestCaseButton.setPreferredSize(new Dimension(150, 20));
        this.add(this.addTestCaseButton, "span 1 1");

        this.generateCodeButton = new JButton("Gen Out");
        this.generateCodeButton.setPreferredSize(new Dimension(150, 20));
        this.add(this.generateCodeButton, "span 1 1");
    }

    private void attachListeners() {
        this.inputHeaderTextArea.addMouseListener(new InputHeaderAreaListener());
        this.submitHeaderButton.addActionListener(new InputHeaderButtonListener());
        this.addTestCaseButton.addActionListener(new AddTestCaseButtonListener());
        this.generateCodeButton.addActionListener((new GenerateCodeButtonListener()));
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
        this.inputSignalsArea.removeAll();
        ArrayList<String> inputs = this.getInputs();
        this.inputs = inputs;
        if (inputs.size() != 0) {
            ArrayList<String> signalInputs = new ArrayList<String>(inputs.subList(1, inputs.size()));
            this.setTestCase = new SetTestCase(signalInputs);
            this.inputSignalsArea.add(this.setTestCase, "span 1");
        } else {
            this.editTestCaseArea.removeAll();
            this.editTestCaseArea.revalidate();
            this.editTestCaseArea.repaint();
            this.inputSignalsArea.removeAll();
            this.inputSignalsArea.revalidate();
            this.inputSignalsArea.repaint();
        }
        this.inputSignalsArea.revalidate();
    }

    private ArrayList<String> getInputs() {
        headerParser.load(inputHeaderTextArea.getText());
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
            inputHeaderTextArea.setText("");
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
