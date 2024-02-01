package xyz.someboringnerd.b3bot.managers;

import lombok.AccessLevel;
import lombok.Getter;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import xyz.someboringnerd.b3bot.ClientInit;
import xyz.someboringnerd.b3bot.events.PlayerJoinServerEvent;
import xyz.someboringnerd.b3bot.events.PlayerLeaveServerEvent;
import xyz.someboringnerd.b3bot.events.PlayerTickEvent;
import xyz.someboringnerd.b3bot.systems.tracker.Session;
import xyz.someboringnerd.b3bot.util.ChatUtil;
import xyz.someboringnerd.b3bot.util.DataBaseUtil;
import xyz.someboringnerd.b3bot.util.LogUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SessionManager {

    private final Set<Session> trackedSessions = new HashSet<>();

    @Getter(AccessLevel.PUBLIC)
    private static SessionManager instance;

    public SessionManager() {
        instance = this;

        ClientInit.EVENT_BUS.subscribe(this);
    }

    public int getTrackedSessionNumber() {
        return trackedSessions.size();
    }

    @EventHandler
    private void onPlayerJoinServer(PlayerJoinServerEvent event) {

        AtomicBoolean found = new AtomicBoolean(false);

        trackedSessions.forEach(session -> {
            if(session.getPlayer().equalsIgnoreCase(event.getThePlayer())) {
                found.set(true);
                LogUtil.print("This player is already being tracked, therefore we dont need to create a new session");
            }
        });

        if(!found.get()) {
            trackedSessions.add(new Session(event.getThePlayer()));
            LogUtil.print("Started tracking player %s", event.getThePlayer());
        }
    }

    @EventHandler
    private void onPlayerLeaveServer(PlayerLeaveServerEvent event) {
        AtomicReference<Session> sessionMarkedForDeletion = new AtomicReference<>();
        trackedSessions.forEach(session -> {
            if(session.getPlayer().equalsIgnoreCase(event.getThePlayer())) {
                DataBaseUtil.saveSession(session);
                sessionMarkedForDeletion.set(session);
            }
        });
        trackedSessions.remove(sessionMarkedForDeletion.get());
    }

    @EventHandler
    private void onTick(PlayerTickEvent event) {
        trackedSessions.forEach(Session::update);
    }

    public Session getSessionByName(String sender) {
        AtomicReference<Session> wanted = new AtomicReference<>();
        trackedSessions.forEach(session -> {
            if(session.getPlayer().equalsIgnoreCase(sender)) {
                DataBaseUtil.saveSession(session);
                wanted.set(session);
            }
        });

        return wanted.get();
    }

    public void addAll() {

        AtomicInteger added = new AtomicInteger(0);
        AtomicInteger ignored = new AtomicInteger(0);

        MinecraftClient.getInstance().getNetworkHandler().getPlayerList().forEach(player -> {
            if(getSessionByName(player.getProfile().getName()) == null) {
                trackedSessions.add(new Session(player.getProfile().getName()));
                added.set(added.get()+1);
            } else {
                ignored.set(ignored.get()+1);
            }
        });

        ChatUtil.sendMessage("Added %s sessions to the tracker, ignored %s already tracked sessions", added.get(), ignored.get());
    }

    public void saveAll() {
        trackedSessions.forEach(DataBaseUtil::saveSession);
        MinecraftClient.getInstance().scheduleStop();
    }
}
