package BetterChat;



import BetterChat.Chat.Chat;
import BetterChat.Chat.ChatCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterChat extends JavaPlugin{
    boolean PlayerTitleISEnable;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Component LiuLI=Component.text("[LiuLI_BetterChat]").color(TextColor.color(0xFA9DE3));
        FileConfiguration config = getConfig();
        hasPlayerTitle();
        Chat MainChatInListener = new Chat(config);
        //事件加载
        getServer().getPluginManager().registerEvents(MainChatInListener.NewOnChat(PlayerTitleISEnable),this);
        //事件加载
        getServer().getPluginManager().registerEvents(new ChatCommand(),this);
        //指令加载
        getServer().getPluginCommand("LiuLIBetterChatConfig").setExecutor(new config(MainChatInListener,config));
        CommandSender OPSERVER= getServer().getConsoleSender();
        if (PlayerTitleISEnable){
            OPSERVER.sendMessage(LiuLI.append(Component.text("检测到 PlayerTitle 启用").color(TextColor.color(0x44FA67))));
            OPSERVER.sendMessage(LiuLI.append(Component.text("将在覆写时尝试获取称号").color(TextColor.color(0x44FA67))));
        }else{
            OPSERVER.sendMessage(LiuLI.append(Component.text("未检测到 PlayerTitle").color(TextColor.color(0x44FA67))));
            OPSERVER.sendMessage(LiuLI.append(Component.text("原版执行").color(TextColor.color(0x44FA67))));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public void hasPlayerTitle(){
        PlayerTitleISEnable =this.getServer().getPluginManager().isPluginEnabled("PlayerTitle");
    }

}
