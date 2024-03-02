package org.lidar;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

public class Main {
    public static void main(String[] args) {
        GpioController gpio = GpioFactory.getInstance();

        // Replace with the GPIO pins you want to use
        Gpio.pinMode(2, Gpio.INPUT);
        Gpio.pinMode(3, Gpio.OUTPUT);

        Gpio.analogWrite(3, 1);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Gpio.digitalRead(2) + "!!!!!");

        gpio.shutdown();
    }

    private static void sendDigitalData(GpioPinDigitalOutput pin, int[] data) {
        for (int bit : data) {
            pin.setState(bit == 1);
            try {
                Thread.sleep(100); // Adjust the sleep time based on your requirements
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
