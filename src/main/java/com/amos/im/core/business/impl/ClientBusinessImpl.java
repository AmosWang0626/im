package com.amos.im.core.business.impl;

import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.business.ClientBusiness;
import com.amos.im.core.constant.RedisKeys;
import com.amos.im.core.request.LoginRequest;
import com.amos.im.core.service.ClientService;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
@Service("clientBusiness")
public class ClientBusinessImpl implements ClientBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientBusinessImpl.class);

    @Resource
    private ClientService clientService;

    @Override
    public String login(LoginRequest loginRequest) {
        if (StringUtils.isBlank(loginRequest.getPassword())) {
            loginRequest.setPassword("123456");
        }
        Channel currentChannel = AttributeLoginUtil.getChannel(loginRequest.getUsername());
        if (currentChannel == null) {
            clientService.start(loginRequest);
            return "客户端启动中, 请稍后登录!";
        }

        return "客户端登录成功!";
    }

    @Override
    public List<String> logs() {

        return RedisUtil.lrange(RedisKeys.CLIENT_RUN_LOG, 0, -1);
    }
}
