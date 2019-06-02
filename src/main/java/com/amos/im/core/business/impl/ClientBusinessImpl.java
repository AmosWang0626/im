package com.amos.im.core.business.impl;

import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.business.ClientBusiness;
import com.amos.im.core.constant.RedisKeys;
import com.amos.im.core.service.ClientService;
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
    public void start() {
        clientService.start();
    }

    @Override
    public List<String> logs() {

        return RedisUtil.lrange(RedisKeys.CLIENT_RUN_LOG, 0, -1);
    }
}
