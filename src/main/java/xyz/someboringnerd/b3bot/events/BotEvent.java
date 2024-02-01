package xyz.someboringnerd.b3bot.events;

import meteordevelopment.orbit.ICancellable;

public class BotEvent implements ICancellable {
    @Override
    public void setCancelled(boolean cancelled) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
