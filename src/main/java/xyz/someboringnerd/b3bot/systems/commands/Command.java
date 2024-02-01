package xyz.someboringnerd.b3bot.systems.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();
    PRIVACY privacy() default PRIVACY.PUBLIC;
}
