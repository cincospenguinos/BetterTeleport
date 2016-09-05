package usa.alafleur.betterteleport;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * The teleport, or "tele" command.
 */
public class TeleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        for(int i = 0; i < args.length; i++){
            BetterTeleport.log("Arg " + i + ": " + args[i], Level.INFO);
        }

        if(!verifyArgs(commandSender, args))
            return false;

        Player sender = (Player) commandSender;
        teleport(sender, args);

        return true;
    }

    private boolean verifyArgs(CommandSender sender, String[] args){
        if(!(sender instanceof Player))
            return false;

        if(args.length != 1 && args.length != 3)
            return false;

        if(args.length == 4){
            try {
                Integer.parseInt(args[0]);
                Integer.parseInt(args[1]);
                Integer.parseInt(args[2]);
            } catch (NumberFormatException e){
                return false;
            }
        }

        return true;
    }

    public void teleport(Player player, String[] args){
        int x = 0;
        int y = 0;
        int z = 0;

        if(args.length == 1) {
            // Either going to a player or an alias - check alias first, then player
            String targetName = args[0];
            int[] locs = DBInterface.getCoordinatesFromAlias(targetName);

            if(locs != null){
                x = locs[0];
                y = locs[1];
                z = locs[2];
            } else {
                boolean locationFound = false;

                for(Player p : player.getServer().getOnlinePlayers()){
                    if(p.getPlayerListName() == targetName){
                        x = p.getLocation().getBlockX();
                        y = p.getLocation().getBlockY();
                        z = p.getLocation().getBlockZ();
                        locationFound = true;
                        break;
                    }
                }

                if(!locationFound){
                    player.sendMessage(ChatColor.RED + "There is no alias or player matching \"" + targetName + "\"");
                    return;
                }
            }
        } else {
            // Going to a direct location
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
        }

        Location target = new Location(player.getWorld(), (double) x, (double) y, (double) z);
        if(!player.teleport(target)){
            player.sendMessage(ChatColor.RED + "An error occurred attempting to teleport. Check with your server administrator.");
        }
    }
}
