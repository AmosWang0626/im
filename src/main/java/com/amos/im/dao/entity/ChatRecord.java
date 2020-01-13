package com.amos.im.dao.entity;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * DESCRIPTION: 聊天记录
 *
 * @author <a href="mailto:amos.wang@xiaoi.com">amos.wang</a>
 * @date 1/13/2020
 */
@Getter
@Setter
@Accessors(chain = true)
public class ChatRecord {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "发送人ID")
    private String senderId;
    @ApiModelProperty(value = "发送人昵称")
    private String sender;

    @ApiModelProperty(value = "接收人ID")
    private String receiverId;
    @ApiModelProperty(value = "接收人昵称")
    private String receiver;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "发送人标示[一式两份](true >>> 发送人-接收人; false >>> 接收人-发送人)")
    private Boolean chatFlag;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime createTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
