package BetterChat.Chat;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
//去除指令末尾的空格

public class ChatCommand implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String Command=event.getMessage();
        while (Command.endsWith(" ")) {
            Command=Command.substring(0,Command.length()-1);
        }
        event.setMessage(Command);
    }
}
