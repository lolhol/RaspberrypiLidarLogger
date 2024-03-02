package org.lidar;

import com.pi4j.io.gpio.*;

public class Main {
    public static void main(String[] args) {
        GpioController gpio = GpioFactory.getInstance();

        // Replace with the GPIO pins you want to use
        GpioPinDigitalOutput senderPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "SenderPin", PinState.LOW);
        GpioPinDigitalInput receiverPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "ReceiverPin",
                PinPullResistance.PULL_DOWN);

        int[] dataToSend = {1, 0, 1, 1, 0}; // Replace with your actual data

        sendDigitalData(senderPin, dataToSend);

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
