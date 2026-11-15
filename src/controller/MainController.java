package controller;

import java.time.LocalDate;
import model.Calendar;
import ui.MainFrame;

public class MainController {
    private final Calendar calendar;
    private final MainFrame view;

    public MainController(Calendar calendar, MainFrame view) {
        this.calendar = calendar;
        this.view = view;

        this.view.setOnAddActivity(this::handleAddActivity);
        this.view.setOnRemoveActivity(this::handleRemoveActivity);
    }

    private void handleAddActivity(LocalDate date, String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return;
        }
        String cleanText = rawInput.trim().replaceAll(" +", " ");
        calendar.addActivity(date, cleanText);
        view.refreshDay(date, calendar.getActivities(date));
    }

    private void handleRemoveActivity(LocalDate date, String text) {
        calendar.removeActivity(date, text);
        view.refreshDay(date, calendar.getActivities(date));
    }

    public void start() {
        view.show();
    }
}
