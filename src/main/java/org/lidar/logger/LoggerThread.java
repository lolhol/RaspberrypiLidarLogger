package org.lidar.logger;

public class LoggerThread extends Thread {
    public boolean isRunning = true;
    private final LoggerFunction function;
    private final long sleepTimeMs;

    public LoggerThread(LoggerFunction func, long sleepTimeMs) {
        this.function = func;
        this.sleepTimeMs = sleepTimeMs;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            function.tick();
        }
    }

    public interface LoggerFunction {
        void tick();
    }
}
