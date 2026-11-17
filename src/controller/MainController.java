package controller;

import model.Calendar;
import ui.MainFrame;

import java.time.LocalDate;

/**
 * acts as the middle man between MainFrame and Calendar, adding and removing
 * actitivies both internally and externally
 */
public class MainController {
    private final Calendar calendar;
    private final MainFrame frame;

    /**
     * stores references to the other classes, and also make so methods run when
     * frame methods being ran
     */
    public MainController(Calendar calendar, MainFrame frame) {
        this.calendar = calendar;
        this.frame = frame;

        this.frame.setOnAddActivity(this::handleAddActivity);
        this.frame.setOnRemoveActivity(this::handleRemoveActivity);
    }

    /**
     * handles input and adds it as activity, checks if input is null or blank,
     * cleans text, adds it to
     * activites list in Calendar, and then adds to the ui using MainFrame method
     */
    private void handleAddActivity(LocalDate date, String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return;
        }
        String text = rawInput.trim().replaceAll(" +", " ");
        calendar.addActivity(date, text);
        frame.refreshDay(date, calendar.getActivities(date));
    }

    /** removes activity from list in Calendar and the ui */
    private void handleRemoveActivity(LocalDate date, String text) {
        calendar.removeActivity(date, text);
        frame.refreshDay(date, calendar.getActivities(date));
    }

    /**
     * calls show method from MainFrame which sets frame to visible, need to have
     * this instead of calling show from main directly since we need to make sure
     * this class is initialized before setting
     * frame to visible
     */
    public void start() {
        frame.show();
    }
}
