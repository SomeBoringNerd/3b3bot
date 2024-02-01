package xyz.someboringnerd.b3bot.events;

import lombok.AccessLevel;
import lombok.Getter;

public class PlayerLeaveServerEvent extends BotEvent {

    @Getter(AccessLevel.PUBLIC)
    private String thePlayer;

    private static final PlayerLeaveServerEvent event = new PlayerLeaveServerEvent();

    public static PlayerLeaveServerEvent get(String player) {
        event.thePlayer = player;
        return event;
    }

}