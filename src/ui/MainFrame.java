package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.BorderFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MainFrame {
    // instance frame of extending class so we don't expose methods we don't need
    private final JFrame frame;

    private final Map<LocalDate, List<String>> activities = new HashMap<>();

    public MainFrame() {
        LocalDate now = LocalDate.now();
        int currentWeek = now.get(WeekFields.ISO.weekOfWeekBasedYear());
        Locale locale = Locale.ENGLISH;

        frame = new JFrame("Calendar");
        frame.setSize(1280, 720);

        JPanel weekPanel = new JPanel(new BorderLayout());
        weekPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JLabel weekLabel = new JLabel("Week " + currentWeek, SwingConstants.CENTER);
        weekPanel.add(weekLabel);
        frame.add(weekPanel, BorderLayout.NORTH);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        JPanel activitiesPanel = new JPanel(new GridLayout(1, 7));
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

            // scrollable column
            JPanel scrollPanel = new JPanel();
            scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
            scrollPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JButton addButton = new JButton("+");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addButton.setPreferredSize(new Dimension(0, 50));
            addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            addButton.addActionListener(e -> {
                String activity = JOptionPane.showInputDialog(frame, "Activity for " + dayName + ":");
                if (activity != null && !activity.isBlank()) {
                    String text = activity.trim().replaceAll(" +", " ");

                    JTextPane cardContent = new JTextPane();
                    cardContent.setText(text);
                    cardContent.setEditable(false);
                    cardContent.setFont(weekLabel.getFont());

                    SimpleAttributeSet center = new SimpleAttributeSet();
                    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                    cardContent.getStyledDocument().setParagraphAttributes(0, cardContent.getDocument().getLength(),
                            center, false);

                    JPanel card = new JPanel(new GridBagLayout());
                    card.setBackground(Color.WHITE);
                    card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.weightx = 1.0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    card.add(cardContent, gbc);

                    JButton removeButton = new JButton("x");
                    removeButton.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                    removeButton.setContentAreaFilled(false);
                    removeButton.setFocusPainted(false);
                    card.add(removeButton);

                    card.setPreferredSize(new Dimension(0, 50));
                    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

                    Component spacer = Box.createVerticalStrut(4);
                    removeButton.addActionListener(ev -> {
                        scrollPanel.remove(card);
                        scrollPanel.remove(spacer);
                        scrollPanel.revalidate();
                        scrollPanel.repaint();
                        activities.getOrDefault(currentDate, List.of()).remove(text);
                    });

                    int index = scrollPanel.getComponentCount() - 1;
                    scrollPanel.add(card, index);
                    scrollPanel.add(spacer, index + 1);
                    scrollPanel.revalidate();

                    activities.computeIfAbsent(currentDate, k -> new ArrayList<>()).add(text);

                }
            });

            scrollPanel.add(addButton);

            JScrollPane scrollPane = new JScrollPane(scrollPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getVerticalScrollBar().setUnitIncrement(25);

            activitiesPanel.add(scrollPane);
        }
        JPanel container = new JPanel(new BorderLayout());
        container.add(headerPanel, BorderLayout.NORTH);
        container.add(activitiesPanel, BorderLayout.CENTER);
        frame.add(container, BorderLayout.CENTER);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // centers window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void show() {
        frame.setVisible(true);
    }
}