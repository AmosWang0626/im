package com.amos.im.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * PROJECT: Sales
 * DESCRIPTION: IM 配置
 *
 * @author amos
 * @date 2019/6/2
 */
@Data
@Configuration
public class ImConfig {

    @Value("${im.port}")
    private Integer port;

    @Value("${im.host}")
    private String host;

}
