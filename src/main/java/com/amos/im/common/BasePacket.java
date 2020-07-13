package com.amos.im.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * PROJECT: im
 * DESCRIPTION: request/response基类
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BasePacket {

    /**
     * 发送人token
     */
    private String sender;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令(由实现类指定,指令数量有限[-128, 127])
     *
     * @return byte
     */
    public abstract Byte getCommand();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
