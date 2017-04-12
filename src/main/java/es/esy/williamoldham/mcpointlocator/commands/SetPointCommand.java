package es.esy.williamoldham.mcpointlocator.commands;

import com.google.gson.JsonIOException;
import es.esy.williamoldham.binarycore.util.StringUtils;
import es.esy.williamoldham.mcpointlocator.MCPointLocator;
import es.esy.williamoldham.mcpointlocator.Point;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SetPointCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		MCPointLocator main = MCPointLocator.getInstance();

		if (!sender.isOp()) {
			return true;
		}

		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(StringUtils.colour("&cYou need to be a player to use these args!"));
				sender.sendMessage(StringUtils.colour("&cConsole Usage - /setpoint <name> <x> <z>!"));
			}

			Player player = (Player) sender;

			Location loc = player.getLocation();

			int x = (int) loc.getX();
			int z = (int) loc.getZ();
			String name = args[0];

			Point point = new Point(name, x, z);

			if (main.pointExists(name)) {
				player.sendMessage(StringUtils.colour("&cPoint &4" + name + "&c already exists!"));
				return true;
			} else {
				main.addPoint(point);
			}
			
			try {
				main.savePoints();
			} catch (JsonIOException | IOException e) {
				e.printStackTrace();
			}

			player.sendMessage(StringUtils.colour("&aPoint &2" + name + "&a was created"));

			try {
				main.savePoints();
			} catch (JsonIOException | IOException e) {
				e.printStackTrace();
			}

			return true;

		} else if (args.length == 3) {

			String name;
			int x;
			int z;

			if (main.nameExists(args[0])) {
				sender.sendMessage(StringUtils.colour("&cPoint already exists!"));
				return true;
			} else {
				name = args[0];
			}

			try {
				x = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage(StringUtils.colour("&cX must be an integer!"));
				return true;
			}

			try {
				z = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				sender.sendMessage(StringUtils.colour("&cZ must be an integer!"));
				return true;
			}

			Point point = new Point(name, x, z);

			main.addPoint(point);

			try {
				main.savePoints();
			} catch (JsonIOException | IOException e) {
				e.printStackTrace();
			}

			sender.sendMessage(StringUtils.colour("&aPoint &2" + name + "&a was created"));

		} else {

			sender.sendMessage(StringUtils.colour("&4Usage:"));
			sender.sendMessage(StringUtils.colour("&c/setpoint <name> &2*Players only*"));
			sender.sendMessage(StringUtils.colour("&c/setpoint <name> <x> <z>"));
			return true;

		}

		return true;

	}

}
