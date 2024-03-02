package org.lidar;

import com.pi4j.io.gpio.*;

public class Main {
    public static void main(String[] args) {
        final GpioController gpio = GpioFactory.getInstance();
        GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.DEFAULT_PIN_NUMBERING));
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
        pin.high();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pin.low();
        gpio.shutdown();
    }
}
