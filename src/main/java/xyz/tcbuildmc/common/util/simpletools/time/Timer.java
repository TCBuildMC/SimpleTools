package xyz.tcbuildmc.common.util.simpletools.time;

import org.jetbrains.annotations.Contract;

public class Timer {
    private final long timeout;
    private long startTime = 0;
    private long endTime = 0;

    @Contract(pure = true)
    public Timer() {
        this.timeout = -1;
    }

    public Timer(long timeout) {
        this.timeout = timeout;
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void end() {
        this.endTime = System.nanoTime();
    }

    public long getTimeExecuted() {
        return this.endTime - this.startTime;
    }

    public boolean isTimeout() {
        return this.endTime - this.startTime >= this.timeout;
    }
}
