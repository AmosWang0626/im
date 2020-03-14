package com.amos.im.core.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DESCRIPTION: 定时任务管理
 * 使用时加上注解即可 @Component
 *
 * @author amos.wang
 * @date 2019/7/1
 */
@Slf4j
public class ScheduleManage {

    @Scheduled(fixedDelay = 300000)
    public void serverMessage() {
        String currentTime = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
        log.info("Schedule Manage [{}]", currentTime);
    }

}
