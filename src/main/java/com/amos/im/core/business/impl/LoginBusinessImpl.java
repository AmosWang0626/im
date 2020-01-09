package com.amos.im.core.business.impl;

import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.business.LoginBusiness;
import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.command.request.MessageRequest;
import com.amos.im.core.constant.ImConstant;
import com.amos.im.core.service.ClientService;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
@Service("loginBusiness")
public class LoginBusinessImpl implements LoginBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginBusinessImpl.class);

    @Resource
    private ClientService clientService;

    @Override
    public String login(LoginRequest loginRequest) {
        if (StringUtils.isBlank(loginRequest.getPassword())) {
            loginRequest.setPassword(ImConstant.DEFAULT_PASSWORD);
        }
        if (!ImConstant.DEFAULT_PASSWORD.equals(loginRequest.getPassword())) {
            return "登录失败, 密码错误!";
        }
        String username = loginRequest.getUsername();
        Channel currentChannel = AttributeLoginUtil.getChannelByUsername(username);
        if (currentChannel != null) {
            return "用户名已被占用, 请使用其他用户名登录!";
        }

        return clientService.start(loginRequest);
    }

    @Override
    public List<String> logs() {

        return clientService.logs();
    }

    @Override
    public String alone(MessageRequest messageRequest) {
        Channel fromChannel = AttributeLoginUtil.getChannel(messageRequest.getReceiver());
        messageRequest.setCreateTime(LocalDateTime.now());
        fromChannel.writeAndFlush(messageRequest);

        PrintUtil.message(messageRequest.getCreateTime(), "我", messageRequest.getMessage());

        return "发送成功!";
    }
}
