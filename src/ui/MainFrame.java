package ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Calendar");
        setSize(800, 600);

        // centers window on screen
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}