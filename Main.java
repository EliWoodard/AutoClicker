import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * AutoClicker is a Java program that simulates mouse clicks at a given
 * interval.
 * The user can select the trigger button (left or right), click type (left or
 * right),
 * and number of clicks to perform.
 * 
 * The program also listens to mouse clicks globally and starts the autoclicker
 * when the
 * user clicks the mouse.
 * 
 * This program uses Java's Robot class to simulate mouse clicks and
 * AWTEventListeners
 * to detect mouse clicks.
 * 
 * @author Eli Woodard
 */
public class Main implements ActionListener {

    private JFrame frame;
    private JComboBox<String> triggerButtonBox;
    private JComboBox<String> clickTypeBox;
    private JSpinner clickSpinner;
    private JButton startButton;
    private JButton stopButton;
    private volatile boolean clicking;
    private int clickCount;

    /**
     * The main method starts the program by creating a new instance of the Main
     * class.
     * 
     * @param args the command line arguments (not used in this program)
     */
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        frame = new JFrame("AutoClicker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] triggerButtons = { "Left", "Right" };
        triggerButtonBox = new JComboBox<>(triggerButtons);
        triggerButtonBox.setSelectedIndex(0);

        String[] clickTypes = { "Left Click", "Right Click" };
        clickTypeBox = new JComboBox<>(clickTypes);
        clickTypeBox.setSelectedIndex(0);

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 3, 1);
        clickSpinner = new JSpinner(model);

        startButton = new JButton("Start");
        startButton.addActionListener(this);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);

        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Trigger Button:"));
        panel1.add(triggerButtonBox);
        frame.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Click Type:"));
        panel2.add(clickTypeBox);
        frame.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Clicks:"));
        panel3.add(clickSpinner);
        frame.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.add(startButton);
        panel4.add(stopButton);
        frame.add(panel4);

        frame.setVisible(true);

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent) {
                    MouseEvent mouseEvent = (MouseEvent) event;
                    clicking = true;
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            clickCount = (int) clickSpinner.getValue();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Robot robot = new Robot();
                        int triggerButton = triggerButtonBox.getSelectedIndex() == 0 ? InputEvent.BUTTON1_DOWN_MASK
                                : InputEvent.BUTTON3_DOWN_MASK;
                        int clickType = clickTypeBox.getSelectedIndex() == 0 ? InputEvent.BUTTON1_DOWN_MASK
                                : InputEvent.BUTTON3_DOWN_MASK;

                        while (!startButton.isEnabled()) {
                            if (clicking == true && !startButton.isEnabled()) { // only click if clicking is true
                                System.out.println(clicking);
                                robot.waitForIdle();
                                for (int i = 0; i < clickCount; i++) {
                                    robot.mousePress(clickType);
                                    robot.mouseRelease(clickType);
                                }
                            }
                            clicking = false;
                        }
                    } catch (AWTException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            t.start(); // start the thread
        } else if (e.getSource() == stopButton) {
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        }
    }
}
