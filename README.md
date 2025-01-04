# MC-Plugin.LiuLiBetterChat
兼容PlayerTitle:

https://ricedoc.handyplus.cn/wiki/PlayerTitle/

支持Paper，folia的聊天增强插件,通过[item1]展示主手物品[item2]展示副手物品[web:xxxxxx]展示链接   方便的修改聊天栏点击玩家ID的事件（如：点击后TPA该玩家，可自己修改点击事件与悬浮的文本框）
# 目前支持的：

  - 聊天栏输入[item1]展示主手物品
    
  - 聊天栏输入[item2]展示副手物品
    
  - 聊天栏输入[web:+链接]发送网页链接

都与正常消息兼容，比如  

    你好啊，我手里有[item1]，你想要么
  
# 关于指令的支持:

      /llbc reload  //重新加载ID悬浮文本与点击事件
      /llbc Click /tpa [PlayerName]            
      /llbc Hover 点我tpa到[PlayerName]的位置
  
  更改点击/悬浮执行的指令,[PlayerName]将自带替换为发送者的ID(注意大小写)
  
 ### 最后，本插件是我学习java和插件开发加起来没2天开发出来的，命名规范与注释全靠IDEA写的，不是故意写混淆，望周知
