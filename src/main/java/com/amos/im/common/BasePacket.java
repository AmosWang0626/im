package com.amos.im.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

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
     * 是否成功
     */
    @ApiModelProperty(hidden = true)
    private Boolean success;
    /**
     * 失败原因
     */
    @ApiModelProperty(hidden = true)
    private GeneralCode generalCode;
    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 协议版本
     */
    @ApiModelProperty(hidden = true)
    private Byte version = 1;

    /**
     * 指令
     *
     * @return 命令
     */
    @ApiModelProperty(hidden = true)
    public abstract Byte getCommand();

}
