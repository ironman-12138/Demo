package org.example.service.impl;

import org.example.service.ChannelService;
import org.springframework.stereotype.Service;

/**
 * 简单工厂模式
 */
@Service
public class ChannelFactory {

    public void getChannel(String channel) {
        ChannelService channelService = null;
        switch (channel) {
            case "smk":
                channelService = new SmkChannelServiceImpl();
                channelService.getChannel();
                break;
            case "zfb":
                channelService = new ZfbChannelServiceImpl();
                channelService.getChannel();
                break;
            default:
                System.out.println("暂未开通该渠道");
        }
    }
}
