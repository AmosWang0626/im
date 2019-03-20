package com.amos.im.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: request/response基类
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@Data
@Accessors(chain = true)
public abstract class BasePacket {

    /**
     * response
     */
    private GeneralCode generalCode;

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     *
     * @return 命令
     */
    public abstract Byte getCommand();

}
