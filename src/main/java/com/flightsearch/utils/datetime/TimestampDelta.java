package com.flightsearch.utils.datetime;

import java.sql.Timestamp;

public class TimestampDelta {
    private final long millis1;
    private final long millis2;
    private final long deltaMillis;

    public TimestampDelta(Timestamp t1, Timestamp t2) {
        millis1 = t1.getTime();
        millis2 = t2.getTime();
        deltaMillis = Math.abs(millis2 - millis1);
    }

    public TimestampDelta(Timestamp t) {
        millis1 = System.currentTimeMillis();
        millis2 = t.getTime();
        deltaMillis = Math.abs(millis2 - millis1);
    }

    public long millis() {
        return deltaMillis;
    }

    public long seconds() {
        return deltaMillis / 1_000;
    }

    public long minutes() {
        return deltaMillis / 60_000;
    }

    public long hours() {
        return deltaMillis / 3600_000;
    }

    public long days() {
        return deltaMillis / 86_400_000;
    }
}
