package org.lidar.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author godbrigero
 * @purpose to log lidar data MAINLY but can be used for other devices
 */
public class ReplayLogger {

    final double[][] deviceData;
    final String fileName;
    final ReplayLoggerDevice[] devices;
    final int ticksPerUpdate;
    final FileOutputStream writer;

    LoggerThread loggerThread = null;

    int currentTicks = 0;
    boolean state;

    /**
     * @param devices the interface of devices that you want to use
     * @param fileName the name of the file to write to
     * @param ticksPerUpdate the ticks between writes to file (1 tick = 25ms)
     * @throws IOException if no file is found with the name
     */
    public ReplayLogger(
            ReplayLoggerDevice[] devices,
            String fileName,
            int ticksPerUpdate
    ) throws IOException {
        this.deviceData = new double[devices.length][];
        this.fileName = fileName;
        this.devices = devices;

        this.ticksPerUpdate = ticksPerUpdate;

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        this.writer = new FileOutputStream(file);
    }

    /**
     * @param state so u can start / stop logger (boolean)
     */
    public void startStopLogger(boolean state) {
        this.state = state;

        if (this.state) {
            if (loggerThread == null) {
                loggerThread = new LoggerThread((LoggerThread.LoggerFunction) this::tick25Ms, 100);
            }
        }
    }

    /**
     * @description tick each 25ms also does all the writing
     */
    public void tick25Ms() {
        if (!state) return;

        if (currentTicks >= ticksPerUpdate) {
            currentTicks = 0;
        } else {
            currentTicks++;
            return;
        }

        for (int i = 0; i < devices.length; i++) {
            double[] dataInit = devices[i].getData();
            if (dataInit != null) {
                deviceData[i] = new double[dataInit.length];
                System.arraycopy(dataInit, 0, deviceData[i], 0, dataInit.length);

                continue;
            }

            List<Double> dataInitList = devices[i].getData(false);
            if (dataInitList != null) {
                deviceData[i] = new double[dataInitList.size()];
                for (int j = 0; j < dataInitList.size(); j++) {
                    deviceData[i][j] = dataInitList.get(j);
                }
            }
        }

        try {
            for (double[] i : deviceData) {
                byte[] bytes = new byte[i.length];
                for (int j = 0; j < i.length; j++) {
                    bytes[j] = (byte) i[j];
                }

                writer.write(bytes);
                writer.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
