package xyz.someboringnerd.b3bot;

import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import xyz.someboringnerd.b3bot.managers.CommandManager;
import xyz.someboringnerd.b3bot.managers.DatabaseManager;
import xyz.someboringnerd.b3bot.managers.SessionManager;

import java.lang.invoke.MethodHandles;

public class ClientInit implements ClientModInitializer {

    public static final IEventBus EVENT_BUS = new EventBus();


    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {

        EVENT_BUS.registerLambdaFactory(this.getClass().getPackage().getName() , (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        EVENT_BUS.subscribe(this);

        DatabaseManager.init();
        new CommandManager();
        new SessionManager();

    }
}
