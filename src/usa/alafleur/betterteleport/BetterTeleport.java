package usa.alafleur.betterteleport;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Better teleport command for Minecraft, powered by Spigot API.
 *
 * @author Andre LaFleur
 */
public class BetterTeleport extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        setupConfig();

        // Setup the schema
        Exception e = DBInterface.openConnection(config.getString("dbusername"), config.getString("dbpassword"), config.getString("dburl"));
        if(e != null) {
            log("ERROR! Could not open connection with DB!", Level.SEVERE);
            e.printStackTrace();
            return;
        }

        DBInterface.setupSchema("minecraft");

        // Add the commands
        getCommand("loc").setExecutor(new LocationCommand());
        getCommand("tele").setExecutor(new TeleCommand());
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins

        Exception e;
        if((e = DBInterface.closeConnection()) != null){
            log("ERROR! Could not close DB connection!", Level.SEVERE);
            e.printStackTrace();
            return;
        }
    }

    public static void log(String message, Level type){
        Logger.getLogger("Minecraft").log(type, "[BetterTeleport] " + message);
    }

    private void setupConfig(){
        config = getConfig();
        config.addDefault("dbusername", "username");
        config.addDefault("dbpassword", "dbpassword");
        config.addDefault("dbengine", "mysql");
        config.addDefault("dbschema", "minecraft");
        config.addDefault("dburl", "some_url");
        config.options().copyDefaults(true);
        saveConfig();
    }
}
