package model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * stores activities in a map, has methods that MainController uses to add,
 * remove, and get activities in MainFrame
 */
public class Calendar {
    private final Map<LocalDate, List<String>> activities = new HashMap<>();

    /**
     * adds activity to map, if no arraylist exists for that date, it creates one
     */
    public void addActivity(LocalDate date, String activity) {
        activities.computeIfAbsent(date, k -> new ArrayList<>()).add(activity);
    }

    /** removes activity from map, if it exists */
    public void removeActivity(LocalDate date, String activity) {
        List<String> activitiesReference = activities.get(date);
        if (activitiesReference != null) {
            activitiesReference.remove(activity);
        }
    }

    /** returns list of activities, if none exists, return empty list */
    public List<String> getActivities(LocalDate date) {
        return Collections.unmodifiableList(activities.getOrDefault(date, List.of()));
    }
}
