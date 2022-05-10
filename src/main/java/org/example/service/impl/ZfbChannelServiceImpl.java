package org.example.service.impl;

import org.example.service.ChannelService;
import org.springframework.stereotype.Service;

@Service
public class ZfbChannelServiceImpl implements ChannelService {
    @Override
    public void getChannel() {
        System.out.println("支付宝授权");
    }
}
