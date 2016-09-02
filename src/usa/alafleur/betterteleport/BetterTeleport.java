package usa.alafleur.betterteleport;


import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Better teleport command for Minecraft, powered by Spigot API.
 *
 * @author Andre LaFleur
 */
public class BetterTeleport extends JavaPlugin {

    @Override
    public void onEnable() {
        // TODO: Load up database information

        Logger.getLogger("Minecraft").info("[BetterTeleport] Enabling BetterTeleport");
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins

        Logger.getLogger("Minecraft").info("[BetterTeleport] Disabling BetterTeleport");
    }

    public static void logInfo(String message){
        Logger.getLogger("Minecraft").info("[BetterTeleport] " + message);
    }
}
