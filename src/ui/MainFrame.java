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

/**
 * handles the UI part of the app, creates window and components that make up
 * the visuals, also gives interactive components like buttons functionality
 */
public class MainFrame {
    private final JFrame frame;
    private final Locale locale = Locale.ENGLISH;
    private final Map<LocalDate, JPanel> dayColumns = new HashMap<>();

    // we use these to call methods inside MainController, it's good so we
    // completeley disconnect activity handling from this class, and let
    // MainController handle it solely
    private BiConsumer<LocalDate, String> onAddActivity;
    private BiConsumer<LocalDate, String> onRemoveActivity;

    /** creates a centered window (JFrame) with a fixed size, adds week header, */
    public MainFrame() {
        LocalDate now = LocalDate.now();
        int currentWeek = now.get(WeekFields.ISO.weekOfWeekBasedYear());

        frame = new JFrame("Calendar");
        frame.setSize(1280, 720);
        frame.setResizable(false);

        JPanel topSection = new JPanel(new BorderLayout()); // top section holds week header and the 7 dates

        topSection.add(createWeekHeader(currentWeek), BorderLayout.NORTH);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        JPanel activitiesPanel = new JPanel(new GridLayout(1, 7));
        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); // gets date of latest monday
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = monday.plusDays(i);
            headerPanel.add(createDayHeader(currentDate, now));
            activitiesPanel.add(createDayColumn(currentDate));
        }
        topSection.add(headerPanel, BorderLayout.CENTER);

        frame.add(topSection, BorderLayout.NORTH);
        frame.add(activitiesPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null); // centers window on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * sets MainFrame Biconsumer onAddActivity to MainController handleAddActivity
     * method
     */
    public void setOnAddActivity(BiConsumer<LocalDate, String> listener) {
        this.onAddActivity = listener;
    }

    /**
     * sets MainFrame Biconsumer onRemoveActivity to MainController
     * handleRemoveActivity method
     */
    public void setOnRemoveActivity(BiConsumer<LocalDate, String> listener) {
        this.onRemoveActivity = listener;
    }

    /**
     * refreshes/updates specific day column so it shows current data instead of old
     */
    public void refreshDay(LocalDate date, List<String> activities) {
        JPanel scrollPanel = dayColumns.get(date);
        // safety check in case date is not in current week
        if (scrollPanel == null)
            return;

        // removes cards as long as there is more than one, removes from top, so button
        // won't be deleted
        while (scrollPanel.getComponentCount() > 1) {
            scrollPanel.remove(0);
        }

        // adds card for every activity
        for (String text : activities) {
            addActivityCard(scrollPanel, date, text);
        }

        scrollPanel.revalidate(); // recalculates layout
        scrollPanel.repaint(); // redraws pixels
    }

    /** creates panel that displays week number */
    private JPanel createWeekHeader(int weekNumber) {
        JPanel weekPanel = new JPanel(new BorderLayout());
        weekPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // spacing
        weekPanel.add(new JLabel("Week " + weekNumber, SwingConstants.CENTER));
        return weekPanel;
    }

    /** creates panel that displays day name, day number, and month name */
    private JPanel createDayHeader(LocalDate date, LocalDate today) {
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
        int dayNumber = date.getDayOfMonth();
        String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, locale);

        JPanel datePanel = new JPanel(new GridLayout(2, 1));
        if (date.equals(today)) {
            datePanel.setBackground(new Color(175, 215, 230)); // marks current day with background color
        }
        datePanel.add(new JLabel(dayName, SwingConstants.CENTER));
        datePanel.add(new JLabel(dayNumber + " " + monthName, SwingConstants.CENTER));
        return datePanel;
    }

    /**
     * sets up the scrolling area, adds add activity button, and adds activity with
     * dialog when press button
     */
    private JScrollPane createDayColumn(LocalDate date) {
        // creates area will boxes will be
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // spacing
        dayColumns.put(date, scrollPanel); // stores date and scroll panel

        // create add activity button
        JButton addButton = new JButton("+");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setPreferredSize(new Dimension(0, 50));
        addButton.setMinimumSize(new Dimension(0, 50));
        addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addButton.setFocusPainted(false); // removes the highlight when click

        // shows pop dialog when press add button
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Activity for " + dayName + ":");
            // prevents from crashing if onAddActivity is not assigned yet
            if (onAddActivity != null) {
                onAddActivity.accept(date, input); // takes the parameters and executes the method connected to
                                                   // onAddActivity
            }
        });

        scrollPanel.add(addButton); // adds button to box area

        // makes the scroll area scrollable
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        return scrollPane;
    }

    /** creates activity card using passed panel, date, text */
    private void addActivityCard(JPanel scrollPanel, LocalDate date, String text) {
        // creates card content
        JTextPane cardContent = new JTextPane();
        cardContent.setText(text);
        cardContent.setEditable(false);
        cardContent.setFont(UIManager.getFont("Label.font"));
        cardContent.setToolTipText(text);

        // centers text in card content
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        cardContent.getStyledDocument().setParagraphAttributes(0, cardContent.getDocument().getLength(), center, false);

        // creates card which holds the card content
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        // set size of card
        card.setPreferredSize(new Dimension(0, 50));
        card.setMinimumSize(new Dimension(0, 50));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // makes card content utilize all space it has inside card
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(cardContent, gbc);

        // creates remove button icon for card
        JButton removeButton = new JButton("x");
        removeButton.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6)); // spacing
        removeButton.setContentAreaFilled(false); // removes background color
        removeButton.setFocusPainted(false); // removes the highlight when click
        card.add(removeButton);
        // gives remove button functionality
        removeButton.addActionListener(ev -> {
            if (onRemoveActivity != null) {
                onRemoveActivity.accept(date, text);
            }
        });

        // adds card to passed scrollPanel
        int index = scrollPanel.getComponentCount() - 1; // gets index above button
        scrollPanel.add(card, index); // adds card above button
        scrollPanel.add(Box.createVerticalStrut(4), index + 1); // adds spacing between button and new card
    }

    /** makes JFrame visible */
    public void show() {
        frame.setVisible(true);
    }
}
