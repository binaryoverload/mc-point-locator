package es.esy.williamoldham.mcpointlocator.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.esy.williamoldham.mcpointlocator.Main;
import es.esy.williamoldham.mcpointlocator.Point;

import static es.esy.williamoldham.mcpointlocator.Main.color;

public class LocateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(color("&cThis command is for players only!"));
		}
		
		if(args.length != 2){
			sender.sendMessage(color("&cWrong Command Usage!"));
			sender.sendMessage(color("&4Usage:"));
			sender.sendMessage(color("&c/locate <geLength> <geHeading> &2*Players only*"));
			return true;
		}
		
		double geLength = 0;
		double geHeading = 0;
		
		try {
			geLength = Double.parseDouble(args[0]);
			geHeading = Double.parseDouble(args[1]);
		} catch (NumberFormatException e){
			sender.sendMessage(color("&cBoth the length and the heading need to be valid numbers!"));
		}
		
		Player player = (Player) sender;
		
		if(!player.isOp()){
			return true;
		}
		
		Main main = Main.getInstance();
		
		boolean hasPoint = main.hasSelectedPoint(player);
		
		if(hasPoint){
			Point point = main.getSelectedPoint(player);
			
			Point newPoint = main.geToMC(geHeading, geLength, point);
			
			Location loc = player.getLocation();
			World world = loc.getWorld();
			
			Location newLoc = new Location(world, newPoint.getX() - 0.5 , 8, newPoint.getZ() + 0.5);

			newLoc.getBlock().setType(Material.WOOL);
			
			player.teleport(newLoc.add(0, 1, 0));
			sender.sendMessage(color("&aYou have been teleported to the new point!"));
			
			
		} else {
			sender.sendMessage(color("&cYou need to have a point selected!"));
		}
		
		return true;
	}

}
