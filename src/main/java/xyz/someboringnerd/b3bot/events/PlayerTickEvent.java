package xyz.someboringnerd.b3bot.events;

public class PlayerTickEvent extends BotEvent {

    private static final PlayerTickEvent event = new PlayerTickEvent();

    public static PlayerTickEvent get() {
        return event;
    }

}
