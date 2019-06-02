package com.amos.im.core.business;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
public interface ClientBusiness {

    /**
     * 启动客户端
     */
    void start();

    /**
     * 服务端日志
     *
     * @return 日志
     */
    List<String> logs();

}
