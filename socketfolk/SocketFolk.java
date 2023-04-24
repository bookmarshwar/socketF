package folk.socketfolk;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedWriter;

public final class SocketFolk extends JavaPlugin {
private static SocketFolk instance= null;
public static SocketFolk getinstance(){
    return instance;
}
    @Override
    public void onEnable() {
    instance=this;
        // Plugin startup logic
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    testSocket.main();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
