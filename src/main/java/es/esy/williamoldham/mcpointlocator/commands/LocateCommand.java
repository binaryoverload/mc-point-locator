package es.esy.williamoldham.mcpointlocator.commands;

import es.esy.williamoldham.binarycore.util.StringUtils;
import es.esy.williamoldham.mcpointlocator.MCPointLocator;
import es.esy.williamoldham.mcpointlocator.Point;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(StringUtils.colour("&cThis command is for players only!"));
		}
		
		if(args.length != 2 && args.length != 3){
			sender.sendMessage(StringUtils.colour("&cWrong Command Usage!"));
			sender.sendMessage(StringUtils.colour("&4Usage:"));
			sender.sendMessage(StringUtils.colour("&c/locate <geLength> <geHeading> [height] &2*Players only*"));
			return true;
		}

		double geLength = 0;
		double geHeading = 0;
		double height = 0;

		try {
			geLength = Double.parseDouble(args[0]);
			geHeading = Double.parseDouble(args[1]);
			if(args.length == 3){
				height = Double.parseDouble(args[3]);
			}
		} catch (NumberFormatException e){
			if(args.length != 3) {
				sender.sendMessage(StringUtils.colour("&cBoth the length and the heading need to be valid numbers!"));
			} else {
				sender.sendMessage(StringUtils.colour("&cBoth the length, the heading and the height need to be valid numbers!"));
			}
		}

		Player player = (Player) sender;

		if(!player.isOp()){
			return true;
		}

		MCPointLocator main = MCPointLocator.getInstance();

		boolean hasPoint = main.hasSelectedPoint(player);

		if(hasPoint){
			Point point = main.getSelectedPoint(player);

			Point newPoint = main.geToMC(geHeading, geLength, point);

			Location loc = player.getLocation();

			if(height == 0){
				height = loc.getY();
			} else {
				height += loc.getY();
			}

			World world = loc.getWorld();

			Location newLoc = new Location(world, newPoint.getX() - 0.5 , height , newPoint.getZ() + 0.5);

			newLoc.getBlock().setType(Material.WOOL);
			
			player.teleport(newLoc.add(0, 1, 0));
			sender.sendMessage(StringUtils.colour("&aYou have been teleported to the new point!"));
			
			
		} else {
			sender.sendMessage(StringUtils.colour("&cYou need to have a point selected!"));
		}
		
		return true;
	}

}
