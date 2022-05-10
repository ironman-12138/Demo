package org.example.service.impl;

import org.example.service.ChannelService;
import org.springframework.stereotype.Service;

@Service
public class SmkChannelServiceImpl implements ChannelService {

    public void getChannel() {
        System.out.println("市民卡授权");
    }
}
