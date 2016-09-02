package usa.alafleur.betterteleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

/**
 * The location command management stuff.
 *
 * TODO: This
 */
public class LocationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        // TODO: First extract the description from the command
        BetterTeleport.log(s, Level.INFO);
        StringBuilder builder = new StringBuilder();
        boolean hasDescription = false;

        for(int i = 0; i < args.length; i++){
            if(args[i].startsWith("\"") || hasDescription){
                hasDescription = true;
                builder.append(args[i]);
            }
        }

        // TODO: Verify the remaining arguments
        if(!verifyArgs(args))
            return false;

        // TODO: Execute the command with the DBInterface

        return true;
    }

    private boolean verifyArgs(String[] args){
        // TODO: This
        if(args.length < 2)
            return false;

        switch(args.length){
            case 0:
            case 1:
                return false;
            case 2:
                break;
            case 4:
            case 5:
                // TODO: Command provides x, y, and z coordinates
                break;
        }

        return true;
    }
}
