package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendar {
    private final Map<LocalDate, List<String>> activities = new HashMap<>();

    public void addActivity(LocalDate date, String activity) {
        activities.computeIfAbsent(date, k -> new ArrayList<>()).add(activity);
    }

    public void removeActivity(LocalDate date, String activity) {
        List<String> dayList = activities.get(date);
        if (dayList != null) {
            dayList.remove(activity);
        }
    }

    public List<String> getActivities(LocalDate date) {
        return Collections.unmodifiableList(activities.getOrDefault(date, List.of()));
    }
}
