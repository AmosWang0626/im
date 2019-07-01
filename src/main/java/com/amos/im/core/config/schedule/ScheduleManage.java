package com.amos.im.core.config.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DESCRIPTION: 定时任务管理
 *
 * @author amos.wang
 * @date 2019/7/1
 */
@Component
public class ScheduleManage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleManage.class);

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedDelay = 1000)
    public void serverMessage() {
        String currentTime = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
        LOGGER.info("Message send to client: {}", currentTime);
        // simpMessagingTemplate.convertAndSend("/server/message", currentTime);
    }

}
