package xyz.someboringnerd.b3bot.events;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import meteordevelopment.orbit.ICancellable;

public class PlayerJoinServerEvent extends BotEvent {

    @Getter(AccessLevel.PUBLIC)
    private String thePlayer;

    private static final PlayerJoinServerEvent event = new PlayerJoinServerEvent();

    public static PlayerJoinServerEvent get(String player) {
        event.thePlayer = player;
        return event;
    }

}
