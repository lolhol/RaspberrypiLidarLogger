package org.lidar;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GpioController gpio = GpioFactory.getInstance();

        GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,             // PIN NUMBER
                "MyButton",                   // PIN FRIENDLY NAME (optional)
                PinPullResistance.PULL_DOWN); // PIN RESISTANCE (optional)

        GpioPinDigitalOutput myLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,   // PIN NUMBER
                "My LED",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)

        myLed.pulse(500);

        while (true) {
            boolean buttonPressed = myButton.isHigh();
            Thread.sleep(250);
        }
    }
}
