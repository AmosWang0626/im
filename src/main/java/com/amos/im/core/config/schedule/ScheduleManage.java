package com.amos.im.core.config.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(fixedDelay = 300000)
    public void serverMessage() {
        String currentTime = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
        LOGGER.info("Schedule Manage [{}]", currentTime);
    }

}
