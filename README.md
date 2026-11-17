# 📅 Calendar

A calendar desktop app built with Java and Swing that shows you the current week and allows you to add and remove activities.

## 🧩 Classes
* `MainController` acts as the middle man between MainFrame and Calendar, adding and removing actitivies both internally and externally
* `Calendar` stores activities in a map, has methods that other classes can use to add, remove, and get activities
* `MainFrame` handles the UI part of the app, creates window and components that make up the visuals, also gives interactive components like buttons functionality
* `Main` starts the program

## ✅ Requirement Completion Presentation
* `G-F1` program opens swing window by instancing a `JFrame` object in `MainFrame` constructor, and shows week number along with monday to sunday using `createDayHeader`
* `G-F2` each day shows name, date, month, using java.time using method `createDayHeader` in `MainFrame`
* `G-F3` current day is marked in blue because of method `datePanel.setBackground` in if statement `if (date.equals(today))` in method `createDayHeader` in `MainFrame`
* `G-F4` user can add activity and it displays on correct day because we pass in date inside actionListener inside `createColumnMethod` in `MainFrame`
* `G-F5` one day can have several activities thanks to using BoxLayout
* `G-F6` user can remove an activity through clicking on remove icon on cards which runs `handleRemoveActivity` in `MainController`
* `G-F7` the program doesn't crash when user enters blank or unexpected because our safety check in method `handleAddActivity` in `MainController`
* `G-K1` project has a `Main` class and a `main` method, and the purpose for it to start the app
* `G-K2` activities storer `Calendar` does not import swing, and the code in the project is split up in at least 3 classes which are `MainFrame` `Calendar` `MainController`
* `G-K3` all instance varibles are private
* `G-K4` activities are stored in a map inside `Calendar`
* `G-K5` one method that i created on my own that is not a getter or setter is `refreshDay` inside `MainFrame`
* `G-K6` we use actionListener with labmda in methods `addActivityCard` and `createDayColumn` in `MainFrame`
* `G-K7` everything is named correctly, classes in PascalCase, methods and variables in camelCase
* `G-K8` comments exist for every class and public method
* `G-K9` runs without issues
* `G-R1` i show how to start program and required jdk version under these sections in readme: `Prerequisites` `Getting Started`
* `G-R2` description of classes is in readme file under this section: `Classes`
* `G-R3` this section of the readme you're currently reading is the requirement completion presentation

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