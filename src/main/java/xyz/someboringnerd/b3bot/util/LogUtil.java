package xyz.someboringnerd.b3bot.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogUtil {

    public static void print(String message, Object... parser) {
        log.info(String.format(message, parser));
    }

}
