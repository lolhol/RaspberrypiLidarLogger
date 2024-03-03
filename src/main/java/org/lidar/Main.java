package org.lidar;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;

public class Main {
    public static void main(String[] args) {
        final GpioController gpio = GpioFactory.getInstance();

        // Provision GPIO pin #17 as an output pin and turn it off
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, PinState.LOW);

        // Add a listener to detect state changes on the pin
        pin.addListener((GpioPinListenerDigital) event -> {
            System.out.println("Pin state changed: " + event.getState());
        });

        // Toggle the pin state every second
        try {
            while (true) {
                pin.toggle();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
