package xyz.someboringnerd.b3bot.systems.tracker;

import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import xyz.someboringnerd.b3bot.util.TimeUtil;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

@RequiredArgsConstructor
public class Session {

    @Getter(AccessLevel.PUBLIC)
    private final long start = TimeUtil.getNow();

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private long lastSeen = TimeUtil.getNow();

    @Getter(AccessLevel.PUBLIC)
    private final String player;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int death = 0, kill = 0, sentMessage = 0, averagePing = 0;


    private int lastTick = 0, tickedAmount = 1, totalPing = 0;

    public void update() {
        if(MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player) != null) {
            lastSeen = TimeUtil.getNow();
            if(lastTick >= 20) {
                if(MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player).getLatency() != 0) {
                    totalPing += MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player).getLatency();
                    averagePing = (int) totalPing / tickedAmount;
                    lastTick = 0;
                    tickedAmount++;
                }
            }
            lastTick++;
        }
    }

    public void messageSent() {
        sentMessage++;
    }
}
