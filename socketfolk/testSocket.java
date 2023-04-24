package folk.socketfolk;
import com.alibaba.fastjson2.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class testSocket {
    public static void main()throws Exception {
        Bukkit.broadcastMessage("接入SocketFolk");
        ServerSocket serverSocket = new ServerSocket(9978);
        while(true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = bufferedReader.readLine();
            OutputStream stream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(stream);
            switch (json_re(s,"mode")){
                case "command":
                    command(json_re(s,"msg"));
                    break;
                case "broadcast":
                    broadcast(json_re(s,"msg"));
                    break;
                case "get_players":
                    printWriter.write(getServer().getOnlinePlayers().toString());
                    printWriter.flush();
                    break;
            }
            socket.close();
        }
    }
    public static String json_re(String json_str,String key){
        JSONObject jsonObject = JSONObject.parseObject(json_str);
        return jsonObject.getString(key);
    }
    public static void command(String command_msg){
        getServer().getScheduler().scheduleSyncDelayedTask(SocketFolk.getinstance(), () -> {
            getServer().dispatchCommand(getServer().getConsoleSender(),command_msg);
        });
    }
    public  static void broadcast(String broadcast_msg){
        Bukkit.broadcastMessage(broadcast_msg);
    }
}