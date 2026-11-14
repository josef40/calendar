package ui;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import java.awt.GridLayout;

import java.util.Locale;
import java.util.Calendar;

public class MainFrame {
    // instance frame of extending class so we don't expose methods we don't need
    private final JFrame frame;

    public MainFrame() {
        Calendar now = Calendar.getInstance();
        now.setFirstDayOfWeek(Calendar.MONDAY);
        int currentWeek = now.get(Calendar.WEEK_OF_YEAR);

        frame = new JFrame("Calendar");
        // frame.setTitle("Calendar");
        frame.setSize(800, 600);

        frame.setLayout(new BorderLayout());

        JPanel topContainer = new JPanel(new GridLayout(2, 1));
        topContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel weekLabel = new JLabel("Week " + currentWeek, SwingConstants.CENTER);
        weekLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); //
        topContainer.add(weekLabel);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        for (int i = 0; i < 7; i++) {
            String dayName = now.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
            int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
            String monthName = now.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);

            JPanel dayCell = new JPanel(new GridLayout(2, 1));
            dayCell.add(new JLabel(dayName, SwingConstants.CENTER));
            dayCell.add(new JLabel(dayOfMonth + " " + monthName, SwingConstants.CENTER));

            headerPanel.add(dayCell);
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        topContainer.add(headerPanel);

        frame.add(topContainer, BorderLayout.NORTH);

        frame.setLocationRelativeTo(null); // centers window on screen

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Show() {
        frame.setVisible(true);
    }
}