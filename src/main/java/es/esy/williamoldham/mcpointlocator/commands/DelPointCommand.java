package es.esy.williamoldham.mcpointlocator.commands;

import es.esy.williamoldham.binarycore.util.StringUtils;
import es.esy.williamoldham.mcpointlocator.MCPointLocator;
import es.esy.williamoldham.mcpointlocator.Point;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class DelPointCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		MCPointLocator main = MCPointLocator.getInstance();
		
		if(args.length == 1){
			
			String potentialPoint = args[0];
			
			if(main.pointExists(potentialPoint)){
				
				Point point = main.getPoint(potentialPoint);
				File file = new File(main.getDataFolder(), point.getName() + ".json" );
				
				if(file.exists()){
					file.delete();
				}
				
				main.removePoint(point);
				
				sender.sendMessage(StringUtils.colour("&aPoint &2" + potentialPoint + "&a was deleted!"));
				
			} else {
				
				sender.sendMessage(StringUtils.colour("&cPoint &4" + potentialPoint + "&c does not exist!"));
				
			}
			
			
			return true;
		} else {
			sender.sendMessage(StringUtils.colour("&4Usage:"));
			sender.sendMessage(StringUtils.colour("&c/delpoint <name>"));
			return true;
		}
	}

	
	
}
