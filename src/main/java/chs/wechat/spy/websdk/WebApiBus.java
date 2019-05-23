package chs.wechat.spy.websdk;

import chs.wechat.spy.websdk.api.annotation.Bind;
import chs.wechat.spy.websdk.api.constant.Config;
import chs.wechat.spy.websdk.api.enums.MsgType;
import chs.wechat.spy.websdk.api.model.WeChatMessage;
import chs.wechat.spy.websdk.utils.StringUtils;

public class WebApiBus extends WeChatBot {
    public WebApiBus(Config config) {
        super(config);
    }

    @Bind(msgType = MsgType.TEXT)
    public void handleText(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
            //log.info("接收到 [{}] 的消息: {}", message.getName(), message.getText());
           // this.sendMsg(message.getFromUserName(), "自动回复: " + message.getText());
        }
    }
}
