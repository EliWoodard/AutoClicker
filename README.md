# AutoClicker

This is a simple auto-clicker program that allows users to simulate mouse clicks. It features a graphical user interface (GUI) made with Swing, and uses the `Robot` class to simulate mouse clicks.

## Installation and Usage

To use the program, simply download the `Main.java` file and compile it using any Java compiler. Then run the compiled program to launch the GUI.

## Features

The program allows users to select the trigger button (left or right), click type (left or right), and number of clicks to perform. Once the user starts the auto-clicker, it will click the selected trigger button the specified number of times. The program also includes a stop button that allows the user to stop the auto-clicker at any time.

## Known Issues

The program is currently not functioning as intended. The mouse listener that is meant to detect mouse clicks and set the `clicking` variable to `true` is not working properly. Additionally, the program does not work on some games and websites that have built-in anti-cheat measures.

## License

This program is licensed under the MIT License. See the LICENSE file for more information.
