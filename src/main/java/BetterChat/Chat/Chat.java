package BetterChat.Chat;


import BetterChat.BetterChat;
import com.handy.playertitle.api.PlayerTitleApi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class Chat{
    static String nameHoverEvent;
    static String nameClickEvent;

    /**重加载
     *
     * @param config 新配置文件
     * @param sender 发送人，便于发送提示消息
     */
    public void ReLoadConfig(FileConfiguration config, CommandSender sender){
        nameHoverEvent= config.getString("Player.name.Hover");
        nameClickEvent= config.getString("Player.name.Click");
        if(nameHoverEvent==null){
            nameHoverEvent= " ";
        }
        if(nameClickEvent==null){
            nameClickEvent= " ";
        }
        if (!nameClickEvent.startsWith("/")) {
            nameClickEvent="/"+nameClickEvent;
        }
        sender.sendMessage(Component.text("[LiuLI_BetterChat]").color(TextColor.color(0xFA9DE3)).append(Component.text(":重加载完成").color(TextColor.color(0x44FA67))));
    }

    /**
     * 构造函数
     * @param config 默认的congif即可
     */
    public Chat(FileConfiguration config){
       nameHoverEvent= config.getString("Player.name.Hover");
       nameClickEvent= config.getString("Player.name.Click");
    }
    public Listener NewOnChat(boolean isPlayerTitleEnable){
        if(isPlayerTitleEnable){
            return new onChatPlayerTitleVer();
        }
        else{
            return new onChatVer();
        }

    }

    public  Component changeMessage(String message, Component Text, Player player){
        //
        int i;

        while (true) {
            if (!message.contains("[")){
                Text=Text.append(Component.text(message));
                break;
            }
            i=message.indexOf("[");
            Text = Text.append(Component.text(message.substring(0, i)));
            message=message.substring(i);
            //主手物品检测
            if (message.indexOf("[item1]")==0){
                Text=Text.append(player.getInventory().getItemInMainHand().displayName().color(TextColor.color(0x7ADFFF)));
                message=message.substring(7);
            }
            //副手
            else if(message.indexOf("[item2]")==0){
                Text=Text.append(player.getInventory().getItemInOffHand().displayName().color(TextColor.color(0x83FF72)));
                message=message.substring(7);
            }
            //链接
            else if (message.indexOf("[web:")==0) {
                int temp=message.indexOf("[",1);
                int temp2=message.indexOf("]");
                if (temp==-1)temp=message.length();
                if (temp2==-1){
                    player.sendMessage("网页格式错误，缺少\"]\"作为结束");
                    return null;
                }
                if (temp2<temp){

                    Text=Text.append(Component.text("[网页链接]").clickEvent(ClickEvent.openUrl(message.substring(5,temp2))).hoverEvent(HoverEvent.showText(Component.text(message.substring(5,temp2)))).color(TextColor.color(0xFA9AE3)));
                    message=message.substring(temp2+1);
                }

            } else{
                Text=Text.append(Component.text("["));
                message=message.substring(1);
            }
        }

        return Text;
    }

    public  Component getNameEvent(Player player) {
        String Hover = nameHoverEvent;
        String Click = nameClickEvent;
        Component HoverText = Component.text("");
        //进行Hover的处理
        while (true) {
            if (!Hover.contains("[")) {
                if (Hover.isEmpty()) {
                    break;
                }
                HoverText = HoverText.append(Component.text(Hover));
                break;
            }
            HoverText = HoverText.append(Component.text(Hover.substring(0,Hover.indexOf("["))));
            Hover=Hover.substring(Hover.indexOf("["));
            if (Hover.indexOf("[PlayerName]") == 0) {
                HoverText = HoverText.append(Component.text(player.getName()));
                Hover = Hover.substring("[PlayerName]".length());
            } else {
                HoverText = HoverText.append(Component.text("["));
                Hover = Hover.substring(1);
            }
        }

        //进行Click的处理
        Click=Click.replace("[PlayerName]",player.getName());
        return Component.text(" <" + player.getName() + "> ").hoverEvent(HoverEvent.showText(HoverText)).clickEvent(ClickEvent.runCommand(Click));

    }

    //原版版本的聊天增强
    private class onChatVer implements Listener {
        @EventHandler
        void onPlayerChat(@NotNull AsyncPlayerChatEvent event){
            String message = event.getMessage();
            Component NewMessage;
            Player player = event.getPlayer();
            Server server = player.getServer();
            //获取玩家ID
            NewMessage =getNameEvent(player);
            //编辑信息
            Component finalNewMessage = changeMessage(message,NewMessage, player);
            //同步并发送
            if (finalNewMessage!=null) {
                event.setCancelled(true);
                server.getGlobalRegionScheduler().run(BetterChat.getPlugin(BetterChat.class), task -> {
                    server.sendMessage(finalNewMessage);
                });
            }
        }

    }


    //称号版本
    private class onChatPlayerTitleVer implements Listener{
        @EventHandler
        void onPlayerChatPlayerTitleVer(@NotNull AsyncPlayerChatEvent event){
            String message = event.getMessage();
            Component NewMessage;
            Player player = event.getPlayer();
            Server server = player.getServer();
            if (PlayerTitleApi.getInstance().findByPlayerUuidAndIsUse(player) != null) {
                NewMessage = Component.text(PlayerTitleApi.getInstance().findByPlayerUuidAndIsUse(player).getTitleName());
            } else {
                NewMessage = Component.text("[ " + ChatColor.GRAY + "萌新");
                NewMessage = NewMessage.append(Component.text(" ]"));
            }


            //获取玩家ID
            NewMessage = NewMessage.append(getNameEvent(player));
            //编辑信息
            Component finalNewMessage = changeMessage(message,NewMessage, player);

            //同步并发送
            if (finalNewMessage!=null){
                event.setCancelled(true);
                server.getGlobalRegionScheduler().run(BetterChat.getPlugin(BetterChat.class), task -> {

                    server.sendMessage(finalNewMessage);

                });
            }
        }

    }
}
