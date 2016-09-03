package usa.alafleur.betterteleport;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The location command management stuff.
 *
 * /loc
 *     - add {x y z} alias description ---> uses coordinates of player if integers are not provided
 *     - remove alias
 *     - list
 */
public class LocationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        // Check and make sure all of the args are valid
        if(!verifyArgs(args))
            return false;

        String subCommand = args[0];

        if(subCommand.equalsIgnoreCase("add")){
            String response = add(commandSender, args);

            if(response != null)
                commandSender.sendMessage(ChatColor.RED + response); // NOTE: I think this is how you change the color in the chat
        } else if(subCommand.equalsIgnoreCase("remove")){
            String response = remove(commandSender, args);

            if(response != null)
                commandSender.sendMessage(ChatColor.RED + response);
        } else
            list(commandSender, args);

        return true;
    }

    /**
     * Helper method. Returns whether or not the args provided are valid.
     *
     * @param args - arguments provided for the command
     * @return true if the args are valid.
     */
    private boolean verifyArgs(String[] args){
        if(args.length == 0)
            return false;

        if(args[0].equalsIgnoreCase("add")){
            if(hasDescription(args)){
                // Check and make sure we have the minimum number of args
                int startIndex = 0;

                while(!args[startIndex].startsWith("\""))
                    startIndex++;

                if(startIndex + 1 < 3)
                    return false;

                // Check and see if we have exactly three integers, or no integers
                int intCount = 0;
                for(int i = 1; i < args.length; i++){
                    try {
                        Integer.parseInt(args[i]);
                        intCount++;
                    } catch(NumberFormatException e){
                        if(!(intCount == 0 || intCount == 3))
                            return false;
                    }
                }

            } else {
                if(args.length < 2)
                    return false;
            }
        } else if(args[0].equalsIgnoreCase("remove")){
            if(args.length != 2)
                return false;

        } else if(args[0].equalsIgnoreCase("list")){
            if(args.length != 1)
                return false;
        } else
            return false;

        return true;
    }

    private boolean hasDescription(String[] args){
        int endIndex = args.length - 1;

        while(endIndex >= 0 && !args[endIndex].endsWith("\""))
            endIndex--;

        int startIndex = 0;

        while(startIndex < endIndex && !args[startIndex].startsWith("\""))
            startIndex++;

        return startIndex < endIndex;
    }

    private String getDescription(String[] args){
        int endIndex = args.length - 1;

        while(endIndex >= 0 && !args[endIndex].endsWith("\""))
            endIndex--;

        int startIndex = 0;

        while(startIndex < endIndex && !args[startIndex].startsWith("\""))
            startIndex++;

        StringBuilder builder = new StringBuilder();

        for(; startIndex <= endIndex; startIndex++)
            builder.append(args[startIndex] + " ");

        String description = builder.toString().replaceAll("\"", "").trim();
        return description;
    }

    private String getAlias(String[] args){
        for(int i = 1; i < args.length; i++){
            try {
                Integer.parseInt(args[i]);
            } catch(NumberFormatException e){
                if(args[i].startsWith("\""))
                    return null;

                return args[i];
            }
        }

        return null;
    }

    /**
     * Adds the new location to the set. Returns null if successful or a String if there was an error of some sort.
     *
     * TODO: Test this
     *
     * @param args - The arguments from the command
     * @return
     */
    public String add(CommandSender sender, String[] args) {
        // Let's get the separate pieces together
        String description = null;
        if(hasDescription(args))
            description = getDescription(args);

        String alias = getAlias(args);

        String addedBy = "server";

        int x, y, z;
        ArrayList<String> argsList = new ArrayList<>();
        Collections.addAll(argsList, args);

        // If we have a player and no integers, then we need to get them out here
        if(sender instanceof Player && argsList.indexOf(alias) == 1){
            Player p = (Player) sender;
            Location l = p.getLocation();

            x = l.getBlockX();
            y = l.getBlockY();
            z = l.getBlockZ();

            addedBy = p.getPlayerListName();
        } else {
            try {
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
            } catch (NumberFormatException e){
                return "A proper x, y, and z coordinate must be provided!";
            }
        }

        DBInterface.addLocation(alias, x, y, z, addedBy, description);

        return null;
    }

    /**
     * TODO: This
     * @return
     */
    public String remove(CommandSender sender, String[] args){
        return null;
    }

    /**
     * TODO: This
     * @return
     */
    public String list(CommandSender sender, String[] args){
        return null;
    }
}
