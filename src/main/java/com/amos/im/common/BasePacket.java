package com.amos.im.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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

    @ApiModelProperty(value = "是否成功", hidden = true)
    private Boolean success;

    @ApiModelProperty(value = "失败原因", hidden = true)
    private GeneralCode generalCode;

    @ApiModelProperty(value = "发送人token")
    private String sender;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "协议版本", hidden = true)
    private Byte version = 1;

    /**
     * 指令(由实现类指定,指令数量有限[-128, 127])
     *
     * @return byte
     */
    @ApiModelProperty(value = "指令", hidden = true)
    public abstract Byte getCommand();

}
