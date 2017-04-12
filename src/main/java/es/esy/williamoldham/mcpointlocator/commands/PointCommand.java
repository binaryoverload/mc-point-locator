package es.esy.williamoldham.mcpointlocator.commands;

import es.esy.williamoldham.binarycore.StringUtils;
import es.esy.williamoldham.mcpointlocator.MCPointLocator;
import es.esy.williamoldham.mcpointlocator.Point;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PointCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		MCPointLocator main = MCPointLocator.getInstance();

		List<Point> points = main.getPoints();

		if(!sender.isOp()){
			return true;
		}

		if(args.length == 0){
			sender.sendMessage(StringUtils.colour("&6Points:"));
			for(Point point : points){
				String x = String.valueOf(point.getX());
				String z = String.valueOf(point.getZ());
				String name = point.getName();
				sender.sendMessage(StringUtils.colour("&e" + name + " &6-&e &6X:&e" + x + " &6Z:&e" + z));
			}
		} else if(args.length == 1){
			String point = args[0];

			if(!(sender instanceof Player)){
				sender.sendMessage(StringUtils.colour("&cwrong Command Usage!"));
				sender.sendMessage(StringUtils.colour("&4Usage:"));
				sender.sendMessage(StringUtils.colour("&c/point <name> &2*Players only*"));
				sender.sendMessage(StringUtils.colour("&c/point"));
			}

			Player player = (Player) sender;

			if(main.pointExists(point)){
				Point p = main.getPoint(point);
				main.addPlayerPoint(player, p);
				player.sendMessage(StringUtils.colour("&aPoint &2" + point + "&a was selected!"));
				return true;
			} else {
				player.sendMessage(StringUtils.colour("&cPoint &4" + point + "&c does not exist!"));
			}
			
			
			
		}
		
		return true;
	}


}
