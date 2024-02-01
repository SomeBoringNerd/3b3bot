package xyz.someboringnerd.b3bot.util;

import java.time.Instant;
import java.time.temporal.ChronoField;

public class TimeUtil {
    public static long getNow() {
        return Instant.now().getLong(ChronoField.INSTANT_SECONDS);
    }
}
