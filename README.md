# Team Generator

A Desktop application that dynamically sorts players into 5 regional teams with animated cards, customizable team names, and interactive visual states.

## 🛠 Prerequisites

Before running the project, ensure you have the following installed:
1. **JDK 21**

## ⚙️ Initial Setup & Configuration

This application relies on local assets that are deliberately ignored by Git. You must create them manually before running the app.

### 1. Create the Pictures Folder
Place all player images (as `.png` files) into the `pictures` folder in the project root.

### 2. Create the Configuration File
Modify `players.json` in the root directory of the project.
This file defines the players, their assigned regions (1-5), and their image filenames.

**Required JSON Format:**
```json
[
  {
    "name": "John Doe",
    "nickname": "The Crusher",
    "region": 1,
    "imageFileName": "player1.png"
  },
  {
    "name": "Jane Smith",
    "nickname": "Valkyrie",
    "region": 2,
    "imageFileName": "player2.png"
  }
]
```

### 3. Run the project
Run the application from CLI using the following commands:
./gradlew build -- Builds the project
./gradlew run -- Runs the application

## About the application
- In order to support full screen mode, the method of closing the application is using the `ALT+F4` shortcut as of now.
- On startup `players.json` gets parsed.
- Players show up and get put into their respective `Disctricts`. This flow can be controlled by mouse clicks.
  - This happens in the order players are defined in the JSON file.
- After all players are in their places, their names become clickable.
  - On click, their player card shows up with a death animation, indicating their demise.
- After all players are eliminated the application can only be closed with `ALT+F4` still.