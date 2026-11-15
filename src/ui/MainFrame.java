package ui;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import java.awt.GridLayout;

import java.util.Locale;

import java.awt.Color;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

public class MainFrame {
    // instance frame of extending class so we don't expose methods we don't need
    private final JFrame frame;

    public MainFrame() {
        LocalDate now = LocalDate.now();
        int currentWeek = now.get(WeekFields.ISO.weekOfWeekBasedYear());
        Locale locale = Locale.ENGLISH;

        frame = new JFrame("Calendar");
        frame.setSize(800, 600);

        JPanel topSection = new JPanel(new GridLayout(2, 1));
        topSection.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // top spacing

        JLabel weekLabel = new JLabel("Week " + currentWeek, SwingConstants.CENTER);
        weekLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // bottom spacing

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); // gets monday of current week
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = monday.plusDays(i);

            String dayName = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
            int dayNumber = currentDate.getDayOfMonth();
            String monthName = currentDate.getMonth().getDisplayName(TextStyle.SHORT, locale);

            JPanel datePanel = new JPanel(new GridLayout(2, 1));
            if (currentDate.equals(now)) {
                datePanel.setBackground(new Color(175, 215, 230));
            }
            datePanel.add(new JLabel(dayName, SwingConstants.CENTER));
            datePanel.add(new JLabel(dayNumber + " " + monthName, SwingConstants.CENTER));

            headerPanel.add(datePanel);
        }

        topSection.add(weekLabel);
        topSection.add(headerPanel);
        frame.add(topSection, BorderLayout.NORTH);

        frame.setLocationRelativeTo(null); // centers window

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void show() {
        frame.setVisible(true);
    }
}