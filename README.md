# 📅 Calendar

A calendar desktop app built with Java and Swing that shows you the current week and allows you to add and remove activities.

## 🧩 Classes
* `MainController` acts as the middle man betien MainFrame and Calendar, adding and removing actitivies both internally and externally
* `Calendar` stores activities in a map, has methods that other classes can use to add, remove, and get activities
* `MainFrame` handles the UI part of the app, creates window and components that make up the visuals, also gives interactive components like buttons functionality
* `Main` starts the program

## ✅ Requirement Completion Presentation
| Requirement | Class | Method / Where |
|---|---|---|
| G-F1 | `MainFrame` | Constructor `MainFrame()` creates the `JFrame` and adds Monday–Sunday; `createWeekHeader` shows the week number |
| G-F2 | `MainFrame` | `createDayHeader` shows weekday, date and month using `LocalDate`; the week's dates are calculated in the constructor with `TemporalAdjusters.previousOrSame` |
| G-F3 | `MainFrame` | `createDayHeader`, `if (date.equals(today))` sets a blue background |
| G-F4 | `MainFrame`, `MainController`, `Calendar` | `createDayColumn` (button listener passes the date), `handleAddActivity`, `Calendar.addActivity`, then `refreshDay` shows it on the right day |
| G-F5 | `Calendar`, `MainFrame` | `addActivity` stores a `List` per date; `refreshDay` and `addActivityCard` show every activity in the day column |
| G-F6 | `MainFrame`, `MainController`, `Calendar` | `addActivityCard` (remove button), `handleRemoveActivity`, `Calendar.removeActivity` |
| G-F7 | `MainController` | `handleAddActivity` returns early if input is `null` (cancel) or blank |
| G-K1 | `Main` | `main` only starts the program |
| G-K2 | `Main`, `Calendar`, `MainController`, `MainFrame` | `Calendar` (model) does not import `javax.swing` |
| G-K3 | All classes | All instance variables are `private` |
| G-K4 | `Calendar` | Field `activities`, a `HashMap<LocalDate, List<String>>` |
| G-K5 | `MainFrame` | `createDayHeader(LocalDate date, LocalDate today)` takes parameters and returns a `JPanel` |
| G-K6 | `MainFrame` | Lambda `ActionListener`s in `createDayColumn` (add button) and `addActivityCard` (remove button) |
| G-K7 | All classes | Classes in PascalCase, methods and variables in camelCase |
| G-K8 | All classes | Every class and public method has a Javadoc comment |
| G-K9 | Whole project | Compiles and runs with JDK 25 |
| G-R1 | `README.md` | Sections "Prerequisites" and "Getting Started" |
| G-R2 | `README.md` | Section "Classes" |
| G-R3 | `README.md` | This table |

## 🔧 Prerequisites
* `JDK 25+`

## 📐 Project Structure
```
calendar/
└── src/
    ├── controller/
    │   └── MainController.java
    ├── model/
    │   └── Calendar.java
    ├── ui/
    │   └── MainFrame.java
    └── Main.java
```

## 🚀 Getting Started
Run `Main.java`