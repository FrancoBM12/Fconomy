package com.francobm.fconomy.files;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.List;
import java.util.logging.Level;

public class FileCreator extends YamlConfiguration {

    private final String fileName;
    private final Plugin plugin;
    private final java.io.File file;

    public FileCreator(Plugin plugin, String filename, String fileExtension, java.io.File folder){
        this.plugin = plugin;
        this.fileName = filename + (filename.endsWith(fileExtension) ? "" : fileExtension);
        this.file = new java.io.File(folder, this.fileName);
        this.createFile();
    }

    public FileCreator(Plugin plugin, String fileName) {
        this(plugin, fileName, ".yml");
    }

    public FileCreator(Plugin plugin, String fileName, String fileExtension) {
        this(plugin, fileName, fileExtension, plugin.getDataFolder());
    }

    private void createFile() {
        try {
            if (!file.exists()) {
                if (this.plugin.getResource(this.fileName) != null) {
                    this.plugin.saveResource(this.fileName, false);
                } else {
                    this.save(file);
                }
                this.load(file);
                return;
            }
            this.load(file);

            this.save(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataFolder(){
        return this.plugin.getDataFolder() + java.io.File.separator + this.fileName;
    }
    public void save() {
        try {
            this.save(file);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.", e);
        }
    }

    public void reload() {
        try {
            load(file);
        } catch(IOException | InvalidConfigurationException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.", e);
        }
    }

    @Override
    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', super.getString(path));
    }

    @Override
    public String getString(String path, String def) {
        String s = super.getString(path, def);

        return s != null ? ChatColor.translateAlternateColorCodes('&', s) : def;
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> list = super.getStringList(path);

        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));

        return list;
    }

}