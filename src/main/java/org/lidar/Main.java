package org.lidar;

import com.pi4j.io.gpio.*;

public class Main {
    public static void main(String[] args) {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput senderPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);
        GpioPinDigitalInput receiverPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);
        senderPin.high();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        senderPin.low();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Received data: " + (receiverPin.isHigh() ? "1" : "0"));
    }
}
