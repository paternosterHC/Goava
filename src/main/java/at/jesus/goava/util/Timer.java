package at.jesus.goava.util;

import java.util.concurrent.TimeUnit;

public class Timer {
    private long lastMS;

    private long getMS() {
        return System.currentTimeMillis();
    }

    public void start() {
        lastMS = getMS();
    }

    public long getDifference() {
        return getMS() - lastMS;
    }

    public String format() {
        return FormatUtil.msToString(getDifference());
    }
}
