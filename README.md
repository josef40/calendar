# 📅 Calendar

A calendar desktop app built with Java and Swing that shows you the current week and allows you to add and remove activities.

## 🧩 Classes
* `MainController` acts as the middle man between MainFrame and Calendar, adding and removing actitivies
* `Calendar` stores activities in a map, has methods that MainController uses to add, remove, and get activities
* `MainFrame` handles the UI part of the app, creates window and components that make up the visuals, also gives interactive components like buttons functionality
* `Main` starts the program

## ✅ Requirement Completion Presentation
| requirement | class | method / where |
|---|---|---|
| G-F1 | `MainFrame` | in constructor, JFrame is created to open window, also in there week header and monday to sunday is generated using `createWeekHeader()` and `createDayHeader()`, window is set to visible using `show()` method |
| G-F2 | `MainFrame` | for each day column `createDayHeader()` is ran, in this method LocalTime in passed in order to obtain day name, day number, and month name, then `JLabel` is used to display this information |
| G-F3 | `MainFrame` | current day is marked clearly by setting the background of `dayInfoHolder` to cyan using the `setBackground()` method that belongs to the `JPanel` class |
| G-F4 | `MainFrame`, `MainController`, `Calendar` | in `createActivityColumn()` in `MainFrame` an add button is added which uses `JOptionPane.showInputDialog()` which gives user an input dialog, after user enters text, `handleAddActivity()` is called inside `MainController` which validates the text, cleans the text, stores the activity using `Calendar` class, and gives `MainFrame` back the updated activity list for that date, `MainFrame` receieves it and updates its ui |
| G-F5 | `MainFrame` | one day can have several activities because activities are added to a `JPanel` called `scrollPanel` in `createActivityColumn` that uses `BoxLayout` |
| G-F6 | `MainFrame`, `MainController`, `Calendar` | when an activity is created using `addActivity()` in `MainFrame`, a remove button is added using `JButton`, when this is pressed, `handleRemoveActivity()` in `MainController` is called, and the activity is removed from `Calendar` class which stores it, and then `MainController` calls `refreshDay()` in `MainFrame` to update the ui, `refreshDay` removes all activities in the column and adds back with updated activity list, then re-calculates layout and repaints |
| G-F7 | `MainController` | the program does not crash when user leaves input empty or enters something unexpected because the input gets validated in `handleAddActivity()` |
| G-K1 | `Main` | `main` only starts the program, no more than 10 rows of code |
| G-K2 | `Main`, `Calendar`, `MainController`, `MainFrame` | `Calendar` (used for storing activities) does not import `javax.swing` |
| G-K3 | all classes | all instance variables are `private` |
| G-K4 | `Calendar` | activities are stored using `HashMap<LocalDate, List<String>>` |
| G-K5 | `MainFrame` | `createDayHeader(LocalDate date, LocalDate today)` takes parameters and returns a `JPanel` |
| G-K6 | `MainFrame` | lambda `ActionListener` in `createDayColumn` (add button) and `addActivityCard` (remove button) |
| G-K7 | all classes | classes in PascalCase, methods and variables in camelCase |
| G-K8 | all classes | every class and public method has a javadoc comment |
| G-K9 | whole project | compiles and runs with JDK 25 |
| G-R1 | `README.md` | sections "Prerequisites" and "Getting Started" |
| G-R2 | `README.md` | section "Classes" |
| G-R3 | `README.md` | this table |

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