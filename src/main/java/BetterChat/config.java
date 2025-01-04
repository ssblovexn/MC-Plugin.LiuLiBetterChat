package BetterChat;

import BetterChat.Chat.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class config implements TabCompleter, CommandExecutor {
    Chat Main;
    FileConfiguration config;
    public config(Chat Main, FileConfiguration config) {
        this.Main = Main;
        this.config = config;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("reload");
            completions.add("Hover");
            completions.add("Click");
        }
        if (args.length == 2&&args[0].equalsIgnoreCase("Hover")) {
            completions.add("点我TPA到 [PlayerName]");
        }
        if (args.length == 2&&args[0].equalsIgnoreCase("Click")) {
            completions.add("/tpa [PlayerName]");
        }
        return completions;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0){
            return Tips(sender);
        }
        if(args[0].equalsIgnoreCase("reload")) {
            config=BetterChat.getPlugin(BetterChat.class).getConfig();
            Main.ReLoadConfig(config,sender);
            return true;
        }else if(args[0].equalsIgnoreCase("Hover")) {
            if(args.length==1) {
                sender.sendMessage(Component.text("设置ID悬浮栏需要提供文本,[PlayerName]将替换为ID").color(TextColor.color(0xFA826F)));
                sender.sendMessage(Component.text("如：点我TPA [[PlayerName]] 将会被替换为 点我TPA [LiuLI_EYEs]").color(TextColor.color(0xFA826F)));
            }else {
                StringBuilder NewHover= new StringBuilder();
                for (int i=1;i<args.length;i++) {
                    NewHover.append(args[i]).append(" ");
                }
                config.set("Player.name.Hover",NewHover.toString());
            }
            saveConfigInFile(sender);
            return true;
        }else if(args[0].equalsIgnoreCase("Click")) {
            if(args.length==1) {
                sender.sendMessage(Component.text("设置ID点击后的命令需要提供文本,[PlayerName]将替换为ID").color(TextColor.color(0xFA826F)));
                sender.sendMessage(Component.text("如：/tpa [PlayerName] 将会被替换为 /tpa LiuLI_EYEs").color(TextColor.color(0xFA826F)));
            }else {
                StringBuilder NewHover= new StringBuilder();
                for (int i=1;i<args.length;i++) {
                    NewHover.append(args[i]).append(" ");
                }
                config.set("Player.name.Click",NewHover.toString());
            }
            saveConfigInFile(sender);
            return true;
        }else {
            return Tips(sender);
        }
    }
    //变量支持提示
    private static boolean Tips(@NotNull CommandSender sender) {
        sender.sendMessage(Component.text("目前来说，有以下的变量仅可以在本插件中使用").color(TextColor.color(0x44FA67)));
        sender.sendMessage(Component.text("[PlayerName]将替换为消息发送者的名称，你发出的消息就是你的ID").color(TextColor.color(0xFA9DE3)));
        //sender.sendMessage(Component.text("有以下的变量可在全局玩家指令中生效(控制台不算)").color(TextColor.color(0x44FA67)));
        return  true;
    }

    /**
     * 用于保存配置文件
     */
    private void saveConfigInFile(){

        BetterChat.getPlugin(BetterChat.class).saveConfig();
    }


    private void saveConfigInFile(CommandSender sender){
        saveConfigInFile();
        sender.sendMessage(Component.text("[LiuLI_BetterChat]").color(TextColor.color(0xFA9DE3)).append(Component.text("完成,已保存,重加载(llbc reload)后生效").color(TextColor.color(0x44FA67))));
    }
}
