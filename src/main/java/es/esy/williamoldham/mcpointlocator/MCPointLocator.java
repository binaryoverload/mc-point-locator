package es.esy.williamoldham.mcpointlocator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.esy.williamoldham.binarycore.BinaryPlugin;
import es.esy.williamoldham.binarycore.PluginLoader;
import es.esy.williamoldham.mcpointlocator.commands.*;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class MCPointLocator extends BinaryPlugin {

    private List<Point> points;
    private Map<Player, Point> selected;

    @Override
    public void init() {
        points = new ArrayList<>();
        selected = new HashMap<Player, Point>();
        PluginLoader.getInstance().registerPlugin(this, "1.6.0");
    }

    @Override
    public void load() {
        try {
            loadPoints();
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postload() {
    }

    @Override
    public void preload() {
    }

    @Override
    public void register() {
        getCommand("point").setExecutor(new PointCommand());
        getCommand("point").setTabCompleter(new PointTabCompleter());
        getCommand("setpoint").setExecutor(new SetPointCommand());
        getCommand("delpoint").setExecutor(new DelPointCommand());
        getCommand("locate").setExecutor(new LocateCommand());
    }

    @Override
    public void save() {
        try {
            savePoints();
        } catch (JsonIOException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void schedule() {
    }


    public void loadPoints() throws JsonSyntaxException, JsonIOException, FileNotFoundException {

        getDataFolder().mkdir();

        for (File file : getDataFolder().listFiles()) {
            if (file.isFile() && FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
                Gson gson = new Gson();
                Point point = gson.fromJson(new FileReader(file), Point.class);
                points.add(point);
            }
        }
    }

    public void savePoints() throws JsonIOException, IOException {

        getDataFolder().mkdir();

        for (Point point : points) {
            File file = new File(getDataFolder(), point.getName() + ".json");
            FileWriter writer = new FileWriter(file);
            Gson gson = new Gson();
            String json = gson.toJson(point);
            writer.write(json);
            writer.close();
        }
    }

    public boolean nameExists(String name) {
        for (Point point : points) {
            if (point.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Point geToMC(double geAngle, double geLength, Point point) {
        double angle = geAngle;

        double radAngle = angle / 180.0D * 3.141592653589793D;
        double hypoLength = geLength;

        double preRoundX = hypoLength * Math.sin(radAngle);
        double preRoundZ = hypoLength * Math.cos(radAngle);

        double postRoundX = Math.round(preRoundX);
        double postRoundZ = Math.round(preRoundZ);

        int changeX = (int) postRoundX;
        int changeZ = (int) postRoundZ;

        int xVal = (point.getX() + changeX);
        int zVal = (point.getZ() - changeZ);

        return new Point("", xVal, zVal);
    }

    public static MCPointLocator getInstance() {
        return MCPointLocator.getPlugin(MCPointLocator.class);
    }

    public void addPlayerPoint(Player player, Point p) {
        if (hasSelectedPoint(player)) {
            points.remove(p);
            points.add(p);
        }
        selected.put(player, p);
    }

    public void removePlayerPoint(Player player) {
        selected.remove(player);
    }

    public boolean hasSelectedPoint(Player player) {
        return selected.containsKey(player);
    }

    public Point getSelectedPoint(Player player) {
        if (hasSelectedPoint(player)) {
            return selected.get(player);
        } else {
            return null;
        }
    }

    public void addPoint(Point point) {
        if (!points.contains(point)) {
            points.add(point);
        }
    }

    public void removePoint(Point point) {
        points.remove(point);
    }

    public boolean pointExists(String name) {

        for (Point point : points) {
            if (point.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getPoint(String name) {

        for (Point point : points) {
            if (point.getName().equalsIgnoreCase(name)) {
                return point;
            }
        }

        return null;
    }
}
