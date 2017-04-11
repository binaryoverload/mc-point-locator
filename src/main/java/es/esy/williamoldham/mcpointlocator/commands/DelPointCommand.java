package es.esy.williamoldham.mcpointlocator.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import es.esy.williamoldham.mcpointlocator.Main;
import es.esy.williamoldham.mcpointlocator.Point;

import static es.esy.williamoldham.mcpointlocator.Main.color;

public class DelPointCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		Main main = Main.getInstance();
		
		if(args.length == 1){
			
			String potentialPoint = args[0];
			
			if(main.pointExists(potentialPoint)){
				
				Point point = main.getPoint(potentialPoint);
				File file = new File(main.getDataFolder(), point.getName() + ".json" );
				
				if(file.exists()){
					file.delete();
				}
				
				main.removePoint(point);
				
				sender.sendMessage(color("&aPoint &2" + potentialPoint + "&a was deleted!"));
				
			} else {
				
				sender.sendMessage(color("&cPoint &4" + potentialPoint + "&c does not exist!"));
				
			}
			
			
			return true;
		} else {
			sender.sendMessage(color("&4Usage:"));
			sender.sendMessage(color("&c/delpoint <name>"));
			return true;
		}
	}

	
	
}
