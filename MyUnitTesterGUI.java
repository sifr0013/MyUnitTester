import javax.swing.*;
import java.awt.*;

/**
 * MyUnitTesterGUI handles the view of the program.
 */
public class MyUnitTesterGUI
{
    private JFrame frame;

    private JButton runTestButton;
    private JButton clearButton;

    private JTextField textInputField;
    private JTextArea textOutputArea;

    /**
     * The constructor of the GUI for the program.
     * @param title Sets the title of the GUI.
     */
    public MyUnitTesterGUI(String title)
    {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel middlePanel = buildMiddlePanel();
        JPanel upperPanel = buildUpperPanel();
        JPanel lowerPanel = buildLowerPanel();

        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        frame.pack();
    }

    /**
     * Public method to show the GUI after building it.
     */
    public void show()
    {
        frame.setVisible(true);
    }

    /**
     * Builds the Northern part of the GUI and sets the flow of it to being Centered.
     * @return JPanel - Northern part.
     */
    private JPanel buildUpperPanel()
    {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel fileLabel = new JLabel("Test Filename:");
        textInputField = new JTextField("",15);


        runTestButton = new JButton("Run Tests");
        runTestButton.addActionListener(new ButtonListener(textInputField, textOutputArea));
        runTestButton.setActionCommand("runTestsButton");

        upperPanel.add(fileLabel);
        upperPanel.add(textInputField);
        upperPanel.add(runTestButton);

        return upperPanel;
    }

    /**
     * Method for building the middle part of the GUI. Needs to be called first, since the textOutputArea needs to be
     * instantialized before added to the ButtonListeners in the other parts of the GUI.
     * @return JPanel - Center part.
     */
    private JPanel buildMiddlePanel()
    {
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        textOutputArea = new JTextArea("Please fill in the upper field and press 'Run Tests'.",15,90);
        textOutputArea.setFont(new Font("Andale Mono", Font.PLAIN, 12));
        textOutputArea.setBackground(Color.black);
        textOutputArea.setForeground(Color.green);
        JScrollPane scrollPane = new JScrollPane(textOutputArea);
        textOutputArea.setEditable(false);
        textOutputArea.setLineWrap(true);
        middlePanel.add(textOutputArea);

        return middlePanel;
    }

    /**
     * Builds the Southern part of the GUI and sets the flow of it to being centered.
     * @return JPanel - Southern part.
     */
    private JPanel buildLowerPanel()
    {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ButtonListener(textInputField, textOutputArea));
        clearButton.setActionCommand("clearButton");

        lowerPanel.add(clearButton);

        return lowerPanel;
    }
}
