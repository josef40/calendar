package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MainFrame {
    private final JFrame frame;
    private final Locale locale = Locale.ENGLISH;
    private final Map<LocalDate, JPanel> dayColumns = new HashMap<>();

    private BiConsumer<LocalDate, String> onAddActivity;
    private BiConsumer<LocalDate, String> onRemoveActivity;

    public MainFrame() {
        LocalDate now = LocalDate.now();
        int currentWeek = now.get(WeekFields.ISO.weekOfWeekBasedYear());

        frame = new JFrame("Calendar");
        frame.setSize(1280, 720);
        frame.add(createWeekHeader(currentWeek), BorderLayout.NORTH);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        JPanel activitiesPanel = new JPanel(new GridLayout(1, 7));
        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = monday.plusDays(i);
            headerPanel.add(createDayHeader(currentDate, now));
            activitiesPanel.add(createDayColumn(currentDate));
        }

        JPanel container = new JPanel(new BorderLayout());
        container.add(headerPanel, BorderLayout.NORTH);
        container.add(activitiesPanel, BorderLayout.CENTER);
        frame.add(container, BorderLayout.CENTER);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setOnAddActivity(BiConsumer<LocalDate, String> listener) {
        this.onAddActivity = listener;
    }

    public void setOnRemoveActivity(BiConsumer<LocalDate, String> listener) {
        this.onRemoveActivity = listener;
    }

    public void refreshDay(LocalDate date, List<String> activities) {
        JPanel scrollPanel = dayColumns.get(date);
        if (scrollPanel == null)
            return;

        // Keep only the '+' button (the last component) and clear old cards
        while (scrollPanel.getComponentCount() > 1) {
            scrollPanel.remove(0);
        }

        for (String text : activities) {
            addActivityCard(scrollPanel, date, text);
        }

        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

    private JPanel createWeekHeader(int weekNumber) {
        JPanel weekPanel = new JPanel(new BorderLayout());
        weekPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        weekPanel.add(new JLabel("Week " + weekNumber, SwingConstants.CENTER));
        return weekPanel;
    }

    private JPanel createDayHeader(LocalDate date, LocalDate today) {
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
        int dayNumber = date.getDayOfMonth();
        String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, locale);

        JPanel datePanel = new JPanel(new GridLayout(2, 1));
        if (date.equals(today)) {
            datePanel.setBackground(new Color(175, 215, 230));
        }
        datePanel.add(new JLabel(dayName, SwingConstants.CENTER));
        datePanel.add(new JLabel(dayNumber + " " + monthName, SwingConstants.CENTER));
        return datePanel;
    }

    private JScrollPane createDayColumn(LocalDate date) {
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dayColumns.put(date, scrollPanel);

        JButton addButton = new JButton("+");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setPreferredSize(new Dimension(0, 50));
        addButton.setMinimumSize(new Dimension(0, 50));
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Activity for " + dayName + ":");
            if (onAddActivity != null) {
                onAddActivity.accept(date, input);
            }
        });

        scrollPanel.add(addButton);

        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        return scrollPane;
    }

    private void addActivityCard(JPanel scrollPanel, LocalDate date, String text) {
        JTextPane cardContent = new JTextPane();
        cardContent.setText(text);
        cardContent.setEditable(false);
        cardContent.setFont(UIManager.getFont("Label.font"));

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        cardContent.getStyledDocument().setParagraphAttributes(0, cardContent.getDocument().getLength(), center, false);

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
        card.setMinimumSize(new Dimension(0, 50));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        removeButton.addActionListener(ev -> {
            if (onRemoveActivity != null) {
                onRemoveActivity.accept(date, text);
            }
        });

        int index = scrollPanel.getComponentCount() - 1;
        scrollPanel.add(card, index);
        scrollPanel.add(Box.createVerticalStrut(4), index + 1);
    }

    public void show() {
        frame.setVisible(true);
    }
}
