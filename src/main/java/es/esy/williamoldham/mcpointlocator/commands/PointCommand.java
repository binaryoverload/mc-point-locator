package es.esy.williamoldham.mcpointlocator.commands;

import java.util.List;

import es.esy.williamoldham.mcpointlocator.MCPointLocator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.esy.williamoldham.mcpointlocator.Point;

import static es.esy.williamoldham.mcpointlocator.MCPointLocator.color;

public class PointCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		MCPointLocator main = MCPointLocator.getInstance();

		List<Point> points = main.getPoints();
		
		if(!sender.isOp()){
			return true;
		}
		
		if(args.length == 0){
			sender.sendMessage(color("&6Points:"));
			for(Point point : points){
				String x = String.valueOf(point.getX());
				String z = String.valueOf(point.getZ());
				String name = point.getName();
				sender.sendMessage(color("&e" + name + " &6-&e &6X:&e" + x + " &6Z:&e" + z));
			}
		} else if(args.length == 1){
			String point = args[0];
			
			if(!(sender instanceof Player)){
				sender.sendMessage(color("&cwrong Command Usage!"));
				sender.sendMessage(color("&4Usage:"));
				sender.sendMessage(color("&c/point <name> &2*Players only*"));
				sender.sendMessage(color("&c/point"));
			}
			
			Player player = (Player) sender;
			
			if(main.pointExists(point)){
				Point p = main.getPoint(point);
				main.addPlayerPoint(player, p);
				player.sendMessage(color("&aPoint &2" + point + "&a was selected!"));
				return true;
			} else {
				player.sendMessage(color("&cPoint &4" + point + "&c does not exist!"));
			}
			
			
			
		}
		
		return true;
	}


}
