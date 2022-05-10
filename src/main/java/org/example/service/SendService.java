package org.example.service;

public interface SendService {

    public void sendMessage();

    void sendFanoutMessage();

    void sendTopicMessage();

    void sendNormalMessage();

    void sendDelayedMessage(Integer time);
}
