package com.amos.im.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@ConfigurationProperties(prefix = "im")
@EnableConfigurationProperties({ImConfig.class})
public class ImConfig {

    private Integer port;

    private String host;

    private String wsDomain;

}
