package org.lidar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Specify the GPIO pin number
        int pinNumber = 2; // Replace with your desired GPIO pin number
        int pinNumberRec = 4;

        // Export the pin
        exportPin(pinNumber);

        // Set the direction of the pin (in this case, as an output)
        setDirection(pinNumber, "out");

        // Control the state of the pin (turn it on and off)
        controlPin(pinNumber, 1); // 1 for HIGH, 0 for LOW

        while (true) {
            int pinValue = readPin(pinNumberRec);
            System.out.println("Pin " + pinNumber + " state: " + pinValue);

            // Sleep for a short duration before reading again
            try {
                Thread.sleep(1000); // Adjust as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            controlPin(pinNumber, Math.random() > 0.5 ? 1 : 0);
        }
    }

    private static void exportPin(int pinNumber) {
        try (FileWriter writer = new FileWriter("/sys/class/gpio/export")) {
            writer.write(Integer.toString(pinNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setDirection(int pinNumber, String direction) {
        try (FileWriter writer = new FileWriter("/sys/class/gpio/gpio" + pinNumber + "/direction")) {
            writer.write(direction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void controlPin(int pinNumber, int value) {
        try (FileWriter writer = new FileWriter("/sys/class/gpio/gpio" + pinNumber + "/value")) {
            writer.write(Integer.toString(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unexportPin(int pinNumber) {
        try (FileWriter writer = new FileWriter("/sys/class/gpio/unexport")) {
            writer.write(Integer.toString(pinNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int readPin(int pinNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/sys/class/gpio/gpio" + pinNumber + "/value"))) {
            String valueStr = reader.readLine();
            return Integer.parseInt(valueStr.trim());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1; // Return an error value
        }
    }
}
