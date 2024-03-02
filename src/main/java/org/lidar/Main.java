package org.lidar;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GpioController gpio = GpioFactory.getInstance();

        // Define GPIO pins for transmission and reception
        Pin transmitPin = RaspiPin.GPIO_01; // Replace with your transmit pin
        Pin receivePin = RaspiPin.GPIO_02;  // Replace with your receive pin

        // Provision the pins as digital output and input
        GpioPinDigitalOutput transmitOutputPin = gpio.provisionDigitalOutputPin(transmitPin);
        GpioPinDigitalInput receiveInputPin = gpio.provisionDigitalInputPin(receivePin, PinPullResistance.PULL_DOWN);

        // Transmit data (e.g., HIGH for 1 second)
        transmitOutputPin.high();
        Thread.sleep(1000); // Adjust as needed
        transmitOutputPin.low();

        // Receive data
        boolean receivedData = receiveInputPin.isHigh();
        System.out.println("Received Data: " + (receivedData ? "HIGH" : "LOW"));

        // Release resources
        gpio.shutdown();
    }
}
