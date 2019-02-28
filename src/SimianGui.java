import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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

    private ArrayList<String> signals;

    private HeaderParser headerParser;

    public SimianGui() {
        this.layout = new MigLayout("wrap 8");
        this.signals = new ArrayList<String>();
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
    }

    private void addSignal(String signal) {
        JLabel label = new JLabel(signal);

        JTextField textField = new JTextField("0");
        textField.setPreferredSize(new Dimension(90, 20));

        this.addTestCaseArea.add(textField);
        this.addTestCaseArea.add(label);

        this.addTestCaseArea.revalidate();
    }

    private void addWait() {
        JLabel label = new JLabel("Wait for: ");

        JTextField textField = new JTextField("20");
        textField.setPreferredSize(new Dimension(90, 20));

        this.addTestCaseArea.add(label, "span 1");
        this.addTestCaseArea.add(textField, "span 2");

        this.addTestCaseArea.revalidate();
    }

    // Uses the values in the ADD_TESTCASE_AREA to create a JPanel displaying them
    // JPanel contains a self destruct button
    private JPanel createTestCase() {

        // Create testcase frame
        JPanel testCase = new JPanel();
        testCase.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        testCase.setLayout(new MigLayout("wrap 2"));

        // Create self destruct button
        JButton deleteCase = new JButton("X");
        deleteCase.addActionListener(new DeleteParentButtonListener());

        populateTestCase(testCase);
        testCase.add(deleteCase, "span 1");

        return testCase;
    }

    // fills a JPanel based off of input from ADD_TESTCASE_AREA
    private void populateTestCase(JPanel testCase) {
        Component[] components = this.addTestCaseArea.getComponents();
        for (int i = 0; i < components.length - 1; i++) {
            if (components[i] instanceof JTextField) {
                JTextField copyTextfield = new JTextField();
                JLabel copyLabel = new JLabel();

                JTextField currentTextField = (JTextField) components[i];
                copyTextfield = new JTextField();
                copyTextfield.setText(currentTextField.getText());
                copyTextfield.setSize(currentTextField.getSize());

                JLabel current_label = (JLabel) components[i + 1];
                copyLabel = new JLabel();
                copyLabel.setText(current_label.getText());
                copyLabel.setSize(current_label.getSize());

                testCase.add(copyLabel, "span 1");
                testCase.add(copyTextfield, "span 1");

            }
        }
        JLabel waitLabel = new JLabel();
        JTextField lastComponent = (JTextField) components[components.length - 1];
        waitLabel.setText(lastComponent.getText());
        testCase.add(waitLabel, "span 1");
    }

    private void addTestCase() {
        this.editTestCaseArea.add(createTestCase(), "span 1");
        this.editTestCaseArea.revalidate();
        this.editTestCaseArea.updateUI();
    }

    private void submitHeader() {
        // remove current signals
        addTestCaseArea.removeAll();

        // load input header to header parser
        headerParser.load(inputHeaderArea.getText());

        // get signals from header parser
        signals = headerParser.findInputs();
        for (String signal : signals) {
            addSignal(signal);
        }
        addWait();

    }

    private void killParent(Component child){

        // child is the button

        // parent is the test case panel
        Component parent = child.getParent();

        // grandparent is the edit test case panel
        this.editTestCaseArea.remove(parent);
        this.scrollEditTestCaseArea.repaint();
        this.scrollEditTestCaseArea.updateUI();
    }

    private class InputHeaderButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            submitHeader();

        }
    }

    private class DeleteParentButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof Component){
                Component child = (Component) source;
                killParent(child);
            }
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


}
