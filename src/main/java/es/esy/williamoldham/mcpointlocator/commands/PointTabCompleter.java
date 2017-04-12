package es.esy.williamoldham.mcpointlocator.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import es.esy.williamoldham.mcpointlocator.MCPointLocator;
import es.esy.williamoldham.mcpointlocator.Point;

public class PointTabCompleter implements TabCompleter {

	MCPointLocator main = MCPointLocator.getInstance();
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0){
		} else if(args.length == 1){
			
			List<Point> potPoints = new ArrayList<>();
			
			for(Point point : main.getPoints()){
				if(point.getName().startsWith(args[0])){
					
				}
			}
		} else {
			return null;
		}
		return null;
	}

}
