package org.lidar;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int pin = 0;

        // Provision the pin as an output pin
        GpioUtil.export(pin, GpioUtil.DIRECTION_OUT);
        Gpio.pinMode(pin, Gpio.OUTPUT);

        // Send data to the pin (e.g., turn the LED on)
        Gpio.digitalWrite(pin, Gpio.HIGH);

        // Wait for 5 seconds
        Thread.sleep(5000);

        // Send data to the pin (e.g., turn the LED off)
        Gpio.digitalWrite(pin, Gpio.LOW);

        // Unexport the pin to release resources
        GpioUtil.unexport(pin);

        /*while (true) {
        }*/
    }
}
